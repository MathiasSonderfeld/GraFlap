package de.HsH.inform.GraFlap.io.formatter;

import de.HsH.inform.GraFlap.answerMessage.AnswerMessage;
import de.HsH.inform.GraFlap.entity.TaskMode;
import org.jdom2.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Mathias Sonderfeld
 * Tests the ProformaOutputFormatter with 100% Code Coverage
 * Mocks answerMessage class to isolate Builder from Data conversions in AnswerMessage Implementations.
 */

public class ProformaOutputFormatterTest {
    ProformaOutputFormatter proformaFormatter = new ProformaOutputFormatter();

    /**
     * Tests if correct Submission gets printed as such.
     */
    @Test
    void testSuccess() {
        String taskTitle = "Test Success";
        String svgImageContent = "thisIsATest";
        String svgTitle = "SVGTitle";
        TaskMode taskMode = TaskMode.GG;
        String feedback = "mocked test successfull";
        double score = 1.0;


        AnswerMessage messageMock = mock(AnswerMessage.class);
        when(messageMock.getTaskTitle()).thenReturn(taskTitle);
        when(messageMock.getSvgImage()).thenReturn(new Element(svgImageContent));
        when(messageMock.getSvgTitle()).thenReturn(svgTitle);
        when(messageMock.getTaskMode()).thenReturn(taskMode);
        when(messageMock.getFeedback()).thenReturn(feedback);
        when(messageMock.getWarnings()).thenReturn("");
        when(messageMock.getScore()).thenReturn(score);

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><proforma:response xmlns:proforma=\"urn:proforma:v2.1\"><proforma:separate-test-feedback><proforma:submission-feedback-list/>" +
                "<proforma:tests-response><proforma:test-response id=\"" + taskTitle + "\"><proforma:test-result><proforma:result><proforma:score>" + score + "</proforma:score><proforma:validity>1" +
                ".0</proforma:validity></proforma:result><proforma:feedback-list>" +
                "<proforma:student-feedback><proforma:title>TaskTitle</proforma:title><proforma:content format=\"plaintext\">" + taskTitle +
                "</proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>SvgTitle</proforma:title><proforma:content format=\"plaintext\">" + svgTitle +
                "</proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>SvgImage</proforma:title><proforma:content format=\"plaintext\"><![CDATA[<" + svgImageContent +
                " />]]></proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>FeedbackText</proforma:title><proforma:content format=\"plaintext\">" + feedback +
                "</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response></proforma:tests-response></proforma:separate-test-feedback><proforma:files/>" +
                "<proforma:response-meta-data><proforma:grader-engine name=\"GraFlap\" version=\"0.3\"/></proforma:response-meta-data></proforma:response>";

        Assertions.assertEquals(xml.replaceAll("\\s+", "").trim(), proformaFormatter.format(messageMock).replaceAll("\\s+", "").trim());
    }

    @Test
    void testFeedbackSuccess() {
        String taskTitle = "Test Success";
        String svgImageContent = "thisIsATest";
        String svgTitle = "SVGTitle";
        TaskMode taskMode = TaskMode.GG;
        String feedback = "mocked test successfull";
        String warning = "this is a warning";
        double score = 1.0;


        AnswerMessage messageMock = mock(AnswerMessage.class);
        when(messageMock.getTaskTitle()).thenReturn(taskTitle);
        when(messageMock.getSvgImage()).thenReturn(new Element(svgImageContent));
        when(messageMock.getSvgTitle()).thenReturn(svgTitle);
        when(messageMock.getTaskMode()).thenReturn(taskMode);
        when(messageMock.getFeedback()).thenReturn(feedback);
        when(messageMock.getWarnings()).thenReturn(warning);
        when(messageMock.getScore()).thenReturn(score);

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><proforma:response xmlns:proforma=\"urn:proforma:v2.1\"><proforma:separate-test-feedback><proforma:submission-feedback-list/>" +
                "<proforma:tests-response><proforma:test-response id=\"" + taskTitle + "\"><proforma:test-result><proforma:result><proforma:score>" + score + "</proforma:score><proforma:validity>1" +
                ".0</proforma:validity></proforma:result><proforma:feedback-list>" +
                "<proforma:student-feedback><proforma:title>TaskTitle</proforma:title><proforma:content format=\"plaintext\">" + taskTitle +
                "</proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>SvgTitle</proforma:title><proforma:content format=\"plaintext\">" + svgTitle +
                "</proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>SvgImage</proforma:title><proforma:content format=\"plaintext\"><![CDATA[<" + svgImageContent +
                " />]]></proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>FeedbackText</proforma:title><proforma:content format=\"plaintext\">" + feedback +
                "</proforma:content></proforma:student-feedback><proforma:teacher-feedback><proforma:title>Warnings</proforma:title><proforma:contentformat=\"plaintext\">" + warning + "</proforma" +
                ":content></proforma:teacher-feedback>" +
                "</proforma:feedback-list></proforma:test-result></proforma:test-response></proforma:tests-response></proforma:separate-test-feedback><proforma:files/>" +
                "<proforma:response-meta-data><proforma:grader-engine name=\"GraFlap\" version=\"0.3\"/></proforma:response-meta-data></proforma:response>";

        Assertions.assertEquals(xml.replaceAll("\\s+", "").trim(), proformaFormatter.format(messageMock).replaceAll("\\s+", "").trim());
    }

    @Test
    void testSetsSuccess() {
        String taskTitle = "Test Success with Sets";
        String svgImageContent = "thisIsATest";
        String svgTitle = "SVGTitle";
        TaskMode taskMode = TaskMode.GG;
        String feedback = "mocked test successfull";
        boolean passed = true;
        double score = 1.0;
        double setsScore = 0.5;
        String setsTeacherFeedback = "mocked Teacherfeedback";
        String setsStudentFeedback = "studentFeedback mocked";

        AnswerMessage messageMock = mock(AnswerMessage.class);
        when(messageMock.getTaskTitle()).thenReturn(taskTitle);
        when(messageMock.getSvgImage()).thenReturn(new Element(svgImageContent));
        when(messageMock.getSvgTitle()).thenReturn(svgTitle);
        when(messageMock.getTaskMode()).thenReturn(taskMode);
        when(messageMock.getFeedback()).thenReturn(feedback);
        when(messageMock.getScore()).thenReturn(score);
        when(messageMock.getStatesScore()).thenReturn(setsScore);
        when(messageMock.getInitialsScore()).thenReturn(setsScore);
        when(messageMock.getFinalsScore()).thenReturn(setsScore);
        when(messageMock.getAlphabetScore()).thenReturn(setsScore);
        when(messageMock.getTransitionsScore()).thenReturn(setsScore);
        when(messageMock.getStackAlphabetScore()).thenReturn(setsScore);
        when(messageMock.getStatesTeacherFeedback()).thenReturn(setsTeacherFeedback);
        when(messageMock.getInitialsTeacherFeedback()).thenReturn(setsTeacherFeedback);
        when(messageMock.getFinalsTeacherFeedback()).thenReturn(setsTeacherFeedback);
        when(messageMock.getAlphabetTeacherFeedback()).thenReturn(setsTeacherFeedback);
        when(messageMock.getStackAlphabetTeacherFeedback()).thenReturn(setsTeacherFeedback);
        when(messageMock.getTransitionsTeacherFeedback()).thenReturn(setsTeacherFeedback);
        when(messageMock.getStatesStudentFeedback()).thenReturn(setsStudentFeedback);
        when(messageMock.getInitialsStudentFeedback()).thenReturn(setsStudentFeedback);
        when(messageMock.getFinalsStudentFeedback()).thenReturn(setsStudentFeedback);
        when(messageMock.getAlphabetStudentFeedback()).thenReturn(setsStudentFeedback);
        when(messageMock.getStackAlphabetStudentFeedback()).thenReturn(setsStudentFeedback);
        when(messageMock.getTransitionsStudentFeedback()).thenReturn(setsStudentFeedback);

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><proforma:response xmlns:proforma=\"urn:proforma:v2.1\"><proforma:separate-test-feedback><proforma:submission-feedback-list/>" +
                "<proforma:tests-response><proforma:test-response id=\"" + taskTitle + "\"><proforma:test-result><proforma:result><proforma:score>" + score + "</proforma:score><proforma:validity>1" +
                ".0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback><proforma:title>Musterloesung</proforma:title><proforma:content format=\"plaintext\"/></proforma:teacher-feedback>" +
                "<proforma:student-feedback><proforma:title>TaskTitle</proforma:title><proforma:content format=\"plaintext\">" + taskTitle +
                "</proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>SvgTitle</proforma:title><proforma:content format=\"plaintext\">" + svgTitle +
                "</proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>SvgImage</proforma:title><proforma:content format=\"plaintext\"><![CDATA[<" + svgImageContent +
                " />]]></proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>FeedbackText</proforma:title><proforma:content format=\"plaintext\">" + feedback +
                "</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response><proforma:test-response id=\"states\"><proforma:test-result>" +
                "<proforma:result><proforma:score>0.5</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback><proforma:title>Feedback</proforma:title><proforma:content format=\"plaintext\">\" + setsTeacherFeedback + \"</proforma:content></proforma:teacher-feedback><proforma:student-feedback><proforma:title>Feedback</proforma:title><proforma:content format=\"plaintext\">\" + setsStudentFeedback + \"</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response><proforma:test-response id=\"initials\"><proforma:test-result><proforma:result><proforma:score>0.5</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback><proforma:title>Feedback</proforma:title><proforma:content format=\"plaintext\">\" + setsTeacherFeedback + \"</proforma:content></proforma:teacher-feedback><proforma:student-feedback><proforma:title>Feedback</proforma:title><proforma:content format=\"plaintext\">\" + setsStudentFeedback + \"</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response><proforma:test-response id=\"finals\"><proforma:test-result><proforma:result><proforma:score>0.5</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback><proforma:title>Feedback</proforma:title><proforma:content format=\"plaintext\">\" + setsTeacherFeedback + \"</proforma:content></proforma:teacher-feedback><proforma:student-feedback><proforma:title>Feedback</proforma:title><proforma:content format=\"plaintext\">\" + setsStudentFeedback + \"</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response><proforma:test-response id=\"transitions\"><proforma:test-result><proforma:result><proforma:score>0.5</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback><proforma:title>Feedback</proforma:title><proforma:content format=\"plaintext\">\" + setsTeacherFeedback + \"</proforma:content></proforma:teacher-feedback><proforma:student-feedback><proforma:title>Feedback</proforma:title><proforma:content format=\"plaintext\">\" + setsStudentFeedback + \"</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response><proforma:test-response id=\"alphabet\"><proforma:test-result><proforma:result><proforma:score>0.5</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback><proforma:title>Feedback</proforma:title><proforma:content format=\"plaintext\">\" + setsTeacherFeedback + \"</proforma:content></proforma:teacher-feedback><proforma:student-feedback><proforma:title>Feedback</proforma:title><proforma:content format=\"plaintext\">\" + setsStudentFeedback + \"</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response><proforma:test-response id=\"stackalphabet\"><proforma:test-result><proforma:result><proforma:score>0.5</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback><proforma:title>Feedback</proforma:title><proforma:content format=\"plaintext\">\" + setsTeacherFeedback + \"</proforma:content></proforma:teacher-feedback><proforma:student-feedback><proforma:title>Feedback</proforma:title><proforma:content format=\"plaintext\">\" + setsStudentFeedback + \"</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response></proforma:tests-response></proforma:separate-test-feedback><proforma:files/>" +
                "<proforma:response-meta-data><proforma:grader-engine name=\"GraFlap\" version=\"0.3\"/></proforma:response-meta-data></proforma:response>";
    }

    /**
     * Tests if wrong Submission gets printed as such.
     */
    @Test
    void testGetXMLResultFail() {
        AnswerMessage message = mock(AnswerMessage.class);
        when(message.getTaskTitle()).thenReturn("Test for LoncapaBuilder.getXML Result Fail");
        when(message.getSvgImage()).thenReturn(new Element("thisIsATest"));
        when(message.getSvgTitle()).thenReturn("SVGTitle");
        when(message.getTaskMode()).thenReturn(TaskMode.GG);
        when(message.getFeedback()).thenReturn("mocked test successfull");
        when(message.getWarnings()).thenReturn("");
        when(message.hasPassed()).thenReturn(false);
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><proforma:response xmlns:proforma=\"urn:proforma:v2.1\"><proforma:separate-test-feedback><proforma:submission-feedback-list/><proforma:tests-response><proforma:test-response id=\"Test for LoncapaBuilder.getXML Result Fail\"><proforma:test-result><proforma:result><proforma:score>0.0</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:student-feedback><proforma:title>TaskTitle</proforma:title><proforma:content format=\"plaintext\">Test for LoncapaBuilder.getXML Result Fail</proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>SvgTitle</proforma:title><proforma:content format=\"plaintext\">SVGTitle</proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>SvgImage</proforma:title><proforma:content format=\"plaintext\"><![CDATA[<thisIsATest />]]></proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>FeedbackText</proforma:title><proforma:content format=\"plaintext\">mocked test successfull</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response></proforma:tests-response></proforma:separate-test-feedback><proforma:files/><proforma:response-meta-data><proforma:grader-engine name=\"GraFlap\" version=\"0.3\"/></proforma:response-meta-data></proforma:response>";

        Assertions.assertEquals(xml.replaceAll("\\s+", "").trim(), proformaFormatter.format(message).replaceAll("\\s+", "").trim());
    }

    /**
     * Checks if mode is set to asvg, just the SVG Content is returned. This later gets embedded as CDATA in response.
     */
    @Test
    void testGetXMLResultSVG() {
        AnswerMessage message = mock(AnswerMessage.class);
        when(message.getSvgImage()).thenReturn(new Element("thisIsATest"));
        when(message.getTaskMode()).thenReturn(TaskMode.SVGA);
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><thisIsATest/>";

        Assertions.assertEquals(xml.replaceAll("\\s+", "").trim(), proformaFormatter.format(message).replaceAll("\\s+", "").trim());
    }

    /**
     * Checks if NullPointerException is correctly thrown if values of answerMessage are all null/default.
     */
    @Test
    void testGetXMLAnswerMessageNull() {
        AnswerMessage message = mock(AnswerMessage.class);
        when(message.getTaskTitle()).thenReturn(null);
        when(message.getSvgImage()).thenReturn(null);
        when(message.getSvgTitle()).thenReturn(null);
        when(message.getTaskMode()).thenReturn(null);
        when(message.getFeedback()).thenReturn(null);
        when(message.hasPassed()).thenReturn(false);
        Assertions.assertThrows(NullPointerException.class, () -> proformaFormatter.format(message));
    }

    /**
     * Checks if NullPointerException is correctly thrown if answerMessage is null
     */
    @Test
    void testGetXMLNull() {
        Assertions.assertThrows(NullPointerException.class, () -> proformaFormatter.format(null));
    }
}
