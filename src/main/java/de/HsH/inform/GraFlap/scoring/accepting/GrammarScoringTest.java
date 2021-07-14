package de.HsH.inform.GraFlap.scoring.accepting;

import de.HsH.inform.GraFlap.JflapWrapper.grammar.Grammar;
import de.HsH.inform.GraFlap.entity.Testwords;
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
     * @param testwords a list of words for testing
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    public GrammarScoringTest( Grammar object, Testwords testwords) throws GraFlapException {
        super(object, testwords);
    }

    /**
     * method to test the provided words against the given grammar
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    @Override
    protected void testing() throws GraFlapException {
        correctWordsCount = WordTest.checkWordsWithGrammar(object, testwords.getCorrectWordsArray());
        wrongWordsCount = testwords.getFailingWords().size() - WordTest.checkWordsWithGrammar(object, testwords.getFailingWordsArray());
    }
}