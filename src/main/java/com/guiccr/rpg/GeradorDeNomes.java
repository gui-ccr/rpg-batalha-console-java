package com.guiccr.rpg;

import java.util.ArrayList;
import java.util.List;

public class GeradorDeNomes {
    /*
     * crio uma lista para guardar os nomes de monstros que serao gerados
     * a minha lista é privada, assim elas nao podem ser acessadas de por outras
     * classes
     * a minha lista é estatica, para que nao tenha copias dessa mesma lista e
     * qualquer alteração sera feita na minha lista original
     * e ela é final ou seja nao é possivel de ser mudada, porem os itens podem ser
     * adicionados ou removidos
     */
    private static final ArrayList<String> nomesMonstros = new ArrayList<>();
    // * mesma coisa para a lista de nomes de herois
    private static final ArrayList<String> nomesHerois = new ArrayList<>();

    static {
        nomesMonstros.add("Esqueleto de Lodo");
        nomesMonstros.add("Zumbi de Sangue");
        nomesMonstros.add("Mumia");
        nomesMonstros.add("Zumbi de Lodo");
        nomesMonstros.add("Inexistido");

        nomesHerois.add("Guilherme o Ocultista");
        nomesHerois.add("Milena a Investigadora");
        nomesHerois.add("Henrique o Combatente");
        nomesHerois.add("Matheus o Ocultista");

    }

    public static String gerarNomesAleatorios(List<String> nomesDisponiveis) {
        if (nomesDisponiveis.isEmpty()) {
            return "semNome";
        }
        // aqui vai gerar um indice aleatorio para cada item da minha lista
        int indiceAleatorio = (int) (Math.random() * nomesDisponiveis.size());
        return nomesDisponiveis.get(indiceAleatorio); // retorno o nome no indice que for gerado
    }

    // metodos especificos para gerar o nome dos herois e dos monstros

    public static String gerarNomeHerois() {
        return gerarNomesAleatorios(nomesHerois);
    }

    public static String gerarNomeMonstro() {
        return gerarNomesAleatorios(nomesMonstros);
    }

}
