package com.guiccr.rpg.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.guiccr.rpg.model.Heroi;
import com.guiccr.rpg.model.Inventario;
import com.guiccr.rpg.model.Item;
import com.guiccr.rpg.model.PocaoVida;
import com.guiccr.rpg.model.Espada;
import com.guiccr.rpg.model.ItemGenerico;
import com.guiccr.rpg.ConsoleColors;

public final class RepositorioDeHerois {
    private static final String URL_JDBC = "jdbc:sqlite:db/rpg.db";
    private RepositorioDeHerois() {}

    // Método para garantir que a coluna vida_atual existe
    private static void garantirColunaVidaAtual() {
        String verificarColuna = "PRAGMA table_info(herois);";
        String adicionarColuna = "ALTER TABLE herois ADD COLUMN vida_atual INTEGER;";
        String atualizarDados = "UPDATE herois SET vida_atual = vida_maxima WHERE vida_atual IS NULL;";
        
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             PreparedStatement stmt = conexao.prepareStatement(verificarColuna);
             ResultSet rs = stmt.executeQuery()) {
            
            boolean colunaExiste = false;
            while (rs.next()) {
                if ("vida_atual".equals(rs.getString("name"))) {
                    colunaExiste = true;
                    break;
                }
            }
            
            if (!colunaExiste) {
                try (Statement alterStmt = conexao.createStatement()) {
                    alterStmt.executeUpdate(adicionarColuna);
                    alterStmt.executeUpdate(atualizarDados);
                    System.out.println("Coluna 'vida_atual' adicionada automaticamente ao banco de dados.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar/criar coluna vida_atual: " + e.getMessage());
        }
    }

    // Método para garantir que a tabela de inventário existe
    private static void garantirTabelaInventario() {
        String criarTabela = """
            CREATE TABLE IF NOT EXISTS inventario_itens (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                heroi_nome TEXT NOT NULL,
                item_nome TEXT NOT NULL,
                item_tipo TEXT NOT NULL,
                quantidade INTEGER DEFAULT 1,
                atributo_modificado TEXT,
                valor_modificacao INTEGER DEFAULT 0,
                descricao TEXT,
                FOREIGN KEY (heroi_nome) REFERENCES herois(nome)
            );
        """;
        
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             Statement stmt = conexao.createStatement()) {
            stmt.executeUpdate(criarTabela);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela de inventário: " + e.getMessage());
        }
    }

    public static void salvarHeroi(Heroi heroi) {
        garantirColunaVidaAtual(); // Garantir que a coluna existe antes de salvar
        garantirTabelaInventario(); // Garantir que a tabela de inventário existe
        String sql = "INSERT OR REPLACE INTO herois (nome, vida_atual, vida_maxima, ataque, defesa, chance_critico, multiplicador_critico, chance_esquiva, energia, forca, agilidade, vigor, presenca, intelecto, nivel, experiencia_atual, experiencia_para_proximo_nivel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, heroi.getNome());
            preparedStatement.setInt(2, heroi.getVidaAtual());
            preparedStatement.setInt(3, heroi.getVidaMaxima());
            preparedStatement.setInt(4, heroi.getAtaque());
            preparedStatement.setInt(5, heroi.getDefesa());
            preparedStatement.setInt(6, heroi.getChanceCritico());
            preparedStatement.setDouble(7, heroi.getMultiplicadorCritico());
            preparedStatement.setInt(8, heroi.getChanceEsquiva());
            preparedStatement.setInt(9, heroi.getEnergia());
            preparedStatement.setInt(10, heroi.getForca());
            preparedStatement.setInt(11, heroi.getAgilidade());
            preparedStatement.setInt(12, heroi.getVigor());
            preparedStatement.setInt(13, heroi.getPresenca());
            preparedStatement.setInt(14, heroi.getIntelecto());
            preparedStatement.setInt(15, heroi.getNivel());
            preparedStatement.setInt(16, heroi.getExperienciaAtual());
            preparedStatement.setInt(17, heroi.getExperienciaParaProximoNivel());
            preparedStatement.executeUpdate();
            
            // Salvar o inventário do herói
            salvarInventarioDoHeroi(heroi);
            
            System.out.println("Heroi " + heroi.getNome() + " salvo com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao salvar o herói no banco de dados: " + e.getMessage());
        }
    }
    
    /**
     * Salva o inventário de um herói no banco de dados
     */
    private static void salvarInventarioDoHeroi(Heroi heroi) {
        // Primeiro, limpa o inventário atual do herói no banco
        limparInventario(heroi.getNome());
        
        // Depois, salva todos os itens do inventário atual
        Inventario inventario = heroi.getInventario();
        if (inventario != null) {
            for (Item item : inventario.listarItens()) {
                String tipoItem = obterTipoDoItem(item);
                String atributoMod = obterAtributoModificado(item);
                int valorMod = obterValorModificacao(item);
                
                adicionarItemInventario(heroi.getNome(), item.getNome(), tipoItem, 
                                      1, atributoMod, valorMod, item.getDescricao());
            }
        }
    }

    /**
     * Obtém o tipo do item baseado na sua classe
     */
    private static String obterTipoDoItem(Item item) {
        if (item instanceof PocaoVida) {
            return "poção";
        } else if (item instanceof Espada) {
            return "espada";
        } else {
            return "genérico";
        }
    }

    /**
     * Obtém o atributo modificado pelo item (se aplicável)
     */
    private static String obterAtributoModificado(Item item) {
        if (item instanceof PocaoVida) {
            return "vida";
        } else if (item instanceof Espada) {
            return "ataque";
        }
        return null;
    }

    /**
     * Obtém o valor de modificação do item (se aplicável)
     */
    private static int obterValorModificacao(Item item) {
        if (item instanceof PocaoVida) {
            // Valor padrão para poções de vida
            return 35; 
        } else if (item instanceof Espada) {
            // Valor padrão para espadas
            return 10; 
        }
        return 0;
    }
    
    public static Optional<Heroi> buscarHeroi(String nome) {
        garantirColunaVidaAtual(); // Garantir que a coluna existe antes de buscar
        String sql = "SELECT * FROM herois WHERE nome = ?;";
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, nome);
            try (ResultSet resultado = preparedStatement.executeQuery()) {
                if (resultado.next()) {
                    Heroi heroiEncontrado = new Heroi(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getInt("vida_atual"),
                        resultado.getInt("vida_maxima"),
                        resultado.getInt("ataque"),
                        resultado.getInt("defesa"),
                        resultado.getInt("chance_critico"),
                        resultado.getDouble("multiplicador_critico"),
                        resultado.getInt("chance_esquiva"),
                        resultado.getInt("energia"),
                        resultado.getInt("forca"),
                        resultado.getInt("agilidade"),
                        resultado.getInt("vigor"),
                        resultado.getInt("presenca"),
                        resultado.getInt("intelecto"),
                        resultado.getInt("nivel"),
                        resultado.getInt("experiencia_atual"),
                        resultado.getInt("experiencia_para_proximo_nivel"),
                        carregarInventarioDoHeroi(resultado.getString("nome")) // Carrega inventário do BD
                    );
                    return Optional.of(heroiEncontrado);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            System.err.println("Erro ao buscar herói no banco de dados: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
    
    public static void atualizarHeroi(Heroi heroi) {
        garantirColunaVidaAtual(); // Garantir que a coluna existe antes de atualizar
        String sql = "UPDATE herois SET " +
                     "nome = ?, vida_atual = ?, vida_maxima = ?, ataque = ?, defesa = ?, chance_critico = ?, " +
                     "multiplicador_critico = ?, chance_esquiva = ?, energia = ?, forca = ?, " +
                     "agilidade = ?, vigor = ?, presenca = ?, intelecto = ?, nivel = ?, " +
                     "experiencia_atual = ?, experiencia_para_proximo_nivel = ? " +
                     "WHERE nome = ?;";
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, heroi.getNome());
            preparedStatement.setInt(2, heroi.getVidaAtual());
            preparedStatement.setInt(3, heroi.getVidaMaxima());
            preparedStatement.setInt(4, heroi.getAtaque());
            preparedStatement.setInt(5, heroi.getDefesa());
            preparedStatement.setInt(6, heroi.getChanceCritico());
            preparedStatement.setDouble(7, heroi.getMultiplicadorCritico());
            preparedStatement.setInt(8, heroi.getChanceEsquiva());
            preparedStatement.setInt(9, heroi.getEnergia());
            preparedStatement.setInt(10, heroi.getForca());
            preparedStatement.setInt(11, heroi.getAgilidade());
            preparedStatement.setInt(12, heroi.getVigor());
            preparedStatement.setInt(13, heroi.getPresenca());
            preparedStatement.setInt(14, heroi.getIntelecto());
            preparedStatement.setInt(15, heroi.getNivel());
            preparedStatement.setInt(16, heroi.getExperienciaAtual());
            preparedStatement.setInt(17, heroi.getExperienciaParaProximoNivel());
            preparedStatement.setString(18, heroi.getNome());
            int linhasAfetadas = preparedStatement.executeUpdate();
            if (linhasAfetadas > 0) System.out.println("Herói " + heroi.getNome() + " atualizado com sucesso!");
            else System.out.println("Nenhum herói encontrado com o nome " + heroi.getNome() + " para ser atualizado.");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o herói no banco de dados: " + e.getMessage());
        }
    }
    
    public static void deletarHeroi(String nome) {
        String sql = "DELETE FROM herois WHERE nome = ?;";
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, nome);
            int linhasAfetadas = preparedStatement.executeUpdate();
            if (linhasAfetadas > 0) System.out.println("Herói " + nome + " deletado com sucesso!");
            else System.out.println("Nenhum herói encontrado com o nome " + nome + " para ser deletado.");
        } catch (SQLException e) {
            System.err.println("Erro ao deletar o herói do banco de dados: " + e.getMessage());
        }
    }

    public static void listarHerois() {
        String sql = "SELECT nome, nivel, vida_atual, vida_maxima FROM herois ORDER BY nome;";
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             PreparedStatement preparedStatement = conexao.prepareStatement(sql);
             ResultSet resultado = preparedStatement.executeQuery()) {
            
            System.out.println("\n=== HERÓIS SALVOS ===");
            boolean temHerois = false;
            int indice = 1;
            while (resultado.next()) {
                temHerois = true;
                String nome = resultado.getString("nome");
                int nivel = resultado.getInt("nivel");
                int vidaAtual = resultado.getInt("vida_atual");
                int vidaMaxima = resultado.getInt("vida_maxima");
                
                System.out.println("#" + indice + " - " + ConsoleColors.CYAN_BRIGHT + nome + ConsoleColors.RESET + 
                                 " (Nível " + nivel + ") - HP: " + vidaAtual + "/" + vidaMaxima);
                indice++;
            }
            
            if (!temHerois) {
                System.out.println("Nenhum herói encontrado no banco de dados.");
            }
            System.out.println("====================\n");
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar heróis: " + e.getMessage());
        }
    }

    public static String obterNomeHeroiPorIndice(int indice) {
        String sql = "SELECT nome FROM herois ORDER BY nome;";
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             PreparedStatement preparedStatement = conexao.prepareStatement(sql);
             ResultSet resultado = preparedStatement.executeQuery()) {
            
            int contador = 1;
            while (resultado.next()) {
                if (contador == indice) {
                    return resultado.getString("nome");
                }
                contador++;
            }
            return null; // Índice não encontrado
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar herói por índice: " + e.getMessage());
            return null;
        }
    }

    public static int contarHerois() {
        String sql = "SELECT COUNT(*) as total FROM herois;";
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             PreparedStatement preparedStatement = conexao.prepareStatement(sql);
             ResultSet resultado = preparedStatement.executeQuery()) {
            
            if (resultado.next()) {
                return resultado.getInt("total");
            }
            return 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao contar heróis: " + e.getMessage());
            return 0;
        }
    }

    // ===== MÉTODOS DO INVENTÁRIO =====

    /**
     * Adiciona um item ao inventário do herói
     */
    public static void adicionarItemInventario(String nomeHeroi, String nomeItem, String tipoItem, 
                                             int quantidade, String atributoModificado, int valorModificacao, String descricao) {
        garantirTabelaInventario();
        
        // Verificar se o item já existe no inventário
        String verificarSql = "SELECT quantidade FROM inventario_itens WHERE heroi_nome = ? AND item_nome = ?";
        String inserirSql = "INSERT INTO inventario_itens (heroi_nome, item_nome, item_tipo, quantidade, atributo_modificado, valor_modificacao, descricao) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String atualizarSql = "UPDATE inventario_itens SET quantidade = quantidade + ? WHERE heroi_nome = ? AND item_nome = ?";

        try (Connection conexao = DriverManager.getConnection(URL_JDBC)) {
            // Verificar se item já existe
            try (PreparedStatement verificarStmt = conexao.prepareStatement(verificarSql)) {
                verificarStmt.setString(1, nomeHeroi);
                verificarStmt.setString(2, nomeItem);
                ResultSet rs = verificarStmt.executeQuery();
                
                if (rs.next()) {
                    // Item já existe, apenas aumentar quantidade
                    try (PreparedStatement atualizarStmt = conexao.prepareStatement(atualizarSql)) {
                        atualizarStmt.setInt(1, quantidade);
                        atualizarStmt.setString(2, nomeHeroi);
                        atualizarStmt.setString(3, nomeItem);
                        atualizarStmt.executeUpdate();
                        System.out.println("Quantidade do item " + nomeItem + " aumentada em " + quantidade);
                    }
                } else {
                    // Novo item, inserir
                    try (PreparedStatement inserirStmt = conexao.prepareStatement(inserirSql)) {
                        inserirStmt.setString(1, nomeHeroi);
                        inserirStmt.setString(2, nomeItem);
                        inserirStmt.setString(3, tipoItem);
                        inserirStmt.setInt(4, quantidade);
                        inserirStmt.setString(5, atributoModificado);
                        inserirStmt.setInt(6, valorModificacao);
                        inserirStmt.setString(7, descricao);
                        inserirStmt.executeUpdate();
                        System.out.println("Item " + nomeItem + " adicionado ao inventário de " + nomeHeroi);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar item ao inventário: " + e.getMessage());
        }
    }

    /**
     * Remove um item do inventário do herói
     */
    public static boolean removerItemInventario(String nomeHeroi, String nomeItem, int quantidade) {
        String verificarSql = "SELECT quantidade FROM inventario_itens WHERE heroi_nome = ? AND item_nome = ?";
        String atualizarSql = "UPDATE inventario_itens SET quantidade = quantidade - ? WHERE heroi_nome = ? AND item_nome = ?";
        String deletarSql = "DELETE FROM inventario_itens WHERE heroi_nome = ? AND item_nome = ?";

        try (Connection conexao = DriverManager.getConnection(URL_JDBC)) {
            // Verificar quantidade atual
            try (PreparedStatement verificarStmt = conexao.prepareStatement(verificarSql)) {
                verificarStmt.setString(1, nomeHeroi);
                verificarStmt.setString(2, nomeItem);
                ResultSet rs = verificarStmt.executeQuery();
                
                if (rs.next()) {
                    int quantidadeAtual = rs.getInt("quantidade");
                    
                    if (quantidadeAtual < quantidade) {
                        System.out.println("Quantidade insuficiente de " + nomeItem + " no inventário!");
                        return false;
                    } else if (quantidadeAtual == quantidade) {
                        // Remover item completamente
                        try (PreparedStatement deletarStmt = conexao.prepareStatement(deletarSql)) {
                            deletarStmt.setString(1, nomeHeroi);
                            deletarStmt.setString(2, nomeItem);
                            deletarStmt.executeUpdate();
                            System.out.println("Item " + nomeItem + " removido completamente do inventário");
                        }
                    } else {
                        // Diminuir quantidade
                        try (PreparedStatement atualizarStmt = conexao.prepareStatement(atualizarSql)) {
                            atualizarStmt.setInt(1, quantidade);
                            atualizarStmt.setString(2, nomeHeroi);
                            atualizarStmt.setString(3, nomeItem);
                            atualizarStmt.executeUpdate();
                            System.out.println("Quantidade do item " + nomeItem + " reduzida em " + quantidade);
                        }
                    }
                    return true;
                } else {
                    System.out.println("Item " + nomeItem + " não encontrado no inventário!");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover item do inventário: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista todos os itens do inventário de um herói
     */
    public static void listarInventario(String nomeHeroi) {
        String sql = "SELECT * FROM inventario_itens WHERE heroi_nome = ? ORDER BY item_tipo, item_nome";
        
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, nomeHeroi);
            ResultSet resultado = preparedStatement.executeQuery();
            
            System.out.println("\n=== INVENTÁRIO DE " + nomeHeroi.toUpperCase() + " ===");
            boolean temItens = false;
            String tipoAtual = "";
            
            while (resultado.next()) {
                temItens = true;
                String tipoItem = resultado.getString("item_tipo");
                String nomeItem = resultado.getString("item_nome");
                int quantidade = resultado.getInt("quantidade");
                String atributoMod = resultado.getString("atributo_modificado");
                int valorMod = resultado.getInt("valor_modificacao");
                String descricao = resultado.getString("descricao");
                
                // Separar por tipo de item
                if (!tipoItem.equals(tipoAtual)) {
                    System.out.println("\n--- " + tipoItem.toUpperCase() + " ---");
                    tipoAtual = tipoItem;
                }
                
                System.out.print("• " + nomeItem + " (x" + quantidade + ")");
                if (valorMod != 0 && atributoMod != null) {
                    System.out.print(" - " + atributoMod + " +" + valorMod);
                }
                if (descricao != null && !descricao.trim().isEmpty()) {
                    System.out.print(" | " + descricao);
                }
                System.out.println();
            }
            
            if (!temItens) {
                System.out.println("Inventário vazio.");
            }
            System.out.println("==========================\n");
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar inventário: " + e.getMessage());
        }
    }

    /**
     * Verifica se um herói possui um item específico
     */
    public static int verificarQuantidadeItem(String nomeHeroi, String nomeItem) {
        String sql = "SELECT quantidade FROM inventario_itens WHERE heroi_nome = ? AND item_nome = ?";
        
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, nomeHeroi);
            preparedStatement.setString(2, nomeItem);
            ResultSet resultado = preparedStatement.executeQuery();
            
            if (resultado.next()) {
                return resultado.getInt("quantidade");
            }
            return 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao verificar item no inventário: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Carrega o inventário de um herói do banco de dados
     */
    private static Inventario carregarInventarioDoHeroi(String nomeHeroi) {
        Inventario inventario = new Inventario();
        String sql = "SELECT * FROM inventario_itens WHERE heroi_nome = ?";
        
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, nomeHeroi);
            ResultSet resultado = preparedStatement.executeQuery();
            
            while (resultado.next()) {
                String nomeItem = resultado.getString("item_nome");
                String tipoItem = resultado.getString("item_tipo");
                int quantidade = resultado.getInt("quantidade");
                String atributoMod = resultado.getString("atributo_modificado");
                int valorMod = resultado.getInt("valor_modificacao");
                String descricao = resultado.getString("descricao");
                
                // Cria um item baseado no tipo (por enquanto itens simples)
                Item item = criarItemPorTipo(nomeItem, tipoItem, descricao, atributoMod, valorMod, quantidade);
                if (item != null) {
                    inventario.adicionarItem(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar inventário do herói " + nomeHeroi + ": " + e.getMessage());
        }
        
        return inventario;
    }

    /**
     * Cria um item baseado no tipo especificado
     */
    private static Item criarItemPorTipo(String nome, String tipo, String descricao, 
                                       String atributoMod, int valorMod, int quantidade) {
        switch (tipo.toLowerCase()) {
            case "pocao":
            case "poção":
                return new PocaoVida(nome, descricao, valorMod);
            case "espada":
            case "arma":
                return new Espada(nome, descricao, valorMod, "Normal");
            default:
                // Para tipos não reconhecidos, criar um item genérico
                return new ItemGenerico(nome, descricao);
        }
    }

    /**
     * Limpa todo o inventário de um herói
     */
    public static void limparInventario(String nomeHeroi) {
        String sql = "DELETE FROM inventario_itens WHERE heroi_nome = ?";
        
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, nomeHeroi);
            int itensRemovidos = preparedStatement.executeUpdate();
            System.out.println("Inventário de " + nomeHeroi + " limpo. " + itensRemovidos + " itens removidos.");
            
        } catch (SQLException e) {
            System.err.println("Erro ao limpar inventário: " + e.getMessage());
        }
    }

    // ===== MÉTODOS AUXILIARES PARA DEMONSTRAÇÃO =====

    /**
     * Método de exemplo para demonstrar o uso do sistema de inventário
     */
    public static void exemploUsoInventario(String nomeHeroi) {
        System.out.println("\n=== EXEMPLO DE USO DO INVENTÁRIO ===");
        
        // Adicionar alguns itens de exemplo
        adicionarItemInventario(nomeHeroi, "Poção de Vida Pequena", "poção", 3, "vida", 25, "Restaura 25 pontos de vida");
        adicionarItemInventario(nomeHeroi, "Espada de Ferro", "espada", 1, "ataque", 15, "Uma espada resistente de ferro");
        adicionarItemInventario(nomeHeroi, "Pergaminho Antigo", "genérico", 1, null, 0, "Um pergaminho com escritas misteriosas");
        
        // Listar inventário
        listarInventario(nomeHeroi);
        
        // Verificar quantidade específica
        int pocoes = verificarQuantidadeItem(nomeHeroi, "Poção de Vida Pequena");
        System.out.println("Você possui " + pocoes + " poções de vida pequenas.");
        
        // Usar uma poção
        if (pocoes > 0) {
            removerItemInventario(nomeHeroi, "Poção de Vida Pequena", 1);
            System.out.println("Você usou uma poção de vida!");
        }
        
        // Listar inventário novamente
        System.out.println("\nInventário após usar uma poção:");
        listarInventario(nomeHeroi);
        
        System.out.println("=== FIM DO EXEMPLO ===\n");
    }
}