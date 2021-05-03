package de.HsH.inform.GraFlap.test.accepting;

import de.HsH.inform.GraFlap.words.GenerateWords;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.Submission;
import de.HsH.inform.GraFlap.grammar.Grammar;

import java.util.HashMap;

/**
 * child class of {@link GrammarTest} to separate the testing process with words from the testing process with
 * a regular expression
 * @author Benjamin Held (04-18-2016)
 * @since 06-16-2016
 * @version 0.2.2
 */
public class GrammarRegexTest extends GrammarTest {

    /**
     * overwriting of the parent implementation to generate the test words through a regular expression
     * @param solution the reference solution coded in a string
     * @param studentInput the submission of the student
     * @param numberOfWordsToBeGenerated number that specifies the required number of test words
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    @Override
    public int openInput(String solution, Submission<Grammar> studentInput, int numberOfWordsToBeGenerated) throws GraFlapException {
        HashMap<String, String[]> words = new HashMap<>();
        GenerateWords generateWords = new GenerateWords(numberOfWordsToBeGenerated);
        words.put("rightWords", generateWords.generateRightWordForRegex(solution));
        words.put("wrongWords", generateWords.generateWrongWordsForRegex(solution));
        return testInput(studentInput.getSubmissionObject(), words.get("rightWords"), words.get("wrongWords"));
    }
}
