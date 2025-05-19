package com.cinetech.aplicacao.sessao;

import com.cinetech.dominio.comum.Assento;
import com.cinetech.dominio.comum.StatusAssento;
import com.cinetech.dominio.sessao.Sessao;
import com.cinetech.dominio.sessao.StatusSessao;
import com.cinetech.dominio.sessao.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SessaoService {
    @Autowired
    private SessaoRepository sessaoRepository;

    @Transactional
    public Sessao criarSessao(Sessao sessao) {
        sessao.setStatus(StatusSessao.ABERTA);
        return sessaoRepository.save(sessao);
    }

    public Sessao buscarSessao(Long id) {
        return sessaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));
    }

    @Transactional
    public void atualizarStatusSessao(Long sessaoId, StatusSessao novoStatus) {
        Sessao sessao = buscarSessao(sessaoId);
        sessao.setStatus(novoStatus);
        sessaoRepository.save(sessao);
    }

    @Transactional
    public void verificarLotacaoSessao(Long sessaoId) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
            .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));

        long assentosLivres = sessao.getAssentos().stream()
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