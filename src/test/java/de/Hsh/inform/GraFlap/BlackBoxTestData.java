package de.HsH.inform.GraFlap;

/**
 * @author Mathias Sonderfeld
 * better String-Array at the Moment to contain Testdata.
 * Aim is to store testdata here and create required input format for GraFlap dynamicly
 */
public class BlackBoxTestData {
    private String input;
    private String studentAnswer;

    public String getInput() {
        return input;
    }

    public void setInput( String input ) {
        this.input = input;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer( String studentAnswer ) {
        this.studentAnswer = studentAnswer;
    }
}
