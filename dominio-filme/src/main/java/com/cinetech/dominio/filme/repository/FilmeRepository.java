package com.cinetech.dominio.filme.repository;

import com.cinetech.dominio.filme.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    List<Filme> findByDataFimExibicaoAfter(LocalDate data);
} 