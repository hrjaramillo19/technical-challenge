package com.technical.challenge.account_transaction_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoReporteDTO {
    private LocalDate fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;
}
