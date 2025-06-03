package com.cinetech.api.dominio.modelos.assento;

import com.cinetech.api.dominio.enums.TipoAssento;

public class AssentoVIPDecorator extends AssentoDecorator {
    private static final double TAXA_VIP = 1.5; // 50% mais caro

    public AssentoVIPDecorator(Assento assentoDecorado) {
        super(assentoDecorado);
    }

    @Override
    public double getPreco() {
        return assentoDecorado.getPreco() * TAXA_VIP;
    }

    @Override
    public TipoAssento getTipo() {
        return TipoAssento.VIP;
    }
} 