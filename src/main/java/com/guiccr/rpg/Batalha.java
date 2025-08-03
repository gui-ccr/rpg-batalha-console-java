package com.guiccr.rpg;

import java.util.InputMismatchException; // Para tratar entrada de Scanner
import java.util.Scanner; // Para entrada do usuário

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

    // Método principal para iniciar e gerenciar o fluxo da batalha
    public void iniciar() {
        System.out.println("\n=========================================");
        System.out.println("          A BATALHA COMEÇA!");
        System.out.println("=========================================");
        System.out.println(ConsoleColors.CYAN_BRIGHT + heroi.getNome() + ConsoleColors.RESET + " vs "
                + ConsoleColors.BLACK + monstro.getNome() + " (" + monstro.getTipo() + ")" + ConsoleColors.RESET + "!");
        MenuPrincipal.pausar(2000);

        // Loop principal da batalha: continua enquanto ambos estiverem vivos
        while (heroi.estaVivo() && monstro.estaVivo()) {
            this.turnos++; // Incrementa o contador de turnos no início de CADA TURNO

            System.out.println("\n--- TURNO #" + this.turnos + " ---");
            System.out.println("----------------------------------------");
            heroi.exibirStatus(); // Exibe status do Herói
            monstro.exibirStatus(); // Exibe status do Monstro
            MenuPrincipal.pausar(2200);

            // --- Turno do Herói ---
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
                    if (escolha >= 1 && escolha <= 2) {
                        entradaValida = true;
                    } else {
                        System.out.println("Opção inválida. Por favor, digite 1 (Atacar) ou 2 (Habilidade Especial).");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número (1 ou 2).");
                    scanner.next(); // Limpa o buffer do scanner para evitar loop infinito
                }
            }
            scanner.nextLine(); // Consome a quebra de linha pendente após nextInt()

            // Processar a escolha do Herói
            switch (escolha) {
                case 1:
                    heroi.atacar(monstro);
                    break;
                case 2:
                    if (heroi.getEnergia() >= 10) {
                        heroi.usarHabilidadeEspecial(monstro);
                    } else {
                        System.out.println(ConsoleColors.CYAN_BRIGHT + heroi.getNome() + ConsoleColors.RESET
                                + " não tem energia suficiente.");
                    }
                    break;
            }
            MenuPrincipal.pausar(3000);

            // Verificar se o monstro foi derrotado após o ataque do herói
            if (!monstro.estaVivo()) {
                System.out.println(
                        "\n" + ConsoleColors.BLACK + monstro.getNome() + ConsoleColors.RESET + " foi derrotado!");
                heroi.ganharExperiencia(monstro.getExpConcedida()); // Chama o método do Herói

                MenuPrincipal.pausar(2500); // Pausa para o jogador ler o ganho de EXP
                break; // Sai do loop principal da batalh
            }

            // --- Turno do Monstro (se o monstro ainda estiver vivo) ---
            System.out.println("\n--- VEZ DE " + ConsoleColors.BLACK + monstro.getNome().toUpperCase()
                    + ConsoleColors.RESET + " ---");
            monstro.atacar(heroi); // Monstro ataca automaticamente o herói
            MenuPrincipal.pausar(3000);

            // Verificar se o herói foi derrotado após o ataque do monstro
            if (!heroi.estaVivo()) {
                System.out.println(
                        "\n" + ConsoleColors.CYAN_BRIGHT + heroi.getNome() + ConsoleColors.RESET + " foi derrotado!");
                break; // Sai do loop principal da batalha
            }
        } // Fim do loop while

        // --- Resultado Final da Batalha ---
        System.out.println("\n=========================================");
        System.out.println("          FIM DA BATALHA!");
        System.out.println("=========================================");
        if (heroi.estaVivo()) {
            System.out.println("🎉 VITÓRIA! " + ConsoleColors.CYAN_BRIGHT + heroi.getNome() + ConsoleColors.RESET
                    + " derrotou " + ConsoleColors.BLACK + monstro.getNome() + ConsoleColors.RESET + "!");
            RepositorioDeHerois.atualizarHeroi(heroi);
        } else if (monstro.estaVivo()) {
            System.out.println("💀 DERROTA! " + ConsoleColors.BLACK + monstro.getNome() + ConsoleColors.RESET
                    + " derrotou " + ConsoleColors.CYAN_BRIGHT + heroi.getNome() + ConsoleColors.RESET + ".");
        } else {
            System.out.println("🤝 EMPATE! Ambos os combatentes caíram."); // Caso ambos sejam derrotados no mesmo turno
        }

        System.out.println("\n--- RELATÓRIO DE BATALHA ---");
        System.out.printf("  Total de Turnos: %d%n", this.turnos);
        System.out.printf("  %s - Vida Final: (%d/%d)%n", heroi.getNome(), heroi.getVidaAtual(), heroi.getVidaMaxima());
        System.out.printf("  %s - Vida Final: (%d/%d)%n", monstro.getNome(), monstro.getVidaAtual(),
                monstro.getVidaMaxima());
        System.out.println("----------------------------------------");

    }

    // Método auxiliar para exibir as opções de ação do Herói
    private void exibirMenuHeroi() {
        System.out.println("----------------------------------------");
        System.out.println(
                "Ações disponíveis para " + ConsoleColors.CYAN_BRIGHT + heroi.getNome() + ConsoleColors.RESET + ":");
        System.out.println("1. Atacar");
        System.out.println("2. Usar Habilidade Especial (Custo: 10 Energia)");
        System.out.println("----------------------------------------");
    }
}