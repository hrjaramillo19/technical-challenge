package com.technical.challenge.account_transaction_service.service;

import com.technical.challenge.account_transaction_service.entity.Cuenta;
import com.technical.challenge.account_transaction_service.entity.Movimiento;
import com.technical.challenge.account_transaction_service.repository.CuentaRepository;
import com.technical.challenge.account_transaction_service.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    public List<Movimiento> listar() {
        return movimientoRepository.findAll();
    }

    public Movimiento obtenerPorId(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimiento no encontrado"));
    }

    @Transactional
    public Movimiento crear(Movimiento movimiento) {
        Cuenta cuenta = cuentaRepository.findById(movimiento.getCuenta().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

        Double nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();

        if (nuevoSaldo < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo no disponible");
        }

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        movimiento.setFecha(LocalDate.now());
        movimiento.setSaldo(nuevoSaldo);
        return movimientoRepository.save(movimiento);
    }

    public Movimiento actualizar(Long id, Movimiento movimiento) {
        Movimiento existente = obtenerPorId(id);
        existente.setTipoMovimiento(movimiento.getTipoMovimiento());
        existente.setValor(movimiento.getValor());
        return movimientoRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!movimientoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimiento no encontrado");
        }
        movimientoRepository.deleteById(id);
    }

    public List<Movimiento> listarPorCuenta(Long idCuenta) {
        return movimientoRepository.findByCuentaId(idCuenta);
    }

    public Movimiento obtenerPorCuentaYId(Long idCuenta, Long idMovimiento) {
        return movimientoRepository.findByIdAndCuentaId(idMovimiento, idCuenta)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Movimiento no encontrado para la cuenta"));
    }

}
