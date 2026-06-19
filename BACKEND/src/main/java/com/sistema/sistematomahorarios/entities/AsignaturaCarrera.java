package com.sistema.sistematomahorarios.entities;



import jakarta.persistence.*;

@Entity
@Table(name = "asignatura_carrera")
@IdClass(AsignaturaCarreraId.class)
public class AsignaturaCarrera {

    @Id
    @ManyToOne
    @JoinColumn(name="asignatura_id_asignatura")
    private Asignatura asignatura;

    
    @Id
    @ManyToOne
    @JoinColumn(name="carrera_id_carrera")
    private Carrera carrera;


    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }
    
}
