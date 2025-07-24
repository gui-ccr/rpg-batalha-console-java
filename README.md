# ⚔️ RPG Batalha por Turnos - Operação: Código Amaldiçoado ⚔️

Este projeto é um simulador de batalha por turnos para RPG, desenvolvido em **Java Puro** (Vanilla Java), com foco na aplicação e consolidação de conceitos de Programação Orientada a Objetos (POO) e boas práticas de desenvolvimento. O jogo permite ao jogador controlar um Agente em combates contra Criaturas Paranormais, com um menu interativo e sistema de progressão.

---

## ⚠️ Aviso Legal e Créditos

Este projeto é um **trabalho de fã e de estudo**, **não oficial** e **não possui afiliação ou endosso** por Rafael Lange Severino "Cellbit" ou pela Ordem Paranormal RPG. Ele é uma homenagem e um exercício de programação inspirado na rica temática e conceitos do universo de Ordem Paranormal.

Todos os direitos sobre a obra "Ordem Paranormal RPG" e seus elementos pertencem aos seus respectivos criadores e detentores de direitos autorais. Este código não tem fins lucrativos e é estritamente para propósitos educacionais e de portfólio.

---

## ✨ Funcionalidades Atuais

* **Personagens Personalizáveis:** Crie Agentes (Heróis) e Criaturas (Monstros) com nomes, vida, ataque e defesa definidos.
* **Sistema de Batalha por Turnos:** Lógica de combate baseada em turnos, onde Agentes e Criaturas atacam sequencialmente até um ser derrotado.
* **Habilidade Especial do Agente:** Agentes podem usar uma habilidade especial que consome energia e causa dano extra.
* **Tratamento de Exceções:** Validação de entradas do usuário e de argumentos de construtores para garantir a robustez do jogo.
* **Menu Interativo:** Menu principal no console para iniciar novas batalhas ou sair do jogo.
* **Feedback Visual:** Mensagens claras no console para ações de combate e status.
* **Relatórios Detalhados de Combate:** Ao final de cada batalha, um resumo completo é exibido, incluindo o vencedor, vida final dos combatentes e o número total de turnos.
* **Nomes Dinâmicos:** Heróis e Monstros recebem nomes aleatórios de listas predefinidas a cada nova batalha, tornando a experiência mais variada.

## 📚 Conceitos de POO e Java Aplicados

Este projeto é uma demonstração prática do domínio dos seguintes conceitos:

* **Classes e Objetos:** Modelagem de entidades do jogo (`Personagem`, `Heroi` (Agente), `Monstro` (Criatura), `Batalha`, `MenuPrincipal`, `GeradorDeNomes`).
* **Encapsulamento:** Utilização de atributos `private` com métodos Getters para controle de acesso ao estado dos objetos, e validações em construtores.
* **Herança (`extends`):** `Heroi` (Agente) e `Monstro` (Criatura) estendem a classe abstrata `Personagem`, reusando atributos e métodos comuns, e implementando comportamentos específicos.
* **Polimorfismo (`@Override`):** O método `atacar()` é sobrescrito em `Heroi` e `Monstro`, permitindo que um `Personagem` genérico execute o ataque específico de seu tipo real em tempo de execução.
* **Classes Abstratas:** A classe `Personagem` é abstrata, definindo um contrato para suas subclasses e impedindo a criação de instâncias genéricas.
* **Controle de Fluxo:** Uso extensivo de `if`/`else`, `switch` e loops (`while`, `do-while`) para a lógica do jogo e menus.
* **Tratamento de Exceções:** Implementação de `try-catch` para lidar com entradas inválidas do usuário (`InputMismatchException`) e `IllegalArgumentException` para validação de dados em construtores.
* **Enums:** Para representar estados fixos (futuras Prioridades, Tipos de Habilidade, etc.).
* **`static` e `final`:** Para membros de classe e constantes (ex: `MenuPrincipal.pausar()`, listas de nomes em `GeradorDeNomes`).
* **Passagem de Argumentos:** Demonstração prática do "pass by value" para primitivos e referências.
* **`Scanner`:** Para interação com o usuário via console.

## 🛠️ Tecnologias Utilizadas

* **Java Development Kit (JDK):** Versão 17+ (ou a versão LTS que você estiver usando).
* **Git:** Para controle de versão.
* **GitHub:** Para hospedagem do repositório e gerenciamento de projeto (Issues, Pull Requests, Discussions, Wiki).
* **Editor de Código:** VS Code (com Extension Pack for Java) ou IntelliJ IDEA Community.

## 🚀 Como Rodar o Projeto

Siga estes passos para compilar e executar o jogo no seu terminal:

1.  **Clone o Repositório:**
    ```bash
    git clone [https://github.com/guic-ccr/rpg-batalha-console-java.git](https://github.com/guic-ccr/rpg-batalha-console-java.git) # Substitua 'guic-ccr' pelo seu usuário do GitHub
    cd rpg-batalha-console-java
    ```

2.  **Compile o Código-Fonte:**
    * Navegue até o diretório `src/main/java`.
    ```bash
    cd src/main/java
    ```
    * Compile todas as classes do seu pacote.
    ```bash
    javac com/guiccr/rpg/*.java
    ```
    * Se tudo estiver correto, nenhum erro será exibido e arquivos `.class` serão gerados.

3.  **Execute o Jogo:**
    * A partir do diretório `src/main/java`, execute a classe `Main`.
    ```bash
    java com.guiccr.rpg.Main
    ```

4.  **Interaja no Console:** O jogo será executado diretamente no seu terminal. Siga as instruções do menu.

## 📂 Estrutura de Pastas

```markdown
rpg-batalha-console-java/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── guiccr/
│                   └── rpg/
│                       ├── Main.java              # Ponto de entrada do jogo
│                       ├── Personagem.java        # Classe abstrata base
│                       ├── GeradorDeNomes.java    # Classe para Geradora de Nomes (Agentes & Criaturas)
│                       ├── Heroi.java             # Subclasse de Personagem (Agente)
│                       ├── Monstro.java           # Subclasse de Personagem (Criatura)
│                       ├── Batalha.java           # Lógica do combate
│                       └── MenuPrincipal.java     # Menu interativo
│
├── .gitignore             # Arquivo para ignorar arquivos gerados pelo Git
└── README.md              # Este arquivo de documentação
```

## 📈 Próximos Passos (Roadmap de Desenvolvimento)

O desenvolvimento deste projeto está sendo guiado por Issues no GitHub, dividido em fases:

* **Fase 1: Refinamento e Usabilidade:** (Em andamento)
    - [x] Gerar relatórios de batalha.
    * Refinar validação de entrada de combate.
    - [x] Adicionar sistema de nomes dinâmicos.
    * Implementar chance de crítico e esquiva.
    * Melhorar exibição visual.
* **Fase 2: Expansão da POO e Complexidade de Jogo:**
    * Sistema de habilidades com Enum.
    * Subclasses de Monstro.
    * Sistema de inventário simples.
    * Sistema de level up.
* **Fase 3: Persistência e Organização:**
    * Salvar/carregar jogo.
    * Refatorar para pacotes lógicos (se ainda não totalmente).
      
* **Organização do Projeto e Infraestrutura:** (Issues separadas para README, .gitignore, licença, etc.)

## 🤝 Contribuição

Sinta-se à vontade para explorar o código, levantar Issues para sugestões ou bugs, ou iniciar Discussões para novas ideias.

## 📄 Licença

Este projeto está sob a licença [MIT License](https://opensource.org/licenses/MIT)
