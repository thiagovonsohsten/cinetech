package com.cinetech.api.infraestrutura.web.controller;

import com.cinetech.api.aplicacao.SessaoAplicacao;
import com.cinetech.api.dominio.modelos.sessao.Sessao;
import com.cinetech.api.dominio.modelos.filme.FilmeId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/filmes/{filmeId}/sessoes")
public class SessaoController {

    private final SessaoAplicacao sessaoAplicacao;

    public SessaoController(SessaoAplicacao sessaoAplicacao) {
        this.sessaoAplicacao = sessaoAplicacao;
    }

    @GetMapping
    public ResponseEntity<List<Sessao>> listarSessoes(@PathVariable UUID filmeId) {
        return ResponseEntity.ok(sessaoAplicacao.listarSessoesPorFilme(FilmeId.de(filmeId)));
    }
} 