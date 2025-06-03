package com.cinetech.api.steps;

import com.cinetech.api.dominio.enums.PerfilCliente;
import com.cinetech.api.dominio.modelos.cliente.Cliente;
import com.cinetech.api.dominio.modelos.cliente.ClienteId;
import com.cinetech.api.dominio.modelos.ingresso.Ingresso;
import com.cinetech.api.dominio.modelos.pontofidelidade.PontoFidelidade;
import com.cinetech.api.dominio.repositorios.ClienteRepositorio;
import com.cinetech.api.dominio.repositorios.IngressoRepositorio;
import com.cinetech.api.aplicacao.ClienteAplicacao;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class GestaoClienteSteps {

    @Autowired
    private ClienteAplicacao clienteAplicacao;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private IngressoRepositorio ingressoRepositorio;

    private Cliente cliente;
    private ClienteId clienteId;
    private Exception excecao;

    @Dado("que tenho os dados do novo cliente")
    public void tenhoDadosNovoCliente() {
        cliente = new Cliente("João Silva", "joao@email.com", "12345678900", PerfilCliente.REGULAR);
    }

    @Dado("que existe um cliente cadastrado")
    public void existeClienteCadastrado() {
        cliente = new Cliente("Maria Santos", "maria@email.com", "98765432100", PerfilCliente.REGULAR);
        clienteRepositorio.salvar(cliente);
        clienteId = cliente.getId();
    }

    @Dado("que o cliente possui compras")
    public void clientePossuiCompras() {
        // Implementar lógica para criar compras
    }

    @Dado("que o cliente possui pontos de fidelidade")
    public void clientePossuiPontosFidelidade() {
        // Implementar lógica para criar pontos
    }

    @Quando("eu cadastro o cliente no sistema")
    public void cadastroClienteSistema() {
        try {
            clienteAplicacao.cadastrarNovoCliente(
                cliente.getNome(), 
                cliente.getEmail(), 
                cliente.getCpf(), 
                cliente.getPerfil(),
                LocalDate.now(), // Data de nascimento padrão
                "senha123" // Senha padrão
            );
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("eu atualizo o perfil para estudante")
    public void atualizoPerfilEstudante() {
        try {
            cliente.setPerfil(PerfilCliente.ESTUDANTE);
            clienteRepositorio.salvar(cliente);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("eu envio o comprovante de estudante")
    public void envioComprovanteEstudante() {
        // Implementar lógica de envio
    }

    @Quando("eu consulto o histórico de compras")
    public void consultoHistoricoCompras() {
        // Implementar lógica de consulta
    }

    @Quando("eu consulto os pontos de fidelidade")
    public void consultoPontosFidelidade() {
        // Implementar lógica de consulta
    }

    @Entao("o sistema deve criar um novo registro")
    public void sistemaCriaNovoRegistro() {
        Optional<Cliente> clienteSalvo = clienteRepositorio.buscarPorId(cliente.getId());
        assertTrue(clienteSalvo.isPresent());
    }

    @Entao("o cliente deve receber um ID único")
    public void clienteRecebeIdUnico() {
        assertNotNull(cliente.getId());
    }

    @Entao("o perfil do cliente deve ser definido como normal")
    public void perfilClienteDefinidoNormal() {
        Optional<Cliente> clienteSalvo = clienteRepositorio.buscarPorId(cliente.getId());
        assertTrue(clienteSalvo.isPresent());
        assertEquals(PerfilCliente.REGULAR, clienteSalvo.get().getPerfil());
    }

    @Entao("o sistema deve atualizar o perfil")
    public void sistemaAtualizaPerfil() {
        Optional<Cliente> clienteAtualizado = clienteRepositorio.buscarPorId(clienteId);
        assertTrue(clienteAtualizado.isPresent());
        assertEquals(PerfilCliente.ESTUDANTE, clienteAtualizado.get().getPerfil());
    }

    @Entao("o cliente deve ser elegível para meia entrada")
    public void clienteElegivelMeiaEntrada() {
        Optional<Cliente> clienteAtualizado = clienteRepositorio.buscarPorId(clienteId);
        assertTrue(clienteAtualizado.isPresent());
        assertTrue(clienteAtualizado.get().elegivelParaMeiaEntrada());
    }

    @Entao("o sistema deve listar as compras")
    public void sistemaListaCompras() {
        List<Ingresso> ingressos = ingressoRepositorio.buscarPorClienteId(clienteId);
        assertFalse(ingressos.isEmpty());
    }

    @Entao("mostrar os detalhes de cada compra")
    public void mostrarDetalhesCompras() {
        List<Ingresso> ingressos = ingressoRepositorio.buscarPorClienteId(clienteId);
        ingressos.forEach(ingresso -> {
            assertNotNull(ingresso.getValorPago());
            assertNotNull(ingresso.getDataCompra());
        });
    }

    @Entao("o sistema deve mostrar o saldo atual")
    public void sistemaMostraSaldoAtual() {
        Optional<Cliente> clienteAtualizado = clienteRepositorio.buscarPorId(clienteId);
        assertTrue(clienteAtualizado.isPresent());
        assertTrue(clienteAtualizado.get().getSaldoTotalPontosFidelidadeValidos(LocalDate.now()) > 0);
    }

    @Entao("listar o histórico de pontos")
    public void listarHistoricoPontos() {
        Optional<Cliente> clienteAtualizado = clienteRepositorio.buscarPorId(clienteId);
        assertTrue(clienteAtualizado.isPresent());
        List<PontoFidelidade> pontos = clienteAtualizado.get().getPontosFidelidade();
        assertFalse(pontos.isEmpty());
        pontos.forEach(ponto -> {
            assertNotNull(ponto.getDataAquisicao());
            assertTrue(ponto.getQuantidadeDisponivel() > 0);
        });
    }

    @Entao("indicar a data de expiração dos pontos")
    public void indicarDataExpiracaoPontos() {
        Optional<Cliente> clienteAtualizado = clienteRepositorio.buscarPorId(clienteId);
        assertTrue(clienteAtualizado.isPresent());
        List<PontoFidelidade> pontos = clienteAtualizado.get().getPontosFidelidade();
        assertFalse(pontos.isEmpty());
        pontos.forEach(ponto -> {
            assertNotNull(ponto.getDataExpiracao());
        });
    }
} 