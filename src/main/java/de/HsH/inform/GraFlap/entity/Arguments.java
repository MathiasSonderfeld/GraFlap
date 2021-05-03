package de.HsH.inform.GraFlap.entity;

/**
 * helper class for the main method to extract the required attributes from the program arguments
 * @author Benjamin Held (04-17-2016)
 * @since 07-03-2016
 * @version 0.1.3
 */
public class Arguments {
    /**
     * the name of the task
     */
    private String taskTitle;
    /**
     * shortcut the reading language the user of the submission uses in his browser, e.g. de, en, ...
     */
    private String userLanguage;
    /**
     * the solution to the task
     */
    private String solution;
    /**
     * mode information to determine the correct task type
     */
    private String mode;
    /**
     * the argument type;
     */
    private String argtype;
    /**
     * a string with coded test words
     */
    private String wordString;
    /**
     * the number of requested test words
     */
    private int numberOfWords;
    /**
     * the submission
     */
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
