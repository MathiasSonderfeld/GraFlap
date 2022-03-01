package de.HsH.inform.GraFlap.test;

import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.State;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.Transition;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.io.SilentHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static de.HsH.inform.GraFlap.io.XmlStreamConstants.getNodeListAsList;
import static de.HsH.inform.GraFlap.io.XmlStreamConstants.toElement;

/**
 * parses a jff automaton into Sets of State, String and Transition objects for states, initials, finals, alphabet, stackalphabet and transitions
 * @author Mathias Sonderfeld
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class XmlAutomatonParser {
    private String xml;
    private TreeSet<State> xmlStates = new TreeSet<>();
    private TreeSet<State> xmlInitialStates = new TreeSet<>();
    private TreeSet<State> xmlFinalStates = new TreeSet<>();
    private TreeSet<String> xmlAlphabet = new TreeSet<>();
    private TreeSet<String> xmlStackAlphabet = new TreeSet<>();
    private TreeSet<Transition> xmlTransitions = new TreeSet<>();

    public XmlAutomatonParser(String xml) throws GraFlapException{
        this.xml = xml;
        parse();
    }

    private void parse() throws GraFlapException {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            db.setErrorHandler(SilentHandler.instance);
            Document jflapXmlDocument = db.parse(new ByteArrayInputStream(this.xml.getBytes(StandardCharsets.UTF_8)));
            Element structure = jflapXmlDocument.getDocumentElement();
            List<Element> parsedStates = getNodeListAsList(structure.getElementsByTagName("state")).stream().map(toElement).collect(Collectors.toList());
            List<Element> parsedTransitions = getNodeListAsList(structure.getElementsByTagName("transition")).stream().map(toElement).collect(Collectors.toList());
            TreeMap<Integer, State> statesMap = new TreeMap<>();

            //read States, Initials & Finals
            Integer id;
            String name;
            boolean isInitial, isFinal;
            State newState;
            for(Element parsedState : parsedStates){
                id = Integer.parseInt(parsedState.getAttribute("id"));
                name = parsedState.getAttribute("name");
                isInitial = parsedState.getElementsByTagName("initial").getLength() > 0;
                isFinal = parsedState.getElementsByTagName("final").getLength() > 0;
                newState = new State(name, isInitial, isFinal);
                statesMap.put(id, newState);
                xmlStates.add(newState);
                if(isInitial) xmlInitialStates.add(newState);
                if(isFinal) xmlFinalStates.add(newState);
            }

            //read Alphabets and Transitions
            Integer fromId, toId;
            State from, to;
            String read, pop = "", push = "";
            boolean isPushDownAutomaton = parsedTransitions.get(0).getElementsByTagName("pop").getLength() > 0;
            for(Element parsedTransition : parsedTransitions){
                fromId = Integer.parseInt(parsedTransition.getElementsByTagName("from").item(0).getTextContent());
                toId = Integer.parseInt(parsedTransition.getElementsByTagName("to").item(0).getTextContent());
                read = parsedTransition.getElementsByTagName("read").item(0).getTextContent();

                read.replaceAll("\\s+", "");
                if(read.isEmpty()) read = "E";

                if(isPushDownAutomaton){
                    pop = parsedTransition.getElementsByTagName("pop").item(0).getTextContent().replaceAll("\\s+", "");
                    push = parsedTransition.getElementsByTagName("push").item(0).getTextContent().replaceAll("\\s+", "");

                    if(pop.isEmpty()) pop = "E";
                    if(push.isEmpty()) push = "E";

                    for(char letter : pop.toCharArray()) if('E' != letter) xmlStackAlphabet.add("" + letter);
                    for(char letter : push.toCharArray()) if('E' != letter) xmlStackAlphabet.add("" + letter);
                }
                from = statesMap.get(fromId);
                to = statesMap.get(toId);
                xmlTransitions.add(new Transition(from, to, read, pop, push));
                if(!"E".equals(read)){
                    xmlAlphabet.add(read);
                }
            }
        }
        catch(ClassCastException | ParserConfigurationException | IOException | SAXException e){
            throw new GraFlapException("Cant parse Sets from XML");
        }
    }

    public TreeSet<State> getXmlStates() {
        return xmlStates;
    }

    public TreeSet<State> getXmlInitialStates() {
        return xmlInitialStates;
    }

    public TreeSet<State> getXmlFinalStates() {
        return xmlFinalStates;
    }

    public TreeSet<String> getXmlAlphabet() {
        return xmlAlphabet;
    }

    public TreeSet<String> getXmlStackAlphabet() {
        return xmlStackAlphabet;
    }

    public TreeSet<Transition> getXmlTransitions() {
        return xmlTransitions;
    }
}
