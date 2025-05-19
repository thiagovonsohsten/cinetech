package com.cinetech.aplicacao.filme;

import com.cinetech.dominio.filme.Filme;
import com.cinetech.dominio.filme.repository.FilmeRepository;
import com.cinetech.dominio.comum.Sessao;
import com.cinetech.dominio.comum.StatusSessao;
import com.cinetech.dominio.sessao.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmeService {
    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Scheduled(cron = "0 0 0 * * *") // Executa todos os dias à meia-noite
    @Transactional
    public void removerFilmesVencidos() {
        LocalDate hoje = LocalDate.now();
        filmeRepository.findByDataFimExibicaoAfter(hoje)
            .forEach(filme -> {
                // Aqui você pode implementar a lógica para ocultar o filme
                // Por exemplo, adicionar um campo 'ativo' na entidade Filme
            });
    }

    @Scheduled(cron = "0 0 0 * * ?") // Executa todo dia à meia-noite
    @Transactional
    public void removerFilmesSemSessoes() {
        LocalDateTime agora = LocalDateTime.now();
        
        // Busca todas as sessões futuras
        List<Sessao> sessoesFuturas = sessaoRepository.findByDataHoraBetween(
            agora, 
            agora.plusYears(1)
        );

        // Obtém os IDs dos filmes que ainda têm sessões
        Set<Long> filmesComSessoes = sessoesFuturas.stream()
            .map(Sessao::getFilmeId)
            .collect(Collectors.toSet());

        // TODO: Implementar lógica para remover filmes que não estão em filmesComSessoes
        // Isso dependerá de como os filmes são armazenados no sistema
    }

    @Transactional
    public Filme cadastrarFilme(Filme filme) {
        return filmeRepository.save(filme);
    }

    public Filme buscarFilme(Long id) {
        return filmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));
    }
} 