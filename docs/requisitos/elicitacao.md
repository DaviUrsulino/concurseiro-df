# Elicitação e Modelagem de Requisitos

Abaixo estão definidos os requisitos e regras de negócio essenciais para o funcionamento do sistema.

## 1. Requisitos Funcionais (RFs)

Os Requisitos Funcionais descrevem o que o sistema **deve fazer**.

| ID | Descrição | Prioridade |
|---|---|---|
| **RF01** | O sistema deve listar todos os concursos cadastrados, permitindo filtragem por *status* (Aberto, Previsto, Finalizado). | Alta |
| **RF02** | O sistema deve exibir os detalhes de um concurso específico, incluindo Órgão, Banca, Data da Prova e Cargos disponíveis. | Alta |
| **RF03** | O sistema deve apresentar as **Disciplinas** e **Tópicos** exigidos para cada Cargo específico de um concurso. | Alta |
| **RF04** | O sistema deve possuir uma rotina (Scraper) capaz de coletar editais e andamentos automaticamente em fontes externas. | Média |
| **RF05** | O sistema deve utilizar Inteligência Artificial para extrair as disciplinas de um texto bruto e cadastrá-las no banco de dados. | Média |
| **RF06** | O sistema deve bloquear operações de escrita (criação, edição e exclusão) para usuários não autenticados como administradores. | Alta |

## 2. Requisitos Não Funcionais (RNFs)

Os Requisitos Não Funcionais definem as **restrições e atributos de qualidade** do sistema.

| ID | Descrição | Categoria |
|---|---|---|
| **RNF01** | O Backend deve ser desenvolvido utilizando Java 21 e Spring Boot 3+. | Tecnológica |
| **RNF02** | O Frontend deve ser desenvolvido em Next.js (React) e TailwindCSS. | Tecnológica |
| **RNF03** | A interface gráfica deve seguir o padrão WCAG 2.1 nível AA para acessibilidade. | Usabilidade (IHC) |
| **RNF04** | O sistema deve utilizar PostgreSQL como banco de dados relacional. | Persistência |
| **RNF05** | O código-fonte deve estar coberto por testes unitários na camada de serviço (backend). | Confiabilidade |
| **RNF06** | O deploy e testes devem ser automatizados por meio de pipelines CI/CD (GitHub Actions). | Manutenibilidade |

## 3. Casos de Uso (UCs)

Abaixo estão estruturados os principais cenários de uso da aplicação.

### UC01 - Visualizar Editais Abertos
- **Ator:** Estudante (Usuário Público)
- **Pré-condição:** Nenhuma.
- **Fluxo Principal:**
  1. O Estudante acessa a página inicial da plataforma.
  2. O sistema solicita ao backend a lista de concursos com status `ABERTO`.
  3. O sistema renderiza a lista na tela em formato de *cards*.
  4. O Estudante clica em um concurso para ver mais detalhes (Cargos e Salários).

### UC02 - Extração de Disciplinas via IA
- **Ator:** Sistema (Job em Background) ou Administrador
- **Pré-condição:** Um edital ter sido coletado pelo Scraper.
- **Fluxo Principal:**
  1. O Job envia o texto bruto do edital para o `EditalAiService`.
  2. O serviço se comunica com a OpenAI utilizando um *System Prompt* restritivo.
  3. A IA retorna os dados em formato JSON.
  4. O sistema vincula as Disciplinas ao Cargo no banco de dados.
