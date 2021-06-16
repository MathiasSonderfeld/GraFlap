package de.HsH.inform.GraFlap.test;

import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.SetResult;

import java.util.TreeSet;

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
