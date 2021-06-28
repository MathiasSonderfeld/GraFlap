package de.HsH.inform.GraFlap.answer.Messages.automaton;

import de.HsH.inform.GraFlap.entity.*;
import org.jdom2.Element;

import java.util.Locale;

/**
 * child class to generate the details of the message for machines
 * @author Benjamin Held (07-29-2016)
 * @since 08-06-2016
 * @version 0.1.0
 */
public class TransducerAnswerMessage extends AutomatonAnswerMessage {

    public TransducerAnswerMessage( Result result, Arguments arguments, Element svg){
        super(result, arguments, svg);
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        return messages.getString(String.valueOf(AnswerMessages.TRANSDUCER_Svgtitle));
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        return messages.getString(String.valueOf(AnswerMessages.TRANSDUCER_Feedback));
    }


    @Override
    protected boolean submissionMatchesTarget( TaskType solutionType, TaskType submissionTaskType) {
        matchesNonDeterministic(solutionType, submissionTaskType);
        boolean passed = matchesDeterministic(solutionType, submissionTaskType);
        passed &= matchesTuringMachine(solutionType, submissionTaskType);
        if (solutionType == TaskType.MEALY && submissionTaskType != TaskType.MEALY) {
            passed = false;
            feedbackText.append(messages.getString(String.valueOf(AnswerMessages.TRANSDUCER_Mealy)) + "\n");
        }
        else if (solutionType == TaskType.MOORE && submissionTaskType != TaskType.MOORE) {
            passed = false;
            feedbackText.append(messages.getString(String.valueOf(AnswerMessages.TRANSDUCER_Moore)) + "\n");
        }
        return passed;
    }
}
