package de.HsH.inform.GraFlap.parse.Submission;

import de.HsH.inform.GraFlap.grammar.Grammar;

/**
 * wrapper class for JFLAP isolation
 * @author Benjamin Held (04-20-2016)
 * @since 07-14-2016
 * @version 0.3.0
 */
public class BruteParser extends Parser<model.algorithms.testinput.parse.brute.UnrestrictedBruteParser> {

    /**
     * Constructor
     * @param grammar the grammar that should be used
     */
    public BruteParser(Grammar grammar) {
        super(grammar);
        this.parser = new model.algorithms.testinput.parse.brute.UnrestrictedBruteParser(this.useGrammar);
    }
}
