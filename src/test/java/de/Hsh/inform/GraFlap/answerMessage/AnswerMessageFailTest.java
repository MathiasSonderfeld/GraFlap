package de.HsH.inform.GraFlap.answerMessage;

import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class AnswerMessageFailTest {
    private static Locale locale = Locale.ROOT;
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("GraFlapAnswerMessage", locale);
    private static String feedbackFormat = "%d %s";
    private static Arguments arguments = new Arguments();
    private static int numberOfWordsFailed = 50;
    private static Result result = new Result(new Submission(), numberOfWordsFailed, TaskType.NON);
    private org.jdom2.Element nullElement = null;

    @BeforeAll
    static void init(){
        arguments.setStudentAnswer("StudentAnswer");
        arguments.setSolution("Solution");
        arguments.setTaskTitle("TaskTitle");
        arguments.setTestwords(new Testwords(0,0));
        arguments.setNumberOfWords(123);
        arguments.setUserLanguage(locale);
    }

    @Test
    void testEnumMatchesRessourceBundle(){
        for(FeedbackMessage e : FeedbackMessage.values()){
            Assertions.assertDoesNotThrow(() -> resourceBundle.getString(e.toString()));
        }
    }

    @Test
    void testCYKMode(){
        arguments.setTaskMode(TaskMode.CYK);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.CYK_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, resourceBundle.getString(FeedbackMessage.CYK_Feedback.toString())), answerMessage.getFeedback());
    }

    @Test
    void testDERMode(){
        arguments.setTaskMode(TaskMode.DER);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.DERIVATION_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, resourceBundle.getString(FeedbackMessage.DERIVATION_Feedback.name())), answerMessage.getFeedback());
    }

    @Test
    void testWWMode(){
        arguments.setTaskMode(TaskMode.WW);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.WORD_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, resourceBundle.getString(FeedbackMessage.WORD_Feedback.name())), answerMessage.getFeedback());
    }

    @Test
    void testSVGMode(){
        arguments.setTaskMode(TaskMode.SVGA);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.SVG_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, resourceBundle.getString(FeedbackMessage.SVG_Feedback.name())), answerMessage.getFeedback());
    }

    @Test
    void testMMode(){
        arguments.setTaskMode(TaskMode.MP);
        arguments.setTaskType(TaskType.DTM);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        String svgTitle = resourceBundle.getString(FeedbackMessage.TRANSDUCER_Svgtitle.name());
        StringBuilder s = new StringBuilder();
        s.append(resourceBundle.getString(FeedbackMessage.TRANSDUCER_Feedback.name()));
        s.append("\n").append(resourceBundle.getString(FeedbackMessage.AUTOMATON_IsTuring.name())).append(" ").append(svgTitle);

        Assertions.assertEquals(svgTitle, answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, s), answerMessage.getFeedback());
    }

    @Test
    void testGRAMMARMode(){
        arguments.setTaskMode(TaskMode.GG);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);

        StringBuilder s = new StringBuilder();
        s.append(resourceBundle.getString(FeedbackMessage.GRAMMAR_Feedback.name()));
        s.append("\n").append(resourceBundle.getString(FeedbackMessage.GRAMMAR_Type.name()));

        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.GRAMMAR_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, s), answerMessage.getFeedback());
    }

    @Test
    void testAAMode(){
        arguments.setTaskMode(TaskMode.AA);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.ACCEPTOR_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, resourceBundle.getString(FeedbackMessage.ACCEPTOR_AAFeedback.name())), answerMessage.getFeedback());
    }

    @Test
    void testFAMode(){
        arguments.setTaskMode(TaskMode.ARTWP);
        arguments.setTaskType(TaskType.DFA);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        String svgTitle = resourceBundle.getString(FeedbackMessage.ACCEPTOR_Svgtitle.name());

        StringBuilder s = new StringBuilder();
        s.append(resourceBundle.getString(FeedbackMessage.ACCEPTOR_Feedback.name()));
        s.append("\n").append(resourceBundle.getString(FeedbackMessage.ACCEPTOR_FAFeedback.name()));

        Assertions.assertEquals(svgTitle, answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, s), answerMessage.getFeedback());
    }

    @Test
    void testPDAMode(){
        arguments.setTaskMode(TaskMode.ARTWP);
        arguments.setTaskType(TaskType.DPDA);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        String svgTitle = resourceBundle.getString(FeedbackMessage.ACCEPTOR_Svgtitle.name());

        StringBuilder s = new StringBuilder();
        s.append(resourceBundle.getString(FeedbackMessage.ACCEPTOR_Feedback.name()));
        s.append("\n").append(resourceBundle.getString(FeedbackMessage.ACCEPTOR_PDAFeedback.name()));

        Assertions.assertEquals(svgTitle, answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, s), answerMessage.getFeedback());
    }

    @Test
    void testTMMode(){
        arguments.setTaskMode(TaskMode.ARTWP);
        arguments.setTaskType(TaskType.DTM);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        String svgTitle = resourceBundle.getString(FeedbackMessage.ACCEPTOR_Svgtitle.name());

        StringBuilder s = new StringBuilder();
        s.append(resourceBundle.getString(FeedbackMessage.ACCEPTOR_Feedback.name()));
        s.append("\n").append(resourceBundle.getString(FeedbackMessage.AUTOMATON_IsTuring.name())).append(" ").append(svgTitle);

        Assertions.assertEquals(svgTitle, answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, s), answerMessage.getFeedback());
    }

}

