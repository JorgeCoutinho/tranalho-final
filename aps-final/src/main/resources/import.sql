INSERT INTO categoria (nome_categoria) VALUES ('Ensino');
INSERT INTO categoria (nome_categoria) VALUES ('Pesquisa');
INSERT INTO categoria (nome_categoria) VALUES ('Extensao');
INSERT INTO categoria (nome_categoria) VALUES ('Visita Tecnica');
INSERT INTO categoria (nome_categoria) VALUES ('Aula pratica externa');
INSERT INTO categoria (nome_categoria) VALUES ('Aula no laboratorio');
INSERT INTO categoria (nome_categoria) VALUES ('Outro');

INSERT INTO curso (nome) VALUES ('Curso de Programacao Completa');

INSERT INTO atividade (nome, objetivo, publico_alvo, publicada, data, curso_id, categoria_id) VALUES ('Introducao ao Java', 'Ensinar conceitos basicos de Java','Iniciantes', true, '2024-01-10', 1, 1);

INSERT INTO foto (url, legenda, atividade_id) VALUES ('https://example.com/foto1.jpg', 'Aula de Introducao ao Java', 1);
