package com.cinetech.aplicacao.filme;

import com.cinetech.dominio.filme.Filme;
import com.cinetech.dominio.filme.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GerenciarFilmeUseCase {
    @Autowired
    private FilmeRepository filmeRepository;

    @Transactional
    public Filme cadastrarFilme(Filme filme) {
        return filmeRepository.save(filme);
    }

    @Transactional
    public Filme atualizarFilme(Filme filme) {
        return filmeRepository.save(filme);
    }

    @Transactional
    public void removerFilme(Long id) {
        filmeRepository.deleteById(id);
    }

    public Optional<Filme> buscarFilmePorId(Long id) {
        return filmeRepository.findById(id);
    }

    public List<Filme> listarTodosFilmes() {
        return filmeRepository.findAll();
    }

    public List<Filme> buscarFilmesPorTitulo(String titulo) {
        return filmeRepository.findAll().stream()
            .filter(filme -> filme.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
            .toList();
    }

    public List<Filme> buscarFilmesPorGenero(String genero) {
        return filmeRepository.findAll().stream()
            .filter(filme -> filme.getGenero().toLowerCase().contains(genero.toLowerCase()))
            .toList();
    }
} 