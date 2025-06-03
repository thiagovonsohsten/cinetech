-- Tabela de Clientes
CREATE TABLE cliente (
    id UUID PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    perfil VARCHAR(50) NOT NULL
);

-- Tabela de Créditos de Compensação
CREATE TABLE credito_compensacao (
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    data_validade TIMESTAMP NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    sessao_id UUID,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

-- Tabela de Pontos de Fidelidade
CREATE TABLE ponto_fidelidade (
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL,
    pontos INTEGER NOT NULL,
    data_obtencao DATE NOT NULL,
    data_expiracao DATE NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

-- Tabela de Filmes
CREATE TABLE filme (
    id UUID PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    duracao INTEGER NOT NULL,
    classificacao VARCHAR(10) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    sinopse TEXT,
    data_lancamento DATE NOT NULL
);

-- Tabela de Salas
CREATE TABLE sala (
    id UUID PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    capacidade INTEGER NOT NULL,
    tipo VARCHAR(50) NOT NULL
);

-- Tabela de Sessões
CREATE TABLE sessao (
    id UUID PRIMARY KEY,
    filme_id UUID NOT NULL,
    sala_id UUID NOT NULL,
    data_hora_inicio TIMESTAMP NOT NULL,
    data_hora_fim TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (filme_id) REFERENCES filme(id),
    FOREIGN KEY (sala_id) REFERENCES sala(id)
);

-- Tabela de Ingressos
CREATE TABLE ingresso (
    id UUID PRIMARY KEY,
    sessao_id UUID NOT NULL,
    cliente_id UUID NOT NULL,
    valor_pago DECIMAL(10,2) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    data_compra TIMESTAMP NOT NULL,
    FOREIGN KEY (sessao_id) REFERENCES sessao(id),
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
); 