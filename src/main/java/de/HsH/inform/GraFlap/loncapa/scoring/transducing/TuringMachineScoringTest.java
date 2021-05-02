package de.HsH.inform.GraFlap.loncapa.scoring.transducing;

import de.HsH.inform.GraFlap.loncapa.entity.ValuePair;
import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;
import de.HsH.inform.GraFlap.JFlapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JFlapWrapper.automaton.TuringMachine;

import java.util.HashMap;

/**
 * child class to generate the resulting score for test words and a given turing machine
 * @author Benjamin Held (06-23-2016)
 * @since 06-28-2016
 * @version 0.1.0
 */
public class TuringMachineScoringTest extends TransducerScoringTest<TuringMachine> {

    public TuringMachineScoringTest(Automaton automaton, HashMap<String, String> input) throws GraFlapException {
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
