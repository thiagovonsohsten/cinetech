package com.cinetech.infraestrutura.persistencia.filme;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "filmes")
public class FilmeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String titulo;
    
    @Column(length = 1000)
    private String descricao;
    
    @Column(nullable = false)
    private Integer duracao;
    
    @Column(nullable = false)
    private String genero;
    
    @Column(name = "data_lancamento")
    private LocalDateTime dataLancamento;
    
    @Column(nullable = false)
    private Boolean ativo;
} 