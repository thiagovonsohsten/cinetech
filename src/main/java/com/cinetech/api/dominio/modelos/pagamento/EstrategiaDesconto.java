package com.cinetech.api.dominio.modelos.pagamento;

import com.cinetech.api.dominio.modelos.cliente.Cliente;
import java.math.BigDecimal;
 
public interface EstrategiaDesconto {
    BigDecimal calcularDesconto(BigDecimal valorOriginal, Cliente cliente);
    String getDescricao();
} 