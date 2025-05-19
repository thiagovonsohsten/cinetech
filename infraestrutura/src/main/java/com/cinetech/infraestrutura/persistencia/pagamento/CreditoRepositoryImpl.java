package com.cinetech.infraestrutura.persistencia.pagamento;

import com.cinetech.dominio.pagamento.Credito;
import com.cinetech.dominio.pagamento.repository.CreditoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditoRepositoryImpl extends JpaRepository<Credito, Long>, CreditoRepository {
} 