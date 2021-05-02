package de.HsH.inform.GraFlap.loncapa.main;

import de.HsH.inform.GraFlap.loncapa.GrammarBuilder;
import de.HsH.inform.GraFlap.loncapa.convert.CYKInputParser;
import de.HsH.inform.GraFlap.loncapa.convert.ConvertSubmission;
import de.HsH.inform.GraFlap.loncapa.convert.DerivationParser;
import de.HsH.inform.GraFlap.loncapa.entity.CYKTable;
import de.HsH.inform.GraFlap.loncapa.entity.InputType;
import de.HsH.inform.GraFlap.loncapa.entity.Mode;
import de.HsH.inform.GraFlap.loncapa.scoring.cyk.CYKScoringTest;
import de.HsH.inform.GraFlap.loncapa.scoring.derivation.DerivationScoringTest;
import de.HsH.inform.GraFlap.loncapa.test.accepting.*;
import de.HsH.inform.GraFlap.loncapa.test.transducing.TransducerPairTest;
import de.HsH.inform.GraFlap.loncapa.test.transducing.TransducerWordTest;
import de.HsH.inform.GraFlap.loncapa.typetest.AutomatonTypeTest;
import de.HsH.inform.GraFlap.loncapa.typetest.GrammarTypeTest;
import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;
import de.HsH.inform.GraFlap.loncapa.test.*;
import de.HsH.inform.GraFlap.JFlapWrapper.Submission;
import de.HsH.inform.GraFlap.JFlapWrapper.grammar.Grammar;

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
    private Mode mode;

    /**
     * the {@link Submission} object that encapsulated the required submission data
     */
    private Submission submission;

    /**
     * Constructor
     * @param mode the determined mode used to generate the correct result
     */
    public Result(Mode mode) {
        this.submission = new Submission();
        this.studType = "non";
        this.result = 100;
        this.mode = mode;
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
     * @return the {@link Submission} object of the de.HsH.inform.GraFlap.loncapa submission
     * @throws GraFlapException throws a {@link GraFlapException} if the de.HsH.inform.GraFlap.loncapa submission has not been stored in it
     */
    public Submission getSubmission() throws GraFlapException {
        if (submission.getInputType() != InputType.UNDEFINED) {
            return submission;
        }
        throw new GraFlapException("Error in Result: Tried to access non parsed submission.");
    }

    /**
     * starts the process to generate the result and the grading
     * @param arguments the {@link LoncapaArguments} object that holds the submission information
     * @return a reference of the object
     * @throws GraFlapException throws a {@link GraFlapException} if an error occurs
     */
    public Result generateResult(LoncapaArguments arguments) throws GraFlapException {
        switch(mode) {
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
