-- Atualiza o PDF genérico para um Edital Real e gigantesco do Concurso Nacional Unificado
UPDATE tb_andamento
SET link_documento = 'https://www.gov.br/mgi/pt-br/concurso-nacional-unificado/editais/bloco-1-infraestrutura-exatas-e-engenharia.pdf'
WHERE titulo = 'Edital de Abertura' OR link_documento LIKE '%w3.org%';

-- Adiciona andamento simulado para o concurso SEDES caso não possua
INSERT INTO tb_andamento (id, data_publicacao, titulo, link_documento, concurso_id, extraido_por_ia)
SELECT gen_random_uuid(), CURRENT_DATE, 'Edital de Abertura Publicado', 'https://www.gov.br/mgi/pt-br/concurso-nacional-unificado/editais/bloco-1-infraestrutura-exatas-e-engenharia.pdf', id, true
FROM tb_concurso WHERE titulo LIKE '%SEDES%'
AND NOT EXISTS (
    SELECT 1 FROM tb_andamento a WHERE a.concurso_id = tb_concurso.id
);

-- Substitui o mock simples por um Conteúdo Programático Massivo e Real para o Auditor do TCDF
UPDATE tb_cargo
SET conteudo_programatico = '[
    {"disciplina": "Língua Portuguesa", "topicos": ["Compreensão e intelecção de textos", "Tipologia textual", "Ortografia oficial", "Acentuação gráfica", "Classes de palavras", "Sintaxe da oração e do período", "Pontuação", "Concordância nominal e verbal", "Regência nominal e verbal"]},
    {"disciplina": "Direito Administrativo", "topicos": ["Estado, governo e administração pública", "Conceito, fontes e princípios do Direito Administrativo", "Organização administrativa da União", "Agentes públicos", "Poderes administrativos", "Atos administrativos", "Controle da administração pública", "Licitações e Contratos (Lei nº 14.133/2021)"]},
    {"disciplina": "Direito Constitucional", "topicos": ["Constituição: conceito, classificações, princípios fundamentais", "Direitos e garantias fundamentais", "Organização político-administrativa", "Administração pública", "Poder Legislativo", "Poder Executivo", "Poder Judiciário", "Funções essenciais à Justiça"]},
    {"disciplina": "Contabilidade Pública", "topicos": ["Conceito, objeto e regime", "Orçamento público", "Receita pública: categorias, estágios", "Despesa pública: categorias, estágios", "Restos a pagar", "Despesas de exercícios anteriores", "Suprimento de fundos", "Demonstrações Contábeis", "MCASP"]},
    {"disciplina": "Auditoria Governamental", "topicos": ["Conceitos, evolução e tipos de auditoria", "Normas internacionais de auditoria", "Planejamento de auditoria", "Técnicas de auditoria", "Risco de auditoria", "Evidências e papéis de trabalho", "Relatórios de auditoria", "Controle externo no Brasil"]}
]'
WHERE nome = 'Auditor de Controle Externo';
