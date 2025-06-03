package com.cinetech.api.infraestrutura.web.dto;

import com.cinetech.api.dominio.enums.PerfilCliente;

public class LoginResponseDTO {
    private String id;
    private String nome;
    private String email;
    private PerfilCliente perfil;

    public LoginResponseDTO() {}

    public LoginResponseDTO(String id, String nome, String email, PerfilCliente perfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PerfilCliente getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilCliente perfil) {
        this.perfil = perfil;
    }
} 