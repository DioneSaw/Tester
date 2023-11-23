package com.example.tester.models;

import com.example.tester.classes.Answers;
import com.example.tester.classes.DBHandler;
import com.example.tester.classes.Directions;
import com.example.tester.classes.Questions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswersDAO {
    DBHandler connection = new DBHandler();
    public List<Answers> findAll() throws SQLException {
        // подключение к БД

        try (PreparedStatement stmt = connection.connect().prepareStatement("SELECT * FROM answers")) {

            ResultSet rs = stmt.executeQuery();

            List<Answers> answers = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                int question_id = rs.getInt("question_id");
                String text = rs.getString("text");
                boolean is_true = rs.getBoolean("is_true");

                answers.add(new Answers(id, question_id, text, is_true));
            }

            return answers;
        }
    }

    public void save(Answers answer) throws SQLException {

        try (PreparedStatement stmt = connection.connect().prepareStatement(
                "INSERT INTO answers (question_id, text, is_true) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, answer.getQuestion_id());
            stmt.setString(2, answer.getText());
            stmt.setBoolean(3, answer.isIs_true());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    answer.setId(rs.getInt(1));
                }
            }
        }
    }

    public void update(Answers answer) throws SQLException {

        try (PreparedStatement stmt = connection.connect().prepareStatement(
                "UPDATE answers SET question_id = ?, text = ?, is_true = ? WHERE id = ?")) {

            stmt.setInt(1, answer.getQuestion_id());
            stmt.setString(2, answer.getText());
            stmt.setBoolean(3, answer.isIs_true());
            stmt.setInt(4, answer.getId());

            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {

        try (PreparedStatement stmt = connection.connect().prepareStatement(
                "DELETE FROM answers WHERE id = ?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    public List<Answers> findByQuestionId(int questionid) throws SQLException {

        List<Answers> answers = new ArrayList<>();

        String sql = "SELECT a.* FROM answers a " +
                "INNER JOIN questions q ON a.question_id = q.id " +
                "WHERE q.id = ?";

        try (PreparedStatement stmt = connection.connect().prepareStatement(sql)) {
            stmt.setInt(1, questionid);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int question_id = rs.getInt("question_id");
                String text = rs.getString("text");
                boolean is_true = rs.getBoolean("is_true");

                Answers answer = new Answers(id, question_id, text, is_true);
                answers.add(answer);
            }
        }

        return answers;
    }

    public List<Answers> findCorrectByQuestionId(int questionId) throws SQLException {
        List<Answers> answers = new ArrayList<>();

        String sql = "SELECT * FROM answers WHERE question_id = ? AND is_true = true";

        try(PreparedStatement stmt = connection.connect().prepareStatement(sql)) {
            stmt.setInt(1, questionId);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Answers answer = createAnswer(rs);
                answers.add(answer);
            }
        }
        return answers;
    }
    private Answers createAnswer(ResultSet rs) throws SQLException {

        Answers answer = new Answers();

        answer.setId(rs.getInt("id"));
        answer.setQuestion_id(rs.getInt("question_id"));
        answer.setText(rs.getString("text"));
        answer.setIs_true(rs.getBoolean("is_true"));

        return answer;
    }
}
