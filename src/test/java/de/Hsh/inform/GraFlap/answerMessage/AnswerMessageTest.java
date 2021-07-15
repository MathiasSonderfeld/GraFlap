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

    static Arguments createArgumentsObject(TaskMode taskMode, TaskType taskType, Locale locale){
        Arguments arguments = new Arguments();
        arguments.setStudentAnswer("StudentAnswer");
        arguments.setSolution("Solution");
        arguments.setTaskTitle("TaskTitle");
        arguments.setTaskMode(taskMode);
        arguments.setTaskType(taskType);
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

        HashSet<DynamicTest> createDynamicTests(TaskMode taskMode, TaskType taskType, FeedbackMessage svgT){
            HashSet<DynamicTest> set = new HashSet<>();
            for(Locale l : locales) {
                Result result = new Result(new Submission(), numberOfWordsFailed, taskType);
                Arguments arguments = createArgumentsObject(taskMode, taskType, l);
                AnswerMessage answerMessage = new AnswerMessage(result, arguments, "");
                String localeText = (l==Locale.ROOT?"root":l.toString());

                set.add(DynamicTest.dynamicTest(String.format(testTitleFormat,taskMode.name(), "SVG", resultText, localeText),
                        () -> Assertions.assertEquals(getStringFromBundle(svgT.name(), bundles.get(l)), answerMessage.getSvgTitle())));

                set.add(DynamicTest.dynamicTest(String.format(testTitleFormat,taskMode.name(), "FB", resultText, localeText),
                        () -> Assertions.assertEquals("", answerMessage.getFeedback())));
            }
            return set;
        }

        @TestFactory
        Collection<DynamicTest> testCYKMode(){
            return createDynamicTests(TaskMode.CYK, TaskType.NON, FeedbackMessage.CYK_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testDERMode(){
            return createDynamicTests(TaskMode.DER, TaskType.NON, FeedbackMessage.DERIVATION_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testWWMode(){
            return createDynamicTests(TaskMode.WW, TaskType.NON, FeedbackMessage.WORD_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testSVGMode(){
            return createDynamicTests(TaskMode.SVGA, TaskType.NON, FeedbackMessage.SVG_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testMPMode(){
            return createDynamicTests(TaskMode.MP, TaskType.NON, FeedbackMessage.TRANSDUCER_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testGrammarMode(){
            return createDynamicTests(TaskMode.GG, TaskType.NON, FeedbackMessage.GRAMMAR_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testAutomatonMode(){
            return createDynamicTests(TaskMode.AA, TaskType.NON, FeedbackMessage.ACCEPTOR_Svgtitle);
        }
    }

    @Nested
    class GeneralFail{
        int numberOfWordsFailed = 50;
        String resultText = "Fail";

        HashSet<DynamicTest> createDynamicTests( TaskMode taskMode, TaskType taskType, FeedbackMessage svgT, FeedbackMessage fb){
            HashSet<DynamicTest> set = new HashSet<>();
            for(Locale l : locales) {
                Result result = new Result(new Submission(), numberOfWordsFailed, taskType);
                Arguments arguments = createArgumentsObject(taskMode, taskType, l);
                AnswerMessage answerMessage = new AnswerMessage(result, arguments, "");
                String localeText = (l==Locale.ROOT?"root":l.toString());

                set.add(DynamicTest.dynamicTest(String.format(testTitleFormat,taskMode.name(), "SVG", resultText, localeText),
                        () -> Assertions.assertEquals(getStringFromBundle(svgT.name(), bundles.get(l)).trim(), answerMessage.getSvgTitle().trim())));

                set.add(DynamicTest.dynamicTest(String.format(testTitleFormat,taskMode.name(), "FB", resultText, localeText),
                        () -> Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, getStringFromBundle(fb.name(), bundles.get(l))).trim(), answerMessage.getFeedback().trim())));
            }
            return set;
        }

        @TestFactory
        Collection<DynamicTest> testCYKMode(){
            return createDynamicTests(TaskMode.CYK, TaskType.NON, FeedbackMessage.CYK_Svgtitle, FeedbackMessage.CYK_Feedback);
        }

        @TestFactory
        Collection<DynamicTest> testDERMode(){
            return createDynamicTests(TaskMode.DER, TaskType.NON, FeedbackMessage.DERIVATION_Svgtitle, FeedbackMessage.DERIVATION_Feedback);
        }

        @TestFactory
        Collection<DynamicTest> testWWMode(){
            return createDynamicTests(TaskMode.WW, TaskType.NON, FeedbackMessage.WORD_Svgtitle, FeedbackMessage.WORD_Feedback);
        }

        @TestFactory
        Collection<DynamicTest> testSVGMode(){
            return createDynamicTests(TaskMode.SVGA, TaskType.NON, FeedbackMessage.SVG_Svgtitle, FeedbackMessage.SVG_Feedback);
        }

        @TestFactory
        Collection<DynamicTest> testMPMode(){
            return createDynamicTests(TaskMode.MP, TaskType.NON, FeedbackMessage.TRANSDUCER_Svgtitle, FeedbackMessage.TRANSDUCER_Feedback);
        }

        @TestFactory
        Collection<DynamicTest> testGrammarMode(){
            return createDynamicTests(TaskMode.GG, TaskType.NON, FeedbackMessage.GRAMMAR_Svgtitle, FeedbackMessage.GRAMMAR_Feedback);
        }

        @TestFactory
        Collection<DynamicTest> testAutomatonMode(){
            return createDynamicTests(TaskMode.ARTWP, TaskType.NON, FeedbackMessage.ACCEPTOR_Svgtitle, FeedbackMessage.ACCEPTOR_Feedback);
        }
    }

    @Nested
    class TypeTests{
        int numberOfWordsFailed = 50;
        String resultText = "Type Fail";
        HashSet<DynamicTest> createDynamicTests( TaskMode taskMode, TaskType solutionTaskType, TaskType submissionTaskType, FeedbackMessage svgT, FeedbackMessage... feedbackMessages){
            HashSet<DynamicTest> set = new HashSet<>();
            for(Locale l : locales) {
                Result result = new Result(new Submission(), numberOfWordsFailed, submissionTaskType);
                Arguments arguments = createArgumentsObject(taskMode, solutionTaskType, l);
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

                set.add(DynamicTest.dynamicTest(String.format(testTitleFormat,taskMode.name(), "SVG", resultText, localeText),
                        () -> Assertions.assertEquals(getStringFromBundle(svgT.name(), bundles.get(l)).trim(), answerMessage.getSvgTitle().trim())));

                set.add(DynamicTest.dynamicTest(String.format(testTitleFormat,taskMode.name(), "FB", resultText, localeText),
                        () -> Assertions.assertEquals(String.format(feedbackFormat, numberOfWordsFailed, stringBuilder).trim(), answerMessage.getFeedback().trim())));
            }
            return set;
        }

        @TestFactory
        Collection<DynamicTest> testMPMode(){
            return createDynamicTests(TaskMode.MP, TaskType.DTM, TaskType.NON, FeedbackMessage.TRANSDUCER_Svgtitle,
                    FeedbackMessage.TRANSDUCER_Feedback, FeedbackMessage.AUTOMATON_IsTuring, FeedbackMessage.TRANSDUCER_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testGrammarMode(){
            return createDynamicTests(TaskMode.GG, TaskType.CFG, TaskType.NON, FeedbackMessage.GRAMMAR_Svgtitle,
                    FeedbackMessage.GRAMMAR_Feedback, FeedbackMessage.GRAMMAR_Type);
        }

        @TestFactory
        Collection<DynamicTest> testFAMode(){
            return createDynamicTests(TaskMode.ARTWP, TaskType.DFA, TaskType.NON, FeedbackMessage.ACCEPTOR_Svgtitle,
                    FeedbackMessage.ACCEPTOR_Feedback, FeedbackMessage.ACCEPTOR_FAFeedback);
        }

        @TestFactory
        Collection<DynamicTest> testPDAMode(){
            return createDynamicTests(TaskMode.ARTWP, TaskType.DPDA, TaskType.NON, FeedbackMessage.ACCEPTOR_Svgtitle,
                    FeedbackMessage.ACCEPTOR_Feedback, FeedbackMessage.ACCEPTOR_PDAFeedback);
        }

        @TestFactory
        Collection<DynamicTest> testTMMode(){
            return createDynamicTests(TaskMode.ARTWP, TaskType.DTM, TaskType.NON, FeedbackMessage.ACCEPTOR_Svgtitle,
                    FeedbackMessage.ACCEPTOR_Feedback, FeedbackMessage.AUTOMATON_IsTuring, FeedbackMessage.ACCEPTOR_Svgtitle);
        }

        @TestFactory
        Collection<DynamicTest> testRLCFG(){
            return createDynamicTests(TaskMode.GGT, TaskType.RLCFG, TaskType.CFG, FeedbackMessage.GRAMMAR_Svgtitle,
                    FeedbackMessage.GRAMMAR_Feedback);
        }

        @TestFactory
        Collection<DynamicTest> testDFAvsNFA(){
            return createDynamicTests(TaskMode.ARTWP, TaskType.NFA, TaskType.DFA, FeedbackMessage.ACCEPTOR_Svgtitle,
                    FeedbackMessage.ACCEPTOR_Feedback, FeedbackMessage.AUTOMATON_MatchesNotDeterministic);
        }

        @TestFactory
        Collection<DynamicTest> testNFAvsDFA(){
            return createDynamicTests(TaskMode.ARTWP, TaskType.NFA, TaskType.DFA, FeedbackMessage.ACCEPTOR_Svgtitle,
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

