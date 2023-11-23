package com.example.tester.models;

import com.example.tester.classes.DBHandler;
import com.example.tester.classes.Directions;
import com.example.tester.classes.Results;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ResultsDAO {
    DBHandler connection = new DBHandler();
    public List<Results> findAll() throws SQLException {

        try (PreparedStatement stmt = connection.connect().prepareStatement("SELECT * FROM results")) {

            ResultSet rs = stmt.executeQuery();

            List<Results> results = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String student_name = rs.getString("student_name");
                String student_group = rs.getString("student_group");
                Date date = rs.getDate("date");
                int duration = rs.getInt("duration");
                int grade = rs.getInt("grade");
                double score = rs.getDouble("score");
                int test_id = rs.getInt("test_id");
                int direction_id = rs.getInt("direction_id");
                Blob answers;
                try{answers = rs.getBlob("answers");}
                catch (SQLFeatureNotSupportedException e){answers = null;}

                results.add(new Results(id,student_group, student_name, date, duration, grade, score, test_id, direction_id, answers));
            }

            return results;
        }
    }

    public void save(Results result) throws SQLException {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateString = f.format(result.getDate());

        try (PreparedStatement stmt = connection.connect().prepareStatement(
                "INSERT INTO results (student_name, student_group, date, duration, grade, score, test_id, direction_id,  answers) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, result.getStudent_name());
            stmt.setString(2, result.getStudent_group());
            stmt.setString(3, dateString);
            stmt.setInt(4, result.getDuration());
            stmt.setInt(5, result.getGrade());
            stmt.setDouble(6, result.getScore());
            stmt.setInt(7, result.getTest_id());
            stmt.setInt(8, result.getDirection_id());
            try{stmt.setBlob(9, result.getJson());}
            catch (SQLException e){stmt.setNull(9, Types.BLOB);}

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    result.setId(rs.getInt(1));
                }
            }
        }
    }

    public void update(Results result) throws SQLException {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateString = f.format(result.getDate());

        try (PreparedStatement stmt = connection.connect().prepareStatement(
                "UPDATE results SET student_name = ?, student_group = ?, date = ?, duration = ?, grade = ?, score = ?, test_id = ?, direction_id = ?, answers =?  WHERE id = ?")) {

            stmt.setString(1, result.getStudent_name());
            stmt.setString(2, result.getStudent_group());
            stmt.setString(3, dateString);
            stmt.setInt(4, result.getDuration());
            stmt.setInt(5, result.getGrade());
            stmt.setDouble(6, result.getScore());
            stmt.setInt(7, result.getTest_id());
            stmt.setInt(8, result.getDirection_id());
            try{stmt.setBlob(9, result.getJson());}
            catch (SQLException e){stmt.setNull(9, Types.BLOB);}
            stmt.setInt(10, result.getId());

            stmt.executeUpdate();
        }
    }

    public Results findLastRecord() throws SQLException {

        Results result = null;

        String sql = "SELECT * FROM results ORDER BY id DESC LIMIT 1";

        try (PreparedStatement stmt = connection.connect().prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result = new Results();
                result.setId(rs.getInt("id"));
            }
        }
        return result;
    }

    public void updateGrades(int resultId, double score, int grade) throws SQLException {

        String sql = "UPDATE results SET score = ?, grade = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.connect().prepareStatement(sql)) {

            stmt.setDouble(1, score);
            stmt.setInt(2, grade);
            stmt.setInt(3, resultId);

            stmt.executeUpdate();

        }
    }

    public List<Results> findByTestId(int testId) throws SQLException {

        List<Results> results = new ArrayList<>();

        String sql = "SELECT * FROM results WHERE test_id = ?";

        try (PreparedStatement stmt = connection.connect().prepareStatement(sql)) {

            stmt.setInt(1, testId);

            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()) {

                Results result = new Results();

                result.setStudent_name(resultSet.getString("student_name"));
                result.setStudent_group(resultSet.getString("student_group"));
                result.setScore(resultSet.getDouble("score"));
                result.setGrade(resultSet.getInt("grade"));
//                result.setDate(resultSet.getDate("date"));

                results.add(result);
            }

        }
        return results;
    }

    public List<Results> findByDirectionId(int directionId) throws SQLException {

        List<Results> results = new ArrayList<>();

        String sql = "SELECT * FROM results WHERE direction_id = ?";

        try (PreparedStatement stmt = connection.connect().prepareStatement(sql)) {

            stmt.setInt(1, directionId);

            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()) {

                Results result = new Results();

                result.setStudent_name(resultSet.getString("student_name"));
                result.setStudent_group(resultSet.getString("student_group"));
                result.setScore(resultSet.getDouble("score"));
                result.setGrade(resultSet.getInt("grade"));
                result.setDate(resultSet.getDate("date"));

                results.add(result);
            }

        }
        return results;
    }

    public void delete(int id) throws SQLException {

        try (PreparedStatement stmt = connection.connect().prepareStatement(
                "DELETE FROM results WHERE id = ?")) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
