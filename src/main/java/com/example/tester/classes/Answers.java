package com.example.tester.classes;

public class Answers {
    private int id;
    private int question_id;
    private String text;
    private boolean is_true;

    public Answers() {
    }
    public Answers(int id, int question_id, String text, boolean is_true) {
        this.id = id;
        this.question_id = question_id;
        this.text = text;
        this.is_true = is_true;
    }

    public Answers(int question_id, String text, boolean is_true) {
        this.question_id = question_id;
        this.text = text;
        this.is_true = is_true;
    }
    public int getId() { return id; }
    public void setId(int id) {
        this.id = id;
    }
    public int getQuestion_id() {
        return question_id;
    }
    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public boolean isIs_true() {
        return is_true;
    }
    public void setIs_true(boolean is_true) {
        this.is_true = is_true;
    }
}
