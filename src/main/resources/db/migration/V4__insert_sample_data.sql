-- Inserir filmes
INSERT INTO filme (id, titulo, genero, duracao_minutos, idioma, classificacao_etaria, sinopse, data_inicio_exibicao, data_fim_exibicao, nota_media_avaliacao, removido_da_programacao)
VALUES 
    ('11111111-1111-1111-1111-111111111111', 'O Poderoso Chefão', 'DRAMA', 175, 'Português', '16 anos', 'A história da família Corleone', '2024-06-01', '2024-07-01', 0.0, false),
    ('22222222-2222-2222-2222-222222222222', 'Interestelar', 'FICCAO_CIENTIFICA', 169, 'Português', '12 anos', 'Uma jornada pelo espaço e tempo', '2024-06-01', '2024-07-01', 0.0, false);

-- Inserir salas
INSERT INTO sala (id, nome, capacidade, tipo_exibicao)
VALUES 
    ('33333333-3333-3333-3333-333333333333', 'Sala 1', 100, 'NORMAL'),
    ('44444444-4444-4444-4444-444444444444', 'Sala 2', 80, '3D');

-- Inserir sessões
INSERT INTO sessao (id, filme_id, sala_id, data_hora_inicio, tipo_exibicao, preco_base_ingresso, status)
VALUES 
    ('55555555-5555-5555-5555-555555555555', '11111111-1111-1111-1111-111111111111', '33333333-3333-3333-3333-333333333333', '2024-06-15 19:00:00', 'NORMAL', 30.00, 'PROGRAMADA'),
    ('66666666-6666-6666-6666-666666666666', '22222222-2222-2222-2222-222222222222', '44444444-4444-4444-4444-444444444444', '2024-06-15 20:00:00', '3D', 40.00, 'PROGRAMADA'); 