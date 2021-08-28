package de.HsH.inform.GraFlap.io.parsing;


import de.HsH.inform.GraFlap.entity.DoubleHashmap;
import de.HsH.inform.GraFlap.entity.TaskMode;
import de.HsH.inform.GraFlap.entity.TaskType;

public class FilenameTaskModeConverter {
    public static final String grammar = "grammar";
    public static final String automaton = "(jff|jflap)";
    public static final String exampleWords = "examplewords";

    private DoubleHashmap<TaskMode, String> mapping = null;

    public FilenameTaskModeConverter(){
        mapping = new DoubleHashmap<>();
        for(TaskMode t : TaskMode.values()){
            if(t.isGrammar()){
                mapping.add(t, grammar);
            }
            else if(t.isAutomaton()){
                mapping.add(t, automaton);
            }
            else{
                mapping.add(t, exampleWords);
            }
        }

    }

}
