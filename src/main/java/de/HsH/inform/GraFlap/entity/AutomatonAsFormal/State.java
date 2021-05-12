package de.HsH.inform.GraFlap.entity.AutomatonAsFormal;

/**
 * @author Mathias Sonderfeld
 * Entity Class to store a state of an formally written automaton
 * implements comparable to be useable by TreeMap and TreeSet
 * Note: this class has a natural ordering that is inconsistent with equals!
 * Natural Ordering just goes by State name, equals verifies all attributes.
 * This is required to make sure no two states are in a Set with different tags but with same name as thats not allowed within one automaton,
 * while it is also required to mark a mismatch if the tags are not the same, e.g. if they come from different automatons.
 * Implement a Deep Comparator if you need consistency.
 */
public class State implements Comparable{
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
     * Sorts by name
     * @param o the Object to compare to
     * @return 0 if not sortable, -1, 0 or 1 else
     */
    @Override
    public int compareTo(Object o){
        if(o instanceof State){
            return this.stateName.compareTo(((State) o).getStateName());
        }
        return 0;
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
        return this.stateName + (initial|finale?" (":"") + (initial?"I":"") + (initial&finale?"|":"") + (finale?"F":"") + (initial|finale?")":"");
    }
}
