package de.HsH.inform.GraFlap.scoring.accepting;

import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.scoring.ScoringTest;

import java.util.HashMap;

/**
 *  parent class to generate the resulting score for test words for grammars and accepting automatons
 *  @author Benjamin Held (06-16-2016)
 *  @since 06-28-2016
 *  @version 0.1.1
 */
abstract class AcceptingScoringTest<T> extends ScoringTest<T> {
    protected int correctWordsCount;
    protected int wrongWordsCount;

    protected Testwords testwords;

    /**
     * constructor
     * @param object the object that should be used; can be an Automaton or a Grammar
     * @param testwords words for testing
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    AcceptingScoringTest(T object, Testwords testwords) throws GraFlapException {
        super(object);
        this.testwords = testwords;
        correctWordsCount = testwords.getCorrectWords().size();
        wrongWordsCount = testwords.getFailingWords().size();
        testing();
        resultScore = calculateResultScore();
    }

    /**
     * implementation of the abstract method to calculate the resulting score
     * @return the calculated score
     */
    @Override
    protected int calculateResultScore() {
        double resultValue = (correctWordsCount + wrongWordsCount * 100.0 /
                             (testwords.getCorrectWords().size() + testwords.getFailingWords().size()));
        if (resultValue > 0 && resultValue < 1) {
            return 1;
        }
        return (int) Math.round(resultValue);
    }
}
