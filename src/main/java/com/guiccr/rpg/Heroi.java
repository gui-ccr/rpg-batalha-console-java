package com.guiccr.rpg;

public class Heroi extends Personagem {

    private int energia;

    // atributos especificos do agente 
    // private int forca;     // FOR
    // private int agilidade; // AGI
    // private int vigor;     // VIG
    // private int presenca;  // PRE
    // private int intelecto; // INT

    public Heroi(String nome, int vidaMaxima, int ataque, int defesa,int chanceCritico, double multiplicadorCritico, int chanceEsquiva, // Herdado de Personagem, passado para super
    int energiaInicial) // Próprio do Heroi
    // int forca, int agilidade, int vigor, int presenca, int intelecto IMPLEMENTAR DEPOIS
    {
        // Chama o construtor da superclasse. As validações básicas já são feitas lá.
        super(nome, vidaMaxima, ataque, defesa, chanceCritico, multiplicadorCritico, chanceEsquiva);

        // Validação específica para o Herói: energia inicial
        if (energiaInicial < 0) 
        {
            throw new IllegalArgumentException("Energia inicial não pode ser negativa.");
        }
        // Validações e inicializações para os atributos de Ordem Paranormal.
        // Cada atributo não deve ser negativo.
        // if (forca < 0 || agilidade < 0 || vigor < 0 || presenca < 0 || intelecto < 0) {
        //     throw new IllegalArgumentException("Atributos de FOR, AGI, VIG, PRE, INT não podem ser negativos.");
        // }
        // this.forca = forca;
        // this.agilidade = agilidade;
        // this.vigor = vigor;
        // this.presenca = presenca;
        // this.intelecto = intelecto;
    }

    // === Getters ===
    public int getEnergia() {return energia;}
    // public int getForca() {return forca;}
    // public int getAgilidade() {return agilidade;}
    // public int getVigor() {return vigor;}
    // public int getPresenca() {return presenca;}
    // public int getIntelecto() {return intelecto;}

    // --- Implementação do Método Abstrato 'atacar()' herdado de Personagem ---
    @Override 
    public void atacar(Personagem alvo) { // O parâmetro deve ser do tipo Personagem.
        System.out.println("\n" + this.getNome() + " ataca " + alvo.getNome() + "!");
        
        // logica da esquiva
        if (alvo.tentarEsquiva()) {
            System.out.println(alvo.getNome() + "Esquivou do ataque!");
            return; // o ataque nao causa dano
        }
        
        // Calcula o dano base: Ataque do Herói - Defesa do Alvo
        int danoCausado = Math.max(0, this.getAtaque() - alvo.getDefesa());

        if (this.tentarCritico()) {
            danoCausado = (int) (danoCausado * this.getMultiplicadorCritico());
            System.out.println(this.getNome() + "acertou um GOLPE CRÍTICO!!");
        }

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