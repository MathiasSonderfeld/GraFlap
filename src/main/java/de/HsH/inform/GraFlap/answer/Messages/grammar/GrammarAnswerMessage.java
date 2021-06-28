package de.HsH.inform.GraFlap.answer.Messages.grammar;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.*;
import org.jdom2.Element;

import java.util.Locale;

/**
 * child class to generate the details of the message for grammars
 * @author Benjamin Held (07-30-2016)
 * @since 08-08-2016
 * @version 0.1.0
 */
public class GrammarAnswerMessage extends AnswerMessage {

    public GrammarAnswerMessage( Result result, Arguments arguments, Element svg){
        super(result, arguments, svg);
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        return messages.getString(String.valueOf(AnswerMessages.GRAMMAR_Svgtitle));
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        return messages.getString(String.valueOf(AnswerMessages.GRAMMAR_Feedback));
    }

    @Override
    protected boolean submissionMatchesTarget( TaskType solutionType, TaskType submissionTaskType) {
        if (solutionType != submissionTaskType) {
            if (!(solutionType == TaskType.RLCFG && (submissionTaskType == TaskType.RL || submissionTaskType == TaskType.CFG))) {
                feedbackText.append(messages.getString(String.valueOf(AnswerMessages.GRAMMAR_Type)) + "\n");
                return false;
            }
        }
        return true;
    }
}