package de.HsH.inform.GraFlap.util;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

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
