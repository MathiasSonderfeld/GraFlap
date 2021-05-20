package de.HsH.inform.GraFlap.entity;

import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.exception.GraFlapException;

public class Result {
    private String studType;
    private int percentFailed;
    private Submission submission;

    public Result(){
        this.submission = new Submission();
        this.studType = "non";
        this.percentFailed = 100;
    }

    public String getStudType() {
        return studType;
    }

    public int getPercentFailed() {
        return percentFailed;
    }

    public Submission getSubmission() throws GraFlapException {
        if (submission.getOperationType() != OperationType.UNDEFINED) {
            return submission;
        }
        throw new GraFlapException("Error in Grader: Tried to access non parsed submission.");
    }
}
