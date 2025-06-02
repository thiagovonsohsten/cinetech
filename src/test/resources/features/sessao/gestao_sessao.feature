# language: pt
Funcionalidade: Gestão de Sessões
  Como um funcionário do cinema
  Eu quero gerenciar as sessões
  Para organizar as exibições dos filmes

  Cenário: Cadastro de nova sessão
    Dado que existe um filme cadastrado
    E que existe uma sala de cinema
    E que tenho os dados da nova sessão
    Quando eu cadastro a sessão no sistema
    Então o sistema deve criar um novo registro
    E a sessão deve receber um ID único
    E os assentos devem ser inicializados como disponíveis

  Cenário: Consulta de disponibilidade de assentos
    Dado que existe uma sessão cadastrada
    Quando eu consulto a disponibilidade de assentos
    Então o sistema deve mostrar o mapa de assentos
    E indicar quais assentos estão disponíveis
    E indicar quais assentos estão ocupados
    E indicar quais assentos estão reservados

  Cenário: Cancelamento de sessão
    Dado que existe uma sessão cadastrada
    E a sessão possui ingressos vendidos
    Quando eu cancelo a sessão
    Então o sistema deve marcar a sessão como cancelada
    E notificar os clientes com ingressos
    E gerar créditos de compensação
    E liberar os assentos ocupados

  Cenário: Tentativa de cadastro com conflito de horário
    Dado que existe uma sessão cadastrada
    Quando eu tento cadastrar uma nova sessão
    E há conflito de horário
    Então o sistema deve informar o conflito
    E não deve permitir o cadastro da nova sessão 