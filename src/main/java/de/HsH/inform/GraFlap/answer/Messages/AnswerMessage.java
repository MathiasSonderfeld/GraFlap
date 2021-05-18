package de.HsH.inform.GraFlap.answer.Messages;

import org.jdom2.Element;

/**
 * abstract class that serves a parent to generate the answer message, which is sent back to LON-CAPA
 * @author Benjamin Held (07-29-2016)
 * @author Lukas Seltmann (06-12-2019)
 * @author Mathias Sonderfeld
 * @since 08-04-2016
 * @version 0.5
 */
public abstract class AnswerMessage {
    private boolean isGerman;
    protected String taskTitle;
    protected final Element svgImage;
    protected final int percentOfTestWordsFailed;
    protected boolean hasPassed;
    protected String taskMode;
    protected StringBuilder feedbackText;
    protected String language;
    protected String svgTitle;

    /**
     * Constructor
     * @param percentOfTestWordsFailed value how many words failed the testing ranging form [0,100]
     * @param taskTitle the taskTitle of the assignment
     * @param bestLanguage a string coding the used language of the assignment
     * @param taskMode a string holding the coded mode information
     * @param solutionType a string coding the solutionType of the solution
     * @param submissionType a string coding the solutionType of the submission
     * @param svg a XML-element that gains the information for the output svg
     */
    public AnswerMessage(int percentOfTestWordsFailed, String taskTitle, String bestLanguage, String taskMode, String solutionType, String submissionType, Element svg) {
        this.taskTitle = taskTitle;
        this.percentOfTestWordsFailed = percentOfTestWordsFailed;
        this.language = bestLanguage;
        this.feedbackText = new StringBuilder();
        this.svgImage = svg;
        this.taskMode = taskMode;

        this.isGerman = this.language.equalsIgnoreCase("de");
        if(isGerman){
            checkAndReplaceGermanCharactersInTaskTitle();
        }

        this.hasPassed = percentOfTestWordsFailed == 0;
        this.hasPassed &= submissionMatchesTarget(solutionType, submissionType);

        if (this.isGerman) {
            this.svgTitle = getGermanSvgTitle();
            if(!this.hasPassed){
                this.feedbackText.append(percentOfTestWordsFailed).append(" ").append(getGermanFeedbackText());
            }
        }
        else {
            this.svgTitle = getEnglishSvgTitle();
            if(!this.hasPassed){
                this.feedbackText.append(percentOfTestWordsFailed).append(" ").append(getEnglishFeedbackText());
            }
        }
    }

    /**
     * private method to replace german special characters
     */
    private void checkAndReplaceGermanCharactersInTaskTitle() {
        taskTitle = taskTitle.replaceAll("ä","ae");
        taskTitle = taskTitle.replaceAll("ö","oe");
        taskTitle = taskTitle.replaceAll("ü","ue");
        taskTitle = taskTitle.replaceAll("Ä","Ae");
        taskTitle = taskTitle.replaceAll("Ö","Oe");
        taskTitle = taskTitle.replaceAll("Ü","Ue");
        taskTitle = taskTitle.replaceAll("ß","ss");
    }

    protected boolean submissionMatchesTarget(String type, String studType){
        return true;
    }

    protected abstract String getGermanSvgTitle();
    protected abstract String getEnglishSvgTitle();
    protected abstract String getGermanFeedbackText();
    protected abstract String getEnglishFeedbackText();

    public String getTaskTitle() {
        return taskTitle;
    }

    public Element getSvgImage() {
        return svgImage;
    }

    public int getPercentOfTestWordsFailed() {
        return percentOfTestWordsFailed;
    }

    public String getTaskMode() {
        return taskMode;
    }

    public String getFeedbackText() {
        return feedbackText.toString();
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