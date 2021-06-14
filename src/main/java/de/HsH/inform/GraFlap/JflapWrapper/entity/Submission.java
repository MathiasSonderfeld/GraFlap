package de.HsH.inform.GraFlap.JflapWrapper.entity;

import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.entity.SubmissionType;

/**
 * wrapper class which holds all related information of a submission or solution string during the grading process
 * @author Benjamin Held (04-25-2016)
 * @since 04-27-2016
 * @version 0.1.1
 */
public class Submission<T> {
    private final String submissionString;
    private final T submissionObject;
    private SubmissionType SubmissionType;

    public Submission() {
        this.SubmissionType = SubmissionType.UNDEFINED;
        this.submissionString = "";
        this.submissionObject = null;
    }

    public Submission(String submissionString, T object, SubmissionType type) {
        this.submissionString = submissionString;
        this.submissionObject = object;
        this.SubmissionType = type;
    }

    public String getSubmissionString() throws GraFlapException {
        if (!this.submissionString.isEmpty()) {
            return submissionString;
        }
        throw new GraFlapException("Error in Submisson: SubmissionString is empty. Please check.");
    }

    public T getSubmissionObject() throws GraFlapException {
        if (this.submissionObject != null) {
            return submissionObject;
        }
        throw new GraFlapException("Error in Submisson: Conversion from submission to Object failed. Please check.");
    }

    public SubmissionType getOperationType() {
        return SubmissionType;
    }
}
