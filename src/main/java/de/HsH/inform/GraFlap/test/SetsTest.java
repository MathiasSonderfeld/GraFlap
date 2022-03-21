package de.HsH.inform.GraFlap.test;

import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.*;
import de.HsH.inform.GraFlap.exception.GraFlapException;

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * grades the formally noted automaton against a given xml.
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value GraFlap#version}
 */
public class SetsTest {
    private String jflapXml = "";
    private String studentStatesSet = "";
    private String studentInitialsSet = "";
    private String studentFinalsSet = "";
    private String studentAlphabetSet = "";
    private String studentStackAlphabetSet = "";
    private String studentTransitionsSet ="";

    private Pattern cleanUpMultiSet = Pattern.compile("\\{(((\\(|\\[)([a-zA-Z0-9 ,]+)(\\)|\\])|(\\(\\([a-zA-Z0-9 ,]+\\),\\([a-zA-Z0-9 ,]+\\)\\))),?\\s?)*\\}");
    private Pattern cleanUpAtomarSet = Pattern.compile("\\{[a-zA-Z0-9 ,]*\\}");

    private Pattern getAtomarSetsFromMultiSet = Pattern.compile("((\\(|\\[)([a-zA-Z0-9,]+)(\\)|\\])|(\\(\\([a-zA-Z0-9,]+\\),\\([a-zA-Z0-9,]+\\)\\)))");
    private Pattern getAtomarElementsFromSet = Pattern.compile("[a-zA-Z0-9]+");

    private TreeSet<State> studentStates = new TreeSet<>(StateNameComparator.getInstance());
    private TreeSet<State> studentInitialStates = new TreeSet<>(StateNameComparator.getInstance());
    private TreeSet<State> studentFinalStates = new TreeSet<>(StateNameComparator.getInstance());
    private TreeSet<String> studentAlphabet = new TreeSet<>();
    private TreeSet<String> studentStackAlphabet = new TreeSet<>();
    private TreeSet<Transition> studentTransitions = new TreeSet<>(TransitionComparator.getInstance());

    private SetResult<State> statesResult = new SetResult<>();
    private SetResult<State> initialsResult = new SetResult<>();
    private SetResult<State> finalsResult = new SetResult<>();
    private SetResult<String> alphabetResult = new SetResult<>();
    private SetResult<String> stackAlphabetResult = new SetResult<>();
    private SetResult<Transition> transitionsResult = new SetResult<>();

    public void gradeSets() throws GraFlapException {
        XmlAutomatonParser xmlAutomatonParser = new XmlAutomatonParser(jflapXml);
        readStudentSets();
        SetGrader.grade(xmlAutomatonParser.getXmlStates(), studentStates, statesResult);
        SetGrader.grade(xmlAutomatonParser.getXmlInitialStates(), studentInitialStates, initialsResult);
        SetGrader.grade(xmlAutomatonParser.getXmlFinalStates(), studentFinalStates, finalsResult);
        SetGrader.grade(xmlAutomatonParser.getXmlAlphabet(), studentAlphabet, alphabetResult);
        SetGrader.grade(xmlAutomatonParser.getXmlStackAlphabet(), studentStackAlphabet, stackAlphabetResult);
        stackAlphabetResult.getSurplus().removeAll(xmlAutomatonParser.getXmlAlphabet());
        SetGrader.grade(xmlAutomatonParser.getXmlTransitions(), studentTransitions, transitionsResult);
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
            if (!(newState == null)){
                if (studentInitialStates.contains(newState)) {
                    initialsResult.addToDoubles(newState);
                    continue;
                }
                newState.setInitial(true);
                studentInitialStates.add(newState);
            }
        }

        //Finals
        splitMatcher = getAtomarElementsFromSet.matcher(studentFinalsSet);
        while(splitMatcher.find()){
            newState = states.get(splitMatcher.group());
            if (!(newState == null)) {
                if (studentFinalStates.contains(newState)) {
                    finalsResult.addToDoubles(newState);
                    continue;
                }
                newState.setFinale(true);
                studentFinalStates.add(newState);
            }
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
                from = matcherTransition.group();
            }
            if(matcherTransition.find()){
                read = matcherTransition.group();
            }
            if(matcherTransition.find()){
                pop = matcherTransition.group();
            }
            if(matcherTransition.find()){
                to = matcherTransition.group();
            }
            else{
                to = pop; pop = "";
            }
            if(matcherTransition.find()){
                push = matcherTransition.group();
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
        Matcher m = cleanUpAtomarSet.matcher(studentStatesSet.replaceAll("\\s+", ""));
        if(m.find()) this.studentStatesSet = m.group();
    }

    public void setStudentTransitionsSet( String studentTransitionsSet ) {
        Matcher m = cleanUpMultiSet.matcher(studentTransitionsSet.replaceAll("\\s+", ""));
        if(m.find()) this.studentTransitionsSet = m.group();
    }

    public void setStudentInitialsSet( String studentInitialsSet) {
        Matcher m;
        //  accept initials always with or without braces
        m = Pattern.compile("(^[a-zA-Z0-9]+$)|"+cleanUpAtomarSet.pattern()).matcher(studentInitialsSet.replaceAll("\\s+", ""));

        if(m.find()) this.studentInitialsSet = m.group();
    }

    public void setStudentFinalsSet( String studentFinalsSet ) {
        Matcher m = cleanUpAtomarSet.matcher(studentFinalsSet.replaceAll("\\s+", ""));
        if(m.find()) this.studentFinalsSet = m.group();
    }

    public void setStudentAlphabetSet( String studentAlphabetSet ) {
        Matcher m = cleanUpAtomarSet.matcher(studentAlphabetSet.replaceAll("\\s+", ""));
        if(m.find()) this.studentAlphabetSet = m.group();
    }

    public void setStudentStackAlphabetSet( String studentStackAlphabetSet ) {
        Matcher m = cleanUpAtomarSet.matcher(studentStackAlphabetSet.replaceAll("\\s+", ""));
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
