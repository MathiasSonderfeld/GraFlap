package de.HsH.inform.GraFlap.typetest;

import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JflapWrapper.automaton.DeterminismChecker;
import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;

/**
 * static helper class to determine the type of an automaton
 * @author Frauke Sprengel
 * @author Benjamin Held (04-10-2016)
 * @since 05-09-2016
 * @version 0.2.8
 */
public class AutomatonTypeTest {

    /**
     * static method to determine the type of an automaton that is given by a automaton
     * @param automatonSubmission the automaton that should be tested
     * @return the type of automaton as a string: (n|d)(fa|pda|tm)
     * @throws GraFlapException throws a {@link GraFlapException} if there is a problem with the submission
     */
    public static String checkForAutomatonType(Submission<Automaton> automatonSubmission) throws GraFlapException {
        return testDeterminism(automatonSubmission.getSubmissionObject()) +
               testType(automatonSubmission.getSubmissionObject());
    }

    /**
     * static method to get the working type of the automaton
     * @param a the automaton that should be tested
     * @return the type of automaton as a string (fa|pda|tm) or error if it is none of them
     */
    private static String testType(Automaton a){
        return a.testType();
    }

    /**
     * static method to determine if the automaton is deterministic or not
     * @param a the automaton that should be tested
     * @return indicator for (non-)determinism as string: (n|d)
     */
    private static String testDeterminism(Automaton a){
        if (DeterminismChecker.isDeterministic(a)) {
            return "d";
        }
        return "n";
    }
}