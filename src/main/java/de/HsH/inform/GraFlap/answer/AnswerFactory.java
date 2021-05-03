package de.HsH.inform.GraFlap.answer;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.algorithm.CYKAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.algorithm.DerivationAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.automaton.AcceptorAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.automaton.TransducerAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.grammar.GrammarAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.grammar.WordAnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.svg.SvgAnswerMessage;
import de.HsH.inform.GraFlap.answer.XMLBuilder.LoncapaBuilder;
import de.HsH.inform.GraFlap.answer.XMLBuilder.ProformaBuilder;
import de.HsH.inform.GraFlap.answer.XMLBuilder.XMLBuilder;
import de.HsH.inform.GraFlap.entity.OutputType;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.jdom2.Element;

/**
 * static factory class to return the correct {@link AnswerMessage} for a given submission mode
 * @author Benjamin Held (07-30-2016)
 * @since 08-10-2016
 * @version 0.1.0
 */
public class AnswerFactory {

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

    public static String getXML( int resultValue, String title, String bestLanguage, String taskMode, String type,
                                 String studType, Element svg, OutputType outputType) throws GraFlapException{
        AnswerMessage msg = determineAnswer(resultValue, title,bestLanguage,taskMode,type,studType,svg);
        XMLBuilder builder = null;
        switch(outputType){
            case Proforma:
                builder = new ProformaBuilder(msg);
                break;
            case Loncapa:
                builder = new LoncapaBuilder(msg);
                break;
            default:
                throw  new GraFlapException("Error in OutputType Defenition.");
        }
        return builder.getXML();
    }
}
