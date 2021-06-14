package de.HsH.inform.GraFlap.entity;

/**
 * Data-Structure Class for program arguments.
 * Input-Processing moved to ArgumentsParser.
 * @author Benjamin Held (04-17-2016)
 * @author Mathias Sonderfeld
 * @since 07-03-2016
 * @version 0.1.3
 */
public class Arguments {
    private String taskTitle;
    private String userLanguage;
    private String solution;
    private String argtype;
    private String wordString;
    private int numberOfWords;
    private String studentAnswer;
    private TaskMode taskMode;

    private String states;
    private String initials;
    private String finals;
    private String alphabet;
    private String stackalphabet;
    private String transitions;


    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle( String taskTitle ) {
        this.taskTitle = taskTitle;
    }

    public String getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage( String userLanguage ) {
        this.userLanguage = userLanguage;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution( String solution ) {
        this.solution = solution;
    }

    public String getArgtype() {
        return argtype;
    }

    public void setArgtype( String argtype ) {
        this.argtype = argtype;
    }

    public String getWordString() {
        return wordString;
    }

    public void setWordString( String wordString ) {
        this.wordString = wordString;
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
