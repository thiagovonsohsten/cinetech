package com.cinetech.aplicacao.assento;

import com.cinetech.dominio.comum.Assento;
import com.cinetech.dominio.comum.StatusAssento;
import com.cinetech.dominio.comum.repository.AssentoRepository;
import com.cinetech.dominio.comum.repository.SalaRepository;
import com.cinetech.dominio.comum.Sala;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AssentoService {
    @Autowired
    private AssentoRepository assentoRepository;

    @Autowired
    private SalaRepository salaRepository;

    public List<Assento> buscarAssentosDisponiveis(Long salaId) {
        return assentoRepository.findBySalaIdAndStatus(salaId, StatusAssento.DISPONIVEL);
    }

    @Transactional
    public Assento selecionarAssento(Long assentoId) {
        Assento assento = assentoRepository.findById(assentoId)
                .orElseThrow(() -> new RuntimeException("Assento não encontrado"));

        if (assento.getStatus() != StatusAssento.DISPONIVEL) {
            throw new RuntimeException("Assento não está disponível");
        }

        assento.setStatus(StatusAssento.RESERVADO);
        return assentoRepository.save(assento);
    }

    @Transactional
    public void confirmarAssento(Long assentoId) {
        Assento assento = assentoRepository.findById(assentoId)
                .orElseThrow(() -> new RuntimeException("Assento não encontrado"));

        if (assento.getStatus() != StatusAssento.RESERVADO) {
            throw new RuntimeException("Assento não está reservado");
        }

        assento.setStatus(StatusAssento.OCUPADO);
        assentoRepository.save(assento);
    }

    @Transactional
    public void liberarAssento(Long assentoId) {
        Assento assento = assentoRepository.findById(assentoId)
                .orElseThrow(() -> new RuntimeException("Assento não encontrado"));

        assento.setStatus(StatusAssento.DISPONIVEL);
        assentoRepository.save(assento);
    }
} 