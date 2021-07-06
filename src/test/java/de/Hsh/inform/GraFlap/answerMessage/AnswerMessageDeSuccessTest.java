package de.HsH.inform.GraFlap.answerMessage;

import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class AnswerMessageDeSuccessTest {
    private static Locale locale = Locale.GERMAN;
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("GraFlapAnswerMessage", locale);
    private static String feedbackFormat = "%d %s";
    private static Arguments arguments = new Arguments();
    private static int numberOfWordsFailed = 0;
    private static Submission submission = new Submission();
    private static Result result = new Result(submission, numberOfWordsFailed, TaskType.NON);
    private org.jdom2.Element nullElement = null;

    @BeforeAll
    static void init(){
        arguments.setStudentAnswer("StudentAnswer");
        arguments.setSolution("Solution");
        arguments.setTaskTitle("TaskTitle");
        arguments.setWordString("WordString");
        arguments.setNumberOfWords(123);
        arguments.setUserLanguage(locale);
    }

    @Test
    void testCYKMode(){
        arguments.setTaskMode(TaskMode.CYK);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.CYK_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals("", answerMessage.getFeedback());
    }

    @Test
    void testDERMode(){
        arguments.setTaskMode(TaskMode.DER);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.DERIVATION_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals("", answerMessage.getFeedback());
    }

    @Test
    void testWWMode(){
        arguments.setTaskMode(TaskMode.WW);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.WORD_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals("", answerMessage.getFeedback());
    }

    @Test
    void testSVGMode(){
        arguments.setTaskMode(TaskMode.SVGA);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.SVG_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals("", answerMessage.getFeedback());
    }

    @Test
    void testMMode(){
        arguments.setTaskMode(TaskMode.MP);
        arguments.setTaskType(TaskType.DTM);
        result = new Result(submission, numberOfWordsFailed, TaskType.DTM);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);

        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.TRANSDUCER_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals("", answerMessage.getFeedback());
    }

    @Test
    void testGRAMMARMode(){
        arguments.setTaskMode(TaskMode.GG);
        arguments.setTaskType(TaskType.CFG);
        result = new Result(submission, numberOfWordsFailed, TaskType.CFG);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.GRAMMAR_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals("", answerMessage.getFeedback());
    }

    @Test
    void testAAMode(){
        arguments.setTaskMode(TaskMode.AA);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.ACCEPTOR_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals("", answerMessage.getFeedback());
    }

    @Test
    void testFAMode(){
        arguments.setTaskMode(TaskMode.ARTWP);
        arguments.setTaskType(TaskType.DFA);
        result = new Result(submission, numberOfWordsFailed, TaskType.DFA);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);

        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.ACCEPTOR_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals("", answerMessage.getFeedback());
    }

    @Test
    void testPDAMode(){
        arguments.setTaskMode(TaskMode.ARTWP);
        arguments.setTaskType(TaskType.DPDA);
        result = new Result(submission, numberOfWordsFailed, TaskType.DPDA);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);

        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.ACCEPTOR_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals("", answerMessage.getFeedback());
    }

    @Test
    void testTMMode(){
        arguments.setTaskMode(TaskMode.ARTWP);
        arguments.setTaskType(TaskType.DTM);
        result = new Result(submission, numberOfWordsFailed, TaskType.DTM);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);

        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.ACCEPTOR_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals("", answerMessage.getFeedback());
    }

}

