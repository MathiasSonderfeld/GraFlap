package de.HsH.inform.GraFlap.entity.AutomatonAsFormal;

/**
 * Entity Class to store a state of a formally written automaton
 *
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class State{
    private String stateName;
    private boolean initial;
    private boolean finale;

    public State(String stateName){
        this.stateName = stateName;
        this.initial = false;
        this.finale = false;
    }

    public State(String stateName, boolean initial, boolean finale){
        this.stateName = stateName;
        this.initial = initial;
        this.finale = finale;
    }

    public void setInitial( boolean initial ) {
        this.initial = initial;
    }

    public void setFinale( boolean finale ) {
        this.finale = finale;
    }

    public String getStateName() {
        return stateName;
    }

    public boolean isInitial() {
        return initial;
    }

    public boolean isFinale() {
        return finale;
    }

    /**
     * @param o the Object to check equality to
     * @return returns true, if all attributes are equal, else false
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof State){
            State other = (State) o;
            return this.stateName.equals(other.getStateName()) && this.initial == other.isInitial() && this.finale == other.isFinale();
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.stateName.hashCode() * (this.initial?7:1) * (this.initial?13:1);
    }


    /**
     * prints name followed by bracket if tags are set:
     * generic state s3 is written as: s3
     * Initial State s0 is written as: s0 (I)
     * final state s1 is written as: s1 (F)
     * intial and final state s2 is written as s2 (I|F)
     * @return String
     */
    @Override
    public String toString(){
        return this.stateName;
    }
}
