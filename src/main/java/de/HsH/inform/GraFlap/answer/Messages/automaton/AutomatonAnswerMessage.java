package de.HsH.inform.GraFlap.answer.Messages.automaton;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.UserLanguage;
import org.jdom2.Element;

/**
 * abstract class that serves as a parent to generate the answer message for automatons
 * @author Benjamin Held (07-29-2016)
 * @since 08-06-2016
 * @version 0.1.0
 */
public abstract class AutomatonAnswerMessage extends AnswerMessage {

    /**
     * Constructor
     * @param percentOfTestWordsFailed  value how many word failed the testing ranging form [0,100]
     * @param taskTitle        the taskTitle of the assignment
     * @param bestLanguage a string coding the used language of the assignment
     * @param taskMode     a string holding the coded mode information
     * @param type         a string coding the type of the solution
     * @param submissionType     a string coding the type of the submission
     * @param svg          a XML-element that gains the information for the output svg
     */
    public AutomatonAnswerMessage(int percentOfTestWordsFailed, String taskTitle, String bestLanguage, String taskMode, String type, String submissionType, Element svg) {
        super(percentOfTestWordsFailed, taskTitle, bestLanguage, taskMode, type, submissionType, svg);
    }

    /**
     * method to check if a given automaton is not deterministic
     * @param solutionType the predefined type
     * @param submissionType the type of the submission
     * @return true, if the submission is deterministic; false, if not
     */
    boolean matchesDeterministic(String solutionType, String submissionType) {
        if ((solutionType.startsWith("d")) && (submissionType.startsWith("n"))) {
            if (lang == UserLanguage.German) {
                feedbackText.append("Ihr Automat ist nicht deterministisch. \n");
            } else {
                feedbackText.append("Your automaton is not determinisitic. \n");
            }
            return false;
        }
        return true;
    }

    /**
     * method to check if a given automaton is a turing machine
     * @param solutionType the predefined type
     * @param submissionType the type of the submission
     * @return true, if the submission is a turing machine; false, if not
     */
    boolean matchesTuringMachine(String solutionType, String submissionType) {
        if ((solutionType.endsWith("tm")) && (!(submissionType.endsWith("tm")))){
            if (lang == UserLanguage.German) {
                feedbackText.append("Dies ist kein Turing-").append(svgTitle.toLowerCase()).append(". \n");
            } else {
                feedbackText.append("This is not a Turing ").append(svgTitle.toLowerCase()).append(". \n");
            }
            return false;
        }
        return true;
    }

    /**
     * method to check if a given automaton is deterministic
     * @param solutionType the predefined type
     * @param submissionType the type of the submission
     */
    void matchesNonDeterministic(String solutionType, String submissionType) {
        if ((solutionType.startsWith("n")) && (submissionType.startsWith("d"))) {
            if (lang == UserLanguage.German) {
                feedbackText.append("Ihr Automat ist eigentlich deterministisch. \n");
            } else {
                feedbackText.append("Actually, your automaton is deterministic. \n");
            }
        }
    }
}
