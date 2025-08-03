package com.guiccr.rpg;

public class Monstro extends Personagem {
    private String tipo;
    private int experienciaConcedida;

    public Monstro(String nome, int vidaMaxima, int ataque, int defesa,
                   int chanceCritico, double multiplicadorCritico, int chanceEsquiva, String tipo) {
        // Chama o construtor da superclasse
        super(nome, vidaMaxima, ataque, defesa, chanceCritico, multiplicadorCritico, chanceEsquiva);
        if (tipo == null || tipo.trim().isEmpty()) throw new IllegalArgumentException("Tipo do monstro não pode ser nulo ou vazio.");
        this.tipo = tipo;

        int minExp = (this.getVidaMaxima() / 4) + (this.getAtaque() / 2);
        int maxExp = (this.getVidaMaxima() / 2) + this.getAtaque();
        if (minExp <= 0) minExp = 1;
        if (maxExp < minExp) maxExp = minExp + 10;
        this.experienciaConcedida = (int) (Math.random() * (maxExp - minExp + 1)) + minExp;
    }

    public String getTipo() { return tipo; }
    public int getExpConcedida() { return experienciaConcedida; }

    @Override
    public void atacar(Personagem alvo) { 
        System.out.println("\n" + ConsoleColors.RED_BRIGHT + this.getNome() + ConsoleColors.RESET + " ("
                           + this.getTipo() + ") ataca " + ConsoleColors.CYAN_BRIGHT + alvo.getNome() + ConsoleColors.RESET + "!");

        // Lógica da esquiva: Alvo tenta esquivar
        if (alvo.tentarEsquiva()) {
            System.out.println(ConsoleColors.BLUE + alvo.getNome() + " esquivou do ataque!" + ConsoleColors.RESET);
            return; // O ataque não causa dano
        }

        // Calcula o dano base: Ataque do Monstro - Defesa do Alvo
        int danoCausado = Math.max(0, this.getAtaque() - alvo.getDefesa());

        // Lógica de Crítico: Atacante tenta um crítico
        if (this.tentarCritico()) {
            danoCausado = (int) (danoCausado * this.getMultiplicadorCritico());
            System.out.println(ConsoleColors.RED_BRIGHT + ConsoleColors.BOLD + this.getNome() + ConsoleColors.RESET
                               + ConsoleColors.YELLOW_BRIGHT + " acertou um GOLPE CRÍTICO!!" + ConsoleColors.RESET);
        }

        // Lógica de dano mínimo:
        if (this.getAtaque() > 0 && danoCausado == 0) { // Ataque não nulo mas dano zerado pela defesa
            danoCausado = 1;
        } else if (this.getAtaque() == 0) { // Se o monstro não tiver ataque, não causa dano.
            danoCausado = 0;
        }

        alvo.receberDano(danoCausado); // O alvo recebe o dano calculado.
    }
}