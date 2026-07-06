CREATE TABLE tb_usuario (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nivel_escolaridade VARCHAR(255),
    pretensao_salarial NUMERIC(19, 2)
);

CREATE TABLE tb_progresso (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL REFERENCES tb_usuario(id) ON DELETE CASCADE,
    cargo_id UUID NOT NULL REFERENCES tb_cargo(id) ON DELETE CASCADE,
    topicos_concluidos_json TEXT,
    UNIQUE(usuario_id, cargo_id)
);
