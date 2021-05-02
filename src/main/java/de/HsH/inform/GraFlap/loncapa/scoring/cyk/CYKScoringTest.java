package de.HsH.inform.GraFlap.loncapa.scoring.cyk;

import de.HsH.inform.GraFlap.loncapa.entity.CYKTable;
import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;
import de.HsH.inform.GraFlap.loncapa.scoring.ScoringTest;
import de.HsH.inform.GraFlap.JFlapWrapper.grammar.Grammar;
import de.HsH.inform.GraFlap.JFlapWrapper.parse.CYKParser;

/**
 * child class to generate the resulting score for a cyk algorithm exercise
 * @author Benjamin Held (07-12-2016)
 * @since 09-17-2016
 * @version 0.1.1
 */
public class CYKScoringTest extends ScoringTest<CYKTable> {

    private String word;
    private Grammar solutionGrammar;

    /**
     * constructor which takes a generic object
     * @param object the object that should be used
     * @param word the input word
     * @param solutionGrammar the provided solution grammar
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further in the calling hierarchy
     */
    public CYKScoringTest(CYKTable object, String word, Grammar solutionGrammar) throws GraFlapException {
        super(object);
        this.word = word;
        this.solutionGrammar = solutionGrammar;
        testing();
        this.resultScore = calculateResultScore();
    }

    @Override
    protected void testing() throws GraFlapException {
        CYKParser parser = new CYKParser(solutionGrammar);
        parser.solve(word);
        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < (word.length() - i); j++) {
                if (!parser.compareProductionsAt(object.getProductionsAt(i,j), i, j)) {
                    resultScore++;
                }
            }
        }
    }

    @Override
    protected int calculateResultScore() {
        return resultScore;
    }
}
