package com.cinetech.api.dominio.enums;

public enum PerfilCliente {
    REGULAR("Regular"),
    ESTUDANTE("Estudante"),
    IDOSO("Idoso (60+)"),
    PCD("Pessoa com Deficiência");
    private final String descricao;

    PerfilCliente(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
