<div align="center">

# ⚔️ RPG Batalha por Turnos - Operação: Código Amaldiçoado ⚔️

**Um simulador de batalha de RPG em console, construído com Java puro para demonstrar conceitos avançados de Programação Orientada a Objetos e design de software.**

</div>

<div align="center">

<img src="https://img.shields.io/badge/Java-17+-blue?style=for-the-badge&logo=java" alt="Java 17+">
<img src="https://img.shields.io/badge/Database-SQLite-blue?style=for-the-badge&logo=sqlite" alt="SQLite">
<img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge" alt="License MIT">
<img src="https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=for-the-badge" alt="Status">

</div>

<br>

<div align="center">

<img src="assets/Gameplay.gif" alt="Gameplay do Jogo" width="80%">

</div>

---

> Este projeto é um showcase técnico e um trabalho de fã inspirado no universo de **Ordem Paranormal RPG**. Ele foi criado com o objetivo de aplicar e solidificar conhecimentos em desenvolvimento de software, desde os fundamentos de POO até a persistência de dados com um banco de dados local.

<br>

## 📜 Índice

- [⚔️ RPG Batalha por Turnos - Operação: Código Amaldiçoado ⚔️](#️-rpg-batalha-por-turnos---operação-código-amaldiçoado-️)
  - [📜 Índice](#-índice)
  - [✨ Funcionalidades Principais](#-funcionalidades-principais)
  - [🛠️ Tecnologias e Conceitos Aplicados](#️-tecnologias-e-conceitos-aplicados)
  - [🚀 Comece a Jogar em 5 Minutos](#-comece-a-jogar-em-5-minutos)
    - [**📋 Pré-requisitos**](#-pré-requisitos)
    - [**▶️ Executando o Jogo**](#️-executando-o-jogo)
  - [📸 Galeria do Jogo](#-galeria-do-jogo)
  - [📂 Estrutura do Projeto](#-estrutura-do-projeto)
  - [📈 Roadmap de Desenvolvimento](#-roadmap-de-desenvolvimento)
  - [⚠️ Aviso Legal e Créditos](#️-aviso-legal-e-créditos)
  - [📄 Licença](#-licença)

---

## ✨ Funcionalidades Principais

Este projeto vai além de uma simples batalha. É um sistema de RPG completo com mecânicas pensadas para criar uma experiência de jogo dinâmica e re-jogável.

| Funcionalidade          | Descrição                                                                                             | Status      |
| ----------------------- | ----------------------------------------------------------------------------------------------------- | ----------- |
| ⚔️ **Combate por Turnos** | Sistema de batalha com ataques normais, habilidades especiais, chance de crítico e esquiva.             | ✅ Concluído |
| 💾 **Persistência de Dados** | Salve e carregue seus heróis! Todo o progresso é armazenado em um banco de dados **SQLite** local.      | ✅ Concluído |
| 📈 **Progressão de Herói** | Ganhe experiência, suba de nível, e veja seus atributos e status de combate melhorarem automaticamente. | ✅ Concluído |
| 🎒 **Sistema de Inventário** | Colete, equipe espadas para aumentar seu dano e use poções para se curar no meio do combate.         | ✅ Concluído |
| 🎨 **UI de Console Rica** | Interface colorida e com feedback claro para todas as ações, melhorando a experiência do jogador.   | ✅ Concluído |
| 🎲 **Nomes Aleatórios** | Heróis e monstros recebem nomes dinâmicos a cada partida, garantindo variedade.                     | ✅ Concluído |

---

## 🛠️ Tecnologias e Conceitos Aplicados

Este projeto foi uma excelente oportunidade para aplicar um vasto conjunto de conhecimentos teóricos em um cenário prático e funcional.

<details>
<summary><strong>🧠 Conceitos de Programação Orientada a Objetos (POO)</strong></summary>

* **Classes e Objetos:** Modelagem de todas as entidades (`Heroi`, `Monstro`, `Item`).
* **Encapsulamento:** Uso de `private` com Getters e Setters para proteger o estado dos objetos.
* **Herança:** `Heroi` e `Monstro` herdam de uma classe abstrata `Personagem`.
* **Polimorfismo:** Sobrescrita de métodos como `atacar()` para comportamentos específicos.
* **Classes Abstratas:** `Personagem` e `Item` como contratos base.
* **Interfaces:** Uso de `Equipavel` para definir um contrato que itens vestíveis devem seguir.
* **Composição:** O `Heroi` *tem um* `Inventario`, que por sua vez *tem uma lista* de `Item`.

</details>

<details>
<summary><strong>💻 Stack de Desenvolvimento e Ferramentas</strong></summary>

* **Linguagem:** `Java 17+`
* **Banco de Dados:** `SQLite` para persistência de dados local e sem necessidade de servidor.
* **Conectividade DB:** `JDBC` (Java Database Connectivity) para comunicação com o SQLite.
* **Controle de Versão:** `Git` e `GitHub`.
* **Build/Execução:** Scripts (`.bat`) para facilitar a compilação e execução.

</details>

<details>
<summary><strong>🏛️ Arquitetura e Boas Práticas</strong></summary>

* **Repository Pattern:** A classe `RepositorioDeHerois` isola toda a lógica de acesso a dados, separando as regras de negócio da persistência.
* **Tratamento de Exceções:** Uso de `try-catch` para lidar com erros de SQL e entrada de usuário.
* **Código Limpo:** Foco em nomes de variáveis e métodos claros e uma estrutura de pacotes organizada.

</details>

---

## 🚀 Comece a Jogar em 5 Minutos

Siga os passos abaixo para mergulhar na aventura.

### **📋 Pré-requisitos**
1.  **Java Development Kit (JDK) 17 ou superior:** [Faça o download aqui](https://www.oracle.com/java/technologies/downloads/).
    * *Verificação:* Abra seu terminal e digite `java --version`.
2.  **Git (Opcional):** [Faça o download aqui](https://git-scm.com/downloads).

### **▶️ Executando o Jogo**

<details>
<summary><strong>Método 1: Para Jogadores (Windows - O Mais Fácil)</strong></summary>

1.  Baixe o projeto como **ZIP** e extraia a pasta.
2.  Entre na pasta extraída.
3.  Execute o arquivo `Jogar.bat` com um duplo clique.
4.  O script cuidará da compilação e execução. Divirta-se! 🎮

</details>

<details>
<summary><strong>Método 2: Para Desenvolvedores (Multiplataforma)</strong></summary>

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/gui-ccr/rpg-batalha-console-java.git](https://github.com/gui-ccr/rpg-batalha-console-java.git)
    cd rpg-batalha-console-java
    ```
2.  **Compile o código:**
    ```bash
    # Para Linux/Mac
    javac -d target/classes -cp "lib/*" src/main/java/com/guiccr/rpg/*.java

    # Para Windows (PowerShell)
    javac -d target/classes -cp "lib\*" src\main\java\com\guiccr\rpg\*.java
    ```
3.  **Execute o jogo:**
    ```bash
    # Para Linux/Mac
    java -cp "target/classes:lib/*" com.guiccr.rpg.Main

    # Para Windows (PowerShell)
    java -cp "target\classes;lib\*" com.guiccr.rpg.Main
    ```

</details>

---

## 📸 Galeria do Jogo

<div align="center">
    <img src="assets/Tela de Batalha.png" alt="Tela de Batalha" width="45%">
    <img src="assets/Inventario.png" alt="Inventário" width="45%">
    <img src="assets/Menu Principal.png" alt="Menu Principal" width="45%">
</div>

---

## 📂 Estrutura do Projeto

A estrutura de pastas foi organizada para separar responsabilidades, seguindo as convenções do Maven.

```
rpg-batalha-console-java/
├── .github/          # (Opcional) Para templates de Issues, PRs, etc.
├── assets/           # Imagens e GIFs para o README
├── db/               # Scripts e banco de dados
├── lib/              # Dependências .jar (JDBC Driver)
├── src/
│   └── main/java/com/guiccr/rpg/
│       ├── Main.java                # Ponto de entrada
│       ├── model/                   # Pacote para entidades (Heroi, Monstro)
│       ├── repository/              # Pacote para acesso a dados
│       └── service/                 # Pacote para regras de negócio (Batalha)
├── target/           # Classes compiladas (gerado automaticamente)
├── .gitignore
├── Jogar.bat         # Script de execução para Windows
├── LICENSE
└── README.md
```
---

## 📈 Roadmap de Desenvolvimento

O projeto segue um plano de desenvolvimento claro, com futuras expansões já idealizadas.

-   [x] **Fase 0: Infraestrutura:** Estrutura de pastas, README e licença.
-   [x] **Fase 1: Mecânicas de Jogo:** Sistema de combate, nomes dinâmicos e UI.
-   [x] **Fase 2: Expansão POO:** Level up, inventário, itens e habilidades.
-   [x] **Fase 3: Persistência:** Implementação completa de Salvar/Carregar com SQLite.
-   [ ] **Fase 4: Refatoração e Qualidade:**
    -   [ ] Adicionar testes unitários (JUnit).
    -   [x] Refatorar para pacotes de `model`, `service` e `repository`.
    -   [ ] Implementar um sistema de Log (ex: Log4j).
-   [ ] **Fase 5: Futuras Funcionalidades:**
    -   [ ] Sistema de classes de Herói (Guerreiro, Mago).
    -   [ ] Múltiplos tipos de monstros.
    -   [ ] Masmorras com múltiplas batalhas.

---

## ⚠️ Aviso Legal e Créditos

Este projeto é um **trabalho de fã e de estudo**, **não oficial** e **não possui afiliação ou endosso** por Rafael Lange Severino "Cellbit" ou pela Ordem Paranormal RPG. Ele é uma homenagem e um exercício de programação inspirado na rica temática e conceitos do universo de Ordem Paranormal. Todos os direitos sobre a obra original pertencem aos seus respectivos criadores.

---

## 📄 Licença

Este projeto está sob a licença [MIT License](LICENSE).
