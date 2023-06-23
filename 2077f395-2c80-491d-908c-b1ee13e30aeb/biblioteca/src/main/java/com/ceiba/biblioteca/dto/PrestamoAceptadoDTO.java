package com.ceiba.biblioteca.dto;

public class PrestamoAceptadoDTO extends PrestamoDTO{

    private Integer id;
    private String fechaMaximaDevolucion;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFechaMaximaDevolucion() {
        return fechaMaximaDevolucion;
    }

    public void setFechaMaximaDevolucion(String fechaMaximaDevolucion) {
        this.fechaMaximaDevolucion = fechaMaximaDevolucion;
    }
}
