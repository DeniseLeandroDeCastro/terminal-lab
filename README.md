# 💳 CieloPayLab

Aplicativo Android desenvolvido como projeto de estudo prático para aprofundamento em 
**Android Nativo, Kotlin, arquitetura de software, integração com APIs, testes, segurança e boas práticas de Engenharia de Software**, 
com foco nos requisitos de uma oportunidade de Desenvolvedor(a) Android.

---

## 📱 Sobre o projeto

O **CieloPayLab** é um aplicativo Android que simula o processamento de transações de pagamento.

O objetivo principal não é apenas construir uma interface de pagamento, mas desenvolver uma aplicação que permita praticar conceitos utilizados em projetos Android profissionais, como:

- Kotlin
- Jetpack Compose
- Clean Architecture
- SOLID
- MVVM
- Hilt
- Retrofit
- Moshi
- Room
- Coroutines
- Flow
- testes automatizados
- Git
- tratamento de erros
- segurança
- performance
- observabilidade
- integração com APIs REST

O projeto foi estruturado pensando em um cenário semelhante ao de aplicações financeiras e de pagamentos, onde **qualidade, confiabilidade, segurança e manutenção do código são fundamentais**.

---

# 🎯 Objetivo

Este projeto foi criado para estudar e demonstrar, na prática, conhecimentos relacionados aos seguintes requisitos:

### Android

- Desenvolvimento Android nativo
- Kotlin
- Java
- Jetpack Compose
- ViewModel
- Lifecycle
- Coroutines
- Flow

### Arquitetura

- Clean Architecture
- MVVM
- SOLID
- Clean Code
- Separation of Concerns
- Dependency Injection

### Comunicação

- REST API
- Retrofit
- Moshi
- DTO
- tratamento de erros
- idempotência

### Persistência

- Room
- SQLite
- Repository Pattern

### Qualidade

- testes unitários
- testes instrumentados
- MockK
- MockWebServer

### Engenharia

- Git
- branching
- code review
- redução de dívida técnica
- documentação

### Segurança

- proteção de dados
- armazenamento seguro
- comunicação HTTPS
- prevenção de informações sensíveis em logs

### Engenharia de produto

- indicadores técnicos
- KPIs
- OKRs
- performance
- observabilidade

### Inteligência Artificial

- geração de parte do código
- documentação
- análise de erros
- apoio na criação de testes
- refatoração

---

# 🏦 Contexto de negócio

O projeto simula uma solução de pagamentos.

Em um cenário real, uma transação poderia seguir aproximadamente este fluxo:

```text
Usuário
   │
   ▼
Tela de pagamento
   │
   ▼
ViewModel
   │
   ▼
UseCase
   │
   ▼
Repository
   │
   ▼
Retrofit
   │
   ▼
API Backend
   │
   ▼
Processamento da transação
   │
   ▼
Resposta
   │
   ▼
Repository
   │
   ▼
ViewModel
   │
   ▼
Interface
