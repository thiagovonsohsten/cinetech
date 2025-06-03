package com.cinetech.api.dominio.modelos.sessao;

public enum StatusSessao {
    PROGRAMADA,    // Sessão foi criada mas ainda não está aberta para vendas
    ABERTA,        // Sessão está aberta para vendas
    LOTADA,        // Todos os assentos vendáveis foram vendidos
    CANCELADA,     // Sessão foi cancelada
    FINALIZADA,    // Sessão já ocorreu
    EM_ANDAMENTO   // Sessão está em andamento
} 