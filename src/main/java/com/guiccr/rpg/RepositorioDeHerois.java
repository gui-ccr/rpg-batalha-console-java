package com.guiccr.rpg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement; // Usaremos PreparedStatement em vez de Statement
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional; // Para o metodo de busca

public final class RepositorioDeHerois {

    private static final String URL_JDBC = "jdbc:sqlite:db/rpg.db";

    // * construtor privado para evitar que a classe seja instanciada
    private RepositorioDeHerois() {
    }

    public static void testarConexao() {
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
                Statement comando = conexao.createStatement()) {
            System.out.println("Conexão com o banco de dados estabelecida com sucesso! ");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }

    }

    // * === METODOS CRUD ===

    // ? --- 1. INSERT (Create) ---
    public static void salvarHeroi(Heroi heroi) {
        // Removido 'id' da lista de colunas, e o primeiro '?' da lista de valores.
        String sql = "INSERT INTO herois (nome, vida_maxima, ataque, defesa, chance_critico, multiplicador_critico, chance_esquiva, energia, forca, agilidade, vigor, presenca, intelecto, nivel, experiencia_atual, experiencia_para_proximo_nivel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
                PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

            // Os índices agora começam em 1 para 'nome', 2 para 'vida_maxima', e assim por
            // diante.
            preparedStatement.setString(1, heroi.getNome()); // * nome
            preparedStatement.setInt(2, heroi.getVidaMaxima()); // * vida_maxima
            preparedStatement.setInt(3, heroi.getAtaque()); // * ataque
            preparedStatement.setInt(4, heroi.getDefesa()); // * defesa
            preparedStatement.setInt(5, heroi.getChanceCritico()); // * chance_critico
            preparedStatement.setDouble(6, heroi.getMultiplicadorCritico()); // * multiplicador_critico
            preparedStatement.setInt(7, heroi.getChanceEsquiva()); // * chance_esquiva
            preparedStatement.setInt(8, heroi.getEnergia()); // * energia
            preparedStatement.setInt(9, heroi.getForca()); // * forca
            preparedStatement.setInt(10, heroi.getAgilidade()); // * agilidade
            preparedStatement.setInt(11, heroi.getVigor()); // * vigor
            preparedStatement.setInt(12, heroi.getPresenca()); // * presenca
            preparedStatement.setInt(13, heroi.getIntelecto()); // * intelecto
            preparedStatement.setInt(14, heroi.getNivel()); // * nivel
            preparedStatement.setInt(15, heroi.getExperienciaAtual()); // * experiencia_atual
            preparedStatement.setInt(16, heroi.getExperienciaParaProximoNivel()); // * experiencia_para_proximo_nivel

            // Usar executeUpdate() para INSERT, UPDATE ou DELETE.
            preparedStatement.executeUpdate();
            System.out.println("Heroi " + heroi.getNome() + " salvo com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar o herói no banco de dados: " + e.getMessage());
        }
    }


    // ? --- 2. SELECT (Read) ---
    public static Optional<Heroi> buscarHeroi(String nome) { 
        String sql = "SELECT * FROM herois WHERE nome = ?;"; 
        
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            
            // Usar o parâmetro 'nome' do método para preencher o placeholder.
            preparedStatement.setString(1, nome); 
            
            try (ResultSet resultado = preparedStatement.executeQuery()) {
                if (resultado.next()) { // Verificar se há um resultado
                    // Lógica para ler TODOS os 16 campos do ResultSet
                    String nomeHeroi = resultado.getString("nome");
                    int vidaMaxima = resultado.getInt("vida_maxima");
                    int ataque = resultado.getInt("ataque");
                    int defesa = resultado.getInt("defesa");
                    int chanceCritico = resultado.getInt("chance_critico");
                    double multiplicadorCritico = resultado.getDouble("multiplicador_critico");
                    int chanceEsquiva = resultado.getInt("chance_esquiva");
                    int energia = resultado.getInt("energia");
                    int forca = resultado.getInt("forca");
                    int agilidade = resultado.getInt("agilidade");
                    int vigor = resultado.getInt("vigor");
                    int presenca = resultado.getInt("presenca");
                    int intelecto = resultado.getInt("intelecto");
                    int nivel = resultado.getInt("nivel");
                    int experienciaAtual = resultado.getInt("experiencia_atual");
                    int experienciaParaProximoNivel = resultado.getInt("experiencia_para_proximo_nivel");

                    // Cria um novo objeto Heroi com todos os dados lidos
                    Heroi heroiEncontrado = new Heroi(nomeHeroi, forca, agilidade, vigor, presenca, intelecto, energia);
                    // Como os atributos do Heroi (vida, ataque, defesa, crítico/esquiva)
                    // já são setados no construtor da superclasse Personagem, não precisamos
                    // setar eles individualmente.
                    // Os atributos de EXP e Nível precisarão ser setados com setters se o construtor do Heroi não os receber.
                    
                    // Isso pode ser ajustado para um construtor de Heroi que receba todos os parâmetros, ou usar setters
                    // Por enquanto, vamos usar um construtor simplificado na Heroi para testes.
                    // Para o seu projeto, o ideal é criar um construtor em Heroi que receba todos esses valores.
                    
                    return Optional.of(heroiEncontrado); // CORREÇÃO 5: Retorna o Optional com o objeto
                }
            }
            // Se o if não for executado (nenhum resultado), retorna Optional.empty()
            return Optional.empty();

        } catch (SQLException e) { // CORREÇÃO 6: Adicionado catch para SQLException
            System.err.println("Erro ao buscar herói no banco de dados: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty(); // Retorna vazio em caso de erro
        }
    }
    
    
    // ... código anterior da classe RepositorioDeHerois

    // ? --- 3. UPDATE (Update) ---
    public static void atualizarHeroi(Heroi heroi) {
        // SQL para atualizar um herói. O 'id' é usado na cláusula WHERE para identificar o herói.
        String sql = "UPDATE herois SET " +
                     "nome = ?, vida_maxima = ?, ataque = ?, defesa = ?, chance_critico = ?, " +
                     "multiplicador_critico = ?, chance_esquiva = ?, energia = ?, forca = ?, " +
                     "agilidade = ?, vigor = ?, presenca = ?, intelecto = ?, nivel = ?, " +
                     "experiencia_atual = ?, experiencia_para_proximo_nivel = ? " +
                     "WHERE nome = ?;"; // Usar o nome como chave para a atualização
    
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

            // Seta os valores para os placeholders na ordem em que aparecem no SQL
            preparedStatement.setString(1, heroi.getNome());
            preparedStatement.setInt(2, heroi.getVidaMaxima());
            preparedStatement.setInt(3, heroi.getAtaque());
            preparedStatement.setInt(4, heroi.getDefesa());
            preparedStatement.setInt(5, heroi.getChanceCritico());
            preparedStatement.setDouble(6, heroi.getMultiplicadorCritico());
            preparedStatement.setInt(7, heroi.getChanceEsquiva());
            preparedStatement.setInt(8, heroi.getEnergia());
            preparedStatement.setInt(9, heroi.getForca());
            preparedStatement.setInt(10, heroi.getAgilidade());
            preparedStatement.setInt(11, heroi.getVigor());
            preparedStatement.setInt(12, heroi.getPresenca());
            preparedStatement.setInt(13, heroi.getIntelecto());
            preparedStatement.setInt(14, heroi.getNivel());
            preparedStatement.setInt(15, heroi.getExperienciaAtual());
            preparedStatement.setInt(16, heroi.getExperienciaParaProximoNivel());
            
            // O último placeholder é para a cláusula WHERE
            preparedStatement.setString(17, heroi.getNome());
    
            int linhasAfetadas = preparedStatement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Herói " + heroi.getNome() + " atualizado com sucesso!");
            } else {
                System.out.println("Nenhum herói encontrado com o nome " + heroi.getNome() + " para ser atualizado.");
            }
    
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o herói no banco de dados: " + e.getMessage());
        }
    }


    // ? --- 4. DELETE (Delete) ---
    public static void deletarHeroi(String nome) {
        // SQL para deletar um herói pelo nome
        String sql = "DELETE FROM herois WHERE nome = ?;";
    
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
             PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {

            // Seta o valor para o placeholder do WHERE
            preparedStatement.setString(1, nome);
            
            int linhasAfetadas = preparedStatement.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Herói " + nome + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum herói encontrado com o nome " + nome + " para ser deletado.");
            }
    
        } catch (SQLException e) {
            System.err.println("Erro ao deletar o herói do banco de dados: " + e.getMessage());
        }
    }


    
}
