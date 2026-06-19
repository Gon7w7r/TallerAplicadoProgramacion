package com.sistema.sistematomahorarios.dto;

public class ItemCountDTO {
    private String nombre;
    private long   cantidad;

    public ItemCountDTO(String nombre, long cantidad) {
        this.nombre   = nombre;
        this.cantidad = cantidad;
    }

    public String getNombre()  { return nombre; }
    public long   getCantidad(){ return cantidad; }
}