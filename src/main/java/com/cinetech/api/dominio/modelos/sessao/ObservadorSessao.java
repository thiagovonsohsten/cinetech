package com.cinetech.api.dominio.modelos.sessao;

public interface ObservadorSessao {
    void notificarMudancaStatus(Sessao sessao, StatusSessao statusAnterior, StatusSessao statusNovo);
    void notificarLotacao(Sessao sessao);
    void notificarCancelamento(Sessao sessao);
} 