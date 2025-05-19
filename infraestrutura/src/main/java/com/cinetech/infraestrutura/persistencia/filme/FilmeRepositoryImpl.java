package com.cinetech.infraestrutura.persistencia.filme;

import com.cinetech.dominio.filme.Filme;
import com.cinetech.dominio.filme.repository.FilmeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepositoryImpl extends JpaRepository<Filme, Long>, FilmeRepository {
} 