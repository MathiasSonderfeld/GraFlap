package de.HsH.inform.GraFlap.entity;

import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.SetResult;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.State;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.Transition;
import de.HsH.inform.GraFlap.exception.GraFlapException;

/**
 * Datastructure to store the grading result
 * @auther Benjamin Held (07-12-2016)
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class Result {
    private TaskType submissionTaskType;
    private int percentageFailed;
    private Submission submission;
    private SetResult<State> states;
    private SetResult<State> initials;
    private SetResult<State> finals;
    private SetResult<String> alphabet;
    private SetResult<String> stackalphabet;
    private SetResult<Transition> transitions;
    private long time = 0;
    private String extraText = "";

    public Result( Submission submission, int percentageFailed, TaskType submissionTaskType ){
        this.submission = submission;
        this.percentageFailed = percentageFailed;
        this.submissionTaskType = submissionTaskType;
    }

    public TaskType getsubmissionTaskType() {
        return submissionTaskType;
    }

    public int getPercentageFailed() {
        return percentageFailed;
    }

    public Submission getSubmission() throws GraFlapException {
        if (submission.getOperationType() != SubmissionType.UNDEFINED) {
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

    public long getTime( ) {return time;}

    public void setTime (long time) { this.time = time; }

    public String getExtraText() {
        return extraText;
    }

    public void setExtraText(String extraText) {
        this.extraText = extraText;
    }
}
