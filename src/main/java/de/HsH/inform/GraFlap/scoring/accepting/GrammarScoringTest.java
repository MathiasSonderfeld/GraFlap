package de.HsH.inform.GraFlap.scoring.accepting;

import de.HsH.inform.GraFlap.JflapWrapper.grammar.Grammar;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.test.WordTest;

/**
 *  child class to generate the resulting score for test words and a given grammar
 *  @author Benjamin Held (05-30-2016)
 *  @since 06-25-2016
 *  @version 0.2.0
 */
public class GrammarScoringTest extends AcceptingScoringTest<Grammar> {

    /**
     * constructor
     * @param object the grammar that should be used to test the words
     * @param rightWords a list of words that should be accepted
     * @param wrongWords a list of words that should be rejected
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    public GrammarScoringTest(Grammar object, String[] rightWords, String[] wrongWords)
            throws GraFlapException {
        super(object, rightWords, wrongWords);
    }

    /**
     * method to test the provided words against the given grammar
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    @Override
    protected void testing() throws GraFlapException {
        countWordTypes.put(true, WordTest.checkWordsWithGrammar(object, rightWords));
        countWordTypes.put(false, wrongWords.length - WordTest.checkWordsWithGrammar(object, wrongWords));
    }
}