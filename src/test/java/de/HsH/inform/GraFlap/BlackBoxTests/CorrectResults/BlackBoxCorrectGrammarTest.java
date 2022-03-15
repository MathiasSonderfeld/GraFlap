package de.HsH.inform.GraFlap.BlackBoxTests.CorrectResults;

import de.HsH.inform.GraFlap.BlackBoxTests.BlackBoxTestTestwordsUtil;
import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.answerMessage.AnswerMessage;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Mode;
import de.HsH.inform.GraFlap.entity.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class BlackBoxCorrectGrammarTest{
    @Test
    void testContextFreeGrammar() {
        String title = "Beispiel fuer eine kontextfreie Grammatik";
        Mode mode = Mode.EGT;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("n,o,p");
        arguments.setMode(mode);
        arguments.setType(Type.CFG);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(0);
        arguments.setStudentAnswer("S -> E | p | n S o");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testNonContextFreeGrammar() {
        String title = "Beispiel fuer eine nicht kontextfreie Grammatik";
        Mode mode = Mode.EGT;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("u,v,w");
        arguments.setMode(mode);
        arguments.setType(Type.NCFG);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(0);
        arguments.setStudentAnswer("S -> w | uSv, uS -> Sw");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testRightRegularGrammar() {
        String title = "Beispiel fuer eine rechtslineare Grammatik";
        Mode mode = Mode.EGT;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("h,i,j");
        arguments.setMode(mode);
        arguments.setType(Type.RL);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(0);
        arguments.setStudentAnswer("S -> E | j | hiS");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testContextFreeGrammarForGivenLanguage() {
        String title = "Kontextfreie Grammatik fuer gegebene Sprache";
        Mode mode = Mode.GGTW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S -> T|V, T -> ef | e T f, V -> eef | ee V f");
        arguments.setMode(mode);
        arguments.setType(Type.CFG);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getTestWords("ef%eef%eeff%eeeeff%eeefff%eeeeffff%eeeeeefff%eeeeefffff%eeeeeeeeffff%eeeeeeffffff%eeeeeeefffffff%eeeeeeeeeefffff%eeeeeeeeffffffff%eeeeeeeeeeeeffffff%eeeeeeeeefffffffff%eeeeeeeeeeffffffffff%eeeeeeeeeeeeeefffffff%eeeeeeeeeeefffffffffff%eeeeeeeeeeeeeeeeffffffff%eeeeeeeeeeeeeeeeeefffffffff%eeeeeeeeeeeeeeeeeeeeffffffffff%eeeeeeeeeeeeeeeeeeeeeefffffffffff!%f%ee%ff%fe%fff%ffe%fee%eee%eff%efe%efee%fefe%ffef%ffff%eeef%efef%feff%eeee%efffe%eeefe%efeff%feeff%effee%eefff%eeeee%eeeef%effff%eefee%fefee%ffeee%fffef%eeffe%efefe%feeef%feefe%ffffe%fefff%efeee%efeef%effef%eefef"));
        arguments.setNumberOfWords(64);
        arguments.setStudentAnswer("S -> T|V, T -> ef | e T f, V -> eef | ee V f");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testGrammarForIntegers() {
        String title = "Grammatik fuer ganze Zahlen";
        Mode mode = Mode.GGTW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D | -P, P ->  1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D , D ->  0| 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 0D | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D");
        arguments.setMode(mode);
        arguments.setType(Type.RL);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getTestWords("0%9%-2%91%24%-10%-20%437%-32%350%720%887%-791%-253%-532%-268%-319%-680%-973%-807%-431!-%-0%00%-00%-1-2%00884%00687%00695%00224%00957%00855%00806%00656" +
                "%00871%00292%-0090%00892%00870%00907%00-703%-00106%00-558%-00819%00-380%-00547%-00785%00-943%-00352%-00528%00-695%-00-28%-00594%-00717%-00-180%-00-328%-00-793%-00-304%-00-388%-00-384%-00-365%-00-392"));
        arguments.setNumberOfWords(62);
        arguments.setStudentAnswer("S -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9  \t  | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D  | -P, P -> 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D, D -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9  | 0D  | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testGrammarForGivenLanguage() {
        String title = "Grammatik fuer Sprache (enthaelt nicht ef)";
        Mode mode = Mode.GRTW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("e*|(f|g)*e*((g+f*)*e*)*");
        arguments.setMode(mode);
        arguments.setType(Type.RL);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getTestWords("eeeee%eeeeee%eeeeeeeeee%eeeeeeeeeee%eeeeeeeeeeee%eeeeeeeeeeeeee%eeeeeeeeeeeeeee%eeeeeeeeeeeeeeee%eeeeeeeeeeeeeeeee%eeeeeeeeeeeeeeeeee%ffeeeegffffffffffffffgffffffffffffffegffffffffffffffgffffffffffffffegffffffffffffffgffffffffffffffegffffffffffffffgffffffffffffffe%ffgfggfgfggffggfgeeeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeee%!eef%gffef%egfeefg%eefefeegg%ggffgefgf%fgfgeffgff%ggegfefgff%feeggeffgf%gffffeefgee%eeggffefefe%egegeffgeee%eeggefefege%efgegegggfg%fgegggeeefee%efefgggggeef%eeffgeefffeg%egeefefeffge%ggfgffeefffg%fffeeeegeeef%eegggfeefeef%gefffgefegeg%geffeegefggff%fgefgffgfegff%egffeffffgegf%fefeegegggfgee%ggeefggfgegfgg%fefgeefgeggfgg%egffgfeeffggeg%fgefeeggffffee%fgfeegfgeeffff%fgefgegegeeege%egegeffegfffefg%efgfefgfffggeff%egggggefgggfgef%fgggfffefgggggf%ffegefegggeffff%fffeefgefgeggee%feggfgefgggfgeef%efgegffgfgeggfgf%gfggffgefefggggg%feffgegefeeggfegg%fegegeeefgeggeggf%ggffgefeffgffggeff%gfgefffeggfeffffef%gegfegfefeeeffgfeg%gfgggfffegfeeggefg%efeffffffggfgfgffe%eegeffffggeeefeeff%ffefgfeffgggggefege%fgefgefeeffgefegfef%effeggeeggfegfgffge%eegeeeffgeffggggggg%feffgegfggeffgfffeg%ffefeffeffffgggegfg%gfeefefegfgfgeeffee%efggffgegfeegffeefef%eggeegeeegefgeegeeef%geefefeegffeffgfegge%feeeefgefeffgeeggggf%ffgfefeegegffeefgegg"));
        arguments.setNumberOfWords(80);
        arguments.setStudentAnswer("S -> e A | f S | g S | E , A -> e A | g S | E");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testCFGGrammarForLanguage() {
        String title = "CFG fuer Sprache - richtig";
        Mode mode = Mode.GG;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S->aSa|a|bSb");
        arguments.setMode(mode);
        arguments.setType(Type.NON);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("S->aSa|a|bSb");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testRightLinearForLanguage() {
        String title = "RLG fuer Sprache";
        Mode mode = Mode.GGT;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S -> a A | b S | c S | E , A -> a A | c S | E");
        arguments.setMode(mode);
        arguments.setType(Type.RL);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("S -> a A | b S | c S | E , A -> a A | c S | E");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testRightLinearForRegex() {
        String title = "RLG fuer Regex";
        Mode mode = Mode.GRT;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("a*|(b|c)*a*((c+b*)*a*)*");
        arguments.setMode(mode);
        arguments.setType(Type.RL);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("S -> a A | b S | c S | E , A -> a A | c S | E");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }
}
