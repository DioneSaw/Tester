package com.example.tester.models;

import com.example.tester.classes.Directions;
import com.example.tester.classes.DBHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DirectionsDAO {
    DBHandler connection = new DBHandler();
    public List<Directions> findAll() throws SQLException {
        // подключение к БД

        try (PreparedStatement stmt = connection.connect().prepareStatement("SELECT * FROM directions")) {

            ResultSet rs = stmt.executeQuery();

            List<Directions> directions = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                directions.add(new Directions(id, name));
            }

            return directions;
        }
    }

    public void save(Directions direction) throws SQLException {

        try (PreparedStatement stmt = connection.connect().prepareStatement(
                "INSERT INTO directions (name) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, direction.getName());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    direction.setId(rs.getInt(1));
                }
            }
        }
    }

    public void update(Directions direction) throws SQLException {

        try (PreparedStatement stmt = connection.connect().prepareStatement(
                "UPDATE directions SET name = ? WHERE id = ?")) {

            stmt.setString(1, direction.getName());
            stmt.setInt(2, direction.getId());

            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {

        try (PreparedStatement stmt = connection.connect().prepareStatement(
                "DELETE FROM directions WHERE id = ?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
