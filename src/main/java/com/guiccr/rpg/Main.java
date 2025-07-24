package com.guiccr.rpg;

public class Main{
    public static void main(String[] args) {

        String nomeAleatorioHeroi = GeradorDeNomes.gerarNomeHerois();
        String nomeAleatorioMonstro = GeradorDeNomes.gerarNomeMonstro();
        
        System.out.println("--- RPG de Batalha por Turnos ---");

        Heroi heroi_principal = new Heroi(nomeAleatorioHeroi, 100, 20, 10, 45);
        Monstro monstro_g0 = new Monstro(nomeAleatorioMonstro, 80, 15, 5, "Goblin");


        // Exibindo os status dos personagens 
        // System.out.println("\n ---- Aventureiros e Monstro Preparados ----");
        // heroi_principal.exibirStatus();
        // monstro_g0.exibirStatus();

        Batalha batalha = new Batalha(heroi_principal, monstro_g0);

        batalha.iniciar();
        System.out.println("\n--- Jogo Encerrado ---");

    }
}