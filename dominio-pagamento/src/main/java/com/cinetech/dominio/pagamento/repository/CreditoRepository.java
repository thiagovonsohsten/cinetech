package com.cinetech.dominio.pagamento.repository;

import com.cinetech.dominio.pagamento.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CreditoRepository extends JpaRepository<Credito, Long> {
    List<Credito> findByClienteIdAndUtilizadoFalse(Long clienteId);
} 