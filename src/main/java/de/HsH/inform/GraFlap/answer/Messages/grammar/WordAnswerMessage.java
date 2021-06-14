package de.HsH.inform.GraFlap.answer.Messages.grammar;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.UserLanguage;
import org.jdom2.Element;

/**
 * child class to generate the details of the message for word exercises
 * @author Benjamin Held (07-30-2016)
 * @since 08-08-2016
 * @version 0.1.0
 */
public class WordAnswerMessage extends AnswerMessage {
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
    public WordAnswerMessage(int percentOfTestWordsFailed, String taskTitle, String bestLanguage, String taskMode, String taskType, String submissionType, Element svg) {
        super(percentOfTestWordsFailed, taskTitle, bestLanguage, taskMode, taskType, submissionType, svg);
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Worte";

            case English:
            default:
                return "Words";
        }
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Prozent der getesteten Worte haben den Test nicht bestanden.";

            case English:
            default:
                return "percent of the tested words did not pass the test.";
        }
    }
}