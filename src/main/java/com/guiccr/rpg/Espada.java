package com.guiccr.rpg;

public class Espada extends Item {
    private final int danoEspada;
    private final String qualidade;

    public Espada(String nome, String descricao, int danoEspada, String qualidade){
        super(nome, descricao);
        if (danoEspada  < 0) {
            throw new IllegalArgumentException("O dano da espada não pode ser negativo.");
            
        }
        if (qualidade == null || qualidade.isEmpty()) {
            throw new IllegalArgumentException("A qualidade da espada não pode ser nula ou vazia.");
            
        }
        this.danoEspada = danoEspada;
        this.qualidade = qualidade;
    }

    // Getters
    public int getDanoEspada() {
        return danoEspada;
    }

    public String getQualidade() {
        return qualidade;
    }

    @Override
    public void aplicarEfeito(Heroi heroi) {
        System.out.println("Equipando espada: " + ConsoleColors.GOLD + getNome() + ConsoleColors.RESET + " e qualidade " + ConsoleColors.MAGENTA + getQualidade() + ConsoleColors.RESET + ".");

        int novoAtaque = heroi.getAtaque() + danoEspada;
        heroi.setAtaque(novoAtaque);

        System.out.println("Espada equipada com sucesso! Ataque aumentado para: " + ConsoleColors.RED_BRIGHT + novoAtaque + ConsoleColors.RESET + ".");
    }
}
