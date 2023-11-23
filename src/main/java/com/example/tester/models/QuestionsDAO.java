package com.example.tester.models;

import com.example.tester.classes.Answers;
import com.example.tester.classes.DBHandler;
import com.example.tester.classes.Questions;
import com.example.tester.classes.Tests;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionsDAO {
    DBHandler connection = new DBHandler();
    public List<Questions> findAll() throws SQLException {
        // подключение к БД

        try (PreparedStatement stmt = connection.connect().prepareStatement("SELECT * FROM questions")) {

            ResultSet rs = stmt.executeQuery();

            List<Questions> questions = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                int test_id = rs.getInt("test_id");
                int type = rs.getInt("type");
                String text = rs.getString("text");
                byte[] image = rs.getBytes("image");
//                Blob image;
//                try{image = rs.getBlob("image");}
//                catch (SQLFeatureNotSupportedException e){image = null;}

                questions.add(new Questions(id, test_id, type, text, image));
            }

            return questions;
        }
    }

    public void save(Questions question) throws SQLException {

        try (PreparedStatement stmt = connection.connect().prepareStatement(
                "INSERT INTO questions (test_id, type, text, image) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, question.getTest_id());
            stmt.setInt(2, question.getType());
            stmt.setString(3, question.getText());
            try{stmt.setBytes(4, question.getImage());}
            catch (SQLException e){stmt.setNull(4, Types.BINARY);}

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    question.setId(rs.getInt(1));
                }
            }
        }
    }

    public void update(Questions question) throws SQLException {

        try (PreparedStatement stmt = connection.connect().prepareStatement(
                "UPDATE questions SET test_id = ?, type = ?, text = ?, image = ? WHERE id = ?")) {

            stmt.setInt(1, question.getTest_id());
            stmt.setInt(2, question.getType());
            stmt.setString(3, question.getText());
            try{stmt.setBytes(4, question.getImage());}
            catch (SQLException e){stmt.setNull(4, Types.BLOB);}
            stmt.setInt(5, question.getId());

            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {

        try (PreparedStatement stmt = connection.connect().prepareStatement(
                "DELETE FROM questions WHERE id = ?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Questions> findByTestId(int testid) throws SQLException {

        List<Questions> questions = new ArrayList<>();

        String sql = "SELECT q.* FROM questions q " +
                "INNER JOIN tests t ON q.test_id = t.id " +
                "WHERE t.id = ?";

        try (PreparedStatement stmt = connection.connect().prepareStatement(sql)) {
            stmt.setInt(1, testid);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int test_id = rs.getInt("test_id");
                int type = rs.getInt("type");
                String text = rs.getString("text");
                byte[] image = rs.getBytes("image");
//                try{image = rs.getBlob("image");}
//                catch (SQLFeatureNotSupportedException e){image = null;}

                Questions question = new Questions(id, test_id, type, text, image);
                questions.add(question);
            }
        }

        return questions;
    }

    public List<Questions> findAllByDirectionId(int directionId) {

        List<Questions> questions = new ArrayList<>();

        String sql = "SELECT * FROM questions " +
                "WHERE test_id IN (" +
                "SELECT id FROM tests WHERE direction_id = ?)";

        try (PreparedStatement stmt = connection.connect().prepareStatement(sql)) {

            stmt.setInt(1, directionId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int test_id = rs.getInt("test_id");
                int type = rs.getInt("type");
                String text = rs.getString("text");
                byte[] image = rs.getBytes("image");
//                try{image = rs.getBlob("image");}
//                catch (SQLFeatureNotSupportedException e){image = null;}

                Questions question = new Questions(id, test_id, type, text, image);
                questions.add(question);
            }

        } catch (SQLException e) {
        }

        return questions;

    }

    public List<Questions> findQuestionsForFinal(int directionId)  {

        List<Questions> questions = new ArrayList<>();

        List<Questions> allQuestions = findAllByDirectionId(directionId);

        Random rand = new Random();

        for(int i = 0; i < 10; i++) {
            int randomIndex = rand.nextInt(allQuestions.size());
            questions.add(allQuestions.get(randomIndex));
            allQuestions.remove(randomIndex);
        }

        return questions;

    }
}
