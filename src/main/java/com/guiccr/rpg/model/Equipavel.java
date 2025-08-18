package com.guiccr.rpg.model;

/*
 * interface para itens que podem ser equipados pelo heroi
 * define os metodos que devem ser implementados pelas classes de itens equipaveis
 */
public interface Equipavel {

    // aplica efeito do equipamento no heroi
    void aplicarEfeito(Heroi heroi);

    // remove efeito do equipamento no heroi
    void removerEfeito(Heroi heroi);

    // verifica se o equipamento está equipado
    boolean isEquipado();

    // retorna o tipo do equipamento para o Map
    String getTipoEquipamento();

}