package com.cinetech.aplicacao.pagamento;

import com.cinetech.dominio.pagamento.Credito;
import com.cinetech.dominio.pagamento.StatusCredito;
import com.cinetech.dominio.pagamento.repository.CreditoRepository;
import com.cinetech.dominio.comum.Reserva;
import com.cinetech.dominio.comum.Cliente;
import com.cinetech.dominio.comum.Sessao;
import com.cinetech.dominio.comum.StatusSessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreditoService {
    @Autowired
    private CreditoRepository creditoRepository;

    private Cliente converterCliente(Cliente clienteComum) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteComum.getId());
        cliente.setNome(clienteComum.getNome());
        cliente.setEmail(clienteComum.getEmail());
        cliente.setCpf(clienteComum.getCpf());
        cliente.setSenha(clienteComum.getSenha());
        cliente.setDataNascimento(clienteComum.getDataNascimento());
        return cliente;
    }

    private Sessao converterSessao(Sessao sessaoComum) {
        Sessao sessao = new Sessao();
        sessao.setId(sessaoComum.getId());
        sessao.setFilmeId(sessaoComum.getFilmeId());
        sessao.setDataHora(sessaoComum.getDataHora());
        sessao.setSalaId(sessaoComum.getSalaId());
        sessao.setStatus(sessaoComum.getStatus());
        return sessao;
    }

    @Transactional
    public Credito gerarCredito(Reserva reserva) {
        Credito credito = new Credito();
        credito.setCliente(converterCliente(reserva.getCliente()));
        credito.setSessao(converterSessao(reserva.getSessao()));
        credito.setValor(reserva.getValor());
        credito.setStatus(StatusCredito.PENDENTE);
        credito.setDataCredito(LocalDateTime.now());
        return creditoRepository.save(credito);
    }

    public List<Credito> buscarCreditosPendentes(Long clienteId) {
        return creditoRepository.findByClienteIdAndStatus(clienteId, StatusCredito.PENDENTE);
    }
} 