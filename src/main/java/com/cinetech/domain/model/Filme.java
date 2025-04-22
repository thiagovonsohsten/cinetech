package com.cinetech.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "filmes")
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Integer duracaoMinutos;

    @Column(nullable = false)
    private LocalDate dataInicioExibicao;

    @Column(nullable = false)
    private LocalDate dataFimExibicao;

    @OneToMany(mappedBy = "filme")
    private List<Sessao> sessoes;
} 