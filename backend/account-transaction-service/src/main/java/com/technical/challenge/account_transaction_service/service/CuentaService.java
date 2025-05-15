package com.technical.challenge.account_transaction_service.service;

import com.technical.challenge.account_transaction_service.entity.Cuenta;
import com.technical.challenge.account_transaction_service.repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    private final CuentaRepository repo;

    public CuentaService(CuentaRepository repo) {
        this.repo = repo;
    }

    public List<Cuenta> listar() {
        return repo.findAll();
    }

    public Optional<Cuenta> obtenerPorId(Long id) {
        return repo.findById(id);
    }

    public Cuenta crear(Cuenta cuenta) {
        if (repo.existsByNumeroCuenta(cuenta.getNumeroCuenta())) {
            throw new RuntimeException("El número de cuenta ya existe.");
        }
        return repo.save(cuenta);
    }

    public Cuenta actualizar(Long id, Cuenta nuevaCuenta) {
        Cuenta existente = repo.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        existente.setNumeroCuenta(nuevaCuenta.getNumeroCuenta());
        existente.setTipoCuenta(nuevaCuenta.getTipoCuenta());
        existente.setSaldoInicial(nuevaCuenta.getSaldoInicial());
        existente.setEstado(nuevaCuenta.getEstado());
        existente.setClienteId(nuevaCuenta.getClienteId());

        return repo.save(existente);
    }

    public void eliminar(Long id) {
        Cuenta cuenta = repo.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        repo.delete(cuenta);
    }
}
