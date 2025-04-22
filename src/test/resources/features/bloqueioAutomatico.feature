Feature: Bloqueio automático de filmes após fim de exibição

  Scenario: Remover automaticamente filmes com tempo de exibição expirado
    Given que o tempo de exibição de um filme expirou
    When o sistema atualiza a grade
    Then o filme deve ser removido da programação

  Scenario: Manter filme na programação se ainda está em exibição
    Given que o tempo de exibição de um filme ainda não expirou
    When o sistema atualiza a grade
    Then o filme deve continuar na programação