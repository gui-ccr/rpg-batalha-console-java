package com.guiccr.rpg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

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

    public static void salvarHeroi(Heroi heroi) {
        garantirColunaVidaAtual(); // Garantir que a coluna existe antes de salvar
        String sql = "INSERT INTO herois (nome, vida_atual, vida_maxima, ataque, defesa, chance_critico, multiplicador_critico, chance_esquiva, energia, forca, agilidade, vigor, presenca, intelecto, nivel, experiencia_atual, experiencia_para_proximo_nivel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            System.out.println("Heroi " + heroi.getNome() + " salvo com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao salvar o herói no banco de dados: " + e.getMessage());
        }
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
                        resultado.getInt("experiencia_para_proximo_nivel")
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
}