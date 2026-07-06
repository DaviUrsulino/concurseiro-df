# Status de Implementação (RFs)

Abaixo apresentamos o detalhamento completo do status de todos os Requisitos Funcionais (RFs) planejados para o sistema **Concurseiro DF**. 

Esta página serve para validar a coesão da aplicação e destrinchar exatamente como cada RF está operando no momento.

## Visão Geral dos RFs

| ID | Requisito Funcional | Status | Descrição da Implementação e Evidências |
|---|---|---|---|
| **RF01** | Listagem e Filtro de Concursos | ✅ Concluído | **Onde ver:** Na página inicial (`/`).<br>**Como funciona:** O Frontend consome a rota `/api/v1/concursos` e exibe em formato de cards. Existe uma barra de busca interativa que filtra os concursos pelo título ou órgão instantaneamente via *React State*. |
| **RF02** | Detalhes do Concurso e Cargos | ✅ Concluído | **Onde ver:** Rota `/concurso/[id]`.<br>**Como funciona:** Ao clicar em um card na Home, o usuário é direcionado para a página de detalhes, onde são exibidas as informações do órgão, banca, data de prova e uma listagem clara de todos os Cargos com salários formatados. |
| **RF03** | Extração de Conteúdo Programático | ✅ Concluído | **Onde ver:** Dentro de um Cargo, na tela de detalhes, e no Dashboard de Estudos.<br>**Como funciona:** O banco de dados armazena os tópicos exigidos por edital no formato JSON. Atualmente, os dados são mockados durante o Scraper Batch, mas a arquitetura do BD (coluna `conteudo_programatico`) já suporta a estrutura aninhada de *Disciplina* -> *Tópicos*. |
| **RF04** | Geração de Guia de Estudos / Dashboard | ✅ Evoluído | **Onde ver:** Rota `/concurso/[id]/estudar/[cargoId]`.<br>**Como funciona:** Inicialmente planejado como uma geração estática de PDF, o requisito evoluiu para um **Dashboard de Estudos Interativo**. Ao clicar no botão *"Acessar Dashboard de Estudos"*, o usuário tem acesso a checkboxes para gerenciar seu estudo tópico a tópico, com uma progress bar em tempo real. |
| **RF05** | Scraper de Andamentos e Editais | ✅ Concluído | **Onde ver:** Timeline do Edital (`/concurso/[id]`).<br>**Como funciona:** Existe um Job do Spring Batch (`ScraperJobConfig`) configurado no backend que, ao executar, realiza *parsing* de links e salva instâncias de `Concurso` e `Andamento`. Os links de PDF são tratados e disponibilizados para visualização. |
| **RF06** | IA para Estruturação (Conteúdo Bruto) | 🚧 Parcial | **Onde ver:** Tag *"Lido por IA"*. <br>**Como funciona:** A infraestrutura e a modelagem do banco (flag `extraido_por_ia`) estão concluídas. As extrações automáticas reais via LLM ainda necessitam ser acopladas à Pipeline do Batch na Fase 5. Atualmente o sistema exibe os badges de IA e renderiza a saída com sucesso. |
| **RF07** | Bloqueio de Operações de Escrita | ✅ Concluído | **Onde ver:** Backend Security Filter.<br>**Como funciona:** Foi implementada uma cadeia de segurança rigorosa (`WebSecurityConfig`) que exige roles administrativos para qualquer método `POST`, `PUT` ou `DELETE` em entidades fundamentais, prevenindo poluição de dados. |
| **RF08** | Sistema de Autenticação com JWT | ✅ Concluído | **Onde ver:** Rotas `/login` e `/cadastro`.<br>**Como funciona:** A camada `Auth` possui senhas encriptadas com BCrypt. O login devolve um *Bearer Token* (JWT) assinado. O Frontend armazena esse token no LocalStorage e repassa via cabeçalho `Authorization` do Axios de forma invisível para o usuário, controlando as rotas com *React Context*. Erros como "E-mail já existente" (`DataIntegrityViolation`) são tratados para mensagens amigáveis na UI. |
| **RF09** | Smart Matcher (Filtro Inteligente) | ✅ Concluído | **Onde ver:** Aba "Smart Matcher" na Home.<br>**Como funciona:** O usuário configura seu Nível de Escolaridade e Pretensão Salarial Mínima. O Backend compara isso dinamicamente no método `findRecomendados()` retornando apenas editais 100% aderentes. Possui um Empty State (estado vazio) responsivo e Premium para quando não houver match perfeito. |
| **RF10** | Progresso Salvo em Tempo Real | ✅ Concluído | **Onde ver:** Dashboard de Estudos Interativo.<br>**Como funciona:** Ao marcar um checkbox no dashboard, a aplicação emite uma request `PUT /api/v1/progresso` transparente. Caso feche o navegador e retorne no futuro, os checkboxes do estudante permanecem exatamente como estavam graças à persistência no banco relacional `tb_progresso`. |

---
*Documento atualizado automaticamente de acordo com as versões mais recentes do projeto.*
