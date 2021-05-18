package de.HsH.inform.GraFlap.answer.Messages.Error;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.UserLanguage;
import de.HsH.inform.GraFlap.exception.GraFlapException;

public class ErrorAnswerMessage extends AnswerMessage {
    public ErrorAnswerMessage(String exceptionMessage, String taskTitle){
        super(-1, taskTitle, "","","", exceptionMessage,null);
    }
    @Override
    protected boolean submissionMatchesTarget(String type, String studType){
        return false;
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        return "-";
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        return "-";
    }
}
