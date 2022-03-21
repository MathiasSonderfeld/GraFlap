package de.HsH.inform.GraFlap.JflapWrapper.parse;

import de.HsH.inform.GraFlap.JflapWrapper.grammar.Grammar;

/**
 * wrapper class for JFLAP isolation
 * @author Benjamin Held (04-23-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class RestrictedBruteParser extends Parser<model.algorithms.testinput.parse.brute.RestrictedBruteParser> {
    /**
     * Constructor
     * @param grammar the grammar that should be used
     */
    public RestrictedBruteParser( Grammar grammar) {
        super(grammar);
        this.parser = new model.algorithms.testinput.parse.brute.RestrictedBruteParser(this.useGrammar);
    }
}
