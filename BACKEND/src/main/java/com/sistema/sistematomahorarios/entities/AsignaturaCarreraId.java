package com.sistema.sistematomahorarios.entities;

import java.io.Serializable;
import java.util.Objects;

public class AsignaturaCarreraId implements Serializable {
    
    private Integer asignatura;
    private Integer carrera;

    public AsignaturaCarreraId() {}

    public AsignaturaCarreraId(Integer asignatura, Integer carrera) {
        this.asignatura = asignatura;
        this.carrera = carrera;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsignaturaCarreraId)) return false;
        AsignaturaCarreraId that = (AsignaturaCarreraId) o;
        return Objects.equals(asignatura, that.asignatura) &&
               Objects.equals(carrera, that.carrera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(asignatura, carrera);
    }

}
