package de.HsH.inform.GraFlap.answerMessage;

import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class AnswerMessageDeFailTest {
    private static Locale locale = Locale.GERMAN;
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("GraFlapAnswerMessage", locale);
    private static String feedbackFormat = "%d %s";
    private static Arguments arguments = new Arguments();
    private static int numberOfWordsFailed = 50;
    private static Result result = new Result(new Submission(), numberOfWordsFailed, TaskType.NON);

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
    void testEnumMatchesRessourceBundle(){
        for(AnswerMessages e : AnswerMessages.values()){
            Assertions.assertDoesNotThrow(() -> resourceBundle.getString(e.toString()));
        }
    }

    @Test
    void testCYKMode(){
        arguments.setTaskMode(TaskMode.CYK);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, null);
        Assertions.assertEquals(resourceBundle.getString(AnswerMessages.CYK_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, resourceBundle.getString(AnswerMessages.CYK_Feedback.toString())), answerMessage.getFeedback());
    }

    @Test
    void testDERMode(){
        arguments.setTaskMode(TaskMode.DER);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, null);
        Assertions.assertEquals(resourceBundle.getString(AnswerMessages.DERIVATION_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, resourceBundle.getString(AnswerMessages.DERIVATION_Feedback.name())), answerMessage.getFeedback());
    }

    @Test
    void testWWMode(){
        arguments.setTaskMode(TaskMode.WW);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, null);
        Assertions.assertEquals(resourceBundle.getString(AnswerMessages.WORD_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, resourceBundle.getString(AnswerMessages.WORD_Feedback.name())), answerMessage.getFeedback());
    }

    @Test
    void testSVGMode(){
        arguments.setTaskMode(TaskMode.SVGA);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, null);
        Assertions.assertEquals(resourceBundle.getString(AnswerMessages.SVG_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, resourceBundle.getString(AnswerMessages.SVG_Feedback.name())), answerMessage.getFeedback());
    }

    @Test
    void testMMode(){
        arguments.setTaskMode(TaskMode.MP);
        arguments.setTaskType(TaskType.DTM);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, null);
        String svgTitle = resourceBundle.getString(AnswerMessages.TRANSDUCER_Svgtitle.name());
        StringBuilder s = new StringBuilder();
        s.append(resourceBundle.getString(AnswerMessages.TRANSDUCER_Feedback.name()));
        s.append("\n").append(resourceBundle.getString(AnswerMessages.AUTOMATON_IsTuring.name())).append(" ").append(svgTitle);

        Assertions.assertEquals(svgTitle, answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, s), answerMessage.getFeedback());
    }

    @Test
    void testGRAMMARMode(){
        arguments.setTaskMode(TaskMode.GG);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, null);

        StringBuilder s = new StringBuilder();
        s.append(resourceBundle.getString(AnswerMessages.GRAMMAR_Feedback.name()));
        s.append("\n").append(resourceBundle.getString(AnswerMessages.GRAMMAR_Type.name()));

        Assertions.assertEquals(resourceBundle.getString(AnswerMessages.GRAMMAR_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, s), answerMessage.getFeedback());
    }

    @Test
    void testAAMode(){
        arguments.setTaskMode(TaskMode.AA);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, null);
        Assertions.assertEquals(resourceBundle.getString(AnswerMessages.ACCEPTOR_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, resourceBundle.getString(AnswerMessages.ACCEPTOR_AAFeedback.name())), answerMessage.getFeedback());
    }

    @Test
    void testFAMode(){
        arguments.setTaskMode(TaskMode.ARTWP);
        arguments.setTaskType(TaskType.DFA);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, null);
        String svgTitle = resourceBundle.getString(AnswerMessages.ACCEPTOR_Svgtitle.name());

        StringBuilder s = new StringBuilder();
        s.append(resourceBundle.getString(AnswerMessages.ACCEPTOR_Feedback.name()));
        s.append("\n").append(resourceBundle.getString(AnswerMessages.ACCEPTOR_FAFeedback.name()));

        Assertions.assertEquals(svgTitle, answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, s), answerMessage.getFeedback());
    }

    @Test
    void testPDAMode(){
        arguments.setTaskMode(TaskMode.ARTWP);
        arguments.setTaskType(TaskType.DPDA);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, null);
        String svgTitle = resourceBundle.getString(AnswerMessages.ACCEPTOR_Svgtitle.name());

        StringBuilder s = new StringBuilder();
        s.append(resourceBundle.getString(AnswerMessages.ACCEPTOR_Feedback.name()));
        s.append("\n").append(resourceBundle.getString(AnswerMessages.ACCEPTOR_PDAFeedback.name()));

        Assertions.assertEquals(svgTitle, answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, s), answerMessage.getFeedback());
    }

    @Test
    void testTMMode(){
        arguments.setTaskMode(TaskMode.ARTWP);
        arguments.setTaskType(TaskType.DTM);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, null);
        String svgTitle = resourceBundle.getString(AnswerMessages.ACCEPTOR_Svgtitle.name());

        StringBuilder s = new StringBuilder();
        s.append(resourceBundle.getString(AnswerMessages.ACCEPTOR_Feedback.name()));
        s.append("\n").append(resourceBundle.getString(AnswerMessages.AUTOMATON_IsTuring.name())).append(" ").append(svgTitle);

        Assertions.assertEquals(svgTitle, answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, s), answerMessage.getFeedback());
    }

}

