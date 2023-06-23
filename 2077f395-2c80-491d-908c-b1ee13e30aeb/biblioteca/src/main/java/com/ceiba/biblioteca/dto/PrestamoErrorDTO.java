package com.ceiba.biblioteca.dto;

public class PrestamoErrorDTO extends PrestamoDTO{

    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
