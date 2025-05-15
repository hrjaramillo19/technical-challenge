package com.technical.challenge.client_person_service.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void testCrearCliente() {
        Cliente cliente = new Cliente();
        cliente.setClienteId(1L);
        cliente.setContrasena("1234");
        cliente.setEstado(true);
        cliente.setNombre("Juan");
        cliente.setGenero("Masculino");
        cliente.setEdad(30);
        cliente.setIdentificacion("1100110011");
        cliente.setDireccion("Quito");
        cliente.setTelefono("0999999999");

        assertEquals(1L, cliente.getClienteId());
        assertEquals("1234", cliente.getContrasena());
        assertTrue(cliente.getEstado());
        assertEquals("Juan", cliente.getNombre());
        assertEquals("Masculino", cliente.getGenero());
        assertEquals(30, cliente.getEdad());
        assertEquals("1100110011", cliente.getIdentificacion());
        assertEquals("Quito", cliente.getDireccion());
        assertEquals("0999999999", cliente.getTelefono());
    }
}
