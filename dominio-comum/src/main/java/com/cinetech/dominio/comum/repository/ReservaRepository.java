package com.cinetech.dominio.comum.repository;

import com.cinetech.dominio.comum.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findBySessaoId(Long sessaoId);
} 