package de.HsH.inform.GraFlap.JflapWrapper.exception;

import de.HsH.inform.GraFlap.exception.GraFlapException;

/**
 * exception class to signal the usage of a contextfree grammar that is not in Chomsky-Form
 * @author Benjamin Held (07-15-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class NonCNFGrammarException extends GraFlapException {

    public NonCNFGrammarException(String s) {
        super(s);
    }
}
