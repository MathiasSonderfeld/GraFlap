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

public class BlackBoxFailingGrammarTest{
    @Test
    void testCFGGrammarForLanguageWrong(){
        String title = "CFG fuer Sprache - falsch";
        Mode mode = Mode.GG;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setReference("S->aSa|a|bSb");
        arguments.setMode(mode);
        arguments.setType(Type.NON);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("S->aSa|abSb");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(50, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Falsche Antwort. 50 Prozent der getesteten Worte haben den Test gegen die Grammatik nicht bestanden.", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertFalse(answerMessage.hasPassed());
        Assertions.assertEquals(0.0, answerMessage.getScore());
    }

    @Test
    void testExampleCFGGrammarWrong(){
        String title = "Beispiel für eine CFG Grammatik - falsch";
        Mode mode = Mode.EGT;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setReference("S->aSa|a|bSb");
        arguments.setMode(mode);
        arguments.setType(Type.CFG);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("S->aS");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(100, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Falsche Antwort. 100 Prozent der getesteten Worte haben den Test gegen die Grammatik nicht bestanden.\n" +
                "Die eingereichte Grammatik hat nicht den geforderten Typ.", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertFalse(answerMessage.hasPassed());
        Assertions.assertEquals(0.0, answerMessage.getScore());
    }

    @Test
    void testCFGforLanguageWrong(){
        String title = "CFG fuer Sprache - falsch";
        Mode mode = Mode.GGT;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setReference("S->aSa|a|bSb");
        arguments.setMode(mode);
        arguments.setType(Type.CFG);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("S->aSa|abSb");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(50, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Falsche Antwort. 50 Prozent der getesteten Worte haben den Test gegen die Grammatik nicht bestanden.", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertFalse(answerMessage.hasPassed());
        Assertions.assertEquals(0.0, answerMessage.getScore());
    }

    @Test
    void testRightLinearGrammarForGivenLanguage(){
        String title = "RLG fuer Sprache";
        Mode mode = Mode.GRT;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setReference("a*|(b|c)*a*((c+b*)*a*)*");
        arguments.setMode(mode);
        arguments.setType(Type.RL);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("S -> a A | b S | c S | E , S -> a 9 A | c S | E");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertTrue(answerMessage.getPercentOfTestWordsFailed() > 0);
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Falsche Antwort.  Prozent der getesteten Worte haben den Test gegen die Grammatik nicht bestanden.", answerMessage.getFeedback().replaceAll("[0-9]+", ""));
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertFalse(answerMessage.hasPassed());
        Assertions.assertEquals(0.0, answerMessage.getScore());
    }
}
