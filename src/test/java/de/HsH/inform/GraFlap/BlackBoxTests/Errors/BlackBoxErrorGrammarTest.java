package de.HsH.inform.GraFlap.BlackBoxTests.Errors;

import de.HsH.inform.GraFlap.BlackBoxTests.BlackBoxTestTestwordsUtil;
import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Mode;
import de.HsH.inform.GraFlap.entity.Type;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class BlackBoxErrorGrammarTest{
    @Test
    void testRLGforLangaugeErrorOne(){
        String title = "RLG fuer Sprache - grammar type - Error1";
        Mode mode = Mode.GRT;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setReference("a*|(b|c)*a*((c+b*)*a*)*");
        arguments.setMode(mode);
        arguments.setType(Type.RL);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("S -> a A | b S | c S | E , a A -> a A | c S | E");
        GraFlapException e = Assertions.assertThrows(GraFlapException.class, () -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Test terminated: Error. Please check grammar type..", e.getMessage().trim());
    }

    @Test
    void testRLGforLangaugeErrorTwo(){
        String title = "RLG fuer Sprache - grammar type - Error2";
        Mode mode = Mode.GRT;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setReference("a*|(b|c)*a*((c+b*)*a*)*");
        arguments.setMode(mode);
        arguments.setType(Type.RL);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("S -> a A | b S | c S | E , a -> a A | c S | E");
        GraFlapException e = Assertions.assertThrows(GraFlapException.class, () -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Test terminated: Error. Please check grammar type..", e.getMessage().trim());
    }

    @Test
    void testRLGforLangaugeErrorThree(){
        String title = "RLG fuer Sprache - grammar type - Error3";
        Mode mode = Mode.GRT;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setReference("a*|(b|c)*a*((c+b*)*a*)*");
        arguments.setMode(mode);
        arguments.setType(Type.RL);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("S -> a A | b S | c S | E , S -> a 9 A | c & S | E");
        GraFlapException e = Assertions.assertThrows(GraFlapException.class, () -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Test terminated: ERROR - There is something wrong with your grammar..", e.getMessage().trim());
    }
}
