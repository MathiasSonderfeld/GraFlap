package de.HsH.inform.GraFlap.io.formatter;

import de.HsH.inform.GraFlap.answerMessage.AnswerMessage;

/**
 * @author Mathias Sonderfeld
 * @version 1.0
 *
 * Interface for OutputBuilders. To add new Builder implement this Interface in new Class and create Instance in main method.
 */
public interface OutputFormatter {
    String format( AnswerMessage answerMessage );
}
