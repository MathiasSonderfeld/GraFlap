package de.HsH.inform.GraFlap.answer;

import de.HsH.inform.GraFlap.Grader;
import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.svg.SvgAnswerMessage;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Result;
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
    public static AnswerMessage determineAnswer( Result result, Arguments arguments, String studType, Element svg) throws GraFlapException {
        AnswerMessage answerMessage = null;
        if(arguments.getMode().startsWith("g") || arguments.getMode().equals("egt")) {
            answerMessage = new GrammarAnswerMessage(result.getPercentageFailed(), arguments.getTaskTitle(), arguments.getUserLanguage(), arguments.getMode(), arguments.getArgtype(), studType, svg);
        }
        else if(arguments.getMode().startsWith("a") || arguments.getMode().equals("eat")) {
            answerMessage = new AcceptorAnswerMessage(result.getPercentageFailed(), arguments.getTaskTitle(), arguments.getUserLanguage(), arguments.getMode(), arguments.getArgtype(), studType, svg);
        }
        else if(arguments.getMode().startsWith("m")) {
            answerMessage = new TransducerAnswerMessage(result.getPercentageFailed(), arguments.getTaskTitle(), arguments.getUserLanguage(), arguments.getMode(), arguments.getArgtype(), studType, svg);
        }
        else if(arguments.getMode().startsWith("w")) {
            answerMessage = new WordAnswerMessage(result.getPercentageFailed(), arguments.getTaskTitle(), arguments.getUserLanguage(), arguments.getMode(), arguments.getArgtype(), studType, svg);
        }
        else if(arguments.getMode().equals("cyk")) {
            answerMessage = new CYKAnswerMessage(result.getPercentageFailed(), arguments.getTaskTitle(), arguments.getUserLanguage(), arguments.getMode(), arguments.getArgtype(), studType, svg);
        }
        else if(arguments.getMode().equals("der")) {
            answerMessage = new DerivationAnswerMessage(result.getPercentageFailed(), arguments.getTaskTitle(), arguments.getUserLanguage(), arguments.getMode(), arguments.getArgtype(), studType, svg);
        }
        else if(arguments.getMode().startsWith("svg")) {
            answerMessage = new SvgAnswerMessage(result.getPercentageFailed(), arguments.getTaskTitle(), arguments.getUserLanguage(), arguments.getMode(), arguments.getArgtype(), studType, svg);
        }
        
        if(answerMessage == null){
            throw new GraFlapException("ERROR [AnswerFactory]: the answer to the mode could not be determined.");
        }

        if(arguments.getMode().contains("p")){

        }
        return answerMessage;
    }
}
