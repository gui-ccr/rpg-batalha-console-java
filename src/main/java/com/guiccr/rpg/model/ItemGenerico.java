package com.guiccr.rpg.model;

/**
 * Classe para itens genéricos que não se encaixam em categorias específicas
 */
public class ItemGenerico extends Item {
    
    public ItemGenerico(String nome, String descricao) {
        super(nome, descricao);
    }

    @Override
    public void aplicarEfeito(Heroi heroi) {
        // Itens genéricos não têm efeito específico
        System.out.println("O item " + getNome() + " não possui efeito especial.");
    }

    @Override
    public String toString() {
        return getNome() + " - " + getDescricao();
    }
}
