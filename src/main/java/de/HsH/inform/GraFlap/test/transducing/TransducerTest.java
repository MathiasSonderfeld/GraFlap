package de.HsH.inform.GraFlap.test.transducing;


import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.JflapWrapper.exception.AutomatonMismatchException;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.scoring.transducing.FiniteTransducerScoringTest;
import de.HsH.inform.GraFlap.scoring.transducing.TuringMachineScoringTest;

import java.util.HashMap;

/**
 * abstract class to serve as a parent class for testing mechanisms of transducing components like turing machines
 * and mealy or moore machines
 * @author Benjamin Held (06-19-2016)
 * @version {@value GraFlap#version}
 */
abstract class TransducerTest{



    /**
     * abstract method to open the input and extract the test words from the provided word string
     * @param studentInput the submission of the student
     * @param wordString a string with concatenated test words
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    public abstract int determineResult(Submission<Automaton> studentInput, String wordString) throws GraFlapException;

    /**
     * method to test words with a given submission
     * @param automaton the object transformation of the submission that should be used for testing
     * @param input input words and output that should be tested
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    int testInput(Automaton automaton, HashMap<String, String> input) throws GraFlapException {
        if (automaton.isTuringMachine()) {
            return new TuringMachineScoringTest(automaton, input).returnScore();
        } else if (automaton.isMealyMachine() || automaton.isMooreMachine()) {
            return new FiniteTransducerScoringTest(automaton, input).returnScore();
        } else {
            throw new AutomatonMismatchException("Error: Provided automaton is not a transducer machine.");
        }
    }
}