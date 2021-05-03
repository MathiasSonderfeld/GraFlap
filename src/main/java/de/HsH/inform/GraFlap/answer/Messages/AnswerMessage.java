package de.HsH.inform.GraFlap.answer.Messages;

import de.HsH.inform.GraFlap.entity.OutputType;
import org.jdom2.CDATA;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * abstract class that serves a parent to generate the answer message, which is sent back to LON-CAPA
 * @author Benjamin Held (07-29-2016)
 * @author Lukas Seltmann (06-12-2019)
 * @author Mathias Sonderfeld (14-04-2021)
 * @since 08-04-2016
 * @version 0.1.5
 */
public abstract class AnswerMessage {

    /**
     * a string holding the coded mode information
     */
    protected String mode;
    /**
     * a string coding the information if the submission was correct or not (EXACT_ANS|INCORRECT)
     */
    private String award;
    /**
     * a string coding the grading of the submission (passed|failed)
     */
    private String grade;
    /**
     * a string builder that creates the feedback to the submission
     */
    protected StringBuilder resultText;
    /**
     * a string for the name of the assignment
     */
    private String taskTitle;
    /**
     * a string coding the used language of the assignment
     */
    protected String language;
    /**
     * a string for the title of the result svg
     */
    protected String svgTitle;
    /**
     * a XML-element that gains the information for the output svg
     */
    private Element svgImage;

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
        this.language = bestLanguage;
        this.resultText = new StringBuilder();
        this.svgImage = svg;
        this.mode = taskMode;

        checkAndReplaceGermanCharacter();

        determineSvgTitle();
        boolean hasPassed = submissionMatchesTarget(type, studType);
        hasPassed &= finishAssessment(resultValue);
        determineGradingInformation(hasPassed);
    }

    /**
     * method to create an output string that holds the answer message that can be sent to LonCapa
     * @return a string holding the formatted answer for LonCapa
     */
    public String generateAnswerMessage() {
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        if (mode.equals("asvg")) {
            return out.outputString(svgImage);
        } else {
            Element root = new Element("loncapagrade");
            root.addContent(new Element("awarddetail").addContent(award));
            Element taskResult = new Element("taskresult");
            taskResult.setAttribute("grade", grade);
            taskResult.addContent(new Element("tasktitle").addContent(taskTitle));
            taskResult.addContent(new Element("titlesvg").addContent(svgTitle));

            if (!(svgImage == null)) {
                String svgstring = new XMLOutputter().outputString(svgImage);

                taskResult.addContent(new Element("imagesvg").addContent(new CDATA(svgstring)));
            }

            taskResult.addContent(new Element("resulttext").addContent(resultText.toString()));
            root.addContent(new Element("message").addContent(taskResult));

            return out.outputString(root);
        }
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

    private void determineGradingInformation(boolean hasPassed) {
        if (hasPassed) {
            award = "EXACT_ANS";
            grade = "passed";
        } else {
            award = "INCORRECT";
            grade = "failed";
        }
    }

    protected abstract void determineSvgTitle();
    protected abstract boolean submissionMatchesTarget(String type, String studType);
    protected abstract boolean finishAssessment(int resultValue);
}