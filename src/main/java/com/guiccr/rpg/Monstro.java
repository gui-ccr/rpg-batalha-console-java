package com.guiccr.rpg;

public class Monstro extends Personagem {

    private String tipo; 
    // Construtor do Monstro:
    // Chama o construtor da superclasse. As validações básicas já são feitas lá.
    public Monstro(String nome, int vidaMaxima, int ataque, int defesa, String tipo, int chanceCritico, double multiplicadorCritico, int chanceEsquiva) {
        super(nome, vidaMaxima, ataque, defesa, chanceCritico, multiplicadorCritico, chanceEsquiva);

        // Validação específica para o Monstro: tipo (agora que é String)
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo do monstro não pode ser nulo ou vazio.");
        }
        this.tipo = tipo;
    }

    // === Getter para o atributo 'tipo' ===
    public String getTipo() {
        return tipo;
    }

    // --- Implementação do Método Abstrato 'atacar()' herdado de Personagem ---
    @Override
    public void atacar(Personagem alvo) { 
        System.out.println("\n" + ConsoleColors.BLACK + this.getNome() + " (" + this.getTipo() + ")" + ConsoleColors.RESET + " ataca " + ConsoleColors.CYAN_BRIGHT +alvo.getNome() + ConsoleColors.RESET +"!");


        // Lógica da Esquiva: Alvo tenta esquivar
        if (alvo.tentarEsquiva()) {
            System.out.println(ConsoleColors.BLACK + alvo.getNome() + ConsoleColors.RESET +" esquivou do ataque!");
            return; // O ataque não causa dano
        }
        // Calcula o dano base: Ataque do Monstro - Defesa do Alvo
        int danoCausado = Math.max(0, this.getAtaque() - alvo.getDefesa());
        
        // Lógica de dano mínimo:
        // Se o atacante tem ataque > 0, mas o dano calculado é 0 ou negativo devido à defesa do alvo,
        // ainda causa 1 de dano para que o ataque não seja ineficaz.
        if (this.getAtaque() > 0 && danoCausado == 0) {
            danoCausado = 1;
        } else if (this.getAtaque() == 0) { // Se o monstro não tiver ataque, não causa dano.
            danoCausado = 0;
        }
        // Lógica de Crítico: Atacante tenta um crítico
        if (this.tentarCritico()) {
            danoCausado = (int) (danoCausado * this.getMultiplicadorCritico());
            System.out.println(ConsoleColors.BLACK +this.getNome() + ConsoleColors.RESET + " acertou um GOLPE CRÍTICO!");
        }

        alvo.receberDano(danoCausado);
    }
}