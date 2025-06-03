package com.cinetech.api.dominio.modelos.pagamento;

import com.cinetech.api.dominio.modelos.cliente.Cliente;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DescontoFidelidade implements EstrategiaDesconto {
    private static final BigDecimal PERCENTUAL_DESCONTO = new BigDecimal("0.10"); // 10% de desconto
    private static final int PONTOS_MINIMOS = 100;

    @Override
    public BigDecimal calcularDesconto(BigDecimal valorOriginal, Cliente cliente) {
        int pontosTotais = cliente.getPontosFidelidade().stream()
                .mapToInt(ponto -> ponto.getQuantidadeDisponivel())
                .sum();
                
        if (pontosTotais >= PONTOS_MINIMOS) {
            return valorOriginal.multiply(PERCENTUAL_DESCONTO).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String getDescricao() {
        return "Desconto por Fidelidade (10%)";
    }
} 