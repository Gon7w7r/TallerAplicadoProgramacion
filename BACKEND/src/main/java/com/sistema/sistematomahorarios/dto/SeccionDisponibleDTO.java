package com.sistema.sistematomahorarios.dto;

public class SeccionDisponibleDTO {

     private Integer idSeccion;
    private String profesor;
    private String sala;
    private Integer cupos;

    public SeccionDisponibleDTO(
            Integer idSeccion,
            String profesor,
            String sala,
            Integer cupos
    ) {
        this.idSeccion = idSeccion;
        this.profesor = profesor;
        this.sala = sala;
        this.cupos = cupos;
    }

    public Integer getIdSeccion() {
        return idSeccion;
    }

    public String getProfesor() {
        return profesor;
    }

    public String getSala() {
        return sala;
    }

    public Integer getCupos() {
        return cupos;
    }
}
