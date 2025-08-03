package com.guiccr.rpg;

public abstract class Item {
    private final String nome;
    private final String descricao;


    public Item(String nome, String descricao) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do item não pode ser nulo ou vazio.");
        }
        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("Descrição do item não pode ser nula ou vazia.");
        }


        this.nome = nome;
        this.descricao = descricao;
    }

    // Getters
    public String getNome() {return nome;}

    public String getDescricao() {return descricao;}

    @Override
    public String toString() {
        return "Item{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    // metodos abstratos
    public abstract void aplicarEfeito(Heroi heroi);

    // metodo concreto
    public void exibirDetalhes() {
        System.out.println("============ Detalhes do Item =============");
        System.out.println("\nNome: " + nome);
        System.out.println("\nDescrição: " + descricao);
        System.out.println("===========================================");
    }
}