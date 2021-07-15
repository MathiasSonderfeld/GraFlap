package de.HsH.inform.GraFlap.JflapWrapper.parse;

import de.HsH.inform.GraFlap.JflapWrapper.grammar.Grammar;

/**
 * wrapper class for JFLAP isolation
 * @author Benjamin Held (04-20-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class BruteParser extends Parser<model.algorithms.testinput.parse.brute.UnrestrictedBruteParser> {

    /**
     * Constructor
     * @param grammar the grammar that should be used
     */
    public BruteParser( Grammar grammar) {
        super(grammar);
        this.parser = new model.algorithms.testinput.parse.brute.UnrestrictedBruteParser(this.useGrammar);
    }
}
