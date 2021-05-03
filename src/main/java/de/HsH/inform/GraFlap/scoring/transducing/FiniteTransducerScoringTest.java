package de.HsH.inform.GraFlap.scoring.transducing;

import de.HsH.inform.GraFlap.entity.ValuePair;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.automaton.Automaton;

import java.util.HashMap;

/**
 * child class to generate the resulting score for test words and a given finite transducer
 * @author Benjamin Held (07-05-2016)
 * @since 07-11-2016
 * @version 0.1.0
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