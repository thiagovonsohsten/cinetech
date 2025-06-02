package com.cinetech.api.infraestrutura.web.controller;

import com.cinetech.api.aplicacao.AvaliacaoAplicacao;
import com.cinetech.api.dominio.modelos.avaliacao.Avaliacao;
import com.cinetech.api.dominio.modelos.filme.FilmeId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/filmes/{filmeId}/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoAplicacao avaliacaoAplicacao;

    public AvaliacaoController(AvaliacaoAplicacao avaliacaoAplicacao) {
        this.avaliacaoAplicacao = avaliacaoAplicacao;
    }

    @GetMapping
    public ResponseEntity<List<Avaliacao>> listarAvaliacoes(@PathVariable UUID filmeId) {
        return ResponseEntity.ok(avaliacaoAplicacao.listarAvaliacoesAprovadasPorFilme(FilmeId.de(filmeId)));
    }
} 