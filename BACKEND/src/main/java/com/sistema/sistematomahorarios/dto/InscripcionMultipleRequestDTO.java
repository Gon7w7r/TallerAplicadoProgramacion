package com.sistema.sistematomahorarios.dto;

import java.util.List;

public class InscripcionMultipleRequestDTO {
    private Integer idAlumno;
    private Integer idPeriodo;

    private List<Integer> secciones;

    public Integer getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Integer idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public List<Integer> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<Integer> secciones) {
        this.secciones = secciones;
    }

}
