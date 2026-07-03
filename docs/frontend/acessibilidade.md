# Guia de Acessibilidade e IHC

Para garantirmos que a plataforma **Concurseiro DF** possa ser utilizada pelo maior número de pessoas possível, incluindo Pessoas com Deficiência (PcDs), o desenvolvimento do Frontend (Next.js) deve seguir rigorosamente as diretrizes abaixo.

Esse guia foi formulado com base nas normativas do **WCAG 2.1 (Web Content Accessibility Guidelines)** e inspirado em avaliações acadêmicas de Interação Humano-Computador.

## 1. Semântica HTML e Leitores de Tela (Screen Readers)
O uso de tags corretas informa aos leitores de tela a estrutura da página.
- **Evitar `<div>` genéricas:** Para seções, use `<section>`, `<article>`, `<nav>`, `<header>` e `<footer>`.
- **Hierarquia de Títulos (H1 a H6):** Nunca pule níveis. Toda página deve ter apenas um `<h1>` principal que identifique o seu propósito.
- **`aria-labels`:** Em botões que contêm apenas ícones (como o de "lupa" ou "menu hamburguer"), obrigatoriamente inclua `aria-label="Buscar concurso"` ou `aria-label="Abrir menu"`.

## 2. Navegação por Teclado (Keyboard Accessibility)
Muitos usuários navegam apenas usando a tecla `TAB`.
- **Foco Visível:** Nunca utilize `outline: none` no CSS sem providenciar um `focus:ring` visível (via Tailwind, usar `focus:ring-2 focus:ring-blue-500`). O usuário precisa saber onde o cursor dele está.
- **Skip Links:** Na primeira linha de código, deve haver um link oculto (que só aparece com `tab`) escrito "Pular para o conteúdo principal". Isso evita que pessoas cegas precisem ouvir todos os botões de menu toda vez que trocam de página.

## 3. Contraste e Cores (Visão Subnormal / Daltonismo)
- O contraste de cor do texto para o fundo deve ser, no mínimo, **4.5:1** (nível AA). Textos grandes podem ter 3:1.
- **Não dependa apenas da cor:** Se um campo obrigatório estiver com erro, não o deixe apenas "vermelho". Coloque também um ícone de "X" ou texto escrito "Campo obrigatório".

## 4. Formulários
- Sempre utilize a tag `<label>` para identificar inputs. Se o design não comportar um label visual, use uma classe `sr-only` do Tailwind para escondê-lo visualmente, mas deixá-lo acessível ao leitor de tela.

## Tabela de Verificação (Checklist Front-End)
| Item | Descrição | Status |
|---|---|---|
| Contraste | Texto legível de acordo com o padrão AA. | A Fazer |
| Foco do Teclado | Possível navegar apenas no TAB. Todos botões recebem `focus`. | A Fazer |
| Zoom | O layout não quebra com 200% de Zoom no navegador. | A Fazer |
| Alt Text | Imagens significativas têm atributo `alt`. | A Fazer |
| Semântica | Uso de H1-H6 corretos. | A Fazer |

> Qualquer Pull Request envolvendo Frontend só será aprovado após ser checado contra esta lista.
