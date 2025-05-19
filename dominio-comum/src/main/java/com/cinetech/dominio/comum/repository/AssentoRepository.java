package com.cinetech.dominio.comum.repository;

import com.cinetech.dominio.comum.Assento;
import com.cinetech.dominio.comum.StatusAssento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AssentoRepository extends JpaRepository<Assento, Long> {
    List<Assento> findBySalaIdAndStatus(Long salaId, StatusAssento status);
} 