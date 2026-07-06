# Quality Assurance (Testes Automatizados)

Esta página documenta a estratégia e cobertura de testes automatizados implementada no **Concurseiro DF**. Como um projeto MVP com escopo escalável, priorizamos testes unitários e de integração essenciais para garantir a robustez das funcionalidades centrais.

---

## 🛠 Backend (Spring Boot)

Utilizamos a stack padrão de testes do ecossistema Spring:
- **JUnit 5** (Engine principal)
- **Mockito** (Mocking e Stubbing)
- **Spring Boot Test** (Contexto e Integração)

### Cobertura do Backend
A bateria do backend foca em isolar a lógica de negócios e as regras de serviço:

1. **`UsuarioServiceTest`**
   - Valida a criação de usuários e a geração de exceções em caso de duplicação de e-mail.
   - Testa a busca segura por e-mail, simulando cenários felizes e erros de *not found*.

2. **`JwtServiceTest`**
   - Testa os algoritmos de geração de Tokens JWT.
   - Valida extração de *Claims* (ex: nome de usuário) e detecção de tokens expirados.

3. **`ProgressoServiceTest`**
   - O coração do "Dashboard de Estudos" (Jornada).
   - Testa a marcação e desmarcação dinâmica de tópicos estudados em `json`.
   - Simula cenários onde o usuário tenta interagir com progresso de um cargo inexistente.

**Como Rodar os Testes no Backend:**
Na raiz da pasta `/backend`, execute:
```bash
./mvnw clean test
```

---

## 🎨 Frontend (Next.js)

Utilizamos a stack moderna recomendada para projetos React:
- **Jest** (Test Runner)
- **React Testing Library - RTL** (Renderização e simulação de usuário)

### Cobertura do Frontend
O foco está na experiência do usuário, garantindo que componentes chave se comportem bem perante cenários variados.

1. **Testes de Autenticação (`useAuth.test.tsx`)**
   - Testa todo o ciclo de vida do *Hook* global de Auth.
   - Validação de Mocked Login com sucesso e injeção do objeto do usuário na sessão (Zustand/Contexto).
   - Validação de Login com credenciais inválidas.
   - Ação de Logout e limpeza de estado.

2. **Testes de Visualização (Ex: `Dashboard.test.tsx` e `PDFViewer.test.tsx`)**
   - Valida se componentes reagem bem a ausência de dados (Loading states).
   - Confirma a renderização correta de layouts de grade de estudos e visualizadores condicionados.

**Como Rodar os Testes no Frontend:**
Na raiz da pasta `/frontend`, execute:
```bash
npm run test
```

---

*Nota: Em fases futuras do produto, incluiremos testes End-to-End (E2E) com o Cypress e cobertura completa de CI/CD via GitHub Actions.*
