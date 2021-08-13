package com.example.pfe.dto;

import java.util.ArrayList;

public class ScanBlock {

    private Boolean isTake;
    private ArrayList<String> lines;

    public ScanBlock() {
        lines = new ArrayList<>();
        isTake = false;
    }

    public Boolean getTake() {
        return isTake;
    }

    public void setTake(Boolean take) {
        isTake = take;
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }

    public void appendLine(String line) {
        lines.add(line);
    }


}
