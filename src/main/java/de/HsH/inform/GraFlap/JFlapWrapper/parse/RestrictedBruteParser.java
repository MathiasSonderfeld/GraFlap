package de.HsH.inform.GraFlap.JFlapWrapper.parse;

import de.HsH.inform.GraFlap.JFlapWrapper.grammar.Grammar;

/**
 * wrapper class for JFLAP isolation
 * @author Benjamin Held (04-23-2016)
 * @since 07-14-2016
 * @version 0.3.0
 */
public class RestrictedBruteParser extends Parser<model.algorithms.testinput.parse.brute.RestrictedBruteParser> {
    /**
     * Constructor
     * @param grammar the grammar that should be used
     */
    public RestrictedBruteParser(Grammar grammar) {
        super(grammar);
        this.parser = new model.algorithms.testinput.parse.brute.RestrictedBruteParser(this.useGrammar);
    }
}
