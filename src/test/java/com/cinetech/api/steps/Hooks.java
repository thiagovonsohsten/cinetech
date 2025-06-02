package com.cinetech.api.steps;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class Hooks {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void limparBancoDeDados() {
        // Limpar todas as tabelas antes de cada cenário
        jdbcTemplate.execute("DELETE FROM ingressos");
        jdbcTemplate.execute("DELETE FROM assentos");
        jdbcTemplate.execute("DELETE FROM sessoes");
        jdbcTemplate.execute("DELETE FROM clientes");
        jdbcTemplate.execute("DELETE FROM salas");
        jdbcTemplate.execute("DELETE FROM filmes");
        jdbcTemplate.execute("DELETE FROM promocoes");
        jdbcTemplate.execute("DELETE FROM pontos_fidelidade");
        jdbcTemplate.execute("DELETE FROM creditos_compensacao");
    }

    @After
    public void limparContexto() {
        // Limpar contexto após cada cenário
        jdbcTemplate.execute("DELETE FROM ingressos");
        jdbcTemplate.execute("DELETE FROM assentos");
        jdbcTemplate.execute("DELETE FROM sessoes");
        jdbcTemplate.execute("DELETE FROM clientes");
        jdbcTemplate.execute("DELETE FROM salas");
        jdbcTemplate.execute("DELETE FROM filmes");
        jdbcTemplate.execute("DELETE FROM promocoes");
        jdbcTemplate.execute("DELETE FROM pontos_fidelidade");
        jdbcTemplate.execute("DELETE FROM creditos_compensacao");
    }
} 