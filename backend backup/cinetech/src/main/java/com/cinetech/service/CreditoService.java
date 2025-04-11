package com.cinetech.service;

import com.cinetech.model.Credito;
import com.cinetech.model.Reserva;
import com.cinetech.repository.CreditoRepository;
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