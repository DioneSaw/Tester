package com.example.tester.classes;

import java.sql.Blob;
import java.sql.Date;

public class Results {
    private int id;
    private String student_name;
    private String student_group;
    private Date date;
    private int duration;
    private int grade;
    private double score;
    private int test_id;
    private int direction_id;
    private Blob answers;

    public Results(int id, String student_name, String student_group, Date date, int duration, int grade, double score, int test_id, int direction_id, Blob answers) {
        this.id = id;
        this.student_name = student_name;
        this.student_group = student_group;
        this.date = date;
        this.duration = duration;
        this.grade = grade;
        this.score = score;
        this.test_id = test_id;
        this.direction_id = direction_id;
        this.answers = answers;
    }

    public Results(String student_name, String student_group, Date date, int duration, int grade, double score, int test_id, int direction_id,  Blob answers) {
        this.student_name = student_name;
        this.student_group = student_group;
        this.date = date;
        this.duration = duration;
        this.grade = grade;
        this.score = score;
        this.test_id = test_id;
        this.direction_id = direction_id;
        this.answers = answers;
    }

    public Results() {
    }

    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }
    public String getStudent_name() {
        return student_name;
    }
    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getGrade() {
        return grade;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }
    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }
    public int getTest_id() { return test_id; }
    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }
    public Blob getJson() { return answers; }
    public void setJson(Blob answers) { this.answers = answers; }
    public int getDirection_id() { return direction_id; }
    public void setDirection_id(int direction_id) { this.direction_id = direction_id; }
    public String getStudent_group() { return student_group; }
    public void setStudent_group(String student_group) { this.student_group = student_group; }
}