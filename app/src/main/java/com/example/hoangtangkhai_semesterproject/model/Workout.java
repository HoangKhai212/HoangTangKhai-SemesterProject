package com.example.hoangtangkhai_semesterproject.model;

import java.io.Serializable;
import java.util.List;

public class Workout implements Serializable {
    private int id;
    private String date;
    private String exercise;

    public Workout() {
    }

    public Workout(int id, String date, String exercise) {
        this.id = id;
        this.date = date;
        this.exercise = exercise;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
}
