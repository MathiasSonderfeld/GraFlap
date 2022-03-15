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

public class BlackBoxFailingOtherTest{
    @Test
    void testCYKforGrammar() {
        String title = "CYK fuer Grammatik";
        Mode mode = Mode.CYK;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S->XY|XZ,Z->SY,X->a,Y->b");
        arguments.setMode(mode);
        arguments.setType(Type.CFG);
        arguments.setWordString("aaabbb");
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getSingleWordTestwords("aaabbb"));
        arguments.setNumberOfWords(1);
        arguments.setStudentAnswer("{F->a};{F->a};{X->a};{Y->b};{Y->b};{F->b}%{};{};{S->XY};{};{}%{};{};{Z->SY};{}%{};{S->XZ};{}%{};{Z->SY}%{S->XZ}");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(3, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Falsche Antwort. 3 Tabelleneinträge haben den Test nicht bestanden.", answerMessage.getFeedback());
        Assertions.assertEquals("CYK-Algorithmus", answerMessage.getSvgTitle());
        Assertions.assertFalse(answerMessage.hasPassed());
        Assertions.assertEquals(0.0, answerMessage.getScore());
    }

    @Test
    void testDerivationforCFGrammar() {
        String title = "Left side derivation for contextfree grammar and word aaabbb";
        Mode mode = Mode.DER;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S->aSb|ab");
        arguments.setMode(mode);
        arguments.setType(Type.CFG);
        arguments.setWordString("aaabbb");
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getSingleWordTestwords("aaabbb"));
        arguments.setNumberOfWords(1);
        arguments.setStudentAnswer("S=>YZ=>yZ=>aaabbb");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(100, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Falsche Antwort. 100 Prozent der Ableitungsschritte haben den Test nicht bestanden.", answerMessage.getFeedback());
        Assertions.assertEquals("Wortableitung", answerMessage.getSvgTitle());
        Assertions.assertFalse(answerMessage.hasPassed());
        Assertions.assertEquals(0.0, answerMessage.getScore());
    }

    @Test
    void testDerivationforCFGrammarTooLong() {
        String title = "Left side derivation for contextfree grammar and word aaabbb - too long";
        Mode mode = Mode.DER;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S->aSb|ab");
        arguments.setMode(mode);
        arguments.setType(Type.CFG);
        arguments.setWordString("aaabbb");
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getSingleWordTestwords("aaabbbb"));
        arguments.setNumberOfWords(1);
        arguments.setStudentAnswer("S=>aSb=>aaSbb=>aaaSbbb=>aaaabbbb");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(25, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Falsche Antwort. 25 Prozent der Ableitungsschritte haben den Test nicht bestanden.", answerMessage.getFeedback());
        Assertions.assertEquals("Wortableitung", answerMessage.getSvgTitle());
        Assertions.assertFalse(answerMessage.hasPassed());
        Assertions.assertEquals(0.0, answerMessage.getScore());
    }

    @Test
    void testMealyForSmoothingBinaryImages() {
        String title = "Mealy Maschine zur Bildglättung";
        Mode mode = Mode.MMW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>mealy</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>103.0</x><y>93.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>237.0</x><y>52.0</y></state><state id=\"2\" name=\"q2\"><x>237.0</x><y>168.0</y></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>1</read><transout>1</transout></transition><transition><from>2</from><to>2</to><read>0</read><transout>0</transout></transition><transition><from>2</from><to>0</to><read>1</read><transout>0</transout></transition><transition><from>0</from><to>2</to><read>0</read><transout>0</transout></transition><transition><from>0</from><to>1</to><read>1</read><transout>1</transout></transition><transition><from>1</from><to>0</to><read>0</read><transout>1</transout></transition></automaton></structure>");
        arguments.setMode(mode);
        arguments.setType(Type.MEALY);
        arguments.setWordString("01%10%001%010%011%1010010%00101101");
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>moore</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>141.0</x><y>120.0</y><initial/><output/></state><state id=\"1\" name=\"q1\"><x>224.0</x><y>205.0</y><output>0</output></state><state id=\"2\" name=\"q2\"><x>22.0</x><y>202.0</y><output>0</output></state><state id=\"3\" name=\"q3\"><x>27.0</x><y>33.0</y><output>1</output></state><state id=\"4\" name=\"q4\"><x>284.0</x><y>43.0</y><output>1</output></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>3</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>2</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>0</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>3</from><to>4</to><read>0</read><transout>1</transout></transition><transition><from>4</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>4</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>2</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>0</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>1</from><to>2</to><read>1</read><transout>0</transout></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Die Antwort ist teilweise richtig. 0 Prozent der getesteten Worte haben den Test gegen die Maschine nicht bestanden.\n" +
                "Dies ist keine Mealy-Maschine.", answerMessage.getFeedback());
        Assertions.assertEquals("Maschine", answerMessage.getSvgTitle());
        Assertions.assertFalse(answerMessage.hasPassed());
        Assertions.assertEquals(0.5, answerMessage.getScore());
    }

    @Test
    void testMooreForSmoothingBinaryImages() {
        String title = "Moore Maschine zur Bildglättung";
        Mode mode = Mode.MMW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>moore</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>141.0</x><y>120.0</y><initial/><output/></state><state id=\"1\" name=\"q1\"><x>224.0</x><y>205.0</y><output>0</output></state><state id=\"2\" name=\"q2\"><x>22.0</x><y>202.0</y><output>0</output></state><state id=\"3\" name=\"q3\"><x>27.0</x><y>33.0</y><output>1</output></state><state id=\"4\" name=\"q4\"><x>284.0</x><y>43.0</y><output>1</output></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>3</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>2</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>0</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>3</from><to>4</to><read>0</read><transout>1</transout></transition><transition><from>4</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>4</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>2</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>0</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>1</from><to>2</to><read>1</read><transout>0</transout></transition></automaton></structure>");
        arguments.setMode(mode);
        arguments.setType(Type.MOORE);
        arguments.setWordString("01%10%001%010%011%1010010%00101101");
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>mealy</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>103.0</x><y>93.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>237.0</x><y>52.0</y></state><state id=\"2\" name=\"q2\"><x>237.0</x><y>168.0</y></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>1</read><transout>1</transout></transition><transition><from>2</from><to>2</to><read>0</read><transout>0</transout></transition><transition><from>2</from><to>0</to><read>1</read><transout>0</transout></transition><transition><from>0</from><to>2</to><read>0</read><transout>0</transout></transition><transition><from>0</from><to>1</to><read>1</read><transout>1</transout></transition><transition><from>1</from><to>0</to><read>0</read><transout>1</transout></transition></automaton></structure>");
        arguments.setNumberOfWords(50); AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Die Antwort ist teilweise richtig. 0 Prozent der getesteten Worte haben den Test gegen die Maschine nicht bestanden.\n" +
                "Dies ist keine Moore-Maschine.", answerMessage.getFeedback());
        Assertions.assertEquals("Maschine", answerMessage.getSvgTitle());
        Assertions.assertFalse(answerMessage.hasPassed());
        Assertions.assertEquals(0.5, answerMessage.getScore());
    }
}
