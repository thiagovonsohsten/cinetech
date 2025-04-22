Feature: Seleção de assento pelo cliente

   Scenario: Cliente escolhe assento ao comprar ingresso
    Given que o cliente acessou a página de compra de ingressos
    When ele seleciona uma sessão e escolhe um assento específico
    Then esse assento deve ser reservado para ele

   Scenario: Cliente escolhe um assento que já foi reservado
    Given que o assento "C5" de uma sessão específica em uma sala já especifica já foi reservado
    When um cliente tenta comprar ingresso para essa sessão
    Then o sistema deve exibir "Esse assento já está ocupado nessa sala para essa sessão, escolha outro assento"
