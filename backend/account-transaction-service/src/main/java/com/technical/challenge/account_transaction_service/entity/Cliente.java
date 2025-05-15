package com.technical.challenge.account_transaction_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "clientes")
public class Cliente {
    @Id
    private Long clienteId;

    private String nombre;

}
