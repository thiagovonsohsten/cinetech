# language: pt
Funcionalidade: Compra de Ingressos
  Como um cliente do cinema
  Eu quero comprar ingressos para uma sessão
  Para que eu possa assistir ao filme

  Cenário: Compra de ingressos com sucesso
    Dado que existe um cliente cadastrado
    E que existe uma sessão disponível
    E que existem assentos disponíveis
    Quando eu seleciono os assentos desejados
    E eu escolho o tipo de ingresso
    E eu confirmo a compra
    Então o sistema deve reservar os assentos
    E gerar os ingressos
    E calcular o valor total
    E enviar o comprovante por email

  Cenário: Compra de ingressos com pontos de fidelidade
    Dado que existe um cliente cadastrado
    E que existe uma sessão disponível
    E que existem assentos disponíveis
    E que o cliente possui pontos de fidelidade suficientes
    Quando eu seleciono os assentos desejados
    E eu escolho o tipo de ingresso
    E eu aplico os pontos de fidelidade
    E eu confirmo a compra
    Então o sistema deve reservar os assentos
    E gerar os ingressos
    E calcular o valor total
    E aplicar o desconto dos pontos
    E gerar novos pontos de fidelidade
    E enviar o comprovante por email

  Cenário: Tentativa de compra sem assentos disponíveis
    Dado que existe um cliente cadastrado
    E que existe uma sessão disponível
    E que não existem assentos disponíveis
    Quando eu seleciono os assentos desejados
    E eu escolho o tipo de ingresso
    E eu confirmo a compra
    Então o sistema deve informar que não há assentos disponíveis
    E não deve permitir a compra

  Cenário: Falha no pagamento com cartão
    Dado que existe um cliente cadastrado
    E que existe uma sessão disponível
    E que existem assentos disponíveis
    Quando eu seleciono os assentos desejados
    E eu escolho o tipo de ingresso
    E o pagamento com cartão falha
    Então o sistema deve liberar os assentos reservados
    E informar o erro de pagamento
    E não deve gerar os ingressos

  Cenário: Cancelamento de compra antes da confirmação
    Dado que existe um cliente cadastrado
    E que existe uma sessão disponível
    E que existem assentos disponíveis
    Quando eu seleciono os assentos desejados
    E eu escolho o tipo de ingresso
    E eu cancelo a compra
    Então o sistema deve liberar os assentos reservados
    E não deve gerar os ingressos
    E não deve processar nenhum pagamento

  Cenário: Compra com promoção aplicada
    Dado que existe um cliente cadastrado
    E que existe uma sessão disponível
    E que existem assentos disponíveis
    E que existe uma promoção válida
    Quando eu seleciono os assentos desejados
    E eu escolho o tipo de ingresso
    E eu aplico a promoção
    E eu confirmo a compra
    Então o sistema deve reservar os assentos
    E gerar os ingressos
    E aplicar o desconto da promoção
    E calcular o valor total com desconto
    E enviar o comprovante por email

  Cenário: Tentativa de compra com promoção expirada
    Dado que existe um cliente cadastrado
    E que existe uma sessão disponível
    E que existem assentos disponíveis
    E que existe uma promoção expirada
    Quando eu seleciono os assentos desejados
    E eu escolho o tipo de ingresso
    E eu tento aplicar a promoção
    Então o sistema deve informar que a promoção está expirada
    E não deve aplicar o desconto
    E deve manter o valor original 