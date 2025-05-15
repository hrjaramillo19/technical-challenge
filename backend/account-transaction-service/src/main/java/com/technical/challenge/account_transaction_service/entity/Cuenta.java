package com.technical.challenge.account_transaction_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cuentas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cuenta", unique = true, nullable = false)
    private String numeroCuenta;

    @Column(name = "tipo_cuenta", nullable = false)
    private String tipoCuenta;

    @Column(name = "saldo_inicial", nullable = false)
    private Double saldoInicial;

    @Column(nullable = false)
    private String estado;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId; // relación lógica al cliente (referencia al ID)

}