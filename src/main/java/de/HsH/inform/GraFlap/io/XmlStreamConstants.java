package de.HsH.inform.GraFlap.io;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Constants for multiple classes in IO
 * @author Mathias Sonderfeld
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class XmlStreamConstants {
    public static final Predicate<Node> byIsElement = node -> node.getNodeType() == Node.ELEMENT_NODE;
    public static final Predicate<Node> byIsCDATAOrText = node -> node.getNodeType() == Node.CDATA_SECTION_NODE ||
                                                                  (node.getNodeType() == Node.TEXT_NODE && node.getTextContent().trim().length() > 0);
    public static final Function<Node, Element> toElement = node -> (Element) node;
    public static final Function<Node, Stream<Node>> toChildNodes = element -> getNodeListAsList(element.getChildNodes()).stream();
    public static final Function<Node, Stream<Element>> toChildElements = element -> toChildNodes.apply(element).filter(byIsElement).map(toElement);

    public static Predicate<Element> byName(String name){
        return element -> element.getTagName().matches(".*" + name + ".*");
    }

    public static Predicate<Element> byAttribute(String attributeName, String attributeValue){
        return element -> element.getAttribute(attributeName).matches(".*" + attributeValue + ".*");
    }

    public static ArrayList<Node> getNodeListAsList( NodeList nodeList){
        ArrayList<Node> list = new ArrayList<>(nodeList.getLength());
        for(int i = 0; i < nodeList.getLength(); i++) {
            list.add(nodeList.item(i));
        }
        return  list;
    }
}
