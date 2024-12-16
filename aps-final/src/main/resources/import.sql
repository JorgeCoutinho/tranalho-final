-- INSERINDO UMA CATEGORIA
INSERT INTO categoria (nome_categoria) VALUES ('Categoria de Programacao');

-- INSERINDO UM CURSO
INSERT INTO curso (nome) VALUES ('Curso de Programacao Completa');

-- INSERINDO UMA ATIVIDADE RELACIONADA AO CURSO E À CATEGORIA
INSERT INTO atividade (nome, objetivo, publico_alvo, publicada, data, curso_id, categoria_id) VALUES ('Introducao ao Java', 'Ensinar conceitos basicos de Java','Iniciantes', true, '2024-01-10', 1, 1);

-- INSERINDO UMA FOTO RELACIONADA À ATIVIDADE
INSERT INTO foto (url, legenda, atividade_id) VALUES ('https://example.com/foto1.jpg', 'Aula de Introducao ao Java', 1);
