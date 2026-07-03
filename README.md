# Concurseiro DF (v2.0)

Plataforma de inteligência de editais para centralizar, analisar e notificar estudantes sobre oportunidades de concursos públicos no Distrito Federal.

## Arquitetura e Stack
- **Backend:** Java 21, Spring Boot 3.4+, Spring AI, PostgreSQL, RabbitMQ
- **Frontend:** Next.js, TypeScript, Tailwind CSS
- **Infraestrutura:** Docker, Docker Compose

## Estrutura do Repositório
- `backend/`: Código fonte do monólito Modular (API, Scraper, Processor, Notification).
- `frontend/`: Código fonte do portal web (Next.js).
- `docker-compose.yml`: Definição dos serviços locais (banco de dados e mensageria).

## Como executar localmente

1. **Subir a infraestrutura:**
   ```bash
   docker-compose up -d
   ```
2. **Rodar o backend:**
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```
3. **Rodar o frontend:**
   ```bash
   cd frontend
   npm run dev
   ```