package com.coursemanagement;

public class Course {
    private int id;
    private String name;
    private String description;
    private int duration;
    private double fee;

    public Course(int id, String name, String description, int duration, double fee) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.fee = fee;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public double getFee() { return fee; }
    public void setFee(double fee) { this.fee = fee; }
}
