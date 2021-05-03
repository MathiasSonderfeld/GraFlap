package de.HsH.inform.GraFlap.answer.Messages;

import org.jdom2.Element;

/**
 * abstract class that serves a parent to generate the answer message, which is sent back to LON-CAPA
 * @author Benjamin Held (07-29-2016)
 * @author Lukas Seltmann (06-12-2019)
 * @author Mathias Sonderfeld (14-04-2021)
 * @since 08-04-2016
 * @version 0.1.5
 */
public abstract class AnswerMessage {
    private String taskTitle;
    private Element svgImage;
    private int resultScore;
    private boolean hasPassed;

    /**
     * a string holding the coded mode information
     */
    protected String taskMode;

    /**
     * a string builder that creates the feedback to the submission
     */
    protected StringBuilder resultText;

    /**
     * a string coding the used language of the assignment
     */
    protected String language;

    /**
     * a string for the title of the result svg
     */
    protected String svgTitle;

    /**
     * Constructor
     * @param resultValue value how many word failed the testing ranging form [0,100]
     * @param title the title of the assignment
     * @param bestLanguage a string coding the used language of the assignment
     * @param taskMode a string holding the coded mode information
     * @param type a string coding the type of the solution
     * @param studType a string coding the type of the submission
     * @param svg a XML-element that gains the information for the output svg
     */
    public AnswerMessage(int resultValue, String title, String bestLanguage, String taskMode, String type,
                         String studType, Element svg) {
        this.taskTitle = title;
        this.resultScore = resultValue;
        this.language = bestLanguage;
        this.resultText = new StringBuilder();
        this.svgImage = svg;
        this.taskMode = taskMode;

        checkAndReplaceGermanCharacter();

        determineSvgTitle();
        this.hasPassed = submissionMatchesTarget(type, studType);
        this.hasPassed &= finishAssessment(resultValue);
    }

    /**
     * private method to replace german special characters
     */
    private void checkAndReplaceGermanCharacter() {
        if(language.equalsIgnoreCase("de")){
            taskTitle = taskTitle.replaceAll("ä","ae");
            taskTitle = taskTitle.replaceAll("ö","oe");
            taskTitle = taskTitle.replaceAll("ü","ue");
            taskTitle = taskTitle.replaceAll("Ä","Ae");
            taskTitle = taskTitle.replaceAll("Ö","Oe");
            taskTitle = taskTitle.replaceAll("Ü","Ue");
            taskTitle = taskTitle.replaceAll("ß","ss");
        }
    }

    protected abstract void determineSvgTitle();
    protected abstract boolean submissionMatchesTarget(String type, String studType);
    protected abstract boolean finishAssessment(int resultValue);


    public String getTaskTitle() {
        return taskTitle;
    }

    public Element getSvgImage() {
        return svgImage;
    }

    public int getResultScore() {
        return resultScore;
    }

    public String getTaskMode() {
        return taskMode;
    }

    public String getResultText() {
        return resultText.toString();
    }

    public String getLanguage() {
        return language;
    }

    public String getSvgTitle() {
        return svgTitle;
    }

    public boolean hasPassed() {
        return hasPassed;
    }
}