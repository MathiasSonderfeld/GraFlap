package de.HsH.inform.GraFlap.svg;

import de.HsH.inform.GraFlap.entity.OperationMode;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.entity.Arguments;
import org.jdom2.Document;
import org.jdom2.Element;
import de.HsH.inform.GraFlap.file.DOMFactory;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

/**
 * Child class of the {@link SvgAutomatonBuilder} that creates a result svg for a jflap automaton
 * @author Benjamin Held (04-24-2016)
 * @since 07-07-2016
 * @version 0.3.1
 */
class SvgJflapBuilder extends SvgAutomatonBuilder {

    SvgJflapBuilder( Arguments arguments, OperationMode operationMode ) throws GraFlapException {
        super(operationMode);
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
        Document doc = DOMFactory.buildDocument(givenString, "ERROR - Cannot open JFLAP");
        try {
            Element root = doc.getRootElement();
            Element automaton = findAutomatonRoot(root);
            String automatonType = automaton.getAttributeValue("type");
            List<Element> states = getChildrenForStructure(automaton, "state_set");
            List<Element> starts = getChildrenForStructure(automaton, "start_state");
            List<Element> finals = getChildrenForStructure(automaton, "final_states");
            List<Element> transitions = getChildrenForStructure(automaton, "transition_set");
            List<Element> positions = findChildrenOfXMLObject(root, "state_point_map");

            StringBuilder sb = new StringBuilder();
            sb.append("digraph automaton{ ");
            sb.append("rankdir = LR; nodesep = 0.3; ranksep = 1.2; maxiter= 1; size=\"15,30\"; ratio= compress;");

            StringBuilder stateBuilder = new StringBuilder();
            for (Element state : states) {
                stateBuilder.append(appendElement(state, positions, starts, finals));
            }
            sb.append("node [shape=none,label=\"\", pos = \"").append(start.getX()).append(",");
            sb.append(start.getY()).append("\"]; start; ").append(stateBuilder);

            for (Element transition : transitions) {
                String from = transition.getChild("from").getChildText("id");
                String to = transition.getChild("to").getChildText("id");
                StringBuilder label = new StringBuilder();

                if (automatonType.equals("pda") || automatonType.equals("fsa")) {
                    label.append(transition.getChildText("input"));
                } else if (automatonType.equals("turing")) {
                    label.append(transition.getChildText("read0"));
                }
                if (label.toString().isEmpty()) {
                    label.append("E");
                }

                if (automatonType.equals("pda")) {
                    label.append(buildPDATransitionText(transition));
                } else if (automatonType.equals("mealy")) {
                    String write = transition.getChildText("transout");
                    label.append(" ; ").append(write);
                } else if (automatonType.equals("turing")) {
                    String write = transition.getChildText("write0");
                    if (write.isEmpty()) {
                        write = "E";
                    }
                    String move = transition.getChildText("move0");
                    label.append(" : ").append(write).append(", ").append(move);
                }

                sb.append(from).append(" -> ").append(to).append(" [ label = \"").append(label).append("\"");
                if (from.equals(to) && operationMode != OperationMode.SVGA) {
                    sb.append(" tailport=ne headport=nw");
                }
                sb.append(" ];");
            }

            sb.append("}");
            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new GraFlapException("Error while building svg for jff-automaton for input" );
        }
    }

    /**
     * method to retrieve the children of a structure element with a given name
     * @param root the starting node
     * @param structureName the structure name
     * @return the children of the required element
     * @throws GraFlapException throws a {@link GraFlapException} if the required node cannot be found
     */
    private List<Element> findChildrenOfXMLObject(Element root, String structureName) throws GraFlapException {
        LinkedList<Element> queue = new LinkedList<>();
        queue.push(root);

        while (!queue.isEmpty()) {
            Element element = queue.pop();
            Element child = element.getChild(structureName);
            if (child != null) {
                return child.getChildren();
            } else {
                element.getChildren().forEach(queue::push);
            }
        }

        throw new GraFlapException("Error: Could not find " + structureName + " to draw a jflap automaton");
    }

    /**
     * method to retrieve the children of a structure with a given type string
     * @param automaton the root node of the automaton
     * @param typeName the type string of the required node
     * @return the children of the required element
     * @throws GraFlapException throws a {@link GraFlapException} if the required node cannot be found
     */
    private List<Element> getChildrenForStructure(Element automaton, String typeName) throws GraFlapException {
        for (Element element: automaton.getChildren()) {
            if (element.getAttribute("type") != null && element.getAttributeValue("type").equals(typeName)) {
                return element.getChildren();
            }
        }
        throw new GraFlapException("Error: Could not find " + typeName + " for the given automaton.");
    }

    /**
     * method to find the root node of the automaton within the xml document
     * @param root the root node of the xml document
     * @return the root node of the automaton
     * @throws GraFlapException throws a {@link GraFlapException} if the required node cannot be found
     */
    private Element findAutomatonRoot(Element root) throws GraFlapException {
        LinkedList<Element> queue = new LinkedList<>();
        queue.push(root);

        while (!queue.isEmpty()) {
            Element element = queue.pop();
            List<Element> children = element.getChildren();
            for (Element child: children) {
                if (child.getAttribute("mode") != null && child.getAttributeValue("mode").equals("Default mode")) {
                    return child;
                } else {
                    queue.push(child);
                }
            }
        }

        throw new GraFlapException("Error: Could not find automaton root to draw a jflap automaton");
    }

    /**
     * method to add the current element to the svg
     * @param element the current element
     * @param positions a list with elements representing the positions of the elements on the panel
     * @param starts as list of elements representing the start states
     * @param finals as list of elements representing the final states
     * @return the string coding the given element
     * @throws GraFlapException throws a {@link GraFlapException} if the required node cannot be found
     */
    private String appendElement(Element element, List<Element> positions, List<Element> starts,
                                 List<Element> finals) throws GraFlapException {
        StringBuilder sb = new StringBuilder();
        String nodeName = element.getChildText("id");
        String nodeLabel = element.getChildText("name");
        Element position = findElementToId(positions, nodeName).getChild("point");
        double xPos = Double.parseDouble(position.getChildText("x")) / SCALING;
        double yPos = Double.parseDouble(position.getChildText("y")) / -SCALING;

        sb.append("node [ pos = \"").append(xPos).append(", ").append(yPos).append("\", shape= ");
        if (isFinalState(element, finals)) {
            sb.append("doublecircle");
        } else {
            sb.append("circle");
        }
        sb.append(", label = \"").append(nodeLabel).append("\" ]; ").append(nodeName).append("; ");
        if (isStartState(element, starts)) {
            start = new Point2D.Double(xPos - SCALING / 25, yPos);
            sb.append("start -> ").append(nodeName).append("; ");
        }

        return sb.toString();
    }

    /**
     * method to determine if a given state is a final state
     * @param element the considered state
     * @param finals a list holding all final states of the automaton
     * @return true: if the state is found within the list, false: if not
     */
    private boolean isFinalState(Element element, List<Element> finals) {
        String id = element.getChildText("id");
        for (Element finalState: finals) {
            if (id.equals(finalState.getChildText("id"))) {
                return true;
            }
        }
        return false;
    }

    /**
     * method to determine if a given state is a start state
     * @param element the considered state
     * @param starts a list holding all start states of the automaton
     * @return true: if the state is found within the list, false: if not
     */
    private boolean isStartState(Element element, List<Element> starts) {
        String id = element.getChildText("id");
        for (Element startState: starts) {
            if (id.equals(startState.getChildText("id"))) {
                return true;
            }
        }
        return false;
    }

    /**
     * method to find an element with a given id
     * @param elements the list of elements that should be searched
     * @param id the requested id
     * @return the element the the corresponding id
     * @throws GraFlapException throws a {@link GraFlapException} if the required node cannot be found
     */
    private Element findElementToId(List<Element> elements, String id) throws GraFlapException {
        for (Element element: elements) {
            if (id.equals(element.getChildText("state"))) {
                return element;
            }
        }

        throw new GraFlapException("Error: Could not find coordinates for an automaton state.");
    }
}