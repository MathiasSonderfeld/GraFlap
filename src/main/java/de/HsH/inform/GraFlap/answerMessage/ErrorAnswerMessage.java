package de.HsH.inform.GraFlap.answerMessage;

import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.entity.*;

public class ErrorAnswerMessage extends AnswerMessage {

    public ErrorAnswerMessage(String exceptionMessage, String taskTitle){
        super(new Result(new Submission(), 100, TaskType.ERROR), new Arguments(),null);
        this.taskTitle = taskTitle;
        this.svgTitle = "ERROR";
        this.aditionalFeedback.setLength(0);
        this.aditionalFeedback.append(exceptionMessage);
    }
}
