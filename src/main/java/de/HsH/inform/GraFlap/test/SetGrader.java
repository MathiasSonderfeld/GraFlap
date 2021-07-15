package de.HsH.inform.GraFlap.test;

import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.SetResult;

import java.util.TreeSet;

/**
 * grades a set against a reference and saves the result in the given SetResult
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value GraFlap#version}
 */
public class SetGrader {
    public static <Generic> void grade( TreeSet<Generic> xmlSet, TreeSet<Generic> studentSet, SetResult<Generic> result){
        boolean equal = true;
        for(Generic xmlE : xmlSet){
            if(!studentSet.contains(xmlE)){
                equal = false;
                result.addToMissing(xmlE);
            }
        }
        for(Generic studentE : studentSet){
            if(!xmlSet.contains(studentE)){
                equal = false;
                result.addToSurplus(studentE);
            }
        }
        result.setScore(equal?1.0:0.0);
    }
}
