package com.sistema.sistematomahorarios.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "alumno_carrera")
@IdClass(AlumnoCarreraId.class)
public class AlumnoCarrera {
    @Id
    @ManyToOne
    @JoinColumn(name = "ALUMNO_id_alumno")
    private Alumno alumno;

    @Id
    @ManyToOne
    @JoinColumn(name = "CARRERA_id_carrera")
    private Carrera carrera;

    private String estado; // ACTIVO, EGRESADO, etc


    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
