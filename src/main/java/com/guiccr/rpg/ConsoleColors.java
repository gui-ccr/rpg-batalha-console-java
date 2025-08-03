package com.guiccr.rpg;

// Esta classe contém constantes para códigos de cores ANSI.
// Torna o uso de cores no console mais legível.
public final class ConsoleColors { // 'final' porque não queremos que seja herdada ou alterada

    // Reset
    public static final String RESET = "\u001B[0m"; // Volta à cor padrão

    // Cores de Texto (Foreground)
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String ORANGE = "\u001B[38;5;208m"; // Laranja (não é padrão ANSI, mas funciona na maioria dos terminais)
    public static final String GRAY = "\u001B[90m"; // Cinza (usado como cor de texto escuro)
    public static final String LIGHT_GRAY = "\u001B[37m"; // Cinza Claro (usado como cor de texto claro)
    public static final String MAGENTA = "\u001B[35m"; // Magenta (pode ser usado como roxo)
    public static final String BROWN = "\u001B[38;5;94m"; // Marrom (não é padrão ANSI, mas funciona na maioria dos terminais)
    public static final String PINK = "\u001B[38;5;213m"; // Rosa (não é padrão ANSI, mas funciona na maioria dos terminais)
    public static final String GOLD = "\u001B[38;5;220m"; // Dourado (não é padrão ANSI, mas funciona na maioria dos terminais)
    public static final String SILVER = "\u001B[38;5;250m"; // Prata (não é padrão ANSI, mas funciona na maioria dos terminais)

    // Cores de Texto Escuras (Dark Foreground Colors)
    public static final String DARK_BLUE = "\u001B[38;5;21m"; // Azul Escuro (não é padrão ANSI, mas funciona na maioria dos terminais)
    public static final String DARK_GREEN = "\u001B[38;5;22m"; // Verde Escuro (não é padrão ANSI, mas funciona na maioria dos terminais)
    public static final String DARK_RED = "\u001B[38;5;88m"; // Vermelho Escuro (não é padrão ANSI, mas funciona na maioria dos terminais)
    public static final String DARK_PURPLE = "\u001B[38;5;54m"; // Roxo Escuro (não é padrão ANSI, mas funciona na maioria dos terminais)
    public static final String DARK_CYAN = "\u001B[38;5;30m"; // Ciano Escuro (não é padrão ANSI, mas funciona na maioria dos terminais)
    public static final String DARK_GRAY = "\u001B[38;5;236m"; // Cinza Escuro (não é padrão ANSI, mas funciona na maioria dos terminais)


    // Cores de Texto Brilhantes (Bright/Light Foreground Colors)
    public static final String BLACK_BRIGHT = "\u001B[90m";  // Cinza
    public static final String RED_BRIGHT = "\u001B[91m";    // Vermelho Claro
    public static final String GREEN_BRIGHT = "\u001B[92m";  // VERDE CLARO
    public static final String YELLOW_BRIGHT = "\u001B[93m"; // AMARELO BRILHANTE (Laranja Claro)
    public static final String BLUE_BRIGHT = "\u001B[94m";   // Azul Claro
    public static final String PURPLE_BRIGHT = "\u001B[95m"; // Magenta Claro
    public static final String CYAN_BRIGHT = "\u001B[96m";   // Ciano Claro
    public static final String WHITE_BRIGHT = "\u001B[97m";  // Branco Brilhante


    // Estilos
    public static final String BOLD = "\u001B[1m"; // Negrito
    public static final String FAINT = "\u001B[2m"; // Fraco (dim)
    public static final String ITALIC = "\u001B[3m"; // Itálico
    public static final String UNDERLINE = "\u001B[4m"; // Sublinhado

    // Cores de Fundo (Background) - Opcional, menos comum em jogos de console simples
    public static final String BG_RED = "\u001B[41m";

    // Construtor privado para evitar instanciação (é uma classe utilitária de constantes)
    private ConsoleColors() {
        // Não faz nada, apenas para evitar que instanciem esta classe.
    }
}