package com.technical.challenge.account_transaction_service.dto;

public class ClienteDTO {

    private String nombre;

    public ClienteDTO() {
    }

    public ClienteDTO(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
