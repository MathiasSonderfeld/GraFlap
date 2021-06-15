package de.HsH.inform.GraFlap.test;

import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.CommentMarker;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.SetResult;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.State;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.Transition;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static de.HsH.inform.GraFlap.io.XmlStreamConstants.*;

public class SetsTest {
    private TreeSet<State> xmlStates = new TreeSet<>();
    private TreeSet<State> xmlInitialStates = new TreeSet<>();
    private TreeSet<State> xmlFinalStates = new TreeSet<>();
    private TreeSet<String> xmlAlphabet = new TreeSet<>();
    private TreeSet<String> xmlStackAlphabet = new TreeSet<>();
    private TreeSet<Transition> xmlTransitions = new TreeSet<>();

    private TreeSet<State> studentStates = new TreeSet<>();
    private TreeSet<State> studentInitialStates = new TreeSet<>();
    private TreeSet<State> studentFinalStates = new TreeSet<>();
    private TreeSet<String> studentAlphabet = new TreeSet<>();
    private TreeSet<String> studentStackAlphabet = new TreeSet<>();
    private TreeSet<Transition> studentTransitions = new TreeSet<>();

    private Pattern cleanUpMultiSet = Pattern.compile("\\{(((\\(|\\[)([a-zA-Z0-9 ,]+)(\\)|\\])|(\\(\\([a-zA-Z0-9 ,]+\\),\\([a-zA-Z0-9 ,]+\\)\\))),?)*\\}");
    private Pattern cleanUpAtomarSet = Pattern.compile("\\{[a-zA-Z0-9 ,]*\\}");

    private Pattern getAtomarSetsFromMultiSet = Pattern.compile("((\\(|\\[)([a-zA-Z0-9 ,]+)(\\)|\\])|(\\(\\([a-zA-Z0-9 ,]+\\),\\([a-zA-Z0-9 ,]+\\)\\)))");
    private Pattern getAtomarElementsFromSet = Pattern.compile("[a-zA-Z0-9 ]+");

    private String jflapXml = "";
    private String studentStatesSet = "";
    private String studentInitialsSet = "";
    private String studentFinalsSet = "";
    private String studentAlphabetSet = "";
    private String studentStackAlphabetSet = "";
    private String studentTransitionsSet ="";

    private SetResult<State> statesResult = new SetResult<>();
    private SetResult<State> initialsResult = new SetResult<>();
    private SetResult<State> finalsResult = new SetResult<>();
    private SetResult<String> alphabetResult = new SetResult<>();
    private SetResult<String> stackAlphabetResult = new SetResult<>();
    private SetResult<Transition> transitionsResult = new SetResult<>();

    public void gradeSets() throws GraFlapException {
        readJFlapXML();
        readStudentSets();
        gradeSet(xmlStates, studentStates, statesResult);
        gradeSet(xmlInitialStates, studentInitialStates, initialsResult);
        gradeSet(xmlFinalStates, studentFinalStates, finalsResult);
        gradeSet(xmlAlphabet, studentAlphabet, alphabetResult);
        gradeSet(xmlStackAlphabet, studentStackAlphabet, stackAlphabetResult);
        stackAlphabetResult.getSurplus().removeAll(xmlAlphabet);
        gradeSet(xmlTransitions, studentTransitions, transitionsResult);
    }

    private <T> void gradeSet(TreeSet<T> xmlSet, TreeSet<T> studentSet, SetResult<T> setResult){
        boolean equal = true;
        for(T xmlE : xmlSet){
            if(!studentSet.contains(xmlE)){
                equal = false;
                setResult.addToMissing(xmlE);
            }
        }
        for(T studentE : studentSet){
            if(!xmlSet.contains(studentE)){
                equal = false;
                setResult.addToSurplus(studentE);
            }
        }
        setResult.setScore(equal?1.0:0.0);
    }


    private void readJFlapXML() throws GraFlapException {
        try {
            Document jflapXmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(jflapXml.getBytes(StandardCharsets.UTF_8)));
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

    private void readStudentSets(){
        //States get stored by their name as key to apply initial & final tags and transitions then the map is abadonend
        TreeMap<String, State> states = new TreeMap<>();

        //States
        Matcher splitMatcher = getAtomarElementsFromSet.matcher(studentStatesSet);
        String match;
        State newState;
        while(splitMatcher.find()){
            match = splitMatcher.group();
            if(states.containsKey(match)){
                statesResult.addToDoubles(states.get(match));
                continue;
            }
            newState = new State(match);
            states.put(match, newState);
            studentStates.add(newState);
        }

        //Initials
        splitMatcher = getAtomarElementsFromSet.matcher(studentInitialsSet);
        while(splitMatcher.find()){
            newState = states.get(splitMatcher.group());
            if(studentInitialStates.contains(newState)){
                initialsResult.addToDoubles(newState);
                continue;
            }
            newState.setInitial(true);
            studentInitialStates.add(newState);
        }

        //Finals
        splitMatcher = getAtomarElementsFromSet.matcher(studentFinalsSet);
        while(splitMatcher.find()){
            newState = states.get(splitMatcher.group());
            if(studentFinalStates.contains(newState)){
                finalsResult.addToDoubles(newState);
                continue;
            }
            newState.setFinale(true);
            studentFinalStates.add(newState);
        }

        //Transitions
        splitMatcher = getAtomarSetsFromMultiSet.matcher(studentTransitionsSet);
        Matcher matcherTransition;
        String from = "", to = "", read = "", pop = "", push = "";
        Transition newTransition;
        State fromState, toState;
        boolean hasUsedSquareBrackets = false;
        while(splitMatcher.find()){
            match = splitMatcher.group();
            hasUsedSquareBrackets = match.startsWith("[");
            matcherTransition = getAtomarElementsFromSet.matcher(match);
            if(matcherTransition.find()){
                from = matcherTransition.group().replaceAll("\\s+", "");
            }
            if(matcherTransition.find()){
                read = matcherTransition.group().replaceAll("\\s+", "");
            }
            if(matcherTransition.find()){
                pop = matcherTransition.group().replaceAll("\\s+", "");
            }
            if(matcherTransition.find()){
                to = matcherTransition.group().replaceAll("\\s+", "");
            }
            if(matcherTransition.find()){
                push = matcherTransition.group().replaceAll("\\s+", "");
            }
            fromState = states.get(from);
            toState = states.get(to);
            if(fromState == null || toState == null) continue;
            newTransition = new Transition(fromState, toState, read, pop, push);
            if(studentTransitions.contains(newTransition)){
                transitionsResult.addToDoubles(newTransition);
            }
            else {
                studentTransitions.add(newTransition);
            }
        }
        if(hasUsedSquareBrackets){
            transitionsResult.addToComments(CommentMarker.SquareBrackets);
        }

        //Alphabet
        splitMatcher = getAtomarElementsFromSet.matcher(studentAlphabetSet);
        while(splitMatcher.find()){
            match = splitMatcher.group();
            if(studentAlphabet.contains(match)){
                alphabetResult.addToDoubles(match);
                continue;
            }
            studentAlphabet.add(match);
        }

        //Stack Alphabet
        splitMatcher = getAtomarElementsFromSet.matcher(studentStackAlphabetSet);
        while(splitMatcher.find()){
            match = splitMatcher.group();
            if(studentStackAlphabet.contains(match)){
                stackAlphabetResult.addToDoubles(match);
                continue;
            }
            studentStackAlphabet.add(match);
        }
    }

    public void setJflapXml( String jflapXml ) {
        this.jflapXml = jflapXml;
    }

    public void setStudentStatesSet( String studentStatesSet ) {
        Matcher m = cleanUpAtomarSet.matcher(studentStatesSet);
        if(m.find()) this.studentStatesSet = m.group();
    }

    public void setStudentTransitionsSet( String studentTransitionsSet ) {
        Matcher m = cleanUpMultiSet.matcher(studentTransitionsSet);
        if(m.find()) this.studentTransitionsSet = m.group();
    }

    public void setStudentInitialsSet( String studentInitialsSet, boolean isDFA ) {
        Matcher m;
        if(isDFA){
            m = Pattern.compile("[a-z|A-Z]+[0-9]*").matcher(studentInitialsSet);
        }
        else {
            m = cleanUpAtomarSet.matcher(studentInitialsSet);
        }
        if(m.find()) this.studentInitialsSet = m.group();
    }

    public void setStudentFinalsSet( String studentFinalsSet ) {
        Matcher m = cleanUpAtomarSet.matcher(studentFinalsSet);
        if(m.find()) this.studentFinalsSet = m.group();
    }

    public void setStudentAlphabetSet( String studentAlphabetSet ) {
        Matcher m = cleanUpAtomarSet.matcher(studentAlphabetSet);
        if(m.find()) this.studentAlphabetSet = m.group();
    }

    public void setStudentStackAlphabetSet( String studentStackAlphabetSet ) {
        Matcher m = cleanUpAtomarSet.matcher(studentStackAlphabetSet);
        if(m.find()) this.studentStackAlphabetSet = m.group();
    }

    public SetResult<State> getStatesResult() {
        return statesResult;
    }

    public SetResult<State> getInitialsResult() {
        return initialsResult;
    }

    public SetResult<State> getFinalsResult() {
        return finalsResult;
    }

    public SetResult<String> getAlphabetResult() {
        return alphabetResult;
    }

    public SetResult<String> getStackAlphabetResult() {
        return stackAlphabetResult;
    }

    public SetResult<Transition> getTransitionsResult() {
        return transitionsResult;
    }
}
