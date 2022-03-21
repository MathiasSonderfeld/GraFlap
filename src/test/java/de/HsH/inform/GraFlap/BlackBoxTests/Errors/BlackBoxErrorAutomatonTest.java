package de.HsH.inform.GraFlap.BlackBoxTests.Errors;

import de.HsH.inform.GraFlap.BlackBoxTests.BlackBoxTestTestwordsUtil;
import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Mode;
import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.entity.Type;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class BlackBoxErrorAutomatonTest{
    @Test
    void testAutomatonForEvenNumberOfWBrokenTransition() {
        String title = "Automat fuer gerade Anzahl von w";
        Mode mode = Mode.ARTW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setReference("x*(x*wx*wx*)*");
        arguments.setMode(mode);
        arguments.setType(Type.DFA);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getListTestWords("xxxxxxx%xxxxxxxxxxxwxwxxwxwxxwxwxxwxwxxwxwxxwxw%xxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxx" +
                "%xxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxx%xxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxx!w%xw%xwx%www%wxww%xwxx%wwwx%xxwxxx%wxxxww%xwwxxwx%wwxwwxwx%wxxxwwww%wxxxwwxww%wxxwwxwxxw%xxxxwwwwxw"));
        arguments.setNumberOfWords(20);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>fa</type><automaton><!--The list of states.--><state id=\"0\" name=\"s0\"><x>82.0</x><y>90.0</y><initial/><final/></state><state id=\"1\" name=\"s1\"><x>180.0</x><y>90.0</y></state><!--The list of transitions.--><transition><from>0</from><to>1</to><read>w</read></transition><transition><to>0</to><read>w</read></transition><transition><from>0</from><to>0</to><read>x</read></transition><transition><from>1</from><to>1</to></transition></automaton></structure>");
        GraFlapException e = Assertions.assertThrows(GraFlapException.class, () -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Test terminated: A transition has no from state!.", e.getMessage().trim());
    }

    @Test
    void testAutomatonForBinaryNumbersBrokenJFF() {
        String title = "Automat fuer den Vergleich von Dualzahlen";
        Mode mode = Mode.ARTW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setReference("(00|11)*10(00|01|10|11)*");
        arguments.setMode(mode);
        arguments.setType(Type.FA);
        Testwords testwords = BlackBoxTestTestwordsUtil.getListTestWords("0010%111100000010%1100111111110011001100001110001100%0000110011001111001110101010010111%1100000000001100000011111110111010" +
                "%0000000010001110011010000110111100%11111001010110100111111101011010001011%10110110101111110101001111001110010010%11111010001001100001001011001101100000" +
                "%001111111100110000001111101100000101110011%000000100110110111000001000010111110011000%11101010100110000111110001111101110000110101%000000000011000011111100110000000011101011101111%00111111111111110000000000110010000000100001011000%0011110011100110101100001101010101000000000000111111%110011111100000011000011110000000011111110100111011101%110000001111111111001111111111001100100000001111110000%00111111000000110000000000111111111100101011100000000001%00111100110011000000110011110000100001000110100110001101010110010010%1111111100111100110011111111000011001000000010000111101000111000100111!%1%0%11%01%110%111%100%000%010%0110%0000%1111%01111%01110%01001%011000%000001%1111100%0001110%0010011%01011101%11000001%00010110%011001001%111001010%111101000%110001110%111000010%0001111000%1111011000%11011110010%10000110010%110101000000%1101101000100%1010000101000%0110100001010%1010011010100%0011111101110%0100101101000%01111100111100%00000011110111%01000010101100%01110110010100%01100111100011%111110000111001%000000000100101%000011011001001%0110101111100101%1100110011001101%1101011100011010%0000010101100000%01111000010000001%11011000110111011%00001010001011101%00011111001000010%000111100011001101%0001101001010110011%01011001110000110000%00000011011000100101");
        arguments.setTestwords(testwords);
        arguments.setNumberOfWords(80);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>fa</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>90.0</x><y>144.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>253.0</x><y>144.0</y></state><state id=\"2\" name=\"q2\"><x>67.0</x><y>33.0</y></state><state id=\"3\" name=\"q3\"><x>252.0</x><y>24.0</y><final/><state id=\"4\" name=\"q4\"><x>407.0</x><y>19.0</y></state><!--The list of transitions.--><transition><from>4</from><to>3</to><read>0</read></transition><transition><from>3</from><to>4</to><read>0</read></transition><transition><from>2</from><to>3</to><read>0</read></transition><transition><from>4</from><to>3</to><read>1</read></transition><transition><from>3</from><to>4</to><read>1</read></transition><transition><from>1</from><to>0</to><read>0</read></transition><transition><from>0</from><to>1</to><read>0</read></transition><transition><from>0</from><to>2</to><read>1</read></transition><transition><from>2</from><to>0</to><read>1</read></transition></automaton></structure>");
        GraFlapException e = Assertions.assertThrows(GraFlapException.class, () -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Test terminated: Parsing error - Cannot open JFF.", e.getMessage().trim());
    }

}
