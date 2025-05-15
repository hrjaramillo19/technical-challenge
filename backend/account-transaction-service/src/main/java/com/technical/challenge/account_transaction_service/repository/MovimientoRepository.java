package com.technical.challenge.account_transaction_service.repository;

import com.technical.challenge.account_transaction_service.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaId(Long cuentaId);

    Optional<Movimiento> findByIdAndCuentaId(Long id, Long cuentaId);

    List<Movimiento> findByCuentaIdAndFechaGreaterThanEqualOrderByFechaAscIdAsc(Long cuentaId, LocalDate fecha);

    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDate fechaInicio, LocalDate fechaFin);

}
