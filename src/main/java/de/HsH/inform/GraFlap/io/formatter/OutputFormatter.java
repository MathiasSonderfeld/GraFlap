package de.HsH.inform.GraFlap.io.formatter;

import de.HsH.inform.GraFlap.answerMessage.AnswerMessage;

/**
 * Interface for OutputBuilders. To add new Builder implement this Interface in new Class and create Instance in main method.
 * @author Mathias Sonderfeld
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public interface OutputFormatter {
    String format( AnswerMessage answerMessage );
}
