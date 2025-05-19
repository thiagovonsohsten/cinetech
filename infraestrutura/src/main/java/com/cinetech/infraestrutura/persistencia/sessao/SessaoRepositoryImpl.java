package com.cinetech.infraestrutura.persistencia.sessao;

import com.cinetech.dominio.comum.Sessao;
import com.cinetech.dominio.sessao.repository.SessaoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoRepositoryImpl extends JpaRepository<Sessao, Long>, SessaoRepository {
} 