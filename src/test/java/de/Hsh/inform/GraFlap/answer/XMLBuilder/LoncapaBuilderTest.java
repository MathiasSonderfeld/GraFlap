package de.Hsh.inform.GraFlap.answer.XMLBuilder;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.grammar.GrammarAnswerMessage;
import de.HsH.inform.GraFlap.answer.XMLBuilder.LoncapaBuilder;
import org.jdom2.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class LoncapaBuilderTest {
    LoncapaBuilder loncapaBuilder = new LoncapaBuilder();

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

        Assertions.assertEquals(xml, loncapaBuilder.getXML(message).replaceAll("[\r|\n]\\s+", "").trim());
    }

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

        Assertions.assertEquals(xml, loncapaBuilder.getXML(message).replaceAll("[\r|\n]\\s+", "").trim());
    }

    @Test
    void testGetXMLResultSVG(){
        AnswerMessage message = mock(AnswerMessage.class);
        when(message.getSvgImage()).thenReturn(new Element("thisIsATest"));
        when(message.getTaskMode()).thenReturn("asvg");
        String xml = "<thisIsATest />";

        Assertions.assertEquals(xml, loncapaBuilder.getXML(message).replaceAll("[\r|\n]\\s+", "").trim());
    }

    @Test
    void testGetXMLNull(){
        AnswerMessage message = mock(AnswerMessage.class);
        when(message.getTaskTitle()).thenReturn(null);
        when(message.getSvgImage()).thenReturn(null);
        when(message.getSvgTitle()).thenReturn(null);
        when(message.getTaskMode()).thenReturn(null);
        when(message.getResultText()).thenReturn(null);
        when(message.hasPassed()).thenReturn(false);
        String xml = "<loncapagrade><awarddetail>INCORRECT</awarddetail><message><taskresult grade=\"failed\"><tasktitle /><titlesvg /><resulttext /></taskresult></message></loncapagrade>";

        Assertions.assertEquals(xml, loncapaBuilder.getXML(message).replaceAll("[\r|\n]\\s+", "").trim());
    }
}
