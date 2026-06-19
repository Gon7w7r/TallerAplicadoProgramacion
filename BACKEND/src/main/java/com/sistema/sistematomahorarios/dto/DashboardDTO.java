package com.sistema.sistematomahorarios.dto;

import java.util.List;

public class DashboardDTO {
    private long alumnosConHorario;
    private long departamentos;
    private long carreras;
    private long asignaturas;
    private long secciones;
    private List<ItemCountDTO> alumnosPorCarrera;
    private List<ItemCountDTO> seccionesPorAsignatura;

    public DashboardDTO(
        long alumnosConHorario,
        long departamentos,
        long carreras,
        long asignaturas,
        long secciones,
        List<ItemCountDTO> alumnosPorCarrera,
        List<ItemCountDTO> seccionesPorAsignatura
    ) {
        this.alumnosConHorario      = alumnosConHorario;
        this.departamentos          = departamentos;
        this.carreras               = carreras;
        this.asignaturas            = asignaturas;
        this.secciones              = secciones;
        this.alumnosPorCarrera      = alumnosPorCarrera;
        this.seccionesPorAsignatura = seccionesPorAsignatura;
    }

    public long getAlumnosConHorario()                   { return alumnosConHorario; }
    public long getDepartamentos()                        { return departamentos; }
    public long getCarreras()                             { return carreras; }
    public long getAsignaturas()                          { return asignaturas; }
    public long getSecciones()                            { return secciones; }
    public List<ItemCountDTO> getAlumnosPorCarrera()     { return alumnosPorCarrera; }
    public List<ItemCountDTO> getSeccionesPorAsignatura(){ return seccionesPorAsignatura; }
}