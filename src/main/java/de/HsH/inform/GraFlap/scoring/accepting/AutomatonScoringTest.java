package de.HsH.inform.GraFlap.scoring.accepting;

import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JflapWrapper.simulation.AutomatonSimulator;
import de.HsH.inform.GraFlap.exception.GraFlapException;

/**
 *  child class to generate the resulting score for test words and a given automaton
 *  @author Benjamin Held (05-30-2016)
 *  @since 06-25-2016
 *  @version 0.2.0
 */
public class AutomatonScoringTest extends AcceptingScoringTest<Automaton> {

    /**
     * constructor
     * @param object the automaton that should be used to test the words
     * @param rightWords a list of words that should be accepted
     * @param wrongWords a list of words that should be rejected
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    public AutomatonScoringTest(Automaton object, String[] rightWords, String[] wrongWords)
            throws GraFlapException {
        super(object, rightWords, wrongWords);
    }

    /**
     * method to test the provided words against the given automaton
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    @Override
    protected void testing() throws GraFlapException {
        testRightWords(rightWords);
        testWrongWords(wrongWords);
    }

    /**
     * method to test the correct words against the automaton
     * @param testWords the array with the correct test words
     * @throws GraFlapException throws a {@link GraFlapException} if the number of words is not zero
     */
    private void testRightWords(String[] testWords) throws GraFlapException {
        int numberOfWords = testWords.length;
        for (String input : testWords) {
            boolean result = new AutomatonSimulator(object).acceptInput(input);
            if (result) {
                countWordTypes.put(true, countWordTypes.get(true) - 1);
            }
            numberOfWords--;
        }
        if (numberOfWords != 0) {
            throw new GraFlapException("Error in Logic: numberOfWords is " + numberOfWords + " and not zero.");
        }
    }

    /**
     * method to test the wrong words against the automaton
     * @param testWords the array with the wrong test words
     * @throws GraFlapException throws a {@link GraFlapException} if the number of words is not zero
     */
    private void testWrongWords(String[] testWords) throws GraFlapException {
        int numberOfWords = testWords.length;
        for (String input : testWords) {
            boolean result = new AutomatonSimulator(object).acceptInput(input);
            if (!result) {
                countWordTypes.put(false, countWordTypes.get(false) - 1);
            }
            numberOfWords--;
        }
        if (numberOfWords != 0) {
            throw new GraFlapException("Error in Logic: numberOfWords is " + numberOfWords + " and not zero.");
        }
    }
}