package de.HsH.inform.GraFlap.JflapWrapper.file;

import de.HsH.inform.GraFlap.exception.GraFlapException;
import file.xml.*;
import de.HsH.inform.GraFlap.entity.OperationType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import de.HsH.inform.GraFlap.JflapWrapper.exception.JffTuringException;

/**
 * wrapper class for JFLAP isolation to represent a Transducer
 * @author Benjamin Held (04-20-2016)
 * @since 09-22-2016
 * @version 0.2.6
 */
public class Transducer {
    private OperationType operationType = OperationType.UNDEFINED;

    /**
     * wrapper method retrieve the given object from the jflap transducer
     * @param document the given document
     * @return the created object
     * @throws JffTuringException throws a {@link JffTuringException} when a turing automaton in jff-format is provided
     * @throws GraFlapException throws a {@link GraFlapException} as a wrapper for any other exception type
     */
    public Object parseDocument(Document document) throws JffTuringException, GraFlapException {
        try {
            StructureTransducer transducer;
            Element root = document.getDocumentElement();
            if(!root.hasAttribute("type")) {
                operationType = OperationType.JFFSTRUCTURE;
                transducer = getJFFStructureTransducer(root);
            } else {
                operationType = OperationType.JFLAPSTRUCTURE;
                transducer = getStructureTransducer(root);
            }

            return transducer.fromStructureRoot(document.getDocumentElement());
        } catch (NullPointerException ex) {
            throw new GraFlapException("\nInput is missing necessary values, e.g. start or final state, alphabet!");
        } catch (ExceptionInInitializerError var5) {
            throw new GraFlapException("\nTransducer: Unexpected Error!");
        }
    }

    public OperationType getInputType() {
        return operationType;
    }

    /**
     * helper method to retrieve the required transducer based on the content of the root type
     * @param root the root element of the xml file
     * @return the transducer which parses the given xml document
     */
    private StructureTransducer getStructureTransducer(Element root) {
        String tag = root.getAttribute("type");
        return (StructureTransducer) file.xml.TransducerFactory.getTransducerForTag(tag);
    }

    private StructureTransducer getJFFStructureTransducer(Element root) throws JffTuringException {
        Element tag_elem = XMLHelper.getChildrenWithTag(root, "type").get(0);
        String tag = XMLHelper.containedText(tag_elem);
        if (!tag.equals("turing")) {
            return (StructureTransducer)JFFTransducerFactory.getTransducerForTag(tag);
        }
        throw new JffTuringException("Found jff turing machine in transducer.");
    }
}
