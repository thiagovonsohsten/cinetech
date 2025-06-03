package com.cinetech.api.infraestrutura.web.controller;

import com.cinetech.api.aplicacao.ClienteAplicacao;
import com.cinetech.api.dominio.modelos.cliente.Cliente;
import com.cinetech.api.infraestrutura.web.dto.LoginDTO;
import com.cinetech.api.infraestrutura.web.dto.LoginResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final ClienteAplicacao clienteAplicacao;
    private final PasswordEncoder passwordEncoder;

    public AuthController(ClienteAplicacao clienteAplicacao, PasswordEncoder passwordEncoder) {
        this.clienteAplicacao = clienteAplicacao;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            logger.info("Dados recebidos no login: email={}, senha={}", loginDTO.getEmail(), loginDTO.getSenha() != null ? "***" : "null");

            if (loginDTO == null) {
                logger.error("LoginDTO é nulo");
                return ResponseEntity.badRequest().body("Dados de login inválidos");
            }

            if (loginDTO.getEmail() == null || loginDTO.getEmail().trim().isEmpty()) {
                logger.error("Email é nulo ou vazio");
                return ResponseEntity.badRequest().body("Email é obrigatório");
            }

            if (loginDTO.getSenha() == null || loginDTO.getSenha().trim().isEmpty()) {
                logger.error("Senha é nula ou vazia");
                return ResponseEntity.badRequest().body("Senha é obrigatória");
            }

            Optional<Cliente> clienteOpt = clienteAplicacao.buscarClientePorEmail(loginDTO.getEmail());

            if (clienteOpt.isEmpty()) {
                logger.warn("Tentativa de login com email não cadastrado: {}", loginDTO.getEmail());
                return ResponseEntity.badRequest().body("Email ou senha inválidos");
            }

            Cliente cliente = clienteOpt.get();
            logger.info("Cliente encontrado: id={}, nome={}", cliente.getId(), cliente.getNome());

            if (!passwordEncoder.matches(loginDTO.getSenha(), cliente.getSenha())) {
                logger.warn("Tentativa de login com senha incorreta para o email: {}", loginDTO.getEmail());
                return ResponseEntity.badRequest().body("Email ou senha inválidos");
            }

            logger.info("Login realizado com sucesso para o cliente: {}", cliente.getId());
            return ResponseEntity.ok(new LoginResponseDTO(
                cliente.getId().getValor().toString(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getPerfil()
            ));

        } catch (Exception e) {
            logger.error("Erro ao realizar login", e);
            return ResponseEntity.internalServerError().body("Erro interno ao realizar login");
        }
    }
} 