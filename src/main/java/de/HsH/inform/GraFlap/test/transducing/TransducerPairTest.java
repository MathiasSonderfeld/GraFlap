package de.HsH.inform.GraFlap.test.transducing;

import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.words.MachineInputOutputParser;
import de.HsH.inform.GraFlap.entity.Submission;
import de.HsH.inform.GraFlap.automaton.Automaton;

/**
 * child class to to start the testing mechanism when receiving input/output pairs
 * @author Benjamin Held (06-30-2016)
 * @since 07-02-2016
 * @version 0.1.0
 */
public class TransducerPairTest extends TransducerTest {

    /**
     * method to open the input and extract the test words from the provided word string
     * @param studentInput the submission of the student
     * @param wordString a string with concatenated test words
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    @Override
    public int determineResult(Submission<Automaton> studentInput, String wordString) throws GraFlapException {
        return testInput(studentInput.getSubmissionObject(), new MachineInputOutputParser(wordString).getMapping());
    }
}
