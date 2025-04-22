package com.cinetech.stepdefs;

import com.cinetech.domain.model.Assento;
import com.cinetech.domain.model.Reserva;
import com.cinetech.domain.model.Sala;
import com.cinetech.domain.model.Sessao;
import com.cinetech.domain.model.StatusAssento;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;

import io.cucumber.java.pt.*;

public class SelecionarAssentoSteps {

    private Sessao sessao;
    private Assento assentoSelecionado;
    private String mensagem;

    private Map<String, Assento> mapaAssentos = new HashMap<>();
    //Cenário 1 

    @Dado("que o cliente acessou a página de compra de ingressos")
    public void cliente_acessou_a_pagina() {
        Sala sala = new Sala();
        sala.setNome("Sala 1");

        sessao = new Sessao();
        sessao.setSala(sala);

        // Criando assentos
        Assento assento = new Assento();
        assento.setNumero("A1");
        assento.setStatus(StatusAssento.LIVRE);
        mapaAssentos.put("A1", assento);
    }

    @Quando("ele seleciona uma sessão e escolhe um assento específico")
    public void cliente_seleciona_assento() {
        assentoSelecionado = mapaAssentos.get("A1");

        if (assentoSelecionado.getStatus() == StatusAssento.LIVRE) {
            assentoSelecionado.setStatus(StatusAssento.RESERVADO);
            mensagem = "Assento reservado com sucesso";
        } else {
            mensagem = "Assento indisponível";
        }
    }

    @Então("esse assento deve ser reservado para ele")
    public void verificar_assento_reservado() {
        assertEquals("Assento reservado com sucesso", mensagem);
        assertEquals(StatusAssento.RESERVADO, assentoSelecionado.getStatus());
    }

    //Cenário 2 
    @Dado("que o assento {string} de uma sessão específica em uma sala já especifica já foi reservado")
    public void assento_ja_reservado(String numeroAssento) {
    Sala sala = new Sala();
    sala.setNome("Sala 2");

    sessao = new Sessao();
    sessao.setSala(sala);

    Assento assento = new Assento();
    assento.setNumero(numeroAssento);
    assento.setStatus(StatusAssento.RESERVADO);
    mapaAssentos.put(numeroAssento, assento);
}

    @Quando("um cliente tenta comprar ingresso para essa sessão")
    public void cliente_tenta_comprar_assento_ocupado() {
        assentoSelecionado = mapaAssentos.get("C5");

        if (assentoSelecionado.getStatus() == StatusAssento.RESERVADO) {
            mensagem = "Esse assento já está ocupado nessa sala para essa sessão, escolha outro assento";
        } else {
            mensagem = "Assento reservado com sucesso";
        }
    }
    @Então("o sistema deve exibir {string}")
    public void o_sistema_deve_exibir(String mensagemEsperada) {
        assertEquals(mensagemEsperada, mensagem);
    }
}
