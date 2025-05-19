package com.cinetech.dominio.sessao;

import com.cinetech.dominio.comum.Assento;
import com.cinetech.dominio.sessao.StatusSessao;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sessoes")
@Data
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filme_id", nullable = false)
    private Long filmeId;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "sala_id", nullable = false)
    private Long salaId;

    @OneToMany(mappedBy = "sessao", cascade = CascadeType.ALL)
    private List<Assento> assentos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSessao status = StatusSessao.ABERTA;
} 