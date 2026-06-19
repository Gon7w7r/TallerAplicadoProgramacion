package com.sistema.sistematomahorarios.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "administrativo")
public class Administrativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAdministrativo;

    @OneToOne
    @JoinColumn(name = "usuarios_rut", nullable = false)
    private Usuario usuario;

    public Administrativo() {}

    public Administrativo(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getIdAdministrativo() { return idAdministrativo; }
    public void setIdAdministrativo(Integer id) { this.idAdministrativo = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}