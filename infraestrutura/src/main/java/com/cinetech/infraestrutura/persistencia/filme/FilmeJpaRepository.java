package com.cinetech.infraestrutura.persistencia.filme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmeJpaRepository extends JpaRepository<FilmeEntity, Long> {
    List<FilmeEntity> findByTituloContaining(String titulo);
    List<FilmeEntity> findByGenero(String genero);
} 