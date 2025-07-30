package com.guiccr.rpg;

// A classe personagem é abstrata porque ela vai definir atributos e metodos gerais de todos os personagens
// no caso suas subclasses (Heroi, Monstro) devem implementar esses componentes
public abstract class Personagem {

    // atributos privados para encapsulamento
    private String nome;
    private int vidaAtual;
    private int vidaMaxima;
    private int ataque;
    private int defesa;

    // NOVOS ATRIBUTOS para Crítico e Esquiva
    private int chanceCritico; // Porcentagem (0-100)
    private double multiplicadorCritico; // Ex: 1.5, 2.0
    private int chanceEsquiva; // Porcentagem (0-100)

    // construtor inicial do personagem pede nome a vida, ataque e defesa
    public Personagem(String nome, int vidaMaxima, int ataque, int defesa, int chanceCritico,
            double multiplicadorCritico, int chanceEsquiva) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        }
        if (vidaMaxima < 0) {
            throw new IllegalArgumentException("Vida máxima não pode ser negativa.");
        }
        if (ataque < 0) {
            throw new IllegalArgumentException("Ataque não pode ser negativo.");
        }
        if (defesa < 0) {
            throw new IllegalArgumentException("Defesa não pode ser negativa.");
        }
        if (chanceCritico < 0 || chanceCritico > 100) {
            throw new IllegalArgumentException("Chance de crítico deve ser entre 0 e 100.");
        }
        if (chanceEsquiva < 0 || chanceEsquiva > 100) {
            throw new IllegalArgumentException("Chance de esquiva deve ser entre 0 e 100.");
        }
        if (multiplicadorCritico < 1.0) {
            throw new IllegalArgumentException("multiplicador de Critico deve ser no minimo 1.0.");
        }
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
        this.ataque = ataque;
        this.defesa = defesa;
        this.chanceCritico = chanceCritico;
        this.multiplicadorCritico = multiplicadorCritico;
        this.chanceEsquiva = chanceEsquiva;
    }

    /*
     * -- Metodos getters ( para acessar os atributos privados)
     * nao vou precisar de setter porque vou alterar os atributos internamente
     * eles vao ser alterados por outros metodos como "receber dano"
     */
    // --- Métodos Getters ---
    public String getNome() {
        return this.nome;
    }

    public int getVidaAtual() {
        return this.vidaAtual;
    }

    public int getVidaMaxima() {
        return this.vidaMaxima;
    }

    public int getAtaque() {
        return this.ataque;
    }

    public int getDefesa() {
        return this.defesa;
    }

    public int getChanceCritico() {
        return this.chanceCritico;
    }

    public double getMultiplicadorCritico() {
        return this.multiplicadorCritico;
    }

    public int getChanceEsquiva() {
        return this.chanceEsquiva;
    }

    // --- Métodos Setters ---
    public void setVidaAtual(int vidaAtual) {
        if (vidaAtual < 0) {
            throw new IllegalArgumentException("Vida atual não pode ser negativa.");
        }
        this.vidaAtual = Math.min(vidaAtual, this.vidaMaxima); // Garante que a vida não ultrapasse o máximo
    }

    public void setVidaMaxima(int novaVidaMaxima) {
        if (novaVidaMaxima < 0) {
            throw new IllegalArgumentException("Vida maxima não pode ser negativa");
        }
        this.vidaMaxima = novaVidaMaxima;
        if (this.vidaAtual > this.vidaMaxima) {
            this.vidaAtual = this.vidaMaxima;
        }

    }

    public void setDefesa(int novaDefesa) {
        if (novaDefesa < 0) {
            throw new IllegalArgumentException("Defesa nao pode ser negativo.");
        }
        this.defesa = novaDefesa;
    }

    public void setAtaque(int novoAtaque) {
        if (novoAtaque < 0) {
            throw new IllegalArgumentException("Ataque nao pode ser negativo.");
        }
        this.ataque = novoAtaque;
    }

    public void setChanceCritico(int chanceCriticoAtual) {
        if (chanceCriticoAtual < 0 || chanceCriticoAtual > 100) {
            throw new IllegalArgumentException("Chance de Critico nao pode ser negativo e nem maior que 100%");
        }
        this.chanceCritico = chanceCriticoAtual;
    }

    public void setChanceEsquiva(int chanceEsquivaAtual) {
        if (chanceEsquivaAtual < 0 || chanceEsquivaAtual > 100) {
            throw new IllegalArgumentException("Chance de Esquiva nao pode ser negativo e  nem maior que 100%");
        }
        this.chanceEsquiva = chanceEsquivaAtual;
    }

    public void setMultiplicadorCritico(double multiplicadorCritico) {
        if (multiplicadorCritico < 1.0) {
            throw new IllegalArgumentException("Multiplicador de Critico nao pode ser menor que 1.0");
        }
        this.multiplicadorCritico = multiplicadorCritico;
    }

    // === Metodos de Comportamento ====

    // Método concreto: define como o personagem recebe o dano e como a vida será
    // afetada; no caso, vai diminuir da sua vida atual
    public void receberDano(int dano) {
        this.vidaAtual -= dano;

        if (this.vidaAtual <= 0) {
            this.vidaAtual = 0;
        }

        System.out.println(this.nome + ConsoleColors.RED + " recebeu " + dano + " de dano." + ConsoleColors.RESET
                + " Vida Atual: " + ConsoleColors.GREEN + this.vidaAtual + ConsoleColors.RESET);
    }

    // metodo concreto: verifica se o personagem está vivo com um if simples
    public boolean estaVivo() {
        return this.vidaAtual > 0;
    }

    // Metodo abstrato: Define que todo personagem ATACA, mas não especifica COMO.

    // A implementação sera feita pelas subclasses (Heroi, Monstro).
    public abstract void atacar(Personagem alvo);

    // Metodo concreto: exibe os status atuais do personagem
    public void exibirStatus() {
        final int COMPRIMENTO_BARRA_HP = 20; // Comprimento fixo da barra
        double porcentagemVida = (double) this.getVidaAtual() / this.getVidaMaxima();
        int partesPreenchidas = (int) (porcentagemVida * COMPRIMENTO_BARRA_HP);
        int partesVazias = COMPRIMENTO_BARRA_HP - partesPreenchidas;

        StringBuilder barraConstruida = new StringBuilder();

        barraConstruida.append("[");
        for (int i = 0; i < partesPreenchidas; i++) {
            barraConstruida.append("█"); // Caractere de preenchimento
        }
        for (int i = 0; i < partesVazias; i++) {
            barraConstruida.append("-"); // Caractere de vazio
        }
        barraConstruida.append("]");

        String corVida;
        if (porcentagemVida > 0.5) {
            corVida = ConsoleColors.GREEN;
        } else if (porcentagemVida > 0.2) {
            corVida = ConsoleColors.YELLOW;
        } else {
            corVida = ConsoleColors.RED;
        }
        // Exibição mais detalhada e formatada
        System.out.printf("%-15s HP: %s%s (%d/%d)%s%n",
                this.getNome(),
                corVida + barraConstruida.toString() + ConsoleColors.RESET, // Barra colorida
                corVida, // Para a parte numérica da vida também ter a cor
                this.getVidaAtual(),
                this.getVidaMaxima(),
                ConsoleColors.RESET);
        // Garante que tudo reseta no final da linha
        // System.out.println(" | Atk: " + this.ataque + " | Def: " + this.defesa +
        // " | Crítico: " + this.chanceCritico + "% | Esquiva: " + this.chanceEsquiva +
        // "%");
        // Decidi não exibir Atk/Def/Crit/Esquiva aqui para manter o status mais limpo.
        // Isso pode ser exibido em um "perfil" do personagem ou no relatório final se
        // necessário.
    }

    // --- NOVOS MÉTODOS AUXILIARES para Crítico e Esquiva ---
    // Métodos auxiliares concretos para calcular a chance (herdados por Heroi e
    // Monstro)

    public boolean tentarCritico() {
        // Gera um número aleatório entre 0.0 (inclusive) e 1.0 (exclusivo)
        // Multiplica por 100 para ter um número entre 0 e 99.99...
        // Compara com a chanceCritico do personagem
        return Math.random() * 100 < this.chanceCritico;
    }

    public boolean tentarEsquiva() {
        return Math.random() * 100 < this.chanceEsquiva;
    }
}