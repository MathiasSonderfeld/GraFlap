package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.TestwordsUtil;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Mode;
import de.HsH.inform.GraFlap.entity.Type;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Locale;


public class ProformaParserTest {
    @Test
    void testArgumentsToInputConverter(){
        String studentAnswer = "ThisShouldBeTheStudentAnswer";
        Arguments arguments = new Arguments();
        arguments.setTestId("graflap");
        arguments.setTaskTitle("ProformaParserTest OK");
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setMode(Mode.AGP);
        arguments.setType(Type.DFA);
        arguments.setReference("ThisIsGiven");
        arguments.setNumberOfWords(0);
        arguments.setTestwords(TestwordsUtil.emptyTestwords);
        arguments.setStudentAnswer(studentAnswer);
        arguments.setTransitions("Transitions");
        arguments.setInitials("Initials");
        arguments.setFinals("Finals");
        arguments.setStates("States");
        arguments.setAlphabet("Alphabet");
        ArgumentsToInputConverter argumentsToInputConverter = new ArgumentsToInputConverter(arguments);
        String[] proforma = {"", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><proforma:submission xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:proforma=\"urn:proforma:v2.1\" id=\"SubmissionUUID\"><proforma:task uuid=\"0123\" parent-uuid=\"0\" lang=\"de\">ProformaParserTest OK<proforma:description>Blocked</proforma:description><proforma:proglang version=\"1.0\">Blocked</proforma:proglang><proforma:files><proforma:file id=\"graflap-arguments\" mimetype=\"base64\" used-by-grader=\"true\" visible=\"no\" usage-by-lms=\"edit\"><proforma:embedded-txt-file filename=\"graflap-arguments\"><![CDATA[ProformaParserTest OK#de#ThisIsGiven#AGP#DFA#0#-]]></proforma:embedded-txt-file></proforma:file></proforma:files><tests><test id=\"graflap\"><title>GraFlap</title><test-type>graflap</test-type><test-configuration><filerefs><fileref refid=\"graflap-arguments\" /></filerefs></test-configuration></test></tests><proforma:meta-data /></proforma:task><proforma:files><proforma:file id=\"studentAnswer\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"(jff|jflap)\"><![CDATA[ThisShouldBeTheStudentAnswer]]></proforma:embedded-txt-file></proforma:file></proforma:files><proforma:grader-spec format=\"xml\" lang=\"proforma\" structure=\"separate-test-feedback\" /></proforma:submission>"};
        String[] proformaSets = {"", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><proforma:submission xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:proforma=\"urn:proforma:v2.1\" id=\"SubmissionUUID\"><proforma:task uuid=\"0123\" parent-uuid=\"0\" lang=\"de\">ProformaParserTest OK<proforma:description>Blocked</proforma:description><proforma:proglang version=\"1.0\">Blocked</proforma:proglang><proforma:files><proforma:file id=\"graflap-arguments\" mimetype=\"base64\" used-by-grader=\"true\" visible=\"no\" usage-by-lms=\"edit\"><proforma:embedded-txt-file filename=\"graflap-arguments\"><![CDATA[ProformaParserTest OK#de#ThisIsGiven#AGP#DFA#0#-]]></proforma:embedded-txt-file></proforma:file></proforma:files><tests><test id=\"graflap\"><title>GraFlap</title><test-type>graflap</test-type><test-configuration><filerefs><fileref refid=\"graflap-arguments\" /></filerefs></test-configuration></test></tests><proforma:meta-data /></proforma:task><proforma:files><proforma:file id=\"studentAnswer\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"(jff|jflap)\"><![CDATA[ThisShouldBeTheStudentAnswer]]></proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"states\"><![CDATA[States]]></proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"transitions\"><![CDATA[Transitions]]></proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"initials\"><![CDATA[Initials]]></proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"alphabet\"><![CDATA[Alphabet]]></proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"finals\"><![CDATA[Finals]]></proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"stackalphabet\"><![CDATA[]]></proforma:embedded-txt-file></proforma:file></proforma:files><proforma:grader-spec format=\"xml\" lang=\"proforma\" structure=\"separate-test-feedback\" /></proforma:submission>"};

        Assertions.assertTrue(Arrays.equals(proforma, argumentsToInputConverter.getProformaInput()));
        Assertions.assertTrue(Arrays.equals(proformaSets, argumentsToInputConverter.getProformaInputWithSets()));

    }

    @Nested
    class ProformaParserNestedTest{
        ProformaParser proformaParser = new ProformaParser();

        @Test
        void testSuccess(){
            Arguments arguments = new Arguments();
            arguments.setTestId("graflap");
            arguments.setTaskTitle("ProformaParserTest OK");
            arguments.setUserLanguage(Locale.GERMAN);
            arguments.setMode(Mode.GG);
            arguments.setType(Type.CFG);
            arguments.setReference("ThisIsGiven");
            arguments.setNumberOfWords(0);
            arguments.setTestwords(TestwordsUtil.emptyTestwords);
            arguments.setStudentAnswer("ThisShouldBeTheStudentAnswer");
            ArgumentsToInputConverter argumentsToInputConverter = new ArgumentsToInputConverter(arguments);
            String[] generatedInput = argumentsToInputConverter.getProformaInput();
            Arguments parsed = Assertions.assertDoesNotThrow(() -> proformaParser.parse(generatedInput));
            Assertions.assertEquals(arguments, parsed);
        }

        @Test
        void testSetsSuccess(){
            Arguments arguments = new Arguments();
            arguments.setTestId("graflap");
            arguments.setTaskTitle("ProformaParserTest OK");
            arguments.setUserLanguage(Locale.GERMAN);
            arguments.setMode(Mode.AGTP);
            arguments.setType(Type.DPDA);
            arguments.setReference("ThisIsGiven");
            arguments.setNumberOfWords(0);
            arguments.setTestwords(TestwordsUtil.emptyTestwords);
            arguments.setStudentAnswer("ThisShouldBeTheStudentAnswer");
            arguments.setTransitions("Transitions");
            arguments.setInitials("Initials");
            arguments.setFinals("Finals");
            arguments.setStates("States");
            arguments.setAlphabet("Alphabet");
            arguments.setAlphabet("Stackalphabet");
            ArgumentsToInputConverter argumentsToInputConverter = new ArgumentsToInputConverter(arguments);
            String[] generatedInput = argumentsToInputConverter.getProformaInputWithSets();
            Arguments parsed = Assertions.assertDoesNotThrow(() -> proformaParser.parse(generatedInput));
            Assertions.assertEquals(arguments, parsed);
        }

        @Test
        void testNull(){
            Arguments arguments = new Arguments();
            arguments.setTaskTitle(null);
            arguments.setUserLanguage(null);
            arguments.setMode(null);
            arguments.setType(null);
            arguments.setReference(null);
            arguments.setNumberOfWords(0);
            arguments.setTestwords(null);
            arguments.setStudentAnswer(null);
            ArgumentsToInputConverter argumentsToInputConverter = new ArgumentsToInputConverter(arguments);
            Assertions.assertThrows(GraFlapException.class, () -> proformaParser.parse(argumentsToInputConverter.getProformaInput()));
        }

        @Test
        void testNullArray(){
            Assertions.assertThrows(GraFlapException.class, () -> proformaParser.parse(null));
        }
    }
}
