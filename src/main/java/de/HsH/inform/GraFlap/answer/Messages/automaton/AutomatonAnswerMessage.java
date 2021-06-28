package de.HsH.inform.GraFlap.answer.Messages.automaton;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.*;
import org.jdom2.Element;

import java.util.Locale;

/**
 * abstract class that serves as a parent to generate the answer message for automatons
 * @author Benjamin Held (07-29-2016)
 * @since 08-06-2016
 * @version 0.1.0
 */
public abstract class AutomatonAnswerMessage extends AnswerMessage {

    public AutomatonAnswerMessage( Result result, Arguments arguments, Element svg){
        super(result, arguments, svg);
    }

    /**
     * method to check if a given automaton is not deterministic
     * @param taskType the predefined type
     * @param submissionTaskType the type of the submission
     * @return true, if the submission is deterministic; false, if not
     */
    boolean matchesDeterministic(TaskType taskType, TaskType submissionTaskType) {
        if (taskType.isDeterministic() && submissionTaskType.isNonDeterministic()) {
            feedbackText.append(messages.getString(String.valueOf(AnswerMessages.AUTOMATON_MatchesDeterministic)) + "\n");
            return false;
        }
        return true;
    }

    /**
     * method to check if a given automaton is a turing machine
     * @param taskType the predefined type
     * @param submissionTaskType the type of the submission
     * @return true, if the submission is a turing machine; false, if not
     */
    boolean matchesTuringMachine(TaskType taskType, TaskType submissionTaskType) {
        if (isTuring(taskType) && !isTuring(submissionTaskType)){
            feedbackText.append(messages.getString(String.valueOf(AnswerMessages.AUTOMATON_IsTuring)) + "\n");
            return false;
        }
        return true;
    }



    protected boolean isFiniteAutomaton(TaskType taskType){
        switch(taskType){
            case FA:
            case DFA:
            case NFA:
                return true;
            default:
                return false;
        }
    }

    protected boolean isPushDownAutomaton(TaskType taskType){
        switch(taskType){
            case PDA:
            case DPDA:
            case NPDA:
                return true;
            default:
                return false;
        }
    }

    protected boolean isTuring(TaskType taskType){
        switch(taskType){
            case TM:
            case DTM:
            case NTM:
                return true;
            default:
                return false;
        }
    }



    /**
     * method to check if a given automaton is deterministic
     * @param taskType the predefined type
     * @param submissionTaskType the type of the submission
     */
    void matchesNonDeterministic( TaskType taskType, TaskType submissionTaskType) {
        if (taskType.isNonDeterministic() && submissionTaskType.isDeterministic()) {
            feedbackText.append(messages.getString(String.valueOf(AnswerMessages.AUTOMATON_MatchesNotDeterministic)) + "\n");
        }
    }
}
