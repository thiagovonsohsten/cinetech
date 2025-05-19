package com.cinetech.dominio.assento.repository;

import com.cinetech.dominio.comum.Assento;
import com.cinetech.dominio.comum.StatusAssento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssentoRepository extends JpaRepository<Assento, Long> {
    List<Assento> findBySalaIdAndStatus(Long salaId, StatusAssento status);
} 