package com.cinetech.stepdefs;

import com.cinetech.model.*;
import com.cinetech.model.enums.*;
import com.cinetech.repository.*;
import com.cinetech.service.SessaoService;
import io.cucumber.java.pt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SessaoLotadaSteps {

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private SessaoService sessaoService;

    private Sessao sessao;

    @Dado("uma sessão com todos os assentos ocupados")
    public void umaSessaoComTodosOsAssentosOcupados() {
        // Criar e salvar sala
        Sala sala = new Sala();
        sala.setNome("Sala 1");
        sala.setCapacidade(100);

        Assento assento1 = new Assento();
        assento1.setNumero("A1");
        assento1.setTipo(TipoAssento.COMUM);
        assento1.setStatus(StatusAssento.RESERVADO);
        assento1.setSala(sala);

        sala.setAssentos(List.of(assento1));
        salaRepository.save(sala);

        // Criar e salvar filme
        Filme filme = new Filme();
        filme.setTitulo("Filme Teste");
        filme.setDescricao("Descrição do filme");
        filme.setDuracaoMinutos(120);
        filme.setDataInicioExibicao(LocalDate.now());
        filme.setDataFimExibicao(LocalDate.now().plusDays(7));
        filmeRepository.save(filme);

        // Criar e salvar sessão
        sessao = new Sessao();
        sessao.setSala(sala);
        sessao.setFilme(filme);
        sessao.setDataHoraInicio(LocalDateTime.now());
        sessao.setDataHoraFim(LocalDateTime.now().plusHours(2));
        sessao.setStatus(StatusSessao.DISPONIVEL);
        sessaoRepository.save(sessao);
    }

    @Quando("o sistema verifica a lotação da sessão")
    public void oSistemaVerificaALotacaoDaSessao() {
        sessaoService.verificarLotacaoSessao(sessao.getId());
        sessao = sessaoRepository.findById(sessao.getId()).orElseThrow();
    }

    @Então("a sessão deve ser marcada como LOTADA")
    public void aSessaoDeveSerMarcadaComoLOTADA() {
        assertEquals(StatusSessao.LOTADA, sessao.getStatus());
    }

    @Dado("uma sessão com ao menos um assento livre")
    public void umaSessaoComAoMenosUmAssentoLivre() {
        // Criar e salvar sala
        Sala sala = new Sala();
        sala.setNome("Sala 2");
        sala.setCapacidade(100);

        Assento assento1 = new Assento();
        assento1.setNumero("B1");
        assento1.setTipo(TipoAssento.COMUM);
        assento1.setStatus(StatusAssento.LIVRE);
        assento1.setSala(sala);

        sala.setAssentos(List.of(assento1));
        salaRepository.save(sala);

        // Criar e salvar filme
        Filme filme = new Filme();
        filme.setTitulo("Filme Teste 2");
        filme.setDescricao("Descrição do filme 2");
        filme.setDuracaoMinutos(110);
        filme.setDataInicioExibicao(LocalDate.now());
        filme.setDataFimExibicao(LocalDate.now().plusDays(10));
        filmeRepository.save(filme);

        // Criar e salvar sessão
        sessao = new Sessao();
        sessao.setSala(sala);
        sessao.setFilme(filme);
        sessao.setDataHoraInicio(LocalDateTime.now());
        sessao.setDataHoraFim(LocalDateTime.now().plusHours(2));
        sessao.setStatus(StatusSessao.DISPONIVEL);
        sessaoRepository.save(sessao);
    }

    @Então("a sessão deve permanecer com status DISPONIVEL")
    public void aSessaoDevePermanecerComStatusDISPONIVEL() {
        assertEquals(StatusSessao.DISPONIVEL, sessao.getStatus());
    }
}

