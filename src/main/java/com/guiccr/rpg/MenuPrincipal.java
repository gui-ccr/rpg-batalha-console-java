package com.guiccr.rpg;

import java.util.Scanner;
import java.util.InputMismatchException;

// A classe MenuPrincipal é uma classe utilitária (não precisa de instâncias)
// por isso, seus métodos são estáticos.
public class MenuPrincipal {

    // Método estático para exibir as opções do menu principal do jogo
    public static void exibirOpcoes() {
        System.out.println("\n========================================================");
        System.out.println("          RPG BATALHA POR TURNOS - CÓDIGO AMALDIÇOADO");
        System.out.println("========================================================");
        System.out.println("\nEscolha o que deseja fazer:");
        System.out.println(" 1: Iniciar Nova Batalha");
        System.out.println(" 2: Sair do Jogo");
        System.out.print("Sua opção: ");
    }

    // Método estático para ler a opção do usuário, com tratamento de exceções
    // Ele recebe o Scanner da Main, garantindo que o mesmo Scanner seja usado e fechado uma vez.
    public static int lerOpcao(Scanner scanner) {
        int escolha = -1; // Valor padrão inválido para entrar no loop
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                escolha = scanner.nextInt(); // Tenta ler um número
                if (escolha >= 1 && escolha <= 2) { // As opções válidas são 1 ou 2
                    entradaValida = true;
                } else {
                    System.out.println("\n--- Opção inválida! ---"); // Mensagem de erro clara
                    System.out.println("Por favor, digite 1 para INICIAR a batalha ou 2 para SAIR do jogo.");
                    exibirOpcoes(); // Reexibe as opções completas após erro
                }
            } catch (InputMismatchException e) { // Captura se o usuário digitar algo que não é número
                System.out.println("\n--- Entrada inválida! ---");
                System.out.println("O que você digitou não é um número. Por favor, digite 1 ou 2.");
                scanner.next(); // Limpa o buffer do scanner para evitar loop infinito
                exibirOpcoes(); // Reexibe as opções completas
            }
        }
        scanner.nextLine(); // Consome a quebra de linha pendente após nextInt()
        return escolha; // Retorna a escolha válida
    }

    // Método estático para introduzir um pequeno atraso no console (melhora a experiência do usuário)
    public static void pausar(int milissegundos) {
        try {
            Thread.sleep(milissegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaura o estado de interrupção
            System.err.println("A pausa foi interrompida inesperadamente.");
        }
    }
}