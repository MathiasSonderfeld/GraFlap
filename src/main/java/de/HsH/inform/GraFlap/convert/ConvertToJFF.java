package de.HsH.inform.GraFlap.convert;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * helper class to convert an xml formatted string to a convert-formatted string
 * @author Ufuk Tosun (2012)
 * @author Benjamin Held (04-12-2016)
 * @since 05-05-2016
 * @version 0.2.2
 */
public class ConvertToJFF {
    /**
     * a set which contains the unused letters that still can be used
     */
    private TreeSet<String> freeLetter = new TreeSet<>();
    /**
     * a hashmap that contains the already used letter mapped by {StringToken, Letter}
     */
    private HashMap<String, String> usedLetter = new HashMap<>();

    /**
     * Constructor
     */
    public ConvertToJFF() {
        for (char a = 'A', z = 'Z'; a < z; a++) {
            freeLetter.add("" + a);
        }
    }

    /**
     * method to transform a given xml-formatted string into a convert-string
     * @param inputXML xml-formatted string
     * @return xml-formatted convert-string
     * @throws JDOMException throws occurring {@link JDOMException} further within the call hierarchy
     * @throws IOException throws occurring {@link IOException} further within the call hierarchy
     */
    public String convertToJFF(String inputXML) throws JDOMException, IOException {
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(new StringReader(inputXML));

        Element root = doc.getRootElement();
        Element production = root.getChild("productions");
        List<Element> children = production.getChildren();

        Element structure = new Element("structure");
        Document newDoc = new Document(structure);
        structure.addContent(new Element("type").addContent("grammar"));

        for (Element child : children) {
            String left = renameVariables(child.getAttributeValue("name"));
            String right = "";
            List<Element> elementChildren;
            elementChildren = child.getChildren();
            for (Element grandChild : elementChildren) {
                right += renameVariables(grandChild.getText());
            }
            structure.addContent(new Element("production")
                     .addContent(new Element("left").addContent(left))
                     .addContent(new Element("right").addContent(right)));
        }
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());

        return out.outputString(newDoc);
    }

    /**
     * method to rename the variables
     * @param token the string token that should be checked
     * @return the corresponding character
     */
    private String renameVariables(String token) {
        if (token.equals("S")) {
            freeLetter.remove("S");
            usedLetter.put("S", "S");
            return "S";
        } else {
            if (usedLetter.containsKey(token)) {
                return usedLetter.get(token);
            } else {
                if (token.matches("[a-z]") || token.matches("[0-9]") || token.matches("-")) {
                    return token;
                }
                String letter = freeLetter.first();
                freeLetter.remove(letter);
                usedLetter.put(token, letter);
                return letter;
            }
        }
    }
}