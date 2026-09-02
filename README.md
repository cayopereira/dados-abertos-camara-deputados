# 🏛️ Transparência Legislativa - Atuação Parlamentar

![Badge Status](https://img.shields.io/badge/Status-Em_Desenvolvimento-green)
![Badge Spring](https://img.shields.io/badge/Spring_Boot-3.X-brightgreen)
![Badge React](https://img.shields.io/badge/React-18.X-blue)
![Badge PostgreSQL](https://img.shields.io/badge/PostgreSQL-Ready-blue)

Plataforma web desenvolvida para monitorar, de forma centralizada e acessível, as frentes de atuação, presenças e despesas dos deputados federais, consumindo dados abertos diretamente da **API da Câmara dos Deputados**.

Este projeto foi desenvolvido como parte do **Projeto Integrador II** da Univesp, pelos estudandes do Grupo 20 - 2026/2.

---

## 🎯 Objetivo

Desenvolver um software web *Full Stack* que promova o controle social e a transparência pública, superando a fragmentação e complexidade dos dados brutos oficiais. O sistema armazena informações localmente para garantir performance, apresenta os dados de forma visual e acessível e conta com cobertura de testes e integração contínua.

## 🚀 Funcionalidades

- **Sincronização de Dados:** Consumo em *background* da [API de Dados Abertos da Câmara](https://dadosabertos.camara.leg.br/) para alimentar o banco de dados interno.
- **Perfil do Parlamentar:** Visualização detalhada de gastos (Cota para Exercício da Atividade Parlamentar), presenças em plenário e proposições.
- **Análise de Dados:** Gráficos interativos no *front-end* detalhando a evolução de despesas ao longo do mandato.
- **Acessibilidade (WCAG):** Interface responsiva, alto contraste e suporte adequado para leitores de tela e navegação por teclado.
- **Cache Local:** Redução do número de requisições à API pública, melhorando a velocidade da aplicação e evitando *rate limits*.

## 🛠️ Tecnologias Utilizadas

### Back-end
- **Java 17+**
- **Spring Boot** (Web, Data JPA, Validation)
- **Jakarta Persistence API** para mapeamento objeto-relacional
- **Maven** para gerenciamento de dependências
- **JUnit 5 & Mockito** para testes unitários e de integração

### Banco de Dados
- **PostgreSQL**
- Modelagem relacional estruturada para armazenar parlamentares, despesas e frentes parlamentares.

### Front-end
- **React.js** (JavaScript puro)
- **Axios** para consumo da API interna
- **Chart.js / Recharts** para a plotagem dos dados
- Foco em semântica HTML e atributos ARIA para acessibilidade.

### Infraestrutura & DevOps
- **Git & GitHub** para controle de versão
- **Nuvem:** Preparado para *deploy* em plataformas como Render, Heroku ou AWS (a definir).

---

## ⚙️ Arquitetura do Sistema

1. O **Back-end (Spring Boot)** realiza *requests* programados à API da Câmara.
2. Os dados em JSON são convertidos para entidades Java e persistidos no **PostgreSQL** via Spring Data JPA.
3. O Back-end expõe uma REST API própria e otimizada (com paginação e filtros).
4. O **Front-end (React)** consome esta API interna e renderiza a interface para o usuário final.

---

## 📦 Como rodar o projeto localmente

### Pré-requisitos
- [JDK 17+](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/)
- [Node.js](https://nodejs.org/) e NPM/Yarn
- Banco de dados **PostgreSQL** instalado e rodando na porta `5432`.

Ao longo do projeto iremos documentar aqui todos os detalhes.
