package com.sistema.sistematomahorarios.entities;


import java.io.Serializable;
import java.util.Objects;

public class AlumnoCarreraId implements Serializable {

    private Integer alumno;
    private Integer carrera;

    public AlumnoCarreraId() {}

    public AlumnoCarreraId(Integer alumno, Integer carrera) {
        this.alumno = alumno;
        this.carrera = carrera;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlumnoCarreraId)) return false;
        AlumnoCarreraId that = (AlumnoCarreraId) o;
        return Objects.equals(alumno, that.alumno) &&
               Objects.equals(carrera, that.carrera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alumno, carrera);
    }    

}
