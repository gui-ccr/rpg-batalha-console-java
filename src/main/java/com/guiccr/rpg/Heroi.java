package com.guiccr.rpg;

public class Heroi extends Personagem {

    private int energia;

    // Atributos específicos do agente (Ordem Paranormal)
    private int forca;     // FOR
    private int agilidade; // AGI
    private int vigor;     // VIG
    private int presenca;  // PRE
    private int intelecto; // INT

    public Heroi(String nome,
                 // Parâmetros de Ordem Paranormal (novos no construtor do Heroi)
                 int forca, int agilidade, int vigor, int presenca, int intelecto,
                 // Parâmetro específico do Heroi
                 int energiaInicial)
    {
        // ====================================================================
        // PASSO 1: CHAMADA AO CONSTRUTOR DA SUPERCLASSE (SUPER)
        // ESTA DEVE SER A PRIMEIRA LINHA EXECUTÁVEL DO CONSTRUTOR.
        // As validações e cálculos para 'super()' são feitos INLINE ou por métodos auxiliares.
        // ====================================================================

        // As validações para nome, vidaMaxima, ataque, defesa, crítico/esquiva
        // serão feitas no construtor de Personagem.

        // VALIDAÇÕES PARA OS ATRIBUTOS DE FORÇA/AGILIDADE/ETC. SÃO FEITAS
        // POR MÉTODOS AUXILIARES QUE RETORNAM O PRÓPRIO ATRIBUTO OU LANÇAM EXCEÇÃO.
        // Isso permite a validação antes do uso nos cálculos do 'super()'.

        super(nome, // Nome do personagem
              // Atributos calculados para Personagem, com validações embutidas nos métodos auxiliares
              calcularVidaMaxima(vigor),
              calcularAtaque(forca),
              calcularDefesa(agilidade),
              calcularChanceCritico(presenca, forca),
              calcularMultiplicadorCritico(intelecto),
              calcularChanceEsquiva(agilidade, intelecto));

        // ====================================================================
        // PASSO 2: INICIALIZAÇÃO DOS ATRIBUTOS PRÓPRIOS DO HEROI
        // ESTE BLOCO SÓ PODE VIR DEPOIS DA CHAMADA 'super()'.
        // ====================================================================
        // Atribuições dos atributos de Ordem Paranormal e energia (já validados pelos métodos auxiliares ou no super())
        this.energia = energiaInicial; // Assume que energiaInicial já foi validada indiretamente
        this.forca = forca;
        this.agilidade = agilidade;
        this.vigor = vigor;
        this.presenca = presenca;
        this.intelecto = intelecto;
    }

    // === Getters ===
    public int getEnergia() {return energia;}
    public int getForca() {return forca;}
    public int getAgilidade() {return agilidade;}
    public int getVigor() {return vigor;}
    public int getPresenca() {return presenca;}
    public int getIntelecto() {return intelecto;}

    // --- MÉTODOS AUXILIARES ESTÁTICOS PARA CÁLCULO E VALIDAÇÃO (Chamados no construtor) ---
    // Estes métodos garantem que os valores passados para o construtor da superclasse
    // e inicializados em Heroi sejam válidos e calculados.

    // Métodos para cálculos de atributos de combate
    private static int calcularVidaMaxima(int vigor) {
        if (vigor < 0) throw new IllegalArgumentException("Vigor não pode ser negativo.");
        return 50 + (vigor * 10);
    }
    private static int calcularAtaque(int forca) {
        if (forca < 0) throw new IllegalArgumentException("Força não pode ser negativa.");
        return 10 + (forca * 2);
    }
    private static int calcularDefesa(int agilidade) {
        if (agilidade < 0) throw new IllegalArgumentException("Agilidade não pode ser negativa.");
        return 5 + (agilidade * 1);
    }
    private static int calcularChanceCritico(int presenca, int forca) {
        if (presenca < 0 || forca < 0) throw new IllegalArgumentException("Presença e Força não podem ser negativos para cálculo de crítico.");
        return Math.min(100, 5 + (presenca * 2) + (forca * 1));
    }
    private static double calcularMultiplicadorCritico(int intelecto) {
        if (intelecto < 0) throw new IllegalArgumentException("Intelecto não pode ser negativo para cálculo de multiplicador crítico.");
        return 1.5 + (intelecto * 0.05);
    }
    private static int calcularChanceEsquiva(int agilidade, int intelecto) {
        if (agilidade < 0 || intelecto < 0) throw new IllegalArgumentException("Agilidade e Intelecto não podem ser negativos para cálculo de esquiva.");
        return Math.min(100, 5 + (agilidade * 2) + (intelecto * 1));
    }
    // Para energia inicial, a validação já está no corpo do construtor de Heroi, antes da atribuição.


    // --- Implementação do Método Abstrato 'atacar()' herdado de Personagem ---
    @Override
    public void atacar(Personagem alvo) {
        System.out.println("\n"+ ConsoleColors.CYAN_BRIGHT + this.getNome() + ConsoleColors.RESET + " ataca " + ConsoleColors.RED_BRIGHT + alvo.getNome() + ConsoleColors.RESET+ "!");

        // Lógica da esquiva
        if (alvo.tentarEsquiva()) {
            System.out.println(ConsoleColors.BLUE + alvo.getNome() + " esquivou do ataque!" + ConsoleColors.RESET);
            return; // o ataque nao causa dano
        }

        // Calcula o dano base: Ataque do Herói - Defesa do Alvo
        int danoCausado = Math.max(0, this.getAtaque() - alvo.getDefesa());

        // Lógica de Crítico
        if (this.tentarCritico()) {
            danoCausado = (int) (danoCausado * this.getMultiplicadorCritico());
            System.out.println(ConsoleColors.CYAN_BRIGHT + ConsoleColors.BOLD + this.getNome() + ConsoleColors.RESET + ConsoleColors.YELLOW_BRIGHT + " acertou um GOLPE CRÍTICO!!" + ConsoleColors.RESET);
        }

        // Lógica de dano mínimo:
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
            System.out.println( ConsoleColors.CYAN_BRIGHT + this.getNome() + ConsoleColors.RESET + " usa uma " + ConsoleColors.PURPLE + " habilidade especial " + ConsoleColors.RESET + " em " + ConsoleColors.RED_BRIGHT + alvo.getNome() +  ConsoleColors.RESET + "!");

            // Calcula o dano base: Dano dobrado do ataque do Herói - Defesa do Alvo
            int danoBaseHabilidade = this.getAtaque() * 2;

            // Lógica de dano mínimo:
            if (this.getAtaque() > 0 && danoBaseHabilidade == 0) {
                danoBaseHabilidade = 1;
            } else if (danoBaseHabilidade == 0) {
                // danoCausado = 0; // Esta linha não é necessária se danoBaseHabilidade já for 0
            }

            if (this.tentarCritico()) {
                danoBaseHabilidade = (int) (danoBaseHabilidade * this.getMultiplicadorCritico());
                System.out.println(ConsoleColors.CYAN_BRIGHT + ConsoleColors.BOLD + this.getNome() + ConsoleColors.RESET + ConsoleColors.YELLOW_BRIGHT + " acertou um GOLPE CRÍTICO com a habilidade especial!!" + ConsoleColors.RESET);
            }

            int danoCausado = Math.max(0, danoBaseHabilidade - alvo.getDefesa());

            if (danoCausado == 0 && danoBaseHabilidade > 0) { // Garantir dano mínimo também na habilidade
                 danoCausado = 1;
            } else if (danoBaseHabilidade == 0) {
                 danoCausado = 0;
            }

            alvo.receberDano(danoCausado);
            this.energia -= 10;
            System.out.println("Energia de " + ConsoleColors.CYAN_BRIGHT + this.getNome() + ConsoleColors.RESET + " atual: "+ ConsoleColors.PURPLE_BRIGHT + this.energia + ConsoleColors.RESET);
        } else {
            System.out.println( ConsoleColors.CYAN_BRIGHT + this.getNome() + ConsoleColors.RESET + " não tem energia suficiente para usar a habilidade especial!");
        }
    }
}