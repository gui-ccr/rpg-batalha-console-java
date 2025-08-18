package com.guiccr.rpg.model;

import com.guiccr.rpg.ConsoleColors;

public abstract class Personagem {

    // Atributos privados para encapsulamento
    private String nome;
    private int vidaAtual;
    private int vidaMaxima;
    private int ataque;
    private int defesa;
    private int chanceCritico;
    private double multiplicadorCritico;
    private int chanceEsquiva;

    // --- CONSTRUTORES ---
    // Construtor 1: Para criar um NOVO personagem (vida atual = vida máxima)
    public Personagem(String nome, int vidaMaxima, int ataque, int defesa,
                      int chanceCritico, double multiplicadorCritico, int chanceEsquiva) {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        if (vidaMaxima < 0) throw new IllegalArgumentException("Vida máxima não pode ser negativa.");
        if (ataque < 0) throw new IllegalArgumentException("Ataque não pode ser negativo.");
        if (defesa < 0) throw new IllegalArgumentException("Defesa não pode ser negativa.");
        if (chanceCritico < 0 || chanceCritico > 100) throw new IllegalArgumentException("Chance de crítico deve ser entre 0 e 100.");
        if (multiplicadorCritico < 1.0) throw new IllegalArgumentException("Multiplicador de crítico deve ser no mínimo 1.0.");
        if (chanceEsquiva < 0 || chanceEsquiva > 100) throw new IllegalArgumentException("Chance de esquiva deve ser entre 0 e 100.");

        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
        this.ataque = ataque;
        this.defesa = defesa;
        this.chanceCritico = chanceCritico;
        this.multiplicadorCritico = multiplicadorCritico;
        this.chanceEsquiva = chanceEsquiva;
    }

    // Construtor 2: Para carregar um personagem existente (vida atual é um parâmetro)
    public Personagem(String nome, int vidaAtual, int vidaMaxima, int ataque, int defesa,
                      int chanceCritico, double multiplicadorCritico, int chanceEsquiva) {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        if (vidaAtual < 0 || vidaMaxima < 0) throw new IllegalArgumentException("Vida atual ou vida máxima não pode ser negativa.");
        if (ataque < 0) throw new IllegalArgumentException("Ataque não pode ser negativo.");
        if (defesa < 0) throw new IllegalArgumentException("Defesa não pode ser negativa.");
        if (chanceCritico < 0 || chanceCritico > 100) throw new IllegalArgumentException("Chance de crítico deve ser entre 0 e 100.");
        if (multiplicadorCritico < 1.0) throw new IllegalArgumentException("Multiplicador de crítico deve ser no mínimo 1.0.");
        if (chanceEsquiva < 0 || chanceEsquiva > 100) throw new IllegalArgumentException("Chance de esquiva deve ser entre 0 e 100.");

        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaAtual;
        this.ataque = ataque;
        this.defesa = defesa;
        this.chanceCritico = chanceCritico;
        this.multiplicadorCritico = multiplicadorCritico;
        this.chanceEsquiva = chanceEsquiva;
    }

    // --- MÉTODOS GETTERS ---
    public String getNome() { return this.nome; }
    public int getVidaAtual() { return this.vidaAtual; }
    public int getVidaMaxima() { return this.vidaMaxima; }
    public int getAtaque() { return this.ataque; }
    public int getDefesa() { return this.defesa; }
    public int getChanceCritico() { return this.chanceCritico; }
    public double getMultiplicadorCritico() { return this.multiplicadorCritico; }
    public int getChanceEsquiva() { return this.chanceEsquiva; }
    
    // --- MÉTODOS SETTERS ---
    public void setVidaAtual(int novaVidaAtual) {
        if (novaVidaAtual < 0) throw new IllegalArgumentException("Vida atual não pode ser negativa.");
        this.vidaAtual = Math.min(novaVidaAtual, this.vidaMaxima);
    }
    public void setVidaMaxima(int novaVidaMaxima) {
        if (novaVidaMaxima < 0) throw new IllegalArgumentException("Vida máxima não pode ser negativa.");
        this.vidaMaxima = novaVidaMaxima;
        if (this.vidaAtual > this.vidaMaxima) this.vidaAtual = this.vidaMaxima;
    }
    public void setAtaque(int novoAtaque) {
        if (novoAtaque < 0) throw new IllegalArgumentException("Ataque não pode ser negativo.");
        this.ataque = novoAtaque;
    }
    public void setDefesa(int novaDefesa) {
        if (novaDefesa < 0) throw new IllegalArgumentException("Defesa não pode ser negativa.");
        this.defesa = novaDefesa;
    }
    public void setChanceCritico(int novaChanceCritico) {
        if (novaChanceCritico < 0 || novaChanceCritico > 100) throw new IllegalArgumentException("Chance de crítico deve ser entre 0 e 100.");
        this.chanceCritico = novaChanceCritico;
    }
    public void setMultiplicadorCritico(double novoMultiplicadorCritico) {
        if (novoMultiplicadorCritico < 1.0) throw new IllegalArgumentException("Multiplicador de crítico deve ser no mínimo 1.0.");
        this.multiplicadorCritico = novoMultiplicadorCritico;
    }
    public void setChanceEsquiva(int novaChanceEsquiva) {
        if (novaChanceEsquiva < 0 || novaChanceEsquiva > 100) throw new IllegalArgumentException("Chance de esquiva deve ser entre 0 e 100.");
        this.chanceEsquiva = novaChanceEsquiva;
    }

    // --- MÉTODOS DE COMPORTAMENTO ---
    public void receberDano(int dano) {
        this.vidaAtual -= dano;
        if (this.vidaAtual <= 0) this.vidaAtual = 0;
        System.out.println(this.nome + ConsoleColors.RED + " recebeu " + dano + " de dano." + ConsoleColors.RESET + " Vida Atual: " + ConsoleColors.GREEN + this.vidaAtual + ConsoleColors.RESET);
    }
    public boolean estaVivo() { return this.vidaAtual > 0; }
    public abstract void atacar(Personagem alvo);
    public void exibirStatus() {
        final int COMPRIMENTO_BARRA_HP = 20;
        double porcentagemVida = (double) this.getVidaAtual() / this.getVidaMaxima();
        int partesPreenchidas = (int) (porcentagemVida * COMPRIMENTO_BARRA_HP);
        int partesVazias = COMPRIMENTO_BARRA_HP - partesPreenchidas;
        StringBuilder barraConstruida = new StringBuilder();
        barraConstruida.append("[");
        for (int i = 0; i < partesPreenchidas; i++) barraConstruida.append("█");
        for (int i = 0; i < partesVazias; i++) barraConstruida.append("-");
        barraConstruida.append("]");
        String corVida = (porcentagemVida > 0.5) ? ConsoleColors.GREEN : (porcentagemVida > 0.2) ? ConsoleColors.YELLOW : ConsoleColors.RED;
        System.out.printf("%-15s HP: %s%s (%d/%d)%s%n",
                          this.getNome(), corVida + barraConstruida.toString() + ConsoleColors.RESET,
                          corVida, this.getVidaAtual(), this.getVidaMaxima(), ConsoleColors.RESET);
    }
    
    // --- MÉTODOS DE COMBATE ---
    protected boolean tentarCritico() {
        return Math.random() * 100 < this.chanceCritico;
    }
    
    protected boolean tentarEsquiva() {
        return Math.random() * 100 < this.chanceEsquiva;
    }
    
    // --- MÉTODOS AUXILIARES ESTÁTICOS PARA CÁLCULO E VALIDAÇÃO ---
    private static int calcularVidaMaxima(int vigor) {
        if (vigor < 0) throw new IllegalArgumentException("Vigor não pode ser negativo.");
        return 50 + (vigor * 10);
    }
    private static int calcularAtaque(int forca) {
        if (forca < 0) throw new IllegalArgumentException("Força não pode ser negativa.");
        return 10 + (forca * 2);
    }
    private static int calcularDefesa(int agilidade) {
        if (agilidade < 0) throw new IllegalArgumentException("Agilidade não pode ser negativa.");
        return 5 + (agilidade * 1);
    }
    private static int calcularChanceCritico(int presenca, int forca) {
        if (presenca < 0 || forca < 0) throw new IllegalArgumentException("Presença e Força não podem ser negativos para cálculo de crítico.");
        return Math.min(100, 5 + (presenca * 2) + (forca * 1));
    }
    private static double calcularMultiplicadorCritico(int intelecto) {
        if (intelecto < 0) throw new IllegalArgumentException("Intelecto não pode ser negativo para cálculo de multiplicador crítico.");
        return 1.5 + (intelecto * 0.05);
    }
    private static int calcularChanceEsquiva(int agilidade, int intelecto) {
        if (agilidade < 0 || intelecto < 0) throw new IllegalArgumentException("Agilidade e Intelecto não podem ser negativos para cálculo de esquiva.");
        return Math.min(100, 5 + (agilidade * 2) + (intelecto * 1));
    }
}