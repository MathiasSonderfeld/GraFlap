package de.HsH.inform.GraFlap.exception;

/**
 * exception class to signal the usage of a contextfree grammar that is not in Chomsky-Form
 * @author Benjamin Held (07-15-2016)
 * @since 07-17-2016
 * @version 0.1.0
 */
public class NonCNFGrammarException extends GraFlapException {

    public NonCNFGrammarException(String s) {
        super(s);
    }
}
