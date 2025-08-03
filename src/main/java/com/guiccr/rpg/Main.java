package com.guiccr.rpg;

import java.util.Scanner; // Importar Scanner
import java.util.Optional;

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
                case 1: // NOVO JOGO
                    System.out.println("\nIniciando nova batalha...");
                    MenuPrincipal.pausar(1000); // Pausa de 1 segundo

                    // Crie instâncias de Heroi e Monstro AQUI (usando GeradorDeNomes)
                    Heroi heroi_principal = new Heroi(nomeAleatorioHeroi, 12, 3, 6, 3, 4, 20);
                    Monstro monstro_g0 = new Monstro(nomeAleatorioMonstro, 110, 20, 10, 10, 1.4, 15, "Criatura");
                    RepositorioDeHerois.salvarHeroi(heroi_principal);

                    System.out.println("\n--- Aventureiros e Monstro Preparados ---");
                    MenuPrincipal.pausar(1500); // Pausa para leitura

                    // Passe o scanner para a Batalha
                    Batalha batalha = new Batalha(heroi_principal, monstro_g0, scanner);
                    batalha.iniciar(); // Inicia o loop da batalha
                    break;
                case 2: // CARREGAR JOGO
                    System.out.println("Digite o nome do heroi para carrega: ");
                    String nomeParaCarregar = scanner.nextLine();
                    Optional<Heroi> heroiCarregado = RepositorioDeHerois.buscarHeroi(nomeParaCarregar);

                    Monstro monstro_g1 = new Monstro(nomeAleatorioMonstro, 110, 20, 10, 10, 1.4, 15, "Criatura");

                    if (heroiCarregado.isPresent()) {
                        System.out.println("Herói " + ConsoleColors.CYAN_BRIGHT + heroiCarregado.get().getNome()
                                + " carregado com sucesso!");
                        heroiCarregado.get().exibirStatus();
                        Batalha batalhaCarregada = new Batalha(heroiCarregado.get(), monstro_g1, scanner);
                        batalhaCarregada.iniciar();
                    } else {
                        System.out.println("Herói não encontrado. Tente novamente.");

                    }
                    break;
                    case 3: // SAIR DO JOGO 
                    System.out.println("Saindo do jogo....");
                    break;
                default:
                    // Este caso dificilmente será alcançado devido à validação em
                    // MenuPrincipal.lerOpcao
                    System.out.println("Opção inválida. Por favor, tente novamente.");
                    break;
            }
            MenuPrincipal.pausar(2000); // Pausa após cada rodada do menu principal (antes de reexibir)
        } while (escolhaMenu != 3); // Repete o menu até o usuário escolher Sair

        scanner.close(); // Fecha o scanner ao sair do jogo (e somente aqui)
        System.out.println("--- Jogo Encerrado ---");
    }
}