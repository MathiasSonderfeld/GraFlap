package de.HsH.inform.GraFlap.answer.Messages.automaton;

import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Result;
import de.HsH.inform.GraFlap.entity.TaskType;
import de.HsH.inform.GraFlap.entity.UserLanguage;
import org.jdom2.Element;

/**
 * child class to generate the details of the message for accepting automatons
 * @author Benjamin Held (07-29-2016)
 * @since 08-06-2016
 * @version 0.1.0
 */
public class AcceptorAnswerMessage extends AutomatonAnswerMessage {

    public AcceptorAnswerMessage( Result result, Arguments arguments, Element svg){
        super(result, arguments, svg);
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Automat";

            case English:
            default:
                return "Automaton";
        }
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Prozent der getesteten Worte haben den Test gegen den Automaten nicht bestanden.";

            case English:
            default:
                return "percent of the tested words did not pass the test against the automaton.";
        }
    }

    @Override
    protected boolean submissionMatchesTarget( TaskType taskType, TaskType submissionTaskType) {
        boolean passed = true;
        if (taskMode.isTyped()) {
            matchesNonDeterministic(taskType, submissionTaskType);
            passed = matchesDeterministic(taskType, submissionTaskType);
            if (isFiniteAutomaton(taskType) && !isFiniteAutomaton(submissionTaskType)) {
                passed = false;
                if (lang == UserLanguage.German) {
                    feedbackText.append("Dies ist kein endlicher Automat. \n");
                } else {
                    feedbackText.append("This is not a finite automaton. \n");
                }
            }
            else if (isPushDownAutomaton(taskType) && !isPushDownAutomaton(submissionTaskType)) {
                passed = false;
                if (lang == UserLanguage.German) {
                    feedbackText.append("Dies ist kein Kellerautomat. \n");
                } else {
                    feedbackText.append("This is not a push-down automaton. \n");
                }
            } else if (!matchesTuringMachine(taskType, submissionTaskType)) {
                passed = false;
            }
        }
        return passed;
    }
}
