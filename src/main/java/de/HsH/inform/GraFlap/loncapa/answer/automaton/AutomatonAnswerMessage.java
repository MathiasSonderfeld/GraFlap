package de.HsH.inform.GraFlap.loncapa.answer.automaton;

import de.HsH.inform.GraFlap.loncapa.answer.AnswerMessage;
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
     * @param resultValue  value how many word failed the testing ranging form [0,100]
     * @param title        the title of the assignment
     * @param bestLanguage a string coding the used language of the assignment
     * @param taskMode     a string holding the coded mode information
     * @param type         a string coding the type of the solution
     * @param studType     a string coding the type of the submission
     * @param svg          a XML-element that gains the information for the output svg
     */
    public AutomatonAnswerMessage(int resultValue, String title, String bestLanguage, String taskMode, String type,
                                  String studType, Element svg) {
        super(resultValue, title, bestLanguage, taskMode, type, studType, svg);
    }

    /**
     * method to check if a given automaton is not deterministic
     * @param type the predefined type
     * @param studType the type of the submission
     * @return true, if the submission is deterministic; false, if not
     */
    boolean matchesDeterministic(String type, String studType) {
        if ((type.startsWith("d")) && (studType.startsWith("n"))) {
            if (language.equalsIgnoreCase("de")) {
                resultText.append("Ihr Automat ist nicht deterministisch. \n");
            } else {
                resultText.append("Your automaton is not determinisitic. \n");
            }
            return false;
        }
        return true;
    }

    /**
     * method to check if a given automaton is a turing machine
     * @param type the predefined type
     * @param studType the type of the submission
     * @return true, if the submission is a turing machine; false, if not
     */
    boolean matchesTuringMachine(String type, String studType) {
        if ((type.endsWith("tm")) && (!(studType.endsWith("tm")))){
            if (language.equalsIgnoreCase("de")) {
                resultText.append("Dies ist kein Turing-").append(svgTitle.toLowerCase()).append(". \n");
            } else {
                resultText.append("This is not a Turing ").append(svgTitle.toLowerCase()).append(". \n");
            }
            return false;
        }
        return true;
    }

    /**
     * method to check if a given automaton is deterministic
     * @param type the predefined type
     * @param studType the type of the submission
     */
    void matchesNonDeterministic(String type, String studType) {
        if ((type.startsWith("n")) && (studType.startsWith("d"))) {
            if (language.equalsIgnoreCase("de")) {
                resultText.append("Ihr Automat ist eigentlich deterministisch. \n");
            } else {
                resultText.append("Actually, your automaton is deterministic. \n");
            }
        }
    }
}
