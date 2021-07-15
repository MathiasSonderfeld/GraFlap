package de.HsH.inform.GraFlap.answerMessage;

import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.entity.*;
import org.jdom2.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class AnswerMessageTypeTest {
    private static Locale locale = Locale.ROOT;
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("GraFlapAnswerMessage", locale);
    private static String feedbackFormat = "%d %s";
    private static Arguments arguments = new Arguments();
    private static int numberOfWordsFailed = 50;
    private static Submission submission = new Submission();
    private static Result result;
    private org.jdom2.Element nullElement = new Element("null");;

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
    void testRLCFG(){
        arguments.setTaskMode(TaskMode.GGT);
        arguments.setTaskType(TaskType.RLCFG);
        result = new Result(submission, numberOfWordsFailed, TaskType.CFG);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);

        StringBuilder s = new StringBuilder();
        s.append(resourceBundle.getString(FeedbackMessage.GRAMMAR_Feedback.name()));

        Assertions.assertEquals(resourceBundle.getString(FeedbackMessage.GRAMMAR_Svgtitle.name()), answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, s), answerMessage.getFeedback());
    }

    @Test
    void testDFAvsNFA(){
        arguments.setTaskMode(TaskMode.ARTWP);
        arguments.setTaskType(TaskType.NFA);
        result = new Result(submission, numberOfWordsFailed, TaskType.DFA);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        String svgTitle = resourceBundle.getString(FeedbackMessage.ACCEPTOR_Svgtitle.name());

        StringBuilder s = new StringBuilder();
        s.append(resourceBundle.getString(FeedbackMessage.ACCEPTOR_Feedback.name()));
        s.append("\n").append(resourceBundle.getString(FeedbackMessage.AUTOMATON_MatchesNotDeterministic.name()));

        Assertions.assertEquals(svgTitle, answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, s), answerMessage.getFeedback());
    }

    @Test
    void testNFAvsDFA(){
        arguments.setTaskMode(TaskMode.ARTWP);
        arguments.setTaskType(TaskType.DFA);
        result = new Result(submission, numberOfWordsFailed, TaskType.NFA);
        AnswerMessage answerMessage = new AnswerMessage(result, arguments, nullElement);
        String svgTitle = resourceBundle.getString(FeedbackMessage.ACCEPTOR_Svgtitle.name());

        StringBuilder s = new StringBuilder();
        s.append(resourceBundle.getString(FeedbackMessage.ACCEPTOR_Feedback.name()));
        s.append("\n").append(resourceBundle.getString(FeedbackMessage.AUTOMATON_MatchesDeterministic.name()));

        Assertions.assertEquals(svgTitle, answerMessage.getSvgTitle());
        Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, s), answerMessage.getFeedback());
    }
}

