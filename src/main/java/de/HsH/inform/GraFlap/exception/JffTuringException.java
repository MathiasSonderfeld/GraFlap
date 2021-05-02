package de.HsH.inform.GraFlap.exception;

/**
 * exception class to signal the usage of an unsupported jff turing machine
 * @author Benjamin Held (05-02-2016)
 * @since 05-03-2016
 * @version 0.1.0
 */
public class JffTuringException extends Exception {
    public JffTuringException(String message) {
        super(message);
    }
}