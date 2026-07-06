# Concurseiro DF (v2.0)

Plataforma de inteligência de editais para centralizar, analisar e notificar estudantes sobre oportunidades de concursos públicos no Distrito Federal.
O sistema resolve o problema de fragmentação extraindo automaticamente o Conteúdo Programático dos editais através de IA e gerando **Guias de Estudo Inteligentes em PDF**.

## Arquitetura e Stack
- **Backend:** Java 21, Spring Boot 3.4+, Spring AI, PostgreSQL, RabbitMQ
- **Frontend:** Next.js, TypeScript, Tailwind CSS
- **Infraestrutura:** Docker, Docker Compose

## Estrutura do Repositório
- `backend/`: Código fonte do monólito Modular (API, Scraper, Processor, Notification).
- `frontend/`: Código fonte do portal web (Next.js).
- `docker-compose.yml`: Definição dos serviços locais (banco de dados e mensageria).

## 🚀 Como executar localmente (Ambiente de Desenvolvimento)

### 1. Subir a Infraestrutura (Banco de Dados e Mensageria)
O projeto depende do PostgreSQL e RabbitMQ, que estão configurados via Docker Compose.
```bash
# Na raiz do projeto, execute:
sudo docker compose up -d
```

### 2. Rodar o Backend (Spring Boot)
O backend cuidará da migração automática do banco de dados (Flyway) e inicializará a API.
```bash
cd backend
./mvnw clean spring-boot:run -Dspring-boot.run.profiles=local
```
*(O banco de dados local será populado automaticamente com os dados e editais mockados do TCDF e SEDES para você testar as funcionalidades).*

### 3. Rodar o Frontend (Next.js)
Abra um novo terminal e inicie a interface do usuário:
```bash
cd frontend
npm install
npm run dev
```
Acesse **http://localhost:3000** no seu navegador!

### 🧪 Executando os Testes Automatizados
O MVP possui uma bateria rigorosa de testes no backend e frontend.
- **Backend (JUnit/Mockito):** `cd backend && ./mvnw test`
- **Frontend (Jest/React Testing Library):** `cd frontend && npm run test`

### 📧 E-mails Simulados
Como estamos rodando localmente, os e-mails (como as notificações de boas-vindas) não são enviados para a internet. Em vez disso, o backend **gera um arquivo HTML simulando a caixa de entrada**.
Eles ficam salvos na pasta: `backend/emails_simulados/`. Abra esses arquivos no VS Code ou no Navegador para visualizar o layout responsivo do e-mail!

---
📚 **[Acesse a Documentação Completa (MkDocs)](http://localhost:8000)** - Instruções detalhadas de arquitetura, requisitos e testes!