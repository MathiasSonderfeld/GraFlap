package de.HsH.inform.GraFlap.entity.AutomatonAsFormal;

/**
 * Entity Class to store a transition of an formally written automaton
 *
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class Transition{
    private State from;
    private State to;
    private String read;
    private String pop;
    private String push;

    public Transition( State from, State to, String read, String pop, String push ) {
        this.from = from;
        this.to = to;
        this.read = read;
        this.pop = pop;
        this.push = push;
    }

    public State getFrom() {
        return from;
    }

    public State getTo() {
        return to;
    }

    public String getRead() {
        return read;
    }

    public String getPop() {
        return pop;
    }

    public String getPush() {
        return push;
    }

    /**
     * @param o the Object to check equality to
     * @return returns true, if all attributes are equal, else false
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Transition){
            Transition other = (Transition) o;
            return this.getFrom().equals(other.getFrom()) && this.getTo().equals(other.getTo()) && this.getRead().equals(other.getRead()) && this.push.equals(other.getPush()) && this.pop.equals(other.getPop());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.getFrom().hashCode() * this.getTo().hashCode() * this.getRead().hashCode();
    }

    /**
     * Writes as: Transitions from X to Y with A
     * X & Y are State.toString() calls
     * @return String
     */
    @Override
    public String toString(){
        StringBuilder transitionString = new StringBuilder();
        transitionString.append("(").append(from).append(", ").append(read);
        if(pop != null && !"".equals(pop)){
            transitionString.append(", ").append(pop);
        }
        transitionString.append(", ").append(to);
        if(push != null && !"".equals(push)){
            transitionString.append(", ").append(push);
        }
        transitionString.append(")");
        return transitionString.toString();
    }
}
