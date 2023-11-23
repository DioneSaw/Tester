package com.example.tester.models;

import com.example.tester.classes.DBHandler;
import com.example.tester.classes.Directions;
import com.example.tester.classes.Tests;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestsDAO {
    DBHandler connection = new DBHandler();
    public List<Tests> findAll() throws SQLException {

        List<Tests> tests = new ArrayList<>();

        try (PreparedStatement stmt = connection.connect().prepareStatement("SELECT * FROM tests")) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                int directionId = rs.getInt("direction_id");
                int duration = rs.getInt("duration");
                int pass_score_3 = rs.getInt("pass_score_3");
                int pass_score_4 = rs.getInt("pass_score_4");
                int pass_score_5 = rs.getInt("pass_score_5");
                int questions_count = rs.getInt("questions_count");

                Tests test = new Tests(id, name, directionId, duration, pass_score_3, pass_score_4, pass_score_5, questions_count);
                tests.add(test);
            }
        }

        return tests;
    }

    public void save(Tests test) throws SQLException {

        try (PreparedStatement stmt = connection.connect().prepareStatement(
                "INSERT INTO tests (name, direction_id, duration, pass_score_3, pass_score_4, pass_score_5, questions_count) VALUES (?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, test.getName());
            stmt.setInt(2, test.getDirection_id());
            stmt.setInt(3, test.getDuration());
            stmt.setInt(4, test.getPass_score_3());
            stmt.setInt(5, test.getPass_score_4());
            stmt.setInt(6, test.getPass_score_5());
            stmt.setInt(7, test.getQuestion_count());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    test.setId(rs.getInt(1));
                }
            }
        }
    }

    public void update(Tests test) throws SQLException {

        try (PreparedStatement stmt = connection.connect().prepareStatement(
                "UPDATE tests SET name = ?, direction_id = ?, duration = ?, pass_score_3 = ?, pass_score_4 = ?, pass_score_5 = ?, questions_count = ? WHERE id = ?")) {

            stmt.setString(1, test.getName());
            stmt.setInt(2, test.getDirection_id());
            stmt.setInt(3, test.getDuration());
            stmt.setInt(4, test.getPass_score_3());
            stmt.setInt(5, test.getPass_score_4());
            stmt.setInt(6, test.getPass_score_5());
            stmt.setInt(7, test.getQuestion_count());
            stmt.setInt(8, test.getId());

            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {

        try (PreparedStatement stmt = connection.connect().prepareStatement(
                "DELETE FROM tests WHERE id = ?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Tests> findByDirectionId(int directionId) throws SQLException {

        List<Tests> tests = new ArrayList<>();

        String sql = "SELECT t.* FROM tests t " +
                "INNER JOIN directions d ON t.direction_id = d.id " +
                "WHERE d.id = ?";

        try (PreparedStatement stmt = connection.connect().prepareStatement(sql)) {
            stmt.setInt(1, directionId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                int duration = rs.getInt("duration");
                int pass_score_3 = rs.getInt("pass_score_3");
                int pass_score_4 = rs.getInt("pass_score_4");
                int pass_score_5 = rs.getInt("pass_score_5");
                int questions_count = rs.getInt("questions_count");

                Tests test = new Tests(id, name, directionId, duration, pass_score_3, pass_score_4, pass_score_5, questions_count);
                tests.add(test);
            }
        }

        return tests;
    }

}
