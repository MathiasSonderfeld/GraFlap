package de.HsH.inform.GraFlap.answer.Messages.automaton;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Result;
import de.HsH.inform.GraFlap.entity.TaskType;
import de.HsH.inform.GraFlap.entity.UserLanguage;
import org.jdom2.Element;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
            if (lang == UserLanguage.German) {
                feedbackText.append("Ihr Automat ist nicht deterministisch. \n");
            } else {
                feedbackText.append("Your automaton is not determinisitic. \n");
            }
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
            if (lang == UserLanguage.German) {
                feedbackText.append("Dies ist kein Turing-").append(svgTitle.toLowerCase()).append(". \n");
            } else {
                feedbackText.append("This is not a Turing ").append(svgTitle.toLowerCase()).append(". \n");
            }
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
            if (lang == UserLanguage.German) {
                feedbackText.append("Ihr Automat ist eigentlich deterministisch. \n");
            } else {
                feedbackText.append("Actually, your automaton is deterministic. \n");
            }
        }
    }
}
