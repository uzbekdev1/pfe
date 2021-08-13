package com.example.pfe.dto;

import com.example.pfe.entity.DisplayEntity;

import java.util.ArrayList;

public class ScanResponse {

    private ArrayList<String> lines;
    private ArrayList<DisplayEntity> solutions;
    private String error;

    public ScanResponse() {
        lines = new ArrayList<>();
        solutions = new ArrayList<>();
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }

    public ArrayList<DisplayEntity> getSolutions() {
        return solutions;
    }

    public void setSolutions(ArrayList<DisplayEntity> solutions) {
        this.solutions = solutions;
    }

    public void appendLines(ArrayList<String> lines) {
        this.lines.addAll(lines);
    }

    public void addEntitiy(DisplayEntity entity) {
        this.solutions.add(entity);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
