package de.HsH.inform.GraFlap.exception;

/**
 * Exception-class that creates an error message that can be returned
 * @author Ufuk Tosun (12-11-12)
 * @author Frauke Sprengel (08-15-2015)
 * @author Benjamin Held (03-31-2016)
 * @author Mathias Sonderfeld (2021)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class GraFlapException extends Exception {
    public GraFlapException(String s){
        super(s);
    }
}