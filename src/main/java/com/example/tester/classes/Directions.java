package com.example.tester.classes;

public class Directions {
    private int id;
    private String name;

    public Directions(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Directions(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
