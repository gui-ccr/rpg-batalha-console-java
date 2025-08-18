package com.guiccr.rpg.model;

import com.guiccr.rpg.ConsoleColors;

public class PocaoVida extends Item {
    private final int cura;

    public PocaoVida(String nome, String descricao, int cura) {
        super(nome, descricao);
        this.cura = cura;
        if (cura < 0) {
            throw new IllegalArgumentException("A quantidade de cura não pode ser negativa.");
        }

    }

    // Getters
    public int getQuantidadeVida() {
        return cura;
    }

    @Override
    public void aplicarEfeito(Heroi heroi) {
        System.out.println("Aplicando efeito da poção de vida: " + cura + " pontos de vida...");
        
        // Verifica se a vida já está cheia
        if (heroi.getVidaAtual() >= heroi.getVidaMaxima()) {
            System.out.println(ConsoleColors.YELLOW + "⚠️  A vida de " + heroi.getNome() + " já está cheia!" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.RED + "💧 A poção foi desperdiçada..." + ConsoleColors.RESET);
            return; // Sai sem curar, mas a poção ainda é consumida
        }
        
        // Calcula a cura real (sem passar da vida máxima)
        int vidaAntes = heroi.getVidaAtual();
        int vidaMaxima = heroi.getVidaMaxima();
        int novaVida = Math.min(vidaAntes + cura, vidaMaxima);
        int curaReal = novaVida - vidaAntes;
        
        // Aplica a cura
        heroi.setVidaAtual(novaVida);
        
        // Exibe mensagem adequada
        if (curaReal < cura) {
            System.out.println(ConsoleColors.GREEN + "✨ " + heroi.getNome() + " foi curado em " + curaReal + " pontos!" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.YELLOW + "📋 Vida máxima atingida! (" + vidaAntes + " → " + novaVida + "/" + vidaMaxima + ")" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "💧 " + (cura - curaReal) + " pontos de cura foram desperdiçados." + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.GREEN + "✨ " + heroi.getNome() + " foi completamente curado!" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "📈 Vida: " + vidaAntes + " → " + novaVida + "/" + vidaMaxima + ConsoleColors.RESET);
        }
    }
}
