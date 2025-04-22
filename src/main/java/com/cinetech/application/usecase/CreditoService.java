package com.cinetech.application.usecase;

import com.cinetech.domain.model.Credito;
import com.cinetech.domain.model.Reserva;
import com.cinetech.infrastructure.repository.CreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class CreditoService {
    @Autowired
    private CreditoRepository creditoRepository;

    @Transactional
    public void gerarCreditoPorCancelamento(Reserva reserva) {
        Credito credito = new Credito();
        credito.setCliente(reserva.getCliente());
        credito.setValor(reserva.getValor());
        credito.setDataCriacao(LocalDateTime.now());
        credito.setSessaoCancelada(reserva.getSessao());
        credito.setUtilizado(false);
        
        creditoRepository.save(credito);
    }
} 