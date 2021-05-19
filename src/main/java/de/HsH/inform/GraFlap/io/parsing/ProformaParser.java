package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.entity.Arguments;
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
import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import java.util.stream.*;

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
        Arguments arguments = null;
        try {
            Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(args[1].getBytes(StandardCharsets.UTF_8)));
            ArrayList<Node> submissionList = new ArrayList<>(1);
            submissionList.add(doc.getDocumentElement());

            String graflapArguments = submissionList.stream()
                                                    .flatMap(toChildElements)
                                                    .filter(byName("task")).flatMap(toChildElements)
                                                    .filter(byName("files")).flatMap(toChildElements)
                                                    .filter(byAttribute("id","graflap-arguments")).flatMap(toChildElements)
                                                    .filter(byName("embedded")).flatMap(toChildNodes)
                                                    .filter(byIsCDATA).findFirst().get().getTextContent();

            List<Element> submissionFiles = submissionList.stream()
                                                          .flatMap(toChildElements)
                                                          .filter(byName("files")).flatMap(toChildElements)
                                                          .collect(Collectors.toList());

            String studentAnswer = submissionFiles.stream().filter(byAttribute("id","studentAnswer")).flatMap(toChildElements)
                                                  .filter(byName("embedded")).flatMap(toChildNodes)
                                                  .filter(byIsCDATA).findFirst().get().getTextContent();

            arguments = new LoncapaParser().parse(new String[]{graflapArguments, studentAnswer});

            //check if sets need to be extracted
            if(arguments.getMode().contains("p")){
                List<Element> otherEmbeddedFiles = submissionFiles.stream()
                                                                  .filter(byAttribute("id", "studentAnswer").negate())
                                                                  .flatMap(toChildElements).collect(Collectors.toList());

                String states = otherEmbeddedFiles.stream()
                                                  .filter(byAttribute("filename", "states")).flatMap(toChildNodes)
                                                  .filter(byIsCDATA).findFirst().get().getTextContent();
                arguments.setStates(states);

                String initials = otherEmbeddedFiles.stream()
                                                    .filter(byAttribute("filename", "initials")).flatMap(toChildNodes)
                                                    .filter(byIsCDATA).findFirst().get().getTextContent();
                arguments.setInitials(initials);

                String finals = otherEmbeddedFiles.stream()
                                                  .filter(byAttribute("filename", "finals")).flatMap(toChildNodes)
                                                  .filter(byIsCDATA).findFirst().get().getTextContent();
                arguments.setFinals(finals);

                String alphabet = otherEmbeddedFiles.stream()
                                                    .filter(byAttribute("filename", "alphabet")).flatMap(toChildNodes)
                                                    .filter(byIsCDATA).findFirst().get().getTextContent();
                arguments.setAlphabet(alphabet);

                String stackalphabet = otherEmbeddedFiles.stream()
                                                         .filter(byAttribute("filename", "stackalphabet")).flatMap(toChildNodes)
                                                         .filter(byIsCDATA).findFirst().get().getTextContent();
                arguments.setStackalphabet(stackalphabet);
            }
        }
        catch(ClassCastException | NullPointerException | SAXException | IOException | ParserConfigurationException e) {
            throw new GraFlapException("Cant parse Proforma XML");
        }

        return arguments;
    }


    private Predicate<Node> byIsElement = node -> node.getNodeType() == Node.ELEMENT_NODE;
    private Predicate<Node> byIsCDATA = node -> node.getNodeType() == Node.CDATA_SECTION_NODE;
    private Function<Node, Element> toElement = node -> (Element) node;
    private Function<Node, Stream<Node>> toChildNodes = element -> getNodeListAsList(element.getChildNodes()).stream();
    private Function<Node, Stream<Element>> toChildElements = element -> toChildNodes.apply(element).filter(byIsElement).map(toElement);

    private Predicate<Element> byName(String name){
        return element -> element.getTagName().contains(name);
    }

    private Predicate<Element> byAttribute(String attributeName, String attributeValue){
        return element -> element.getAttribute(attributeName).equals(attributeValue);
    }

    private List<Node> getNodeListAsList(NodeList nodeList){
        ArrayList<Node> list = new ArrayList<>(nodeList.getLength());
        for(int i = 0; i < nodeList.getLength(); i++) {
            list.add(nodeList.item(i));
        }
        return  list;
    }
}
