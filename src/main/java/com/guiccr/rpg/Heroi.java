package com.guiccr.rpg;

public class Heroi extends Personagem {

    private int energia;

    public Heroi(String nome, int vidaMaxima, int ataque, int defesa, int energiaInicial) {
        // Chama o construtor da superclasse. As validações básicas já são feitas lá.
        super(nome, vidaMaxima, ataque, defesa);

        // Validação específica para o Herói: energia inicial
        if (energiaInicial < 0) {
            throw new IllegalArgumentException("Energia inicial não pode ser negativa.");
        }
        this.energia = energiaInicial;
    }

    // === Getters ===
    public int getEnergia() {
        return energia;
    }

    // --- Implementação do Método Abstrato 'atacar()' herdado de Personagem ---
    @Override 
    public void atacar(Personagem alvo) { // O parâmetro deve ser do tipo Personagem.
        System.out.println("\n" + this.getNome() + " ataca " + alvo.getNome() + "!");

        // Calcula o dano base: Ataque do Herói - Defesa do Alvo
        int danoCausado = Math.max(0, this.getAtaque() - alvo.getDefesa());

        // Lógica de dano mínimo:
        // Se o atacante tem ataque > 0, mas o dano calculado é 0 ou negativo devido à defesa do alvo,
        // ainda causa 1 de dano para que o ataque não seja ineficaz.
        if (this.getAtaque() > 0 && danoCausado == 0) {
            danoCausado = 1;
        } else if (this.getAtaque() == 0) { // Se o herói não tiver ataque, não causa dano.
            danoCausado = 0;
        }
        
        alvo.receberDano(danoCausado);
    }

    // Método específico do Herói: usarHabilidadeEspecial
    public void usarHabilidadeEspecial(Personagem alvo) { // O parâmetro deve ser do tipo Personagem.
        if (this.energia >= 10) { // Custo de energia para a habilidade
            System.out.println(this.getNome() + " usa uma habilidade especial em " + alvo.getNome() + "!");
            
            // Calcula o dano base: Dano dobrado do ataque do Herói - Defesa do Alvo
            int danoCausado = Math.max(0, this.getAtaque() * 2 - alvo.getDefesa());
            
            // Lógica de dano mínimo:
            if (this.getAtaque() > 0 && danoCausado == 0) {
                danoCausado = 1;
            } else if (this.getAtaque() == 0) {
                danoCausado = 0;
            }
            
            alvo.receberDano(danoCausado);
            this.energia -= 10;
            System.out.println("Energia de " + this.getNome() + " atual: " + this.energia);
        } else {
            System.out.println(this.getNome() + " não tem energia suficiente para usar a habilidade especial!");
        }   
    }
}