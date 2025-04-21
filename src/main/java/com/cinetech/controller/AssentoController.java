package com.cinetech.controller;

import com.cinetech.model.Assento;
import com.cinetech.service.AssentoService;
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