package de.HsH.inform.GraFlap.test.accepting;

import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.JflapWrapper.words.GenerateWords;
import de.HsH.inform.GraFlap.JflapWrapper.words.WordSeparator;
import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.scoring.accepting.AutomatonScoringTest;

import java.util.HashMap;

/**
 *  child class of test to open and test a given automaton or regex
 *  @author Ufuk Tosun (11-13-2012)
 *  @author Benjamin Held (04-10-2016)
 *  @since 06-23-2016
 *  @version 0.6.3
 */
public class AutomatonTest extends AcceptingTest<Automaton> {

    /**
     * method to test the given words an generate the appropriate score
     * @param obj the object transformation of the submission that should be used for testing
     * @param rightWords generated words that should be accepted
     * @param wrongWords generated words that should be rejected
     * @return the result score of the testing
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    @Override
    int testInput(Automaton obj, String[] rightWords, String[] wrongWords) throws GraFlapException {
        AutomatonScoringTest scoringTest = new AutomatonScoringTest(obj, rightWords, wrongWords);
        return scoringTest.returnScore();
    }

    /**
     * implementation of the abstract method from the parent class to open the input and extract the test words
     * from the provided word string
     * @param solution the reference solution coded in a string
     * @param studentInput the submission of the student
     * @param testwords the test words
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    @Override
    public int openInput( String solution, Submission<Automaton> studentInput, Testwords testwords ) throws GraFlapException {
        HashMap<String, String[]> words = generateTestWordsFromString(solution, testwords);
        return testInput(studentInput.getSubmissionObject(), words.get("rightWords"), words.get("wrongWords"));
    }

    /**
     * implementation of the abstract method from the parent class to open the input and generate the required
     * number of test words specified by numberOfWordsToBeGenerated
     * @param solution the reference solution coded in a string
     * @param studentInput the submission of the student
     * @param numberOfWordsToBeGenerated number that specifies the required number of test words
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    @Override
    public int openInput(String solution, Submission<Automaton> studentInput, int numberOfWordsToBeGenerated) throws GraFlapException {
        GenerateWords generateWords = new GenerateWords(numberOfWordsToBeGenerated);
        HashMap<String, String[]> words = WordSeparator.splitAcceptedAndNotAcceptedWords(
                                                        generateWords.generateWordsForGrammar(solution),
                                                        numberOfWordsToBeGenerated);
        return testInput(studentInput.getSubmissionObject(), words.get("rightWords"), words.get("wrongWords"));
    }

    public static String getType() {
        return "F";
    }
}
