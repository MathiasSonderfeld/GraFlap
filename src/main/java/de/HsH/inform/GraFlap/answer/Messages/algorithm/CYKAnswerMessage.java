package de.HsH.inform.GraFlap.answer.Messages.algorithm;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.AnswerMessages;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Result;
import de.HsH.inform.GraFlap.entity.UserLanguage;
import org.jdom2.Element;

/**
 * child class of {@link AnswerMessage} to generate the answer message for a cyk exercise
 * @author Benjamin Held (07-30-2016)
 * @since 08-05-2016
 * @version 0.1.0
 */
public class CYKAnswerMessage extends AnswerMessage {

    public CYKAnswerMessage( Result result, Arguments arguments, Element svg){
        super(result, arguments, svg);
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        return messages.getString(String.valueOf(AnswerMessages.CYK_Svgtitle));
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        return messages.getString(String.valueOf(AnswerMessages.CYK_Feedback));
    }
}