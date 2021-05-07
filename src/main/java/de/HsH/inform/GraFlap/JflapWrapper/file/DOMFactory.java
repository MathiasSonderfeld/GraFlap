package de.HsH.inform.GraFlap.JflapWrapper.file;

import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Static helper class to create {@link Document}s
 * @author Benjamin Held (05-05-2016)
 * @since 05-05-2016
 * @version 0.1.0
 */
public class DOMFactory {

    /**
     * method to generate the xml document from the input string
     * @param givenString the input string
     * @param errorMessage the error message for the exception
     * @return the parsed xml document
     * @throws GraFlapException throws {@link GraFlapException} if the content cannot be parsed
     */
    //TODO umschreibbar?
    public static Document buildDocument(String givenString, String errorMessage) throws GraFlapException {
        try {
            InputStream stream = new ByteArrayInputStream(givenString.getBytes(StandardCharsets.UTF_8));
            SAXBuilder saxb = new SAXBuilder();
            return saxb.build(stream);
        } catch (IOException | JDOMException e) {
            throw new GraFlapException(errorMessage);
        }
    }
}