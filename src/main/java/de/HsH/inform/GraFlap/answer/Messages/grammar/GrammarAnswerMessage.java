package de.HsH.inform.GraFlap.answer.Messages.grammar;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
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
     * @param percentOfTestWordsFailed  value how many word failed the testing ranging form [0,100]
     * @param taskTitle        the taskTitle of the assignment
     * @param bestLanguage a string coding the used language of the assignment
     * @param taskMode     a string holding the coded mode information
     * @param solutionType         a string coding the type of the solution
     * @param submissionType     a string coding the type of the submission
     * @param svg          a XML-element that gains the information for the output svg
     */
    public GrammarAnswerMessage(int percentOfTestWordsFailed, String taskTitle, String bestLanguage, String taskMode, String solutionType, String submissionType, Element svg) {
        super(percentOfTestWordsFailed, taskTitle, bestLanguage, taskMode, solutionType, submissionType, svg);
    }

    @Override
    protected String getGermanSvgTitle() {
        return "Grammatik";
    }

    @Override
    protected String getEnglishSvgTitle() {
        return "Grammar";
    }

    @Override
    protected String getGermanFeedbackText() {
        return "Prozent der getesteten Worte haben den Test gegen die Grammatik nicht bestanden.";
    }

    @Override
    protected String getEnglishFeedbackText() {
        return "percent of the tested words did not pass the test against the grammar.";
    }

    @Override
    protected boolean submissionMatchesTarget(String solutionType, String submissionType) {
        if ((!(solutionType.equals(submissionType)))) {
            if (!(solutionType.equals("rlcfg") && ((submissionType.equals("rl") || submissionType.equals("cfg"))))) {
                if (language.equalsIgnoreCase("de")) {
                    feedbackText.append("Die eingereichte Grammatik hat nicht den geforderten Typ. \n");
                } else {
                    feedbackText.append("Your grammar is not of the required solutionType. \n");
                }
                return false;
            }
        }
        return true;
    }
}