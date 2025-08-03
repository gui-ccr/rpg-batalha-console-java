# 🗺️ Mapa de Commits - Classe Batalha.java

## Estrutura Organizada por Funcionalidades

### 📋 **COMMIT 1: Estrutura Base da Classe**
**Linhas: 1-30**
```java
// INÍCIO: Imports e declaração da classe
package com.guiccr.rpg;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

public class Batalha {
    // Atributos privados
    private Heroi heroi;
    private Monstro monstro;
    private Scanner scanner;
    private int turnos;
    
    // Construtor com validações
    public Batalha(Heroi heroi, Monstro monstro, Scanner scanner) {
        // Validações e inicializações
    }
}
// FIM: Estrutura base
```

---

### 🚀 **COMMIT 2: Inicialização da Batalha**
**Linhas: 32-37**
```java
// INÍCIO: Método iniciar() - Introdução
public void iniciar() {
    System.out.println("\n=== INÍCIO DA BATALHA ===");
    System.out.println(heroi.getNome() + " vs " + monstro.getNome());
    MenuPrincipal.pausar(2000);
// FIM: Introdução da batalha
```

---

### 🔄 **COMMIT 3: Loop Principal de Turnos**
**Linhas: 38-46**
```java
// INÍCIO: Loop principal e contagem de turnos
    while (heroi.estaVivo() && monstro.estaVivo()) {
        this.turnos++;
        
        System.out.println("\n--- TURNO #" + this.turnos + " ---");
        System.out.println("----------------------------------------");
        exibirVidaCombatentes();
        MenuPrincipal.pausar(1500);
// FIM: Início do turno
```

---

### 👤 **COMMIT 4: Sistema de Menu do Herói**
**Linhas: 47-71**
```java
// INÍCIO: Turno do herói - validação de entrada
        boolean acaoRealizada = false;
        while (!acaoRealizada && heroi.estaVivo() && monstro.estaVivo()) {
            System.out.println("\n--- VEZ DE " + heroi.getNome().toUpperCase() + " ---");
            exibirMenuHeroi();
            
            // Validação de entrada do usuário
            int escolha = 0;
            boolean entradaValida = false;
            
            while (!entradaValida) {
                try {
                    System.out.print("Escolha sua ação: ");
                    escolha = scanner.nextInt();
                    // Validações...
                } catch (InputMismatchException e) {
                    // Tratamento de erro...
                }
            }
            scanner.nextLine();
// FIM: Validação de entrada
```

---

### ⚔️ **COMMIT 5: Ação 1 - Sistema de Ataque**
**Linhas: 72-83**
```java
// INÍCIO: Case 1 - Ataque básico
            switch (escolha) {
                case 1:
                    System.out.println("\n" + ConsoleColors.RED + "=== ATAQUE ===" + ConsoleColors.RESET);
                    MenuPrincipal.pausar(500);
                    System.out.println(heroi.getNome() + " prepara-se para atacar...");
                    MenuPrincipal.pausar(800);
                    heroi.atacar(monstro);
                    MenuPrincipal.pausar(1500);
                    acaoRealizada = true;
                    break;
// FIM: Sistema de ataque
```

---

### ⚡ **COMMIT 6: Ação 2 - Habilidade Especial**
**Linhas: 84-96**
```java
// INÍCIO: Case 2 - Habilidade especial
                case 2:
                    if (heroi.getEnergia() >= 10) {
                        System.out.println("\n" + ConsoleColors.YELLOW + "=== HABILIDADE ESPECIAL ===");
                        // Execução da habilidade...
                        acaoRealizada = true;
                    } else {
                        System.out.println("\n" + ConsoleColors.RED + "=== ENERGIA INSUFICIENTE ===");
                        // Feedback de energia insuficiente...
                    }
                    break;
// FIM: Habilidade especial
```

---

### 🎒 **COMMIT 7: Ação 3 - Sistema de Inventário (MEGA COMMIT)**
**Linhas: 97-149**
```java
// INÍCIO: Case 3 - Submenu completo do inventário
                case 3:
                    boolean voltarAoMenuPrincipal = false;
                    while (!voltarAoMenuPrincipal && !acaoRealizada) {
                        System.out.println("\n" + ConsoleColors.CYAN + "=== INVENTÁRIO ===");
                        
                        // Validação do inventário
                        boolean inventarioValido = exibirMenuInventario();
                        
                        if (!inventarioValido) {
                            // Inventário vazio...
                            break;
                        }
                        
                        // Submenu do inventário (4 opções)
                        int opcaoInventario = 0;
                        // Validação da opção...
                        
                        switch (opcaoInventario) {
                            case 1: // Usar Item (consome turno)
                            case 2: // Equipar Item (não consome turno)
                            case 3: // Ver Detalhado (não consome turno)
                            case 4: // Voltar
                        }
                    }
                    break;
// FIM: Sistema completo de inventário
```

---

### 🏃 **COMMIT 8: Ação 4 - Sistema de Fuga**
**Linhas: 150-162**
```java
// INÍCIO: Case 4 - Tentativa de fuga
                case 4:
                    System.out.println("\n" + ConsoleColors.YELLOW + "=== TENTATIVA DE FUGA ===");
                    MenuPrincipal.pausar(500);
                    System.out.println(heroi.getNome() + " procura uma saída...");
                    MenuPrincipal.pausar(1000);
                    System.out.println("Tentando escapar...");
                    MenuPrincipal.pausar(1500);
                    System.out.println(ConsoleColors.RED + "A fuga falhou!");
                    acaoRealizada = true;
                    break;
// FIM: Sistema de fuga
```

---

### 📊 **COMMIT 9: Ação 5 - Exibir Status**
**Linhas: 163-175**
```java
// INÍCIO: Case 5 - Status do herói
                case 5:
                    System.out.println("\n" + ConsoleColors.PURPLE + "=== VERIFICANDO STATUS ===");
                    MenuPrincipal.pausar(500);
                    System.out.println("Analisando condição atual do herói...");
                    MenuPrincipal.pausar(800);
                    heroi.exibirStatus();
                    MenuPrincipal.pausar(1000);
                    // NÃO consome turno
                    System.out.println("(Exibir status não consome seu turno.)");
                    break;
// FIM: Sistema de status
```

---

### 🏆 **COMMIT 10: Verificação de Vitória**
**Linhas: 176-184**
```java
// INÍCIO: Verificação pós-ação do herói
            } // Fim do switch
        } // Fim do while de ações
        MenuPrincipal.pausar(3000);

        // Verificar se o monstro foi derrotado
        if (!monstro.estaVivo()) {
            System.out.println("\n" + monstro.getNome() + " foi derrotado!");
            heroi.ganharExperiencia(monstro.getExpConcedida());
            MenuPrincipal.pausar(2500);
            break;
        }
// FIM: Verificação de vitória
```

---

### 👹 **COMMIT 11: Turno do Monstro**
**Linhas: 185-195**
```java
// INÍCIO: Turno do monstro
        System.out.println("\n--- VEZ DE " + monstro.getNome().toUpperCase() + " ---");
        monstro.atacar(heroi);
        MenuPrincipal.pausar(3000);

        // Verificar se o herói foi derrotado
        if (!heroi.estaVivo()) {
            System.out.println("\n" + heroi.getNome() + " foi derrotado!");
            break;
        }
    } // Fim do loop principal
// FIM: Turno do monstro
```

---

### 🎯 **COMMIT 12: Resultado Final da Batalha**
**Linhas: 196-210**
```java
// INÍCIO: Resultado final e relatório
    System.out.println("\n=========================================");
    System.out.println("FIM DA BATALHA!");
    System.out.println("=========================================");
    
    if (heroi.estaVivo()) {
        System.out.println("🎉 VITÓRIA! " + heroi.getNome() + " derrotou " + monstro.getNome());
        RepositorioDeHerois.atualizarHeroi(heroi);
    } else if (monstro.estaVivo()) {
        System.out.println("💀 DERROTA! " + monstro.getNome() + " derrotou " + heroi.getNome());
    } else {
        System.out.println("🤝 EMPATE! Ambos os combatentes caíram.");
    }

    // Relatório final
    System.out.println("\n--- RELATÓRIO DE BATALHA ---");
    System.out.printf("  Total de Turnos: %d%n", this.turnos);
    // Mais estatísticas...
} // FIM do método iniciar()
// FIM: Resultado final
```

---

### 📊 **COMMIT 13: Sistema de Barras de Vida**
**Linhas: 212-251**
```java
// INÍCIO: Método exibirVidaCombatentes()
private void exibirVidaCombatentes() {
    final int COMPRIMENTO_BARRA_HP = 20;
    
    // === BARRA DE VIDA DO HERÓI ===
    double porcentagemVidaHeroi = (double) heroi.getVidaAtual() / heroi.getVidaMaxima();
    // Construção da barra visual [████████████████████]
    // Cores dinâmicas baseadas na porcentagem
    
    // === BARRA DE VIDA DO MONSTRO ===
    // Mesmo processo para o monstro
}
// FIM: Sistema de barras visuais
```

---

### 🎒 **COMMIT 14: Métodos Auxiliares do Inventário**
**Linhas: 253-425**

#### **14A: Menu do Inventário (253-274)**
```java
// INÍCIO: exibirMenuInventario()
private boolean exibirMenuInventario() {
    // Exibe as 4 opções do inventário
    // Conta itens disponíveis
    // Retorna se tem itens
}
// FIM: Menu do inventário
```

#### **14B: Usar Item (279-315)**
```java
// INÍCIO: usarItemInventario()
private boolean usarItemInventario() {
    // Lista itens disponíveis
    // Permite escolher qual usar
    // Aplica efeito e consome turno
}
// FIM: Usar item
```

#### **14C: Equipar Item (320-356)**
```java
// INÍCIO: equiparItemInventario()
private void equiparItemInventario() {
    // Lista equipamentos
    // Permite equipar
    // Não consome turno
}
// FIM: Equipar item
```

#### **14D: Inventário Detalhado (361-394)**
```java
// INÍCIO: verInventarioDetalhado()
private void verInventarioDetalhado() {
    // Efeito visual gradual
    // Lista detalhada com pausas
    // Não consome turno
}
// FIM: Inventário detalhado
```

---

### 🎮 **COMMIT 15: Menu do Herói**
**Linhas: 427-439**
```java
// INÍCIO: exibirMenuHeroi()
private void exibirMenuHeroi() {
    // Exibe as 5 opções do herói
    // Com formatação e cores
}
// FIM: Menu do herói
```

---

## 📊 **Resumo da Organização**

| Commit | Funcionalidade | Linhas | Complexidade |
|--------|---------------|--------|--------------|
| 1 | Estrutura Base | 1-30 | ⭐ |
| 2 | Inicialização | 32-37 | ⭐ |
| 3 | Loop Principal | 38-46 | ⭐⭐ |
| 4 | Menu do Herói | 47-71 | ⭐⭐⭐ |
| 5 | Sistema Ataque | 72-83 | ⭐⭐ |
| 6 | Habilidade Especial | 84-96 | ⭐⭐ |
| 7 | **Sistema Inventário** | 97-149 | ⭐⭐⭐⭐⭐ |
| 8 | Sistema Fuga | 150-162 | ⭐⭐ |
| 9 | Exibir Status | 163-175 | ⭐ |
| 10 | Verificação Vitória | 176-184 | ⭐⭐ |
| 11 | Turno Monstro | 185-195 | ⭐⭐ |
| 12 | Resultado Final | 196-210 | ⭐⭐ |
| 13 | Barras de Vida | 212-251 | ⭐⭐⭐ |
| 14 | **Métodos Inventário** | 253-425 | ⭐⭐⭐⭐⭐ |
| 15 | Menu Herói | 427-439 | ⭐ |

### 🔥 **Commits Mais Complexos:**
- **COMMIT 7**: Sistema de Inventário (52 linhas)
- **COMMIT 14**: Métodos do Inventário (172 linhas)

### ⚡ **Commits Mais Simples:**
- **COMMIT 2**: Inicialização (5 linhas)
- **COMMIT 9**: Exibir Status (12 linhas)
- **COMMIT 15**: Menu Herói (12 linhas)

---

**Total de Linhas**: 439  
**Arquivos Relacionados**: `Batalha.java`  
**Data**: 03/08/2025
