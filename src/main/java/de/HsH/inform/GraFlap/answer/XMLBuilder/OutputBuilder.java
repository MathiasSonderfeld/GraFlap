package de.HsH.inform.GraFlap.answer.XMLBuilder;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;

/**
 * @author Mathias Sonderfeld
 * @version 1.0
 *
 * Interface for OutputBuilders. To add new Builder implement this Interface in new Class, add new Value to OutputType-Enum and create Instance in
 * AnswerFactory.
 */
public interface OutputBuilder {
    public String getOutput( AnswerMessage answerMessage);
}
