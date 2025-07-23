package com.guiccr.rpg;

import java.util.InputMismatchException; // Para tratar entrada de Scanner
import java.util.Scanner; // Para entrada do usuário

public class Batalha {

    private Heroi heroi;
    private Monstro monstro;
    private Scanner scanner; // Para ler a entrada do usuário durante a batalha

    // Construtor da batalha: recebe o herói e o monstro que vão lutar
    public Batalha(Heroi heroi, Monstro monstro) {
        // Validações básicas para garantir que os combatentes não sejam nulos.
        if (heroi == null || monstro == null) {
            throw new IllegalArgumentException("Herói e Monstro não podem ser nulos para iniciar a batalha.");
        }
        this.heroi = heroi;
        this.monstro = monstro;
        this.scanner = new Scanner(System.in); // Inicializa o Scanner para entrada do console
    }

    // Método principal para iniciar e gerenciar o fluxo da batalha
    public void iniciar() {
        System.out.println("\n--- A BATALHA COMEÇA! ---");
        System.out.println(heroi.getNome() + " vs " + monstro.getNome() + "!");

        // Loop principal da batalha: continua enquanto ambos estiverem vivos
        while (heroi.estaVivo() && monstro.estaVivo()) {
            // Exibir o status atual de ambos os combatentes no início do turno
            System.out.println("\n--- Status Atual ---");
            heroi.exibirStatus();
            monstro.exibirStatus();

            // --- Turno do Herói ---
            System.out.println("\n--- Turno de " + heroi.getNome() + " ---");
            exibirMenuHeroi();
            int escolha = 0;
            boolean entradaValida = false;

            // Loop para garantir uma entrada válida do usuário (Tratamento de Exceções)
            while (!entradaValida) {
                try {
                    System.out.print("Escolha sua ação: ");
                    escolha = scanner.nextInt(); // Tenta ler um número
                    if (escolha >= 1 && escolha <= 2) { // As opções são 1 (Atacar) e 2 (Habilidade Especial)
                        entradaValida = true;
                    } else {
                        System.out.println("Opção inválida. Por favor, digite 1 ou 2.");
                    }
                } catch (InputMismatchException e) { // Captura se o usuário digitar algo que não é número
                    System.out.println("Entrada inválida. Por favor, digite um número.");
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
                    heroi.usarHabilidadeEspecial(monstro); // Se o herói não tiver energia, ele avisará
                    break;
            }

            // Verificar se o monstro foi derrotado após o ataque do herói
            if (!monstro.estaVivo()) {
                System.out.println("\n" + monstro.getNome() + " foi derrotado!");
                break; // Sai do loop principal da batalha
            }

            // --- Turno do Monstro (se o monstro ainda estiver vivo) ---
            System.out.println("\n--- Turno de " + monstro.getNome() + " ---");
            monstro.atacar(heroi); // Monstro ataca automaticamente o herói

            // Verificar se o herói foi derrotado após o ataque do monstro
            if (!heroi.estaVivo()) {
                System.out.println("\n" + heroi.getNome() + " foi derrotado!");
                break; // Sai do loop principal da batalha
            }
        } // Fim do loop while

        // --- Resultado Final da Batalha ---
        System.out.println("\n--- Fim da Batalha ---");
        if (heroi.estaVivo()) {
            System.out.println("VITÓRIA! " + heroi.getNome() + " derrotou " + monstro.getNome() + "!");
        } else if (monstro.estaVivo()) {
            System.out.println("DERROTA! " + monstro.getNome() + " derrotou " + heroi.getNome() + ".");
        } else {
            System.out.println("EMPATE! Ambos os combatentes caíram."); // Caso ambos sejam derrotados no mesmo turno
        }

        scanner.close(); // Fecha o scanner para liberar recursos
    }

    // Método auxiliar para exibir as opções de ação do Herói
    private void exibirMenuHeroi() {
        System.out.println("Ações disponíveis:");
        System.out.println("1. Atacar");
        System.out.println("2. Usar Habilidade Especial"); // Custa energia
    }
}