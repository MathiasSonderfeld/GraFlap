package de.HsH.inform.GraFlap.answer;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.svg.SvgAnswerMessage;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Result;
import de.HsH.inform.GraFlap.entity.TaskMode;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.answer.Messages.algorithm.CYKAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.algorithm.DerivationAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.automaton.AcceptorAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.automaton.TransducerAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.grammar.GrammarAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.grammar.WordAnswerMessage;
import org.jdom2.Element;

/**
 * static factory class to answerMessage = the correct Output for a given submission & Output mode
 * @author Benjamin Held (07-30-2016)
 * @author Mathias Sonderfeld
 * @since 08-10-2016
 * @version 0.5
 */
public class AnswerFactory {

    /**
     * 
     * @param result
     * @param arguments
     * @return the AnswerMessage that contains the correct Output-Data
     * @throws GraFlapException if the AnswerMessage can not be identified
     */
    public static AnswerMessage determineAnswer( Result result, Arguments arguments, Element svg) throws GraFlapException {
        AnswerMessage answerMessage = null;
        if(arguments.getTaskMode().isGrammar()) {
            answerMessage = new GrammarAnswerMessage(result.getPercentageFailed(), arguments.getTaskTitle(), arguments.getUserLanguage(), arguments.getTaskMode().toString().toLowerCase(),
                    arguments.getTaskType().toString().toLowerCase(), result.getSubmissionType().toString().toLowerCase(), svg);
        }
        else if(arguments.getTaskMode().isAutomaton()) {
            answerMessage = new AcceptorAnswerMessage(result.getPercentageFailed(), arguments.getTaskTitle(), arguments.getUserLanguage(), arguments.getTaskMode().toString().toLowerCase(), arguments.getTaskType().toString().toLowerCase(), result.getSubmissionType().toString().toLowerCase(), svg);
        }
        else if(arguments.getTaskMode() == TaskMode.MP || arguments.getTaskMode() == TaskMode.MMW) {
            answerMessage = new TransducerAnswerMessage(result.getPercentageFailed(), arguments.getTaskTitle(), arguments.getUserLanguage(), arguments.getTaskMode().toString().toLowerCase(), arguments.getTaskType().toString().toLowerCase(), result.getSubmissionType().toString().toLowerCase(), svg);
        }
        else if(arguments.getTaskMode() == TaskMode.WW) {
            answerMessage = new WordAnswerMessage(result.getPercentageFailed(), arguments.getTaskTitle(), arguments.getUserLanguage(), arguments.getTaskMode().toString().toLowerCase(),
                    arguments.getTaskType().toString().toLowerCase(), result.getSubmissionType().toString().toLowerCase(), svg);
        }
        else if(arguments.getTaskMode() == TaskMode.CYK) {
            answerMessage = new CYKAnswerMessage(result.getPercentageFailed(), arguments.getTaskTitle(), arguments.getUserLanguage(), arguments.getTaskMode().toString().toLowerCase(), arguments.getTaskType().toString().toLowerCase(), result.getSubmissionType().toString().toLowerCase(), svg);
        }
        else if(arguments.getTaskMode() == TaskMode.DER) {
            answerMessage = new DerivationAnswerMessage(result.getPercentageFailed(), arguments.getTaskTitle(), arguments.getUserLanguage(), arguments.getTaskMode().toString().toLowerCase(), arguments.getTaskType().toString().toLowerCase(), result.getSubmissionType().toString().toLowerCase(), svg);
        }
        else if(arguments.getTaskMode() == TaskMode.SVGA || arguments.getTaskMode() == TaskMode.SVGG) {
            answerMessage = new SvgAnswerMessage(result.getPercentageFailed(), arguments.getTaskTitle(), arguments.getUserLanguage(), arguments.getTaskMode().toString().toLowerCase(), arguments.getTaskType().toString().toLowerCase(),
                    result.getSubmissionType().toString().toLowerCase(), svg);
        }
        
        if(answerMessage == null){
            throw new GraFlapException("ERROR [AnswerFactory]: the answer to the mode could not be determined.");
        }

        if(arguments.getTaskMode().isParameterized()){
            answerMessage.setStates(result.getStates());
            answerMessage.setInitials(result.getInitials());
            answerMessage.setFinals(result.getFinals());
            answerMessage.setAlphabet(result.getAlphabet());
            answerMessage.setStackalphabet(result.getStackalphabet());
            answerMessage.setTransitions(result.getTransitions());
        }
        return answerMessage;
    }
}
