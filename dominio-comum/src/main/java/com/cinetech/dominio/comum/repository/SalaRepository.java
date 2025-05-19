package com.cinetech.dominio.comum.repository;

import com.cinetech.dominio.comum.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
} 