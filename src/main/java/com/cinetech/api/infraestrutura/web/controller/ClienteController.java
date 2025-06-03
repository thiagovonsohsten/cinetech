package com.cinetech.api.infraestrutura.web.controller;

import com.cinetech.api.aplicacao.ClienteAplicacao;
import com.cinetech.api.dominio.enums.PerfilCliente;
import com.cinetech.api.dominio.modelos.cliente.Cliente;
import com.cinetech.api.dominio.modelos.cliente.ClienteId;
import com.cinetech.api.infraestrutura.web.dto.ClienteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private final ClienteAplicacao clienteAplicacao;

    public ClienteController(ClienteAplicacao clienteAplicacao) {
        this.clienteAplicacao = clienteAplicacao;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            logger.info("Recebendo requisição para cadastrar cliente: {}", clienteDTO);
            
            if (clienteDTO == null) {
                return ResponseEntity.badRequest().body("Dados do cliente não podem ser nulos");
            }

            if (clienteDTO.getNome() == null || clienteDTO.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Nome do cliente é obrigatório");
            }

            if (clienteDTO.getEmail() == null || clienteDTO.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email do cliente é obrigatório");
            }

            if (clienteDTO.getCpf() == null || clienteDTO.getCpf().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("CPF do cliente é obrigatório");
            }

            if (clienteDTO.getPerfil() == null) {
                return ResponseEntity.badRequest().body("Perfil do cliente é obrigatório");
            }

            Cliente cliente = clienteAplicacao.cadastrarNovoCliente(
                clienteDTO.getNome(),
                clienteDTO.getEmail(),
                clienteDTO.getCpf(),
                clienteDTO.getPerfil()
            );
            
            logger.info("Cliente cadastrado com sucesso: {}", cliente.getId());
            return ResponseEntity.ok(ClienteDTO.fromDomain(cliente));
            
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao cadastrar cliente: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Erro inesperado ao cadastrar cliente", e);
            return ResponseEntity.internalServerError().body("Erro interno ao cadastrar cliente: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable String id) {
        try {
            Optional<Cliente> cliente = clienteAplicacao.buscarClientePorId(ClienteId.de(id));
            return cliente.map(c -> ResponseEntity.ok(ClienteDTO.fromDomain(c)))
                         .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Erro ao buscar cliente por ID: {}", id, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClienteDTO> buscarClientePorCpf(@PathVariable String cpf) {
        try {
            Optional<Cliente> cliente = clienteAplicacao.buscarClientePorCpf(cpf);
            return cliente.map(c -> ResponseEntity.ok(ClienteDTO.fromDomain(c)))
                         .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Erro ao buscar cliente por CPF: {}", cpf, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodosClientes() {
        try {
            List<Cliente> clientes = clienteAplicacao.buscarTodos();
            List<ClienteDTO> clientesDTO = clientes.stream()
                .map(ClienteDTO::fromDomain)
                .collect(Collectors.toList());
            return ResponseEntity.ok(clientesDTO);
        } catch (Exception e) {
            logger.error("Erro ao listar clientes", e);
            return ResponseEntity.internalServerError().build();
        }
    }
} 