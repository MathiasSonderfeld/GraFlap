package de.HsH.inform.GraFlap.BlackBoxTests.FailingResults;

import de.HsH.inform.GraFlap.BlackBoxTests.BlackBoxTestTestwordsUtil;
import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.answerMessage.AnswerMessage;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Mode;
import de.HsH.inform.GraFlap.entity.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class BlackBoxFailingAutomatonTest{
    @Test
    void testAutomatonForEvenNumberZero() {
        String title = "Automat fuer gerade Anzahl von Nullen";
        Mode mode = Mode.AR;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setReference("1*(1*01*01*)*");
        arguments.setMode(mode);
        arguments.setType(Type.NON);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>fa</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>39.0</x><y>137.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>191.0</x><y>136.0</y><final/></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>1</read></transition><transition><from>1</from><to>1</to><read>o</read></transition><transition><from>0</from><to>1</to><read>0</read></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertTrue( answerMessage.getPercentOfTestWordsFailed() > 0);
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Falsche Antwort.  Prozent der getesteten Worte haben den Test gegen den Automaten nicht bestanden.", answerMessage.getFeedback().trim().replaceAll("[0-9]+", ""));
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertFalse(answerMessage.hasPassed());
        Assertions.assertEquals(0.0, answerMessage.getScore());
    }

    @Test
    void testAutomatonForEvenNumberZeroTyped() {
        String title = "Automat fuer gerade Anzahl von Nullen - Typisiert";
        Mode mode = Mode.ART;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setReference("1*(1*01*01*)*");
        arguments.setMode(mode);
        arguments.setType(Type.FA);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>fa</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>39.0</x><y>137.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>191.0</x><y>136.0</y><final/></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>1</read></transition><transition><from>1</from><to>1</to><read>o</read></transition><transition><from>0</from><to>1</to><read>0</read></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertTrue( answerMessage.getPercentOfTestWordsFailed() > 0);
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Falsche Antwort.  Prozent der getesteten Worte haben den Test gegen den Automaten nicht bestanden.", answerMessage.getFeedback().trim().replaceAll("[0-9]+", ""));
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertFalse(answerMessage.hasPassed());
        Assertions.assertEquals(0.0, answerMessage.getScore());
    }

    @Test
    void testPushDownAutomatonForANBN() {
        String title = "PDA fuer a^nb^n";
        Mode mode = Mode.AG;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setReference("S->aSb|ab");
        arguments.setMode(mode);
        arguments.setType(Type.NON);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(20);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>54.0</x><y>91.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>134.0</x><y>20.0</y></state><state id=\"2\" name=\"q2\"><x>265.0</x><y>21.0</y></state><state id=\"3\" name=\"q3\"><x>360.0</x><y>89.0</y><final/></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>a</read><pop>a</pop><push>aa</push></transition><transition><from>2</from><to>2</to><read>b</read><pop>a</pop><push/></transition><transition><from>0</from><to>3</to><read/><pop>Z</pop><push/></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition><transition><from>1</from><to>2</to><read>b</read><pop>a</pop><push/></transition><transition><from>0</from><to>1</to><read>a</read><pop>Z</pop><push>aZ</push></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals( 1, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Falsche Antwort. 1 Prozent der getesteten Worte haben den Test gegen den Automaten nicht bestanden.", answerMessage.getFeedback().trim());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertFalse(answerMessage.hasPassed());
        Assertions.assertEquals(0.0, answerMessage.getScore());
    }

    @Test
    void testPushDownAutomatonForANBNTyped() {
        String title = "PDA fuer a^nb^n - Typisiert";
        Mode mode = Mode.AGT;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setReference("S->aSb|ab");
        arguments.setMode(mode);
        arguments.setType(Type.PDA);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(20);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>54.0</x><y>91.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>134.0</x><y>20.0</y></state><state id=\"2\" name=\"q2\"><x>265.0</x><y>21.0</y></state><state id=\"3\" name=\"q3\"><x>360.0</x><y>89.0</y><final/></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>a</read><pop>a</pop><push>aa</push></transition><transition><from>2</from><to>2</to><read>b</read><pop>a</pop><push/></transition><transition><from>0</from><to>3</to><read/><pop>Z</pop><push/></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition><transition><from>1</from><to>2</to><read>b</read><pop>a</pop><push/></transition><transition><from>0</from><to>1</to><read>a</read><pop>Z</pop><push>aZ</push></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals( 1, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Falsche Antwort. 1 Prozent der getesteten Worte haben den Test gegen den Automaten nicht bestanden.", answerMessage.getFeedback().trim());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertFalse(answerMessage.hasPassed());
        Assertions.assertEquals(0.0, answerMessage.getScore());
    }
}
