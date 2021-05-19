package de.HsH.inform.GraFlap.answer;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.svg.SvgAnswerMessage;
import de.HsH.inform.GraFlap.io.formatter.LoncapaOutputFormatter;
import de.HsH.inform.GraFlap.io.formatter.OutputFormatter;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.answer.Messages.algorithm.CYKAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.algorithm.DerivationAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.automaton.AcceptorAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.automaton.TransducerAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.grammar.GrammarAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.grammar.WordAnswerMessage;
import de.HsH.inform.GraFlap.entity.OutputType;
import de.HsH.inform.GraFlap.io.formatter.ProformaOutputFormatter;
import org.jdom2.Element;

/**
 * static factory class to return the correct Output for a given submission & Output mode
 * @author Benjamin Held (07-30-2016)
 * @author Mathias Sonderfeld
 * @since 08-10-2016
 * @version 0.5
 */
public class AnswerFactory {

    /**
     * @param resultValue the score of the submission
     * @param title the title of the Task
     * @param bestLanguage preferred Language by Student
     * @param taskMode the operationMode from Input, required to determine correct answerMessage
     * @param type the operationType from Input, required to determine correct answerMessage
     * @param studType I dont know what this does, sorry
     * @param svg the SVG to embed into the answer
     * @return the AnswerMessage that contains the correct Output-Data
     * @throws GraFlapException if the AnswerMessage can not be identified
     */
    public static AnswerMessage determineAnswer( int resultValue, String title, String bestLanguage, String taskMode,
                                                  String type, String studType, Element svg) throws GraFlapException {
        if (taskMode.startsWith("g") || taskMode.equals("egt")) {
            return new GrammarAnswerMessage(resultValue, title, bestLanguage, taskMode, type, studType, svg);
        } else if (taskMode.startsWith("a") || taskMode.equals("eat")) {
            return new AcceptorAnswerMessage(resultValue, title, bestLanguage, taskMode, type, studType, svg);
        } else if (taskMode.startsWith("m")) {
            return new TransducerAnswerMessage(resultValue, title, bestLanguage, taskMode, type, studType, svg);
        } else if (taskMode.startsWith("w")) {
            return new WordAnswerMessage(resultValue, title, bestLanguage, taskMode, type, studType, svg);
        } else if (taskMode.equals("cyk")) {
            return new CYKAnswerMessage(resultValue, title, bestLanguage, taskMode, type, studType, svg);
        } else if (taskMode.equals("der")) {
            return new DerivationAnswerMessage(resultValue, title, bestLanguage, taskMode, type, studType, svg);
        } else if (taskMode.startsWith("svg")) {
            return new SvgAnswerMessage(resultValue, title, bestLanguage, taskMode, type, studType, svg);
        } else {
            throw new GraFlapException("ERROR [AnswerFactory]: the answer to the mode could not be determined.");
        }
    }

    /**
     * @param resultValue the score of the submission
     * @param title the title of the Task
     * @param bestLanguage preferred Language by Student
     * @param taskMode the operationMode from Input, required to determine correct answerMessage
     * @param type the operationType from Input, required to determine correct answerMessage
     * @param studType I dont know what this does, sorry
     * @param svg the SVG to embed into the answer
     * @param outputType determines the Output format
     * @return String formatted as determined
     * @throws GraFlapException if Builder cant be determined or if determineAnswer throws Exception
     */
    public static String getOutput( int resultValue, String title, String bestLanguage, String taskMode, String type,
                                    String studType, Element svg, OutputType outputType) throws GraFlapException{
        AnswerMessage msg = determineAnswer(resultValue, title,bestLanguage,taskMode,type,studType,svg);
        OutputFormatter builder = null;
        switch(outputType){
            case Proforma:
                builder = new ProformaOutputFormatter();
                break;
            case Loncapa:
                builder = new LoncapaOutputFormatter();
                break;
            default:
                throw  new GraFlapException("Error in OutputType Defenition.");
        }
        return builder.format(msg);
    }
}
