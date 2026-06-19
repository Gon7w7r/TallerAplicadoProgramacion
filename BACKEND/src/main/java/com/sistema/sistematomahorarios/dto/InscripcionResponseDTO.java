package com.sistema.sistematomahorarios.dto;

public class InscripcionResponseDTO {
    private Integer idAsignatura;
    private String nombreAsignatura;

    private Integer idSeccion;

    public InscripcionResponseDTO(
            Integer idAsignatura,
            String nombreAsignatura,
            Integer idSeccion
    ) {
        this.idAsignatura = idAsignatura;
        this.nombreAsignatura = nombreAsignatura;
        this.idSeccion = idSeccion;
    }

    public Integer getIdAsignatura() {
        return idAsignatura;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public Integer getIdSeccion() {
        return idSeccion;
    }

}
