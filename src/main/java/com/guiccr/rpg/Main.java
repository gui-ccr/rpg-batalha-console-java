package com.guiccr.rpg;

import java.util.Scanner; // Importar Scanner

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner criado UMA VEZ
        int escolhaMenu = 0;
        
        do {
            String nomeAleatorioHeroi = GeradorDeNomes.gerarNomeHerois();
            String nomeAleatorioMonstro = GeradorDeNomes.gerarNomeMonstro();


            MenuPrincipal.exibirOpcoes(); // Exibe o menu principal
            escolhaMenu = MenuPrincipal.lerOpcao(scanner); // Lê a opção do usuário

            switch (escolhaMenu) {
                case 1:
                    System.out.println("\nIniciando nova batalha...");
                    MenuPrincipal.pausar(1000); // Pausa de 1 segundo

                    // Crie instâncias de Heroi e Monstro AQUI (usando GeradorDeNomes)
                    Heroi heroi_principal = new Heroi(
                    nomeAleatorioHeroi,
                    100,
                    20,
                    10, 
                    100,
                    1.5,
                    0,
                    20
                    );
                    Monstro monstro_g0 = new Monstro(
                    nomeAleatorioMonstro,
                    130,
                    15,
                    5,
                    "Criatura",
                    8,
                    1.2,
                    10
                    );
                    System.out.println("\n--- Aventureiros e Monstro Preparados ---");
                    MenuPrincipal.pausar(1500); // Pausa para leitura

                    // Passe o scanner para a Batalha
                    Batalha batalha = new Batalha(heroi_principal, monstro_g0, scanner);
                    batalha.iniciar(); // Inicia o loop da batalha
                    break;
                case 2:
                    System.out.println("Saindo do jogo. Obrigado por jogar!");
                    break;
                default:
                    // Este caso dificilmente será alcançado devido à validação em MenuPrincipal.lerOpcao
                    System.out.println("Opção inválida. Por favor, tente novamente.");
                    break;
            }
            MenuPrincipal.pausar(2000); // Pausa após cada rodada do menu principal (antes de reexibir)
        } while (escolhaMenu != 2); // Repete o menu até o usuário escolher Sair

        scanner.close(); // Fecha o scanner ao sair do jogo (e somente aqui)
        System.out.println("--- Jogo Encerrado ---");
    }
}