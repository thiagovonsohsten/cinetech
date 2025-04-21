package com.cinetech.service;

import com.cinetech.model.Sessao;
import com.cinetech.model.enums.StatusSessao;
import com.cinetech.model.enums.StatusAssento;
import com.cinetech.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SessaoService {
    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private AssentoService assentoService;

    @Transactional
    public void verificarLotacaoSessao(Long sessaoId) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
            .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));

        long assentosLivres = sessao.getSala().getAssentos().stream()
            .filter(assento -> assento.getStatus() == StatusAssento.LIVRE)
            .count();

        if (assentosLivres == 0) {
            sessao.setStatus(StatusSessao.LOTADA);
            sessaoRepository.save(sessao);
        }
    }

    @Transactional
    public void cancelarSessao(Long sessaoId) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
            .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));

        sessao.setStatus(StatusSessao.CANCELADA);
        sessaoRepository.save(sessao);
    }
} 