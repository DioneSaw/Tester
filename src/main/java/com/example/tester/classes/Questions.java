package com.example.tester.classes;

import java.sql.Blob;

public class Questions {
    private int id;
    private int test_id;
    private int type;
    private String text;
    private byte[] image;

    public Questions(int id, int test_id, int type, String text, byte[] image) {
        this.id = id;
        this.test_id = test_id;
        this.type = type;
        this.text = text;
        this.image = image;
    }

    public Questions(int id, int test_id, int type, String text) {
        this.id = id;
        this.test_id = test_id;
        this.type = type;
        this.text = text;
    }

    public Questions(int test_id, int type, String text, byte[] image) {
        this.test_id = test_id;
        this.type = type;
        this.text = text;
        this.image = image;
    }

    public Questions(int test_id, int type, String text) {
        this.test_id = test_id;
        this.type = type;
        this.text = text;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTest_id() {
        return test_id;
    }
    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
}
