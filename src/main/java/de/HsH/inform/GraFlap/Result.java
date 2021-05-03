package de.HsH.inform.GraFlap;

import de.HsH.inform.GraFlap.entity.*;
import de.HsH.inform.GraFlap.grammar.GrammarBuilder;
import de.HsH.inform.GraFlap.convert.CYKInputParser;
import de.HsH.inform.GraFlap.convert.ConvertSubmission;
import de.HsH.inform.GraFlap.convert.DerivationParser;
import de.HsH.inform.GraFlap.scoring.cyk.CYKScoringTest;
import de.HsH.inform.GraFlap.scoring.derivation.DerivationScoringTest;
import de.HsH.inform.GraFlap.test.accepting.*;
import de.HsH.inform.GraFlap.test.transducing.TransducerPairTest;
import de.HsH.inform.GraFlap.test.transducing.TransducerWordTest;
import de.HsH.inform.GraFlap.typetest.AutomatonTypeTest;
import de.HsH.inform.GraFlap.typetest.GrammarTypeTest;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.test.*;
import de.HsH.inform.GraFlap.grammar.Grammar;

/**
 * helper class for the main method to determine the result of the submission
 * @author Benjamin Held (04-17-2016)
 * @since 08-03-2016
 * @version 0.2.3
 */
public class Result {
    /**
     * a string representing the submission type
     */
    private String studType;
    /**
     * a numeric value that holds the grading information; can have values in [0, 100]
     */
    private int result;
    /**
     * the determined mode
     */
    private OperationMode operationMode;

    /**
     * the {@link Submission} object that encapsulated the required submission data
     */
    private Submission submission;

    /**
     * Constructor
     * @param operationMode the determined mode used to generate the correct result
     */
    public Result( OperationMode operationMode ) {
        this.submission = new Submission();
        this.studType = "non";
        this.result = 100;
        this.operationMode = operationMode;
    }

    /**
     * simple getter
     * @return the type of the submission
     */
    public String getStudType() {
        return studType;
    }

    /**
     * simple getter
     * @return the numeric result of the submission
     */
    public int getResult() {
        return result;
    }

    /**
     * simple getter
     * @return the {@link Submission} object of the de.HsH.inform.GraFlap.entity submission
     * @throws GraFlapException throws a {@link GraFlapException} if the de.HsH.inform.GraFlap.entity submission has not been stored in it
     */
    public Submission getSubmission() throws GraFlapException {
        if (submission.getInputType() != JFFType.UNDEFINED) {
            return submission;
        }
        throw new GraFlapException("Error in Result: Tried to access non parsed submission.");
    }

    /**
     * starts the process to generate the result and the grading
     * @param arguments the {@link Arguments} object that holds the submission information
     * @return a reference of the object
     * @throws GraFlapException throws a {@link GraFlapException} if an error occurs
     */
    public Result generateResult( Arguments arguments) throws GraFlapException {
        switch(operationMode) {
            case ERROR:
                throw new GraFlapException("Error in LON-CAPA problem. Please check mode variable.");
            case AR:
                if (!arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                    result = new AutomatonRegexTest().openInput(arguments.getSolution(), submission,
                                                                arguments.getNumberOfWords());
                } else {
                    throw new GraFlapException("Error in LON-CAPA problem. Please check regular expression.");
                }
                break;
            case AG:
                if (arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                    result = new AutomatonTest().openInput(arguments.getSolution(), submission,
                                                           arguments.getNumberOfWords());

                } else {
                    throw new GraFlapException("Error in LON-CAPA problem. Please check given grammar.");
                }

                break;
            case GG:
                if (arguments.getStudentAnswer().contains("->") && arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openGrammar(GrammarBuilder.
                                                   buildGrammar(arguments.getStudentAnswer()));
                    result = new GrammarTest().openInput(arguments.getSolution(), submission,
                                                         arguments.getNumberOfWords());
                } else {
                    throw new GraFlapException("Error. Please check grammar.");
                }
                break;
            case ARW:
                if (!arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                    result = new AutomatonTest().openInput(arguments.getSolution(), submission,
                                                           arguments.getWordString());
                }else {
                    throw new GraFlapException("Error in LON-CAPA problem. Please check regular expression.");
                }
                break;
            case AGW:
                if (arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                    result = new AutomatonTest().openInput(arguments.getSolution(), submission,
                                                           arguments.getWordString());
                } else {
                    throw new GraFlapException("Error in LON-CAPA problem. Please check given grammar.");
                }
                break;
            case GGW:
                if (arguments.getStudentAnswer().contains("->") && arguments.getSolution().contains("->")) {
                    submission = ConvertSubmission.openGrammar(GrammarBuilder.
                                                               buildGrammar(arguments.getStudentAnswer()));
                    result = new GrammarTest().openInput(arguments.getSolution(), submission,
                                                         arguments.getWordString());
                }else {
                    throw new GraFlapException("Error. Please check grammar.");
                }
                break;
            case EAT:
                submission = ConvertSubmission.openGrammar(GrammarBuilder.buildGrammar(arguments.getStudentAnswer()));
                result = new AlphabetTest(submission).checkAlphabet(arguments.getSolution());
                break;
            case WW:
                submission = ConvertSubmission.openWords(arguments.getStudentAnswer(), arguments.getNumberOfWords());
                result = WordTest.checkWords(arguments.getSolution(), (String[]) submission.getSubmissionObject());
                break;
            case GR:
                if (arguments.getStudentAnswer().contains("->") ) {
                    submission = ConvertSubmission.openGrammar(GrammarBuilder.
                                                   buildGrammar(arguments.getStudentAnswer()));
                    studType = GrammarTypeTest.checkForGrammarType(submission);
                    if ((studType.equals("rl") || studType.equals("cfg"))) {
                        result = new GrammarRegexTest().openInput(arguments.getSolution(), submission,
                                                                  arguments.getNumberOfWords());
                    } else {
                        throw new GraFlapException("Error. Please check grammar type.");
                    }
                } else {
                    throw new GraFlapException("Error. Please check grammar.");
                }
                break;
            case GRW:
                if (arguments.getStudentAnswer().contains("->") ) {
                    submission = ConvertSubmission.openGrammar(GrammarBuilder.
                                                   buildGrammar(arguments.getStudentAnswer()));
                    studType = GrammarTypeTest.checkForGrammarType(submission);
                    if ((studType.equals("rl") || studType.equals("cfg"))) {
                        result = new GrammarTest().openInput(arguments.getSolution(), submission,
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
                studType = AutomatonTypeTest.checkForAutomatonType(submission);
                result = new TransducerPairTest().determineResult(submission, arguments.getWordString());
                break;
            case MMW:
                submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                studType = AutomatonTypeTest.checkForAutomatonType(submission);
                result = new TransducerWordTest(arguments.getSolution()).determineResult(submission,
                                                                                         arguments.getWordString());
                break;
            case CYK:
                Submission<Grammar> solution = ConvertSubmission.openGrammar(
                                               GrammarBuilder.buildGrammar(arguments.getSolution()));
                submission = CYKInputParser.openCYKInput(arguments.getStudentAnswer(), arguments.getWordString(),
                                                         solution.getSubmissionObject());
                result = new CYKScoringTest((CYKTable) submission.getSubmissionObject(), arguments.getWordString(),
                                             solution.getSubmissionObject()).returnScore();
                break;
            case DER:
                submission = DerivationParser.openDerivation(arguments.getStudentAnswer());
                solution = ConvertSubmission.openGrammar(GrammarBuilder.buildGrammar(arguments.getSolution()));
                result = new DerivationScoringTest((String[]) submission.getSubmissionObject(),
                                                    solution.getSubmissionObject(), arguments.getWordString())
                                                    .returnScore();
                break;
            case SVGA:
                submission = ConvertSubmission.openAutomaton(arguments.getStudentAnswer());
                studType = AutomatonTypeTest.checkForAutomatonType(submission);
                result = 0;
                break;
            case SVGG:
                submission = ConvertSubmission.openGrammar(GrammarBuilder.buildGrammar(arguments.getStudentAnswer()));
                studType = GrammarTypeTest.checkForGrammarType(submission);
                result = 0;
                break;
        }
        return this;
    }
}
