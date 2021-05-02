package de.HsH.inform.GraFlap.loncapa.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exception-class that creates an error message that can be send to the LonCapaserver
 * @author Ufuk Tosun (12-11-12)
 * @author Frauke Sprengel (08-15-2015)
 * @author Benjamin Held (03-31-2016)
 * @since 04-19-2016
 * @version 0.2.2
 */
public class GraFlapException extends Exception {

    /**
     * Constructor
     * @param s the error string
     */
    //TODO Error-Handling
    public GraFlapException( String s){
        super(s);
    }

    /**
     * method to return the string that will be send to LonCapa
     * @param taskTitle the title string of the given task
     * @return the message for LON-CAPA to be shown
     */
   public String getLonCapaMessage(String taskTitle){
    StringWriter errors = new StringWriter();
    this.printStackTrace(new PrintWriter(errors));
    return taskTitle + ": " + errors.toString();
   }
}
