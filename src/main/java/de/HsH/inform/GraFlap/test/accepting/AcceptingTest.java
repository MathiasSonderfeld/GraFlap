package de.HsH.inform.GraFlap.test.accepting;

import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.JflapWrapper.words.GenerateWords;
import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.exception.GraFlapException;

/**
 * abstract class to serve as a parent class for different testing mechanisms of accepting components like grammars
 * and accepting automatons
 * @author Benjamin Held (04-14-2016)
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value GraFlap#version}
 */
public abstract class AcceptingTest<T extends Object> {

    /**
     * method to test words with a given submission
     * @param obj the object transformation of the submission that should be used for testing
     * @param testwords generated words for testing
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    abstract int testInput(T obj, Testwords testwords) throws GraFlapException;

    /**
     * method to generate correct and wrong test words for the given solution based on a string with coded input words
     * @param solution the given solution
     * @param testwords the test words
     * @return a hashmap mapping {keyword, String[]} which holds the string array for the accepted and non accepted
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    Testwords generateTestWordsFromString(String solution, Testwords testwords) throws GraFlapException {
        if (solution.contains("->")) {
            GenerateWords generateWords = new GenerateWords(10);
            generateWords.checkWrongGrammarWords(solution, testwords);
        }
        return testwords;
    }

    /**
     * abstract method to open the input and extract the test words from the provided word string
     * @param solution the reference solution coded in a string
     * @param studentInput the submission of the student
     * @param testwords a string with concatenated test words
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    public abstract int openInput( String solution, Submission<T> studentInput, Testwords testwords) throws GraFlapException;

    /**
     * abstract method to open the input and generate the required number of test words specified
     * by numberOfWordsToBeGenerated
     * @param solution the reference solution coded in a string
     * @param studentInput the submission of the student
     * @param numberOfWordsToBeGenerated number that specifies the required number of test words
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    public abstract int openInput(String solution, Submission<T> studentInput, int numberOfWordsToBeGenerated)
                                  throws GraFlapException;
}