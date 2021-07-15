package de.HsH.inform.GraFlap.JflapWrapper.file;

import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import model.automata.*;
import model.automata.acceptors.FinalStateSet;
import model.automata.turing.*;
import model.symbols.Symbol;
import org.jdom2.Document;
import org.jdom2.Element;

import java.util.List;

/**
 * helper class to transform a single tape turing machine coded in jff block format into a jflap 8 automaton
 * @author Benjamin Held (05-02-2016)
 * @since 06-14-2016
 * @version 0.1.2
 */
public class TuringConverter {
    private final StateSet states;
    private final FinalStateSet finalStates;
    private StartState startState;
    private final TransitionSet<MultiTapeTMTransition> transitions;
    private final InputAlphabet alphabet;
    private final TapeAlphabet tapeAlphabet;

    public TuringConverter(String submission) throws GraFlapException {
        states = new StateSet();
        finalStates = new FinalStateSet();
        alphabet = new InputAlphabet();
        tapeAlphabet = new TapeAlphabet();
        transitions = new TransitionSet<>();
        String errorMessage = "Error: Transformation of the jff turing machine to jflap automaton failed.";
        Document document = DOMFactory.buildDocument(submission, errorMessage);
        generateStates(document.getRootElement().getChild("automaton").getChildren("block"));
        generateTransitions(document.getRootElement().getChild("automaton").getChildren("transition"));
        checkFormalComponents();
    }

    public Automaton getAutomaton() {
        return new Automaton(new MultiTapeTuringMachine(states, tapeAlphabet, new BlankSymbol(), alphabet, transitions,
                                                        startState, finalStates, 1));
    }

    /**
     * method to check if the formal components are valid
     * @throws GraFlapException throws a {@link GraFlapException} when one or more components are invalid
     */
    private void checkFormalComponents() throws GraFlapException {
        StringBuilder errorString = new StringBuilder();
        Boolean hasFormalError = false;

        if (startState == null) {
            hasFormalError = true;
            errorString.append("\nNo initial state found. ");
        }
        if (finalStates.size() == 0) {
            hasFormalError = true;
            errorString.append("\nNo final state found. ");
        }
        if (alphabet.size() == 0) {
            hasFormalError = true;
            errorString.append("\nNo input alphabet found. ");
        }
        if (transitions.size() == 0) {
            hasFormalError = true;
            errorString.append("\nNo transitions found. ");
        }
        if (hasFormalError) {
            throw new GraFlapException(errorString.toString());
        }
    }

    /**
     * method to create to automaton states from a list of coded states
     * @param blocks the list of xml codes states
     */
    private void generateStates(List<Element> blocks) {
        for (Element element: blocks) {
            int id = Integer.parseInt(element.getAttributeValue("id"));
            State state = new State(element.getAttributeValue("name"), id);
            if (element.getChild("initial") != null) {
                startState = new StartState(element.getAttributeValue("name"), id);
            }
            if (element.getChild("final") != null) {
                finalStates.add(state);
            }
            states.add(state);
        }
    }

    /**
     * method to create the automaton transitions from the list of coded transitions
     * @param xmlTransitions the list of xml coded transitions
     * @throws GraFlapException throws a {@link GraFlapException} if an element cannot be found
     */
    private void generateTransitions(List<Element> xmlTransitions) throws GraFlapException {
        Symbol blank = new BlankSymbol().getSymbol();

        for (Element transition: xmlTransitions) {
            State from = findElementToId(transition.getChildText("from"));
            State to = findElementToId(transition.getChildText("to"));
            Symbol r = !transition.getChildText("read").isEmpty()? new Symbol(transition.getChildText("read")): blank;
            if (!r.equals(blank)) {
                alphabet.add(r);
                tapeAlphabet.add(r);
            }
            Symbol w = !transition.getChildText("write").isEmpty()? new Symbol(transition.getChildText("write")): blank;
            if (!w.equals(blank)) {
                tapeAlphabet.add(w);
            }
            String move = transition.getChildText("move");
            transitions.add(new MultiTapeTMTransition(from, to, r, w, TuringMachineMove.getMove(move)));
        }
    }

    /**
     * method to find an element with a given id
     * @param idString the requested id as a string
     * @return the element the the corresponding id
     * @throws GraFlapException throws a {@link GraFlapException} if the required node cannot be found
     */
    private State findElementToId(String idString) throws GraFlapException {
        int id = Integer.parseInt(idString);
        for (State state: states) {
            if (state.getID() == id) {
                return state;
            }
        }

        throw new GraFlapException("Error: Could not find state to given id.");
    }
}