-- Adiciona a coluna permitindo valores nulos
ALTER TABLE cliente ADD COLUMN data_nascimento date;

-- Atualiza registros existentes com uma data padrão
UPDATE cliente SET data_nascimento = '2000-01-01' WHERE data_nascimento IS NULL;

-- Torna a coluna não nula
ALTER TABLE cliente ALTER COLUMN data_nascimento SET NOT NULL; 