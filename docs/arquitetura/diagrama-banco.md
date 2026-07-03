# Banco de Dados

## Diagrama Entidade-Relacionamento (ER)

O banco de dados relacional (PostgreSQL) foi modelado para garantir integridade e normalização em todas as instâncias da vida de um edital de concurso. 

As entidades principais e seus relacionamentos são os seguintes:

```mermaid
erDiagram
    Orgao {
        UUID id PK
        String nome
        String sigla
        String esfera
    }
    Banca {
        UUID id PK
        String nome
        String site_oficial
    }
    Concurso {
        UUID id PK
        UUID orgao_id FK
        UUID banca_id FK
        String titulo
        Enum status
        String link_pagina_oficial
        Date data_prova
        Timestamp criado_em
    }
    Cargo {
        UUID id PK
        UUID concurso_id FK
        String nome
        Enum nivel
        Decimal salario
        Integer vagas_imediatas
        Integer vagas_cadastro_reserva
    }
    Andamento {
        UUID id PK
        UUID concurso_id FK
        String titulo
        Text descricao
        String link_documento
        Date data_publicacao
        Boolean extraido_por_ia
    }
    Disciplina {
        UUID id PK
        UUID cargo_id FK
        String nome
        JSONB topicos_json
    }

    Orgao ||--o{ Concurso : realiza
    Banca ||--o{ Concurso : organiza
    Concurso ||--o{ Cargo : contem
    Concurso ||--o{ Andamento : possui
    Cargo ||--o{ Disciplina : exige
```

## Dicionário de Dados
- **Órgão:** A entidade governamental que contrata (Ex: SEDF, Polícia Civil).
- **Banca:** A organizadora do certame (Ex: Cebraspe, FGV).
- **Concurso:** O agrupador principal (O "Edital" em si).
- **Cargo:** Específico dentro do concurso (Ex: Professor de Matemática, Agente).
- **Andamento:** As atualizações que o Scraper capta (Gabarito, Retificações).
- **Disciplina:** Conteúdo cobrado, extraído pela Inteligência Artificial.
