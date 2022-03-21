package de.HsH.inform.GraFlap.util;

import de.HsH.inform.GraFlap.GraFlap;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * stops the DocumentBuilder from printing to System.err even if Exceptions are caught.
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value GraFlap#version}
 */
public class EmptyErrorHandler implements ErrorHandler {
    public static final EmptyErrorHandler instance = new EmptyErrorHandler();

    @Override
    public void warning( SAXParseException exception ) throws SAXException {

    }

    @Override
    public void error( SAXParseException exception ) throws SAXException {

    }

    @Override
    public void fatalError( SAXParseException exception ) throws SAXException {

    }
}
