# language: pt
Funcionalidade: Gestão de Clientes
  Como um funcionário do cinema
  Eu quero gerenciar os clientes
  Para manter o cadastro atualizado e oferecer benefícios

  Cenário: Cadastro de novo cliente
    Dado que tenho os dados do novo cliente
    Quando eu cadastro o cliente no sistema
    Então o sistema deve criar um novo registro
    E o cliente deve receber um ID único
    E o perfil do cliente deve ser definido como normal

  Cenário: Atualização de perfil para estudante
    Dado que existe um cliente cadastrado
    Quando eu atualizo o perfil para estudante
    E eu envio o comprovante de estudante
    Então o sistema deve atualizar o perfil
    E o cliente deve ser elegível para meia entrada

  Cenário: Consulta de histórico de compras
    Dado que existe um cliente cadastrado
    E que o cliente possui compras
    Quando eu consulto o histórico de compras
    Então o sistema deve listar as compras
    E mostrar os detalhes de cada compra

  Cenário: Consulta de pontos de fidelidade
    Dado que existe um cliente cadastrado
    E que o cliente possui pontos de fidelidade
    Quando eu consulto os pontos de fidelidade
    Então o sistema deve mostrar o saldo atual
    E listar o histórico de pontos
    E indicar a data de expiração dos pontos 