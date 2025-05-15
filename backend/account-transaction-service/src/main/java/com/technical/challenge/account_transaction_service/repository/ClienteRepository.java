package com.technical.challenge.account_transaction_service.repository;

import com.technical.challenge.account_transaction_service.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
