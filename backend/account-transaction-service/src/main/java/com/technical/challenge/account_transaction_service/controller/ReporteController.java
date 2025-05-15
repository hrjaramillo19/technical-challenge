package com.technical.challenge.account_transaction_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.technical.challenge.account_transaction_service.dto.EstadoCuentaReporteDTO;
import com.technical.challenge.account_transaction_service.service.ReporteService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public ResponseEntity<EstadoCuentaReporteDTO> obtenerReporte(
            @RequestParam("fecha") String fechaRango,
            @RequestParam("cliente") Long clienteId) {

        // Parsear rango fechas, ej: "2025-01-01,2025-05-15"
        String[] fechas = fechaRango.split(",");
        if (fechas.length != 2) {
            return ResponseEntity.badRequest().build();
        }

        LocalDate fechaInicio = LocalDate.parse(fechas[0]);
        LocalDate fechaFin = LocalDate.parse(fechas[1]);

        EstadoCuentaReporteDTO reporte = reporteService.generarReporteEstadoCuenta(clienteId, fechaInicio, fechaFin);

        return ResponseEntity.ok(reporte);
    }
}
