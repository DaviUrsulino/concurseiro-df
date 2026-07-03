# Matriz de Rastreabilidade

A Matriz de Rastreabilidade (Traceability Matrix) garante que nenhum requisito foi construído "à toa". Ela liga os objetivos de negócio aos requisitos (Funcionais e Não Funcionais) e, em seguida, aos módulos da arquitetura de software implementada.

| Requisito | Origem / Objetivo Associado | Módulo/Classe Implementada (Arquitetura) |
|---|---|---|
| **RF01** (Filtrar Concursos) | Centralização de Informações | `ConcursoController.findAll(status)` e `ConcursoRepository` |
| **RF02** (Detalhes do Concurso) | Centralização de Informações | `ConcursoController.findById(id)` e Relacionamentos JPA |
| **RF03** (Exibir Disciplinas) | Estruturação e Inteligência | `CargoController.getDisciplinas()` |
| **RF04** (Scraper de Editais) | Notificação e Acompanhamento | `ScraperJobConfig.java` (Spring Batch) |
| **RF05** (Extração por IA) | Estruturação e Inteligência | `EditalAiService.java` (Spring AI) |
| **RF06** (Segurança) | Proteção do Sistema | `WebSecurityConfig.java` |
| **RNF03** (Acessibilidade IHC) | Inclusão de Usuários | Diretrizes na pasta `frontend/` (A construir) |
| **RNF05** (Testes) | Estabilidade | `ConcursoServiceTest.java` e H2 Database |
| **RNF06** (Pipeline CI/CD) | Governança de Código | `.github/workflows/backend-ci.yml` |

---

> Essa rastreabilidade comprova que todas as dores mapeadas na *Visão do Produto* possuem uma correspondência técnica direta no código-fonte do Backend já construído.
