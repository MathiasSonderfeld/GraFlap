package de.HsH.inform.GraFlap.entity.AutomatonAsFormal;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Datastructure for rating results from Automaton Automaton comparison
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class SetResult<Generic> {
    private double score;
    private ArrayList<Generic> doubles;
    private ArrayList<Generic> missing;
    private ArrayList<Generic> surplus;
    private ArrayList<CommentMarker> comments;

    public SetResult(){
        score = -1.0;
        doubles = new ArrayList<>();
        missing = new ArrayList<>();
        surplus = new ArrayList<>();
        comments = new ArrayList<>();
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

    public ArrayList<CommentMarker> getComments() {
        return comments;
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

    public void addToComments( CommentMarker comment) { this.comments.add(comment); }

    public int getTotalErrors(){
        return this.missing.size() + this.surplus.size();
    }

    public String toString(){
        return "" + this.score + " - Doubles: " + this.doubles + " - Missing: " + this.missing + " - Surplus: " + this.surplus;
    }

    @Override
    public boolean equals( Object o ) {
        if(this == o) { return true; }
        if(!( o instanceof SetResult )) { return false; }
        SetResult<?> setResult = (SetResult<?>) o;
        return Double.compare(setResult.getScore(), getScore()) == 0 && Objects.equals(getDoubles(), setResult.getDoubles()) && Objects
                .equals(getMissing(), setResult.getMissing()) && Objects.equals(getSurplus(), setResult.getSurplus()) && Objects.equals(getComments(), setResult.getComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScore(), getDoubles(), getMissing(), getSurplus(), getComments());
    }



}
