from fastapi import APIRouter
from pydantic import BaseModel
from typing import List, Optional
from itertools import combinations

router = APIRouter()


# --- Modelos ---

class BloqueHorario(BaseModel):
    idHorario: int
    diaSemana: str
    horaInicio: str  # formato "HH:MM:SS"
    horaFin: str

class ProfesorDisponibilidad(BaseModel):
    idProfesor: int
    idAsignatura: int
    maxSecciones: int
    disponibilidad: List[BloqueHorario]

class Sala(BaseModel):
    idSala: int
    nombre: str
    tipo: str
    capacidad: int
    tienePc: bool

class Fase1Request(BaseModel):
    profesores: List[ProfesorDisponibilidad]
    salas: List[Sala]
    cupoMaximo: int
    cupoMinimo: int

class SeccionPosible(BaseModel):
    idProfesor: int
    idAsignatura: int
    idSala: int
    jornada: str
    dias: List[str]       # dias reales de la seccion, usado por fase 2 para s2 y s4
    bloques: List[BloqueHorario]


# --- Helpers ---

def determinar_jornada(bloques: List[BloqueHorario]) -> str:
    # si cualquier bloque empieza a las 19:00 o despues, la seccion es vespertina
    for b in bloques:
        if b.horaInicio >= "19:00":
            return "VESPERTINA"
    return "DIURNA"

def bloques_por_dia(bloques: List[BloqueHorario]) -> dict:
    # agrupa los bloques por dia de la semana
    dias = {}
    for b in bloques:
        dias.setdefault(b.diaSemana, []).append(b)
    return dias

def es_combinacion_valida(bloques: List[BloqueHorario]) -> bool:
    # regla: exactamente 2 dias, cada dia con 2 o 3 bloques, total 5 bloques
    por_dia = bloques_por_dia(bloques)
    if len(por_dia) != 2:
        return False
    for dia, bs in por_dia.items():
        if len(bs) < 2 or len(bs) > 3:
            return False
    total = sum(len(bs) for bs in por_dia.values())
    return total == 5

def sala_compatible(sala: Sala, asignatura_tipo: str, requiere_pc: bool, cupo: int) -> bool:
    # la sala debe tener capacidad suficiente y pc si la asignatura lo requiere
    if sala.capacidad < cupo:
        return False
    if requiere_pc and not sala.tienePc:
        return False
    return True


# --- Endpoint ---

@router.post("/generar-secciones", response_model=List[SeccionPosible])
def generar_secciones(request: Fase1Request):
    secciones = []

    for profesor in request.profesores:
        disponibilidad = profesor.disponibilidad
        dias_disponibles = bloques_por_dia(disponibilidad)

        # generar todos los pares de dias posibles para combinar
        pares_dias = list(combinations(dias_disponibles.keys(), 2))

        secciones_profesor = []

        for dia1, dia2 in pares_dias:
            bloques_dia1 = dias_disponibles[dia1]
            bloques_dia2 = dias_disponibles[dia2]

            # distribucion 2+3 o 3+2 bloques entre los dos dias
            for n1 in [2, 3]:
                n2 = 5 - n1
                if n2 < 2 or n2 > 3:
                    continue

                for combo1 in combinations(bloques_dia1, n1):
                    for combo2 in combinations(bloques_dia2, n2):
                        bloques_seccion = list(combo1) + list(combo2)

                        if not es_combinacion_valida(bloques_seccion):
                            continue

                        jornada = determinar_jornada(bloques_seccion)

                        # primera sala con capacidad >= cupoMinimo
                        sala_asignada = next(
                            (s for s in request.salas if sala_compatible(
                                s, "", False, request.cupoMinimo
                            )),
                            None
                        )

                        if sala_asignada is None:
                            continue

                        secciones_profesor.append(SeccionPosible(
                            idProfesor=profesor.idProfesor,
                            idAsignatura=profesor.idAsignatura,
                            idSala=sala_asignada.idSala,
                            jornada=jornada,
                            dias=[dia1, dia2],
                            bloques=bloques_seccion,
                        ))

        # respetar el maximo de secciones que el profesor puede dictar
        secciones_validas = secciones_profesor[:profesor.maxSecciones]
        secciones.extend(secciones_validas)

    return secciones