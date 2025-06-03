-- Criar tabela de cliente
CREATE TABLE cliente (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    data_nascimento DATE NOT NULL,
    perfil VARCHAR(20) NOT NULL
);

-- Criar tabela de filmes
CREATE TABLE filme (
    id UUID PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    duracao_minutos INTEGER NOT NULL,
    data_lancamento DATE NOT NULL,
    data_encerramento DATE,
    media_avaliacao DECIMAL(3,2),
    total_avaliacoes INTEGER DEFAULT 0,
    removido BOOLEAN DEFAULT FALSE
);

-- Criar tabela de salas
CREATE TABLE sala (
    id UUID PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    capacidade INTEGER NOT NULL,
    tipo VARCHAR(20) NOT NULL
);

-- Criar tabela de sessões
CREATE TABLE sessao (
    id UUID PRIMARY KEY,
    filme_id UUID NOT NULL,
    sala_id UUID NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (filme_id) REFERENCES filme(id),
    FOREIGN KEY (sala_id) REFERENCES sala(id)
);

-- Criar tabela de assentos
CREATE TABLE assento (
    id UUID PRIMARY KEY,
    sessao_id UUID NOT NULL,
    numero VARCHAR(10) NOT NULL,
    disponivel BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (sessao_id) REFERENCES sessao(id)
); 