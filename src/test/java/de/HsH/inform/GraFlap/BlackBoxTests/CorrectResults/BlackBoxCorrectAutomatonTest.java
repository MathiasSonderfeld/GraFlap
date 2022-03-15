package de.HsH.inform.GraFlap.BlackBoxTests.CorrectResults;

import de.HsH.inform.GraFlap.BlackBoxTests.BlackBoxTestTestwordsUtil;
import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.answerMessage.AnswerMessage;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Mode;
import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.entity.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class BlackBoxCorrectAutomatonTest{
    @Test
    void testAutomatonForEvenNumberOfW() {
        String title = "Automat fuer gerade Anzahl von w";
        Mode mode = Mode.ARTW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("x*(x*wx*wx*)*");
        arguments.setMode(mode);
        arguments.setType(Type.DFA);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getListTestWords("xxxxxxx%xxxxxxxxxxxwxwxxwxwxxwxwxxwxwxxwxwxxwxw%xxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxx" +
                "%xxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxx%xxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxx!w%xw%xwx%www%wxww%xwxx%wwwx%xxwxxx%wxxxww%xwwxxwx%wwxwwxwx%wxxxwwww%wxxxwwxww%wxxwwxwxxw%xxxxwwwwxw"));
        arguments.setNumberOfWords(20);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure>&#13;<type>fa</type>&#13;<automaton>&#13;<!--The list of states.-->&#13;<state id=\"0\" name=\"s0\">&#13;<x>82.0</x>&#13;<y>90.0</y>&#13;<initial/>&#13;<final/>&#13;</state>&#13;<state id=\"1\" name=\"s1\">&#13;<x>180.0</x>&#13;<y>90.0</y>&#13;</state>&#13;<!--The list of transitions.-->&#13;<transition>&#13;<from>0</from>&#13;<to>1</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>0</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>0</from>&#13;<to>0</to>&#13;<read>x</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>1</to>&#13;<read>x</read>&#13;</transition>&#13;</automaton>&#13;</structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testAutomatonForBinaryNumbers() {
        String title = "Automat fuer den Vergleich von Dualzahlen";
        Mode mode = Mode.ARTW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("(00|11)*10(00|01|10|11)*");
        arguments.setMode(mode);
        arguments.setType(Type.FA);
        Testwords testwords = BlackBoxTestTestwordsUtil.getListTestWords("0010%111100000010%1100111111110011001100001110001100%0000110011001111001110101010010111%1100000000001100000011111110111010" +
                "%0000000010001110011010000110111100%11111001010110100111111101011010001011%10110110101111110101001111001110010010%11111010001001100001001011001101100000" +
                "%001111111100110000001111101100000101110011%000000100110110111000001000010111110011000%11101010100110000111110001111101110000110101%000000000011000011111100110000000011101011101111%00111111111111110000000000110010000000100001011000%0011110011100110101100001101010101000000000000111111%110011111100000011000011110000000011111110100111011101%110000001111111111001111111111001100100000001111110000%00111111000000110000000000111111111100101011100000000001%00111100110011000000110011110000100001000110100110001101010110010010%1111111100111100110011111111000011001000000010000111101000111000100111!%1%0%11%01%110%111%100%000%010%0110%0000%1111%01111%01110%01001%011000%000001%1111100%0001110%0010011%01011101%11000001%00010110%011001001%111001010%111101000%110001110%111000010%0001111000%1111011000%11011110010%10000110010%110101000000%1101101000100%1010000101000%0110100001010%1010011010100%0011111101110%0100101101000%01111100111100%00000011110111%01000010101100%01110110010100%01100111100011%111110000111001%000000000100101%000011011001001%0110101111100101%1100110011001101%1101011100011010%0000010101100000%01111000010000001%11011000110111011%00001010001011101%00011111001000010%000111100011001101%0001101001010110011%01011001110000110000%00000011011000100101");
        arguments.setTestwords(testwords);
        arguments.setNumberOfWords(80);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>fa</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>90.0</x><y>144.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>253.0</x><y>144.0</y></state><state id=\"2\" name=\"q2\"><x>67.0</x><y>33.0</y></state><state id=\"3\" name=\"q3\"><x>252.0</x><y>24.0</y><final/></state><state id=\"4\" name=\"q4\"><x>407.0</x><y>19.0</y></state><!--The list of transitions.--><transition><from>4</from><to>3</to><read>0</read></transition><transition><from>3</from><to>4</to><read>0</read></transition><transition><from>2</from><to>3</to><read>0</read></transition><transition><from>4</from><to>3</to><read>1</read></transition><transition><from>3</from><to>4</to><read>1</read></transition><transition><from>1</from><to>0</to><read>0</read></transition><transition><from>0</from><to>1</to><read>0</read></transition><transition><from>0</from><to>2</to><read>1</read></transition><transition><from>2</from><to>0</to><read>1</read></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Automat fuer den Vergleich von Dualzahlen", answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(Mode.ARTW, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testAutomatonForGivenLanguage() {
        String title = "Automat fuer gegebene Sprache";
        Mode mode = Mode.AGTW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S -> A, A -> x B|y C, B -> x A|y D,C -> x D|y F, D -> x C|y G, F -> x G|y H, G -> x F|y J, H -> x J| y H | E, J -> x H|y J");
        arguments.setMode(mode);
        arguments.setType(Type.FA);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getListTestWords("yyy%yyyy%xxyyy%xyxyy%xyyxy%xyyyx%yxxyy%yxyxy%yxyyx%yyxxy%yyxyx%yyyxx%yyyyy%xxyyyy%xyxyyy%xyyxyy%xyyyxy%xyyyyx%yxxyyy%yxyxyy%yxyyxy%yxyyyx!%y%xx%yx%yyx%xyy%yxx%xxy%xyx%xxyy%yxxy%yxxx%xyxx%yyxy%xyxy%xxxx%xyyy%xxxy%yyxx%xyxxx%xyxyx%yyyxy%xyyxx%yxxxy%xxxyy%xxyyx%yxxxx%xxxyx%yyxxx%xyxxy%xyyyy%xxyxy%xxyxx"));
        arguments.setNumberOfWords(55);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>fa</type><automaton><!--The list of states.--><state id=\"0\" name=\"s0\"><x>57.0</x><y>61.0</y><initial/></state><state id=\"1\" name=\"s1\"><x>200.0</x><y>65.0</y></state><state id=\"2\" name=\"s2\"><x>414.0</x><y>58.0</y></state><state id=\"3\" name=\"s4\"><x>53.0</x><y>188.0</y></state><state id=\"4\" name=\"s5\"><x>192.0</x><y>189.0</y></state><state id=\"5\" name=\"s6\"><x>404.0</x><y>188.0</y></state><state id=\"6\" name=\"s7\"><x>598.0</x><y>191.0</y></state><state id=\"7\" name=\"s3\"><x>593.0</x><y>57.0</y><final/></state><!--The list of transitions.--><transition><from>0</from><to>3</to><read>x</read></transition><transition><from>3</from><to>0</to><read>x</read></transition><transition><from>7</from><to>7</to><read>y</read></transition><transition><from>6</from><to>6</to><read>y</read></transition><transition><from>0</from><to>1</to><read>y</read></transition><transition><from>5</from><to>6</to><read>y</read></transition><transition><from>3</from><to>4</to><read>y</read></transition><transition><from>4</from><to>1</to><read>x</read></transition><transition><from>1</from><to>4</to><read>x</read></transition><transition><from>6</from><to>7</to><read>x</read></transition><transition><from>7</from><to>6</to><read>x</read></transition><transition><from>4</from><to>5</to><read>y</read></transition><transition><from>2</from><to>7</to><read>y</read></transition><transition><from>5</from><to>2</to><read>x</read></transition><transition><from>2</from><to>5</to><read>x</read></transition><transition><from>1</from><to>2</to><read>y</read></transition></automaton></structure> ");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testAutomatonForRegex() {
        String title = "Automat fuer e^n f^n";
        Mode mode = Mode.AGTW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S->eSf|ef");
        arguments.setMode(mode);
        arguments.setType(Type.PDA);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getListTestWords("ef%eeff%eeefff%eeeeffff%eeeeefffff%eeeeeeffffff%eeeeeeefffffff%eeeeeeeeffffffff%eeeeeeeeefffffffff%eeeeeeeeeeffffffffff%eeeeeeeeeeefffffffffff%eeeeeeeeeeeeffffffffffff%eeeeeeeeeeeeefffffffffffff%eeeeeeeeeeeeeeffffffffffffff%eeeeeeeeeeeeeeefffffffffffffff%eeeeeeeeeeeeeeeeffffffffffffffff%eeeeeeeeeeeeeeeeefffffffffffffffff%eeeeeeeeeeeeeeeeeeffffffffffffffffff%eeeeeeeeeeeeeeeeeeefffffffffffffffffff%eeeeeeeeeeeeeeeeeeeeffffffffffffffffffff%eeeeeeeeeeeeeeeeeeeeefffffffffffffffffffff%eeeeeeeeeeeeeeeeeeeeeeffffffffffffffffffffff!%f%e%ee%efe%eff%eee%fef%fee%ffe%ffee%eeef%fefe%ffef%efee%feef%feff%efef%efffe%efeee%eefee%ffeee%eefff%efefe%effef%fefff%efeff%feffe%ffffe%fefee%eefef%ffeef%eeeff%eeefe%eeeee%feeff%feeffe%eefeff%fefefe%fefeef%effeef%efefef"));
        arguments.setNumberOfWords(64);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>34.0</x><y>122.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>177.0</x><y>117.0</y></state><state id=\"2\" name=\"q2\"><x>317.0</x><y>112.0</y></state><state id=\"3\" name=\"q3\"><x>465.0</x><y>108.0</y><final/></state><!--The list of transitions.--><transition><from>2</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>1</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>0</from><to>1</to><read>e</read><pop>Z</pop><push>e Z</push></transition><transition><from>1</from><to>1</to><read>e</read><pop>e</pop><push>ee</push></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testAutomatonForCombinedLanguage() {
        String title = "Automat fuer Worte einer zusammengesetzten Sprache";
        Mode mode = Mode.ART;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("(g|h)*|(hi)*");
        arguments.setMode(mode);
        arguments.setType(Type.FA);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(10);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>fa</type><automaton><!--The list of states.--><state id=\"0\" name=\"s0\"><x>48.0</x><y>143.0</y><initial/></state><state id=\"1\" name=\"s1\"><x>208.0</x><y>64.0</y><final/></state><state id=\"2\" name=\"s2\"><x>210.0</x><y>185.0</y><final/></state><state id=\"3\" name=\"s3\"><x>360.0</x><y>187.0</y></state><!--The list of transitions.--><transition><from>0</from><to>1</to><read/></transition><transition><from>1</from><to>1</to><read>h</read></transition><transition><from>0</from><to>2</to><read/></transition><transition><from>1</from><to>1</to><read>g</read></transition><transition><from>2</from><to>3</to><read>h</read></transition><transition><from>3</from><to>2</to><read>i</read></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testAutomatonForEvenNumberZero() {
        String title = "Automat fuer gerade Anzahl von Nullen";
        Mode mode = Mode.AR;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("1*(1*01*01*)*");
        arguments.setMode(mode);
        arguments.setType(Type.NON);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>fa</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>100.0</x><y>117.0</y><initial/><final/></state><state id=\"1\" name=\"q1\"><x>303.0</x><y>115.0</y></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>1</read></transition><transition><from>0</from><to>0</to><read>1</read></transition><transition><from>1</from><to>0</to><read>0</read></transition><transition><from>0</from><to>1</to><read>0</read></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testAutomatonForEvenNumberZeroTyped() {
        String title = "Automat fuer gerade Anzahl von Nullen - Typisiert";
        Mode mode = Mode.ART;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("1*(1*01*01*)*");
        arguments.setMode(mode);
        arguments.setType(Type.FA);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>fa</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>100.0</x><y>117.0</y><initial/><final/></state><state id=\"1\" name=\"q1\"><x>303.0</x><y>115.0</y></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>1</read></transition><transition><from>0</from><to>0</to><read>1</read></transition><transition><from>1</from><to>0</to><read>0</read></transition><transition><from>0</from><to>1</to><read>0</read></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testPushDownAutomatonForANBN() {
        String title = "PDA fuer a^nb^n";
        Mode mode = Mode.AG;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S->aSb|ab");
        arguments.setMode(mode);
        arguments.setType(Type.NON);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(20);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>34.0</x><y>122.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>177.0</x><y>117.0</y></state><state id=\"2\" name=\"q2\"><x>317.0</x><y>112.0</y></state><state id=\"3\" name=\"q3\"><x>465.0</x><y>108.0</y><final/></state><!--The list of transitions.--><transition><from>2</from><to>2</to><read>b</read><pop>a</pop><push/></transition><transition><from>1</from><to>2</to><read>b</read><pop>a</pop><push/></transition><transition><from>0</from><to>1</to><read>a</read><pop>Z</pop><push>aZ</push></transition><transition><from>1</from><to>1</to><read>a</read><pop>a</pop><push>aa</push></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testPushDownAutomatonForANBNTyped() {
        String title = "PDA fuer a^nb^n - Typisiert";
        Mode mode = Mode.AGT;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S->aSb|ab");
        arguments.setMode(mode);
        arguments.setType(Type.PDA);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(20);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>34.0</x><y>122.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>177.0</x><y>117.0</y></state><state id=\"2\" name=\"q2\"><x>317.0</x><y>112.0</y></state><state id=\"3\" name=\"q3\"><x>465.0</x><y>108.0</y><final/></state><!--The list of transitions.--><transition><from>2</from><to>2</to><read>b</read><pop>a</pop><push/></transition><transition><from>1</from><to>2</to><read>b</read><pop>a</pop><push/></transition><transition><from>0</from><to>1</to><read>a</read><pop>Z</pop><push>aZ</push></transition><transition><from>1</from><to>1</to><read>a</read><pop>a</pop><push>aa</push></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testJFFTuringAutomatonForRJS2J() {
        String title = "Turing Automat für r^j s^{2 j}";
        Mode mode = Mode.AGTW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S -> r ss | r S s s");
        arguments.setMode(mode);
        arguments.setType(Type.DTM);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getListTestWords("rss%rrssss%rrrssssss%rrrrssssssss%rrrrrssssssssss%rrrrrrssssssssssss%rrrrrrrssssssssssssss%rrrrrrrrssssssssssssssss%rrrrrrrrrssssssssssssssssss%rrrrrrrrrrssssssssssssssssssss%rrrrrrrrrrrssssssssssssssssssssss%rrrrrrrrrrrrssssssssssssssssssssssss%rrrrrrrrrrrrrssssssssssssssssssssssssss%rrrrrrrrrrrrrrssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrssssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrrssssssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrrrssssssssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrrrrssssssssssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrrrrrssssssssssssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrrrrrrssssssssssssssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrrrrrrrssssssssssssssssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrrrrrrrrssssssssssssssssssssssssssssssssssssssssssss!%s%rr%sr%rs%rsr%sss%srr%rrr%srs%ssr%sssr%rrrr%srrr%rsrs%ssrs%rssr%ssrr%rsrr%rrss%ssss%rsrss%sssrr%srsrs%rrrsr%rrrrr%srsrr%rrrss%rsrrs%sssss%rssrr%rsssr%rssss%srssr%srrss%rsrsr%rrsrs%rrsss%srrrs%rrsrr%ssrss%srrrr"));
        arguments.setNumberOfWords(64);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>turing</type><automaton><!--The list of states.--><block id=\"0\" name=\"q0\"><tag>Machine0</tag><x>41.0</x><y>59.0</y><initial/></block><block id=\"1\" name=\"q1\"><tag>Machine1</tag><x>153.0</x><y>56.0</y></block><block id=\"2\" name=\"q2\"><tag>Machine2</tag><x>255.0</x><y>56.0</y></block><block id=\"3\" name=\"q3\"><tag>Machine3</tag><x>369.0</x><y>55.0</y></block><block id=\"4\" name=\"q4\"><tag>Machine4</tag><x>373.0</x><y>199.0</y></block><block id=\"5\" name=\"q5\"><tag>Machine5</tag><x>254.0</x><y>197.0</y></block><block id=\"6\" name=\"q6\"><tag>Machine6</tag><x>49.0</x><y>191.0</y><final/></block><!--The list of transitions.--><transition><from>1</from><to>1</to><read>r</read><write>r</write><move>R</move></transition><transition><from>1</from><to>1</to><read>s</read><write>s</write><move>R</move></transition><transition><from>0</from><to>1</to><read>r</read><write/><move>R</move></transition><transition><from>4</from><to>5</to><read/><write/><move>R</move></transition><transition><from>3</from><to>4</to><read>s</read><write/><move>L</move></transition><transition><from>5</from><to>6</to><read/><write/><move>S</move></transition><transition><from>4</from><to>4</to><read>r</read><write>r</write><move>L</move></transition><transition><from>4</from><to>4</to><read>s</read><write>s</write><move>L</move></transition><transition><from>5</from><to>1</to><read>r</read><write/><move>R</move></transition><transition><from>1</from><to>2</to><read/><write/><move>L</move></transition><transition><from>2</from><to>3</to><read>s</read><write/><move>L</move></transition><!--The list of automata--><Machine0/><Machine2/><Machine1/><Machine4/><Machine3/><Machine6/><Machine5/></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testJFLAPTuringAutomatonForRJS2J() {
        String title = "Turing Automat für r^j s^{2 j}";
        Mode mode = Mode.AGTW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S -> r ss | r S s s");
        arguments.setMode(mode);
        arguments.setType(Type.DTM);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getListTestWords("rss%rrssss%rrrssssss%rrrrssssssss%rrrrrssssssssss%rrrrrrssssssssssss%rrrrrrrssssssssssssss%rrrrrrrrssssssssssssssss%rrrrrrrrrssssssssssssssssss%rrrrrrrrrrssssssssssssssssssss%rrrrrrrrrrrssssssssssssssssssssss%rrrrrrrrrrrrssssssssssssssssssssssss%rrrrrrrrrrrrrssssssssssssssssssssssssss%rrrrrrrrrrrrrrssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrssssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrrssssssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrrrssssssssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrrrrssssssssssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrrrrrssssssssssssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrrrrrrssssssssssssssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrrrrrrrssssssssssssssssssssssssssssssssssssssssss%rrrrrrrrrrrrrrrrrrrrrrssssssssssssssssssssssssssssssssssssssssssss!%s%rr%sr%rs%rsr%sss%srr%rrr%srs%ssr%sssr%rrrr%srrr%rsrs%ssrs%rssr%ssrr%rsrr%rrss%ssss%rsrss%sssrr%srsrs%rrrsr%rrrrr%srsrr%rrrss%rsrrs%sssss%rssrr%rsssr%rssss%srssr%srrss%rsrsr%rrsrs%rrsss%srrrs%rrsrr%ssrss%srrrr"));
        arguments.setNumberOfWords(64);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><structure type=\"editor_panel\"><structure type=\"transition_graph\"><structure mode=\"Default mode\" type=\"turing\"><tapes>1</tapes><structure type=\"tape_alph\"><symbol>r</symbol><symbol>s</symbol><symbol>□</symbol></structure><structure type=\"state_set\"><state><name>q0</name><id>0</id></state><state><name>q1</name><id>1</id></state><state><name>q2</name><id>2</id></state><state><name>q3</name><id>3</id></state><state><name>q4</name><id>4</id></state><state><name>q5</name><id>5</id></state><state><name>q6</name><id>6</id></state></structure><structure type=\"input_alph\"><symbol>r</symbol><symbol>s</symbol></structure><structure type=\"transition_set\"><transition><tapes>1</tapes><write0>□</write0><from><name>q0</name><id>0</id></from><to><name>q1</name><id>1</id></to><move0>R</move0><read0>r</read0></transition><transition><tapes>1</tapes><write0>r</write0><from><name>q1</name><id>1</id></from><to><name>q1</name><id>1</id></to><move0>R</move0><read0>r</read0></transition><transition><tapes>1</tapes><write0>s</write0><from><name>q1</name><id>1</id></from><to><name>q1</name><id>1</id></to><move0>R</move0><read0>s</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>q1</name><id>1</id></from><to><name>q2</name><id>2</id></to><move0>L</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>q2</name><id>2</id></from><to><name>q3</name><id>3</id></to><move0>L</move0><read0>s</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>q3</name><id>3</id></from><to><name>q4</name><id>4</id></to><move0>L</move0><read0>s</read0></transition><transition><tapes>1</tapes><write0>r</write0><from><name>q4</name><id>4</id></from><to><name>q4</name><id>4</id></to><move0>L</move0><read0>r</read0></transition><transition><tapes>1</tapes><write0>s</write0><from><name>q4</name><id>4</id></from><to><name>q4</name><id>4</id></to><move0>L</move0><read0>s</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>q4</name><id>4</id></from><to><name>q5</name><id>5</id></to><move0>R</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>q5</name><id>5</id></from><to><name>q1</name><id>1</id></to><move0>R</move0><read0>r</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>q5</name><id>5</id></from><to><name>q6</name><id>6</id></to><move0>S</move0><read0>□</read0></transition></structure><structure type=\"blank_symbol\"><value>□</value></structure><structure type=\"start_state\"><state><name>q0</name><id>0</id></state></structure><structure type=\"final_states\"><state><name>q6</name><id>6</id></state></structure></structure><state_point_map><state_point><state>0</state><point><x>53.0</x><y>270.0</y></point></state_point><state_point><state>1</state><point><x>194.0</x><y>271.0</y></point></state_point><state_point><state>2</state><point><x>342.0</x><y>270.0</y></point></state_point><state_point><state>3</state><point><x>478.0</x><y>275.0</y></point></state_point><state_point><state>4</state><point><x>470.0</x><y>113.0</y></point></state_point><state_point><state>5</state><point><x>322.0</x><y>103.0</y></point></state_point><state_point><state>6</state><point><x>181.0</x><y>92.0</y></point></state_point></state_point_map><control_point_map><control_point><from>0</from><to>1</to><point><x>123.5</x><y>270.5</y></point></control_point><control_point><from>1</from><to>1</to><point><x>194.0</x><y>201.0</y></point></control_point><control_point><from>1</from><to>2</to><point><x>268.0</x><y>270.5</y></point></control_point><control_point><from>4</from><to>4</to><point><x>470.0</x><y>43.0</y></point></control_point><control_point><from>5</from><to>1</to><point><x>258.0</x><y>187.0</y></point></control_point><control_point><from>2</from><to>3</to><point><x>410.0</x><y>272.5</y></point></control_point><control_point><from>3</from><to>4</to><point><x>474.0</x><y>194.0</y></point></control_point><control_point><from>4</from><to>5</to><point><x>396.0</x><y>108.0</y></point></control_point><control_point><from>5</from><to>6</to><point><x>251.5</x><y>97.5</y></point></control_point></control_point_map></structure><state_label_map/><note_map/></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }
}
