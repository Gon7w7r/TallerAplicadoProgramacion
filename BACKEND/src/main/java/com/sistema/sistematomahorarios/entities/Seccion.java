package com.sistema.sistematomahorarios.entities;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "seccion")
public class Seccion {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSeccion;

    @OneToMany(mappedBy = "seccion")
    private List<SeccionHorario> horarios;

    private Integer cupos;


    //Relaciones

    @ManyToOne
    @JoinColumn(name = "asignatura_id_asignatura", nullable = false)
    private Asignatura asignatura;

    @ManyToOne
    @JoinColumn(name = "profesor_id_profesor", nullable = false)
    private Profesor profesor;

    @ManyToOne
    @JoinColumn(name = "sala_id_sala", nullable = false)
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "sede_id_sede", nullable = false)
    private Sede sede;

    @ManyToOne
    @JoinColumn(name = "modalidad_id_modalidad", nullable = false)
    private Modalidad modalidad;

    @ManyToOne
    @JoinColumn(name = "jornada_id_jornada", nullable = false)
    private Jornada jornada;


    //Getters y Setters

    public Integer getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Integer idSeccion) {
        this.idSeccion = idSeccion;
    }

    public Integer getCupos() {
        return cupos;
    }

    public void setCupos(Integer cupos) {
        this.cupos = cupos;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }

        public List<SeccionHorario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<SeccionHorario> horarios) {
        this.horarios = horarios;
    }
}
