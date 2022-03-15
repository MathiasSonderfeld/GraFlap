package de.HsH.inform.GraFlap;

import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.JflapWrapper.grammar.Grammar;
import de.HsH.inform.GraFlap.convert.CYKInputParser;
import de.HsH.inform.GraFlap.convert.ConvertSubmission;
import de.HsH.inform.GraFlap.convert.DerivationParser;
import de.HsH.inform.GraFlap.entity.*;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.SetResult;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.State;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.Transition;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.scoring.cyk.CYKScoringTest;
import de.HsH.inform.GraFlap.scoring.derivation.DerivationScoringTest;
import de.HsH.inform.GraFlap.test.AlphabetTest;
import de.HsH.inform.GraFlap.test.AutomatonComparisonTest;
import de.HsH.inform.GraFlap.test.SetsTest;
import de.HsH.inform.GraFlap.test.WordTest;
import de.HsH.inform.GraFlap.test.accepting.*;
import de.HsH.inform.GraFlap.test.transducing.TransducerPairTest;
import de.HsH.inform.GraFlap.test.transducing.TransducerWordTest;
import de.HsH.inform.GraFlap.typetest.AutomatonTypeTest;
import de.HsH.inform.GraFlap.typetest.GrammarTypeTest;
import de.HsH.inform.GraFlap.util.TimeoutBlock;

import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.Callable;

/**
 * helper class for the main method to determine the result of the submission
 * @author Benjamin Held (04-17-2016)
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value GraFlap#version}
 */
public class Grader {
    /**
     * generates a Set of Characters that a student submitted grammar MUST contain to be processed.
     * Otherwise its not terminating and the program fails.
     * @return the Set of required Characters
     */
    private static HashSet<Character> getGrammarFilterSet(){
        HashSet<Character> grammarFilterSet = new HashSet<>();
        //add all small letters
        for(int i=0;i<26;i++){
            grammarFilterSet.add((char) ('a' + i));
        }
        //add all digits
        for(int i=0;i<10;i++){
            grammarFilterSet.add((char) ('0' + i));
        }
        return grammarFilterSet;
    }

     /**
     * starts the process to generate the result and the grading
     * @param arguments the {@link Arguments} object that holds the submission information
     * @return a reference of the object
     * @throws GraFlapException throws a {@link GraFlapException} if an error occurs
     */
    public static Result generateResult(Arguments arguments) throws GraFlapException {
        Result result;
        Submission submission = new Submission();
        int percentageFailed = -1;
        Type submissionType = Type.NON;
        String extraText = "";
        boolean setResultsused = false;
        SetResult<State> statesResult = new SetResult<>();
        SetResult<State> initialsResult = new SetResult<>();
        SetResult<State> finalsResult = new SetResult<>();
        SetResult<String> alphabetResult = new SetResult<>();
        SetResult<String> stackAlphabetResult = new SetResult<>();
        SetResult<Transition> transitionsResult = new SetResult<>();

        long start = new Date().getTime();
        //Grammar Precheck
        if(arguments.getMode().isGrammar()){
            HashSet<Character> filter = getGrammarFilterSet();
            int matches = 0;
            for(char c : arguments.getStudentAnswer().toCharArray()) if(filter.contains(c)) matches++;
            if(matches == 0) throw new GraFlapException("Grammar is not terminating, grading aborted");
        }


        switch(arguments.getMode()) {
            case ERROR:
                throw new GraFlapException("Error in given ProFormA task. Please check mode variable.");
            case AA:
                setResultsused = true;
                AutomatonComparisonTest automatonComparisonTest = new AutomatonComparisonTest(arguments.getSolution(), arguments.getStudentAnswer());
                submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                percentageFailed = automatonComparisonTest.getTotalErrors();
                submissionType = arguments.getType();
                statesResult = automatonComparisonTest.getStatesResult();
                initialsResult = automatonComparisonTest.getInitialsResult();
                finalsResult = automatonComparisonTest.getFinalsResult();
                alphabetResult = automatonComparisonTest.getAlphabetResult();
                stackAlphabetResult = automatonComparisonTest.getStackAlphabetResult();
                transitionsResult = automatonComparisonTest.getTransitionsResult();
                break;
            case AR:
            case ARP:
            case ART:
            case ARTP:
                if (!arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                    AutomatonRegexTest myTest = new AutomatonRegexTest();
                    percentageFailed = myTest.openInput(arguments.getSolution(), submission,
                                                                arguments.getNumberOfWords());
                    extraText = myTest.getWordFeedback();
                } else {
                    throw new GraFlapException("Error in given ProFormA task. Please check regular expression.");
                }
                break;
            case AG:
            case AGP:
            case AGT:
            case AGTP:
                if (arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                    AutomatonTest myTest = new AutomatonTest();
                    percentageFailed = myTest.openInput(arguments.getSolution(), submission,
                                                           arguments.getNumberOfWords());
                    extraText = myTest.getWordFeedback();
                } else {
                    throw new GraFlapException("Error in given ProFormA task. Please check given grammar.");
                }

                break;
            case GG:
            case GGT:
                if (arguments.getStudentAnswer().contains("->") && arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openGrammar(GrammarBuilder.
                                                   buildGrammar(arguments.getStudentAnswer()));
                    GrammarTest myTest = new GrammarTest();
                    percentageFailed = myTest.openInput(arguments.getSolution(), submission,
                                                         arguments.getNumberOfWords());
                    extraText = myTest.getWordFeedback();
                } else {
                    throw new GraFlapException("Error. Please check grammar.");
                }
                break;
            case ARW:
            case ARWP:
            case ARTW:
            case ARTWP:
                if (!arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                    AutomatonTest myTest = new AutomatonTest();
                    percentageFailed = myTest.openInput(arguments.getSolution(), submission,
                                                           arguments.getTestwords());
                    extraText = myTest.getWordFeedback();
                }else {
                    throw new GraFlapException("Error in given ProFormA task. Please check regular expression.");
                }
                break;
            case AGW:
            case AGWP:
            case AGTW:
            case AGTWP:
                if (arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                    AutomatonTest myTest = new AutomatonTest();
                    percentageFailed = myTest.openInput(arguments.getSolution(), submission,
                                                           arguments.getTestwords());
                    extraText = myTest.getWordFeedback();
                } else {
                    throw new GraFlapException("Error in given ProFormA task. Please check given grammar.");
                }
                break;
            case GGW:
            case GGTW:
                if (arguments.getStudentAnswer().contains("->") && arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openGrammar(GrammarBuilder.
                                                               buildGrammar(arguments.getStudentAnswer()));
                    GrammarTest myTest = new GrammarTest();
                    percentageFailed = myTest.openInput(arguments.getSolution(), submission,
                                                         arguments.getTestwords());
                    extraText = myTest.getWordFeedback();
                }else {
                    throw new GraFlapException("Error. Please check grammar.");
                }
                break;
            case EAT:
            case EGT:
                submission = ConvertSubmission.openGrammar(GrammarBuilder.buildGrammar(arguments.getStudentAnswer()));
                percentageFailed = new AlphabetTest(submission).checkAlphabet(arguments.getSolution());
                break;
            case WW:
                submission = ConvertSubmission.openWords(arguments.getStudentAnswer(), arguments.getNumberOfWords());
                percentageFailed = WordTest.checkWords(arguments.getSolution(), (String[]) submission.getSubmissionObject());
                //extraText = WordTest.getWordFeedback();
                break;
            case GR:
            case GRT:
                if (arguments.getStudentAnswer().contains("->") ) {
                    submission = ConvertSubmission.openGrammar(GrammarBuilder.
                                                   buildGrammar(arguments.getStudentAnswer()));
                    submissionType = GrammarTypeTest.checkForGrammarType(submission);
                    if ((submissionType == Type.RL || submissionType == Type.CFG)) {
                        percentageFailed = new GrammarRegexTest().openInput(arguments.getSolution(), submission,
                                                                  arguments.getNumberOfWords());
                    } else {
                        throw new GraFlapException("Error. Please check grammar type.");
                    }
                } else {
                    throw new GraFlapException("Error. Please check grammar.");
                }
                break;
            case GRW:
            case GRTW:
                if (arguments.getStudentAnswer().contains("->") ) {
                    submission = ConvertSubmission.openGrammar(GrammarBuilder.
                                                   buildGrammar(arguments.getStudentAnswer()));
                    submissionType = GrammarTypeTest.checkForGrammarType(submission);
                    if ((submissionType == Type.RL || submissionType == Type.CFG)) {
                        GrammarTest myTest = new GrammarTest();
                        percentageFailed = myTest.openInput(arguments.getSolution(), submission,
                                                             arguments.getTestwords());
                        extraText = myTest.getWordFeedback();
                    } else {
                        throw new GraFlapException("Error. Please check grammar type.");
                    }
                } else {
                    throw new GraFlapException("Error. Please check grammar.");
                }
                break;
            case RR:
                if (!arguments.getStudentAnswer().contains("->") ) {
                    submission = ConvertSubmission.openRegex(RegexBuilder.checkAndClean(arguments.getStudentAnswer()));
                    RegexTest myTest = new RegexTest();
                    percentageFailed = myTest.getResult(arguments.getSolution(), submission,
                                arguments.getNumberOfWords());
                    extraText = myTest.getWordFeedback();
                } else {
                    throw new GraFlapException("Error. Please check regex.");
                }
                break;
            case RRW:
                if (!arguments.getStudentAnswer().contains("->") ) {
                    submission = ConvertSubmission.openRegex(RegexBuilder.checkAndClean(arguments.getStudentAnswer()));
                    RegexTest myTest = new RegexTest();
                    percentageFailed = myTest.getResult(arguments.getSolution(), submission,
                                arguments.getTestwords());
                    extraText = myTest.getWordFeedback();
                } else {
                    throw new GraFlapException("Error. Please check regex.");
                }
                break;
            case MP:
                submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                submissionType = AutomatonTypeTest.checkForAutomatonType(submission);
                percentageFailed = new TransducerPairTest().determineResult(submission, arguments.getTestwords());
                break;
            case MMW:
                submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                submissionType = AutomatonTypeTest.checkForAutomatonType(submission);
                percentageFailed = new TransducerWordTest(arguments.getSolution()).determineResult(submission,
                                                                                         arguments.getWordString());
                break;
            case CYK:
                Submission<Grammar> solution = ConvertSubmission.openGrammar(
                                               GrammarBuilder.buildGrammar(arguments.getSolution()));
                submission = CYKInputParser.openCYKInput(arguments.getStudentAnswer(), arguments.getTestwords(),
                                                         solution.getSubmissionObject());
                percentageFailed = new CYKScoringTest((CYKTable) submission.getSubmissionObject(), arguments.getTestwords(),
                                             solution.getSubmissionObject()).returnScore();
                break;
            case DER:
                submission = DerivationParser.openDerivation(arguments.getStudentAnswer());
                solution = ConvertSubmission.openGrammar(GrammarBuilder.buildGrammar(arguments.getSolution()));
                percentageFailed = new DerivationScoringTest((String[]) submission.getSubmissionObject(),
                                                    solution.getSubmissionObject(), arguments.getTestwords())
                                                    .returnScore();
                break;
            case SVGA:
                submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                submissionType = AutomatonTypeTest.checkForAutomatonType(submission);
                percentageFailed = 0;
                break;
            case SVGG:
                submission = ConvertSubmission.openGrammar(GrammarBuilder.buildGrammar(arguments.getStudentAnswer()));
                submissionType = GrammarTypeTest.checkForGrammarType(submission);
                percentageFailed = 0;
                break;
        }

        if (arguments.getMode().isTyped()) {
            if (arguments.getMode().isAutomaton()) {
                submissionType = AutomatonTypeTest.checkForAutomatonType(submission);
            } else if (arguments.getMode().isGrammar()) {
                submissionType = GrammarTypeTest.checkForGrammarType(submission);
            }
        }

        if(arguments.getMode().isParameterized()){
            setResultsused = true;
            SetsTest setsTest = new SetsTest();
            setsTest.setJflapXml(arguments.getStudentAnswer());
            setsTest.setStudentStatesSet(arguments.getStates());
            setsTest.setStudentInitialsSet(arguments.getInitials());
            setsTest.setStudentFinalsSet(arguments.getFinals());
            setsTest.setStudentAlphabetSet(arguments.getAlphabet());
            setsTest.setStudentStackAlphabetSet(arguments.getStackalphabet());
            setsTest.setStudentTransitionsSet(arguments.getTransitions());

            setsTest.gradeSets();
            statesResult = setsTest.getStatesResult();
            initialsResult = setsTest.getInitialsResult();
            finalsResult = setsTest.getFinalsResult();
            alphabetResult = setsTest.getAlphabetResult();
            stackAlphabetResult = setsTest.getStackAlphabetResult();
            transitionsResult = setsTest.getTransitionsResult();
        }

        result = new Result(submission, percentageFailed, submissionType);
        result.setExtraText(extraText);

        if(setResultsused){
            result.setStates(statesResult);
            result.setInitials(initialsResult);
            result.setFinals(finalsResult);
            result.setAlphabet(alphabetResult);
            result.setStackalphabet(stackAlphabetResult);
            result.setTransitions(transitionsResult);
        }

        long runningTime = new Date().getTime() - start;
        result.setTime(runningTime);
        return result;
    }

    public static Result generateResultWithTimeout(Arguments arguments, long timeOutSeconds) throws GraFlapException {
        final Result[] result = {null};
        final Arguments args = arguments;
        final String[] exeptionmessage = {"nothing"};
        TimeoutBlock timeoutBlock = new TimeoutBlock(timeOutSeconds);

         Callable<String> block = new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    result[0] = generateResult(args);
                }catch(GraFlapException e){
                    exeptionmessage[0] = e.getMessage();
                }
                if (!exeptionmessage[0].equals("nothing")){
                	throw new GraFlapException(exeptionmessage[0]);
                }
                return "ready";
            }
        };

        timeoutBlock.addBlock(block);

        return result[0];
    }


}
