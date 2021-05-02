package de.HsH.inform.GraFlap.loncapa.answer.automaton;

import org.jdom2.Element;

/**
 * child class to generate the details of the message for accepting automatons
 * @author Benjamin Held (07-29-2016)
 * @since 08-06-2016
 * @version 0.1.0
 */
public class AcceptorAnswerMessage extends AutomatonAnswerMessage {
    /**
     * Constructor
     *
     * @param resultValue  value how many word failed the testing ranging form [0,100]
     * @param title        the title of the assignment
     * @param bestLanguage a string coding the used language of the assignment
     * @param taskMode     a string holding the coded mode information
     * @param type         a string coding the type of the solution
     * @param studType     a string coding the type of the submission
     * @param svg          a XML-element that gains the information for the output svg
     */
    public AcceptorAnswerMessage(int resultValue, String title, String bestLanguage, String taskMode, String type,
                                 String studType, Element svg) {
        super(resultValue, title, bestLanguage, taskMode, type, studType, svg);
    }

    @Override
    protected boolean finishAssessment(int resultValue) {
        if  (resultValue > 0) {
            resultText.append(resultValue).append(" ");
            if (language.equalsIgnoreCase("de")) {
                resultText.append("Prozent der getesteten Worte haben den Test gegen den Automaten nicht bestanden.");
            } else  {
                resultText.append("percent of the tested words did not pass the test against the automaton.");
            }
            return false;
        }

        return true;
    }

    @Override
    protected boolean submissionMatchesTarget(String type, String studType) {
        boolean passed = true;
        if (mode.contains("t")) {
            matchesNonDeterministic(type, studType);
            passed = matchesDeterministic(type, studType);
            if ((type.endsWith("fa")) && (!(studType.endsWith("fa")))) {
                passed = false;
                if (language.equalsIgnoreCase("de")) {
                    resultText.append("Dies ist kein endlicher Automat. \n");
                } else {
                    resultText.append("This is not a finite automaton. \n");
                }
            } else if ((type.endsWith("pda")) && (!(studType.endsWith("pda")))) {
                passed = false;
                if (language.equalsIgnoreCase("de")) {
                    resultText.append("Dies ist kein Kellerautomat. \n");
                } else {
                    resultText.append("This is not a push-down automaton. \n");
                }
            } else if (!matchesTuringMachine(type, studType)) {
                passed = false;
            }
        }

        return passed;
    }

    @Override
    protected void determineSvgTitle() {
        if (language.equalsIgnoreCase("de")) {
            svgTitle ="Automat";
        } else  {
            svgTitle ="Automaton";
        }
    }
}
