package de.HsH.inform.GraFlap.BlackBoxTests.Errors;

import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Mode;
import de.HsH.inform.GraFlap.entity.Type;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class BlackBoxErrorOtherTest{
    @Test
    void testTuringForSubtractionOfUnaryNumbers(){
        String title = "Turing Automaton for substraction of unary coded numbers";
        Mode mode = Mode.MMW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("<?xmlversion=\"1.0\"encoding=\"UTF-8\"standalone=\"no\"?><structure><type>turing</type><automaton><block id=\"0\" name=\"q0\"><tag>Machine0</tag><x>39.0</x><y>262.0</y><initial/></block><block id=\"1\" name=\"q1\"><tag>Machine1</tag><x>213.0</x><y>290.0</y></block><block id=\"2\" name=\"q2\"><tag>Machine2</tag><x>335.0</x><y>229.0</y></block><block id=\"3\" name=\"q3\"><tag>Machine3</tag><x>247.0</x><y>169.0</y></block><block id=\"4\" name=\"q4\"><tag>Machine4</tag><x>343.0</x><y>83.0</y></block><block id=\"5\" name=\"s_f\"><tag>Machine5</tag><x>31.0</x><y>82.0</y><final/></block><transition><from>1</from><to>1</to><read>|</read><write>|</write><move>R</move></transition><transition><from>1</from><to>1</to><read>0</read><write>0</write><move>R</move></transition><transition><from>2</from><to>4</to><read>0</read><write>|</write><move>L</move></transition><transition><from>0</from><to>1</to><read>|</read><write/><move>R</move></transition><transition><from>1</from><to>2</to><read/><write/><move>L</move></transition><transition><from>2</from><to>3</to><read>|</read><write/><move>L</move></transition><transition><from>3</from><to>0</to><read/><write/><move>R</move></transition><transition><from>4</from><to>4</to><read>|</read><write>|</write><move>L</move></transition><transition><from>3</from><to>3</to><read>|</read><write>|</write><move>L</move></transition><transition><from>3</from><to>3</to><read>0</read><write>0</write><move>L</move></transition><transition><from>0</from><to>5</to><read>0</read><write/><move>R</move></transition><transition><from>4</from><to>5</to><read/><write/><move>R</move></transition><Machine0/><Machine2/><Machine1/><Machine4/><Machine3/><Machine5/></automaton></structure>");
        arguments.setMode(mode);
        arguments.setType(Type.NTM);
        arguments.setWordString("|||||||||||||||0|||||||||||||%||||||||0|||||||%|||||||||||||||||||0|||||||||||||||%||0||%||||||||||||0||||%|||||0||||%|||||||||||||||||||||0||||||||||||||||||||%||||||0||||%||||||||||||||0|||||||||||%||||||||||||||||||||0||||||||||||||%||||0||%|||||||||||||0|||||||||||%||||||||||0|||||||||%|||||||||0|||||||%|||0||%||||||||||||||||||0||||||||%||||||||||||||||0|||%|||||||0|||%|||||||||||0|||||||||||%|||||||||||||||||0|");
        arguments.setNumberOfWords(20);
        arguments.setStudentAnswer("<?xmlversion=\"1.0\"encoding=\"UTF-8\"standalone=\"no\"?><structure><type>turing</type><automaton><block id=\"0\" name=\"q0\"><tag>Machine0</tag><x>39.0</x><y>262.0</y><initial/></block><block id=\"1\" name=\"q1\"><tag>Machine1</tag><x>213.0</x><y>290.0</y></block><block id=\"2\" name=\"q2\"><tag>Machine2</tag><x>335.0</x><y>229.0</y></block><block id=\"3\" name=\"q3\"><tag>Machine3</tag><x>247.0</x><y>169.0</y></block><block id=\"4\" name=\"q4\"><tag>Machine4</tag><x>343.0</x><y>83.0</y></block><block id=\"5\" name=\"s_f\"><tag>Machine5</tag><x>31.0</x><y>82.0</y><final/></block><transition><from>1</from><to>1</to><read>|</read><write>|</write><move>R</move></transition><transition><from>1</from><to>1</to><read>0</read><write>0</write><move>R</move></transition><transition><from>2</from><to>4</to><read>0</read><write>|</write><move>L</move></transition><transition><from>0</from><to>1</to><read>|</read><write/><move>R</move></transition><transition><from>1</from><to>2</to><read/><write/><move>L</move></transition><transition><from>2</from><to>3</to><read>|</read><write/><move>L</move></transition><transition><from>3</from><to>0</to><read/><write/><move>R</move></transition><transition><from>4</from><to>4</to><read>|</read><write>|</write><move>L</move></transition><transition><from>3</from><to>3</to><read>|</read><write>|</write><move>L</move></transition><transition><from>3</from><to>3</to><read>0</read><write>0</write><move>L</move></transition><transition><from>0</from><to>5</to><read>0</read><write/><move>R</move></transition><transition><from>4</from><to>5</to><read/><write/><move>R</move></transition><Machine0/><Machine2/><Machine1/><Machine4/><Machine3/><Machine5/></automaton></structure>");
        GraFlapException e = Assertions.assertThrows(GraFlapException.class, () -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Test terminated: Parsing error - Cannot open JFF.", e.getMessage().trim());
    }
}
