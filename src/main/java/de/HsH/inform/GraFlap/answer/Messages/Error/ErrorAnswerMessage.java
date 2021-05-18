package de.HsH.inform.GraFlap.answer.Messages.Error;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.UserLanguage;
import de.HsH.inform.GraFlap.exception.GraFlapException;

public class ErrorAnswerMessage extends AnswerMessage {

    public ErrorAnswerMessage(GraFlapException e){
        super(-1, "Error", "","","", "",null);
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        switch(lang){
            case German:
                return "";

            case English:
            default:
                return "";
        }
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        switch(lang){
            case German:
                return "";

            case English:
            default:
                return "";
        }
    }
}
