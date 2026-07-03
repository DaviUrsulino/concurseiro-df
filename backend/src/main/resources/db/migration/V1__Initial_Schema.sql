CREATE TABLE tb_orgao (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    sigla VARCHAR(50),
    esfera VARCHAR(50)
);

CREATE TABLE tb_banca (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    site_oficial VARCHAR(255)
);

CREATE TABLE tb_concurso (
    id UUID PRIMARY KEY,
    orgao_id UUID NOT NULL,
    banca_id UUID,
    titulo VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    link_pagina_oficial VARCHAR(255),
    data_prova DATE,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_concurso_orgao FOREIGN KEY (orgao_id) REFERENCES tb_orgao(id),
    CONSTRAINT fk_concurso_banca FOREIGN KEY (banca_id) REFERENCES tb_banca(id)
);

CREATE TABLE tb_cargo (
    id UUID PRIMARY KEY,
    concurso_id UUID NOT NULL,
    nome VARCHAR(255) NOT NULL,
    nivel VARCHAR(50) NOT NULL,
    salario DECIMAL(10, 2),
    vagas_imediatas INT,
    vagas_cadastro_reserva INT,
    CONSTRAINT fk_cargo_concurso FOREIGN KEY (concurso_id) REFERENCES tb_concurso(id)
);

CREATE TABLE tb_andamento (
    id UUID PRIMARY KEY,
    concurso_id UUID NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    link_documento VARCHAR(255),
    data_publicacao DATE,
    extraido_por_ia BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_andamento_concurso FOREIGN KEY (concurso_id) REFERENCES tb_concurso(id)
);

CREATE TABLE tb_disciplina (
    id UUID PRIMARY KEY,
    cargo_id UUID NOT NULL,
    nome VARCHAR(255) NOT NULL,
    topicos_json TEXT,
    CONSTRAINT fk_disciplina_cargo FOREIGN KEY (cargo_id) REFERENCES tb_cargo(id)
);

-- Índices para otimização de consultas frequentes
CREATE INDEX idx_concurso_status ON tb_concurso(status);
CREATE INDEX idx_concurso_orgao ON tb_concurso(orgao_id);
CREATE INDEX idx_cargo_concurso ON tb_cargo(concurso_id);
CREATE INDEX idx_andamento_concurso ON tb_andamento(concurso_id);
