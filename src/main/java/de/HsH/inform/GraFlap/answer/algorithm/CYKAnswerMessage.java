package de.HsH.inform.GraFlap.answer.algorithm;

import de.HsH.inform.GraFlap.answer.AnswerMessage;
import org.jdom2.Element;

/**
 * child class of {@link AnswerMessage} to generate the answer message for a cyk exercise
 * @author Benjamin Held (07-30-2016)
 * @since 08-05-2016
 * @version 0.1.0
 */
public class CYKAnswerMessage extends AnswerMessage {

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
    public CYKAnswerMessage(int resultValue, String title, String bestLanguage, String taskMode, String type,
                            String studType, Element svg) {
        super(resultValue, title, bestLanguage, taskMode, type, studType, svg);
    }

    @Override
    protected void determineSvgTitle() {
        if (language.equalsIgnoreCase("de")) {
            svgTitle = "CYK-Algortihmus";
        } else {
            svgTitle = "CYK-Algorithm";
        }
    }

    @Override
    protected boolean submissionMatchesTarget(String type, String studType) {
        return true;
    }

    @Override
    protected boolean finishAssessment(int resultValue) {
        if  (resultValue > 0) {
            resultText.append(resultValue).append(" ");
            if (language.equalsIgnoreCase("de")) {
                resultText.append("Tabelleneinträge haben den Test nicht bestanden.");
            } else  {
                resultText.append("entries of the table did not pass the test.");
            }
            return false;
        }
        return true;
    }
}