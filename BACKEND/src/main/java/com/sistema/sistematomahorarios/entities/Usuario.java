package com.sistema.sistematomahorarios.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @Column(length = 12)
    private String rut;
    @Column(nullable = false)
    private String nombre;
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    @Column(name="tipo_usuario", nullable = false)
    private String tipoUsuario;
    @Column(nullable = false)
    private String email;

    public Usuario() {
    }

    public Usuario(String rut, String nombre, String passwordHash, String tipoUsuario) {
        this.rut = rut;
        this.nombre = nombre;
        this.passwordHash = passwordHash;
        this.tipoUsuario = tipoUsuario;
    }

    public String getRut() {    
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    public String getEmail() {
        return email; 
    }

    public void setEmail(String email) { 
        this.email = email;
    }

}
