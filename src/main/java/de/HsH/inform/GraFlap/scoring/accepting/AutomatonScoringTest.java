package de.HsH.inform.GraFlap.scoring.accepting;

import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JflapWrapper.simulation.AutomatonSimulator;
import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.exception.GraFlapException;

/**
 *  child class to generate the resulting score for test words and a given automaton
 *  @author Benjamin Held (05-30-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class AutomatonScoringTest extends AcceptingScoringTest<Automaton> {

    /**
     * constructor
     * @param object the automaton that should be used to test the words
     * @param testwords a list of words for testing
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    public AutomatonScoringTest( Automaton object, Testwords testwords) throws GraFlapException {
        super(object, testwords);
    }

    /**
     * method to test the provided words against the given automaton
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    @Override
    protected void testing() throws GraFlapException {
        AutomatonSimulator automatonSimulator = new AutomatonSimulator(object);
        for (String input : testwords.getCorrectWords()) {
            if (automatonSimulator.acceptInput(input)) {
                correctWordsCount--;
            }
        }

        for (String input : testwords.getFailingWords()) {
            if (!automatonSimulator.acceptInput(input)) {
                wrongWordsCount--;
            }
        }
    }
}