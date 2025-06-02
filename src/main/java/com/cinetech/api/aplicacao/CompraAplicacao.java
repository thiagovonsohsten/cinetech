package com.cinetech.api.aplicacao;

import com.cinetech.api.dominio.enums.MetodoPagamento;
import com.cinetech.api.dominio.enums.StatusPagamento;
import com.cinetech.api.dominio.enums.TipoPromocao;
import com.cinetech.api.dominio.modelos.assento.Assento;
import com.cinetech.api.dominio.modelos.cliente.Cliente;
import com.cinetech.api.dominio.modelos.cliente.ClienteId;
import com.cinetech.api.dominio.modelos.ingresso.Ingresso;
import com.cinetech.api.dominio.modelos.ingresso.IngressoId;
import com.cinetech.api.dominio.modelos.pagamento.Pagamento;
import com.cinetech.api.dominio.modelos.promocao.Promocao;
import com.cinetech.api.dominio.modelos.promocao.PromocaoId;
import com.cinetech.api.dominio.modelos.sessao.Sessao;
import com.cinetech.api.dominio.modelos.sessao.SessaoId;
import com.cinetech.api.dominio.repositorios.*;
import com.cinetech.api.dominio.servicos.GestaoPontosFidelidadeServico.GestaoPontosFidelidadeServico;
import com.cinetech.api.dominio.servicos.PrecificacaoServico.PrecificacaoServico;
import com.cinetech.api.infraestrutura.web.dto.compra.ConfirmarPagamentoRequestDTO;
import com.cinetech.api.infraestrutura.web.dto.compra.IniciarCompraRequestDTO;
import com.cinetech.api.infraestrutura.web.dto.compra.PrecoCalculadoResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class CompraAplicacao {

    private final ClienteRepositorio clienteRepositorio;
    private final SessaoRepositorio sessaoRepositorio;
    private final PromocaoRepositorio promocaoRepositorio;
    private final IngressoRepositorio ingressoRepositorio;
    private final PagamentoRepositorio pagamentoRepositorio;
    private final PrecificacaoServico precificacaoService;
    private final GestaoPontosFidelidadeServico gestaoPontosFidelidadeService;
    // Mappers seriam injetados se esta camada retornasse DTOs diretamente,
    // mas vamos retornar entidades de domínio ou VOs simples, e o Controller faz o mapeamento para DTOs da API.

    public CompraAplicacao(ClienteRepositorio clienteRepositorio,
                           SessaoRepositorio sessaoRepositorio,
                           PromocaoRepositorio promocaoRepositorio,
                           IngressoRepositorio ingressoRepositorio,
                           PagamentoRepositorio pagamentoRepositorio,
                           PrecificacaoServico precificacaoService,
                           GestaoPontosFidelidadeServico gestaoPontosFidelidadeService) {
        this.clienteRepositorio = clienteRepositorio;
        this.sessaoRepositorio = sessaoRepositorio;
        this.promocaoRepositorio = promocaoRepositorio;
        this.ingressoRepositorio = ingressoRepositorio;
        this.pagamentoRepositorio = pagamentoRepositorio;
        this.precificacaoService = precificacaoService;
        this.gestaoPontosFidelidadeService = gestaoPontosFidelidadeService;
    }

    /**
     * Caso de Uso: Cliente inicia a compra de um ingresso, selecionando sessão e assento,
     * e o sistema calcula o preço considerando descontos e promoções.
     * Também realiza o bloqueio temporário do assento.
     * (Combina F1 e F5 para o cálculo de preço)
     */
    @Transactional // Envolve buscar dados e potencialmente bloquear um assento
    public PrecoCalculadoResponseDTO iniciarSelecaoIngressoECalcularPreco(IniciarCompraRequestDTO request) {
        Objects.requireNonNull(request, "Dados da requisição não podem ser nulos.");
        ClienteId clienteId = ClienteId.de(request.getClienteId());
        SessaoId sessaoId = SessaoId.de(request.getSessaoId());
        String identificadorAssento = request.getIdentificadorAssento();
        LocalDateTime dataHoraCompra = LocalDateTime.now();

        Cliente cliente = clienteRepositorio.buscarPorId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + clienteId));

        Sessao sessao = sessaoRepositorio.buscarPorId(sessaoId)
                .orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada: " + sessaoId));

        // F1: Bloqueio temporário do assento
        // A entidade Sessao lida com a lógica de reserva e exceções se não for possível
        Assento assentoReservado = sessao.reservarAssentoTemporariamente(identificadorAssento, clienteId, SessaoAplicacao.TEMPO_RESERVA_ASSENTO_MINUTOS);
        sessaoRepositorio.salvar(sessao); // Salva a sessão com o assento atualizado

        // Buscar promoções vigentes
        List<Promocao> promocoesVigentes = promocaoRepositorio.buscarPromocoesVigentes(dataHoraCompra.toLocalDate());

        // Usar o Domain Service para calcular o preço
        PrecificacaoServico.ResultadoPrecificacao resultadoPrecificacao =
                precificacaoService.calcularPrecoFinalIngresso(cliente, sessao, promocoesVigentes, dataHoraCompra);

        // Mensagem de comprovação para meia-entrada (F5 - Source 44)
        String mensagemAdicional = null;
        if (cliente.elegivelParaMeiaEntrada() &&
                resultadoPrecificacao.descontoAplicado().compareTo(BigDecimal.ZERO) > 0) {
            // Se um desconto foi aplicado E o cliente é elegível para meia, assume-se que a meia foi considerada.
            // Uma lógica mais precisa verificaria se a `promocaoAplicada` é do tipo MEIA_ENTRADA_PERFIL.
            if (resultadoPrecificacao.getPromocaoAplicada().isPresent() &&
                    resultadoPrecificacao.getPromocaoAplicada().get().getTipoPromocao() == TipoPromocao.MEIA_ENTRADA_PERFIL ||
                    (resultadoPrecificacao.getPromocaoAplicada().isEmpty() && cliente.elegivelParaMeiaEntrada()) ) { // Meia legal direta
                mensagemAdicional = "Comprovação de meia-entrada será exigida na entrada da sala.";
            }
        }

        // Aqui, em um sistema real, você poderia criar um "Pedido" ou "Carrinho" temporário
        // e retornar um ID para ele, junto com o preço.
        // Por simplicidade, retornamos os detalhes do preço calculado.
        return new PrecoCalculadoResponseDTO(
                resultadoPrecificacao.precoOriginal(),
                resultadoPrecificacao.descontoAplicado(),
                resultadoPrecificacao.precoFinal(),
                resultadoPrecificacao.getPromocaoAplicada().map(Promocao::getNomeDescritivo).orElse(mensagemAdicional != null ? "Meia-Entrada Aplicada" : null),
                identificadorAssento, // Incluindo o assento reservado no DTO de resposta
                mensagemAdicional
        );
    }

    /**
     * Caso de Uso: Cliente confirma o pagamento e finaliza a compra do ingresso. (F1, F6)
     * Este é um exemplo SIMPLIFICADO. Um fluxo de pagamento real envolve gateways, etc.
     */
    @Transactional
    public Ingresso finalizarCompraIngresso(ConfirmarPagamentoRequestDTO request) {
        ClienteId clienteId = ClienteId.de(request.getClienteId());
        SessaoId sessaoId = SessaoId.de(request.getSessaoId());
        String identificadorAssento = request.getIdentificadorAssento();

        Cliente cliente = clienteRepositorio.buscarPorId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + clienteId));
        Sessao sessao = sessaoRepositorio.buscarPorId(sessaoId)
                .orElseThrow(() -> new IllegalArgumentException("Sessão não encontrada: " + sessaoId));
        Assento assento = sessao.buscarAssentoPorIdentificador(identificadorAssento)
                .orElseThrow(() -> new IllegalArgumentException("Assento " + identificadorAssento + " não encontrado na sessão " + sessaoId));

        // Recalcular preço
        List<Promocao> promocoesVigentes = promocaoRepositorio.buscarPromocoesVigentes(LocalDateTime.now().toLocalDate());
        PrecificacaoServico.ResultadoPrecificacao resultadoPrecificacao =
                precificacaoService.calcularPrecoFinalIngresso(cliente, sessao, promocoesVigentes, LocalDateTime.now());
        BigDecimal valorFinalAPagar = resultadoPrecificacao.precoFinal();

        // Se o método de pagamento for pontos de fidelidade, verifica e utiliza os pontos
        if (request.getMetodoPagamento() == MetodoPagamento.PONTOS_FIDELIDADE) {
            int pontosAUtilizar = request.getPontosFidelidadeUtilizados();
            if (pontosAUtilizar > 0) {
                // Verifica se o cliente tem pontos suficientes
                if (cliente.getSaldoTotalPontosFidelidadeValidos(LocalDate.now()) < pontosAUtilizar) {
                    throw new IllegalStateException("Cliente não possui pontos suficientes");
                }
                
                // Utiliza os pontos
                gestaoPontosFidelidadeService.utilizarPontos(cliente, pontosAUtilizar, LocalDate.now());
                
                // Atualiza o valor final a pagar
                valorFinalAPagar = valorFinalAPagar.subtract(new BigDecimal(pontosAUtilizar));
                if (valorFinalAPagar.compareTo(BigDecimal.ZERO) < 0) {
                    valorFinalAPagar = BigDecimal.ZERO;
                }
            }
        }

        // Simular pagamento
        IngressoId futuroIngressoId = IngressoId.novo(); // Gera o ID do ingresso antes para o pagamento
        Pagamento pagamento = new Pagamento(futuroIngressoId, valorFinalAPagar, request.getMetodoPagamento());
        pagamento.iniciarProcessamento();
        pagamento.aprovar("TRANSACAO_SIMULADA_123");
        pagamentoRepositorio.salvar(pagamento);

        // Criar o ingresso
        Ingresso ingresso = new Ingresso(
                cliente,
                sessao,
                assento,
                valorFinalAPagar,
                resultadoPrecificacao.getPromocaoAplicada().isPresent() && 
                    resultadoPrecificacao.getPromocaoAplicada().get().getTipoPromocao() == TipoPromocao.MEIA_ENTRADA_PERFIL,
                resultadoPrecificacao.getPromocaoAplicada().isPresent() ? 
                    resultadoPrecificacao.getPromocaoAplicada().get().getId() : null
        );

        // Salvar o ingresso
        ingresso = ingressoRepositorio.salvar(ingresso);

        // Gerar novos pontos de fidelidade se o valor pago for maior que zero
        if (valorFinalAPagar.compareTo(BigDecimal.ZERO) > 0) {
            gestaoPontosFidelidadeService.concederPontosPorCompra(cliente, valorFinalAPagar, ingresso.getId());
            clienteRepositorio.salvar(cliente);
        }

        return ingresso;
    }
}
