package com.cinetech.aplicacao.filme;

import com.cinetech.dominio.filme.Filme;
import com.cinetech.dominio.filme.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
public class FilmeService {
    @Autowired
    private FilmeRepository filmeRepository;

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

    @Transactional
    public Filme cadastrarFilme(Filme filme) {
        return filmeRepository.save(filme);
    }

    public Filme buscarFilme(Long id) {
        return filmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));
    }
} 