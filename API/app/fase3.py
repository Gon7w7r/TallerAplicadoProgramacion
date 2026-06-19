from fastapi import APIRouter
from pydantic import BaseModel
from typing import List, Dict, Optional
import pulp

router = APIRouter()


# --- Modelos ---

class ScoreAlumnoSeccion(BaseModel):
    idAlumno: int
    idSeccion: int
    score: float

class ClusterAlumno(BaseModel):
    idAlumno: int
    cluster: int

class SeccionInfo(BaseModel):
    idSeccion: int
    idAsignatura: int
    cupoMaximo: int
    cupoMinimo: int

class Fase3Request(BaseModel):
    scores: List[ScoreAlumnoSeccion]
    clusters: List[ClusterAlumno]
    secciones: List[SeccionInfo]
    alumnos: List[int]
    theta: float = 0.40

class AsignacionAlumno(BaseModel):
    idAlumno: int
    idSeccion: int
    score: float

class Fase3Response(BaseModel):
    asignaciones: List[AsignacionAlumno]
    satisfaccionPromedio: float
    seccionesNoAbiertas: List[int]
    alumnosBajoTheta: List[int]
    exitoso: bool
    mensaje: str


# --- Helpers ---

def verificar_factibilidad(
    alumnos: List[int],
    secciones: List[int],
    secciones_por_asignatura: Dict[int, List[int]],
    scores_dict: Dict,
    theta: float,
) -> List[int]:
    # detecta alumnos para los que ninguna seccion de alguna asignatura
    # supera theta; si existen, el solver fallaria de todas formas
    sin_opcion = []
    for alumno in alumnos:
        for idAsignatura, secs in secciones_por_asignatura.items():
            max_score = max(
                (scores_dict.get((alumno, sec), 0.0) for sec in secs),
                default=0.0,
            )
            if max_score < theta:
                sin_opcion.append(alumno)
                break  # basta con que falle en una asignatura
    return sin_opcion


# --- Endpoint ---

@router.post("/asignar", response_model=Fase3Response)
def asignar(request: Fase3Request):

    # indice plano (idAlumno, idSeccion) -> score para acceso O(1)
    scores_dict: Dict = {
        (s.idAlumno, s.idSeccion): s.score
        for s in request.scores
    }

    # agrupacion de secciones por asignatura para las restricciones del ILP
    secciones_por_asignatura: Dict[int, List[int]] = {}
    for sec in request.secciones:
        secciones_por_asignatura.setdefault(sec.idAsignatura, []).append(sec.idSeccion)

    # indice cluster por alumno, disponible para uso futuro en restricciones de afinidad
    cluster_por_alumno: Dict[int, int] = {
        c.idAlumno: c.cluster for c in request.clusters
    }

    alumnos = request.alumnos
    secciones = [s.idSeccion for s in request.secciones]

    # verificacion previa: si algún alumno no puede alcanzar theta en alguna
    # asignatura, el ILP sera infactible; conviene reportarlo antes de correr el solver
    sin_opcion = verificar_factibilidad(
        alumnos, secciones, secciones_por_asignatura, scores_dict, request.theta
    )
    if sin_opcion:
        return Fase3Response(
            asignaciones=[],
            satisfaccionPromedio=0.0,
            seccionesNoAbiertas=[],
            alumnosBajoTheta=sin_opcion,
            exitoso=False,
            mensaje=f"{len(sin_opcion)} alumnos no pueden alcanzar theta con la oferta disponible.",
        )

    # --- construccion del problema ILP ---
    prob = pulp.LpProblem("NexusMatch", pulp.LpMaximize)

    # c[a, s] = 1 si el alumno a es asignado a la seccion s
    c = pulp.LpVariable.dicts(
        "c",
        [(a, s) for a in alumnos for s in secciones],
        cat="Binary",
    )

    # y[s] = 1 si la seccion s se abre (tiene al menos cupoMinimo alumnos)
    y = pulp.LpVariable.dicts("y", secciones, cat="Binary")

    # objetivo: maximizar la suma de scores de todas las asignaciones
    prob += pulp.lpSum(
        scores_dict.get((a, s), 0.0) * c[(a, s)]
        for a in alumnos
        for s in secciones
    )

    # R1: cada alumno recibe exactamente una seccion por asignatura
    for alumno in alumnos:
        for idAsignatura, secs in secciones_por_asignatura.items():
            prob += pulp.lpSum(c[(alumno, s)] for s in secs) == 1

    # R2: ninguna seccion supera su cupo maximo
    for sec_info in request.secciones:
        prob += (
            pulp.lpSum(c[(a, sec_info.idSeccion)] for a in alumnos)
            <= sec_info.cupoMaximo
        )

    # R3: si una seccion se abre (y=1) debe tener al menos cupoMinimo alumnos;
    # si no llega al minimo (y=0) ningun alumno puede ser asignado a ella
    for sec_info in request.secciones:
        s = sec_info.idSeccion
        prob += (
            pulp.lpSum(c[(a, s)] for a in alumnos)
            >= sec_info.cupoMinimo * y[s]
        )
        for a in alumnos:
            prob += c[(a, s)] <= y[s]

    # R4: el score que recibe cada alumno en cada asignatura debe ser >= theta
    # se expresa como: sum(score * c) >= theta * sum(c) para las secciones de esa asignatura
    for alumno in alumnos:
        for idAsignatura, secs in secciones_por_asignatura.items():
            prob += (
                pulp.lpSum(
                    scores_dict.get((alumno, s), 0.0) * c[(alumno, s)]
                    for s in secs
                )
                >= request.theta * pulp.lpSum(c[(alumno, s)] for s in secs)
            )

    # correr CBC sin output; timeLimit evita que instancias grandes cuelguen el servidor
    prob.solve(pulp.PULP_CBC_CMD(msg=0, timeLimit=60))

    if pulp.LpStatus[prob.status] != "Optimal":
        return Fase3Response(
            asignaciones=[],
            satisfaccionPromedio=0.0,
            seccionesNoAbiertas=[],
            alumnosBajoTheta=[],
            exitoso=False,
            mensaje=f"El solver termino con estado: {pulp.LpStatus[prob.status]}.",
        )

    # extraer asignaciones donde c[a,s] = 1
    asignaciones: List[AsignacionAlumno] = []
    for a in alumnos:
        for s in secciones:
            val = pulp.value(c[(a, s)])
            if val is not None and round(val) == 1:
                asignaciones.append(AsignacionAlumno(
                    idAlumno=a,
                    idSeccion=s,
                    score=scores_dict.get((a, s), 0.0),
                ))

    # secciones que no alcanzaron cupo minimo y no se abren
    secciones_no_abiertas = [
        s for s in secciones
        if pulp.value(y[s]) is not None and round(pulp.value(y[s])) == 0
    ]

    # alumnos cuyo score final quedo por debajo de theta (puede ocurrir si
    # el solver relajo la restriccion R4 al acercarse al time limit)
    alumnos_bajo_theta = [
        a.idAlumno for a in asignaciones
        if a.score < request.theta
    ]

    satisfaccion_promedio = (
        sum(a.score for a in asignaciones) / len(asignaciones)
        if asignaciones else 0.0
    )

    return Fase3Response(
        asignaciones=asignaciones,
        satisfaccionPromedio=round(satisfaccion_promedio, 4),
        seccionesNoAbiertas=secciones_no_abiertas,
        alumnosBajoTheta=alumnos_bajo_theta,
        exitoso=True,
        mensaje="Asignacion completada exitosamente.",
    )