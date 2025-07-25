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
    public Personagem(String nome, int vidaMaxima, int ataque, int defesa, int chanceCritico, double multiplicadorCritico, int chanceEsquiva){
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
    public String getNome() { return this.nome; }
    public int getVidaAtual() { return this.vidaAtual; }
    public int getVidaMaxima() { return this.vidaMaxima; }
    public int getAtaque() { return this.ataque; }
    public int getDefesa() { return this.defesa; }

    // NOVOS GETTERS para atributos de Crítico e Esquiva
    public int getChanceCritico() { return this.chanceCritico; }
    public double getMultiplicadorCritico() { return this.multiplicadorCritico; }
    public int getChanceEsquiva() { return this.chanceEsquiva; }

    // === Metodos de Comportamento ====

    // Método concreto: define como o personagem recebe o dano e como a vida será afetada; no caso, vai diminuir da sua vida atual
    public void receberDano(int dano){
        this.vidaAtual -= dano;

        if (this.vidaAtual <= 0) {
            this.vidaAtual = 0;
        }
        System.out.println(this.nome + " recebeu " + dano + " de dano. Vida Atual: " + this.vidaAtual);
    }
    // metodo concreto: verifica se o personagem está vivo com um if simples
    public boolean estaVivo(){
        return this.vidaAtual > 0;
    }

    // Metodo abstrato: Define que todo personagem ATACA, mas não especifica COMO.

    // A implementação sera feita pelas subclasses (Heroi, Monstro).
    public abstract void atacar(Personagem alvo);

    // Metodo concreto: exibe os status atuais do personagem
    public void exibirStatus() {
    final int COMPRIMENTO_BARRA_HP = 20;
    double porcentagemVida = (double) this.getVidaAtual() / this.getVidaMaxima();
    int partesPreenchidas = (int) (porcentagemVida * COMPRIMENTO_BARRA_HP);
    int partesVazias = (int) COMPRIMENTO_BARRA_HP - partesPreenchidas;

    StringBuilder barraConstruida = new StringBuilder();
    for (int i = 0; i < partesPreenchidas; i++){
        barraConstruida.append("█"); 
    }
    // Loop para adicionar os caracteres vazios
    for (int i = 0; i < partesVazias; i++) {
    barraConstruida.append("-");
    }
    String barraFinal = "[" + barraConstruida.toString() + "]"; // Converte para String no final
    System.out.println(this.getNome() + " HP: " + barraFinal + " (" + this.getVidaAtual() + "/" + this.getVidaMaxima() + ")");
    }

    // --- NOVOS MÉTODOS AUXILIARES para Crítico e Esquiva ---
    // Métodos auxiliares concretos para calcular a chance (herdados por Heroi e Monstro)

    public boolean tentarCritico(){
        // Gera um número aleatório entre 0.0 (inclusive) e 1.0 (exclusivo)
        // Multiplica por 100 para ter um número entre 0 e 99.99...
        // Compara com a chanceCritico do personagem
        return Math.random() * 100 < this.chanceCritico;
    }

    public boolean tentarEsquiva(){
        return Math.random() * 100 < this.chanceEsquiva;
    }
}
