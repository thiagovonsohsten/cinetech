package com.cinetech.api.dominio.modelos.sala;

import java.util.Iterator;
import java.util.List;

public class AssentoIterator implements Iterator<Assento> {
    private final List<Assento> assentos;
    private int posicaoAtual;

    public AssentoIterator(List<Assento> assentos) {
        this.assentos = assentos;
        this.posicaoAtual = 0;
    }

    @Override
    public boolean hasNext() {
        return posicaoAtual < assentos.size();
    }

    @Override
    public Assento next() {
        if (!hasNext()) {
            throw new IllegalStateException("Não há mais assentos para iterar");
        }
        return assentos.get(posicaoAtual++);
    }
} 