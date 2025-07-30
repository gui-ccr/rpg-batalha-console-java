package com.guiccr.rpg;

public class Heroi extends Personagem {

    private int energia;

    // Atributos específicos do agente (Ordem Paranormal)
    private int forca; // FOR
    private int agilidade; // AGI
    private int vigor; // VIG
    private int presenca; // PRE
    private int intelecto; // INT
    private int nivel = 1;
    private int experienciaAtual = 0;
    private int experienciaParaProximoNivel;

    public Heroi(String nome, int forca, int agilidade, int vigor, int presenca, int intelecto,
            int energiaInicial) {
        
        super(nome, // Nome do personagem
                // Atributos calculados para Personagem, com validações embutidas nos métodos
                // auxiliares
                calcularVidaMaxima(vigor),
                calcularAtaque(forca),
                calcularDefesa(agilidade),
                calcularChanceCritico(presenca, forca),
                calcularMultiplicadorCritico(intelecto),
                calcularChanceEsquiva(agilidade, intelecto));

      
        this.energia = energiaInicial; // Assume que energiaInicial já foi validada indiretamente
        this.forca = forca;
        this.agilidade = agilidade;
        this.vigor = vigor;
        this.presenca = presenca;
        this.intelecto = intelecto;
        this.nivel = 1;
        this.experienciaAtual = 0;
        this.experienciaParaProximoNivel = this.nivel * 112;
    }

    // === Getters ===
    public int getEnergia() {
        return energia;
    }

    public int getForca() {
        return forca;
    }

    public int getAgilidade() {
        return agilidade;
    }

    public int getVigor() {
        return vigor;
    }

    public int getPresenca() {
        return presenca;
    }

    public int getIntelecto() {
        return intelecto;
    }

    public int getNivel() {
        return nivel;
    }

    public int getExperienciaAtual() {
        return experienciaAtual;
    }

    public int getExperienciaParaProximoNivel() {
        return experienciaParaProximoNivel;
    }

    // --- MÉTODOS AUXILIARES ESTÁTICOS PARA CÁLCULO E VALIDAÇÃO ---

    // Métodos para cálculos de atributos de combate
    private static int calcularVidaMaxima(int vigor) {
        if (vigor < 0)
            throw new IllegalArgumentException("Vigor não pode ser negativo.");
        return 50 + (vigor * 10);
    }

    private static int calcularAtaque(int forca) {
        if (forca < 0)
            throw new IllegalArgumentException("Força não pode ser negativa.");
        return 10 + (forca * 2);
    }

    private static int calcularDefesa(int agilidade) {
        if (agilidade < 0)
            throw new IllegalArgumentException("Agilidade não pode ser negativa.");
        return 5 + (agilidade * 1);
    }

    private static int calcularChanceCritico(int presenca, int forca) {
        if (presenca < 0 || forca < 0)
            throw new IllegalArgumentException("Presença e Força não podem ser negativos para cálculo de crítico.");
        return Math.min(100, 5 + (presenca * 2) + (forca * 1));
    }

    private static double calcularMultiplicadorCritico(int intelecto) {
        if (intelecto < 0)
            throw new IllegalArgumentException(
                    "Intelecto não pode ser negativo para cálculo de multiplicador crítico.");
        return 1.5 + (intelecto * 0.05);
    }

    private static int calcularChanceEsquiva(int agilidade, int intelecto) {
        if (agilidade < 0 || intelecto < 0)
            throw new IllegalArgumentException(
                    "Agilidade e Intelecto não podem ser negativos para cálculo de esquiva.");
        return Math.min(100, 5 + (agilidade * 2) + (intelecto * 1));
    }

    // --- MÉTODO ESTÁTICO: Calcular EXP para o Próximo Nível ---
    private static int calcularEXPParaProximoNivel(int nivelAtual) {
        // Fórmula de progressão de EXP
        return nivelAtual * 100 + (nivelAtual - 1) * 50;
    }

    // metodos para calcular exp ganha e nivel
    // --- NOVOS MÉTODOS DE LEVEL UP E EXPERIÊNCIA ---
    // Método para adicionar experiência ao Herói e verificar level up
    public void ganharExperiencia(int expGanha) { 
        if (expGanha < 0) {
            throw new IllegalArgumentException("Experiência ganha não pode ser negativa.");
        }
        this.experienciaAtual += expGanha; 
        System.out.println(ConsoleColors.CYAN_BRIGHT + this.getNome() + ConsoleColors.RESET +
                " recebeu " + ConsoleColors.YELLOW_BRIGHT + expGanha + ConsoleColors.RESET + " EXP!" +
                " Total: " + this.experienciaAtual + "/" + this.experienciaParaProximoNivel);

        // Verifica se é hora de subir de nível
        if (this.experienciaAtual >= this.experienciaParaProximoNivel) {
            subirNivel(); // Chama o método de subir de nível
        }
    }

    // Método para subir de nível (Esqueleto Inicial - a interação virá na próxima
    // etapa)
    void subirNivel() { 
        int expNecessariaAnterior = this.experienciaParaProximoNivel; // Guarda a EXP necessária do nível atual

        this.nivel++; // Incrementa o nível

        // Atualiza a experiência atual (mantém o excedente)
        this.experienciaAtual -= expNecessariaAnterior; // Subtrai o custo do nível anterior

        // Recalcula a experiência para o próximo nível com base no novo nível
        this.experienciaParaProximoNivel = calcularEXPParaProximoNivel(this.nivel);

        // Mensagem de Level Up
        System.out.println("\n" + ConsoleColors.GREEN_BRIGHT + ConsoleColors.BOLD + "🎉 LEVEL UP! " +
                this.getNome() + " alcançou o Nível " + this.nivel + "!" + ConsoleColors.RESET);

        // Por enquanto, atributos de combate são recalculados automaticamente (apenas
        // para teste)
        // Isso será interativo na próxima etapa.
        recalcularAtributosDeCombate(); // Chama método para recalcular Personagem attributes
        this.setVidaAtual(this.getVidaMaxima()); // Cura o herói totalmente

        System.out.println(ConsoleColors.GREEN_BRIGHT + "Novos status base recalculados:" + ConsoleColors.RESET);
        exibirStatus(); // Exibe os novos status base do Personagem
    }

    // MÉTODO: Recalcula os atributos de combate baseados nos atributos OP

    private void recalcularAtributosDeCombate() {
        // Usa os setters de Personagem para atualizar os atributos
        this.setVidaMaxima(calcularVidaMaxima(this.vigor));
        this.setAtaque(calcularAtaque(this.forca));
        this.setDefesa(calcularDefesa(this.agilidade));
        this.setChanceCritico(calcularChanceCritico(this.presenca, this.forca));
        this.setMultiplicadorCritico(calcularMultiplicadorCritico(this.intelecto));
        this.setChanceEsquiva(calcularChanceEsquiva(this.agilidade, this.intelecto));
        // A energia é um atributo próprio do Heroi, pode ser recalculada aqui se basear
        // em INT
        this.energia = 10 + (this.intelecto * 2); // Exemplo: Energia também escala com Intelecto
    }

    // --- Implementação do Método Abstrato 'atacar()' herdado de Personagem ---
    @Override
    public void atacar(Personagem alvo) {
        System.out.println("\n" + ConsoleColors.CYAN_BRIGHT + this.getNome() + ConsoleColors.RESET + " ataca "
                + ConsoleColors.RED_BRIGHT + alvo.getNome() + ConsoleColors.RESET + "!");

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
            System.out.println(ConsoleColors.CYAN_BRIGHT + ConsoleColors.BOLD + this.getNome() + ConsoleColors.RESET
                    + ConsoleColors.YELLOW_BRIGHT + " acertou um GOLPE CRÍTICO!!" + ConsoleColors.RESET);
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
            System.out.println(ConsoleColors.CYAN_BRIGHT + this.getNome() + ConsoleColors.RESET + " usa uma "
                    + ConsoleColors.PURPLE + " habilidade especial " + ConsoleColors.RESET + " em "
                    + ConsoleColors.RED_BRIGHT + alvo.getNome() + ConsoleColors.RESET + "!");

            // Calcula o dano base: Dano dobrado do ataque do Herói - Defesa do Alvo
            int danoBaseHabilidade = this.getAtaque() * 2;

            // Lógica de dano mínimo:
            if (this.getAtaque() > 0 && danoBaseHabilidade == 0) {
                danoBaseHabilidade = 1;
            } else if (danoBaseHabilidade == 0) {
                // danoCausado = 0; // Esta linha não é necessária se danoBaseHabilidade já for
                // 0
            }

            if (this.tentarCritico()) {
                danoBaseHabilidade = (int) (danoBaseHabilidade * this.getMultiplicadorCritico());
                System.out.println(ConsoleColors.CYAN_BRIGHT + ConsoleColors.BOLD + this.getNome() + ConsoleColors.RESET
                        + ConsoleColors.YELLOW_BRIGHT + " acertou um GOLPE CRÍTICO com a habilidade especial!!"
                        + ConsoleColors.RESET);
            }

            int danoCausado = Math.max(0, danoBaseHabilidade - alvo.getDefesa());

            if (danoCausado == 0 && danoBaseHabilidade > 0) { // Garantir dano mínimo também na habilidade
                danoCausado = 1;
            } else if (danoBaseHabilidade == 0) {
                danoCausado = 0;
            }

            alvo.receberDano(danoCausado);
            this.energia -= 10;
            System.out.println("Energia de " + ConsoleColors.CYAN_BRIGHT + this.getNome() + ConsoleColors.RESET
                    + " atual: " + ConsoleColors.PURPLE_BRIGHT + this.energia + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.CYAN_BRIGHT + this.getNome() + ConsoleColors.RESET
                    + " não tem energia suficiente para usar a habilidade especial!");
        }
    }

}