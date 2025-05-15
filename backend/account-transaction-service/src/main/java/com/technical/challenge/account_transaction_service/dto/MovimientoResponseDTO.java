package com.technical.challenge.account_transaction_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.technical.challenge.account_transaction_service.entity.Movimiento;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoResponseDTO {
    private Long id;
    private LocalDate fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;
    private Long cuenta;

    // Constructor
    public MovimientoResponseDTO(Movimiento movimiento) {
        this.id = movimiento.getId();
        this.fecha = movimiento.getFecha();
        this.tipoMovimiento = movimiento.getTipoMovimiento();
        this.valor = movimiento.getValor();
        this.saldo = movimiento.getSaldo();
        this.cuenta = movimiento.getCuenta().getId();
    }
}