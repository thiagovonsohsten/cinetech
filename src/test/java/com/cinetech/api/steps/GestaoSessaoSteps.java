package com.cinetech.api.steps;

import com.cinetech.api.dominio.enums.StatusSessao;
import com.cinetech.api.dominio.enums.TipoExibicao;
import com.cinetech.api.dominio.enums.TipoSala;
import com.cinetech.api.dominio.enums.StatusAssento;
import com.cinetech.api.dominio.modelos.filme.Filme;
import com.cinetech.api.dominio.modelos.filme.FilmeId;
import com.cinetech.api.dominio.modelos.sala.Sala;
import com.cinetech.api.dominio.modelos.sala.SalaId;
import com.cinetech.api.dominio.modelos.sessao.Sessao;
import com.cinetech.api.dominio.modelos.sessao.SessaoId;
import com.cinetech.api.dominio.repositorios.FilmeRepositorio;
import com.cinetech.api.dominio.repositorios.SalaRepositorio;
import com.cinetech.api.dominio.repositorios.SessaoRepositorio;
import com.cinetech.api.aplicacao.SessaoAplicacao;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GestaoSessaoSteps {

    @Autowired
    private SessaoAplicacao sessaoAplicacao;

    @Autowired
    private SessaoRepositorio sessaoRepositorio;

    @Autowired
    private FilmeRepositorio filmeRepositorio;

    @Autowired
    private SalaRepositorio salaRepositorio;

    private Sessao sessao;
    private SessaoId sessaoId;
    private Filme filme;
    private Sala sala;
    private Exception excecao;

    @Dado("que existe um filme cadastrado")
    public void existeFilmeCadastrado() {
        filme = new Filme("Filme Teste", "Ação", 120, "Português", "12 anos", 
            LocalDate.now(), LocalDate.now().plusMonths(1), "Sinopse teste");
        filmeRepositorio.salvar(filme);
    }

    @Dado("que existe uma sala de cinema")
    public void existeSalaCinema() {
        sala = new Sala("Sala 1", 100, TipoSala.SALA_2D, true);
        salaRepositorio.salvar(sala);
    }

    @Dado("que tenho os dados da nova sessão")
    public void tenhoDadosNovaSessao() {
        LocalDateTime dataHoraInicio = LocalDateTime.now().plusDays(1);
        sessao = new Sessao(filme, sala, dataHoraInicio, TipoExibicao.D2, new BigDecimal("30.00"));
    }

    @Dado("que existe uma sessão cadastrada")
    public void existeSessaoCadastrada() {
        LocalDateTime dataHoraInicio = LocalDateTime.now().plusDays(1);
        sessao = new Sessao(filme, sala, dataHoraInicio, TipoExibicao.D2, new BigDecimal("30.00"));
        sessaoRepositorio.salvar(sessao);
        sessaoId = sessao.getId();
    }

    @Dado("a sessão possui ingressos vendidos")
    public void sessaoPossuiIngressosVendidos() {
        // Implementar lógica para criar ingressos vendidos
    }

    @Quando("eu cadastro a sessão no sistema")
    public void cadastroSessaoSistema() {
        try {
            sessao = sessaoAplicacao.agendarNovaSessao(
                filme.getId(),
                sala.getId(),
                sessao.getDataHoraInicio(),
                sessao.getTipoExibicao(),
                sessao.getPrecoIngressoBase()
            );
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("eu consulto a disponibilidade de assentos")
    public void consultoDisponibilidadeAssentos() {
        // Implementar lógica de consulta
    }

    @Quando("eu cancelo a sessão")
    public void canceloSessao() {
        try {
            sessao = sessaoRepositorio.buscarPorId(sessaoId).orElseThrow();
            sessao = new Sessao(sessao.getId(), sessao.getFilme(), sessao.getSala(), 
                sessao.getDataHoraInicio(), sessao.getTipoExibicao(), 
                sessao.getPrecoIngressoBase(), StatusSessao.CANCELADA, sessao.getAssentos());
            sessaoRepositorio.salvar(sessao);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("eu tento cadastrar uma nova sessão")
    public void tentoCadastrarNovaSessao() {
        try {
            LocalDateTime dataHoraInicio = sessao.getDataHoraInicio();
            sessao = sessaoAplicacao.agendarNovaSessao(
                filme.getId(),
                sala.getId(),
                dataHoraInicio,
                TipoExibicao.D2,
                new BigDecimal("30.00")
            );
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("há conflito de horário")
    public void haConflitoHorario() {
        // O conflito é simulado pelo horário igual ao da sessão existente
    }

    @Entao("o sistema deve criar um novo registro")
    public void sistemaCriaNovoRegistro() {
        Optional<Sessao> sessaoSalva = sessaoRepositorio.buscarPorId(sessao.getId());
        assertTrue(sessaoSalva.isPresent());
    }

    @Entao("a sessão deve receber um ID único")
    public void sessaoRecebeIdUnico() {
        assertNotNull(sessao.getId());
    }

    @Entao("os assentos devem ser inicializados como disponíveis")
    public void assentosInicializadosDisponiveis() {
        Optional<Sessao> sessaoSalva = sessaoRepositorio.buscarPorId(sessao.getId());
        assertTrue(sessaoSalva.isPresent());
        assertTrue(sessaoSalva.get().getAssentos().stream()
                .allMatch(assento -> assento.getStatus() == StatusAssento.DISPONIVEL));
    }

    @Entao("o sistema deve mostrar o mapa de assentos")
    public void sistemaMostraMapaAssentos() {
        // Implementar verificação do mapa de assentos
    }

    @Entao("indicar quais assentos estão disponíveis")
    public void indicarAssentosDisponiveis() {
        // Implementar verificação de assentos disponíveis
    }

    @Entao("indicar quais assentos estão ocupados")
    public void indicarAssentosOcupados() {
        // Implementar verificação de assentos ocupados
    }

    @Entao("indicar quais assentos estão reservados")
    public void indicarAssentosReservados() {
        // Implementar verificação de assentos reservados
    }

    @Entao("o sistema deve marcar a sessão como cancelada")
    public void sistemaMarcaSessaoCancelada() {
        Optional<Sessao> sessaoCancelada = sessaoRepositorio.buscarPorId(sessaoId);
        assertTrue(sessaoCancelada.isPresent());
        assertEquals(StatusSessao.CANCELADA, sessaoCancelada.get().getStatus());
    }

    @Entao("notificar os clientes com ingressos")
    public void notificarClientesIngressos() {
        // Implementar verificação de notificação
    }

    @Entao("gerar créditos de compensação")
    public void gerarCreditosCompensacao() {
        // Implementar verificação de créditos
    }

    @Entao("liberar os assentos ocupados")
    public void liberarAssentosOcupados() {
        Optional<Sessao> sessaoAtualizada = sessaoRepositorio.buscarPorId(sessaoId);
        assertTrue(sessaoAtualizada.isPresent());
        assertTrue(sessaoAtualizada.get().getAssentos().stream()
                .allMatch(assento -> assento.getStatus() == StatusAssento.DISPONIVEL));
    }

    @Entao("o sistema deve informar o conflito")
    public void sistemaInformaConflito() {
        assertNotNull(excecao);
        // Implementar verificação específica do tipo de exceção
    }

    @Entao("não deve permitir o cadastro da nova sessão")
    public void naoPermiteCadastroNovaSessao() {
        assertNotNull(excecao);
        // Implementar verificação específica do tipo de exceção
    }
} 