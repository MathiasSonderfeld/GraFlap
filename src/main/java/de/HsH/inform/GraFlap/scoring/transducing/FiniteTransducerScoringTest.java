package de.HsH.inform.GraFlap.scoring.transducing;

import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.entity.ValuePair;
import de.HsH.inform.GraFlap.exception.GraFlapException;

import java.util.HashMap;

/**
 * child class to generate the resulting score for test words and a given finite transducer
 * @author Benjamin Held (07-05-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class FiniteTransducerScoringTest extends TransducerScoringTest<Automaton> {

    public FiniteTransducerScoringTest(Automaton automaton, HashMap<String, String> input) throws GraFlapException {
        super(automaton, input);
    }

    /**
     * method to test the provided words against the given machine
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    @Override
    protected void testing() throws GraFlapException {
        for (String input: inputOutputPairs.keySet()) {
            ValuePair<String, Boolean> resultPair = testInput(input);
            String result = resultPair.getKey();
            if (!result.equals(inputOutputPairs.get(input)) || !resultPair.getValue()) {
                resultScore++;
            }
        }
    }
}