package com.cinetech.api.dominio.modelos.sala;

import com.cinetech.api.dominio.enums.TipoAssento;
import java.util.Objects;

public class Assento {
    private final String identificador;
    private final TipoAssento tipo;
    private boolean ocupado;

    public Assento(String identificador, TipoAssento tipo) {
        this.identificador = Objects.requireNonNull(identificador, "Identificador do assento não pode ser nulo");
        this.tipo = Objects.requireNonNull(tipo, "Tipo do assento não pode ser nulo");
        this.ocupado = false;
    }

    public String getIdentificador() {
        return identificador;
    }

    public TipoAssento getTipo() {
        return tipo;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void ocupar() {
        if (ocupado) {
            throw new IllegalStateException("Assento " + identificador + " já está ocupado");
        }
        this.ocupado = true;
    }

    public void liberar() {
        this.ocupado = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assento assento = (Assento) o;
        return identificador.equals(assento.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }
} 