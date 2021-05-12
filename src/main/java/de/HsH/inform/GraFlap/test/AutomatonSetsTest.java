package de.HsH.inform.GraFlap.test;

import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.State;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.Transition;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutomatonSetsTest {
    private TreeSet<State> xmlStates = new TreeSet<>();
    private TreeSet<State> xmlInitialStates = new TreeSet<>();
    private TreeSet<State> xmlFinalStates = new TreeSet<>();
    private TreeSet<State> studentStates = new TreeSet<>();
    private TreeSet<State> studentInitialStates = new TreeSet<>();
    private TreeSet<State> studentFinalStates = new TreeSet<>();
    private TreeSet<Transition> xmlTransitions = new TreeSet<>();
    private TreeSet<Transition> studentTransitions = new TreeSet<>();

    private DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder documentBuilder;
    private Pattern transitionSetSplitPattern;
    private Pattern setSplitPattern;

    private String setSplitRegex = "\\{[a-z|0-9|,]+\\}";
    private String transitionSetSplitRegex = "[a-z|0-9]+";

    private String jflapXml = "";
    private String studentStatesSet = "";
    private String studentTransitionsSet ="";
    private String studentInitialsSet = "";
    private String studentFinalsSet = "";

    private double gradeSubtraction;
    private ArrayList<String> teacherFeedback;
    private ArrayList<String> studentFeedback;


    public AutomatonSetsTest() throws GraFlapException {
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            setSplitPattern = Pattern.compile(setSplitRegex);
            transitionSetSplitPattern = Pattern.compile(transitionSetSplitRegex);
        }
        catch(ParserConfigurationException e) {
            throw new GraFlapException("XML Parser could not be initiliased. " + e.getMessage());
        }
    }

    private double gradeSets(){
        double gradeSubtraction = 0.0;
        ArrayList<State> missingStatesInStudentSet = new ArrayList<>();
        ArrayList<State> surplusStatesInStudentSet = new ArrayList<>();
        ArrayList<State> missingStatesInStudentInitialSet = new ArrayList<>();
        ArrayList<State> surplusStatesInStudentInitialSet = new ArrayList<>();
        ArrayList<State> missingStatesInStudentFinalSet = new ArrayList<>();
        ArrayList<State> surplusStatesInStudentFinalSet = new ArrayList<>();
        ArrayList<Transition> missingTransitionsInStudentSet = new ArrayList<>();
        ArrayList<Transition> surplusTransitionsInStudentSet = new ArrayList<>();

        boolean equalStates = compareSets(studentStates, xmlStates, missingStatesInStudentSet, surplusStatesInStudentSet);
        boolean equalInitials = compareSets(studentInitialStates, xmlInitialStates, missingStatesInStudentInitialSet, surplusStatesInStudentInitialSet);
        boolean equalFinals = compareSets(studentFinalStates, xmlFinalStates, missingStatesInStudentFinalSet, surplusStatesInStudentFinalSet);
        boolean equalTransitions = compareSets(studentTransitions, xmlTransitions, missingTransitionsInStudentSet, surplusTransitionsInStudentSet);

        int expectedStateCount = Math.max(xmlStates.size(), 1); //filters empty xmlStates
        int expectedTransitionCount = Math.max(xmlTransitions.size(), 1); //filters empty xmlTransitions
        if(!equalStates){
            int mismatchCount = missingStatesInStudentSet.size() + surplusStatesInStudentSet.size();
            gradeSubtraction += (mismatchCount / ((double) expectedStateCount));
            studentFeedback.add("Die angegebenen Zustände enthalten " + mismatchCount + " Fehler.");
        }
        if(!equalInitials){
            int mismatchCount = missingStatesInStudentFinalSet.size() + surplusStatesInStudentFinalSet.size();
            gradeSubtraction += (mismatchCount / ((double) expectedStateCount));
            studentFeedback.add("Die angegebenen Initial-Zustände enthalten " + mismatchCount + " Fehler.");
        }
        if(!equalFinals){
            int mismatchCount = missingStatesInStudentInitialSet.size() + surplusStatesInStudentInitialSet.size();
            gradeSubtraction += (mismatchCount / ((double) expectedStateCount));
            studentFeedback.add("Die angegebenen Final-Zustände enthalten " + mismatchCount + " Fehler.");
        }
        if(!equalTransitions){
            int mismatchCount = missingTransitionsInStudentSet.size() + surplusStatesInStudentSet.size();
            gradeSubtraction += (mismatchCount / ((double) expectedTransitionCount));
            studentFeedback.add("Die angegebene Übergangsmenge enthält " + mismatchCount + " Fehler.");
        }
        gradeSubtraction = Math.min(1.0, gradeSubtraction); //can not subtract more than 100%
        return gradeSubtraction;
    }

    /**
     * Compares two Collections and puts the missing into the given Collections.
     * Used to evaluate differences between student jflap file and student sets.
     * @param compareFrom #1 of the two Collections to be compared
     * @param compareTo #2 of the two Collections to be compared
     * @param missingInFrom all elements that are in #2 but not #1, empty Collection recommended.
     * @param missingInTo all elements that are in #1 but not in #2, empty Collection recommended.
     * @param <T> the Type for all Datastructures. There is no point in comparing different objects.
     * @return returns wether #1 and #2 are equal.
     */
    private <T> boolean compareSets( Collection<T> compareFrom, Collection<T> compareTo, Collection<T> missingInFrom, Collection<T> missingInTo ){
        boolean equal = true;
        for(T to : compareTo){
            if(!compareFrom.contains(to)){
                equal = false;
                missingInFrom.add(to);
            }
        }
        for(T from : compareFrom){
            if(!compareTo.contains(from)){
                equal = false;
                missingInTo.add(from);
            }
        }
        return equal;
    }

    private void readJFlapXML() throws GraFlapException {
        Document jflapXmlDocument = null;
        try {
            jflapXmlDocument = documentBuilder.parse(new ByteArrayInputStream(jflapXml.getBytes(StandardCharsets.UTF_8)));
            NodeList statesXml = jflapXmlDocument.getElementsByTagName("state");
            NodeList transitionsXml = jflapXmlDocument.getElementsByTagName("transition");
            //States get stored in a TreeMap temporarily with id Index to match Transitions to States, Ids are no longer used after that.
            TreeMap<Integer , State> statesMap = new TreeMap<>();

            //NodeList is not iterable :(
            Element elementIterator;
            boolean initial = false;
            boolean finalState = false;
            State newState, toState;
            int id;
            for(int i = 0; i < statesXml.getLength(); i++){
                elementIterator = (Element) statesXml.item(i);
                initial = elementIterator.getElementsByTagName("initial").getLength() > 0;  //are there any initial tags within this state?
                finalState = elementIterator.getElementsByTagName("final").getLength() > 0;     //are there any final tags within this state?
                id = Integer.parseInt(elementIterator.getAttribute("id"));
                newState = new State(elementIterator.getAttribute("name"), initial, finalState);

                statesMap.put(id, newState);
                xmlStates.add(newState);
                if(initial){
                    xmlInitialStates.add(newState);
                }
                if(finalState){
                    xmlFinalStates.add(newState);
                }
            }

            Element fromIterator, toIterator;
            for(int i = 0; i < transitionsXml.getLength(); i++){
                elementIterator = (Element) transitionsXml.item(i);

                //there is always just one tag for either from or to
                fromIterator = (Element) elementIterator.getElementsByTagName("from").item(0);
                toIterator = (Element) elementIterator.getElementsByTagName("to").item(0);

                //get the content of the tag, parse the String to an Integer, then get the State from the State TreeMap
                newState = statesMap.get(Integer.parseInt(fromIterator.getTextContent()));
                toState = statesMap.get(Integer.parseInt(toIterator.getTextContent()));

                //get the alphabet used in the transition
                String read = ((Element) elementIterator.getElementsByTagName("read").item(0)).getTextContent();
                xmlTransitions.add(new Transition(newState, toState, read));
            }
        }
        catch(ClassCastException | SAXException | IOException e) {
            throw new GraFlapException("Could not parse JFLAP file." + e.getMessage());
        }
    }

    private void readStudentSets(){
        //States get stored by their name as key to apply initial & final tags and transitions then the map is abadonend
        TreeMap<String, State> states = new TreeMap<>();
        State tmp;
        for(String stateString : getMatches(studentStatesSet, setSplitPattern)){
            if(states.containsKey(stateString)){
                studentFeedback.add(stateString + " wurde mehrfach bei den Zuständen angegeben.");
                continue;
            }
            tmp = new State(stateString);
            states.put(stateString, tmp);
            studentStates.add(tmp);
        }

        for(String initialState : getMatches(studentInitialsSet, setSplitPattern)){
            if(states.get(initialState).isInitial()){
                studentFeedback.add(initialState + " wurde mehrfach bei den Initial-Zuständen angegeben.");
                continue;
            }
            tmp = states.get(initialState);
            tmp.setInitial(true);
            studentInitialStates.add(tmp);

        }

        for(String finalState : getMatches(studentFinalsSet, setSplitPattern)){
            if(states.get(finalState).isFinale()){
                studentFeedback.add(finalState + " wurde mehrfach bei den Final-Zuständen angegeben.");
                continue;
            }
            tmp = states.get(finalState);
            tmp.setFinale(true);
            studentFinalStates.add(tmp);
        }

        ArrayList<String> transitionValues;
        Transition newTransition;
        for(String transitionInSet : getMatches(studentTransitionsSet, transitionSetSplitPattern)){
            transitionValues = getMatches(transitionInSet, setSplitPattern);
            newTransition = new Transition(states.get(transitionValues.get(0)), states.get(transitionValues.get(1)), transitionValues.get(2));

            if(studentTransitions.contains(newTransition)){
                studentFeedback.add(transitionInSet + " wurde mehrfach bei den Übergängen angegeben.");
                continue;
            }
            studentTransitions.add(newTransition);
        }
    }

    /**
     * returns a List of Strings containing all matches of given Regex Pattern in given String
     * @param in the String to search for Pattern matches
     * @param pattern the pattern to select by
     * @return List of Strings, empty List if no matches found
     */
    private ArrayList<String> getMatches(String in, Pattern pattern){
        ArrayList<String> ret = new ArrayList<>();
        Matcher matcher = pattern.matcher(in);
        while(matcher.find()){
            ret.add(matcher.group());
        }
        return ret;
    }
}
