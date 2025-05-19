package com.cinetech.aplicacao.pagamento;

import com.cinetech.dominio.pagamento.Credito;
import com.cinetech.dominio.pagamento.StatusCredito;
import com.cinetech.dominio.pagamento.repository.CreditoRepository;
import com.cinetech.dominio.reserva.Reserva;
import com.cinetech.dominio.usuario.Cliente;
import com.cinetech.dominio.comum.Sessao;
import com.cinetech.dominio.comum.StatusSessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreditoService {
    @Autowired
    private CreditoRepository creditoRepository;

    private Cliente converterCliente(com.cinetech.dominio.comum.Cliente clienteComum) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteComum.getId());
        cliente.setNome(clienteComum.getNome());
        cliente.setEmail(clienteComum.getEmail());
        cliente.setCpf(clienteComum.getCpf());
        return cliente;
    }

    private Sessao converterSessao(com.cinetech.dominio.sessao.Sessao sessaoSessao) {
        Sessao sessao = new Sessao();
        sessao.setId(sessaoSessao.getId());
        sessao.setFilmeId(sessaoSessao.getFilmeId());
        sessao.setDataHora(sessaoSessao.getDataHora());
        sessao.setSalaId(sessaoSessao.getSalaId());
        sessao.setStatus(StatusSessao.valueOf(sessaoSessao.getStatus().name()));
        return sessao;
    }

    @Transactional
    public Credito gerarCredito(Reserva reserva) {
        Credito credito = new Credito();
        credito.setCliente(converterCliente(reserva.getCliente()));
        credito.setSessao(converterSessao(reserva.getSessao()));
        credito.setValor(reserva.getValor());
        credito.setStatus(StatusCredito.PENDENTE);
        return creditoRepository.save(credito);
    }
} 