package de.HsH.inform.GraFlap.answer.Messages.automaton;

import de.HsH.inform.GraFlap.entity.UserLanguage;
import org.jdom2.Element;

/**
 * child class to generate the details of the message for machines
 * @author Benjamin Held (07-29-2016)
 * @since 08-06-2016
 * @version 0.1.0
 */
public class TransducerAnswerMessage extends AutomatonAnswerMessage {
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
    public TransducerAnswerMessage(int percentOfTestWordsFailed, String taskTitle, String bestLanguage, String taskMode, String solutionType, String submissionType, Element svg) {
        super(percentOfTestWordsFailed, taskTitle, bestLanguage, taskMode, solutionType, submissionType, svg);
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Maschine";

            case English:
            default:
                return "Machine";
        }
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Prozent der getesteten Worte haben den Test gegen die Maschine nicht bestanden.";

            case English:
            default:
                return "percent of the tested words did not pass the test against the machine.";
        }
    }


    @Override
    protected boolean submissionMatchesTarget(String solutionType, String submissionType) {
        matchesNonDeterministic(solutionType, submissionType);
        boolean passed = matchesDeterministic(solutionType, submissionType);
        passed &= matchesTuringMachine(solutionType, submissionType);
        if ((solutionType.contains("mealy")) && (!(submissionType.contains("mealy")))) {
            passed = false;
            if (lang == UserLanguage.German) {
                feedbackText.append("Dies ist keine Mealy-Maschine. \n");
            } else {
                feedbackText.append("This is not a mealy machine. \n");
            }
        }
        else if ((solutionType.contains("moore")) && (!(submissionType.contains("moore")))) {
            passed = false;
            if (lang == UserLanguage.German) {
                feedbackText.append("Dies ist keine Moore-Maschine. \n");
            } else {
                feedbackText.append("This is not a moore machine. \n");
            }
        }
        return passed;
    }
}
