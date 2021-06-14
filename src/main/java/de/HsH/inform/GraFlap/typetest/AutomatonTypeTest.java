package de.HsH.inform.GraFlap.typetest;

import de.HsH.inform.GraFlap.entity.TaskType;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JflapWrapper.automaton.DeterminismChecker;
import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import javafx.concurrent.Task;

import java.util.concurrent.ExecutionException;

/**
 * static helper class to determine the type of an automaton
 * @author Frauke Sprengel
 * @author Benjamin Held (04-10-2016)
 * @since 05-09-2016
 * @version 0.2.8
 */
public class AutomatonTypeTest {

    /**
     * static method to determine the type of an automaton that is given by a automaton
     * @param automatonSubmission the automaton that should be tested
     * @return the type of automaton as a string: (n|d)(fa|pda|tm)
     * @throws GraFlapException throws a {@link GraFlapException} if there is a problem with the submission
     */
    public static TaskType checkForAutomatonType( Submission<Automaton> automatonSubmission) throws GraFlapException {
        boolean isDeterministic = DeterminismChecker.isDeterministic(automatonSubmission.getSubmissionObject());
        switch(automatonSubmission.getSubmissionObject().testType()){
            case "fa":
                if(isDeterministic) return TaskType.DFA;
                else return TaskType.NFA;
            case "pda":
                if(isDeterministic) return TaskType.DPDA;
                else return TaskType.NPDA;
            case "tm":
                if(isDeterministic) return TaskType.DTM;
                else return TaskType.NTM;
            default:
                return TaskType.ERROR;
        }
    }
}