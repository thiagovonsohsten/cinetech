package com.cinetech.dominio.filme;

import java.util.List;
import java.util.Optional;

public interface FilmeRepository {
    Filme salvar(Filme filme);
    Optional<Filme> buscarPorId(Long id);
    List<Filme> listarTodos();
    void remover(Long id);
    List<Filme> buscarPorTitulo(String titulo);
    List<Filme> buscarPorGenero(String genero);
} 