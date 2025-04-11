package com.cinetech.controller;

import com.cinetech.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessoes")
public class SessaoController {
    @Autowired
    private SessaoService sessaoService;

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarSessao(@PathVariable Long id) {
        sessaoService.cancelarSessao(id);
        return ResponseEntity.ok().build();
    }
} 