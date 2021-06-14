package de.HsH.inform.GraFlap.answer.Messages.Error;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.TaskType;
import de.HsH.inform.GraFlap.entity.UserLanguage;
import de.HsH.inform.GraFlap.exception.GraFlapException;

public class ErrorAnswerMessage extends AnswerMessage {
    public ErrorAnswerMessage(String exceptionMessage, String taskTitle){
        super(null, null,null);
    }
    @Override
    protected boolean submissionMatchesTarget( TaskType type, TaskType studType){
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
