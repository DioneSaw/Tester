package com.example.tester.classes;

import java.sql.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExecuteSQLScript {

    static DBHandler conn = new DBHandler();
    public void execute(String script) {

        try {
            Statement stmt = conn.connect().createStatement();

            String sql = readSqlScript(script);

            executeScript(stmt, sql);

            conn.connect().close();

        } catch (SQLException e) {
            System.out.println("Ошибка выполнения скрипта: " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readSqlScript(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner sc = new Scanner(file, "UTF-8");
        StringBuilder sql = new StringBuilder();
        while (sc.hasNextLine()) {
            sql.append(sc.nextLine());
            sql.append("");
        }
        return sql.toString();
    }

    private static void executeScript(Statement stmt, String sql) throws SQLException {

        String[] queries = sql.split(";");

        for (String query : queries) {

            try {
                stmt.execute(query);
            } catch (SQLException e) {
                System.out.println("Ошибка в запросе: " + query);
                System.out.println("Сообщение: " + e.getMessage());
            }

        }

    }

}