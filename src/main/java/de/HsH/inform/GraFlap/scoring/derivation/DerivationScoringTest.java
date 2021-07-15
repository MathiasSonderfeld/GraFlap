package de.HsH.inform.GraFlap.scoring.derivation;

import de.HsH.inform.GraFlap.JflapWrapper.grammar.Grammar;
import de.HsH.inform.GraFlap.derivation.DerivationChecker;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.scoring.ScoringTest;

/**
 * child class to generate the resulting score for a derivation exercise
 * @author Benjamin Held (07-27-2016)
 * @since 09-17-2016
 * @version 0.1.4
 */
public class DerivationScoringTest extends ScoringTest<String[]> {

    private final String word;
    private final Grammar solutionGrammar;

    /**
     * constructor which takes a generic object
     * @param object the object that should be used
     * @param solutionGrammar the provided solution grammar
     * @param word the provided test word
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further in the hierarchy
     */
    public DerivationScoringTest(String[] object, Grammar solutionGrammar, String word) throws GraFlapException {
        super(object);
        this.word = word;
        this.solutionGrammar = solutionGrammar;
        testing();
        this.resultScore = calculateResultScore();
    }

    @Override
    protected void testing() throws GraFlapException {
        DerivationChecker checker = new DerivationChecker(solutionGrammar, word);
        checker.checkSubmission(object);
        resultScore = checker.getFalseCounter();
    }

    @Override
    protected int calculateResultScore() {
        if (object.length == 1) {
            return 100;
        }
        return Math.min(100, resultScore * 100 / (object.length - 1));
    }
}
