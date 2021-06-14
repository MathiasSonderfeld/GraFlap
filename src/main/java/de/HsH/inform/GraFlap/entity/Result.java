package de.HsH.inform.GraFlap.entity;

import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.SetResult;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.State;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.Transition;
import de.HsH.inform.GraFlap.exception.GraFlapException;

public class Result {
    private TaskType submissionType;
    private int percentageFailed;
    private Submission submission;
    private SetResult<State> states;
    private SetResult<State> initials;
    private SetResult<State> finals;
    private SetResult<String> alphabet;
    private SetResult<String> stackalphabet;
    private SetResult<Transition> transitions;

    public Result( Submission submission, int percentageFailed, TaskType submissionType ){
        this.submission = submission;
        this.percentageFailed = percentageFailed;
        this.submissionType = submissionType;
    }

    public TaskType getSubmissionType() {
        return submissionType;
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

    public SetResult<State> getStates() {
        return states;
    }

    public void setStates( SetResult<State> states ) {
        this.states = states;
    }

    public SetResult<State> getInitials() {
        return initials;
    }

    public void setInitials( SetResult<State> initials ) {
        this.initials = initials;
    }

    public SetResult<State> getFinals() {
        return finals;
    }

    public void setFinals( SetResult<State> finals ) {
        this.finals = finals;
    }

    public SetResult<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet( SetResult<String> alphabet ) {
        this.alphabet = alphabet;
    }

    public SetResult<String> getStackalphabet() {
        return stackalphabet;
    }

    public void setStackalphabet( SetResult<String> stackalphabet ) {
        this.stackalphabet = stackalphabet;
    }

    public SetResult<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions( SetResult<Transition> transitions ) {
        this.transitions = transitions;
    }
}
