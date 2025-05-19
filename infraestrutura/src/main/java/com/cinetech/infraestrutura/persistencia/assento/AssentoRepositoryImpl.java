package com.cinetech.infraestrutura.persistencia.assento;

import com.cinetech.dominio.comum.Assento;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cinetech.dominio.assento.repository.AssentoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssentoRepositoryImpl extends JpaRepository<Assento, Long>, AssentoRepository {
} 