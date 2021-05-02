package de.HsH.inform.GraFlap.loncapa.test.accepting;

import de.HsH.inform.GraFlap.words.GenerateWords;
import de.HsH.inform.GraFlap.words.WordSeparator;
import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;
import de.HsH.inform.GraFlap.JFlapWrapper.Submission;

import java.util.HashMap;

/**
 * abstract class to serve as a parent class for different testing mechanisms of accepting components like grammars
 * and accepting automatons
 * @author Benjamin Held (04-14-2016)
 * @since 06-23-2016
 * @version 0.8.0
 */
public abstract class AcceptingTest<T extends Object> {

    /**
     * method to test words with a given submission
     * @param obj the object transformation of the submission that should be used for testing
     * @param rightWords generated words that should be accepted
     * @param wrongWords generated words that should be rejected
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    abstract int testInput(T obj, String[] rightWords, String[] wrongWords) throws GraFlapException;

    /**
     * method to generate correct and wrong test words for the given solution based on a string with coded input words
     * @param solution the given solution
     * @param wordString a string with coded test words
     * @return a hashmap mapping {keyword, String[]} which holds the string array for the accepted and non accepted
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    HashMap<String, String[]> generateTestWordsFromString(String solution, String wordString) throws GraFlapException {
        HashMap<String, String[]> words = new HashMap<>();
        words.put("rightWords", WordSeparator.getCorrectTestingWords(wordString));
        if (solution.contains("->")){
            GenerateWords generateWords = new GenerateWords(10);
            words.put("wrongWords", generateWords.checkWrongGrammarWords(solution, wordString));
        } else {
            words.put("wrongWords", WordSeparator.getWrongTestingWords(wordString));
        }
        return words;
    }

    /**
     * abstract method to open the input and extract the test words from the provided word string
     * @param solution the reference solution coded in a string
     * @param studentInput the submission of the student
     * @param wordString a string with concatenated test words
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    public abstract int openInput(String solution, Submission<T> studentInput,  String wordString) throws GraFlapException;

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