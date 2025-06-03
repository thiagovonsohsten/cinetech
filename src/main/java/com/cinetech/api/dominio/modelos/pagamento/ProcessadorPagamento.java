package com.cinetech.api.dominio.modelos.pagamento;

import com.cinetech.api.dominio.modelos.sessao.StatusSessao;
import com.cinetech.api.dominio.modelos.sessao.ObservadorSessao;
import com.cinetech.api.dominio.modelos.sessao.Sessao;

public class ProcessadorPagamento implements ObservadorSessao {
    @Override
    public void notificarMudancaStatus(Sessao sessao, StatusSessao statusAnterior, StatusSessao statusNovo) {
        if (statusNovo == StatusSessao.CANCELADA) {
            processarReembolso(sessao);
        }
    }

    @Override
    public void notificarLotacao(Sessao sessao) {
        System.out.println("Sessão " + sessao.getId() + " está lotada. Não é possível realizar mais vendas.");
    }

    @Override
    public void notificarCancelamento(Sessao sessao) {
        processarReembolso(sessao);
    }

    private void processarReembolso(Sessao sessao) {
        // Lógica para processar reembolso dos ingressos vendidos
        System.out.println("Processando reembolso para a sessão " + sessao.getId());
    }
} 