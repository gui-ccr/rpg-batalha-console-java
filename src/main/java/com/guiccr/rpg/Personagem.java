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
    
    // construtor inicial do personagem pede nome a vida, ataque e defesa
    public Personagem(String nome, int vidaMaxima, int ataque, int defesa){
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
        this.nome = nome;
        this.vidaAtual = vidaMaxima;
        this.vidaMaxima = vidaMaxima;
        this.ataque = ataque;
        this.defesa = defesa;
    }

    /*
     * -- Metodos getters ( para acessar os atributos privados)
     * nao vou precisar de setter porque vou alterar os atributos internamente
     * eles vao ser alterados por outros metodos como "receber dano"
     */
    public String getNome(){
        return this.nome;
    }
    public int getVidaAtual(){
        return this.vidaAtual;
    }
    public int getVidaMaxima(){
        return this.vidaMaxima;
    }
    public int getAtaque(){
        return this.ataque;
    }
    public int getDefesa(){
        return this.defesa;
    }

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
    public void exibirStatus(){
        System.out.println(this.nome + " | Vida: " + this.vidaAtual + "/" + this.vidaMaxima + " | Atk: " + this.ataque + " | Def: " + this.defesa);
    }
}
