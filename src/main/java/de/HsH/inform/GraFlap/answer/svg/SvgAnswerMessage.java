package de.HsH.inform.GraFlap.answer.svg;

import de.HsH.inform.GraFlap.answer.AnswerMessage;
import org.jdom2.Element;

/**
 * child class to generate the details of the message for the svg mode
 * @author Benjamin Held (07-30-2016)
 * @since 08-09-2016
 * @version 0.1.0
 */
public class SvgAnswerMessage extends AnswerMessage {

    /**
     * Constructor
     * @param resultValue  value how many word failed the testing ranging form [0,100]
     * @param title        the title of the assignment
     * @param bestLanguage a string coding the used language of the assignment
     * @param taskMode     a string holding the coded mode information
     * @param type         a string coding the type of the solution
     * @param studType     a string coding the type of the submission
     * @param svg          a XML-element that gains the information for the output svg
     */
    public SvgAnswerMessage(int resultValue, String title, String bestLanguage, String taskMode, String type,
                            String studType, Element svg) {
        super(resultValue, title, bestLanguage, taskMode, type, studType, svg);
    }

    @Override
    protected void determineSvgTitle() {
        if (language.equalsIgnoreCase("de")) {
            svgTitle = "Svg-Modus";
        } else {
            svgTitle = "Svg-Mode";
        }
    }

    @Override
    protected boolean submissionMatchesTarget(String type, String studType) {
        resultText.append("Note: This is the svg test mode! \n");
        return true;
    }

    @Override
    protected boolean finishAssessment(int resultValue) {
        return true;
    }
}