package com.sistema.sistematomahorarios.dto;

import java.util.List;

public class SeccionRequestDTO {
    private Integer cupos;
    private Integer idAsignatura;
    private Integer idProfesor;
    private Integer idSala;
    private Integer idSede;
    private Integer idModalidad;
    private Integer idJornada;
    private List<Integer> idHorarios;

    public Integer getCupos() { 
        return cupos; 
    }

    public void setCupos(Integer cupos) { 
        this.cupos = cupos; 
    }

    public Integer getIdAsignatura() { 
        return idAsignatura; 
    }

    public void setIdAsignatura(Integer idAsignatura) { 
        this.idAsignatura = idAsignatura; 
    }

    public Integer getIdProfesor() { 
        return idProfesor; 
    }

    public void setIdProfesor(Integer idProfesor) { 
        this.idProfesor = idProfesor; 
    }

    public Integer getIdSala() { 
        return idSala; 
    }

    public void setIdSala(Integer idSala) { 
        this.idSala = idSala; 
    }

    public Integer getIdSede() { 
        return idSede; 
    }

    public void setIdSede(Integer idSede) { 
        this.idSede = idSede; 
    }

    public Integer getIdModalidad() { 
        return idModalidad; 
    }

    public void setIdModalidad(Integer idModalidad) { 
        this.idModalidad = idModalidad; 
    }

    public Integer getIdJornada() { 
        return idJornada; 
    }

    public void setIdJornada(Integer idJornada) { 
        this.idJornada = idJornada; 
    }

    public List<Integer> getIdHorarios() { 
        return idHorarios; 
    }

    public void setIdHorarios(List<Integer> idHorarios) { 
        this.idHorarios = idHorarios; 
    }
}