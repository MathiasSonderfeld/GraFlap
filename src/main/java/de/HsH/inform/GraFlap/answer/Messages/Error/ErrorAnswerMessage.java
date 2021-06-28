package de.HsH.inform.GraFlap.answer.Messages.Error;

import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Result;
import de.HsH.inform.GraFlap.entity.TaskType;
import de.HsH.inform.GraFlap.entity.UserLanguage;
import de.HsH.inform.GraFlap.exception.GraFlapException;

public class ErrorAnswerMessage extends AnswerMessage {

    public ErrorAnswerMessage(String exceptionMessage, String taskTitle){
        super(new Result(new Submission(), 100, TaskType.ERROR), new Arguments(),null);
        this.taskTitle = taskTitle;
        this.feedbackText.setLength(0);
        this.feedbackText.append(exceptionMessage);
    }
    @Override
    protected boolean submissionMatchesTarget( TaskType type, TaskType studType){
        return false;
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        return "";
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        return "";
    }
}
