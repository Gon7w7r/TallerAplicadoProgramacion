package com.sistema.sistematomahorarios.dto;

public class LoginResponse {

    private Integer idEntidad;   
    private String rut;
    private String nombre;
    private String tipoUsuario;

    public LoginResponse(Integer idEntidad, String rut, String nombre, String tipoUsuario) {
        this.idEntidad  = idEntidad;
        this.rut        = rut;
        this.nombre     = nombre;
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getIdEntidad()   { return idEntidad; }
    public String  getRut()         { return rut; }
    public String  getNombre()      { return nombre; }
    public String  getTipoUsuario() { return tipoUsuario; }
}