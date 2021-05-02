package de.HsH.inform.GraFlap.loncapa.test;

import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;
import de.HsH.inform.GraFlap.JFlapWrapper.Submission;
import de.HsH.inform.GraFlap.JFlapWrapper.grammar.Grammar;

/**
 * class to test alphabet of a given input string
 * @author Frauke Sprengel (08-17-2015)
 * @author Benjamin Held (04-10-2016)
 * @since 04-26-2016
 * @version 0.3.0
 */
public class AlphabetTest {
    /**
     * an array holding the letters of an alphabet
     */
    private String[] letters;

    /**
     * extract the alphabet of the given automaton / grammar
     * @param grammarSubmission the given input string
     * @throws GraFlapException if another exception occurs it is replaced by a LonCapaException
     */
    public AlphabetTest(Submission<Grammar> grammarSubmission) throws GraFlapException {
        this.letters = grammarSubmission.getSubmissionObject().getAlphabet();
    }

    /**
     * checks the given string against the alphabet and grades it
     * @param given the alphabet to check against
     * @return 100 if OK, 0 if not OK
     */
    public int checkAlphabet(String given){
        given = given.replace(" ","");
        String[] givenAlphabet = given.split(",");

        boolean hasFailed = true;
        int i = 0;

        while ((i < letters.length) && hasFailed){
            hasFailed = false;
            int j = 0;
            while ((j < givenAlphabet.length) && !hasFailed){
                if (letters[i].equals(givenAlphabet[j])){
                    hasFailed = true;
                }
                j++;
            }
            i++;
        }

        if (hasFailed) {
            return 0;
        }
        return 100;
    }

    /**
     * simple getter of the letters
     * @return the letters of the alphabet
     */
    public String[] getLetters() {
        return letters;
    }
}
