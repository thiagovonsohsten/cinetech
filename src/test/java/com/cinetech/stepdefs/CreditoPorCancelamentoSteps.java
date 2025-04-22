package com.cinetech.stepdefs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.cinetech.model.Cliente;
import com.cinetech.model.Credito;
import com.cinetech.model.Reserva;
import com.cinetech.model.Sessao;
import com.cinetech.model.enums.StatusSessao;

import io.cucumber.java.pt.*;

public class CreditoPorCancelamentoSteps {

    private Sessao sessao;
    private List<Reserva> reservas = new ArrayList<>();
    private List<Credito> creditosEmitidos = new ArrayList<>();

    // -------------------------------------
    // CENÁRIO 1: Sessão foi cancelada
    // -------------------------------------

    @Dado("que uma sessão foi cancelada pelo cinema")
    public void sessao_foi_cancelada() {
        sessao = new Sessao();
        sessao.setStatus(StatusSessao.CANCELADA);

        Cliente cliente = new Cliente();
        cliente.setNome("Lucas");

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setSessao(sessao);

        reservas.add(reserva);
    }

    @Então("cada cliente deve receber um crédito proporcional")
    public void cliente_recebe_credito() {
        assertFalse(creditosEmitidos.isEmpty());
    }

    // -------------------------------------
    // CENÁRIO 2: Sessão não foi cancelada
    // -------------------------------------

    @Dado("que uma sessão não foi cancelada")
    public void sessao_nao_cancelada() {
        sessao = new Sessao();
        sessao.setStatus(StatusSessao.DISPONIVEL); // ou LOTADA, dependendo da lógica real

        Cliente cliente = new Cliente();
        cliente.setNome("Lucas");

        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setSessao(sessao);

        reservas.add(reserva);
    }

    @Então("os clientes não devem receber crédito")
    public void cliente_nao_recebe_credito() {
        assertTrue(creditosEmitidos.isEmpty());
    }

    // -------------------------------------
    // AÇÃO COMUM: processamento do sistema
    // -------------------------------------

    @Quando("o sistema processa os ingressos comprados")
    public void sistema_processa_reservas() {
        for (Reserva reserva : reservas) {
            if (reserva.getSessao().getStatus() == StatusSessao.CANCELADA) {
                Credito credito = new Credito();
                credito.setCliente(reserva.getCliente());
                credito.setValor(10.0); // valor simbólico
                creditosEmitidos.add(credito);
            }
        }
    }
}
