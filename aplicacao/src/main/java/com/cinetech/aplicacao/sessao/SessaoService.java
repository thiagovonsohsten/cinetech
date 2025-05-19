package com.cinetech.aplicacao.sessao;

import com.cinetech.dominio.comum.Assento;
import com.cinetech.dominio.comum.StatusAssento;
import com.cinetech.dominio.comum.Sessao;
import com.cinetech.dominio.comum.StatusSessao;
import com.cinetech.dominio.comum.Sala;
import com.cinetech.dominio.comum.Reserva;
import com.cinetech.dominio.sessao.repository.SessaoRepository;
import com.cinetech.dominio.comum.repository.SalaRepository;
import com.cinetech.dominio.comum.repository.AssentoRepository;
import com.cinetech.dominio.comum.repository.ReservaRepository;
import com.cinetech.aplicacao.pagamento.CreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SessaoService {
    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private AssentoRepository assentoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private CreditoService creditoService;

    @Transactional
    public Sessao criarSessao(Sessao sessao) {
        sessao.setStatus(StatusSessao.ABERTA);
        return sessaoRepository.save(sessao);
    }

    public Sessao buscarSessao(Long id) {
        return sessaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));
    }

    @Transactional
    public void atualizarStatusSessao(Long sessaoId, StatusSessao novoStatus) {
        Sessao sessao = buscarSessao(sessaoId);
        sessao.setStatus(novoStatus);
        sessaoRepository.save(sessao);
    }

    @Transactional
    public void verificarLotacaoSessao(Long sessaoId) {
        Sessao sessao = buscarSessao(sessaoId);
        Sala sala = salaRepository.findById(sessao.getSalaId())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        List<Assento> assentosOcupados = assentoRepository.findBySalaIdAndStatus(
            sessao.getSalaId(), 
            StatusAssento.OCUPADO
        );

        if (assentosOcupados.size() >= sala.getCapacidade()) {
            sessao.setStatus(StatusSessao.LOTADA);
            sessaoRepository.save(sessao);
        }
    }

    @Transactional
    public void cancelarSessao(Long sessaoId) {
        Sessao sessao = buscarSessao(sessaoId);
        sessao.setStatus(StatusSessao.CANCELADA);
        sessaoRepository.save(sessao);

        // Liberar todos os assentos
        List<Assento> assentos = assentoRepository.findBySalaIdAndStatus(
            sessao.getSalaId(), 
            StatusAssento.OCUPADO
        );
        for (Assento assento : assentos) {
            assento.setStatus(StatusAssento.DISPONIVEL);
            assentoRepository.save(assento);
        }

        // Gerar créditos para todas as reservas
        List<Reserva> reservas = reservaRepository.findBySessaoId(sessaoId);
        for (Reserva reserva : reservas) {
            creditoService.gerarCredito(reserva);
        }
    }
} 