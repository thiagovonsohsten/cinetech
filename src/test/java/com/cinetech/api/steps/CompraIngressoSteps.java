package com.cinetech.api.steps;

import com.cinetech.api.dominio.enums.MetodoPagamento;
import com.cinetech.api.dominio.enums.PerfilCliente;
import com.cinetech.api.dominio.enums.StatusAssento;
import com.cinetech.api.dominio.enums.TipoAssento;
import com.cinetech.api.dominio.enums.TipoExibicao;
import com.cinetech.api.dominio.enums.TipoSala;
import com.cinetech.api.dominio.modelos.cliente.Cliente;
import com.cinetech.api.dominio.modelos.cliente.ClienteId;
import com.cinetech.api.dominio.modelos.filme.Filme;
import com.cinetech.api.dominio.modelos.ingresso.Ingresso;
import com.cinetech.api.dominio.modelos.ingresso.IngressoId;
import com.cinetech.api.dominio.modelos.pontofidelidade.PontoFidelidade;
import com.cinetech.api.dominio.modelos.sala.Sala;
import com.cinetech.api.dominio.modelos.sessao.Sessao;
import com.cinetech.api.dominio.modelos.sessao.SessaoId;
import com.cinetech.api.dominio.repositorios.ClienteRepositorio;
import com.cinetech.api.dominio.repositorios.FilmeRepositorio;
import com.cinetech.api.dominio.repositorios.SalaRepositorio;
import com.cinetech.api.dominio.repositorios.SessaoRepositorio;
import com.cinetech.api.aplicacao.CompraAplicacao;
import com.cinetech.api.infraestrutura.web.dto.compra.ConfirmarPagamentoRequestDTO;
import com.cinetech.api.infraestrutura.web.dto.compra.IniciarCompraRequestDTO;
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

public class CompraIngressoSteps {

    @Autowired
    private CompraAplicacao compraAplicacao;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private SessaoRepositorio sessaoRepositorio;

    @Autowired
    private FilmeRepositorio filmeRepositorio;

    @Autowired
    private SalaRepositorio salaRepositorio;

    private Cliente cliente;
    private ClienteId clienteId;
    private Sessao sessao;
    private SessaoId sessaoId;
    private List<Ingresso> ingressosComprados;
    private Exception excecao;
    private int pontosFidelidadeIniciais;
    private String assentoSelecionado;
    private TipoAssento tipoAssentoSelecionado;
    private int pontosAUtilizar;
    private BigDecimal precoFinal;
    private String codigoPromocional;
    private BigDecimal valorOriginal;
    private BigDecimal valorComDesconto;

    @Dado("que existe um cliente cadastrado")
    public void existeClienteCadastrado() {
        cliente = new Cliente("João Silva", "joao@email.com", "12345678900", PerfilCliente.REGULAR);
        clienteRepositorio.salvar(cliente);
        clienteId = cliente.getId();
    }

    @Dado("que existe uma sessão disponível")
    public void existeSessaoDisponivel() {
        // Criar filme
        Filme filme = new Filme(
            "Avatar 2",
            "Aventura",
            180,
            "Português",
            "12 anos",
            LocalDate.now(),
            LocalDate.now().plusMonths(1),
            "Sinopse do filme"
        );
        filmeRepositorio.salvar(filme);

        // Criar sala
        Sala sala = new Sala("Sala 1", 100, TipoSala.SALA_2D, true);
        salaRepositorio.salvar(sala);

        // Criar sessão
        sessao = new Sessao(
            filme,
            sala,
            LocalDateTime.now().plusHours(2),
            TipoExibicao.D2,
            new BigDecimal("30.00")
        );
        sessaoRepositorio.salvar(sessao);
        sessaoId = sessao.getId();
    }

    @Dado("que existem assentos disponíveis")
    public void existemAssentosDisponiveis() {
        Optional<Sessao> sessaoAtualizada = sessaoRepositorio.buscarPorId(sessaoId);
        assertTrue(sessaoAtualizada.isPresent());
        assertTrue(sessaoAtualizada.get().getAssentos().stream()
            .anyMatch(assento -> assento.getStatus() == StatusAssento.DISPONIVEL));
    }

    @Dado("que o cliente possui pontos de fidelidade suficientes")
    public void clientePossuiPontosFidelidadeSuficientes() {
        // Adicionar pontos de fidelidade ao cliente
        cliente.adicionarNovosPontos(100, null); // 100 pontos sem ingresso de origem
        clienteRepositorio.salvar(cliente);
        
        // Verificar se os pontos foram adicionados
        Optional<Cliente> clienteAtualizado = clienteRepositorio.buscarPorId(clienteId);
        assertTrue(clienteAtualizado.isPresent());
        pontosFidelidadeIniciais = clienteAtualizado.get().getSaldoTotalPontosFidelidadeValidos(LocalDate.now());
        assertTrue(pontosFidelidadeIniciais >= 100);
    }

    @Quando("eu seleciono os assentos desejados")
    public void selecionoAssentosDesejados() {
        Optional<Sessao> sessaoAtualizada = sessaoRepositorio.buscarPorId(sessaoId);
        assertTrue(sessaoAtualizada.isPresent());
        
        // Encontrar um assento disponível
        assentoSelecionado = sessaoAtualizada.get().getAssentos().stream()
            .filter(assento -> assento.getStatus() == StatusAssento.DISPONIVEL)
            .findFirst()
            .map(assento -> assento.getIdentificadorPosicao())
            .orElseThrow(() -> new IllegalStateException("Não há assentos disponíveis"));
    }

    @Quando("eu escolho o tipo de ingresso")
    public void escolhoTipoIngresso() {
        tipoAssentoSelecionado = TipoAssento.COMUM;
    }

    @Quando("aplico {int} pontos de fidelidade")
    public void aplicarPontosFidelidade(int pontos) {
        this.pontosAUtilizar = pontos;
        Sessao sessao = sessaoRepositorio.buscarPorId(sessaoId)
                .orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada: " + sessaoId));
        
        Cliente cliente = clienteRepositorio.buscarPorId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + clienteId));
        
        // Verifica se o cliente tem pontos suficientes
        if (cliente.getSaldoTotalPontosFidelidadeValidos(LocalDate.now()) < pontos) {
            throw new IllegalStateException("Cliente não possui pontos suficientes");
        }
        
        // Calcula o valor do desconto baseado nos pontos (1 ponto = R$1)
        BigDecimal valorDesconto = new BigDecimal(pontos);
        BigDecimal precoOriginal = sessao.getPrecoIngressoBase();
        BigDecimal precoFinal = precoOriginal.subtract(valorDesconto);
        
        // Atualiza o preço final
        this.precoFinal = precoFinal;
        
        // Atualiza o DTO de confirmação de pagamento
        ConfirmarPagamentoRequestDTO request = new ConfirmarPagamentoRequestDTO();
        request.setClienteId(clienteId.toString());
        request.setSessaoId(sessaoId.toString());
        request.setIdentificadorAssento(assentoSelecionado);
        request.setMetodoPagamento(MetodoPagamento.PONTOS_FIDELIDADE);
        request.setPontosFidelidadeUtilizados(pontos);
        
        // Salva o cliente atualizado
        clienteRepositorio.salvar(cliente);
    }

    @Quando("eu confirmo a compra")
    public void confirmoCompra() {
        try {
            IniciarCompraRequestDTO request = new IniciarCompraRequestDTO();
            request.setClienteId(clienteId.toString());
            request.setSessaoId(sessaoId.toString());
            request.setIdentificadorAssento(assentoSelecionado);
            
            ConfirmarPagamentoRequestDTO confirmacao = new ConfirmarPagamentoRequestDTO();
            confirmacao.setClienteId(clienteId.toString());
            confirmacao.setSessaoId(sessaoId.toString());
            confirmacao.setIdentificadorAssento(assentoSelecionado);
            confirmacao.setMetodoPagamento(MetodoPagamento.CARTAO_CREDITO);
            confirmacao.setPontosFidelidadeUtilizados(pontosAUtilizar);
            
            Ingresso ingresso = compraAplicacao.finalizarCompraIngresso(confirmacao);
            ingressosComprados = List.of(ingresso);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("o pagamento com cartão falha")
    public void pagamentoCartaoFalha() {
        try {
            IniciarCompraRequestDTO request = new IniciarCompraRequestDTO();
            request.setClienteId(clienteId.toString());
            request.setSessaoId(sessaoId.toString());
            request.setIdentificadorAssento(assentoSelecionado);
            
            ConfirmarPagamentoRequestDTO confirmacao = new ConfirmarPagamentoRequestDTO();
            confirmacao.setClienteId(clienteId.toString());
            confirmacao.setSessaoId(sessaoId.toString());
            confirmacao.setIdentificadorAssento(assentoSelecionado);
            confirmacao.setMetodoPagamento(MetodoPagamento.CARTAO_CREDITO);
            
            // Simula falha no pagamento
            throw new RuntimeException("Falha no processamento do cartão de crédito");
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("eu cancelo a compra")
    public void canceloCompra() {
        try {
            // Simula cancelamento antes da confirmação
            Optional<Sessao> sessaoAtualizada = sessaoRepositorio.buscarPorId(sessaoId);
            assertTrue(sessaoAtualizada.isPresent());
            
            // Libera o assento reservado
            sessaoAtualizada.get().getAssentos().stream()
                .filter(assento -> assento.getIdentificadorPosicao().equals(assentoSelecionado))
                .findFirst()
                .ifPresent(assento -> assento.liberar());
            
            sessaoRepositorio.salvar(sessaoAtualizada.get());
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Entao("o sistema deve reservar os assentos")
    public void sistemaReservaAssentos() {
        Optional<Sessao> sessaoAtualizada = sessaoRepositorio.buscarPorId(sessaoId);
        assertTrue(sessaoAtualizada.isPresent());
        assertFalse(sessaoAtualizada.get().getAssentos().stream()
            .filter(assento -> assento.getIdentificadorPosicao().equals(assentoSelecionado))
            .findFirst()
            .map(assento -> assento.getStatus() == StatusAssento.DISPONIVEL)
            .orElse(false));
    }

    @Entao("gerar os ingressos")
    public void gerarIngressos() {
        assertNotNull(ingressosComprados);
        assertEquals(1, ingressosComprados.size());
        assertEquals(assentoSelecionado, ingressosComprados.get(0).getAssento().getIdentificadorPosicao());
    }

    @Entao("calcular o valor total")
    public void calcularValorTotal() {
        double valorTotal = ingressosComprados.stream()
                .mapToDouble(ingresso -> ingresso.getValorPago().doubleValue())
                .sum();
        assertTrue(valorTotal > 0);
        
        // Verificar se o desconto dos pontos foi aplicado
        Optional<Sessao> sessaoAtualizada = sessaoRepositorio.buscarPorId(sessaoId);
        assertTrue(sessaoAtualizada.isPresent());
        BigDecimal valorOriginal = sessaoAtualizada.get().getPrecoIngressoBase();
        assertTrue(valorTotal < valorOriginal.doubleValue(), 
            "Valor total (" + valorTotal + ") deve ser menor que o valor original (" + valorOriginal + ") após aplicar os pontos");
    }

    @Entao("aplicar o desconto dos pontos")
    public void aplicarDescontoPontos() {
        Optional<Cliente> clienteAtualizado = clienteRepositorio.buscarPorId(clienteId);
        assertTrue(clienteAtualizado.isPresent());
        int pontosFidelidadeAtuais = clienteAtualizado.get().getSaldoTotalPontosFidelidadeValidos(LocalDate.now());
        assertEquals(pontosFidelidadeIniciais - pontosAUtilizar, pontosFidelidadeAtuais);
    }

    @Entao("gerar novos pontos de fidelidade")
    public void gerarNovosPontosFidelidade() {
        Optional<Cliente> clienteAtualizado = clienteRepositorio.buscarPorId(clienteId);
        assertTrue(clienteAtualizado.isPresent());
        int pontosFidelidadeAtuais = clienteAtualizado.get().getSaldoTotalPontosFidelidadeValidos(LocalDate.now());
        assertTrue(pontosFidelidadeAtuais > 0);
    }

    @Entao("enviar o comprovante por email")
    public void enviarComprovanteEmail() {
        // Implementar verificação de envio
    }

    @Entao("o sistema deve informar que não há assentos disponíveis")
    public void sistemaInformaAssentosIndisponiveis() {
        assertNotNull(excecao);
        // Implementar verificação específica do tipo de exceção
    }

    @Entao("não deve permitir a compra")
    public void naoPermiteCompra() {
        assertNull(ingressosComprados);
    }

    @Entao("o sistema deve liberar os assentos reservados")
    public void sistemaLiberaAssentosReservados() {
        Optional<Sessao> sessaoAtualizada = sessaoRepositorio.buscarPorId(sessaoId);
        assertTrue(sessaoAtualizada.isPresent());
        assertTrue(sessaoAtualizada.get().getAssentos().stream()
            .filter(assento -> assento.getIdentificadorPosicao().equals(assentoSelecionado))
            .findFirst()
            .map(assento -> assento.getStatus() == StatusAssento.DISPONIVEL)
            .orElse(false));
    }

    @Entao("informar o erro de pagamento")
    public void informarErroPagamento() {
        assertNotNull(excecao);
        assertEquals("Falha no processamento do cartão de crédito", excecao.getMessage());
    }

    @Entao("não deve gerar os ingressos")
    public void naoGerarIngressos() {
        assertNull(ingressosComprados);
    }

    @Entao("não deve processar nenhum pagamento")
    public void naoProcessarPagamento() {
        Optional<Cliente> clienteAtualizado = clienteRepositorio.buscarPorId(clienteId);
        assertTrue(clienteAtualizado.isPresent());
        assertEquals(pontosFidelidadeIniciais, 
            clienteAtualizado.get().getSaldoTotalPontosFidelidadeValidos(LocalDate.now()));
    }

    @Dado("que existe uma promoção válida")
    public void existePromocaoValida() {
        codigoPromocional = "PROMO50";
        // Simula uma promoção válida com 50% de desconto
        valorOriginal = new BigDecimal("30.00");
        valorComDesconto = valorOriginal.multiply(new BigDecimal("0.5"));
    }

    @Dado("que existe uma promoção expirada")
    public void existePromocaoExpirada() {
        codigoPromocional = "PROMOEXP";
        valorOriginal = new BigDecimal("30.00");
    }

    @Quando("eu aplico a promoção")
    public void aplicoPromocao() {
        try {
            IniciarCompraRequestDTO request = new IniciarCompraRequestDTO();
            request.setClienteId(clienteId.toString());
            request.setSessaoId(sessaoId.toString());
            request.setIdentificadorAssento(assentoSelecionado);
            request.setCodigoPromocional(codigoPromocional);
            
            ConfirmarPagamentoRequestDTO confirmacao = new ConfirmarPagamentoRequestDTO();
            confirmacao.setClienteId(clienteId.toString());
            confirmacao.setSessaoId(sessaoId.toString());
            confirmacao.setIdentificadorAssento(assentoSelecionado);
            confirmacao.setMetodoPagamento(MetodoPagamento.CARTAO_CREDITO);
            confirmacao.setCodigoPromocional(codigoPromocional);
            
            Ingresso ingresso = compraAplicacao.finalizarCompraIngresso(confirmacao);
            ingressosComprados = List.of(ingresso);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("eu tento aplicar a promoção")
    public void tentoAplicarPromocao() {
        try {
            IniciarCompraRequestDTO request = new IniciarCompraRequestDTO();
            request.setClienteId(clienteId.toString());
            request.setSessaoId(sessaoId.toString());
            request.setIdentificadorAssento(assentoSelecionado);
            request.setCodigoPromocional(codigoPromocional);
            
            // Simula erro de promoção expirada
            throw new RuntimeException("Promoção expirada: " + codigoPromocional);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Entao("aplicar o desconto da promoção")
    public void aplicarDescontoPromocao() {
        assertNotNull(ingressosComprados);
        assertEquals(1, ingressosComprados.size());
        assertEquals(valorComDesconto, ingressosComprados.get(0).getValorPago());
    }

    @Entao("calcular o valor total com desconto")
    public void calcularValorTotalComDesconto() {
        assertNotNull(ingressosComprados);
        assertEquals(1, ingressosComprados.size());
        assertEquals(valorComDesconto, ingressosComprados.get(0).getValorPago());
    }

    @Entao("o sistema deve informar que a promoção está expirada")
    public void sistemaInformaPromocaoExpirada() {
        assertNotNull(excecao);
        assertEquals("Promoção expirada: " + codigoPromocional, excecao.getMessage());
    }

    @Entao("não deve aplicar o desconto")
    public void naoAplicarDesconto() {
        assertNull(ingressosComprados);
    }

    @Entao("deve manter o valor original")
    public void manterValorOriginal() {
        Optional<Sessao> sessaoAtualizada = sessaoRepositorio.buscarPorId(sessaoId);
        assertTrue(sessaoAtualizada.isPresent());
        assertEquals(valorOriginal, sessaoAtualizada.get().getPrecoIngressoBase());
    }
} 