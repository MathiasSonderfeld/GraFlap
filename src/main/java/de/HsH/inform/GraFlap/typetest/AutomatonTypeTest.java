package de.HsH.inform.GraFlap.typetest;

import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JflapWrapper.automaton.DeterminismChecker;
import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.entity.Type;
import de.HsH.inform.GraFlap.exception.GraFlapException;

/**
 * static helper class to determine the type of an automaton
 * @author Frauke Sprengel
 * @author Benjamin Held (04-10-2016)
 * @version {@value GraFlap#version}
 */
public class AutomatonTypeTest {

    /**
     * static method to determine the type of an automaton that is given by a automaton
     * @param automatonSubmission the automaton that should be tested
     * @return the type of automaton as a string: (n|d)(fa|pda|tm)
     * @throws GraFlapException throws a {@link GraFlapException} if there is a problem with the submission
     */
    public static Type checkForAutomatonType(Submission<Automaton> automatonSubmission) throws GraFlapException {
        boolean isDeterministic = DeterminismChecker.isDeterministic(automatonSubmission.getSubmissionObject());
        switch(automatonSubmission.getSubmissionObject().testType()){
            case FA:
                if(isDeterministic) return Type.DFA;
                else return Type.NFA;
            case PDA:
                if(isDeterministic) return Type.DPDA;
                else return Type.NPDA;
            case TM:
                if(isDeterministic) return Type.DTM;
                else return Type.NTM;
            default:
                return automatonSubmission.getSubmissionObject().testType();
        }
    }
}