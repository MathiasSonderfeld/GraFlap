package de.HsH.inform.GraFlap.grammar;

import de.HsH.inform.GraFlap.exception.GraFlapException;
import model.symbols.Symbol;
import model.symbols.symbolizer.Symbolizers;

/**
 * wrapper class for JFLAP isolation to represent a production
 * @author Benjamin Held (04-20-2016)
 * @since 09-06-2016
 * @version 0.3.0
 */
public class Production {
    /**
     * the jflap Production that this class represents
     */
    private model.grammar.Production production;

    /**
     * Constructor
     * @param production the jflap production that should be wrapped
     */
    Production(model.grammar.Production production) {
        this.production = production;
    }

    public Production(String lhs, Grammar grammar) throws GraFlapException {
        if (lhs.isEmpty()) {
            throw new GraFlapException("Error [Production]: left side of the production is empty");
        }
        this.production = new model.grammar.Production(Symbolizers.symbolize(lhs, grammar.getJFLAPGrammar()),
                                                       Symbolizers.symbolize("", grammar.getJFLAPGrammar()));

    }

    public Production(String lhs, String rhs, Grammar grammar) throws GraFlapException {
        if (lhs.isEmpty() || rhs.isEmpty()) {
            throw new GraFlapException("Error [Production]: at least on side of the production is empty");
        }
        this.production = new model.grammar.Production(Symbolizers.symbolize(lhs, grammar.getJFLAPGrammar()),
                                                       Symbolizers.symbolize(rhs, grammar.getJFLAPGrammar()));
    }

    /**
     * package internal getter to retrieve the jflap configuration
     * @return the jflap production
     */
    model.grammar.Production getJFLAPProduction() {
        return this.production;
    }

    /**
     * wrapper method to retrieve the left side of the production
     * @return a string with the left side argument
     */
    public String getLHS() {
        Symbol[] leftSideSymbols = this.production.getLHS();
        StringBuilder lhs = new StringBuilder();
        for (Symbol symbol: leftSideSymbols) {
            lhs.append(symbol.toString());
        }
        return lhs.toString();
    }

    /**
     * wrapper method to retrieve the right side of the production
     * @return a string with the right side argument
     */
    public String getRHS() {
        Symbol[] rightSideSymbols = this.production.getRHS();
        StringBuilder rhs = new StringBuilder();
        for (Symbol symbol: rightSideSymbols) {
            rhs.append(symbol.toString());
        }
        return rhs.toString();
    }
}
