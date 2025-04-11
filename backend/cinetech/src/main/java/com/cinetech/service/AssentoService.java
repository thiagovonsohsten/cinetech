package com.cinetech.service;

import com.cinetech.model.Assento;
import com.cinetech.model.enums.StatusAssento;
import com.cinetech.repository.AssentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssentoService {
    @Autowired
    private AssentoRepository assentoRepository;

    @Transactional
    public Assento reservarAssento(Long assentoId) {
        Assento assento = assentoRepository.findById(assentoId)
            .orElseThrow(() -> new RuntimeException("Assento não encontrado"));

        if (assento.getStatus() != StatusAssento.LIVRE) {
            throw new RuntimeException("Assento não está disponível");
        }

        assento.setStatus(StatusAssento.RESERVADO);
        return assentoRepository.save(assento);
    }

    @Transactional
    public void liberarAssento(Long assentoId) {
        Assento assento = assentoRepository.findById(assentoId)
            .orElseThrow(() -> new RuntimeException("Assento não encontrado"));

        assento.setStatus(StatusAssento.LIVRE);
        assentoRepository.save(assento);
    }
} 