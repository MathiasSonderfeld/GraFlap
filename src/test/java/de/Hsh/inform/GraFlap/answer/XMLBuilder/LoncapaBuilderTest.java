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
public class LoncapaBuilderTest {
    LoncapaBuilder loncapaBuilder = new LoncapaBuilder();

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
        String xml = "<loncapagrade><awarddetail>EXACT_ANS</awarddetail><message><taskresult grade=\"passed\"><tasktitle>Test for LoncapaBuilder.getXML Result Success</tasktitle><titlesvg>SVGTitle</titlesvg><imagesvg><![CDATA[<thisIsATest />]]></imagesvg><resulttext>mocked test successfull</resulttext></taskresult></message></loncapagrade>";

        Assertions.assertEquals(xml, loncapaBuilder.getOutput(message).replaceAll("[\r|\n]\\s+", "").trim());
    }

    /**
     *  Tests if wrong Submission gets printed as such.
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
        String xml = "<loncapagrade><awarddetail>INCORRECT</awarddetail><message><taskresult grade=\"failed\"><tasktitle>Test for LoncapaBuilder.getXML Result Fail</tasktitle><titlesvg>SVGTitle</titlesvg><imagesvg><![CDATA[<thisIsATest />]]></imagesvg><resulttext>mocked test successfull</resulttext></taskresult></message></loncapagrade>";

        Assertions.assertEquals(xml, loncapaBuilder.getOutput(message).replaceAll("[\r|\n]\\s+", "").trim());
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

        Assertions.assertEquals(xml, loncapaBuilder.getOutput(message).replaceAll("[\r|\n]\\s+", "").trim());
    }

    /**
     * Checks if output values are empty as expected if values of answerMessage are all null/default.
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
        String xml = "<loncapagrade><awarddetail>INCORRECT</awarddetail><message><taskresult grade=\"failed\"><tasktitle /><titlesvg /><resulttext /></taskresult></message></loncapagrade>";

        Assertions.assertEquals(xml, loncapaBuilder.getOutput(message).replaceAll("[\r|\n]\\s+", "").trim());
    }

    /**
     * Checks if NullPointerException is correctly thrown if answerMessage is null
     */
    @Test
    void testGetXMLNull(){
        Assertions.assertThrows(NullPointerException.class, () ->  loncapaBuilder.getOutput(null));
    }
}