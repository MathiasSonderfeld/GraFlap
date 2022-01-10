package de.HsH.inform.GraFlap.io.formatter;

import de.HsH.inform.GraFlap.answerMessage.AnswerMessage;
import de.HsH.inform.GraFlap.entity.MetaData;
import de.HsH.inform.GraFlap.entity.TaskMode;
import de.HsH.inform.GraFlap.entity.TaskType;
import org.junit.jupiter.api.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Mathias Sonderfeld
 * Tests the ProformaOutputFormatter with 100% Code Coverage
 * Mocks answerMessage class to isolate Builder from Data conversions in AnswerMessage Implementations.
 */

public class ProformaOutputFormatterTest {
    ProformaOutputFormatter proformaFormatter = new ProformaOutputFormatter();
    static MetaData metaData = new MetaData();

    @BeforeAll
    static void init(){
        metaData.setTestID("graflap");
    }

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
        String gradingtime = "Grading took 2 ms";
        String extra = "";

        AnswerMessage messageMock = mock(AnswerMessage.class);
        when(messageMock.getTaskTitle()).thenReturn(taskTitle);
        when(messageMock.getSvgImage()).thenReturn("<" + svgImageContent +"/>");
        when(messageMock.getSvgTitle()).thenReturn(svgTitle);
        when(messageMock.getTaskMode()).thenReturn(taskMode);
        when(messageMock.getFeedback()).thenReturn(feedback);
        when(messageMock.getWarnings()).thenReturn("");
        when(messageMock.getScore()).thenReturn(score);
        when(messageMock.getTime()).thenReturn(gradingtime);
        when(messageMock.getTeachersExtra()).thenReturn(extra);

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><proforma:response xmlns:proforma=\"urn:proforma:v2.1\"><proforma:separate-test-feedback><proforma:submission-feedback-list/><proforma:tests-response><proforma:test-response id=\"graflap\"><proforma:test-result><proforma:result><proforma:score>1.0</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback level=\"info\"><proforma:title>Time</proforma:title><proforma:content format=\"html\">Grading took 2 ms</proforma:content></proforma:teacher-feedback><proforma:student-feedback level=\"info\"><proforma:title>SVGTitle</proforma:title><proforma:content format=\"html\"><![CDATA[<thisIsATest/>]]></proforma:content></proforma:student-feedback><proforma:student-feedback level=\"info\"><proforma:title/><proforma:content format=\"html\"><![CDATA[mocked test successfull]]></proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response></proforma:tests-response></proforma:separate-test-feedback><proforma:files/><proforma:response-meta-data><proforma:grader-engine name=\"GraFlap\" version=\"\"/><proforma:grade-date datetime=\"\"/></proforma:response-meta-data></proforma:response>";
        Assertions.assertEquals(xml, Filter.filter(proformaFormatter.format(messageMock, ProformaOutputFormatterTest.metaData)));
    }

    @Test
    void testFeedbackSuccess() {
        String taskTitle = "Test Success";
        String svgImageContent = "thisIsATest";
        String svgTitle = "SVGTitle";
        TaskMode taskMode = TaskMode.GG;
        String feedback = "mocked test successfull";
        String warning = "this is a warning";
        String extra = "";
        double score = 1.0;
        String gradingtime = "Grading took 2 ms";


        AnswerMessage messageMock = mock(AnswerMessage.class);
        when(messageMock.getTaskTitle()).thenReturn(taskTitle);
        when(messageMock.getSvgImage()).thenReturn("<" + svgImageContent +"/>");
        when(messageMock.getSvgTitle()).thenReturn(svgTitle);
        when(messageMock.getTaskMode()).thenReturn(taskMode);
        when(messageMock.getFeedback()).thenReturn(feedback);
        when(messageMock.getWarnings()).thenReturn(warning);
        when(messageMock.getScore()).thenReturn(score);
        when(messageMock.getTime()).thenReturn(gradingtime);
        when(messageMock.getTeachersExtra()).thenReturn(extra);

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><proforma:response xmlns:proforma=\"urn:proforma:v2.1\"><proforma:separate-test-feedback><proforma:submission-feedback-list/><proforma:tests-response><proforma:test-response id=\"graflap\"><proforma:test-result><proforma:result><proforma:score>1.0</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback level=\"info\"><proforma:title>Time</proforma:title><proforma:content format=\"html\">Grading took 2 ms</proforma:content></proforma:teacher-feedback><proforma:student-feedback level=\"info\"><proforma:title>SVGTitle</proforma:title><proforma:content format=\"html\"><![CDATA[<thisIsATest/>]]></proforma:content></proforma:student-feedback><proforma:student-feedback level=\"info\"><proforma:title/><proforma:content format=\"html\"><![CDATA[mocked test successfull]]></proforma:content></proforma:student-feedback><proforma:teacher-feedback level=\"info\"><proforma:title>Warnings</proforma:title><proforma:content format=\"html\"><![CDATA[this is a warning]]></proforma:content></proforma:teacher-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response></proforma:tests-response></proforma:separate-test-feedback><proforma:files/><proforma:response-meta-data><proforma:grader-engine name=\"GraFlap\" version=\"\"/><proforma:grade-date datetime=\"\"/></proforma:response-meta-data></proforma:response>";

        Assertions.assertEquals(xml, Filter.filter(proformaFormatter.format(messageMock, ProformaOutputFormatterTest.metaData)));
    }

    @Test
    void testSetsSuccess() {
        String taskTitle = "Test Success with Sets";
        String svgImageContent = "thisIsATest";
        String svgTitle = "SVGTitle";
        TaskMode taskMode = TaskMode.ARP;
        TaskType taskType = TaskType.PDA;
        String feedback = "mocked test successfull";
        boolean passed = true;
        double score = 1.0;
        double setsScore = 0.5;
        String gradingtime = "Grading took 2 ms";
        String setsTeacherFeedback = "mocked Teacherfeedback";
        String setsStudentFeedback = "studentFeedback mocked";
        String warnings = "mocked warnings";
        String extra = "";

        AnswerMessage messageMock = mock(AnswerMessage.class);
        when(messageMock.getTaskTitle()).thenReturn(taskTitle);
        when(messageMock.getSvgImage()).thenReturn("<" + svgImageContent +"/>");
        when(messageMock.getSvgTitle()).thenReturn(svgTitle);
        when(messageMock.getTaskMode()).thenReturn(taskMode);
        when(messageMock.getTaskType()).thenReturn(taskType);
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
        when(messageMock.getWarnings()).thenReturn(warnings);
        when(messageMock.getTime()).thenReturn(gradingtime);
        when(messageMock.getTeachersExtra()).thenReturn(extra);

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><proforma:response xmlns:proforma=\"urn:proforma:v2.1\"><proforma:separate-test-feedback><proforma:submission-feedback-list/><proforma:tests-response><proforma:test-response id=\"graflap\"><proforma:test-result><proforma:result><proforma:score>1.0</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback level=\"info\"><proforma:title>Time</proforma:title><proforma:content format=\"html\">Grading took 2 ms</proforma:content></proforma:teacher-feedback><proforma:student-feedback level=\"info\"><proforma:title>SVGTitle</proforma:title><proforma:content format=\"html\"><![CDATA[<thisIsATest/>]]></proforma:content></proforma:student-feedback><proforma:student-feedback level=\"info\"><proforma:title/><proforma:content format=\"html\"><![CDATA[mocked test successfull]]></proforma:content></proforma:student-feedback><proforma:teacher-feedback level=\"info\"><proforma:title>Warnings</proforma:title><proforma:content format=\"html\"><![CDATA[mocked warnings]]></proforma:content></proforma:teacher-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response><proforma:test-response id=\"states\"><proforma:test-result><proforma:result><proforma:score>0.5</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback level=\"info\"><proforma:title>Feedback</proforma:title><proforma:content format=\"html\">mocked Teacherfeedback</proforma:content></proforma:teacher-feedback><proforma:student-feedback level=\"info\"><proforma:title>Feedback</proforma:title><proforma:content format=\"html\">studentFeedback mocked</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response><proforma:test-response id=\"initials\"><proforma:test-result><proforma:result><proforma:score>0.5</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback level=\"info\"><proforma:title>Feedback</proforma:title><proforma:content format=\"html\">mocked Teacherfeedback</proforma:content></proforma:teacher-feedback><proforma:student-feedback level=\"info\"><proforma:title>Feedback</proforma:title><proforma:content format=\"html\">studentFeedback mocked</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response><proforma:test-response id=\"finals\"><proforma:test-result><proforma:result><proforma:score>0.5</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback level=\"info\"><proforma:title>Feedback</proforma:title><proforma:content format=\"html\">mocked Teacherfeedback</proforma:content></proforma:teacher-feedback><proforma:student-feedback level=\"info\"><proforma:title>Feedback</proforma:title><proforma:content format=\"html\">studentFeedback mocked</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response><proforma:test-response id=\"transitions\"><proforma:test-result><proforma:result><proforma:score>0.5</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback level=\"info\"><proforma:title>Feedback</proforma:title><proforma:content format=\"html\">mocked Teacherfeedback</proforma:content></proforma:teacher-feedback><proforma:student-feedback level=\"info\"><proforma:title>Feedback</proforma:title><proforma:content format=\"html\">studentFeedback mocked</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response><proforma:test-response id=\"alphabet\"><proforma:test-result><proforma:result><proforma:score>0.5</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback level=\"info\"><proforma:title>Feedback</proforma:title><proforma:content format=\"html\">mocked Teacherfeedback</proforma:content></proforma:teacher-feedback><proforma:student-feedback level=\"info\"><proforma:title>Feedback</proforma:title><proforma:content format=\"html\">studentFeedback mocked</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response><proforma:test-response id=\"stackalphabet\"><proforma:test-result><proforma:result><proforma:score>0.5</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback level=\"info\"><proforma:title>Feedback</proforma:title><proforma:content format=\"html\">mocked Teacherfeedback</proforma:content></proforma:teacher-feedback><proforma:student-feedback level=\"info\"><proforma:title>Feedback</proforma:title><proforma:content format=\"html\">studentFeedback mocked</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response></proforma:tests-response></proforma:separate-test-feedback><proforma:files/><proforma:response-meta-data><proforma:grader-engine name=\"GraFlap\" version=\"\"/><proforma:grade-date datetime=\"\"/></proforma:response-meta-data></proforma:response>";

        Assertions.assertEquals(xml, Filter.filter(proformaFormatter.format(messageMock, ProformaOutputFormatterTest.metaData)));
    }

    /**
     * Tests if wrong Submission gets printed as such.
     */
    @Test
    void testGetXMLResultFail() {
        String gradingtime = "Grading took 2 ms";
        String extra = "";

        AnswerMessage messageMock = mock(AnswerMessage.class);
        when(messageMock.getTaskTitle()).thenReturn("Test for LoncapaBuilder.getXML Result Fail");
        when(messageMock.getSvgImage()).thenReturn("<thisIsATest/>");
        when(messageMock.getSvgTitle()).thenReturn("SVGTitle");
        when(messageMock.getTaskMode()).thenReturn(TaskMode.GG);
        when(messageMock.getFeedback()).thenReturn("mocked test successfull");
        when(messageMock.getWarnings()).thenReturn("");
        when(messageMock.hasPassed()).thenReturn(false);
        when(messageMock.getTime()).thenReturn(gradingtime);
        when(messageMock.getTeachersExtra()).thenReturn(extra);

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><proforma:response xmlns:proforma=\"urn:proforma:v2.1\"><proforma:separate-test-feedback><proforma:submission-feedback-list/><proforma:tests-response><proforma:test-response id=\"graflap\"><proforma:test-result><proforma:result><proforma:score>0.0</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:teacher-feedback level=\"info\"><proforma:title>Time</proforma:title><proforma:content format=\"html\">Grading took 2 ms</proforma:content></proforma:teacher-feedback><proforma:student-feedback level=\"info\"><proforma:title>SVGTitle</proforma:title><proforma:content format=\"html\"><![CDATA[<thisIsATest/>]]></proforma:content></proforma:student-feedback><proforma:student-feedback level=\"info\"><proforma:title/><proforma:content format=\"html\"><![CDATA[mocked test successfull]]></proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response></proforma:tests-response></proforma:separate-test-feedback><proforma:files/><proforma:response-meta-data><proforma:grader-engine name=\"GraFlap\" version=\"\"/><proforma:grade-date datetime=\"\"/></proforma:response-meta-data></proforma:response>";

        Assertions.assertEquals(Filter.filter(xml), Filter.filter(proformaFormatter.format(messageMock, ProformaOutputFormatterTest.metaData)));
    }

    /**
     * Checks if mode is set to asvg, just the SVG Content is returned. This later gets embedded as CDATA in response.
     */
    @Test
    void testGetXMLResultSVG() {

        String extra = "";
        AnswerMessage messageMock = mock(AnswerMessage.class);
        when(messageMock.getSvgImage()).thenReturn("<svgimagecontent/>");
        when(messageMock.getTaskMode()).thenReturn(TaskMode.SVGA);
        when(messageMock.getTeachersExtra()).thenReturn(extra);

        String xml = "<svgimagecontent/>";

        Assertions.assertEquals(Filter.filter(xml), Filter.filter(proformaFormatter.format(messageMock, ProformaOutputFormatterTest.metaData)));
    }

    /**
     * Checks if NullPointerException is correctly thrown if values of answerMessage are all null/default.
     */
    @Test
    void testGetXMLAnswerMessageNull() {
        AnswerMessage messageMock = mock(AnswerMessage.class);
        when(messageMock.getTaskTitle()).thenReturn(null);
        when(messageMock.getSvgImage()).thenReturn(null);
        when(messageMock.getSvgTitle()).thenReturn(null);
        when(messageMock.getTaskMode()).thenReturn(null);
        when(messageMock.getFeedback()).thenReturn(null);
        when(messageMock.hasPassed()).thenReturn(false);
        Assertions.assertThrows(NullPointerException.class, () -> proformaFormatter.format(messageMock, ProformaOutputFormatterTest.metaData));
    }

    /**
     * Checks if NullPointerException is correctly thrown if answerMessage is null
     */
    @Test
    void testGetXMLNull() {
        Assertions.assertThrows(NullPointerException.class, () -> proformaFormatter.format(null, null));
    }
}
