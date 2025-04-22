package com.cinetech.stepdefs;

import static org.junit.jupiter.api.Assertions.*;

import com.cinetech.domain.model.Filme;

import io.cucumber.java.pt.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BloqueioAutomaticoSteps {

    private List<Filme> gradeDeFilmes;
    private Filme filmeExpirado;

    @Dado("que o tempo de exibição de um filme expirou")
    public void tempo_exibicao_expirado() {
        gradeDeFilmes = new ArrayList<>();

        filmeExpirado = new Filme();
        filmeExpirado.setTitulo("Matrix");
        filmeExpirado.setDataFimExibicao(LocalDate.now().minusDays(1)); // Já expirou

        gradeDeFilmes.add(filmeExpirado);
    }

    @Quando("o sistema atualiza a grade")
    public void sistema_atualiza_grade() {
        gradeDeFilmes.removeIf(filme -> filme.getDataFimExibicao().isBefore(LocalDate.now()));
    }

    @Então("o filme deve ser removido da programação")
    public void filme_removido_da_grade() {
        assertFalse(gradeDeFilmes.contains(filmeExpirado));
    }
    private Filme filmeNaoExpirado;

    //Cenário 2 
    @Dado("que o tempo de exibição de um filme ainda não expirou")
    public void tempo_exibicao_nao_expirado() {
        gradeDeFilmes = new ArrayList<>();

        filmeNaoExpirado = new Filme();
        filmeNaoExpirado.setTitulo("Vingadores");
        filmeNaoExpirado.setDataFimExibicao(LocalDate.now().plusDays(5)); // Ainda em exibição

        gradeDeFilmes.add(filmeNaoExpirado);
    }

    @Então("o filme deve continuar na programação")
    public void filme_deve_continuar_na_grade() {
        assertTrue(gradeDeFilmes.contains(filmeNaoExpirado));
    }
}
