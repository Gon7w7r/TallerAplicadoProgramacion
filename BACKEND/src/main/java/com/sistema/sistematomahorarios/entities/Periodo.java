package com.sistema.sistematomahorarios.entities;

import jakarta.persistence.*;


@Entity
@Table(name="periodo")
public class Periodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPeriodo;

    @Column(nullable = false)
    private String nombre;

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    


}
