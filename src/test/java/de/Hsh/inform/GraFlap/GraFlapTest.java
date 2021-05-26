package de.HsH.inform.GraFlap;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.OperationMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//TODO rewrite testcases as example
public class GraFlapTest {

    @Test
    void testExample() {
        Arguments arguments = new Arguments();
        arguments.setTaskTitle("");
        arguments.setUserLanguage("");
        arguments.setSolution("");
        arguments.setMode("");
        arguments.setArgtype("");
        arguments.setWordString("");
        arguments.setNumberOfWords(0);
        arguments.setStudentAnswer("");
        arguments.setOperationMode(OperationMode.DEFAULT);
        
        arguments.setStates("");
        arguments.setInitials("");
        arguments.setFinals("");
        arguments.setAlphabet("");
        arguments.setStackalphabet("");
        arguments.setTransitions("");

        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("", answerMessage.getTaskTitle());
        Assertions.assertEquals(new org.jdom2.Element(""), answerMessage.getSvgImage());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals("", answerMessage.getTaskMode());
        Assertions.assertEquals("", answerMessage.getFeedback());
        Assertions.assertEquals("", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
        Assertions.assertEquals("", answerMessage.getStatesTeacherFeedback());
        Assertions.assertEquals("", answerMessage.getStatesStudentFeedback());
        Assertions.assertEquals(1.0, answerMessage.getInitialsScore());
        Assertions.assertEquals("", answerMessage.getInitialsTeacherFeedback());
        Assertions.assertEquals("", answerMessage.getInitialsStudentFeedback());
        Assertions.assertEquals(1.0, answerMessage.getFinalsScore());
        Assertions.assertEquals("", answerMessage.getFinalsTeacherFeedback());
        Assertions.assertEquals("", answerMessage.getFinalsStudentFeedback());
        Assertions.assertEquals(1.0, answerMessage.getAlphabetScore());
        Assertions.assertEquals("", answerMessage.getAlphabetTeacherFeedback());
        Assertions.assertEquals("", answerMessage.getAlphabetStudentFeedback());
        Assertions.assertEquals(1.0, answerMessage.getStackAlphabetScore());
        Assertions.assertEquals("", answerMessage.getStackAlphabetTeacherFeedback());
        Assertions.assertEquals("", answerMessage.getStackAlphabetStudentFeedback());
        Assertions.assertEquals(1.0, answerMessage.getTransitionsScore());
        Assertions.assertEquals("", answerMessage.getTransitionsTeacherFeedback());
        Assertions.assertEquals("", answerMessage.getTransitionsStudentFeedback());
        
        //...
    }
}
