# Visão do Produto

## 1. O Problema
Atualmente, concurseiros enfrentam uma enorme fragmentação de informações. Editais são publicados em diários oficiais densos (como o DODF) ou em sites de bancas organizadoras com layouts confusos e PDFs com centenas de páginas. 

Para um estudante, descobrir quais tópicos exatos estudar para o cargo X ou Y exige um trabalho manual exaustivo de garimpo e leitura de anexos complexos. Isso resulta em perda de tempo precioso que deveria ser dedicado aos estudos.

## 2. Objetivos
O **Concurseiro DF** visa resolver esse problema atuando como um agregador inteligente. Os principais objetivos do negócio são:
- **Centralização:** Reunir informações de diferentes órgãos e bancas em uma única interface clara e padronizada.
- **Inteligência e Estruturação:** Utilizar Inteligência Artificial para extrair automaticamente o "Conteúdo Programático" dos editais (em PDF ou HTML) e transformá-los em listas de disciplinas e tópicos estruturados, permitindo que o aluno **Gere Guias de Estudos Inteligentes em PDF** com apenas um clique. *(Status: Implementado)*
- **Notificação e Acompanhamento:** Permitir que o usuário acompanhe o "Andamento" dos concursos (ex: "Saiu o gabarito", "Edital retificado") sem precisar entrar todo dia no site da banca.

## 3. Características do Produto
Para alcançar os objetivos, o sistema possui as seguintes características essenciais:
1. **Módulo de Web Scraping:** Uma rotina assíncrona (Spring Batch) que varre portais em busca de atualizações.
2. **Processamento com IA:** Integração com LLMs (OpenAI) para interpretar a semântica de textos burocráticos e retornar JSON estruturado.
3. **Plataforma Web Responsiva e Acessível:** Uma interface Frontend amigável construída com as melhores práticas de Interação Humano-Computador (IHC), permitindo que qualquer pessoa, independentemente de limitações físicas, possa navegar pelas oportunidades.
4. **Arquitetura Escalável:** Uso de mensageria e banco de dados relacional (PostgreSQL) garantindo que, futuramente, o escopo possa crescer de "DF" para o "Brasil" sem refatorações extremas.
