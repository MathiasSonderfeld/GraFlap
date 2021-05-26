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

    public ArrayList<Generic> getMissing() {
        return missing;
    }

    public ArrayList<Generic> getSurplus() {
        return surplus;
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

    public String toString(){
        return "" + this.score + " - Doubles: " + this.doubles + " - Missing: " + this.missing + " - Surplus: " + this.surplus;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof SetResult){
            SetResult other = (SetResult) o;
            return this.score == other.getScore() && this.doubles.equals(other.getDoubles()) && this.missing.equals(other.getMissing()) && this.surplus.equals(other.getSurplus());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return (int)(this.missing.hashCode() + this.surplus.hashCode() + this.doubles.hashCode() * this.score);
    }
}
