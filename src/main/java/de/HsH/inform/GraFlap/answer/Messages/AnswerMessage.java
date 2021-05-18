package de.HsH.inform.GraFlap.answer.Messages;

import de.HsH.inform.GraFlap.entity.UserLanguage;
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
    protected String taskTitle;
    protected UserLanguage lang;
    protected final Element svgImage;
    protected final int percentOfTestWordsFailed;
    protected boolean hasPassed;
    protected String taskMode;
    protected StringBuilder feedbackText;
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
        this.lang = UserLanguage.get(bestLanguage);
        this.feedbackText = new StringBuilder();
        this.svgImage = svg;
        this.taskMode = taskMode;

        if(lang == UserLanguage.German){
            checkAndReplaceGermanCharactersInTaskTitle();
        }

        this.hasPassed = percentOfTestWordsFailed == 0;
        this.hasPassed &= submissionMatchesTarget(solutionType, submissionType);
        this.svgTitle = getLangDependentSvgTitle(lang);
        if(!this.hasPassed){
            this.feedbackText.append(percentOfTestWordsFailed).append(" ").append(getLangDependentFeedback(lang));
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

    protected abstract String getLangDependentSvgTitle(UserLanguage lang);
    protected abstract String getLangDependentFeedback(UserLanguage lang);

    protected boolean submissionMatchesTarget(String type, String studType){
        return true;
    }

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

    public String getFeedback() {
        return feedbackText.toString();
    }

    public String getSvgTitle() {
        return svgTitle;
    }

    public boolean hasPassed() {
        return hasPassed;
    }
}