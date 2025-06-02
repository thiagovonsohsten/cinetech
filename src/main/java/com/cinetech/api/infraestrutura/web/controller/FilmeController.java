package com.cinetech.api.infraestrutura.web.controller;

import com.cinetech.api.aplicacao.FilmeAplicacao;
import com.cinetech.api.dominio.modelos.filme.Filme;
import com.cinetech.api.dominio.modelos.filme.FilmeId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/filmes")
public class FilmeController {

    private final FilmeAplicacao filmeAplicacao;

    public FilmeController(FilmeAplicacao filmeAplicacao) {
        this.filmeAplicacao = filmeAplicacao;
    }

    @GetMapping
    public ResponseEntity<List<Filme>> listarFilmes() {
        return ResponseEntity.ok(filmeAplicacao.listarTodosOsFilmes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarFilme(@PathVariable UUID id) {
        return filmeAplicacao.buscarFilmePorId(FilmeId.de(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 