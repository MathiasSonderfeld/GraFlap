package de.HsH.inform.GraFlap.JflapWrapper.words;

import java.util.HashMap;

/**
 * static helper class to extract the test words coded into a provided string
 * @author Ufuk Tosun (2012)
 * @author Frauke Sprengel (2015)
 * @author Benjamin Held (04-15-2016)
 * @since 06-25-2016
 * @version 0.3.5
 */
public class WordSeparator {
    /**
     * a factor that specifies the ration between right and wrong words
     */
    private static final int WRONG_WORD_RATIO_FACTOR = 3;

    /**
     * method to split the array with the words to be accepted and to be not accepted in two separate arrays
     * @param arrayToSplit the array with the words to be accepted and not accepted
     * @param numberOfAcceptedWords the number of words to be accepted
     * @return a {@link HashMap} mapping {keyword, String[]} which holds the string array for the accepted and non accepted
     * words
     */
    public static HashMap<String, String[]> splitAcceptedAndNotAcceptedWords(String[] arrayToSplit, int numberOfAcceptedWords) {
        String[] rightWords = new String[numberOfAcceptedWords];
        String[] wrongWords = new String[numberOfAcceptedWords * WRONG_WORD_RATIO_FACTOR];
        HashMap<String, String[]> words = new HashMap<>();

        for (int i = 0; i < arrayToSplit.length; i++) {
            if (i >= numberOfAcceptedWords) {
                wrongWords[i - numberOfAcceptedWords] = arrayToSplit[i];
            } else {
                rightWords[i] = arrayToSplit[i];
            }
        }
        words.put("rightWords", rightWords);
        words.put("wrongWords", wrongWords);
        return words;
    }
}
