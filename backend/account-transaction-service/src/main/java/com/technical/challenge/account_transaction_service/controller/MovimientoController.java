package com.technical.challenge.account_transaction_service.controller;

import com.technical.challenge.account_transaction_service.entity.Movimiento;
import com.technical.challenge.account_transaction_service.entity.Cuenta;
import com.technical.challenge.account_transaction_service.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.technical.challenge.account_transaction_service.dto.MovimientoDTO;
import com.technical.challenge.account_transaction_service.dto.MovimientoResponseDTO;
import com.technical.challenge.account_transaction_service.repository.CuentaRepository;
import com.technical.challenge.account_transaction_service.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.LinkedHashMap;
import java.time.LocalDate;
import com.technical.challenge.account_transaction_service.exception.ApiError;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private MovimientoRepository movimientoRepository;

    @GetMapping
    public ResponseEntity<Object> manejarSinCuenta() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", 400);
        body.put("error", "Bad Request");
        body.put("message", "Debe proporcionar el ID de la cuenta en la URL");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @GetMapping("/{id_cuenta}")
    public List<MovimientoResponseDTO> listarPorCuenta(@PathVariable Long id_cuenta) {
        List<Movimiento> movimientos = movimientoService.listarPorCuenta(id_cuenta);
        return movimientos.stream()
                .map(MovimientoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id_cuenta}/{id_movimiento}")
    public MovimientoResponseDTO obtenerPorCuentaYId(
            @PathVariable Long id_cuenta,
            @PathVariable Long id_movimiento) {
        Movimiento movimiento = movimientoService.obtenerPorCuentaYId(id_cuenta, id_movimiento);
        return new MovimientoResponseDTO(movimiento);
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody MovimientoDTO movimientoDTO) {
        Cuenta cuenta = cuentaRepository.findById(movimientoDTO.getCuentaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

        // Validar saldo si es retiro
        if (movimientoDTO.getTipoMovimiento().equalsIgnoreCase("RETIRO")
                && cuenta.getSaldoInicial() < movimientoDTO.getValor()) {
            ApiError error = new ApiError(LocalDate.now(), 400, "Bad Request", "Saldo no disponible");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        Double nuevoSaldo = movimientoDTO.getTipoMovimiento().equalsIgnoreCase("RETIRO")
                ? cuenta.getSaldoInicial() - movimientoDTO.getValor()
                : cuenta.getSaldoInicial() + movimientoDTO.getValor();

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        Movimiento nuevoMovimiento = Movimiento.builder()
                .tipoMovimiento(movimientoDTO.getTipoMovimiento())
                .valor(movimientoDTO.getValor())
                .fecha(LocalDate.now())
                .saldo(nuevoSaldo)
                .cuenta(cuenta)
                .build();

        Movimiento movimientoGuardado = movimientoRepository.save(nuevoMovimiento);

        return ResponseEntity.ok(new MovimientoResponseDTO(movimientoGuardado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody MovimientoDTO movimientoDTO) {

        Cuenta cuenta = cuentaRepository.findById(movimientoDTO.getCuentaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

        Movimiento movimiento = movimientoRepository.findByIdAndCuentaId(id, movimientoDTO.getCuentaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Movimiento no encontrado para la cuenta"));

        Double saldoActual = cuenta.getSaldoInicial();
        Double saldoRevertido = movimiento.getTipoMovimiento().equalsIgnoreCase("RETIRO")
                ? saldoActual + movimiento.getValor()
                : saldoActual - movimiento.getValor();

        Double saldoNuevo = movimientoDTO.getTipoMovimiento().equalsIgnoreCase("RETIRO")
                ? saldoRevertido - movimientoDTO.getValor()
                : saldoRevertido + movimientoDTO.getValor();

        if (saldoNuevo < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo no disponible");
        }

        cuenta.setSaldoInicial(saldoNuevo);
        cuentaRepository.save(cuenta);

        movimiento.setTipoMovimiento(movimientoDTO.getTipoMovimiento());
        movimiento.setValor(movimientoDTO.getValor());
        movimiento.setSaldo(saldoNuevo);

        Movimiento actualizado = movimientoRepository.save(movimiento);

        return ResponseEntity.ok(new MovimientoResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimiento no encontrado"));
        System.out.println("Eliminando movimiento: " + movimiento);
        System.out.println("Eliminando movimiento: " + movimiento.getCuenta());
        Cuenta cuenta = movimiento.getCuenta();
        Double saldoActual = cuenta.getSaldoInicial();

        Double saldoNuevo = movimiento.getTipoMovimiento().equalsIgnoreCase("RETIRO")
                ? saldoActual + movimiento.getValor()
                : saldoActual - movimiento.getValor();

        if (saldoNuevo < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La eliminación dejaría el saldo negativo");
        }

        cuenta.setSaldoInicial(saldoNuevo);
        cuentaRepository.save(cuenta);

        movimientoRepository.deleteById(id);

        List<Movimiento> movimientosPosteriores = movimientoRepository
                .findByCuentaIdAndFechaGreaterThanEqualOrderByFechaAscIdAsc(
                        cuenta.getId(), movimiento.getFecha());

        Double saldo = saldoNuevo;

        for (Movimiento m : movimientosPosteriores) {
            if (m.getTipoMovimiento().equalsIgnoreCase("DEPOSITO")) {
                saldo += m.getValor();
            } else {
                saldo -= m.getValor();
            }

            m.setSaldo(saldo);
        }

        movimientoRepository.saveAll(movimientosPosteriores);

        return ResponseEntity.noContent().build();
    }
}
