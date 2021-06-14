package de.HsH.inform.GraFlap.answer.Messages.grammar;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Result;
import de.HsH.inform.GraFlap.entity.TaskType;
import de.HsH.inform.GraFlap.entity.UserLanguage;
import org.jdom2.Element;

/**
 * child class to generate the details of the message for grammars
 * @author Benjamin Held (07-30-2016)
 * @since 08-08-2016
 * @version 0.1.0
 */
public class GrammarAnswerMessage extends AnswerMessage {

    public GrammarAnswerMessage( Result result, Arguments arguments, Element svg){
        super(result, arguments, svg);
    }

    @Override
    protected String getLangDependentSvgTitle( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Grammatik";

            case English:
            default:
                return "Grammar";
        }
    }

    @Override
    protected String getLangDependentFeedback( UserLanguage lang ) {
        switch(lang){
            case German:
                return "Prozent der getesteten Worte haben den Test gegen die Grammatik nicht bestanden.";

            case English:
            default:
                return "percent of the tested words did not pass the test against the grammar.";
        }
    }

    @Override
    protected boolean submissionMatchesTarget( TaskType solutionType, TaskType submissionTaskType) {
        if (solutionType != submissionTaskType) {
            if (!(solutionType == TaskType.RLCFG && (submissionTaskType == TaskType.RL || submissionTaskType == TaskType.CFG))) {
                if (lang == UserLanguage.German) {
                    feedbackText.append("Die eingereichte Grammatik hat nicht den geforderten Typ. \n");
                } else {
                    feedbackText.append("Your grammar is not of the required solutionType. \n");
                }
                return false;
            }
        }
        return true;
    }
}