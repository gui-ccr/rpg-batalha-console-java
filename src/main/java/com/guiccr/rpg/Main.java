package com.guiccr.rpg;

import java.util.Scanner; // Importar Scanner

import com.guiccr.rpg.model.Espada;
import com.guiccr.rpg.model.Heroi;
import com.guiccr.rpg.model.Inventario;
import com.guiccr.rpg.model.Monstro;
import com.guiccr.rpg.model.PocaoVida;
import com.guiccr.rpg.repository.RepositorioDeHerois;
import com.guiccr.rpg.service.Batalha;

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
                    PocaoVida pocaoVida = new PocaoVida("Poção de Vida", "Restaura 35 pontos de vida.", 35);
                    Espada espada = new Espada("Espada Longa", "Uma espada longa de conhecimento.", 19, "Alta");
                    Inventario inventario = new Inventario(); // Cria um novo inventário
                    inventario.adicionarItem(pocaoVida); // Adiciona a poção de vida ao inventário
                    inventario.adicionarItem(espada); // Adiciona a espada ao inventário
                    // Cria um novo herói com o inventário

                    // Crie instâncias de Heroi e Monstro AQUI (usando GeradorDeNomes)
                    Heroi heroi_principal = new Heroi(nomeAleatorioHeroi, 6, 3, 6, 3, 4, 20, inventario );
                    Monstro monstro_g0 = new Monstro(nomeAleatorioMonstro, 110, 20, 10, 10, 1.4, 15, "Criatura");
                    RepositorioDeHerois.salvarHeroi(heroi_principal);

                    System.out.println("\n--- Aventureiros e Monstro Preparados ---");
                    MenuPrincipal.pausar(1500); // Pausa para leitura

                    // Passe o scanner para a Batalha
                    Batalha batalha = new Batalha(heroi_principal, monstro_g0, scanner);
                    batalha.iniciar(); // Inicia o loop da batalha
                    break;
                case 2: // CARREGAR JOGO
                    System.out.println("\n=== CARREGAR JOGO ===");
                    RepositorioDeHerois.listarHerois(); // Lista os heróis disponíveis com índices
                    
                    int totalHeroisCarregar = RepositorioDeHerois.contarHerois();
                    if (totalHeroisCarregar == 0) {
                        System.out.println("Não há heróis salvos para carregar.");
                        break;
                    }
                    
                    System.out.print("Digite o número do herói que deseja carregar (1-" + totalHeroisCarregar + ") ou 0 para cancelar: ");
                    
                    try {
                        int indiceEscolhidoCarregar = scanner.nextInt();
                        scanner.nextLine(); // Consome a quebra de linha
                        
                        if (indiceEscolhidoCarregar == 0) {
                            System.out.println("Operação cancelada.");
                        } else if (indiceEscolhidoCarregar >= 1 && indiceEscolhidoCarregar <= totalHeroisCarregar) {
                            String nomeParaCarregar = RepositorioDeHerois.obterNomeHeroiPorIndice(indiceEscolhidoCarregar);
                            if (nomeParaCarregar != null) {
                                Optional<Heroi> heroiCarregado = RepositorioDeHerois.buscarHeroi(nomeParaCarregar);
                                
                                if (heroiCarregado.isPresent()) {
                                    System.out.println("Herói " + ConsoleColors.CYAN_BRIGHT + heroiCarregado.get().getNome()
                                            + ConsoleColors.RESET + " carregado com sucesso!");
                                    heroiCarregado.get().exibirStatus();
                                    
                                    Monstro monstro_g1 = new Monstro(nomeAleatorioMonstro, 110, 20, 10, 10, 1.4, 15, "Criatura");
                                    Batalha batalhaCarregada = new Batalha(heroiCarregado.get(), monstro_g1, scanner);
                                    batalhaCarregada.iniciar();
                                } else {
                                    System.out.println("Erro: Herói não encontrado no banco de dados.");
                                }
                            } else {
                                System.out.println("Erro: Herói não encontrado.");
                            }
                        } else {
                            System.out.println("Número inválido. Digite um número entre 1 e " + totalHeroisCarregar + ".");
                        }
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Por favor, digite apenas números.");
                        scanner.nextLine(); // Limpa o buffer
                    }
                    break;
                case 3: // EXCLUIR HERÓI
                    System.out.println("\n=== EXCLUIR HERÓI ===");
                    RepositorioDeHerois.listarHerois(); // Lista os heróis disponíveis com índices
                    
                    int totalHerois = RepositorioDeHerois.contarHerois();
                    if (totalHerois == 0) {
                        System.out.println("Não há heróis para excluir.");
                        break;
                    }
                    
                    System.out.print("Digite o número do herói que deseja excluir (1-" + totalHerois + ") ou 0 para cancelar: ");
                    
                    try {
                        int indiceEscolhido = scanner.nextInt();
                        scanner.nextLine(); // Consome a quebra de linha
                        
                        if (indiceEscolhido == 0) {
                            System.out.println("Operação cancelada.");
                        } else if (indiceEscolhido >= 1 && indiceEscolhido <= totalHerois) {
                            String nomeParaExcluir = RepositorioDeHerois.obterNomeHeroiPorIndice(indiceEscolhido);
                            if (nomeParaExcluir != null) {
                                System.out.print("Tem certeza que deseja excluir o herói '#" + indiceEscolhido + " - " + nomeParaExcluir + "'? (s/n): ");
                                String confirmacao = scanner.nextLine().trim().toLowerCase();
                                if (confirmacao.equals("s") || confirmacao.equals("sim")) {
                                    RepositorioDeHerois.deletarHeroi(nomeParaExcluir);
                                } else {
                                    System.out.println("Exclusão cancelada.");
                                }
                            } else {
                                System.out.println("Erro: Herói não encontrado.");
                            }
                        } else {
                            System.out.println("Número inválido. Digite um número entre 1 e " + totalHerois + ".");
                        }
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Por favor, digite apenas números.");
                        scanner.nextLine(); // Limpa o buffer
                    }
                    break;
                case 4: // SAIR DO JOGO 
                    System.out.println("Saindo do jogo....");
                    break;
                default:
                    // Este caso dificilmente será alcançado devido à validação em
                    // MenuPrincipal.lerOpcao
                    System.out.println("Opção inválida. Por favor, tente novamente.");
                    break;
            }
            MenuPrincipal.pausar(2000); // Pausa após cada rodada do menu principal (antes de reexibir)
        } while (escolhaMenu != 4); // Repete o menu até o usuário escolher Sair

        scanner.close(); // Fecha o scanner ao sair do jogo (e somente aqui)
        System.out.println("--- Jogo Encerrado ---");
    }
}