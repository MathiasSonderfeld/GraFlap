package de.HsH.inform.GraFlap.parse.Arguments;

import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

/**
 * @author Mathias Sonderfeld
 * parses Input-Data in Proforma-XML 2.1 Format to Arguments Object
 */
public class ProformaParser extends ArgumentsParser{

    public ProformaParser(){
        super();
    }
    /**
     * parses the Input structured as Proforma-XMl 2.1 Format and returns an Arguments Object
     * @param args the input arguments, only the second element of the array gets processed.
     * @return Arguments Object
     * @throws GraFlapException if input constraints are infringed
     */
    public Arguments parse( String[] args ) throws GraFlapException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        String inputArgs = null, studentAnswer = null;
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            ByteArrayInputStream xmlstream = new ByteArrayInputStream(args[1].getBytes(StandardCharsets.UTF_8));
            Document doc = db.parse(xmlstream);
            doc.normalizeDocument();
            Element root = doc.getDocumentElement(); //Submission
            Function<Node, Boolean> getCDATANode = node -> node.getNodeType() == Node.CDATA_SECTION_NODE;

            //Get Task Input
            Element task = (Element) getSubElementOf(root, getCheckForElementByName("task"));
            Element taskFiles = (Element) getSubElementOf(task, getCheckForElementByName("files"));
            Element taskInputFile = (Element) getSubElementOf(taskFiles, getCheckforElementByNameAndAttribute("file","id", "input"));
            Element taskInputEmbeddedFile = (Element) getSubElementOf(taskInputFile, getCheckForElementByName("embedded"));
            Node inputNode = getSubElementOf(taskInputEmbeddedFile, getCDATANode);
            inputArgs = inputNode.getNodeValue();

            //Get Student Answer
            Element submissionFiles = (Element) getSubElementOf(root, getCheckForElementByName("files"));
            Element submissionFile = (Element) getSubElementOf(submissionFiles, getCheckforElementByNameAndAttribute("file", "id", "studentAnswer"));
            Element submissionEmbeddedFile = (Element) getSubElementOf(submissionFile, getCheckForElementByName("embedded"));
            Node studentAnswerNode = getSubElementOf(submissionEmbeddedFile, getCDATANode);
            studentAnswer = studentAnswerNode.getNodeValue();
        }
        catch(ClassCastException | NullPointerException | SAXException | IOException | ParserConfigurationException e) {
            throw new GraFlapException("Cant parse Proforma XML");
        }

        return new LoncapaParser().parse(new String[]{inputArgs, studentAnswer});
    }

    /**
     * Iterates through child nodes of given Element. If check functions approves, child node is returned.
     * @param parent The Element to iterate through.
     * @param check the function to identify the wanted child node.
     * @return child node if matched, null if not.
     */
    private Node getSubElementOf( Element parent, Function<Node, Boolean> check ) {
        NodeList children = parent.getChildNodes();
        Node n = null;
        for(int nodeIterator = 0; nodeIterator < children.getLength(); nodeIterator++) {
            n = children.item(nodeIterator);
            if(check.apply(n)) {
                break;
            }
        }
        return n;
    }

    /**
     *  Creates a check function for getSubElementOf-Method to match Node to Element with given Tag name
     * @param filterName the tag name, that is wanted
     * @return check function for getSubElementOf-Method
     */
    private Function<Node, Boolean> getCheckForElementByName( String filterName ) {
        return node -> node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().contains(filterName);
    }

    /**
     *  Creates a check function for getSubElementOf-Method to match Node to Element with given Name and Attribute
     * @param elementName the tag name, that is wanted
     * @param attributeName the attribute name, that shall be checked
     * @param attributeValue the attribute value, that is wanted
     * @return check function for getSubElementOf-Method
     */
    private Function<Node, Boolean> getCheckforElementByNameAndAttribute( String elementName, String attributeName, String attributeValue ) {
        return node -> getCheckForElementByName(elementName).apply(node) && ( (Element) node ).getAttribute(attributeName).equals(attributeValue);
    }
}
