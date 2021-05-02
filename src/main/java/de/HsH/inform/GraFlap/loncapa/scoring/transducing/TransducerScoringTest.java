package de.HsH.inform.GraFlap.loncapa.scoring.transducing;

import de.HsH.inform.GraFlap.loncapa.entity.ValuePair;
import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;
import de.HsH.inform.GraFlap.loncapa.scoring.ScoringTest;
import de.HsH.inform.GraFlap.JFlapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JFlapWrapper.simulation.AutomatonSimulator;

import java.util.HashMap;

/**
 * child class to generate the resulting score for test words and a given transducer
 * @author Benjamin Held (06-23-2016)
 * @since 06-28-2016
 * @version 0.1.0
 */
abstract class TransducerScoringTest<T extends Automaton> extends ScoringTest<T> {

    HashMap<String, String> inputOutputPairs;

    TransducerScoringTest(T automaton, HashMap<String, String> input) throws GraFlapException {
        super(automaton);
        inputOutputPairs = input;
        testing();
        resultScore = calculateResultScore();
    }

    /**
     * method to test a given input word against the given machine
     * @param input a given test word
     * @return the output to the provided input and the information if the input has been accepted in
     * a {@link ValuePair}
     */
    ValuePair<String, Boolean> testInput(String input) {
        AutomatonSimulator simulator = new AutomatonSimulator(object);
        return new ValuePair<>(simulator.testAndGetResult(input), simulator.acceptInput(input));
    }

    /**
     * implementation of the abstract method to calculate the resulting score for the transducers
     * @return the calculated score
     */
    @Override
    protected int calculateResultScore() {
        return resultScore * 100/ inputOutputPairs.keySet().size();
    }
}
