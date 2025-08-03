package com.guiccr.rpg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TesteConexao {
    private static final String URL_JDBC = "jdbc:sqlite:rpg.db";

    public static void main(String[] args) {
        // Bloco try-with-resources para garantir que a conexão, o comando e o resultado
        // sejam fechados automaticamente.
        try (Connection conexao = DriverManager.getConnection(URL_JDBC);
        Statement comando = conexao.createStatement()) {

            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");

            //? --- 1. INSERT (Create) ---
            System.out.println("\n--- Inserindo um novo herói na tabela 'herois' ---");
            String sqlInsert = "INSERT INTO herois (nome, vida, ataque) VALUES ('Kaiser The Angel Of The Night', 120, 25);";
            int linhasAfetadas = comando.executeUpdate(sqlInsert);
            System.out.println("Linhas afetadas pela inserção: " + linhasAfetadas);

            //? --- 2. SELECT (Read) ---
            System.out.println("\n--- Selecionando todos os heróis ---");
            String sqlSelect = "SELECT id, nome, vida, ataque FROM herois;";
            try (ResultSet resultado = comando.executeQuery(sqlSelect)) {
                while (resultado.next()) {
                    int id = resultado.getInt("id");
                    String nome = resultado.getString("nome");
                    int vida = resultado.getInt("vida");
                    int ataque = resultado.getInt("ataque");
                    System.out.printf("Herói encontrado -> ID: %d, Nome: %s, Vida: %d, Ataque: %d%n", id, nome, vida,
                            ataque);
                }
            }

            //? --- 3. UPDATE (Update) ---
            System.out.println("\n--- Atualizando o ataque do herói com ID 1 ---");
            String sqlUpdate = "UPDATE herois SET ataque = 30 WHERE id = 1;";
            linhasAfetadas = comando.executeUpdate(sqlUpdate);
            System.out.println("Linhas afetadas pela atualização: " + linhasAfetadas);

            //? --- 4. DELETE (Delete) ---
            System.out.println("\n--- Deletando o herói com ID 1 ---");
            String sqlDelete = "DELETE FROM herois WHERE id = 1;";
            linhasAfetadas = comando.executeUpdate(sqlDelete);
            System.out.println("Linhas afetadas pela exclusão: " + linhasAfetadas);

            //? --- 5. SELECT (Read) para confirmar a exclusão ---
            System.out.println("\n--- Selecionando todos os heróis (após a exclusão) ---");
            try (ResultSet resultado = comando.executeQuery(sqlSelect)) {
                if (!resultado.next()) {
                    System.out.println("Nenhum herói encontrado na tabela.");
                } else {
                    resultado.beforeFirst(); // Volta para o início do ResultSet
                    while (resultado.next()) {
                        System.out.printf("Herói encontrado -> ID: %d, Nome: %s, Vida: %d, Ataque: %d%n",
                                resultado.getInt("id"),
                                resultado.getString("nome"),
                                resultado.getInt("vida"),
                                resultado.getInt("ataque"));
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Ocorreu um erro no banco de dados: " + e.getMessage());
        }
    }
}