package com.guiccr.rpg.service;

import java.util.InputMismatchException; // Para tratar entrada de Scanner
import java.util.Scanner; // Para entrada do usuário

import com.guiccr.rpg.ConsoleColors;
import com.guiccr.rpg.MenuPrincipal;
import com.guiccr.rpg.model.Equipavel;
import com.guiccr.rpg.model.Heroi;
import com.guiccr.rpg.model.Item;
import com.guiccr.rpg.model.Monstro;
import com.guiccr.rpg.repository.RepositorioDeHerois;

import java.util.List; // Para trabalhar com listas

// =================================================================
// COMMIT 1: ESTRUTURA BASE DA CLASSE
// =================================================================
public class Batalha {

    private Heroi heroi;
    private Monstro monstro;
    private Scanner scanner; // Scanner agora será recebido via construtor
    private int turnos;

    // Construtor da batalha: recebe o herói, o monstro E o Scanner da Main
    public Batalha(Heroi heroi, Monstro monstro, Scanner scanner) {
        // Validações básicas para garantir que os combatentes não sejam nulos.
        if (heroi == null || monstro == null) {
            throw new IllegalArgumentException("Herói e Monstro não podem ser nulos para iniciar a batalha.");
        }
        // Validação do Scanner para garantir que não seja nulo
        if (scanner == null) {
            throw new IllegalArgumentException("O Scanner não pode ser nulo para a batalha.");
        }

        this.turnos = 0; // Inicia com 0, incrementa no início do loop para ser Turno #1
        this.heroi = heroi;
        this.monstro = monstro;
        this.scanner = scanner;
    }
// =================================================================
// FIM COMMIT 1: ESTRUTURA BASE DA CLASSE
// =================================================================

    // =================================================================
    // COMMIT 2: INICIALIZAÇÃO DA BATALHA
    // =================================================================
    // Método principal para iniciar e gerenciar o fluxo da batalha
    public void iniciar() {
        System.out.println("\n=== INÍCIO DA BATALHA ===");
        System.out.println(ConsoleColors.CYAN_BRIGHT + heroi.getNome() + ConsoleColors.RESET + " vs "
                + ConsoleColors.RED + monstro.getNome() + " (" + monstro.getTipo() + ")" + ConsoleColors.RESET + "!");
        MenuPrincipal.pausar(2000);
        // =================================================================
        // FIM COMMIT 2: INICIALIZAÇÃO DA BATALHA
        // =================================================================

        // =================================================================
        // COMMIT 3: LOOP PRINCIPAL DE TURNOS
        // =================================================================
        // Loop principal da batalha: continua enquanto ambos estiverem vivos
        while (heroi.estaVivo() && monstro.estaVivo()) {
            this.turnos++; // Incrementa o contador de turnos no início de CADA TURNO

            System.out.println("\n--- TURNO #" + this.turnos + " ---");
            System.out.println("----------------------------------------");
            // Exibe apenas a vida dos combatentes durante os turnos
            exibirVidaCombatentes();
            MenuPrincipal.pausar(1500);
            // =================================================================
            // FIM COMMIT 3: LOOP PRINCIPAL DE TURNOS
            // =================================================================

            // =================================================================
            // COMMIT 4: SISTEMA DE MENU DO HERÓI
            // =================================================================
            // --- Turno do Herói ---
            boolean acaoRealizada = false;
            while (!acaoRealizada && heroi.estaVivo() && monstro.estaVivo()) {
                System.out.println("\n--- VEZ DE " + ConsoleColors.CYAN_BRIGHT + heroi.getNome().toUpperCase()
                        + ConsoleColors.RESET + " ---");
                exibirMenuHeroi();
                int escolha = 0;
                boolean entradaValida = false;

                // Loop para garantir uma entrada válida do usuário (Tratamento de Exceções)
                while (!entradaValida) {
                    try {
                        System.out.print("Escolha sua ação: ");
                        escolha = scanner.nextInt(); // Tenta ler um número
                        if (escolha >= 1 && escolha <= 5) {
                            entradaValida = true;
                        } else {
                            System.out.println(
                                    "Opção inválida. Por favor, digite 1 (Atacar), 2 (Habilidade Especial), 3 (Inventário), 4 (Fugir) ou 5 (Status).");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida. Por favor, digite um número (1-5).");
                        scanner.next(); // Limpa o buffer do scanner para evitar loop infinito
                    }
                }
                scanner.nextLine(); // Consome a quebra de linha pendente após nextInt()
                // =================================================================
                // FIM COMMIT 4: SISTEMA DE MENU DO HERÓI
                // =================================================================

                // Processar a escolha do Herói
                switch (escolha) {
                    // =================================================================
                    // COMMIT 5: AÇÃO 1 - SISTEMA DE ATAQUE
                    // =================================================================
                    case 1:
                        System.out.println("\n" + ConsoleColors.RED + "=== ATAQUE ===" + ConsoleColors.RESET);
                        MenuPrincipal.pausar(500);
                        System.out.println(heroi.getNome() + " prepara-se para atacar...");
                        MenuPrincipal.pausar(800);
                        heroi.atacar(monstro);
                        MenuPrincipal.pausar(1500);
                        acaoRealizada = true; // Ação que consome turno
                        break;
                    // =================================================================
                    // FIM COMMIT 5: AÇÃO 1 - SISTEMA DE ATAQUE
                    // =================================================================
                    
                    // =================================================================
                    // COMMIT 6: AÇÃO 2 - HABILIDADE ESPECIAL
                    // =================================================================
                    case 2:
                        if (heroi.getEnergia() >= 10) {
                            System.out.println("\n" + ConsoleColors.YELLOW + "=== HABILIDADE ESPECIAL ===" + ConsoleColors.RESET);
                            MenuPrincipal.pausar(500);
                            System.out.println(heroi.getNome() + " concentra energia para uma habilidade especial...");
                            MenuPrincipal.pausar(1000);
                            heroi.usarHabilidadeEspecial(monstro);
                            MenuPrincipal.pausar(1500);
                            acaoRealizada = true; // Ação que consome turno
                        } else {
                            System.out.println("\n" + ConsoleColors.RED + "=== ENERGIA INSUFICIENTE ===" + ConsoleColors.RESET);
                            MenuPrincipal.pausar(500);
                            System.out.println(ConsoleColors.CYAN_BRIGHT + heroi.getNome() + ConsoleColors.RESET
                                    + " não tem energia suficiente para usar habilidade especial.");
                            System.out.println("Energia necessária: 10 | Energia atual: " + heroi.getEnergia());
                            MenuPrincipal.pausar(2000);
                        }
                        break;
                    // =================================================================
                    // FIM COMMIT 6: AÇÃO 2 - HABILIDADE ESPECIAL
                    // =================================================================
                    
                    // =================================================================
                    // COMMIT 7: AÇÃO 3 - SISTEMA DE INVENTÁRIO (MEGA COMMIT)
                    // =================================================================
                    case 3:
                        // Submenu do Inventário
                        boolean voltarAoMenuPrincipal = false;
                        while (!voltarAoMenuPrincipal && !acaoRealizada) {
                            System.out.println("\n" + ConsoleColors.CYAN + "=== INVENTÁRIO ===" + ConsoleColors.RESET);
                            MenuPrincipal.pausar(500);
                            System.out.println("Abrindo inventário...");
                            MenuPrincipal.pausar(800);
                            
                            boolean inventarioValido = exibirMenuInventario();
                            MenuPrincipal.pausar(1000);
                            
                            if (!inventarioValido) {
                                System.out.println(ConsoleColors.RED + "Inventário vazio! Retornando ao menu principal..." + ConsoleColors.RESET);
                                MenuPrincipal.pausar(1500);
                                break;
                            }
                            
                            int opcaoInventario = 0;
                            boolean entradaInventarioValida = false;
                            
                            while (!entradaInventarioValida) {
                                try {
                                    System.out.print("Escolha uma opção do inventário: ");
                                    opcaoInventario = scanner.nextInt();
                                    if (opcaoInventario >= 1 && opcaoInventario <= 5) {
                                        entradaInventarioValida = true;
                                    } else {
                                        System.out.println(ConsoleColors.RED + "Opção inválida. Digite 1, 2, 3, 4 ou 5." + ConsoleColors.RESET);
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println(ConsoleColors.RED + "Entrada inválida. Digite um número (1-5)." + ConsoleColors.RESET);
                                    scanner.next();
                                }
                            }
                            scanner.nextLine();
                            
                            switch (opcaoInventario) {
                                case 1: // Usar Item
                                    if (usarItemInventario()) {
                                        acaoRealizada = true; // Consome turno
                                        voltarAoMenuPrincipal = true;
                                    }
                                    break;
                                case 2: // Equipar Item
                                    equiparItemInventario(); // Não consome turno
                                    break;
                                case 3: // Desequipar Item
                                    desequiparItemInventario(); // Não consome turno
                                    break;
                                case 4: // Ver Inventário Detalhado
                                    verInventarioDetalhado(); // Não consome turno
                                    break;
                                case 5: // Voltar
                                    System.out.println(ConsoleColors.YELLOW + "Retornando ao menu de batalha..." + ConsoleColors.RESET);
                                    MenuPrincipal.pausar(800);
                                    voltarAoMenuPrincipal = true;
                                    break;
                            }
                        }
                        break;
                    // =================================================================
                    // FIM COMMIT 7: AÇÃO 3 - SISTEMA DE INVENTÁRIO (MEGA COMMIT)
                    // =================================================================
                    
                    // =================================================================
                    // COMMIT 8: AÇÃO 4 - SISTEMA DE FUGA
                    // =================================================================
                    case 4:
                        System.out.println("\n" + ConsoleColors.YELLOW + "=== TENTATIVA DE FUGA ===" + ConsoleColors.RESET);
                        MenuPrincipal.pausar(500);
                        System.out.println(heroi.getNome() + " procura uma saída...");
                        MenuPrincipal.pausar(1000);
                        System.out.println("Tentando escapar...");
                        MenuPrincipal.pausar(1500);
                        System.out.println(ConsoleColors.RED + "A fuga falhou!" + ConsoleColors.RESET);
                        MenuPrincipal.pausar(500);
                        System.out.println(ConsoleColors.CYAN_BRIGHT + heroi.getNome() + ConsoleColors.RESET
                                + " tentou fugir da batalha, mas não conseguiu!");
                        MenuPrincipal.pausar(1500);
                        acaoRealizada = true; // Ação que consome turno
                        break;
                    // =================================================================
                    // FIM COMMIT 8: AÇÃO 4 - SISTEMA DE FUGA
                    // =================================================================
                    
                    // =================================================================
                    // COMMIT 9: AÇÃO 5 - EXIBIR STATUS
                    // =================================================================
                    case 5:
                        // Exibe o status do herói 
                        System.out.println("\n" + ConsoleColors.PURPLE + "=== VERIFICANDO STATUS ===" + ConsoleColors.RESET);
                        MenuPrincipal.pausar(500);
                        System.out.println("Analisando condição atual do herói...");
                        MenuPrincipal.pausar(800);
                        heroi.exibirStatus();
                        MenuPrincipal.pausar(1000);
                        // NÃO marca acaoRealizada = true (não consome turno)
                        System.out.println(ConsoleColors.GREEN + "(Exibir status não consome seu turno. Escolha uma ação de combate.)" + ConsoleColors.RESET);
                        MenuPrincipal.pausar(1500);
                        break;
                    // =================================================================
                    // FIM COMMIT 9: AÇÃO 5 - EXIBIR STATUS
                    // =================================================================
                }
            }
            MenuPrincipal.pausar(3000);

            // =================================================================
            // COMMIT 10: VERIFICAÇÃO DE VITÓRIA
            // =================================================================
            // Verificar se o monstro foi derrotado após o ataque do herói
            if (!monstro.estaVivo()) {
                System.out.println(
                        "\n" + ConsoleColors.RED + monstro.getNome() + ConsoleColors.RESET + " foi derrotado!");
                heroi.ganharExperiencia(monstro.getExpConcedida()); // Chama o método do Herói

                MenuPrincipal.pausar(2500); // Pausa para o jogador ler o ganho de EXP
                break; // Sai do loop principal da batalha
            }
            // =================================================================
            // FIM COMMIT 10: VERIFICAÇÃO DE VITÓRIA
            // =================================================================

            // =================================================================
            // COMMIT 11: TURNO DO MONSTRO
            // =================================================================
            // --- Turno do Monstro (se o monstro ainda estiver vivo) ---
            System.out.println("\n--- VEZ DE " + ConsoleColors.RED + monstro.getNome().toUpperCase()
                    + ConsoleColors.RESET + " ---");
            monstro.atacar(heroi); // Monstro ataca automaticamente o herói
            MenuPrincipal.pausar(3000);

            // Verificar se o herói foi derrotado após o ataque do monstro
            if (!heroi.estaVivo()) {
                System.out.println(
                        "\n" + ConsoleColors.CYAN_BRIGHT + heroi.getNome() + ConsoleColors.RESET + " foi derrotado!");
                break; // Sai do loop principal da batalha
            }
            // =================================================================
            // FIM COMMIT 11: TURNO DO MONSTRO
            // =================================================================
        } // Fim do loop while

        // =================================================================
        // COMMIT 12: RESULTADO FINAL DA BATALHA
        // =================================================================
        // --- Resultado Final da Batalha ---
        System.out.println("\n=========================================");
        System.out.println("FIM DA BATALHA!");
        System.out.println("=========================================");
        if (heroi.estaVivo()) {
            System.out.println("🎉 VITÓRIA! " + ConsoleColors.CYAN_BRIGHT + heroi.getNome() + ConsoleColors.RESET
                    + " derrotou " + ConsoleColors.RED + monstro.getNome() + ConsoleColors.RESET + "!");
            // aqui vou desequipar os itens do heroi para que nao tenha bug de conseguir aumentar o dano infinitamente
            heroi.desequiparItem("arma");


            RepositorioDeHerois.atualizarHeroi(heroi);
        } else if (monstro.estaVivo()) {
            System.out.println("💀 DERROTA! " + ConsoleColors.RED + monstro.getNome() + ConsoleColors.RESET
                    + " derrotou " + ConsoleColors.CYAN_BRIGHT + heroi.getNome() + ConsoleColors.RESET + ".");
            heroi.desequiparItem("arma");
            RepositorioDeHerois.atualizarHeroi(heroi);
        } else {
            System.out.println("🤝 EMPATE! Ambos os combatentes caíram."); // Caso ambos sejam derrotados no mesmo turno
        }

        System.out.println("\n--- RELATÓRIO DE BATALHA ---");
        System.out.printf("  Total de Turnos: %d%n", this.turnos);
        System.out.printf("  %s - Vida Final: (%d/%d)%n", heroi.getNome(), heroi.getVidaAtual(), heroi.getVidaMaxima());
        System.out.printf("  %s - Vida Final: (%d/%d)%n", monstro.getNome(), monstro.getVidaAtual(),
                monstro.getVidaMaxima());
        System.out.println("----------------------------------------");
        // =================================================================
        // FIM COMMIT 12: RESULTADO FINAL DA BATALHA
        // =================================================================

    }

    // =================================================================
    // COMMIT 13: SISTEMA DE BARRAS DE VIDA
    // =================================================================
    /**
     * Exibe apenas a vida dos combatentes com barras visuais
     */
    private void exibirVidaCombatentes() {
        final int COMPRIMENTO_BARRA_HP = 20;
        
        // === BARRA DE VIDA DO HERÓI ===
        double porcentagemVidaHeroi = (double) heroi.getVidaAtual() / heroi.getVidaMaxima();
        int partesPreenchidas = (int) (porcentagemVidaHeroi * COMPRIMENTO_BARRA_HP);
        int partesVazias = COMPRIMENTO_BARRA_HP - partesPreenchidas;
        
        StringBuilder barraHeroi = new StringBuilder();
        barraHeroi.append("[");
        for (int i = 0; i < partesPreenchidas; i++) barraHeroi.append("█");
        for (int i = 0; i < partesVazias; i++) barraHeroi.append("-");
        barraHeroi.append("]");
        
        String corVidaHeroi = (porcentagemVidaHeroi > 0.5) ? ConsoleColors.GREEN : 
                              (porcentagemVidaHeroi > 0.2) ? ConsoleColors.YELLOW : ConsoleColors.RED;
        
        System.out.printf("%-15s HP: %s%s (%d/%d)%s%n",
                          ConsoleColors.CYAN_BRIGHT + heroi.getNome() + ConsoleColors.RESET,
                          corVidaHeroi + barraHeroi.toString() + ConsoleColors.RESET,
                          corVidaHeroi, heroi.getVidaAtual(), heroi.getVidaMaxima(), ConsoleColors.RESET);
        
        // === BARRA DE VIDA DO MONSTRO ===
        double porcentagemVidaMonstro = (double) monstro.getVidaAtual() / monstro.getVidaMaxima();
        partesPreenchidas = (int) (porcentagemVidaMonstro * COMPRIMENTO_BARRA_HP);
        partesVazias = COMPRIMENTO_BARRA_HP - partesPreenchidas;
        
        StringBuilder barraMonstro = new StringBuilder();
        barraMonstro.append("[");
        for (int i = 0; i < partesPreenchidas; i++) barraMonstro.append("█");
        for (int i = 0; i < partesVazias; i++) barraMonstro.append("-");
        barraMonstro.append("]");
        
        String corVidaMonstro = (porcentagemVidaMonstro > 0.5) ? ConsoleColors.GREEN : 
                                (porcentagemVidaMonstro > 0.2) ? ConsoleColors.YELLOW : ConsoleColors.RED;
        
        System.out.printf("%-15s HP: %s%s (%d/%d)%s%n",
                          ConsoleColors.RED + monstro.getNome() + ConsoleColors.RESET,
                          corVidaMonstro + barraMonstro.toString() + ConsoleColors.RESET,
                          corVidaMonstro, monstro.getVidaAtual(), monstro.getVidaMaxima(), ConsoleColors.RESET);
    }
    // =================================================================
    // FIM COMMIT 13: SISTEMA DE BARRAS DE VIDA
    // =================================================================

    // =================================================================
    // COMMIT 14: MÉTODOS AUXILIARES DO INVENTÁRIO
    // =================================================================
    // === MÉTODOS AUXILIARES DO INVENTÁRIO ===
    
    /**
     * Exibe o menu do inventário e retorna se há itens disponíveis
     */
    private boolean exibirMenuInventario() {
        System.out.println("----------------------------------------");
        System.out.println("           INVENTÁRIO DE " + ConsoleColors.CYAN_BRIGHT + heroi.getNome().toUpperCase() + ConsoleColors.RESET);
        System.out.println("----------------------------------------");
    System.out.println("1. Usar Item");
    System.out.println("2. Equipar Equipamento");
    System.out.println("3. Desequipar Equipamento");
    System.out.println("4. Ver Inventário Detalhado");
        System.out.println("5. Voltar ao Menu de Batalha");
        System.out.println("----------------------------------------");
        
        // Verifica se há itens no inventário (sem exibi-los aqui)
        List<Item> itens = heroi.getInventario().listarItens();
        boolean temItens = itens.size() > 0;
        
        if (temItens) {
            System.out.println(ConsoleColors.GREEN + "Itens disponíveis: " + itens.size() + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "Inventário vazio!" + ConsoleColors.RESET);
        }
        
        return temItens;
    }
    
    /**
     * Gerencia o uso de itens do inventário
     */
    private boolean usarItemInventario() {
        System.out.println("\n" + ConsoleColors.GREEN + "=== USAR ITEM ===" + ConsoleColors.RESET);
        MenuPrincipal.pausar(500);
        System.out.println("Procurando itens utilizáveis no inventário...");
        MenuPrincipal.pausar(800);
        
        List<Item> itens = heroi.getInventario().listarItens();
        if (itens.isEmpty()) {
            System.out.println(ConsoleColors.RED + "Não há itens para usar!" + ConsoleColors.RESET);
            MenuPrincipal.pausar(1500);
            return false;
        }
        
        // Exibe apenas a lista de itens sem duplicação
        System.out.println("Itens disponíveis:");
        for (int i = 0; i < itens.size(); i++) {
            Item item = itens.get(i);
            System.out.println((i + 1) + ". " + item.getNome() + " - " + item.getDescricao());
        }
        
        MenuPrincipal.pausar(1000);
        System.out.print("Digite o número do item que deseja usar (0 para cancelar): ");
        int numItem = scanner.nextInt();
        scanner.nextLine();
        
        if (numItem == 0) {
            System.out.println(ConsoleColors.YELLOW + "Uso de item cancelado." + ConsoleColors.RESET);
            MenuPrincipal.pausar(1000);
            return false;
        }
        
        int indiceItem = numItem - 1;
        if (indiceItem < 0 || indiceItem >= itens.size()) {
            System.out.println(ConsoleColors.RED + "Número inválido!" + ConsoleColors.RESET);
            MenuPrincipal.pausar(1500);
            return false;
        }
        
        System.out.println("\nUsando item...");
        MenuPrincipal.pausar(800);
    heroi.getInventario().aplicarEfeitoItem(indiceItem, heroi);
    MenuPrincipal.pausar(1500);
    return false; // Usar item não consome turno
    }
    
    /**
     * Gerencia equipar itens do inventário
     */
    private void equiparItemInventario() {
        System.out.println("\n" + ConsoleColors.YELLOW + "=== EQUIPAR EQUIPAMENTO ===" + ConsoleColors.RESET);
        MenuPrincipal.pausar(500);
        System.out.println("Verificando equipamentos disponíveis...");
        MenuPrincipal.pausar(800);
        
        List<Item> itens = heroi.getInventario().listarItens();
        if (itens.isEmpty()) {
            System.out.println(ConsoleColors.RED + "Não há equipamentos para equipar!" + ConsoleColors.RESET);
            MenuPrincipal.pausar(1500);
            return;
        }
        
        // Exibe apenas a lista de itens sem duplicação
        System.out.println("Equipamentos disponíveis:");
        for (int i = 0; i < itens.size(); i++) {
            Item item = itens.get(i);
            System.out.println((i + 1) + ". " + item.getNome() + " - " + item.getDescricao());
        }
        
        MenuPrincipal.pausar(1000);
        System.out.print("Digite o número do equipamento que deseja equipar (0 para cancelar): ");
        int numEquipar = scanner.nextInt();
        scanner.nextLine();
        
        if (numEquipar == 0) {
            System.out.println(ConsoleColors.YELLOW + "Equipar cancelado." + ConsoleColors.RESET);
            MenuPrincipal.pausar(1000);
            return;
        }
        
        int indiceEquipar = numEquipar - 1;
        if (indiceEquipar < 0 || indiceEquipar >= itens.size()) {
            System.out.println(ConsoleColors.RED + "Número inválido!" + ConsoleColors.RESET);
            MenuPrincipal.pausar(1500);
            return;
        }
        
        System.out.println("\nEquipando item...");
        MenuPrincipal.pausar(800);
        
        // Usa o novo método que integra com o sistema de equipamentos
        if (heroi.getInventario().equiparItemDoInventario(indiceEquipar, heroi)) {
            System.out.println(ConsoleColors.GREEN + "Equipamento alterado com sucesso!" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "Não foi possível equipar o item." + ConsoleColors.RESET);
        }
        MenuPrincipal.pausar(1500);
    }
    
    /**
     * Gerencia desequipar itens equipados
     */
    private void desequiparItemInventario() {
        System.out.println("\n" + ConsoleColors.PURPLE + "=== DESEQUIPAR EQUIPAMENTO ===" + ConsoleColors.RESET);
        MenuPrincipal.pausar(500);
        System.out.println("Verificando equipamentos equipados...");
        MenuPrincipal.pausar(800);
        
        // Verifica quais equipamentos estão equipados
        var equipamentosEquipados = heroi.getEquipamentos();
        if (equipamentosEquipados.isEmpty()) {
            System.out.println(ConsoleColors.RED + "Nenhum equipamento está equipado no momento!" + ConsoleColors.RESET);
            MenuPrincipal.pausar(1500);
            return;
        }
        
        // Exibe os equipamentos equipados
        System.out.println("Equipamentos equipados:");
        java.util.List<String> tiposEquipados = new java.util.ArrayList<>(equipamentosEquipados.keySet());
        for (int i = 0; i < tiposEquipados.size(); i++) {
            String tipo = tiposEquipados.get(i);
            Equipavel equipamento = equipamentosEquipados.get(tipo);
            System.out.println((i + 1) + ". " + tipo.toUpperCase() + ": " + 
                             ((Item) equipamento).getNome() + " - " + ((Item) equipamento).getDescricao());
        }
        
        MenuPrincipal.pausar(1000);
        System.out.print("Digite o número do equipamento que deseja desequipar (0 para cancelar): ");
        int numDesequipar = scanner.nextInt();
        scanner.nextLine();
        
        if (numDesequipar == 0) {
            System.out.println(ConsoleColors.YELLOW + "Desequipar cancelado." + ConsoleColors.RESET);
            MenuPrincipal.pausar(1000);
            return;
        }
        
        int indiceDesequipar = numDesequipar - 1;
        if (indiceDesequipar < 0 || indiceDesequipar >= tiposEquipados.size()) {
            System.out.println(ConsoleColors.RED + "Número inválido!" + ConsoleColors.RESET);
            MenuPrincipal.pausar(1500);
            return;
        }
        
        String tipoParaDesequipar = tiposEquipados.get(indiceDesequipar);
        System.out.println("\nDesequipando item...");
        MenuPrincipal.pausar(800);
        
        // Desequipa o item e adiciona de volta ao inventário
        if (heroi.desequiparItemParaInventario(tipoParaDesequipar)) {
            System.out.println(ConsoleColors.GREEN + "Equipamento desequipado com sucesso!" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "Não foi possível desequipar o item." + ConsoleColors.RESET);
        }
        MenuPrincipal.pausar(1500);
    }
    
    /**
     * Exibe o inventário detalhado com efeito visual gradual
     */
    private void verInventarioDetalhado() {
        System.out.println("\n" + ConsoleColors.PURPLE + "=== INVENTÁRIO DETALHADO ===" + ConsoleColors.RESET);
        MenuPrincipal.pausar(500);
        System.out.println("Analisando seus itens...");
        MenuPrincipal.pausar(800);
        System.out.println("Verificando condição dos equipamentos...");
        MenuPrincipal.pausar(600);
        System.out.println("Organizando inventário...");
        MenuPrincipal.pausar(700);
        
        List<Item> itens = heroi.getInventario().listarItens();
        if (itens.isEmpty()) {
            System.out.println(ConsoleColors.RED + "Seu inventário está vazio." + ConsoleColors.RESET);
            MenuPrincipal.pausar(1000);
            System.out.println("Procure por itens durante suas aventuras!");
        } else {
            System.out.println(ConsoleColors.GREEN + "Inventário catalogado com sucesso!" + ConsoleColors.RESET);
            MenuPrincipal.pausar(500);
            System.out.println("\n" + ConsoleColors.CYAN + "--- RELATÓRIO DETALHADO ---" + ConsoleColors.RESET);
            MenuPrincipal.pausar(500);
            
            // Exibe cada item individualmente com detalhes
            for (int i = 0; i < itens.size(); i++) {
                Item item = itens.get(i);
                System.out.println((i + 1) + ". " + ConsoleColors.YELLOW + item.getNome() + ConsoleColors.RESET + " - " + item.getDescricao());
                // Exibe detalhes extras conforme o tipo
                if (item instanceof com.guiccr.rpg.model.PocaoVida) {
                    com.guiccr.rpg.model.PocaoVida pocao = (com.guiccr.rpg.model.PocaoVida) item;
                    System.out.println(ConsoleColors.GREEN + "Tipo: Poção de Vida | Cura: " + pocao.getQuantidadeVida() + ConsoleColors.RESET);
                } else if (item instanceof com.guiccr.rpg.model.Espada) {
                    com.guiccr.rpg.model.Espada espada = (com.guiccr.rpg.model.Espada) item;
                    System.out.println(ConsoleColors.MAGENTA + "Tipo: Espada | Dano: " + espada.getDanoEspada() + " | Qualidade: " + espada.getQualidade() + ConsoleColors.RESET);
                } else if (item instanceof com.guiccr.rpg.model.ItemGenerico) {
                    System.out.println(ConsoleColors.CYAN + "Tipo: Item Genérico" + ConsoleColors.RESET);
                }
                MenuPrincipal.pausar(400); // Pausa entre cada item
            }
            
            MenuPrincipal.pausar(500);
            System.out.println("\n" + ConsoleColors.CYAN + "Total de itens catalogados: " + itens.size() + ConsoleColors.RESET);
        }
        
        MenuPrincipal.pausar(1500);
        System.out.println(ConsoleColors.GREEN + "Pressione Enter para continuar..." + ConsoleColors.RESET);
        scanner.nextLine();
    }
    // =================================================================
    // FIM COMMIT 14: MÉTODOS AUXILIARES DO INVENTÁRIO
    // =================================================================

    // =================================================================
    // COMMIT 15: MENU DO HERÓI
    // =================================================================
    // Método auxiliar para exibir as opções de ação do Herói
    private void exibirMenuHeroi() {
        System.out.println("----------------------------------------");
        System.out.println(
                "Ações disponíveis para " + ConsoleColors.CYAN_BRIGHT + heroi.getNome() + ConsoleColors.RESET + ":");
        System.out.println("1. Atacar");
        System.out.println("2. Usar Habilidade Especial (Custo: 10 Energia)");
        System.out.println("3. Inventário (usar itens, equipar equipamentos)");
        System.out.println("4. Fugir da Batalha (Não garantido)");
        System.out.println("5. Exibir Status do Herói (não consome turno)");
        System.out.println("----------------------------------------");
    }
    // =================================================================
    // FIM COMMIT 15: MENU DO HERÓI
    // =================================================================
}
