package de.HsH.inform.GraFlap.test.accepting;

import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.JflapWrapper.words.GenerateWords;
import de.HsH.inform.GraFlap.exception.GraFlapException;

import java.util.HashMap;

/**
 * child class of {@link AutomatonTest} to separate the testing process with words from the testing process with
 * a regular expression
 * @author Benjamin Held (04-18-2016)
 * @since 06-16-2016
 * @version 0.2.2
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
        HashMap<String, String[]> words = new HashMap<>();
        GenerateWords generateWords = new GenerateWords(numberOfWordsToBeGenerated);
        words.put("rightWords", generateWords.generateRightWordForRegex(solution));
        words.put("wrongWords", generateWords.generateWrongWordsForRegex(solution));
        return testInput(studentInput.getSubmissionObject(), words.get("rightWords"), words.get("wrongWords"));
    }
}
