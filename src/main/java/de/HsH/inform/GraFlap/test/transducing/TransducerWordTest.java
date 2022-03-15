package de.HsH.inform.GraFlap.test.transducing;

import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JflapWrapper.automaton.TuringMachine;
import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.JflapWrapper.exception.AutomatonMismatchException;
import de.HsH.inform.GraFlap.JflapWrapper.simulation.AutomatonSimulator;
import de.HsH.inform.GraFlap.convert.ConvertSubmission;
import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.exception.GraFlapException;

import java.util.HashMap;

/**
 * child class to to start the testing mechanism when receiving test words
 * @author Benjamin Held (06-30-2016)
 * @version {@value GraFlap#version}
 */
public class TransducerWordTest extends TransducerTest {

    private final Automaton solution;

    public TransducerWordTest(String solution) throws GraFlapException {
        this.solution = ConvertSubmission.openAutomaton(solution).getSubmissionObject();
    }

    /**
     * method to open the input and extract the test words from the provided word string
     * @param studentInput the submission of the student
     * @param testwords the testwords
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    @Override
    public int determineResult(Submission<Automaton> studentInput, Testwords testwords) throws GraFlapException {
        return testInput(studentInput.getSubmissionObject(), produceOutput(testwords));
    }

    /**
     * method to produce testable output for the submission based on the test words and the given solution automaton
     * @param testwords the testwords
     * @return the mapping of input word to output word for every provided test word
     * @throws GraFlapException throws a {@link GraFlapException} is an input word is not accepted
     */
    private HashMap<String, String> produceOutput(Testwords testwords) throws GraFlapException {
        HashMap<String, String> pairs = new HashMap<>();
        AutomatonSimulator simulator = new AutomatonSimulator(solution);

        for (String word: testwords.getTestWordsList()) {
            if (simulator.acceptInput(word)) {
                String output = simulator.testAndGetResult(word);
                if (solution.isTuringMachine()) {
                    output = removeBlankSymbols(output);
                }
                pairs.put(word, output);
            } else {
                throw new GraFlapException("Error while creating output in TransducerWordTest: Input was not accepted.");
            }
        }

        return pairs;
    }

    /**
     * special method to remove the blank symbols from the output of a turing machine
     * @param output the generated output
     * @return the formatted output without blank symbol and whitespaces
     * @throws AutomatonMismatchException throws a {@link AutomatonMismatchException} that occurs further within
     * the calling hierarchy
     */
    private String removeBlankSymbols(String output) throws AutomatonMismatchException {
        String blank = new TuringMachine(solution.getJFLAPAutomaton()).getBlankSymbol();
        return output.replaceAll(blank,"").replaceAll(" ","");
    }
}