-- Adiciona conteudo_programatico na tabela Cargo
ALTER TABLE tb_cargo ADD COLUMN conteudo_programatico TEXT;

-- Remove a tabela Disciplina que se tornou obsoleta
DROP TABLE IF EXISTS tb_disciplina;
