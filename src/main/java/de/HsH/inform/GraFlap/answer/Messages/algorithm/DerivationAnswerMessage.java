package de.HsH.inform.GraFlap.answer.Messages.algorithm;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.UserLanguage;
import org.jdom2.Element;

/**
 * child class of {@link AnswerMessage} to generate the answer message for a derivation exercise
 * @author Benjamin Held (07-30-2016)
 * @since 08-22-2016
 * @version 0.1.1
 */
public class DerivationAnswerMessage extends AnswerMessage {

    /**
     * Constructor
     * @param percentOfTestWordsFailed  value how many word failed the testing ranging form [0,100]
     * @param taskTitle        the taskTitle of the assignment
     * @param bestLanguage a string coding the used language of the assignment
     * @param taskMode     a string holding the coded mode information
     * @param taskType         a string coding the taskType of the solution
     * @param submissionType     a string coding the taskType of the submission
     * @param svg          a XML-element that gains the information for the output svg
     */
    public DerivationAnswerMessage(int percentOfTestWordsFailed, String taskTitle, String bestLanguage, String taskMode, String taskType, String submissionType, Element svg) {
        super(percentOfTestWordsFailed, taskTitle, bestLanguage, taskMode, taskType, submissionType, svg);
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Wortableitung";

            case English:
            default:
                return "Word derivation";
        }
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Prozent der Ableitungsschritte haben den Test nicht bestanden.";

            case English:
            default:
                return "percent of the derivation steps did not pass the test.";
        }
    }
}