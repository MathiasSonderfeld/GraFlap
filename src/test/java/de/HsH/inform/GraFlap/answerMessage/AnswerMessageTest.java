package de.HsH.inform.GraFlap.answerMessage;

import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.entity.*;
import de.HsH.inform.GraFlap.util.LocaleControl;
import org.junit.jupiter.api.*;

import java.util.*;

public class AnswerMessageTest {

    private static String feedbackFormat = "%d %s";
    private static String testTitleFormat = "%s %s %s %s";
    private static Locale[] locales = {Locale.ROOT, Locale.ENGLISH, Locale.GERMAN};
    private static HashMap<Locale, ResourceBundle> bundles = new HashMap<>();

    static Arguments createArgumentsObject(Mode mode, Type type, Locale locale){
        Arguments arguments = new Arguments();
        arguments.setStudentAnswer("StudentAnswer");
        arguments.setSolution("Solution");
        arguments.setTaskTitle("TaskTitle");
        arguments.setMode(mode);
        arguments.setType(type);
        arguments.setUserLanguage(locale);
        arguments.setTestwords(new Testwords(0,0));
        arguments.setNumberOfWords(123);
        return arguments;
    }

    @BeforeAll
    static void init(){
        LocaleControl lc = new LocaleControl();
        for(Locale l : locales){
            bundles.put(l, ResourceBundle.getBundle("GraFlapAnswerMessage", l, lc));
        }
    }

    @Test
    void testEnumMatchesRessourceBundle(){
        for(FeedbackMessage e : FeedbackMessage.values()){
            Assertions.assertDoesNotThrow(() -> bundles.get(Locale.ROOT).getString(e.name()));
        }
    }



    @Nested
    class CorrectResults{
        int numberOfWordsFailed = 0;
        String resultText = "Success";

        HashSet<DynamicTest> createDynamicTests(Mode mode, Type type, FeedbackMessage svgT){
            HashSet<DynamicTest> set = new HashSet<>();
            for(Locale l : locales) {
                Result result = new Result(new Submission(), numberOfWordsFailed, type);
                Arguments arguments = createArgumentsObject(mode, type, l);
                AnswerMessage answerMessage = new AnswerMessage(result, arguments, "");
                String localeText = (l==Locale.ROOT?"root":l.toString());

                set.add(DynamicTest.dynamicTest(String.format(testTitleFormat, mode.name(), "SVG", resultText, localeText),
                        () -> Assertions.assertEquals(getStringFromBundle(svgT.name(), bundles.get(l)), answerMessage.getSvgTitle())));

                set.add(DynamicTest.dynamicTest(String.format(testTitleFormat, mode.name(), "FB", resultText, localeText),
                        () -> Assertions.assertEquals(getStringFromBundle(FeedbackMessage.All_Correct.name(), bundles.get(l)), answerMessage.getFeedback())));
            }
            return set;
        }

        @TestFactory
        Collection<DynamicTest> testCYKMode(){
            return createDynamicTests(Mode.CYK, Type.NON, FeedbackMessage.CYK_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testDERMode(){
            return createDynamicTests(Mode.DER, Type.NON, FeedbackMessage.DERIVATION_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testWWMode(){
            return createDynamicTests(Mode.WW, Type.NON, FeedbackMessage.WORD_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testSVGMode(){
            return createDynamicTests(Mode.SVGA, Type.NON, FeedbackMessage.SVG_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testMPMode(){
            return createDynamicTests(Mode.MP, Type.NON, FeedbackMessage.TRANSDUCER_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testGrammarMode(){
            return createDynamicTests(Mode.GG, Type.NON, FeedbackMessage.GRAMMAR_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testAutomatonMode(){
            return createDynamicTests(Mode.AA, Type.NON, FeedbackMessage.ACCEPTOR_Svgtitle);
        }
    }

    @Nested
    class GeneralFail{
        int numberOfWordsFailed = 50;
        String resultText = "Fail";

        HashSet<DynamicTest> createDynamicTests(Mode mode, Type type, FeedbackMessage svgT, FeedbackMessage fb){
            HashSet<DynamicTest> set = new HashSet<>();
            for(Locale l : locales) {
                Result result = new Result(new Submission(), numberOfWordsFailed, type);
                Arguments arguments = createArgumentsObject(mode, type, l);
                AnswerMessage answerMessage = new AnswerMessage(result, arguments, "");
                String localeText = (l==Locale.ROOT?"root":l.toString());

                set.add(DynamicTest.dynamicTest(String.format(testTitleFormat, mode.name(), "SVG", resultText, localeText),
                        () -> Assertions.assertEquals(getStringFromBundle(svgT.name(), bundles.get(l)).trim(), answerMessage.getSvgTitle().trim())));

                set.add(DynamicTest.dynamicTest(String.format(testTitleFormat, mode.name(), "FB", resultText, localeText),
                        () -> Assertions.assertEquals(getStringFromBundle(FeedbackMessage.Anything_wrong.name(), bundles.get(l)) + " " + String.format(feedbackFormat, numberOfWordsFailed, getStringFromBundle(fb.name(), bundles.get(l))).trim(), answerMessage.getFeedback().trim())));
            }
            return set;
        }

        @TestFactory
        Collection<DynamicTest> testCYKMode(){
            return createDynamicTests(Mode.CYK, Type.NON, FeedbackMessage.CYK_Svgtitle, FeedbackMessage.CYK_Feedback);
        }

        @TestFactory
        Collection<DynamicTest> testDERMode(){
            return createDynamicTests(Mode.DER, Type.NON, FeedbackMessage.DERIVATION_Svgtitle, FeedbackMessage.DERIVATION_Feedback);
        }

        @TestFactory
        Collection<DynamicTest> testWWMode(){
            return createDynamicTests(Mode.WW, Type.NON, FeedbackMessage.WORD_Svgtitle, FeedbackMessage.WORD_Feedback);
        }

        @TestFactory
        Collection<DynamicTest> testSVGMode(){
            return createDynamicTests(Mode.SVGA, Type.NON, FeedbackMessage.SVG_Svgtitle, FeedbackMessage.SVG_Feedback);
        }

        @TestFactory
        Collection<DynamicTest> testMPMode(){
            return createDynamicTests(Mode.MP, Type.NON, FeedbackMessage.TRANSDUCER_Svgtitle, FeedbackMessage.TRANSDUCER_Feedback);
        }

        @TestFactory
        Collection<DynamicTest> testGrammarMode(){
            return createDynamicTests(Mode.GG, Type.NON, FeedbackMessage.GRAMMAR_Svgtitle, FeedbackMessage.GRAMMAR_Feedback);
        }

        @TestFactory
        Collection<DynamicTest> testAutomatonMode(){
            return createDynamicTests(Mode.ARTWP, Type.NON, FeedbackMessage.ACCEPTOR_Svgtitle, FeedbackMessage.ACCEPTOR_Feedback);
        }
    }

    @Nested
    class TypeTests{
        int numberOfWordsFailed = 50;
        String resultText = "Type Fail";
        HashSet<DynamicTest> createDynamicTests(Mode mode, Type solutionType, Type submissionType, FeedbackMessage svgT, FeedbackMessage... feedbackMessages){
            HashSet<DynamicTest> set = new HashSet<>();
            for(Locale l : locales) {
                Result result = new Result(new Submission(), numberOfWordsFailed, submissionType);
                Arguments arguments = createArgumentsObject(mode, solutionType, l);
                AnswerMessage answerMessage = new AnswerMessage(result, arguments, "");
                String localeText = (l==Locale.ROOT?"root":l.toString());

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(getStringFromBundle(feedbackMessages[0].name(), bundles.get(l)));
                if(feedbackMessages.length > 1) {
                    stringBuilder.append("\n").append(getStringFromBundle(feedbackMessages[1].name(), bundles.get(l)));
                }
                if(feedbackMessages.length > 2) {
                    stringBuilder.append(" ").append(getStringFromBundle(feedbackMessages[2].name(), bundles.get(l)));
                }

                set.add(DynamicTest.dynamicTest(String.format(testTitleFormat, mode.name(), "SVG", resultText, localeText),
                        () -> Assertions.assertEquals(getStringFromBundle(svgT.name(), bundles.get(l)).trim(), answerMessage.getSvgTitle().trim())));

                set.add(DynamicTest.dynamicTest(String.format(testTitleFormat, mode.name(), "FB", resultText, localeText),
                        () -> Assertions.assertEquals(getStringFromBundle(FeedbackMessage.Anything_wrong.name(), bundles.get(l)) + " " + String.format(feedbackFormat, numberOfWordsFailed, stringBuilder).trim(), answerMessage.getFeedback().trim())));
            }
            return set;
        }

        @TestFactory
        Collection<DynamicTest> testMPMode(){
            return createDynamicTests(Mode.MP, Type.DTM, Type.NON, FeedbackMessage.TRANSDUCER_Svgtitle,
                    FeedbackMessage.TRANSDUCER_Feedback, FeedbackMessage.AUTOMATON_IsTuring, FeedbackMessage.TRANSDUCER_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testGrammarMode(){
            return createDynamicTests(Mode.GG, Type.CFG, Type.NON, FeedbackMessage.GRAMMAR_Svgtitle,
                    FeedbackMessage.GRAMMAR_Feedback, FeedbackMessage.GRAMMAR_Type);
        }

        @TestFactory
        Collection<DynamicTest> testFAMode(){
            return createDynamicTests(Mode.ARTWP, Type.DFA, Type.NON, FeedbackMessage.ACCEPTOR_Svgtitle,
                    FeedbackMessage.ACCEPTOR_Feedback, FeedbackMessage.ACCEPTOR_FAFeedback);
        }

        @TestFactory
        Collection<DynamicTest> testPDAMode(){
            return createDynamicTests(Mode.ARTWP, Type.DPDA, Type.NON, FeedbackMessage.ACCEPTOR_Svgtitle,
                    FeedbackMessage.ACCEPTOR_Feedback, FeedbackMessage.ACCEPTOR_PDAFeedback);
        }

        @TestFactory
        Collection<DynamicTest> testTMMode(){
            return createDynamicTests(Mode.ARTWP, Type.DTM, Type.NON, FeedbackMessage.ACCEPTOR_Svgtitle,
                    FeedbackMessage.ACCEPTOR_Feedback, FeedbackMessage.AUTOMATON_IsTuring, FeedbackMessage.ACCEPTOR_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testRLCFG(){
            return createDynamicTests(Mode.GGT, Type.RLCFG, Type.CFG, FeedbackMessage.GRAMMAR_Svgtitle,
                    FeedbackMessage.GRAMMAR_Feedback);
        }

        @TestFactory
        Collection<DynamicTest> testDFAvsNFA(){
            return createDynamicTests(Mode.ARTWP, Type.NFA, Type.DFA, FeedbackMessage.ACCEPTOR_Svgtitle,
                    FeedbackMessage.ACCEPTOR_Feedback, FeedbackMessage.AUTOMATON_MatchesNotDeterministic);
        }

        @TestFactory
        Collection<DynamicTest> testNFAvsDFA(){
            return createDynamicTests(Mode.ARTWP, Type.NFA, Type.DFA, FeedbackMessage.ACCEPTOR_Svgtitle,
                    FeedbackMessage.ACCEPTOR_Feedback, FeedbackMessage.AUTOMATON_MatchesNotDeterministic);
        }
    }

    String getStringFromBundle( String search, ResourceBundle bundle ){
        String ret = bundle.getString(search);
        if(GraFlap.printAsACII){
            return search.replaceAll("ä","ae")
                         .replaceAll("ö","oe")
                         .replaceAll("ü","ue")
                         .replaceAll("Ä","Ae")
                         .replaceAll("Ö","Oe")
                         .replaceAll("Ü","Ue")
                         .replaceAll("ß","ss");
        }
        return ret;
    }
}

