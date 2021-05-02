package de.HsH.inform.GraFlap.loncapa.svg;

import de.HsH.inform.GraFlap.loncapa.entity.Mode;
import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;
import de.HsH.inform.GraFlap.loncapa.main.LoncapaArguments;
import org.jdom2.Document;
import org.jdom2.Element;
import de.HsH.inform.GraFlap.JFlapWrapper.file.DOMFactory;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * Child class of the {@link SvgAutomatonBuilder} that creates a result svg for a jff automaton
 * @author Benjamin Held (04-24-2016)
 * @since 09-15-2016
 * @version 0.3.3
 */
class SvgJffBuilder extends SvgAutomatonBuilder {

    SvgJffBuilder(LoncapaArguments arguments, Mode mode) throws GraFlapException {
        super(mode);
        buildSvgElement(arguments.getStudentAnswer());
    }

    @Override
    /**
     * method to generate a graphvis object of the jff-content
     * @param givenString the jff submission from LonCapa
     * @return an XML coded string that contains the graphvis object of the given jff-content
     * @throws GraFlapException throws {@link GraFlapException} if the jff-content cannot be used or has errors
     */
    String transformXMLToGraphVis(String givenString) throws GraFlapException {
        Document doc = DOMFactory.buildDocument(givenString, "ERROR - Cannot open JFF");
        try {
            Element root = doc.getRootElement();
            String automatonType = root.getChildText("type");
            Element automaton = root.getChild("automaton");
            List<Element> states = automaton.getChildren("state");
            List<Element> blocks = automaton.getChildren("block");
            List<Element> transitions = automaton.getChildren("transition");

            StringBuilder sb = new StringBuilder();
            sb.append("digraph automaton{ ");
            sb.append("rankdir = LR; nodesep = 0.3; ranksep = 1.2; maxiter= 1; size=\"15,30\"; ratio= compress;");

            StringBuilder elementBuilder = new StringBuilder();
            for (Element state : states) {
                elementBuilder.append(appendElement(state, automatonType));
            }

            for (Element block : blocks) {
                elementBuilder.append(appendElement(block, automatonType));
            }
            sb.append("node [shape=none,label=\"\", pos = \"").append(start.getX()).append(",");
            sb.append(start.getY()).append("\"]; start; ").append(elementBuilder);

            for (Element transition : transitions) {
                String from = transition.getChildText("from");
                String to = transition.getChildText("to");
                StringBuilder label = new StringBuilder(transition.getChildText("read"));
                if (label.toString().isEmpty()) {
                    label.append("E");
                }
                switch (automatonType) {
                    case "pda":
                        label.append(buildPDATransitionText(transition));
                        break;
                    case "mealy": {
                        String write = transition.getChildText("transout");
                        label.append(" ; ").append(write);
                        break;
                    }
                    case "turing": {
                        String write = transition.getChildText("write");
                        if (write.isEmpty()) {
                            write = "E";
                        }
                        String move = transition.getChildText("move");
                        label.append(" : ").append(write).append(", ").append(move);
                        break;
                    }
                }

                sb.append(from).append(" -> ").append(to).append(" [ label = \"").append(label).append("\"");
                if (from.equals(to) && mode != Mode.SVGA) {
                    sb.append(" tailport=ne headport=nw");
                }
                sb.append(" ];");
            }

            sb.append("}");
            return sb.toString();
        } catch (Exception ex) {
            throw new GraFlapException("Error while building svg for jff-automaton for input" + givenString);
        }
    }

    /**
     * method to add the current element to the svg
     * @param element the current element
     * @param automatonType the given automaton type
     * @return the string coding the given element
     */
    private String appendElement(Element element, String automatonType) {
        StringBuilder sb = new StringBuilder();
        String nodeName = element.getAttributeValue("id");
        String nodeLabel = element.getAttributeValue("name");
        double xPos = Double.parseDouble(element.getChildText("x")) / SCALING;
        double yPos = Double.parseDouble(element.getChildText("y")) / -SCALING;
        Element finalState = element.getChild("final");
        Element startState = element.getChild("initial");
        sb.append("node [ pos = \"").append(xPos).append(", ").append(yPos).append("\", shape= ");
        if (finalState == null) {
            sb.append("circle");
        } else {
            sb.append("doublecircle");
        }
        sb.append(", label = \"").append(nodeLabel);
        if (automatonType.equals("moore")) {
            String output = element.getChildText("output");
            if (output.isEmpty()) {
                output = "L";
            }
            sb.append(" / ").append(output);
        }
        sb.append("\"");
        sb.append(" ]; ").append(nodeName).append("; ");
        if (startState != null) {
            start = new Point2D.Double(xPos - SCALING / 25, yPos);
            sb.append("start -> ").append(nodeName).append("; ");
        }
        return sb.toString();
    }
}
