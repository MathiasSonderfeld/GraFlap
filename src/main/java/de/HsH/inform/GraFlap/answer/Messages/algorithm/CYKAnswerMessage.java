package de.HsH.inform.GraFlap.answer.Messages.algorithm;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.UserLanguage;
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
     * @param percentOfTestWordsFailed  value how many word failed the testing ranging form [0,100]
     * @param taskTitle        the taskTitle of the assignment
     * @param bestLanguage a string coding the used language of the assignment
     * @param taskMode     a string holding the coded mode information
     * @param solutionType a string coding the type of the solution
     * @param submissionType     a string coding the type of the submission
     * @param svg          a XML-element that gains the information for the output svg
     */
    public CYKAnswerMessage(int percentOfTestWordsFailed, String taskTitle, String bestLanguage, String taskMode, String solutionType, String submissionType, Element svg) {
        super(percentOfTestWordsFailed, taskTitle, bestLanguage, taskMode, solutionType, submissionType, svg);
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        switch(lang){
            case German:
                return "CYK-Algortihmus";

            case English:
            default:
                return "CYK-Algorithm";
        }
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Tabelleneinträge haben den Test nicht bestanden.";

            case English:
            default:
                return "entries of the table did not pass the test.";
        }
    }
}