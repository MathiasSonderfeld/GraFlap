package de.HsH.inform.GraFlap.test.accepting;

import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.scoring.accepting.GrammarScoringTest;
import de.HsH.inform.GraFlap.words.GenerateWords;
import de.HsH.inform.GraFlap.words.WordSeparator;
import de.HsH.inform.GraFlap.entity.Submission;
import de.HsH.inform.GraFlap.grammar.Grammar;

import java.util.HashMap;

/**
 *  child class of test to open and test a given grammar or regex
 *  @author Ufuk Tosun (11-29-2012)
 *  @author Benjamin Held (04-06-2016)
 *  @since 06-23-2016
 *  @version 0.6.3
 */
public class GrammarTest extends AcceptingTest<Grammar> {

    /**
     * method to test the given words an generate the appropriate score
     * @param obj the object transformation of the submission that should be used for testing
     * @param rightWords generated words that should be accepted
     * @param wrongWords generated words that should be rejected
     * @return the result score of the testing
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    @Override
    int testInput(Grammar obj, String[] rightWords, String[] wrongWords) throws GraFlapException {
        GrammarScoringTest scoringTest = new GrammarScoringTest(obj, rightWords, wrongWords);
        return scoringTest.returnScore();
    }

    /**
     * implementation of the abstract method from the parent class to open the input and extract the test words
     * from the provided word string
     * @param solution the reference solution coded in a string
     * @param studentInput the submission of the student
     * @param wordString a string with concatenated test words
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a GraFlapException that occurs further within the calling hierarchy
     */
    @Override
    public int openInput(String solution, Submission<Grammar> studentInput, String wordString) throws GraFlapException {
        HashMap<String, String[]> words = generateTestWordsFromString(solution, wordString);
        return testInput(studentInput.getSubmissionObject(), words.get("rightWords"), words.get("wrongWords"));
    }

    /**
     * implementation of the abstract method from the parent class to open the input and generate the required
     * number of test words specified by numberOfWordsToBeGenerated
     * @param solution the reference solution coded in a string
     * @param studentInput the submission of the student
     * @param numberOfWordsToBeGenerated number that specifies the required number of test words
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a GraFlapException that occurs further within the calling hierarchy
     */
    @Override
    public int openInput(String solution, Submission<Grammar> studentInput, int numberOfWordsToBeGenerated) throws GraFlapException {
        GenerateWords generateWords = new GenerateWords(numberOfWordsToBeGenerated);
        HashMap<String, String[]> words = WordSeparator.splitAcceptedAndNotAcceptedWords(
                                                        generateWords.generateWordsForGrammar(solution),
                                                        numberOfWordsToBeGenerated);
        return testInput(studentInput.getSubmissionObject(), words.get("rightWords"), words.get("wrongWords"));
    }
}
