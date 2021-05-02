package de.HsH.inform.GraFlap.loncapa.scoring.accepting;

import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;
import de.HsH.inform.GraFlap.loncapa.scoring.ScoringTest;

import java.util.HashMap;

/**
 *  parent class to generate the resulting score for test words for grammars and accepting automatons
 *  @author Benjamin Held (06-16-2016)
 *  @since 06-28-2016
 *  @version 0.1.1
 */
abstract class AcceptingScoringTest<T> extends ScoringTest<T> {
    /**
     * this {@link HashMap} stores the values of correct and wrong words.
     * correct words will be counted under the true key, the wrong
     * word will be counted under the false key
     */
    HashMap<Boolean, Integer> countWordTypes;
    /**
     * an array for the word that should be accepted
     */
    String[] rightWords;
    /**
     * an array for the word that should not be accepted
     */
    String[] wrongWords;

    /**
     * constructor
     * @param object the object that should be used; can be an Automaton or a Grammar
     * @param rightWords a list of words that should be accepted
     * @param wrongWords a list of words that should be rejected
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    AcceptingScoringTest(T object, String[] rightWords, String[] wrongWords) throws GraFlapException {
        super(object);
        countWordTypes = new HashMap<>();
        countWordTypes.put(true, rightWords.length);
        countWordTypes.put(false, wrongWords.length);
        this.rightWords = rightWords;
        this.wrongWords = wrongWords;
        testing();
        resultScore = calculateResultScore();
    }

    /**
     * implementation of the abstract method to calculate the resulting score
     * @return the calculated score
     */
    @Override
    protected int calculateResultScore() {
        double resultValue = (countWordTypes.get(true) + countWordTypes.get(false)) * 100.0 /
                             (rightWords.length + wrongWords.length);
        if (resultValue > 0 && resultValue < 1) {
            return 1;
        }
        return (int) Math.round(resultValue);
    }
}
