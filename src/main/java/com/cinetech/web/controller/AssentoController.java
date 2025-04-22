package com.cinetech.web.controller;


import com.cinetech.domain.model.Assento;
import com.cinetech.application.usecase.AssentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assentos")
public class AssentoController {
    @Autowired
    private AssentoService assentoService;

    @PostMapping("/{id}/reservar")
    public ResponseEntity<Assento> reservarAssento(@PathVariable Long id) {
        Assento assento = assentoService.reservarAssento(id);
        return ResponseEntity.ok(assento);
    }

    @PostMapping("/{id}/liberar")
    public ResponseEntity<Void> liberarAssento(@PathVariable Long id) {
        assentoService.liberarAssento(id);
        return ResponseEntity.ok().build();
    }
} 