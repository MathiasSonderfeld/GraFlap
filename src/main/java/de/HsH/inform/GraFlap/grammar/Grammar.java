package de.HsH.inform.GraFlap.grammar;

import model.grammar.typetest.GrammarType;

/**
 * wrapper class for JFLAP isolation to represent a Grammar
 * @author Benjamin Held (04-20-2016)
 * @since 08-17-2016
 * @version 0.3.1
 */
public class Grammar {
    /**
     * the jflap Grammar object that this class represents
     */
    private model.grammar.Grammar grammar;

    /**
     * Constructor
     * @param grammar the jflap grammar that should be wrapped
     */
    public Grammar(model.grammar.Grammar grammar) {
        this.grammar = grammar;
    }

    /**
     * Constructor
     * @param grammar object reference of a jflap grammar that should be wrapped
     */
    public Grammar(Object grammar) {
        if (grammar instanceof model.grammar.Grammar) {
            this.grammar = (model.grammar.Grammar) grammar;
        } else {
            throw new IllegalArgumentException("Error in Grammar: type conversion to JFALP grammar failed.");
        }
    }

    /**
     * getter to retrieve the jflap grammar
     * @return the jflap grammar
     */
    public model.grammar.Grammar getJFLAPGrammar() {
        return this.grammar;
    }

    /**
     * wrapper method to retrieve the productions to the jflap grammar
     * @return an array containing the productions
     */
    public Production[] getProductions() {
        model.grammar.Production[] jflapProductions = this.grammar.getProductionSet().toArray();
        Production[] productions = new Production[jflapProductions.length];
        for (int i = 0; i < jflapProductions.length; i++) {
            productions[i] = new Production(jflapProductions[i]);
        }
        return productions;
    }

    public String getStartSymbol() {
        return this.grammar.getStartVariable().toString();
    }

    /**
     * wrapper method to retrieve the terminal strings of the jflap grammar
     * @return an array containing the terminals
     */
    public String[] getAlphabet() {
        return this.grammar.getTerminals().getSymbolStringArray();
    }

    /**
     * wrapper method to retrieve the variable strings of the jflap grammar
     * @return an array containing the terminals
     */
    public String[] getVariables() {
        return this.grammar.getVariables().getSymbolStringArray();
    }

    public boolean isRightlinear() {
        return this.grammar.isType(GrammarType.RIGHT_LINEAR);
    }

    public boolean isContextFree() {
        return this.grammar.isType(GrammarType.CONTEXT_FREE);
    }

    public boolean isContextSensitive() {
        return this.grammar.isType(GrammarType.CONTEXT_SENSITIVE);
    }

    public boolean isUnrestricted() {
        return this.grammar.isType(GrammarType.UNRESTRICTED);
    }

    public boolean isCNF() {
        return this.grammar.isType(GrammarType.CHOMSKY_NORMAL_FORM);
    }
}
