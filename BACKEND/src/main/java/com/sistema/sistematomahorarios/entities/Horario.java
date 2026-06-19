package com.sistema.sistematomahorarios.entities;

import jakarta.persistence.*;



@Entity
@Table(name = "horario")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHorario;

    @Column(name= "dia_semana", nullable = false)
    private String diaSemana;

    @Column(name= "hora_inicio", nullable = false)
    private java.time.LocalTime horaInicio;

    @Column(name= "hora_fin", nullable = false)
    private java.time.LocalTime horaFin;


    //getters y setters
    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public java.time.LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(java.time.LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public java.time.LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(java.time.LocalTime horaFin) {
        this.horaFin = horaFin;
    }
}
