package com.cinetech.apresentacao.backend.filme;

import com.cinetech.aplicacao.filme.GerenciarFilmeUseCase;
import com.cinetech.dominio.filme.Filme;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filmes")
@RequiredArgsConstructor
public class FilmeController {

    private final GerenciarFilmeUseCase gerenciarFilmeUseCase;

    @PostMapping
    public ResponseEntity<Filme> cadastrarFilme(@RequestBody Filme filme) {
        return ResponseEntity.ok(gerenciarFilmeUseCase.cadastrarFilme(filme));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizarFilme(@PathVariable Long id, @RequestBody Filme filme) {
        filme.setId(id);
        return ResponseEntity.ok(gerenciarFilmeUseCase.atualizarFilme(filme));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerFilme(@PathVariable Long id) {
        gerenciarFilmeUseCase.removerFilme(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarFilmePorId(@PathVariable Long id) {
        return gerenciarFilmeUseCase.buscarFilmePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Filme>> listarTodosFilmes() {
        return ResponseEntity.ok(gerenciarFilmeUseCase.listarTodosFilmes());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Filme>> buscarFilmesPorTitulo(@RequestParam(required = false) String titulo,
                                                           @RequestParam(required = false) String genero) {
        if (titulo != null) {
            return ResponseEntity.ok(gerenciarFilmeUseCase.buscarFilmesPorTitulo(titulo));
        } else if (genero != null) {
            return ResponseEntity.ok(gerenciarFilmeUseCase.buscarFilmesPorGenero(genero));
        }
        return ResponseEntity.badRequest().build();
    }
} 