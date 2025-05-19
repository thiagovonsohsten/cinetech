package com.cinetech.dominio.filme;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "filmes")
@Data
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String diretor;

    @Column(nullable = false)
    private String genero;

    @Column(nullable = false)
    private Integer duracao;

    @Column(name = "data_inicio_exibicao", nullable = false)
    private LocalDate dataInicioExibicao;

    @Column(name = "data_fim_exibicao", nullable = false)
    private LocalDate dataFimExibicao;
} 