package com.guiccr.rpg;

import java.util.InputMismatchException; // Para tratar entrada de Scanner
import java.util.Scanner; // Para entrada do usuário

public class Batalha {

    private Heroi heroi;
    private Monstro monstro;
    private Scanner scanner; // Para ler a entrada do usuário durante a batalha
    private int turnos;

    // Método auxiliar privado para introduzir um pequeno atraso no console
    // Movemos ele para cá temporariamente, já que MenuPrincipal.pausar() não existe ainda.
    private void pausar(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("A pausa foi interrompida inesperadamente.");
        }
    }

    // Construtor da batalha: recebe o herói e o monstro que vão lutar
    public Batalha(Heroi heroi, Monstro monstro) {
        // Validações básicas para garantir que os combatentes não sejam nulos.
        if (heroi == null || monstro == null) {
            throw new IllegalArgumentException("Herói e Monstro não podem ser nulos para iniciar a batalha.");
        }
        this.turnos = 0; // Inicia com 0, incrementa no início do loop para ser Turno #1
        this.heroi = heroi;
        this.monstro = monstro;
        this.scanner = new Scanner(System.in); // Inicializa o Scanner para entrada do console
    }

    // Método principal para iniciar e gerenciar o fluxo da batalha
    public void iniciar() {
        System.out.println("\n=========================================");
        System.out.println("          A BATALHA COMEÇA!");
        System.out.println("=========================================");
        System.out.println(heroi.getNome() + " vs " + monstro.getNome() + " (" + monstro.getTipo() + ")!");
        pausar(3000); // Pausa inicial

        // Loop principal da batalha: continua enquanto ambos estiverem vivos
        while (heroi.estaVivo() && monstro.estaVivo()) {
            this.turnos++; // Incrementa o contador de turnos no início de CADA TURNO

            System.out.println("\n--- TURNO #" + this.turnos + " ---");
            System.out.println("----------------------------------------");
            heroi.exibirStatus(); // Exibe status do Herói
            
            System.out.println("");
            monstro.exibirStatus(); // Exibe status do Monstro
            pausar(3000); // Pausa para leitura dos status

            // --- Turno do Herói ---
            System.out.println("\n--- VEZ DE " + heroi.getNome().toUpperCase() + " ---");
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
                        System.out.println(heroi.getNome() + " não tem energia suficiente. Atacando normalmente.");
                        heroi.atacar(monstro); // Ataca normalmente se não tiver energia
                    }
                    break;
            }
            pausar(4000); // Pausa após a ação do herói

            // Verificar se o monstro foi derrotado após o ataque do herói
            if (!monstro.estaVivo()) {
                System.out.println("\n" + monstro.getNome() + " foi derrotado!");
                break; // Sai do loop principal da batalha
            }

            // --- Turno do Monstro (se o monstro ainda estiver vivo) ---
            System.out.println("\n--- VEZ DE " + monstro.getNome().toUpperCase() + " ---");
            monstro.atacar(heroi); // Monstro ataca automaticamente o herói
            pausar(4000); // Pausa após a ação do monstro

            // Verificar se o herói foi derrotado após o ataque do monstro
            if (!heroi.estaVivo()) {
                System.out.println("\n" + heroi.getNome() + " foi derrotado!");
                break; // Sai do loop principal da batalha
            }
            // Não precisa de 'turnos++;' aqui, já está no início do loop.
        } // Fim do loop while

        // --- Resultado Final da Batalha ---
        System.out.println("\n=========================================");
        System.out.println("          FIM DA BATALHA!");
        System.out.println("=========================================");
        if (heroi.estaVivo()) {
            System.out.println("🎉 VITÓRIA! " + heroi.getNome() + " derrotou " + monstro.getNome() + "!");
        } else if (monstro.estaVivo()) {
            System.out.println("💀 DERROTA! " + monstro.getNome() + " derrotou " + heroi.getNome() + ".");
        } else {
            System.out.println("🤝 EMPATE! Ambos os combatentes caíram."); // Caso ambos sejam derrotados no mesmo turno
        }

        System.out.println("\n--- RELATÓRIO DE BATALHA ---");
        System.out.printf("  Total de Turnos: %d%n", this.turnos);
        System.out.printf("  %s - Vida Final: (%d/%d)%n", heroi.getNome(), heroi.getVidaAtual(), heroi.getVidaMaxima());
        System.out.printf("  %s - Vida Final: (%d/%d)%n", monstro.getNome(), monstro.getVidaAtual(), monstro.getVidaMaxima());
        System.out.println("----------------------------------------");

        // O scanner deve ser fechado na classe Main, onde foi aberto.
        // scanner.close(); // Comente ou remova esta linha se o scanner for fechado na Main.
    }

    // Método auxiliar para exibir as opções de ação do Herói
    private void exibirMenuHeroi() {
        System.out.println("----------------------------------------");
        System.out.println("Ações disponíveis para " + heroi.getNome() + ":");
        System.out.println("1. Atacar");
        System.out.println("2. Usar Habilidade Especial (Custo: 10 Energia)");
        System.out.println("----------------------------------------");
    }
}