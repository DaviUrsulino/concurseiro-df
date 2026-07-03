# Arquitetura do Backend

A API do Concurseiro DF foi construída baseada no ecossistema Spring (Java 21).

## Tecnologias Core
- **Spring Boot 3.4.1:** Framework base (MVC).
- **Spring Data JPA & Hibernate:** ORM para persistência no PostgreSQL.
- **Spring Security:** Autenticação e autorização (Stateless API).
- **Spring Batch:** Job processor para rotinas pesadas (Web Scraper).
- **Spring AI:** Integração com OpenAI para extração estruturada de dados de editais brutos em JSON.
- **Flyway:** Versionamento de banco de dados (`V1__Initial_Schema.sql`).

## Qualidade e Integração Contínua
A arquitetura conta com uma pipeline rigorosa via **GitHub Actions** (`.github/workflows/backend-ci.yml`), que garante:
1. Checagem de tipagem e compilação do Maven a cada commit.
2. Execução da suíte de testes unitários (`ConcursoServiceTest`) e integração com banco em memória (H2 Database).

O Backend opera puramente como uma API REST, retornando DTOs (Data Transfer Objects) e capturando erros globais via `@RestControllerAdvice`, garantindo que o Frontend (Next.js) nunca receba *stacktraces* sujos de Java.
