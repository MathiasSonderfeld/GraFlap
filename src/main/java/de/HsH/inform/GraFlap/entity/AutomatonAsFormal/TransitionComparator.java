package de.HsH.inform.GraFlap.entity.AutomatonAsFormal;

import java.util.Comparator;
/**
 * Comparator Class to compare two Transitions for equal attributes
 * Can be inconsistent with Transition.equals as the State comparison is done with the StateNameComparator.
 *
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class TransitionComparator implements Comparator<Transition>{
    private static TransitionComparator instance = null;

    private TransitionComparator(){}

    public static TransitionComparator getInstance(){
        if(instance == null) instance = new TransitionComparator();
        return instance;
    }

    @Override
    public int compare(Transition o1, Transition o2){
        int ret;
        StateNameComparator sc = StateNameComparator.getInstance();
        ret = sc.compare(o1.getFrom(), o2.getFrom());
        if(ret==0){
            ret = sc.compare(o1.getTo(), o2.getTo());
        }
        if(ret==0){
            ret = o1.getRead().compareTo(o2.getRead());
        }
        if(ret==0){
            ret = o1.getPop().compareTo(o2.getPop());
        }
        if(ret==0){
            ret = o1.getPush().compareTo(o2.getPush());
        }
        return ret;
    }
}