from fastapi import APIRouter
from pydantic import BaseModel
from typing import List, Optional, Dict
import json
import networkx as nx
import community as community_louvain

router = APIRouter()


# --- Modelos ---

class SeccionPosible(BaseModel):
    idSeccion: int
    idProfesor: int
    idAsignatura: int
    jornada: str
    subjornada: str   # MANANA, TARDE, ANTES_21, DESPUES_21
    dias: List[str]   # dias reales de la seccion, viene de fase 1

class PreferenciaAlumno(BaseModel):
    idAlumno: int
    jornada: str
    bloqueHorario: str
    concentracion: str
    diaSinClase: Optional[str]
    idCompaneroPreferido: Optional[int]
    profesoresPreferidos: Optional[str]  # JSON {"idAsignatura": idProfesor}

class Fase2Request(BaseModel):
    secciones: List[SeccionPosible]
    preferencias: List[PreferenciaAlumno]

class ScoreAlumnoSeccion(BaseModel):
    idAlumno: int
    idSeccion: int
    score: float

class ClusterAlumno(BaseModel):
    idAlumno: int
    cluster: int

class Fase2Response(BaseModel):
    scores: List[ScoreAlumnoSeccion]
    clusters: List[ClusterAlumno]
    demandaPorSeccion: Dict[int, int]


# --- Funciones de score ---

def calcular_s1(preferencia: PreferenciaAlumno, seccion: SeccionPosible) -> float:
    # coincidencia exacta entre bloque preferido y subjornada de la seccion
    return 1.0 if preferencia.bloqueHorario == seccion.subjornada else 0.0

def calcular_s2(preferencia: PreferenciaAlumno, dias_seccion: List[str]) -> float:
    # penaliza la diferencia entre dias reales y el ideal segun concentracion
    # CONCENTRADO quiere 2 dias, DISTRIBUIDO quiere hasta 5
    d_real = len(set(dias_seccion))
    d_ideal = 2 if preferencia.concentracion == "CONCENTRADO" else 5
    diferencia = abs(d_real - d_ideal)
    # normalizado sobre el rango maximo posible (0 a 3 dias de diferencia)
    return max(0.0, 1.0 - (diferencia / 3))

def calcular_s3(preferencia: PreferenciaAlumno, idProfesor: int, idAsignatura: int) -> float:
    # 1.0 si el profesor de la seccion coincide con el preferido para esa asignatura
    if not preferencia.profesoresPreferidos:
        return 0.0
    try:
        prefs = json.loads(preferencia.profesoresPreferidos)
        prof_preferido = prefs.get(str(idAsignatura))
        if prof_preferido and int(prof_preferido) == idProfesor:
            return 1.0
    except (json.JSONDecodeError, ValueError):
        pass
    return 0.0

def calcular_s4(preferencia: PreferenciaAlumno, dias_seccion: List[str]) -> float:
    # 0.0 si la seccion cae en el dia que el alumno quiere libre, 1.0 si no hay conflicto
    if not preferencia.diaSinClase:
        return 1.0
    return 0.0 if preferencia.diaSinClase in dias_seccion else 1.0


# --- Construccion del grafo de companeros ---

def construir_grafo_companeros(preferencias: List[PreferenciaAlumno]) -> nx.Graph:
    G = nx.Graph()

    for alumno in preferencias:
        G.add_node(alumno.idAlumno)

    # indice rapido para verificar si la preferencia es mutua
    companeros = {
        a.idAlumno: a.idCompaneroPreferido
        for a in preferencias
        if a.idCompaneroPreferido is not None
    }

    for alumno in preferencias:
        if alumno.idCompaneroPreferido is None:
            continue
        # peso 2 si ambos se eligieron mutuamente, 1 si solo uno eligio al otro
        es_mutuo = companeros.get(alumno.idCompaneroPreferido) == alumno.idAlumno
        peso = 2 if es_mutuo else 1
        # add_edge es idempotente con el mismo par, no duplica la arista
        if not G.has_edge(alumno.idAlumno, alumno.idCompaneroPreferido):
            G.add_edge(alumno.idAlumno, alumno.idCompaneroPreferido, weight=peso)

    return G


# --- Endpoint ---

@router.post("/calcular-scores", response_model=Fase2Response)
def calcular_scores(request: Fase2Request):
    scores: List[ScoreAlumnoSeccion] = []
    # demanda estimada: cuantos alumnos elegirian cada seccion como su mejor opcion
    demanda: Dict[int, int] = {s.idSeccion: 0 for s in request.secciones}

    for alumno in request.preferencias:
        scores_alumno: List[ScoreAlumnoSeccion] = []

        for seccion in request.secciones:
            # usar los dias reales que vienen de fase 1, no un placeholder
            dias = seccion.dias

            s1 = calcular_s1(alumno, seccion)
            s2 = calcular_s2(alumno, dias)
            s3 = calcular_s3(alumno, seccion.idProfesor, seccion.idAsignatura)
            s4 = calcular_s4(alumno, dias)
            # test comment
            # pesos: bloque horario 40%, concentracion 25%, profesor 20%, dia libre 15%
            # nota: los pesos suman 1.0, s5 (companero) se incorpora solo en louvain
            score = (0.40 * s1) + (0.25 * s2) + (0.20 * s3) + (0.15 * s4)

            scores_alumno.append(ScoreAlumnoSeccion(
                idAlumno=alumno.idAlumno,
                idSeccion=seccion.idSeccion,
                score=round(score, 4),
            ))

        scores.extend(scores_alumno)

        # la seccion con mayor score es la que el alumno "demandaria" naturalmente
        if scores_alumno:
            mejor = max(scores_alumno, key=lambda x: x.score)
            demanda[mejor.idSeccion] = demanda.get(mejor.idSeccion, 0) + 1

    # clustering de afinidad social con Louvain
    # alumnos en el mismo cluster tendran preferencia de quedar juntos en fase 3
    G = construir_grafo_companeros(request.preferencias)
    particion = community_louvain.best_partition(G)
    clusters = [ClusterAlumno(idAlumno=k, cluster=v) for k, v in particion.items()]

    return Fase2Response(
        scores=scores,
        clusters=clusters,
        demandaPorSeccion=demanda,
    )