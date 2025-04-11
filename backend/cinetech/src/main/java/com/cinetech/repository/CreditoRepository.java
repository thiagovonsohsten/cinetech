package com.cinetech.repository;

import com.cinetech.model.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CreditoRepository extends JpaRepository<Credito, Long> {
    List<Credito> findByClienteIdAndUtilizadoFalse(Long clienteId);
} 