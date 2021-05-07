package de.HsH.inform.GraFlap.JflapWrapper.grammar;

import model.grammar.typetest.matchers.CNFChecker;

/**
 * wrapper class for JFLAP isolation to represent a CNFConverter
 * @author Benjamin Held (04-20-2016)
 * @since 05-29-2016
 * @version 0.2.1
 */
public class CNFConverter {
    /**
     * the jflap CNFConverter that this class represents
     */
    private final model.algorithms.transform.grammar.CNFConverter converter;

    /**
     * Constructor
     * @param grammar the grammar that the converter should use
     */
    public CNFConverter(Grammar grammar) {
        model.grammar.Grammar jflapGrammar = grammar.getJFLAPGrammar();
        this.converter = new model.algorithms.transform.grammar.CNFConverter(jflapGrammar);
    }

    /**
     * wrapper method to check if the given production fulfills the chomsky form rules
     * @param production the production that should be checked
     * @return a boolean depending if the production fulfills the chomsky form or not
     */
    public boolean isChomsky(Production production) {
        return new CNFChecker().matchesProduction(production.getJFLAPProduction());
    }

    public Grammar getCNF() {
        this.converter.stepToCompletion();
        return new Grammar(this.converter.getTransformedGrammar());
    }
}
