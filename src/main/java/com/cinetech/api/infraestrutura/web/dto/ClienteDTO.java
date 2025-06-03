package com.cinetech.api.infraestrutura.web.dto;

import com.cinetech.api.dominio.enums.PerfilCliente;
import com.cinetech.api.dominio.modelos.cliente.Cliente;
import com.cinetech.api.dominio.modelos.cliente.ClienteId;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class ClienteDTO {
    private String id;
    private String nome;
    private String email;
    private String cpf;
    private PerfilCliente perfil;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    private String senha;

    // Construtor vazio necessário para deserialização JSON
    public ClienteDTO() {}

    public ClienteDTO(String id, String nome, String email, String cpf, PerfilCliente perfil, LocalDate dataNascimento, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.perfil = perfil;
        this.dataNascimento = dataNascimento;
        this.senha = senha;
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public PerfilCliente getPerfil() { return perfil; }
    public void setPerfil(PerfilCliente perfil) { this.perfil = perfil; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    // Método para converter de Cliente para ClienteDTO
    public static ClienteDTO fromDomain(Cliente cliente) {
        if (cliente == null) return null;
        return new ClienteDTO(
            cliente.getId().getValor().toString(),
            cliente.getNome(),
            cliente.getEmail(),
            cliente.getCpf(),
            cliente.getPerfil(),
            cliente.getDataNascimento(),
            null // Não retornamos a senha por segurança
        );
    }

    // Método para converter de ClienteDTO para Cliente
    public Cliente toDomain() {
        return new Cliente(
            nome,
            email,
            cpf,
            perfil,
            dataNascimento,
            senha
        );
    }
} 