package de.HsH.inform.GraFlap.answer.Messages.grammar;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.UserLanguage;
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
     * @param taskType         a string coding the type of the solution
     * @param submissionType     a string coding the type of the submission
     * @param svg          a XML-element that gains the information for the output svg
     */
    public GrammarAnswerMessage(int percentOfTestWordsFailed, String taskTitle, String bestLanguage, String taskMode, String taskType, String submissionType, Element svg) {
        super(percentOfTestWordsFailed, taskTitle, bestLanguage, taskMode, taskType, submissionType, svg);
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Grammatik";

            case English:
            default:
                return "Grammar";
        }
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Prozent der getesteten Worte haben den Test gegen die Grammatik nicht bestanden.";

            case English:
            default:
                return "percent of the tested words did not pass the test against the grammar.";
        }
    }

    @Override
    protected boolean submissionMatchesTarget(String solutionType, String submissionType) {
        if ((!(solutionType.equals(submissionType)))) {
            if (!(solutionType.equals("rlcfg") && ((submissionType.equals("rl") || submissionType.equals("cfg"))))) {
                if (lang == UserLanguage.German) {
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