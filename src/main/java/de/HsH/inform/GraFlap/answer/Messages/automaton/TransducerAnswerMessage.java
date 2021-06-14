package de.HsH.inform.GraFlap.answer.Messages.automaton;

import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Result;
import de.HsH.inform.GraFlap.entity.TaskType;
import de.HsH.inform.GraFlap.entity.UserLanguage;
import org.jdom2.Element;

/**
 * child class to generate the details of the message for machines
 * @author Benjamin Held (07-29-2016)
 * @since 08-06-2016
 * @version 0.1.0
 */
public class TransducerAnswerMessage extends AutomatonAnswerMessage {

    public TransducerAnswerMessage( Result result, Arguments arguments, Element svg){
        super(result, arguments, svg);
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
    protected boolean submissionMatchesTarget( TaskType solutionType, TaskType submissionTaskType) {
        matchesNonDeterministic(solutionType, submissionTaskType);
        boolean passed = matchesDeterministic(solutionType, submissionTaskType);
        passed &= matchesTuringMachine(solutionType, submissionTaskType);
        if (solutionType == TaskType.MEALY && submissionTaskType != TaskType.MEALY) {
            passed = false;
            if (lang == UserLanguage.German) {
                feedbackText.append("Dies ist keine Mealy-Maschine. \n");
            } else {
                feedbackText.append("This is not a mealy machine. \n");
            }
        }
        else if (solutionType == TaskType.MOORE && submissionTaskType != TaskType.MOORE) {
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
