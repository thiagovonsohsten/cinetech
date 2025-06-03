package com.cinetech.api.dominio.modelos.pagamento;

import com.cinetech.api.dominio.modelos.cliente.Cliente;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DescontoPromocao implements EstrategiaDesconto {
    private static final BigDecimal PERCENTUAL_DESCONTO = new BigDecimal("0.20"); // 20% de desconto

    @Override
    public BigDecimal calcularDesconto(BigDecimal valorOriginal, Cliente cliente) {
        LocalDateTime agora = LocalDateTime.now();
        // Aplica desconto nas segundas e terças
        if (agora.getDayOfWeek() == DayOfWeek.MONDAY || agora.getDayOfWeek() == DayOfWeek.TUESDAY) {
            return valorOriginal.multiply(PERCENTUAL_DESCONTO).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    @Override
    public String getDescricao() {
        return "Desconto Promocional (20%)";
    }
} 