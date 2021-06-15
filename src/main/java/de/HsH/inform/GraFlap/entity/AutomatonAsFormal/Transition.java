package de.HsH.inform.GraFlap.entity.AutomatonAsFormal;

public class Transition implements Comparable{
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
     * Sorts by From, then To, then read, then pop, then push
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
                ret = this.getRead().compareTo(other.getRead());
            }
            if(ret==0){
                ret = this.getPop().compareTo(other.getPop());
            }
            if(ret==0){
                ret = this.getPush().compareTo(other.getPush());
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
        StringBuilder transitionString = new StringBuilder();
        transitionString.append("(").append(from).append(", ").append(read).append(", ").append(to);
        if(pop != null && !"".equals(pop)){
            transitionString.append(", ").append(pop);
        }
        else{
            transitionString.append(",");
        }
        if(push != null && !"".equals(push)){
            transitionString.append(", ").append(push);
        }
        else{
            transitionString.append(",");
        }
        transitionString.append(")");
        return transitionString.toString();
    }
}
