package de.HsH.inform.GraFlap.grammar;

import model.algorithms.AlgorithmException;
import model.algorithms.transform.grammar.LambdaProductionRemover;
import model.algorithms.transform.grammar.UselessProductionRemover;
import model.algorithms.transform.grammar.UnitProductionRemover;
import de.HsH.inform.GraFlap.exception.GraFlapException;


/**
 * static helper class to reduce a given grammar in order to transform it into chomsky form
 * @author Ufuk Tosun (12-06-2012)
 * @author Benjamin Held (04-10-2016)
 * @since 05-29-2016
 * @version 0.2.4
 */
public class GrammarReducer {

    /**
     * public method to start the transformation and to check if its a valid grammar
     * @param grammar the grammar that should be transformed
     * @return the grammar in chomsky form
     * @throws GraFlapException throws a {@link GraFlapException} when no terminals can be produced and when it occurs
     * further within the calling hierarchy
     */
    public static Grammar checkAndReduceContextfreeGrammar(Grammar grammar) throws GraFlapException {
        if (grammar == null)
            return null;
        if (grammar.getAlphabet().length==0) {
            throw new GraFlapException("ERROR - Grammar without terminals? Please check.");
        }
        return hypothesizeLambda(grammar);
    }

    /**
     * method to remove lambda variables and lambda derivers in the grammar
     * @param grammar Original grammar that is going to be changed
     * @return the reduced grammar
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    private static Grammar hypothesizeLambda(Grammar grammar) throws GraFlapException {
        //TODO: this does not remove the LambdaRules correctly
        LambdaProductionRemover remover = new LambdaProductionRemover(grammar.getJFLAPGrammar());
        remover.stepToCompletion();
        return hypothesizeUnit(new Grammar(remover.getTransformedGrammar()));
    }

    /**
     * method to remove unit productions and chain rules
     * @param grammar Grammar in transformation
     * @return the reduced grammar
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    private static Grammar hypothesizeUnit(Grammar grammar) throws GraFlapException {
        UnitProductionRemover remover = new UnitProductionRemover(grammar.getJFLAPGrammar());
        remover.stepToCompletion();
        return hypothesizeUseless(new Grammar(remover.getTransformedGrammar()));
    }

    /**
     * method to remove useless productions
     * @param grammar Grammar in transformation
     * @return the reduced grammar
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    private static Grammar hypothesizeUseless(Grammar grammar) throws GraFlapException {
        try {
            UselessProductionRemover remover = new UselessProductionRemover(grammar.getJFLAPGrammar());
            remover.stepToCompletion();
            Grammar reducedGrammar = new Grammar(remover.getTransformedGrammar());
            if (reducedGrammar.getAlphabet().length==0) {
                throw new GraFlapException("ERROR - CF Grammar has a Loop. Please check.");
            }
            return new CNFConverter(reducedGrammar).getCNF();
        } catch (AlgorithmException e) {
            throw new GraFlapException("ERROR - CF Grammar has a Loop. Please check.");
        }
    }
}
