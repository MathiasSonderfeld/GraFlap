package de.HsH.inform.GraFlap.answer.Messages.algorithm;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Result;
import de.HsH.inform.GraFlap.entity.UserLanguage;
import org.jdom2.Element;

/**
 * child class of {@link AnswerMessage} to generate the answer message for a derivation exercise
 * @author Benjamin Held (07-30-2016)
 * @since 08-22-2016
 * @version 0.1.1
 */
public class DerivationAnswerMessage extends AnswerMessage {

    public DerivationAnswerMessage( Result result, Arguments arguments, Element svg){
        super(result, arguments, svg);
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Wortableitung";

            case English:
            default:
                return "Word derivation";
        }
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Prozent der Ableitungsschritte haben den Test nicht bestanden.";

            case English:
            default:
                return "percent of the derivation steps did not pass the test.";
        }
    }
}