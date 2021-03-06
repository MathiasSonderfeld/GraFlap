package de.HsH.inform.GraFlap.entity;

import java.util.Locale;
import java.util.Objects;

/**
 * Data-Structure Class for program arguments.
 * @author Benjamin Held (04-17-2016)
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class Arguments {
    private String testId = "";
    private String taskTitle = "";
    private Locale userLanguage = Locale.ROOT;
    private String reference = "";
    private Testwords testwords;
    private int numberOfWords = -1;
    private String studentAnswer = "";
    private Mode mode = Mode.ERROR;
    private Type type = Type.ERROR;

    private String states = "";
    private String initials = "";
    private String finals = "";
    private String alphabet = "";
    private String stackalphabet = "";
    private String transitions = "";

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle( String taskTitle ) {
        this.taskTitle = taskTitle;
    }

    public Locale getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage( Locale userLanguage ) {
        this.userLanguage = userLanguage;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setTestwords(Testwords testwords){
        this.testwords = testwords;
    }

    public Testwords getTestwords() {
        return testwords;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords( int numberOfWords ) {
        this.numberOfWords = numberOfWords;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer( String studentAnswer ) {
        this.studentAnswer = studentAnswer;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getStates() {
        return states;
    }

    public void setStates( String states ) {
        this.states = states;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials( String initials ) {
        this.initials = initials;
    }

    public String getFinals() {
        return finals;
    }

    public void setFinals( String finals ) {
        this.finals = finals;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet( String alphabet ) {
        this.alphabet = alphabet;
    }

    public String getStackalphabet() {
        return stackalphabet;
    }

    public void setStackalphabet( String stackalphabet ) {
        this.stackalphabet = stackalphabet;
    }

    public String getTransitions() {
        return transitions;
    }

    public void setTransitions( String transitions ) {
        this.transitions = transitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Arguments)) return false;
        Arguments arguments = (Arguments) o;
        return getNumberOfWords() == arguments.getNumberOfWords() && Objects.equals(getTestId(), arguments.getTestId()) && Objects.equals(getTaskTitle(), arguments.getTaskTitle()) && Objects.equals(getUserLanguage(), arguments.getUserLanguage()) && Objects.equals(getReference(), arguments.getReference()) && Objects.equals(getTestwords(), arguments.getTestwords()) && Objects.equals(getStudentAnswer(), arguments.getStudentAnswer()) && getMode() == arguments.getMode() && getType() == arguments.getType() && Objects.equals(getStates(), arguments.getStates()) && Objects.equals(getInitials(), arguments.getInitials()) && Objects.equals(getFinals(), arguments.getFinals()) && Objects.equals(getAlphabet(), arguments.getAlphabet()) && Objects.equals(getStackalphabet(), arguments.getStackalphabet()) && Objects.equals(getTransitions(), arguments.getTransitions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTestId(), getTaskTitle(), getUserLanguage(), getReference(), getTestwords(), getNumberOfWords(), getStudentAnswer(), getMode(), getType(), getStates(), getInitials(), getFinals(), getAlphabet(), getStackalphabet(), getTransitions());
    }
}
