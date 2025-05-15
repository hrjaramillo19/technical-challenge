package com.technical.challenge.account_transaction_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaReporteDTO {
    private Long cuentaId;
    private String numeroCuenta;
    private Double saldo;
    private List<MovimientoReporteDTO> movimientos;
}
