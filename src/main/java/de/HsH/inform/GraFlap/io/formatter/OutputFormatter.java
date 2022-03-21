package de.HsH.inform.GraFlap.io.formatter;

import de.HsH.inform.GraFlap.answerMessage.AnswerMessage;
import de.HsH.inform.GraFlap.entity.MetaData;

/**
 * Interface for OutputBuilders. To add new Builder implement this Interface in new Class and create Instance in main method.
 * @author Mathias Sonderfeld
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public interface OutputFormatter {
    String format(AnswerMessage answerMessage, MetaData metaData);
}
