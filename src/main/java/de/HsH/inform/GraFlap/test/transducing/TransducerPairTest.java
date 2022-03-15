package de.HsH.inform.GraFlap.test.transducing;

import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.JflapWrapper.words.MachineInputOutputParser;
import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.exception.GraFlapException;

/**
 * child class to to start the testing mechanism when receiving input/output pairs
 * @author Benjamin Held (06-30-2016)
 * @version {@value GraFlap#version}
 */
public class TransducerPairTest extends TransducerTest {

    /**
     * method to open the input and extract the test words from the provided word string
     * @param studentInput the submission of the student
     * @param testwords the testword pairs
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    @Override
    public int determineResult(Submission<Automaton> studentInput, Testwords testwords) throws GraFlapException {
        return testInput(studentInput.getSubmissionObject(), testwords.getWordpairs());
    }

}
