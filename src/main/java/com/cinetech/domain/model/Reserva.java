package com.cinetech.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "sessao_id", nullable = false)
    private Sessao sessao;

    @ManyToOne
    @JoinColumn(name = "assento_id", nullable = false)
    private Assento assento;

    @Column(nullable = false)
    private LocalDateTime dataReserva;

    @Column(nullable = false)
    private Double valor;
} 