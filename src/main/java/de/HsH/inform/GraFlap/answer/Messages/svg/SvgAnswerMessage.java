package de.HsH.inform.GraFlap.answer.Messages.svg;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.AnswerMessages;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Result;
import de.HsH.inform.GraFlap.entity.UserLanguage;
import org.jdom2.Element;

/**
 * child class to generate the details of the message for the svg mode
 * @author Benjamin Held (07-30-2016)
 * @since 08-09-2016
 * @version 0.1.0
 */
public class SvgAnswerMessage extends AnswerMessage {

    public SvgAnswerMessage( Result result, Arguments arguments, Element svg){
        super(result, arguments, svg);;
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        return messages.getString(String.valueOf(AnswerMessages.SVG_Svgtitle));
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        return messages.getString(String.valueOf(AnswerMessages.SVG_Feedback));
    }
}