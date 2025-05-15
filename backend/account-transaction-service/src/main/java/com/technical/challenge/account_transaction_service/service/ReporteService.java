package com.technical.challenge.account_transaction_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.technical.challenge.account_transaction_service.dto.MovimientoReporteDTO;
import com.technical.challenge.account_transaction_service.dto.EstadoCuentaReporteDTO;
import com.technical.challenge.account_transaction_service.dto.CuentaReporteDTO;
import com.technical.challenge.account_transaction_service.repository.CuentaRepository;
import com.technical.challenge.account_transaction_service.repository.MovimientoRepository;
import com.technical.challenge.account_transaction_service.repository.ClienteRepository;
import com.technical.challenge.account_transaction_service.entity.Cuenta;
import com.technical.challenge.account_transaction_service.entity.Cliente;
import com.technical.challenge.account_transaction_service.entity.Movimiento;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

        @Autowired
        private CuentaRepository cuentaRepository;

        @Autowired
        private MovimientoRepository movimientoRepository;

        @Autowired
        private ClienteRepository clienteRepository;

        public EstadoCuentaReporteDTO generarReporteEstadoCuenta(Long clienteId, LocalDate fechaInicio,
                        LocalDate fechaFin) {
                List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);

                String nombreCliente = obtenerNombreCliente(clienteId);

                List<CuentaReporteDTO> cuentasReporte = cuentas.stream().map(cuenta -> {
                        List<Movimiento> movimientos = movimientoRepository
                                        .findByCuentaIdAndFechaBetween(cuenta.getId(), fechaInicio, fechaFin);

                        List<MovimientoReporteDTO> movimientosDto = movimientos.stream()
                                        .map(mov -> {
                                                double valorConSigno = mov.getValor();
                                                if ("RETIRO".equalsIgnoreCase(mov.getTipoMovimiento())) {
                                                        valorConSigno = -Math.abs(mov.getValor());
                                                }
                                                return new MovimientoReporteDTO(
                                                                mov.getFecha(),
                                                                mov.getTipoMovimiento(),
                                                                valorConSigno,
                                                                mov.getSaldo());
                                        })
                                        .collect(Collectors.toList());

                        return new CuentaReporteDTO(cuenta.getId(), cuenta.getNumeroCuenta(), cuenta.getSaldoInicial(),
                                        movimientosDto);
                }).collect(Collectors.toList());

                return new EstadoCuentaReporteDTO(clienteId, nombreCliente, cuentasReporte);
        }

        private String obtenerNombreCliente(Long clienteId) {
                return clienteRepository.findById(clienteId)
                                .map(Cliente::getNombre)
                                .orElse("Desconocido");
        }
}
