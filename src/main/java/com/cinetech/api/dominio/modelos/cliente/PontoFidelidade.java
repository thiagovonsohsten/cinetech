package com.cinetech.api.dominio.modelos.cliente;

import java.time.LocalDateTime;
import java.util.Objects;

public class PontoFidelidade {
    private final int quantidade;
    private final LocalDateTime dataObtido;
    private final String motivo;

    public PontoFidelidade(int quantidade, String motivo) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade de pontos deve ser positiva");
        }
        this.quantidade = quantidade;
        this.dataObtido = LocalDateTime.now();
        this.motivo = Objects.requireNonNull(motivo, "Motivo não pode ser nulo");
    }

    public int getQuantidade() {
        return quantidade;
    }

    public LocalDateTime getDataObtido() {
        return dataObtido;
    }

    public String getMotivo() {
        return motivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PontoFidelidade that = (PontoFidelidade) o;
        return quantidade == that.quantidade &&
                Objects.equals(dataObtido, that.dataObtido) &&
                Objects.equals(motivo, that.motivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantidade, dataObtido, motivo);
    }
} 