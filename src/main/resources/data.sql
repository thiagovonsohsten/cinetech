-- Limpar tabelas (opcional, só para ambiente de desenvolvimento)
TRUNCATE TABLE sessao CASCADE;
TRUNCATE TABLE sala CASCADE;
TRUNCATE TABLE filme CASCADE;

-- Inserir filmes na ordem correta das colunas
INSERT INTO filme (
  id, titulo, genero, duracao_minutos, idioma, classificacao_etaria, sinopse, data_inicio_exibicao, data_fim_exibicao, nota_media_avaliacao, removido_da_programacao
) VALUES
  ('11111111-1111-1111-1111-111111111111', 'O Poderoso Chefão', 'Drama', 175, 'Português', '16 anos', 'Uma família mafiosa luta para estabelecer sua supremacia nos Estados Unidos depois da Segunda Guerra Mundial.', CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', 4.8, false),
  ('22222222-2222-2222-2222-222222222222', 'Interestelar', 'Ficção Científica', 169, 'Português', '12 anos', 'Um grupo de astronautas viaja através de um buraco de minhoca no espaço em uma tentativa de garantir a sobrevivência da humanidade.', CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', 4.7, false),
  ('33333333-3333-3333-3333-333333333333', 'O Senhor dos Anéis', 'Fantasia', 178, 'Português', '12 anos', 'Um hobbit é encarregado de destruir um poderoso anel mágico.', CURRENT_DATE, CURRENT_DATE + INTERVAL '30 days', 4.9, false)
ON CONFLICT (id) DO NOTHING;

-- Inserir salas
INSERT INTO sala (id, capacidade_total, disponivel_para_eventos, nome, tipo)
VALUES
  ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 100, true, 'Sala 1', 'SALA_2D'),
  ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 80, true, 'Sala 2', 'SALA_3D'),
  ('cccccccc-cccc-cccc-cccc-cccccccccccc', 120, true, 'Sala 3', 'SALA_2D')
ON CONFLICT (id) DO NOTHING;

-- Inserir sessões
INSERT INTO sessao (id, data_hora_inicio, preco_ingresso_base, status, tipo_exibicao, filme_id, sala_id)
VALUES
  ('11111111-aaaa-1111-aaaa-111111111111', CURRENT_TIMESTAMP + INTERVAL '1 day', 30.00, 'PROGRAMADA', 'D2', '11111111-1111-1111-1111-111111111111', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
  ('22222222-bbbb-2222-bbbb-222222222222', CURRENT_TIMESTAMP + INTERVAL '2 days', 35.00, 'PROGRAMADA', 'D3', '22222222-2222-2222-2222-222222222222', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
  ('33333333-cccc-3333-cccc-333333333333', CURRENT_TIMESTAMP + INTERVAL '3 days', 30.00, 'PROGRAMADA', 'D2', '33333333-3333-3333-3333-333333333333', 'cccccccc-cccc-cccc-cccc-cccccccccccc')
ON CONFLICT (id) DO NOTHING; 