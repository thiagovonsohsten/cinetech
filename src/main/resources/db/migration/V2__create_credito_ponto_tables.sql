-- Criar tabela de crédito de compensação
CREATE TABLE credito_compensacao (
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL,
    valor_original DECIMAL(10,2) NOT NULL,
    valor_utilizado DECIMAL(10,2) NOT NULL DEFAULT 0,
    data_emissao TIMESTAMP NOT NULL,
    data_validade TIMESTAMP,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    motivo VARCHAR(255) NOT NULL,
    sessao_origem_id UUID,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

-- Criar tabela de pontos de fidelidade
CREATE TABLE ponto_fidelidade (
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL,
    quantidade_original INTEGER NOT NULL,
    quantidade_utilizada INTEGER NOT NULL DEFAULT 0,
    data_aquisicao TIMESTAMP NOT NULL,
    data_expiracao TIMESTAMP,
    ingresso_origem_id UUID,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
); 