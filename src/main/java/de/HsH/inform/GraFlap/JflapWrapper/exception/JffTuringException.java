package de.HsH.inform.GraFlap.JflapWrapper.exception;

/**
 * exception class to signal the usage of an unsupported jff turing machine
 * @author Benjamin Held (05-02-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class JffTuringException extends Exception {
    public JffTuringException(String message) {
        super(message);
    }
}