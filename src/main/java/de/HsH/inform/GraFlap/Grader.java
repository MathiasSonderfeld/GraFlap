package de.HsH.inform.GraFlap;

import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.JflapWrapper.grammar.Grammar;
import de.HsH.inform.GraFlap.convert.CYKInputParser;
import de.HsH.inform.GraFlap.convert.ConvertSubmission;
import de.HsH.inform.GraFlap.convert.DerivationParser;
import de.HsH.inform.GraFlap.entity.*;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.scoring.cyk.CYKScoringTest;
import de.HsH.inform.GraFlap.scoring.derivation.DerivationScoringTest;
import de.HsH.inform.GraFlap.test.AlphabetTest;
import de.HsH.inform.GraFlap.test.SetsTest;
import de.HsH.inform.GraFlap.test.WordTest;
import de.HsH.inform.GraFlap.test.accepting.AutomatonRegexTest;
import de.HsH.inform.GraFlap.test.accepting.AutomatonTest;
import de.HsH.inform.GraFlap.test.accepting.GrammarRegexTest;
import de.HsH.inform.GraFlap.test.accepting.GrammarTest;
import de.HsH.inform.GraFlap.test.transducing.TransducerPairTest;
import de.HsH.inform.GraFlap.test.transducing.TransducerWordTest;
import de.HsH.inform.GraFlap.typetest.AutomatonTypeTest;
import de.HsH.inform.GraFlap.typetest.GrammarTypeTest;

import java.util.Arrays;

/**
 * helper class for the main method to determine the result of the submission
 * @author Benjamin Held (04-17-2016)
 * @since 08-03-2016
 * @version 0.2.3
 */
public class Grader {
     /**
     * starts the process to generate the result and the grading
     * @param arguments the {@link Arguments} object that holds the submission information
     * @return a reference of the object
     * @throws GraFlapException throws a {@link GraFlapException} if an error occurs
     */
    public static Result generateResult(Arguments arguments) throws GraFlapException {
        Submission submission = new Submission();
        int percentageFailed = -1;
        String submissionType = "";
        switch(arguments.getTaskMode()) {
            case ERROR:
                throw new GraFlapException("Error in LON-CAPA problem. Please check mode variable.");
            case AR:
            case ARP:
            case ART:
            case ARTP:
                if (!arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                    percentageFailed = new AutomatonRegexTest().openInput(arguments.getSolution(), submission,
                                                                arguments.getNumberOfWords());
                } else {
                    throw new GraFlapException("Error in LON-CAPA problem. Please check regular expression.");
                }
                break;
            case AG:
            case AGP:
            case AGT:
            case AGTP:
                if (arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                    percentageFailed = new AutomatonTest().openInput(arguments.getSolution(), submission,
                                                           arguments.getNumberOfWords());

                } else {
                    throw new GraFlapException("Error in LON-CAPA problem. Please check given grammar.");
                }

                break;
            case GG:
            case GGT:
                if (arguments.getStudentAnswer().contains("->") && arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openGrammar(GrammarBuilder.
                                                   buildGrammar(arguments.getStudentAnswer()));
                    percentageFailed = new GrammarTest().openInput(arguments.getSolution(), submission,
                                                         arguments.getNumberOfWords());
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
                    percentageFailed = new AutomatonTest().openInput(arguments.getSolution(), submission,
                                                           arguments.getWordString());
                }else {
                    throw new GraFlapException("Error in LON-CAPA problem. Please check regular expression.");
                }
                break;
            case AGW:
            case AGWP:
            case AGTW:
            case AGTWP:
                if (arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                    percentageFailed = new AutomatonTest().openInput(arguments.getSolution(), submission,
                                                           arguments.getWordString());
                } else {
                    throw new GraFlapException("Error in LON-CAPA problem. Please check given grammar.");
                }
                break;
            case GGW:
            case GGTW:
                if (arguments.getStudentAnswer().contains("->") && arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openGrammar(GrammarBuilder.
                                                               buildGrammar(arguments.getStudentAnswer()));
                    percentageFailed = new GrammarTest().openInput(arguments.getSolution(), submission,
                                                         arguments.getWordString());
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
                break;
            case GR:
            case GRT:
                if (arguments.getStudentAnswer().contains("->") ) {
                    submission = ConvertSubmission.openGrammar(GrammarBuilder.
                                                   buildGrammar(arguments.getStudentAnswer()));
                    submissionType = GrammarTypeTest.checkForGrammarType(submission);
                    if ((submissionType.equals("rl") || submissionType.equals("cfg"))) {
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
                    if ((submissionType.equals("rl") || submissionType.equals("cfg"))) {
                        percentageFailed = new GrammarTest().openInput(arguments.getSolution(), submission,
                                                             arguments.getWordString());
                    } else {
                        throw new GraFlapException("Error. Please check grammar type.");
                    }
                } else {
                    throw new GraFlapException("Error. Please check grammar.");
                }
                break;
            case MP:
                submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                submissionType = AutomatonTypeTest.checkForAutomatonType(submission);
                percentageFailed = new TransducerPairTest().determineResult(submission, arguments.getWordString());
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
                submission = CYKInputParser.openCYKInput(arguments.getStudentAnswer(), arguments.getWordString(),
                                                         solution.getSubmissionObject());
                percentageFailed = new CYKScoringTest((CYKTable) submission.getSubmissionObject(), arguments.getWordString(),
                                             solution.getSubmissionObject()).returnScore();
                break;
            case DER:
                submission = DerivationParser.openDerivation(arguments.getStudentAnswer());
                solution = ConvertSubmission.openGrammar(GrammarBuilder.buildGrammar(arguments.getSolution()));
                percentageFailed = new DerivationScoringTest((String[]) submission.getSubmissionObject(),
                                                    solution.getSubmissionObject(), arguments.getWordString())
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

        if (arguments.getTaskMode().isTyped()) {
            if (arguments.getTaskMode().isAutomaton()) {
                submissionType = AutomatonTypeTest.checkForAutomatonType(submission);
            } else if (arguments.getTaskMode().isGrammar()) {
                submissionType = GrammarTypeTest.checkForGrammarType(submission);
            }
        }
        Result result = new Result(submission, percentageFailed, submissionType);

        if(arguments.getTaskMode().isParameterized()){
            SetsTest setsTest = new SetsTest();
            setsTest.setJflapXml(arguments.getStudentAnswer());
            setsTest.setStudentStatesSet(arguments.getStates());
            setsTest.setStudentInitialsSet(arguments.getInitials());
            setsTest.setStudentFinalsSet(arguments.getFinals());
            setsTest.setStudentAlphabetSet(arguments.getAlphabet());
            setsTest.setStudentStackAlphabetSet(arguments.getStackalphabet());
            setsTest.setStudentTransitionsSet(arguments.getTransitions());

            setsTest.gradeSets();
            result.setStates(setsTest.getStatesResult());
            result.setInitials(setsTest.getInitialsResult());
            result.setFinals(setsTest.getFinalsResult());
            result.setAlphabet(setsTest.getAlphabetResult());
            result.setStackalphabet(setsTest.getStackAlphabetResult());
            result.setTransitions(setsTest.getTransitionsResult());
        }
        return result;
    }
}
