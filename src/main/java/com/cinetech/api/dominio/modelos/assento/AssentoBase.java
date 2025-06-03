package com.cinetech.api.dominio.modelos.assento;

import com.cinetech.api.dominio.enums.StatusAssento;
import com.cinetech.api.dominio.enums.TipoAssento;
import com.cinetech.api.dominio.modelos.cliente.ClienteId;
import com.cinetech.api.dominio.modelos.sessao.Sessao;

import java.time.LocalDateTime;
import java.util.Objects;

public class AssentoBase extends Assento {
    public AssentoBase(Sessao sessao, String identificadorPosicao, TipoAssento tipo) {
        super(sessao, identificadorPosicao, tipo);
    }

    public AssentoBase(AssentoId id, Sessao sessao, String identificadorPosicao, TipoAssento tipo, 
                      StatusAssento status, ClienteId clienteIdReservaTemporaria, 
                      LocalDateTime timestampExpiracaoReserva) {
        super(id, sessao, identificadorPosicao, tipo, status, clienteIdReservaTemporaria, timestampExpiracaoReserva);
    }

    @Override
    public double getPreco() {
        return getPrecoBase();
    }
} 