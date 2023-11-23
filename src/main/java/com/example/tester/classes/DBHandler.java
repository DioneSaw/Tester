package com.example.tester.classes;

import java.sql.*;

public class DBHandler {
    public Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:tester.db";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // методы для запросов к БД
}