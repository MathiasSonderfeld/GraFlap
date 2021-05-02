package de.HsH.inform.GraFlap.loncapa.answer;

import de.HsH.inform.GraFlap.loncapa.answer.algorithm.CYKAnswerMessage;
import de.HsH.inform.GraFlap.loncapa.answer.algorithm.DerivationAnswerMessage;
import de.HsH.inform.GraFlap.loncapa.answer.automaton.AcceptorAnswerMessage;
import de.HsH.inform.GraFlap.loncapa.answer.automaton.TransducerAnswerMessage;
import de.HsH.inform.GraFlap.loncapa.answer.grammar.GrammarAnswerMessage;
import de.HsH.inform.GraFlap.loncapa.answer.grammar.WordAnswerMessage;
import de.HsH.inform.GraFlap.loncapa.answer.svg.SvgAnswerMessage;
import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;
import org.jdom2.Element;

/**
 * static factory class to return the correct {@link AnswerMessage} for a given submission mode
 * @author Benjamin Held (07-30-2016)
 * @since 08-10-2016
 * @version 0.1.0
 */
public class AnswerFactory {

    public static AnswerMessage determineAnswer(int resultValue, String title, String bestLanguage, String taskMode,
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
}
