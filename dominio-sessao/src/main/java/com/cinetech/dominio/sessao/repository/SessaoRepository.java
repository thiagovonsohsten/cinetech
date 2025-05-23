package com.cinetech.dominio.sessao.repository;

import com.cinetech.dominio.comum.Sessao;
import com.cinetech.dominio.comum.StatusSessao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    List<Sessao> findByStatus(StatusSessao status);
    List<Sessao> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
} 