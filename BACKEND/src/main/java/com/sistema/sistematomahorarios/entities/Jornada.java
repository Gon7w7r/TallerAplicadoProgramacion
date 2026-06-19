package com.sistema.sistematomahorarios.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "jornada")
public class Jornada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idJornada;

    @Column(nullable = false)
    private String nombre;

    public Integer getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(Integer idJornada) {
        this.idJornada = idJornada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
