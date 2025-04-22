package com.cinetech.infrastructure.repository;

import com.cinetech.domain.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    List<Filme> findByDataFimExibicaoAfter(LocalDate data);
} 