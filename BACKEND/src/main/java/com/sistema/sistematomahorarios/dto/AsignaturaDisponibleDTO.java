package com.sistema.sistematomahorarios.dto;


import java.util.List;

public class AsignaturaDisponibleDTO {
     private Integer idAsignatura;
    private String nombreAsignatura;
    private List<SeccionDisponibleDTO> secciones;

    public AsignaturaDisponibleDTO(
            Integer idAsignatura,
            String nombreAsignatura,
            List<SeccionDisponibleDTO> secciones
    ) {
        this.idAsignatura = idAsignatura;
        this.nombreAsignatura = nombreAsignatura;
        this.secciones = secciones;
    }

    public Integer getIdAsignatura() {
        return idAsignatura;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public List<SeccionDisponibleDTO> getSecciones() {
        return secciones;
    }
    

}
