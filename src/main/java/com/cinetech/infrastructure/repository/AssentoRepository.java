package com.cinetech.infrastructure.repository;

import com.cinetech.domain.model.Assento;
import com.cinetech.domain.model.StatusAssento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssentoRepository extends JpaRepository<Assento, Long> {
    List<Assento> findBySalaIdAndStatus(Long salaId, StatusAssento status);
} 