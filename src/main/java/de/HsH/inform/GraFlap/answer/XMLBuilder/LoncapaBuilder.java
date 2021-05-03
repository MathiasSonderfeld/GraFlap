package de.HsH.inform.GraFlap.answer.XMLBuilder;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;

public class LoncapaBuilder extends XMLBuilder{

    public LoncapaBuilder( AnswerMessage answerMessage ) {
        this.xml = answerMessage.generateAnswerMessage();
    }
}
