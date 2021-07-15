package de.HsH.inform.GraFlap.JflapWrapper.exception;

import de.HsH.inform.GraFlap.exception.GraFlapException;

/**
 * exception class to signal the usage of a false automaton type
 * @author Benjamin Held (06-25-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class AutomatonMismatchException extends GraFlapException {
    public AutomatonMismatchException(String s) {
        super(s);
    }
}
