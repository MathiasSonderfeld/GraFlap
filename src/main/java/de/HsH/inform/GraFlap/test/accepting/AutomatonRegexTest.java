package de.HsH.inform.GraFlap.test.accepting;

import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.JflapWrapper.words.GenerateWords;
import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.exception.GraFlapException;

/**
 * child class of {@link AutomatonTest} to separate the testing process with words from the testing process with
 * a regular expression
 * @author Benjamin Held (04-18-2016)
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value GraFlap#version}
 */
public class AutomatonRegexTest extends AutomatonTest {

    /**
     * overwriting of the parent implementation to generate the test words through a regular expression
     * @param solution the reference solution coded in a string
     * @param studentInput the submission of the student
     * @param numberOfWordsToBeGenerated number that specifies the required number of test words
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    @Override
    public int openInput( String solution, Submission<Automaton> studentInput, int numberOfWordsToBeGenerated) throws GraFlapException {
        GenerateWords generateWords = new GenerateWords(numberOfWordsToBeGenerated);
        Testwords testwords = generateWords.generateTestWords(solution);
        return testInput(studentInput.getSubmissionObject(), testwords);
    }
}
