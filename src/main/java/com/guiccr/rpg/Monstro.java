package com.guiccr.rpg;

public class Monstro extends Personagem {

    private String tipo; 
    // Construtor do Monstro:
    // Chama o construtor da superclasse. As validações básicas já são feitas lá.
    public Monstro(String nome, int vidaMaxima, int ataque, int defesa, String tipo) {
        super(nome, vidaMaxima, ataque, defesa);

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
        System.out.println("\n" + this.getNome() + " (" + this.getTipo() + ") ataca " + alvo.getNome() + "!");

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

        alvo.receberDano(danoCausado);
    }
}