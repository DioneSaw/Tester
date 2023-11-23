package com.example.tester.classes;

public class Tests {
    private int id;
    private String name;
    private int direction_id;
    private int duration;
    private int pass_score_3;
    private int pass_score_4;
    private int pass_score_5;
    private int question_count;

    public Tests(int id, String name, int direction_id, int duration, int pass_score_3, int pass_score_4, int pass_score_5, int question_count) {
        this.id = id;
        this.name = name;
        this.direction_id = direction_id;
        this.duration = duration;
        this.pass_score_3 = pass_score_3;
        this.pass_score_4 = pass_score_4;
        this.pass_score_5 = pass_score_5;
        this.question_count = question_count;
    }

    public Tests(String name, int direction_id, int duration, int pass_score_3, int pass_score_4, int pass_score_5, int question_count) {
        this.name = name;
        this.direction_id = direction_id;
        this.duration = duration;
        this.pass_score_3 = pass_score_3;
        this.pass_score_4 = pass_score_4;
        this.pass_score_5 = pass_score_5;
        this.question_count = question_count;
    }

    public Tests() {
    }

    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getDirection_id() {
        return direction_id;
    }
    public void setDirection_id(int direction_id) {
        this.direction_id = direction_id;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getPass_score_3() {
        return pass_score_3;
    }
    public void setPass_score_3(int pass_score_3) {
        this.pass_score_3 = pass_score_3;
    }
    public int getPass_score_4() {
        return pass_score_4;
    }
    public void setPass_score_4(int pass_score_4) {
        this.pass_score_4 = pass_score_4;
    }
    public int getPass_score_5() {
        return pass_score_5;
    }
    public void setPass_score_5(int pass_score_5) {
        this.pass_score_5 = pass_score_5;
    }
    public int getQuestion_count() {
        return question_count;
    }
    public void setQuestion_count(int question_count) {
        this.question_count = question_count;
    }
}
