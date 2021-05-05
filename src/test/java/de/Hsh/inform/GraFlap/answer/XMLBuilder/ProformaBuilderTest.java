package de.HsH.inform.GraFlap.answer.XMLBuilder;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import org.jdom2.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Mathias Sonderfeld
 * Tests the ProformaBuilder with 100% Code Coverage
 * Mocks answerMessage class to isolate Builder from Data conversions in AnswerMessage Implementations.
 */
public class ProformaBuilderTest {
    ProformaBuilder proformaBuilder = new ProformaBuilder();

    /**
     * Tests if correct Submission gets printed as such.
     */
    @Test
    void testGetXMLResultSuccess(){
        AnswerMessage message = mock(AnswerMessage.class);
        when(message.getTaskTitle()).thenReturn("Test for LoncapaBuilder.getXML Result Success");
        when(message.getSvgImage()).thenReturn(new Element("thisIsATest"));
        when(message.getSvgTitle()).thenReturn("SVGTitle");
        when(message.getTaskMode()).thenReturn("cfg");
        when(message.getResultText()).thenReturn("mocked test successfull");
        when(message.hasPassed()).thenReturn(true);
        String xml = "<proforma:response xmlns:proforma=\"urn:proforma:v2.1\"><proforma:separate-test-feedback><proforma:submission-feedback-list /><proforma:tests-response><proforma:test-response id=\"Test for LoncapaBuilder.getXML Result Success\"><proforma:test-result><proforma:result><proforma:score>1.0</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:student-feedback><proforma:title>TaskTitle</proforma:title><proforma:content format=\"plaintext\">Test for LoncapaBuilder.getXML Result Success</proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>SvgTitle</proforma:title><proforma:content format=\"plaintext\">SVGTitle</proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>SvgImage</proforma:title><proforma:content format=\"html\" /></proforma:student-feedback><proforma:student-feedback><proforma:title>ResultText</proforma:title><proforma:content format=\"plaintext\">mocked test successfull</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response></proforma:tests-response></proforma:separate-test-feedback><proforma:files /><proforma:response-meta-data><proforma:grader-engine name=\"de.HsH.inform.GraFlap.GraFlap\" version=\"0.1\" /></proforma:response-meta-data></proforma:response>";

        Assertions.assertEquals(xml, proformaBuilder.getOutput(message).replaceAll("[\r|\n]\\s+", "").trim());
    }

    /**
     *  Tests if wrong Submission gets printed as such.
     *  TODO Redundant Test?
     */
    @Test
    void testGetXMLResultFail(){
        AnswerMessage message = mock(AnswerMessage.class);
        when(message.getTaskTitle()).thenReturn("Test for LoncapaBuilder.getXML Result Fail");
        when(message.getSvgImage()).thenReturn(new Element("thisIsATest"));
        when(message.getSvgTitle()).thenReturn("SVGTitle");
        when(message.getTaskMode()).thenReturn("cfg");
        when(message.getResultText()).thenReturn("mocked test successfull");
        when(message.hasPassed()).thenReturn(false);
        String xml = "<proforma:response xmlns:proforma=\"urn:proforma:v2.1\"><proforma:separate-test-feedback><proforma:submission-feedback-list /><proforma:tests-response><proforma:test-response id=\"Test for LoncapaBuilder.getXML Result Fail\"><proforma:test-result><proforma:result><proforma:score>1.0</proforma:score><proforma:validity>1.0</proforma:validity></proforma:result><proforma:feedback-list><proforma:student-feedback><proforma:title>TaskTitle</proforma:title><proforma:content format=\"plaintext\">Test for LoncapaBuilder.getXML Result Fail</proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>SvgTitle</proforma:title><proforma:content format=\"plaintext\">SVGTitle</proforma:content></proforma:student-feedback><proforma:student-feedback><proforma:title>SvgImage</proforma:title><proforma:content format=\"html\" /></proforma:student-feedback><proforma:student-feedback><proforma:title>ResultText</proforma:title><proforma:content format=\"plaintext\">mocked test successfull</proforma:content></proforma:student-feedback></proforma:feedback-list></proforma:test-result></proforma:test-response></proforma:tests-response></proforma:separate-test-feedback><proforma:files /><proforma:response-meta-data><proforma:grader-engine name=\"de.HsH.inform.GraFlap.GraFlap\" version=\"0.1\" /></proforma:response-meta-data></proforma:response>";

        Assertions.assertEquals(xml, proformaBuilder.getOutput(message).replaceAll("[\r|\n]\\s+", "").trim());
    }

    /**
     * Checks if mode is set to asvg, just the SVG Content is returned. This later gets embedded as CDATA in response.
     */
    @Test
    void testGetXMLResultSVG(){
        AnswerMessage message = mock(AnswerMessage.class);
        when(message.getSvgImage()).thenReturn(new Element("thisIsATest"));
        when(message.getTaskMode()).thenReturn("asvg");
        String xml = "<thisIsATest />";

        Assertions.assertEquals(xml, proformaBuilder.getOutput(message).replaceAll("[\r|\n]\\s+", "").trim());
    }

    /**
     * Checks if NullPointerException is correctly thrown if values of answerMessage are all null/default.
     */
    @Test
    void testGetXMLAnswerMessageNull(){
        AnswerMessage message = mock(AnswerMessage.class);
        when(message.getTaskTitle()).thenReturn(null);
        when(message.getSvgImage()).thenReturn(null);
        when(message.getSvgTitle()).thenReturn(null);
        when(message.getTaskMode()).thenReturn(null);
        when(message.getResultText()).thenReturn(null);
        when(message.hasPassed()).thenReturn(false);
        Assertions.assertThrows(NullPointerException.class, () ->  proformaBuilder.getOutput(message));
    }

    /**
     * Checks if NullPointerException is correctly thrown if answerMessage is null
     */
    @Test
    void testGetXMLNull(){
        Assertions.assertThrows(NullPointerException.class, () ->  proformaBuilder.getOutput(null));
    }
}
