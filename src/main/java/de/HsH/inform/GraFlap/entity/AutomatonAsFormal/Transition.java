package de.HsH.inform.GraFlap.entity.AutomatonAsFormal;

public class Transition implements Comparable{
    private State from;
    private State to;
    private String alphabet;

    public Transition( State from, State to, String alphabet ) {
        this.from = from;
        this.to = to;
        this.alphabet = alphabet;
    }

    public State getFrom() {
        return from;
    }

    public State getTo() {
        return to;
    }

    public String getAlphabet() {
        return alphabet;
    }

    /**
     * @param o the Object to check equality to
     * @return returns true, if all attributes are equal, else false
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Transition){
            Transition other = (Transition) o;
            return this.getFrom().equals(other.getFrom()) && this.getTo().equals(other.getTo()) && this.getAlphabet().equals(other.getAlphabet());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.getFrom().hashCode() * this.getTo().hashCode() * this.getAlphabet().hashCode();
    }

    /**
     * Sorts by From, then To, then alphabet
     * @param o the Object to compare to
     * @return 0 if not sortable, -1, 0 or 1 else
     */
    @Override
    public int compareTo(Object o){
        int ret = 0;
        if(o instanceof Transition){
            Transition other = (Transition) o;
            ret = this.getFrom().compareTo(other.getFrom());
            if(ret==0){
                ret = this.getTo().compareTo(other.getTo());
            }
            if(ret==0){
                ret = this.getAlphabet().compareTo(other.getAlphabet());
            }
        }
        return ret;
    }

    /**
     * Writes as: Transitions from X to Y with A
     * X & Y are State.toString() calls
     * @return String
     */
    @Override
    public String toString(){
        return "Transition from " + from + " to " + to + " with " + alphabet;
    }
}