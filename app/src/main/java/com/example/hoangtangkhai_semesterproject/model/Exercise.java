package com.example.hoangtangkhai_semesterproject.model;

import java.io.Serializable;

public class Exercise implements Serializable {
    private int id;
    private String nameEx;
    private int numReps;
    private int numSets;
    private int typeEx;

    public Exercise() {
    }

    public Exercise(int id, String nameEx, int numSets, int numReps, int typeEx) {
        this.id = id;
        this.nameEx = nameEx;
        this.numReps = numReps;
        this.numSets = numSets;
        this.typeEx = typeEx;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameEx() {
        return nameEx;
    }

    public void setNameEx(String nameEx) {
        this.nameEx = nameEx;
    }

    public int getNumReps() {
        return numReps;
    }

    public void setNumReps(int numReps) {
        this.numReps = numReps;
    }

    public int getNumSets() {
        return numSets;
    }

    public void setNumSets(int numSets) {
        this.numSets = numSets;
    }

    public int getTypeEx() {
        return typeEx;
    }

    public void setTypeEx(int typeEx) {
        this.typeEx = typeEx;
    }
}
