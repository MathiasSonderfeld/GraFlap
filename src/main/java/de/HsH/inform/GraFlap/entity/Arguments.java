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
    private String mode;
    private String argtype;
    private String wordString;
    private int numberOfWords;
    private String studentAnswer;
    private OperationMode operationMode;

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

    public String getMode() {
        return mode;
    }

    public void setMode( String mode ) {
        this.mode = mode;
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

    public OperationMode getOperationMode() {
        return operationMode;
    }

    public void setOperationMode( OperationMode operationMode ) {
        this.operationMode = operationMode;
    }
}
