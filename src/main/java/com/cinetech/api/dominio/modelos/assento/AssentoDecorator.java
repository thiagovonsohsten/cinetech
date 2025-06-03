package com.cinetech.api.dominio.modelos.assento;

import com.cinetech.api.dominio.enums.TipoAssento;
import com.cinetech.api.dominio.modelos.sessao.Sessao;

public abstract class AssentoDecorator extends Assento {
    protected Assento assentoDecorado;

    public AssentoDecorator(Assento assentoDecorado) {
        super(assentoDecorado.getSessao(), assentoDecorado.getIdentificadorPosicao(), assentoDecorado.getTipo());
        this.assentoDecorado = assentoDecorado;
    }

    @Override
    public double getPreco() {
        return assentoDecorado.getPreco();
    }

    @Override
    public TipoAssento getTipo() {
        return assentoDecorado.getTipo();
    }
} 