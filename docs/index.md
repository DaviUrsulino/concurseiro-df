# Bem-vindo ao Concurseiro DF

O **Concurseiro DF** é uma plataforma de inteligência de editais desenvolvida para centralizar, analisar e notificar estudantes sobre oportunidades de concursos públicos no Distrito Federal (com escalabilidade para nível nacional).

Diferente de um simples agregador, o sistema atua como uma ferramenta de **Decision Support System (DSS)**, utilizando Inteligência Artificial para transformar editais densos em planos de estudo estruturados, permitindo que os candidatos otimizem seu tempo.

---

## Estrutura da Documentação

Neste portal, você encontrará toda a base de conhecimento do projeto, dividida nas seguintes áreas:

1. **[Engenharia de Requisitos](requisitos/visao-produto.md):** Onde definimos o problema, os objetivos de negócio, requisitos funcionais (RFs) e não funcionais (RNFs), casos de uso e a matriz de rastreabilidade.
2. **[Arquitetura de Software](arquitetura/backend.md):** Como o sistema foi modelado, diagramas de banco de dados e as decisões técnicas do Backend (Spring Boot, RabbitMQ, PostgreSQL).
3. **[Frontend e Acessibilidade (IHC)](frontend/acessibilidade.md):** As diretrizes de Interação Humano-Computador que norteiam a construção das telas no Next.js.
4. **[NOVO] [Bateria de Testes (QA)](testes.md):** Documentação oficial da nossa cobertura de testes de Frontend (Jest/RTL) e Backend (JUnit/Mockito).

---

## 🎯 Entregas do MVP Atual (Versão 2.0)
O MVP atingiu sua maturidade e conta com as seguintes features prontas para uso:
- **Autenticação Simples:** Login e cadastro simulado salvando no banco de dados.
- **Smart Matcher:** Filtro inteligente de concursos recomendados baseados no perfil do usuário (escolaridade e pretensão salarial).
- **Dashboard / Jornada de Estudos:** Acompanhamento do progresso nos tópicos do edital, com cálculo de porcentagem por disciplina.
- **E-mails Simulados (Caixa de Entrada Local):** Notificações de boas-vindas e alertas de novos concursos enviadas localmente e visíveis na pasta de e-mails simulados do backend.
- **Integração com Editais Reais:** O banco local já inicializa contendo concursos com links e PDF oficiais (ex: CNU) extraídos com IA simulada.
