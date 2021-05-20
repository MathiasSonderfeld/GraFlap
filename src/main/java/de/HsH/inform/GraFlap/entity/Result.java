package de.HsH.inform.GraFlap.entity;

import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.exception.GraFlapException;

public class Result {
    private String studType;
    private int percentageFailed;
    private Submission submission;

    public Result(){
        this.submission = new Submission();
        this.studType = "non";
        this.percentageFailed = 100;
    }

    public Result(Submission submission, int percentageFailed, String studType){
        this.submission = submission;
        this.percentageFailed = percentageFailed;
        this.studType = studType;
    }

    public String getStudType() {
        return studType;
    }

    public int getPercentageFailed() {
        return percentageFailed;
    }

    public Submission getSubmission() throws GraFlapException {
        if (submission.getOperationType() != OperationType.UNDEFINED) {
            return submission;
        }
        throw new GraFlapException("Error in Grader: Tried to access non parsed submission.");
    }

    public void setStudType( String studType ) {
        this.studType = studType;
    }

    public void setPercentageFailed( int percentageFailed ) {
        this.percentageFailed = percentageFailed;
    }

    public void setSubmission( Submission submission ) {
        this.submission = submission;
    }
}
