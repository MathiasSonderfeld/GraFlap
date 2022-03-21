package de.HsH.inform.GraFlap.scoring.transducing;

import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JflapWrapper.simulation.AutomatonSimulator;
import de.HsH.inform.GraFlap.entity.ValuePair;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.scoring.ScoringTest;

import java.util.HashMap;

/**
 * child class to generate the resulting score for test words and a given transducer
 * @author Benjamin Held (06-23-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
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
