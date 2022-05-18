package de.HsH.inform.GraFlap.entity.AutomatonAsFormal;

import java.util.Comparator;

/**
 * Comparator Class to compare two States for equal name
 * Inconsistent with State.equals
 *
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class StateNameComparator implements Comparator<State>{
    private static StateNameComparator instance = null;

    private StateNameComparator(){}

    public static StateNameComparator getInstance(){
        if(instance == null) instance = new StateNameComparator();
        return instance;
    }

    @Override
    public int compare(State o1, State o2){
        return o1.getStateName().compareTo(o2.getStateName());
    }
}