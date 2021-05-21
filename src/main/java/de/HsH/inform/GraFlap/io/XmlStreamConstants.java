package de.HsH.inform.GraFlap.io;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class XmlStreamConstants {
    public static final Predicate<Node> byIsElement = node -> node.getNodeType() == Node.ELEMENT_NODE;
    public static final Predicate<Node> byIsCDATA = node -> node.getNodeType() == Node.CDATA_SECTION_NODE;
    public static final Function<Node, Element> toElement = node -> (Element) node;
    public static final Function<Node, Stream<Node>> toChildNodes = element -> getNodeListAsList(element.getChildNodes()).stream();
    public static final Function<Node, Stream<Element>> toChildElements = element -> toChildNodes.apply(element).filter(byIsElement).map(toElement);

    public static Predicate<Element> byName(String name){
        return element -> element.getTagName().contains(name);
    }

    public static Predicate<Element> byAttribute(String attributeName, String attributeValue){
        return element -> element.getAttribute(attributeName).equals(attributeValue);
    }

    public static ArrayList<Node> getNodeListAsList( NodeList nodeList){
        ArrayList<Node> list = new ArrayList<>(nodeList.getLength());
        for(int i = 0; i < nodeList.getLength(); i++) {
            list.add(nodeList.item(i));
        }
        return  list;
    }
}
