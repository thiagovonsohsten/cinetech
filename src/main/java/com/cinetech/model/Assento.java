package com.cinetech.model;

import com.cinetech.model.enums.StatusAssento;
import com.cinetech.model.enums.TipoAssento;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "assentos")
public class Assento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAssento tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAssento status = StatusAssento.LIVRE;

    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;
} 