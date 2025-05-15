package com.technical.challenge.client_person_service.service;

import com.technical.challenge.client_person_service.entity.Cliente;
import com.technical.challenge.client_person_service.exception.BusinessException;
import com.technical.challenge.client_person_service.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public List<Cliente> listar() {
        return repo.findAll();
    }

    public Cliente obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new BusinessException("Cliente no encontrado con id: " + id));
    }

    public Cliente crear(Cliente cliente) {
        if (repo.findByIdentificacion(cliente.getIdentificacion()).isPresent()) {
            throw new BusinessException("Ya existe un cliente con esa identificación.");
        }
        return repo.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente clienteActualizado) {
        Cliente existente = obtenerPorId(id);
        existente.setNombre(clienteActualizado.getNombre());
        existente.setGenero(clienteActualizado.getGenero());
        existente.setEdad(clienteActualizado.getEdad());
        existente.setIdentificacion(clienteActualizado.getIdentificacion());
        existente.setDireccion(clienteActualizado.getDireccion());
        existente.setTelefono(clienteActualizado.getTelefono());
        existente.setContrasena(clienteActualizado.getContrasena());
        existente.setEstado(clienteActualizado.getEstado());
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        Cliente cliente = obtenerPorId(id);
        repo.delete(cliente);
    }
}
