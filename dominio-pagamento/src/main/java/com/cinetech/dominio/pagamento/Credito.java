package com.cinetech.dominio.pagamento;

import com.cinetech.dominio.comum.Sessao;
import com.cinetech.dominio.usuario.Cliente;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "creditos")
public class Credito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "data_credito", nullable = false)
    private LocalDateTime dataCredito;

    @ManyToOne
    @JoinColumn(name = "sessao_id")
    private Sessao sessao;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusCredito status = StatusCredito.PENDENTE;
} 