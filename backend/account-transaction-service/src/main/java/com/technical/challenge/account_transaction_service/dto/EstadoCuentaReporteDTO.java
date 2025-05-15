package com.technical.challenge.account_transaction_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoCuentaReporteDTO {
    private Long clienteId;
    private String clienteNombre;
    private List<CuentaReporteDTO> cuentas;
}
