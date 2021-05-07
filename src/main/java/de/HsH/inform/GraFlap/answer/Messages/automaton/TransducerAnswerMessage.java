package de.HsH.inform.GraFlap.answer.Messages.automaton;

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
     * @param resultValue  value how many word failed the testing ranging form [0,100]
     * @param title        the title of the assignment
     * @param bestLanguage a string coding the used language of the assignment
     * @param taskMode     a string holding the coded mode information
     * @param type         a string coding the type of the solution
     * @param studType     a string coding the type of the submission
     * @param svg          a XML-element that gains the information for the output svg
     */
    public TransducerAnswerMessage(int resultValue, String title, String bestLanguage, String taskMode, String type,
                                   String studType, Element svg) {
        super(resultValue, title, bestLanguage, taskMode, type, studType, svg);
    }

    @Override
    protected boolean finishAssessment(int resultValue) {
        if  (resultValue > 0) {
            resultText.append(resultValue).append(" ");
            if (language.equalsIgnoreCase("de")) {
                resultText.append("Prozent der getesteten Worte haben den Test gegen die Maschine nicht bestanden.");
            } else  {
                resultText.append("percent of the tested words did not pass the test against the machine.");
            }
            return false;
        }

        return true;
    }

    @Override
    protected void determineSvgTitle() {
        if (language.equalsIgnoreCase("de")) {
            svgTitle = "Maschine";
        } else {
            svgTitle = "Machine";
        }
    }

    @Override
    protected boolean submissionMatchesTarget(String type, String studType) {
        matchesNonDeterministic(type, studType);
        boolean passed = matchesDeterministic(type, studType);
        passed &= matchesTuringMachine(type, studType);
        if ((type.contains("mealy")) && (!(studType.contains("mealy")))) {
            passed = false;
            if (language.equalsIgnoreCase("de")) {
                resultText.append("Dies ist keine Mealy-Maschine. \n");
            } else {
                resultText.append("This is not a mealy machine. \n");
            }
        } else if ((type.contains("moore")) && (!(studType.contains("moore")))) {
            passed = false;
            if (language.equalsIgnoreCase("de")) {
                resultText.append("Dies ist keine Moore-Maschine. \n");
            } else {
                resultText.append("This is not a moore machine. \n");
            }
        }

        return passed;
    }
}
