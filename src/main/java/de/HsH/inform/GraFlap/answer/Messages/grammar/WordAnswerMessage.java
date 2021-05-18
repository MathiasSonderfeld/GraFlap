package de.HsH.inform.GraFlap.answer.Messages.grammar;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
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
     * @param solutionType         a string coding the type of the solution
     * @param submissionType     a string coding the type of the submission
     * @param svg          a XML-element that gains the information for the output svg
     */
    public WordAnswerMessage(int percentOfTestWordsFailed, String taskTitle, String bestLanguage, String taskMode, String solutionType, String submissionType, Element svg) {
        super(percentOfTestWordsFailed, taskTitle, bestLanguage, taskMode, solutionType, submissionType, svg);
    }

    @Override
    protected String getGermanSvgTitle() {
        return "Worte";
    }

    @Override
    protected String getEnglishSvgTitle() {
        return "Words";
    }

    @Override
    protected String getGermanFeedbackText() {
        return "Prozent der getesteten Worte haben den Test nicht bestanden.";
    }

    @Override
    protected String getEnglishFeedbackText() {
        return "percent of the tested words did not pass the test.";
    }
}