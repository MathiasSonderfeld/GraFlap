package de.HsH.inform.GraFlap.entity;

import java.util.Locale;

/**
 * Data-Structure Class for program arguments.
 * @author Benjamin Held (04-17-2016)
 * @author Mathias Sonderfeld
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class Arguments {
    private String taskTitle = "";
    private Locale userLanguage = Locale.ROOT;
    private String solution = "";
    private Testwords testwords;
    private String wordString = "-";
    private int numberOfWords = -1;
    private String studentAnswer = "";
    private TaskMode taskMode = TaskMode.ERROR;
    private TaskType taskType = TaskType.ERROR;

    private String states = "";
    private String initials = "";
    private String finals = "";
    private String alphabet = "";
    private String stackalphabet = "";
    private String transitions = "";

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType( TaskType taskType ) {
        this.taskType = taskType;
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

    public String getSolution() {
        return solution;
    }

    public void setSolution( String solution ) {
        this.solution = solution;
    }

    public void setTestwords(Testwords testwords){
        this.testwords = testwords;
    }

    public Testwords getTestwords() {
        return testwords;
    }

    public void setWordString( String wordString ) {
        this.wordString = wordString;
    }

    public String getWordString() {
        return wordString;
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

    public TaskMode getTaskMode() {
        return taskMode;
    }

    public void setTaskMode( TaskMode taskMode ) {
        this.taskMode = taskMode;
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
}
