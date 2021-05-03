package de.HsH.inform.GraFlap.answer.grammar;

import de.HsH.inform.GraFlap.answer.AnswerMessage;
import org.jdom2.Element;

/**
 * child class to generate the details of the message for grammars
 * @author Benjamin Held (07-30-2016)
 * @since 08-08-2016
 * @version 0.1.0
 */
public class GrammarAnswerMessage extends AnswerMessage {

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
    public GrammarAnswerMessage(int resultValue, String title, String bestLanguage, String taskMode, String type,
                                String studType, Element svg) {
        super(resultValue, title, bestLanguage, taskMode, type, studType, svg);
    }

    @Override
    protected void determineSvgTitle() {
        if (language.equalsIgnoreCase("de")) {
            svgTitle = "Grammatik";
        } else {
            svgTitle = "Grammar";
        }
    }

    @Override
    protected boolean submissionMatchesTarget(String type, String studType) {
        if ((!(type.equals(studType)))) {
            if (!(type.equals("rlcfg") && ((studType.equals("rl") || studType.equals("cfg"))))) {
                if (language.equalsIgnoreCase("de")) {
                    resultText.append("Die eingereichte Grammatik hat nicht den geforderten Typ. \n");
                } else {
                    resultText.append("Your grammar is not of the required type. \n");
                }
                return false;
            }
        }
        return true;
    }

    @Override
    protected boolean finishAssessment(int resultValue) {
        if  (resultValue > 0) {
            resultText.append(resultValue).append(" ");
            if (language.equalsIgnoreCase("de")) {
                resultText.append("Prozent der getesteten Worte haben den Test gegen die Grammatik nicht bestanden.");
            } else  {
                resultText.append("percent of the tested words did not pass the test against the grammar.");
            }
            return false;
        }

        return true;
    }
}