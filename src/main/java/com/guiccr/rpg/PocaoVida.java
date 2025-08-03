package com.guiccr.rpg;

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
        System.out.println("Aplicando efeito da poção de vida: " + cura + " pontos de vida restaurados.");
        heroi.setVidaAtual(heroi.getVidaAtual() + cura);
    }
}
