package com.technical.challenge.account_transaction_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoDTO {
    private String tipoMovimiento;
    private Double valor;
    private Long cuentaId;
}