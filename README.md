# ⚔️ RPG Batalha por Turnos - Operação: Código Amaldiçoado ⚔️

Este projeto é um simulador de batalha por turnos para RPG, desenvolvido em **Java Puro** (Vanilla Java), com foco na aplicação e consolidação de conceitos de Programação Orientada a Objetos (POO) e boas práticas de desenvolvimento. O jogo permite ao jogador controlar um Herói em combates contra Criaturas Paranormais, com um menu interativo, **sistema de persistência** e **progressão de personagem**.

---

## ⚠️ Aviso Legal e Créditos

Este projeto é um **trabalho de fã e de estudo**, **não oficial** e **não possui afiliação ou endosso** por Rafael Lange Severino "Cellbit" ou pela Ordem Paranormal RPG. Ele é uma homenagem e um exercício de programação inspirado na rica temática e conceitos do universo de Ordem Paranormal.

Todos os direitos sobre a obra "Ordem Paranormal RPG" e seus elementos pertencem aos seus respectivos criadores e detentores de direitos autorais. Este código não tem fins lucrativos e é estritamente para propósitos educacionais e de portfólio.

---

## ✨ Funcionalidades Principais

### 🎮 **Sistema de Jogo Completo**
* **Criação de Heróis:** Personalize seu herói com atributos únicos (Força, Agilidade, Vigor, Presença, Intelecto)
* **Sistema de Combate Avançado:** Batalhas por turnos com ataques normais e habilidades especiais
* **Mecânicas de RPG:** Sistema de crítico, esquiva, dano mínimo e consumo de energia
* **Progressão de Personagem:** Sistema de experiência e evolução de nível
* **Nomes Dinâmicos:** Heróis e Monstros recebem nomes aleatórios a cada batalha

### 💾 **Sistema de Persistência (NOVO!)**
* **Salvar Jogo:** Todos os heróis criados são automaticamente salvos
* **Carregar Jogo:** Continue suas aventuras com heróis salvos anteriormente
* **Banco de Dados SQLite:** Sistema robusto de armazenamento local
* **Gerenciamento de Saves:** Exclua heróis desnecessários do seu banco de dados

### 📈 **Sistema de Progressão (NOVO!)**
* **Level Up Automático:** Heróis evoluem automaticamente ao ganhar experiência suficiente
* **Aumento de Atributos:** Todos os atributos base aumentam ao evoluir (+1 cada)
* **Recálculo Dinâmico:** Stats de combate são atualizados automaticamente
* **Energia Crescente:** Energia máxima aumenta com o intelecto
* **Salvamento Automático:** Progresso é salvo imediatamente ao evoluir

### ⚔️ **Sistema de Combate Completo (NOVO!)**
* **Ataques Funcionais:** Lógica completa de dano, esquiva e crítico
* **Habilidade Especial "Golpe Heroico":** Ataque poderoso que consome energia
* **Feedback Visual Aprimorado:** Cores e mensagens claras para todas as ações
* **Balanceamento:** Sistema equilibrado baseado em atributos do personagem

## 📚 Conceitos de POO e Java Aplicados

Este projeto é uma demonstração prática do domínio dos seguintes conceitos:

### **Conceitos Fundamentais**
* **Classes e Objetos:** Modelagem de entidades do jogo (`Personagem`, `Heroi`, `Monstro`, `Batalha`, `MenuPrincipal`, `RepositorioDeHerois`)
* **Encapsulamento:** Utilização de atributos `private` com métodos Getters para controle de acesso ao estado dos objetos, e validações em construtores
* **Herança (`extends`):** `Heroi` e `Monstro` estendem a classe abstrata `Personagem`, reusando atributos e métodos comuns
* **Polimorfismo (`@Override`):** O método `atacar()` é sobrescrito em `Heroi` e `Monstro`, permitindo comportamentos específicos

### **Conceitos Avançados (NOVO!)**
* **Classes Abstratas:** A classe `Personagem` define contratos para suas subclasses
* **Persistência de Dados:** Sistema completo de CRUD (Create, Read, Update, Delete) com SQLite
* **Tratamento de Exceções:** Implementação robusta de `try-catch` para SQL e validação de dados
* **Design Patterns:** Repository Pattern para separação de responsabilidades
* **JDBC:** Conectividade com banco de dados usando prepared statements

### **Recursos de Java Utilizados**
* **Controle de Fluxo:** Uso extensivo de `if`/`else`, `switch` e loops (`while`, `do-while`)
* **`static` e `final`:** Para membros de classe e constantes
* **Passagem de Argumentos:** Demonstração prática do "pass by value"
* **`Scanner`:** Para interação com o usuário via console
* **Collections:** Uso de listas para gerenciamento de dados

## 🛠️ Tecnologias Utilizadas

* **Java Development Kit (JDK):** Versão 17+ 
* **SQLite:** Banco de dados leve e local para persistência
* **JDBC:** API Java para conectividade com banco de dados
* **Git:** Para controle de versão
* **GitHub:** Para hospedagem do repositório e gerenciamento de projeto
* **VS Code/IntelliJ IDEA:** Ambientes de desenvolvimento recomendados

## 🚀 Como Baixar e Testar o Projeto

### 📋 **Pré-requisitos (Para Iniciantes)**

**Não sabe nada de programação?** Sem problemas! Siga este guia passo a passo:

#### **1. Instalar o Java (Obrigatório)**
- Acesse: https://www.oracle.com/java/technologies/downloads/
- Baixe o **JDK 17** ou superior para seu sistema operacional (Windows/Mac/Linux)
- Execute o instalador e siga as instruções
- **Teste:** Abra o Terminal/Prompt de Comando e digite `java --version` - deve mostrar a versão instalada

#### **2. Instalar o Git (Recomendado)**
- Acesse: https://git-scm.com/downloads
- Baixe e instale para seu sistema operacional
- **Teste:** No terminal, digite `git --version` - deve mostrar a versão instalada

### 📁 **Método 1: Download Direto (Mais Fácil)**

1. **Baixar o Projeto:**
   - Clique no botão verde **"Code"** no topo desta página
   - Selecione **"Download ZIP"**
   - Extraia o arquivo ZIP em qualquer pasta do seu computador

2. **Abrir Terminal na Pasta:**
   - **Windows:** Segure `Shift` + Clique com botão direito na pasta → "Abrir janela do PowerShell aqui"
   - **Mac/Linux:** Clique com botão direito → "Abrir Terminal aqui"

### 💻 **Método 2: Usando Git (Para Programadores)**

```bash
git clone https://github.com/gui-ccr/rpg-batalha-console-java.git
cd rpg-batalha-console-java
```

### ⚙️ **Como Executar o Jogo**

1. **Compilar o Código:**
   ```bash
   # Navegar para a pasta do código fonte
   cd src/main/java
   
   # Compilar todas as classes Java
   javac com/guiccr/rpg/*.java
   ```

2. **Executar o Jogo:**
   ```bash
   # Executar o jogo (ainda na pasta src/main/java)
   java com.guiccr.rpg.Main
   ```

3. **Jogar!** 🎮
   - O jogo abrirá diretamente no seu terminal
   - Use o teclado para navegar pelos menus
   - Digite os números das opções e pressione Enter

### 🐛 **Problemas Comuns e Soluções**

| **Erro** | **Solução** |
|----------|-------------|
| `'java' não é reconhecido como comando` | Instale o JDK e configure as variáveis de ambiente |
| `classe não encontrada` | Certifique-se de estar na pasta `src/main/java` |
| `erro de compilação` | Verifique se baixou todos os arquivos corretamente |

### 📱 **Testando Funcionalidades**

**Menu Principal:**
- **Opção 1:** Criar novo herói e iniciar batalha
- **Opção 2:** Carregar herói salvo e continuar aventura  
- **Opção 3:** Excluir heróis salvos
- **Opção 4:** Sair do jogo

**Durante a Batalha:**
- **1:** Ataque normal
- **2:** Habilidade especial (consome energia)
- **3:** Ver status detalhado

**Dicas para Teste:**
- Crie um herói e vença algumas batalhas para ver o sistema de experiência
- Feche o jogo e carregue o mesmo herói para testar a persistência
- Experimente diferentes estratégias de combate

## 📂 Estrutura de Pastas

```markdown
rpg-batalha-console-java/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── guiccr/
│                   └── rpg/
│                       ├── Main.java                  # Ponto de entrada do jogo
│                       ├── Personagem.java            # Classe abstrata base
│                       ├── Heroi.java                 # Subclasse de Personagem (Agente)
│                       ├── Monstro.java               # Subclasse de Personagem (Criatura)
│                       ├── Batalha.java               # Lógica do combate por turnos
│                       ├── MenuPrincipal.java         # Menu interativo
│                       ├── GeradorDeNomes.java        # Gerador de nomes aleatórios
│                       ├── RepositorioDeHerois.java   # Sistema de persistência SQLite
│                       ├── ConsoleColors.java         # Cores para interface visual
│                       └── TesteConexao.java          # Teste de conexão com banco
├── db/
│   ├── rpg.db                     # Banco de dados SQLite (criado automaticamente)
│   ├── schema.sql                 # Esquema do banco de dados
│   ├── sqlite3.exe                # Executável SQLite (Windows)
│   └── sqlite-jdbc-3.50.3.0.jar   # Driver JDBC para SQLite
├── lib/
│   └── sqlite-jdbc-3.50.3.0.jar   # Biblioteca SQLite
├── target/                        # Arquivos compilados (gerado automaticamente)
├── .gitignore                     # Arquivo para ignorar arquivos gerados pelo Git
├── pom.xml                        # Configuração Maven (futuro)
└── README.md                      # Este arquivo de documentação
```

## 📈 Próximos Passos (Roadmap de Desenvolvimento)

O desenvolvimento deste projeto está sendo guiado por Issues no GitHub, dividido em fases:

### **✅ Fase 0: Organização do Projeto e Infraestrutura (Concluída)**
- [x] Estruturar o README.md do projeto
- [x] Refatorar classes para estrutura de pacotes
- [x] Configurar .gitignore adequado
- [x] Adicionar licença ao projeto
- [x] Configurar Maven/Gradle (futuro)
- [x] Criar guia de contribuição

### **✅ Fase 1: Refinamento e Usabilidade do Jogo (Concluída)**
- [x] Implementar relatórios detalhados de combate
- [x] Refinar validação de entrada de combate
- [x] Adicionar sistema de nomes dinâmicos para Monstro e Herói
- [x] Implementar chance de crítico e esquiva no combate
- [x] Melhorar exibição visual do combate e status
- [x] Implementar Menu Inicial

### **🚧 Fase 2: Expansão da POO e Complexidade de Jogo (Em Desenvolvimento)**
- [x] Implementar sistema de level up para o Herói *(concluído nesta PR)*
- [ ] Implementar sistema de habilidades com Enum
- [ ] Criar subclasses de Monstro para diferentes tipos de inimigos
- [ ] Adicionar sistema de inventário simples para o Herói

### **✅ Fase 3: Persistência e Organização do Projeto (Concluída)**
- [x] Implementar salvamento e carregamento de jogo com SQLite
- [x] Sistema completo de CRUD para heróis
- [x] Organização em pacotes lógicos

### **� Fases Futuras (Propostas)**
- **Sistema de Classes:** Diferentes tipos de heróis (Guerreiro, Mago, Arqueiro)
- **Dungeons:** Múltiplas batalhas sequenciais
- **Interface Gráfica:** Migração para Swing/JavaFX
- **Multiplayer:** Sistema de batalhas entre jogadores

## 🤝 Contribuição

Sinta-se à vontade para explorar o código, levantar Issues para sugestões ou bugs, ou iniciar Discussões para novas ideias.

## 📄 Licença

Este projeto está sob a licença [MIT License](https://opensource.org/licenses/MIT)
