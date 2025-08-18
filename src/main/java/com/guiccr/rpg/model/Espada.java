package com.guiccr.rpg.model;

import com.guiccr.rpg.ConsoleColors;

public class Espada extends Item implements Equipavel {
    private final int danoEspada;
    private final String qualidade;
    private boolean equipado = false; // Controle de estado do equipamento

    public Espada(String nome, String descricao, int danoEspada, String qualidade) {
        super(nome, descricao);
        if (danoEspada < 0) {
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
        System.out.println("Equipando espada: " + ConsoleColors.GOLD + getNome() + ConsoleColors.RESET + " e qualidade "
                + ConsoleColors.MAGENTA + getQualidade() + ConsoleColors.RESET + ".");

        int novoAtaque = heroi.getAtaque() + danoEspada;
        heroi.setAtaque(novoAtaque);

        equipado = true; // Marca como equipado
        
        System.out.println("Espada equipada com sucesso! Ataque aumentado para: " + ConsoleColors.RED_BRIGHT
                + novoAtaque + ConsoleColors.RESET + ".");
    }

    @Override
    public boolean isEquipado() {
        return equipado;
    }

    public String getTipoEquipamento() {
        return "arma"; // Retorna o tipo de equipamento
    }
    @Override
    public void removerEfeito(Heroi heroi) {
        if (equipado) {
            int novoAtaque = heroi.getAtaque() - danoEspada;
            heroi.setAtaque(novoAtaque);
        }
        System.out.println("Efeito da espada removido. Ataque reduzido para: " + ConsoleColors.RED_BRIGHT
                + heroi.getAtaque() + ConsoleColors.RESET + ".");
        equipado = false; // Marca como não equipado
    }
}
