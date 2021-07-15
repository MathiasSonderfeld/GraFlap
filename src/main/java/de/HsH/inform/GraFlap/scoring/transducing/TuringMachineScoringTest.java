package de.HsH.inform.GraFlap.scoring.transducing;

import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JflapWrapper.automaton.TuringMachine;
import de.HsH.inform.GraFlap.entity.ValuePair;
import de.HsH.inform.GraFlap.exception.GraFlapException;

import java.util.HashMap;

/**
 * child class to generate the resulting score for test words and a given turing machine
 * @author Benjamin Held (06-23-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class TuringMachineScoringTest extends TransducerScoringTest<TuringMachine> {

    public TuringMachineScoringTest( Automaton automaton, HashMap<String, String> input) throws GraFlapException {
        super(new TuringMachine(automaton.getJFLAPAutomaton()), input);
    }

    /**
     * method to test the provided words against the given machine
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    @Override
    protected void testing() throws GraFlapException {
        String blank = object.getBlankSymbol();
        for (String input: inputOutputPairs.keySet()) {
            ValuePair<String, Boolean> resultPair = testInput(input);
            String result = resultPair.getKey().replaceAll(blank,"").replaceAll(" ","");
            if (!result.equals(inputOutputPairs.get(input)) || !resultPair.getValue()) {
                resultScore++;
            }
        }
    }
}
