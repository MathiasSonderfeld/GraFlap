package de.HsH.inform.GraFlap.answer.Messages.svg;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.UserLanguage;
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
     * @param taskType         a string coding the type of the solution
     * @param submissionType     a string coding the type of the submission
     * @param svg          a XML-element that gains the information for the output svg
     */
    public SvgAnswerMessage(int resultValue, String title, String bestLanguage, String taskMode, String taskType, String submissionType, Element svg) {
        super(resultValue, title, bestLanguage, taskMode, taskType, submissionType, svg);
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Svg-Modus";

            case English:
            default:
                return "Svg-Mode";
        }
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Notiz: Dies ist ein SVG-Test Modus!";

            case English:
            default:
                return "Note: This is the svg test mode!";
        }
    }
}