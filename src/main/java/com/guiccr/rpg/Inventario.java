package com.guiccr.rpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// Esta classe representa o inventário do herói, onde itens podem ser armazenados e gerenciados.
// Ela pode conter métodos para adicionar, remover e listar itens, além de aplicar efeitos dos itens no herói.
public class Inventario {
    private ArrayList<Item> itens;

    public Inventario() {
        this.itens = new ArrayList<>();
    }

    // Método para adicionar um item ao inventário
    public void adicionarItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("O item não pode ser nulo.");
            
        }
        this.itens.add(item);
        System.out.println("Item adicionado ao inventário: " + item.getNome());
    }
    // Método para remover um item do inventário
    public Optional<Item> removerItem(int indice){
        if (indice < 0 || indice >= itens.size()) {
            throw new IllegalArgumentException("Índice inválido.");
        }
        Item itemRemovido = this.itens.remove(indice);
        System.out.println("Item removido do inventário: " + itemRemovido.getNome());
        return Optional.of(itemRemovido);
    }
    // Método para listar todos os itens no inventário
    public List<Item> listarItens() {
        // Retorna uma cópia da lista de itens para evitar modificações externas
        // Isso é importante para manter a integridade do inventário.
        return new ArrayList<>(this.itens);
    }
    
    // Método para exibir os itens no console (usado internamente quando necessário)
    public void exibirItens() {
        if (itens.isEmpty()) {
            System.out.println("O inventário está vazio.");
            return;
        }
        System.out.println("Itens no inventário:");
        for (int i = 0; i < itens.size(); i++) {
            Item item = itens.get(i);
            System.out.println((i + 1) + ". " + item.getNome() + " - " + item.getDescricao());
        }
        System.out.println("Total de itens: " + itens.size());
    }
    // Método para aplicar o efeito de um item no herói 
    public void aplicarEfeitoItem(int indice, Heroi heroi){
        if (indice < 0 || indice >= itens.size()) {
            throw new IllegalArgumentException("Índice inválido.");
            
        }
        Item item = this.itens.get(indice);
        item.aplicarEfeito(heroi);
        System.out.println("Usando " + item.getNome() + " no Agente " + heroi.getNome() + "." 
                + "\n Efeito aplicado: " + ConsoleColors.FAINT + item.getDescricao() + ConsoleColors.RESET + ".");
    }
    // Método para equipar item
    public void equiparItem(int indice, Heroi heroi){
        if (indice < 0 || indice >= itens.size()) {
            throw new IllegalArgumentException("Índice inválido.");
        }
        Item item = this.itens.get(indice);
        if (item instanceof Espada) {
            ((Espada) item).aplicarEfeito(heroi);
        } else {
            System.out.println("Este item não pode ser equipado: " + item.getNome());
        }
    }
    
    public boolean equiparItemDoInventario(int indice, Heroi heroi) {
        // Valida índice
        if (indice < 0 || indice >= itens.size()) {
            System.out.println("Índice inválido.");
            return false;
        }
        
        Item item = this.itens.get(indice);
        
        // Verifica se é equipável
        if (!(item instanceof Equipavel)) {
            System.out.println("Este item não pode ser equipado.");
            return false;
        }
        
        Equipavel equipamento = (Equipavel) item;
        
        // Tenta equipar no herói
        if (heroi.equiparItem(equipamento)) {
            // Remove do inventário apenas se equipou com sucesso
            this.itens.remove(indice);
            return true;
        }
        
        return false;
    }

}