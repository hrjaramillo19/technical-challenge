package com.technical.challenge.client_person_service.repository;

import com.technical.challenge.client_person_service.entity.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void testGuardarYRecuperarCliente() {
        Cliente cliente = new Cliente();
        cliente.setContrasena("abcd1234");
        cliente.setEstado(true);
        cliente.setNombre("Ana");
        cliente.setGenero("Femenino");
        cliente.setEdad(25);
        cliente.setIdentificacion("1234567890");
        cliente.setDireccion("Guayaquil");
        cliente.setTelefono("0988888888");

        Cliente clienteGuardado = clienteRepository.save(cliente);

        assertThat(clienteGuardado.getClienteId()).isNotNull();
        assertThat(clienteGuardado.getNombre()).isEqualTo("Ana");
        assertThat(clienteRepository.findById(clienteGuardado.getClienteId())).isPresent();
    }
}
