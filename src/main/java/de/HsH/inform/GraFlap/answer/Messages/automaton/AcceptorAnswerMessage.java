package de.HsH.inform.GraFlap.answer.Messages.automaton;

import de.HsH.inform.GraFlap.entity.*;
import org.jdom2.Element;

import java.util.Locale;

/**
 * child class to generate the details of the message for accepting automatons
 * @author Benjamin Held (07-29-2016)
 * @since 08-06-2016
 * @version 0.1.0
 */
public class AcceptorAnswerMessage extends AutomatonAnswerMessage {

    public AcceptorAnswerMessage( Result result, Arguments arguments, Element svg){
        super(result, arguments, svg);
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        return messages.getString(String.valueOf(AnswerMessages.ACCEPTOR_Svgtitle));
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        if(taskMode == TaskMode.AA){
            return messages.getString(String.valueOf(AnswerMessages.ACCEPTOR_AAFeedback));
        }
        else{
            return messages.getString(String.valueOf(AnswerMessages.ACCEPTOR_Feedback));
        }
    }

    @Override
    protected boolean submissionMatchesTarget( TaskType taskType, TaskType submissionTaskType) {
        boolean passed = true;
        if (taskMode.isTyped()) {
            matchesNonDeterministic(taskType, submissionTaskType);
            passed = matchesDeterministic(taskType, submissionTaskType);
            if (isFiniteAutomaton(taskType) && !isFiniteAutomaton(submissionTaskType)) {
                passed = false;
                feedbackText.append(messages.getString(String.valueOf(AnswerMessages.ACCEPTOR_FAFeedback))+ "\n");
            }
            else if (isPushDownAutomaton(taskType) && !isPushDownAutomaton(submissionTaskType)) {
                passed = false;
                feedbackText.append(messages.getString(String.valueOf(AnswerMessages.ACCEPTOR_PDAFeedback))+ "\n");
            } else if (!matchesTuringMachine(taskType, submissionTaskType)) {
                passed = false;
            }
        }
        return passed;
    }
}
