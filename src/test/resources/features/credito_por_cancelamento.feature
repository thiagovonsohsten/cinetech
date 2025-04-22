Feature: Emissão de crédito por sessão cancelada

  Scenario: Emitir crédito por sessão cancelada
    Given que uma sessão foi cancelada pelo cinema
    When o sistema processa os ingressos comprados
    Then cada cliente deve receber um crédito proporcional

  Scenario: Não emitir crédito se a sessão não foi cancelada
    Given que uma sessão não foi cancelada
    When o sistema processa os ingressos comprados
    Then os clientes não devem receber crédito
