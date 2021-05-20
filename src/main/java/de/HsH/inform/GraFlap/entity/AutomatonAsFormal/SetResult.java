package de.HsH.inform.GraFlap.entity.AutomatonAsFormal;

import java.util.ArrayList;

public class SetResult {
    private double score;
    private ArrayList<String> missing;
    private ArrayList<String> surplus;

    public SetResult(){
        score = -1.0;
        missing = new ArrayList<>();
        surplus = new ArrayList<>();
    }

    public SetResult(double score, ArrayList<String> missing, ArrayList<String> surplus){
        this.score=score;
        this.missing=missing;
        this.surplus=surplus;
    }

    public double getScore() {
        return score;
    }

    public void setScore( double score ) {
        this.score = score;
    }

    public ArrayList<String> getMissing() {
        return missing;
    }

    public void setMissing( ArrayList<String> missing ) {
        this.missing = missing;
    }

    public ArrayList<String> getSurplus() {
        return surplus;
    }

    public void setSurplus( ArrayList<String> surplus ) {
        this.surplus = surplus;
    }

    public void addToMissing(String missing){
        this.missing.add(missing);
    }

    public void addToSurplus(String surplus){
        this.surplus.add(surplus);
    }

    public int getTotalErrors(){
        return this.missing.size() + this.surplus.size();
    }
}
