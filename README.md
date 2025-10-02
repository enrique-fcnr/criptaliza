# Criptaliza: Sistema de Gestão de Investimentos em Criptoativos

![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento%20(Fase%202)-yellowgreen)
![Java](https://img.shields.io/badge/Linguagem-Java%2017-007396.svg?logo=java)
![FIAP](https://img.shields.io/badge/Projeto%20Acad%C3%AAmico-FIAP-blue)

---

## 📊 Visão Geral

O Criptaliza é uma plataforma web projetada para simplificar a gestão de carteiras de criptomoedas, atuando como um intermediário inteligente entre o investidor e as exchanges.

Desenvolvido como projeto acadêmico para a disciplina de **Engenharia de Software (2º ano) da FIAP**, o sistema foca em fornecer ferramentas de análise de risco e automação de alocação de capital.

### 🏁 Status do Projeto

A estrutura de classes de domínio está **finalizada**. Estamos entrando na **Fase 3: Implementação da Lógica de Negócio**.

---

## 🎯 Problema e Solução

### Problema Identificado

Embora 12% da população brasileira possua criptomoedas, muitos enfrentam barreiras que impedem a adoção em massa e a gestão eficiente do capital:

* **Complexidade:** Interfaces complexas e terminologia técnica das exchanges.
* **Falta de Orientação:** Ausência de aconselhamento e orientação personalizada de risco.
* **Insegurança:** Dúvidas sobre custódia e fragmentação entre múltiplas plataformas.

### Nossa Solução

O Criptaliza atua como um assistente virtual financeiro, consolidando a gestão e oferecendo inteligência:

| Funcionalidade | Benefício |
| :--- | :--- |
| ✅ **Análise de Perfil** | Classificação automatizada do perfil de risco do investidor. |
| ✅ **Recomendações Personalizadas** | Sugestões de alocação de capital em criptoativos. |
| ✅ **Dashboard Unificado** | Visão centralizada de múltiplas carteiras e exchanges. |
| ✅ **Gestão Ativa** | Ferramentas de rebalanceamento automático e monitoramento de risco. |
| ✅ **Integração Segura** | Comunicação segura com exchanges via APIs. |

---

## 🏗️ Arquitetura e Decisões Técnicas

### 🛠️ Tecnologias Principais

| Categoria | Tecnologia | Notas |
| :--- | :--- | :--- |
| **Linguagem** | Java 17 | Implementação pura, sem *frameworks* de injeção de dependência. |
| **Persistência** | PostgreSQL | (Planejado) Banco de dados robusto e relacional. |

### 📐 Padrões de Projeto (Design Patterns)

* **MVC:** Separação clara entre a lógica de negócio (`entities`), a interface e o controle.
* **Singleton:** Para gerenciar recursos únicos, como instâncias de integração com exchanges.
* **Factory:** Para a criação controlada de entidades complexas, como `Carteira` e `Trade`.
* **Observer:** Para notificação de eventos críticos (ex: execução de ordens).

### 🛡️ Decisões Chave de Engenharia e Segurança

| Decisão | Justificativa |
| :--- | :--- |
| **UUID para IDs** | Unicidade global garantida, segurança contra enumeração e preparado para arquitetura distribuída. |
| **`BigDecimal` para Valores** | **Precisão absoluta** em todos os cálculos financeiros e monetários, evitando erros de ponto flutuante. |
| **Versionamento Otimista** | Controle de concorrência em ambiente multiusuário sem o uso de *locks* pessimistas. |
| **Idempotency Keys** | Evita a duplicação de operações críticas (como `enviarOrdem()`) no caso de falhas de comunicação. |
| **Interfaces Segregadas** | Definição de interfaces como `Auditable`, `Negotiable` e `Validatable` para manter as responsabilidades claras. |
| **Segurança** | Uso de **PBKDF2** para *hashing* de senhas e **JWT** para autenticação de APIs. |

---

## 💻 Estrutura do Código (Implementação Java)

O projeto é organizado em pacotes que refletem as responsabilidades do sistema:

| Pacote | Responsabilidade | Classes Implementadas |
| :--- | :--- | :--- |
| **`entities`** | Objetos de Domínio e Associações. | `Cliente`, `Investidor`, `Carteira`, `Ordem`, `Trade`, `InvestidorTrade`, `PerfilInvestidor`, `RecomendacaoCarteira`, `API_Corretora`. |
| **`integrations`** | Lógica de comunicação com APIs externas. | `ExchangeIntegration` |
| **`enums`** | Tipos e status de negócio. | `TipoOrdem`, `StatusOrdem` |

### Exemplo de Fluxo (Ordem a Trade)

A estrutura de classes garante o rastreamento completo de uma intenção de ordem até a execução final:

```mermaid
graph LR
    A[Carteira] -->|0..*| B(Ordem);
    B -->|1| C[API_Corretora];
    C -->|1| D[ExchangeIntegration];
    B -->|0..*| E[Trade];
    F[Investidor] -->|1| G[PerfilInvestidor];
    F -->|0..*| H[InvestidorTrade];
    E -->|0..*| H;
