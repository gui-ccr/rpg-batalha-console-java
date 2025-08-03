package com.guiccr.rpg;

public class Heroi extends Personagem {

    private int energia;
    private int forca;
    private int agilidade;
    private int vigor;
    private int presenca;
    private int intelecto;
    private int nivel;
    private int experienciaAtual;
    private int experienciaParaProximoNivel;

    // Construtor 1: Para criar um NOVO herói (recebe atributos OP)
    public Heroi(String nome, int forca, int agilidade, int vigor, int presenca, int intelecto, int energiaInicial) {
        // --- CHAMADA super() DEVE SER A PRIMEIRA ---
        super(nome, 
              calcularVidaMaxima(vigor), 
              calcularAtaque(forca), 
              calcularDefesa(agilidade), 
              calcularChanceCritico(presenca, forca), 
              calcularMultiplicadorCritico(intelecto), 
              calcularChanceEsquiva(agilidade, intelecto));

        // Validações DEPOIS do super()
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
        if (forca < 0 || agilidade < 0 || vigor < 0 || presenca < 0 || intelecto < 0) throw new IllegalArgumentException("Atributos de FOR, AGI, VIG, PRE, INT não podem ser negativos.");
        if (energiaInicial < 0) throw new IllegalArgumentException("Energia inicial não pode ser negativa.");

        // Inicializa atributos próprios do Heroi (DEPOIS do super())
        this.forca = forca;
        this.agilidade = agilidade;
        this.vigor = vigor;
        this.presenca = presenca;
        this.intelecto = intelecto;
        this.energia = energiaInicial > 0 ? energiaInicial : (10 + (intelecto * 2));
        this.nivel = 1;
        this.experienciaAtual = 0;
        this.experienciaParaProximoNivel = calcularEXPParaProximoNivel(this.nivel);
    }
    
    // Construtor 2: Para CARREGAR um herói do banco de dados (recebe todos os atributos)
    public Heroi(int id, String nome, int vidaAtual, int vidaMaxima, int ataque, int defesa,
                 int chanceCritico, double multiplicadorCritico, int chanceEsquiva,
                 int energia, int forca, int agilidade, int vigor, int presenca, int intelecto,
                 int nivel, int experienciaAtual, int experienciaParaProximoNivel) {
        // --- CHAMADA super() CORRETA ---
        // Usa o construtor de Personagem que aceita a vida atual como parâmetro
        super(nome, vidaAtual, vidaMaxima, ataque, defesa, chanceCritico, multiplicadorCritico, chanceEsquiva);

        // Inicializa atributos próprios do Heroi com os valores do banco (DEPOIS do super())
        this.energia = energia;
        this.forca = forca;
        this.agilidade = agilidade;
        this.vigor = vigor;
        this.presenca = presenca;
        this.intelecto = intelecto;
        this.nivel = nivel;
        this.experienciaAtual = experienciaAtual;
        this.experienciaParaProximoNivel = experienciaParaProximoNivel;
    }
    
    // --- GETTERS e SETTERS (somente getters para energia, forca, etc.)
    public int getEnergia() { return this.energia; }
    public int getForca() { return this.forca; }
    public int getAgilidade() { return this.agilidade; }
    public int getVigor() { return this.vigor; }
    public int getPresenca() { return this.presenca; }
    public int getIntelecto() { return this.intelecto; }
    public int getNivel() { return this.nivel; }
    public int getExperienciaAtual() { return this.experienciaAtual; }
    public int getExperienciaParaProximoNivel() { return this.experienciaParaProximoNivel; }

    // --- MÉTODOS DE LEVEL UP E EXPERIÊNCIA ---
    public void ganharExperiencia(int expGanha) {
        if (expGanha < 0) throw new IllegalArgumentException("Experiência ganha não pode ser negativa.");
        this.experienciaAtual += expGanha;
        System.out.println(ConsoleColors.CYAN_BRIGHT + this.getNome() + ConsoleColors.RESET +
                           " recebeu " + ConsoleColors.YELLOW_BRIGHT + expGanha + ConsoleColors.RESET + " EXP!" +
                           " Total: " + this.experienciaAtual + "/" + this.experienciaParaProximoNivel);
        if (this.experienciaAtual >= this.experienciaParaProximoNivel) {
            subirNivel();
        }
    }
    void subirNivel() {
        int expNecessariaAnterior = this.experienciaParaProximoNivel;
        this.nivel++;
        this.experienciaAtual -= expNecessariaAnterior;
        this.experienciaParaProximoNivel = calcularEXPParaProximoNivel(this.nivel);
        System.out.println("\n" + ConsoleColors.GREEN_BRIGHT + ConsoleColors.BOLD + "🎉 LEVEL UP! " +
                           this.getNome() + " alcançou o Nível " + this.nivel + "!" + ConsoleColors.RESET);
        
        // Aumenta os atributos base ao subir de nível
        aumentarAtributosBase();
        
        recalcularAtributosDeCombate();
        this.setVidaAtual(this.getVidaMaxima());
        System.out.println(ConsoleColors.GREEN_BRIGHT + "Novos status base recalculados:" + ConsoleColors.RESET);
        exibirStatus();
        
        // Salva imediatamente os novos atributos no banco de dados
        RepositorioDeHerois.atualizarHeroi(this);
        System.out.println(ConsoleColors.GREEN_BRIGHT + "Progresso salvo automaticamente!" + ConsoleColors.RESET);
    }
    
    private void aumentarAtributosBase() {
        // Aumenta todos os atributos base em +1 por nível
        this.forca++;
        this.agilidade++;
        this.vigor++;
        this.presenca++;
        this.intelecto++;
        
        System.out.println(ConsoleColors.YELLOW_BRIGHT + "Atributos base aumentados: " +
                          "FOR+" + 1 + " AGI+" + 1 + " VIG+" + 1 + " PRE+" + 1 + " INT+" + 1 + ConsoleColors.RESET);
    }
    
    private void recalcularAtributosDeCombate() {
        // Usa os setters de Personagem para atualizar os atributos
        this.setVidaMaxima(calcularVidaMaxima(this.vigor));
        this.setAtaque(calcularAtaque(this.forca));
        this.setDefesa(calcularDefesa(this.agilidade));
        this.setChanceCritico(calcularChanceCritico(this.presenca, this.forca));
        this.setMultiplicadorCritico(calcularMultiplicadorCritico(this.intelecto));
        this.setChanceEsquiva(calcularChanceEsquiva(this.agilidade, this.intelecto));
        this.energia = 10 + (this.intelecto * 2);
    }
    
    // Métodos auxiliares estáticos para cálculo (já na Personagem)
    private static int calcularVidaMaxima(int vigor) { return 50 + (vigor * 10); }
    private static int calcularAtaque(int forca) { return 10 + (forca * 2); }
    private static int calcularDefesa(int agilidade) { return 5 + (agilidade * 1); }
    private static int calcularChanceCritico(int presenca, int forca) { return Math.min(100, 5 + (presenca * 2) + (forca * 1)); }
    private static double calcularMultiplicadorCritico(int intelecto) { return 1.5 + (intelecto * 0.05); }
    private static int calcularChanceEsquiva(int agilidade, int intelecto) { return Math.min(100, 5 + (agilidade * 2) + (intelecto * 1)); }
    private static int calcularEXPParaProximoNivel(int nivelAtual) { return nivelAtual * 100 + (nivelAtual - 1) * 50; }

    // --- IMPLEMENTAÇÃO DO MÉTODO ABSTRATO ATACAR() E HABILIDADE ESPECIAL ---
    @Override
    public void atacar(Personagem alvo) {
        System.out.println("\n" + ConsoleColors.CYAN_BRIGHT + this.getNome() + ConsoleColors.RESET + 
                           " ataca " + ConsoleColors.RED_BRIGHT + alvo.getNome() + ConsoleColors.RESET + "!");

        // Lógica da esquiva: Alvo tenta esquivar
        if (alvo.tentarEsquiva()) {
            System.out.println(ConsoleColors.BLUE + alvo.getNome() + " esquivou do ataque!" + ConsoleColors.RESET);
            return; // O ataque não causa dano
        }

        // Calcula o dano base: Ataque do Herói - Defesa do Alvo
        int danoCausado = Math.max(0, this.getAtaque() - alvo.getDefesa());

        // Lógica de Crítico: Atacante tenta um crítico
        if (this.tentarCritico()) {
            danoCausado = (int) (danoCausado * this.getMultiplicadorCritico());
            System.out.println(ConsoleColors.CYAN_BRIGHT + ConsoleColors.BOLD + this.getNome() + ConsoleColors.RESET
                               + ConsoleColors.YELLOW_BRIGHT + " acertou um GOLPE CRÍTICO!!" + ConsoleColors.RESET);
        }

        // Lógica de dano mínimo:
        if (this.getAtaque() > 0 && danoCausado == 0) { // Ataque não nulo mas dano zerado pela defesa
            danoCausado = 1;
        } else if (this.getAtaque() == 0) { // Se o herói não tiver ataque, não causa dano.
            danoCausado = 0;
        }

        alvo.receberDano(danoCausado); // O alvo recebe o dano calculado.
    }
    
    public void usarHabilidadeEspecial(Personagem alvo) {
        if (this.energia < 10) {
            System.out.println(ConsoleColors.RED + this.getNome() + " não tem energia suficiente para usar habilidade especial! (Necessário: 10)" + ConsoleColors.RESET);
            return;
        }

        this.energia -= 10; // Consome 10 de energia

        System.out.println("\n" + ConsoleColors.PURPLE_BRIGHT + ConsoleColors.BOLD + this.getNome() + 
                           " usa GOLPE HEROICO contra " + alvo.getNome() + "!" + ConsoleColors.RESET);

        // Lógica da esquiva: Alvo tenta esquivar (mais difícil contra habilidade especial)
        if (alvo.tentarEsquiva() && Math.random() < 0.5) { // 50% de chance de esquivar mesmo tentando
            System.out.println(ConsoleColors.BLUE + alvo.getNome() + " conseguiu esquivar parcialmente do golpe especial!" + ConsoleColors.RESET);
            // Mesmo esquivando, toma metade do dano
        }

        // Dano da habilidade especial é maior: 1.5x do ataque normal + bônus baseado no nível
        int danoEspecial = (int) ((this.getAtaque() * 1.5) + (this.getNivel() * 5) - (alvo.getDefesa() * 0.5));
        
        // Chance aumentada de crítico na habilidade especial
        if (Math.random() * 100 < (this.getChanceCritico() * 1.5)) {
            danoEspecial = (int) (danoEspecial * this.getMultiplicadorCritico());
            System.out.println(ConsoleColors.YELLOW_BRIGHT + ConsoleColors.BOLD + "🌟 CRÍTICO HEROICO! 🌟" + ConsoleColors.RESET);
        }

        // Dano mínimo para habilidade especial
        danoEspecial = Math.max(5, danoEspecial);

        System.out.println(ConsoleColors.PURPLE + "Energia restante: " + this.energia + ConsoleColors.RESET);
        alvo.receberDano(danoEspecial);
    }
}