Feature: Sessão Lotada

  Como cliente
  Quero que o sistema impeça reservas em sessões sem assentos disponíveis
  Para garantir que sessões lotadas não sejam reservadas indevidamente

  Scenario: Marcar sessão como lotada quando todos os assentos estiverem ocupados
    Given uma sessão com todos os assentos ocupados
    When o sistema verifica a lotação da sessão
    Then a sessão deve ser marcada como LOTADA

  Scenario: Sessão com assentos livres continua disponível
    Given uma sessão com ao menos um assento livre
    When o sistema verifica a lotação da sessão
    Then a sessão deve permanecer com status DISPONIVEL
