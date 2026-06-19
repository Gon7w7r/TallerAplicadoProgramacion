package com.sistema.sistematomahorarios.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "modalidad")
public class Modalidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idModalidad;

    @Column(nullable = false)
    private String nombre;

    public Integer getIdModalidad() {
        return idModalidad;
    }

    public void setIdModalidad(Integer idModalidad) {
        this.idModalidad = idModalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
