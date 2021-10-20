package de.HsH.inform.GraFlap.io.parsing;


import de.HsH.inform.GraFlap.entity.DoubleHashmap;
import de.HsH.inform.GraFlap.entity.TaskMode;
import de.HsH.inform.GraFlap.entity.TaskType;

public class FilenameTaskModeConverter {
    public static final String grammar = "grammar";
    public static final String automaton = "(jff|jflap)";
    public static final String exampleWords = "examplewords";
    public static final String internal = "internal";
    public static final String derivation = "derivation";
    public static final String cyk = "cyk";
    public static final String regex = "regex";

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
                switch (t){
                    case MP:
                    case MMW:
                    case WW: mapping.add(t, automaton); break;
                    case CYK: mapping.add(t, cyk); break;
                    case DER: mapping.add(t, derivation); break;
                    case RR:
                    case RRW:
                        mapping.add(t, regex); break;
                    case SVGA:
                    case SVGG:
                    case ERROR:
                    default: mapping.add(t, internal); break;
                }
            }
        }

    }

    public DoubleHashmap<TaskMode, String> getMapping() {
        return mapping;
    }
}
