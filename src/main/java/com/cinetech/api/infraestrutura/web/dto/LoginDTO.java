package com.cinetech.api.infraestrutura.web.dto;

public class LoginDTO {
    private String email;
    private String senha;

    // Construtor vazio necessário para deserialização JSON
    public LoginDTO() {}

    public LoginDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
} 