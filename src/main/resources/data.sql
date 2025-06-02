-- Inserir filmes
INSERT INTO filme (id, titulo, genero, duracao_minutos, idioma, classificacao_etaria, sinopse, data_inicio_exibicao, data_fim_exibicao, nota_media_avaliacao, removido_da_programacao)
VALUES 
    ('11111111-1111-1111-1111-111111111111', 'O Poderoso Chefão', 'Drama', 175, 'Português', '16 anos', 'Uma família mafiosa luta para estabelecer sua supremacia nos Estados Unidos depois da Segunda Guerra Mundial.', CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', 4.8, false),
    ('22222222-2222-2222-2222-222222222222', 'Interestelar', 'Ficção Científica', 169, 'Português', '12 anos', 'Um grupo de astronautas viaja através de um buraco de minhoca no espaço em uma tentativa de garantir a sobrevivência da humanidade.', CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', 4.7, false),
    ('33333333-3333-3333-3333-333333333333', 'O Senhor dos Anéis', 'Fantasia', 178, 'Português', '12 anos', 'Um hobbit é encarregado de destruir um poderoso anel mágico.', CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', 4.9, false);

-- Inserir salas
INSERT INTO sala (id, nome, capacidade_total, tipo, disponivel_para_eventos)
VALUES 
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Sala 1', 100, 'SALA_2D', true),
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Sala 2', 80, 'SALA_3D', true),
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Sala 3', 120, 'SALA_2D', true);

-- Inserir sessões
INSERT INTO sessao (id, filme_id, sala_id, data_hora_inicio, preco_ingresso_base, status, tipo_exibicao)
VALUES 
    ('11111111-aaaa-1111-aaaa-111111111111', '11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', CURRENT_TIMESTAMP + INTERVAL '1 day', 30.00, 'PROGRAMADA', 'D2'),
    ('22222222-bbbb-2222-bbbb-222222222222', '22222222-2222-2222-2222-222222222222', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', CURRENT_TIMESTAMP + INTERVAL '2 days', 35.00, 'PROGRAMADA', 'D3'),
    ('33333333-cccc-3333-cccc-333333333333', '33333333-3333-3333-3333-333333333333', 'cccccccc-cccc-cccc-cccc-cccccccccccc', CURRENT_TIMESTAMP + INTERVAL '3 days', 30.00, 'PROGRAMADA', 'D2'); 