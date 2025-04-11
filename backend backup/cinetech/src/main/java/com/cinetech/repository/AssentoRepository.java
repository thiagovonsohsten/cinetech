package com.cinetech.repository;

import com.cinetech.model.Assento;
import com.cinetech.model.enums.StatusAssento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssentoRepository extends JpaRepository<Assento, Long> {
    List<Assento> findBySalaIdAndStatus(Long salaId, StatusAssento status);
} 