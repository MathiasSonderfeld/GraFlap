package de.HsH.inform.GraFlap.entity.AutomatonAsFormal;

import java.util.ArrayList;

public class SetResult<Generic> {
    private double score;
    private ArrayList<Generic> doubles;
    private ArrayList<Generic> missing;
    private ArrayList<Generic> surplus;

    public SetResult(){
        score = -1.0;
        doubles = new ArrayList<>();
        missing = new ArrayList<>();
        surplus = new ArrayList<>();
    }

    public SetResult( double score, ArrayList<Generic> missing, ArrayList<Generic> surplus){
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

    public ArrayList<Generic> getDoubles() {
        return doubles;
    }

    public void setDoubles( ArrayList<Generic> doubles ) {
        this.doubles = doubles;
    }

    public ArrayList<Generic> getMissing() {
        return missing;
    }

    public void setMissing( ArrayList<Generic> missing ) {
        this.missing = missing;
    }

    public ArrayList<Generic> getSurplus() {
        return surplus;
    }

    public void setSurplus( ArrayList<Generic> surplus ) {
        this.surplus = surplus;
    }

    public void addToMissing( Generic missing){
        this.missing.add(missing);
    }

    public void addToSurplus( Generic surplus){
        this.surplus.add(surplus);
    }

    public void addToDoubles( Generic doubleElement){
        this.doubles.add(doubleElement);
    }

    public int getTotalErrors(){
        return this.missing.size() + this.surplus.size();
    }
}
