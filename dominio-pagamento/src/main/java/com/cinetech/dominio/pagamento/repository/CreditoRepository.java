package com.cinetech.dominio.pagamento.repository;

import com.cinetech.dominio.pagamento.Credito;
import com.cinetech.dominio.pagamento.StatusCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CreditoRepository extends JpaRepository<Credito, Long> {
    List<Credito> findByClienteIdAndStatus(Long clienteId, StatusCredito status);
} 