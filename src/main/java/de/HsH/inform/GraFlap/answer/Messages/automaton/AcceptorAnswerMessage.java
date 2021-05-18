package de.HsH.inform.GraFlap.answer.Messages.automaton;

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
     * @param percentOfTestWordsFailed  value how many word failed the testing ranging form [0,100]
     * @param taskTitle        the taskTitle of the assignment
     * @param bestLanguage a string coding the used language of the assignment
     * @param taskMode     a string holding the coded mode information
     * @param solutionType a string coding the type of the solution
     * @param submissionType     a string coding the type of the submission
     * @param svg          a XML-element that gains the information for the output svg
     */
    public AcceptorAnswerMessage(int percentOfTestWordsFailed, String taskTitle, String bestLanguage, String taskMode, String solutionType, String submissionType, Element svg) {
        super(percentOfTestWordsFailed, taskTitle, bestLanguage, taskMode, solutionType, submissionType, svg);
    }

    @Override
    protected String getGermanSvgTitle() {
        return "Automat";
    }

    @Override
    protected String getEnglishSvgTitle() {
        return "Automaton";
    }

    @Override
    protected String getGermanFeedbackText() {
        return "Prozent der getesteten Worte haben den Test gegen den Automaten nicht bestanden.";
    }

    @Override
    protected String getEnglishFeedbackText() {
        return "percent of the tested words did not pass the test against the automaton.";
    }

    @Override
    protected boolean submissionMatchesTarget(String solutionType, String submissionType) {
        boolean passed = true;
        if (taskMode.contains("t")) {
            matchesNonDeterministic(solutionType, submissionType);
            passed = matchesDeterministic(solutionType, submissionType);
            if ((solutionType.endsWith("fa")) && (!(submissionType.endsWith("fa")))) {
                passed = false;
                if (language.equalsIgnoreCase("de")) {
                    feedbackText.append("Dies ist kein endlicher Automat. \n");
                } else {
                    feedbackText.append("This is not a finite automaton. \n");
                }
            } else if ((solutionType.endsWith("pda")) && (!(submissionType.endsWith("pda")))) {
                passed = false;
                if (language.equalsIgnoreCase("de")) {
                    feedbackText.append("Dies ist kein Kellerautomat. \n");
                } else {
                    feedbackText.append("This is not a push-down automaton. \n");
                }
            } else if (!matchesTuringMachine(solutionType, submissionType)) {
                passed = false;
            }
        }
        return passed;
    }
}
