package de.HsH.inform.GraFlap.JFlapWrapper.automaton;

import file.xml.graph.AutomatonEditorData;
import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;

/**
 * wrapper class for JFLAP isolation
 *
 * @author Benjamin Held (04-20-2016)
 * @version 0.1.4
 * @since 07-04-2016
 */
public class Automaton {
    model.automata.Automaton automaton;

    public Automaton( model.automata.Automaton automaton ) {
        this.automaton = automaton;
    }

    public Automaton( Object automaton ) throws GraFlapException {
        if(automaton instanceof file.xml.graph.AutomatonEditorData) {
            AutomatonEditorData data = (AutomatonEditorData) automaton;
            this.automaton = data.getGraph().getAutomaton();
        }
        else {
            throw new GraFlapException("Error in Automaton: type conversion from JFLAP automaton failed.");
        }
    }

    public model.automata.Automaton getJFLAPAutomaton() {
        return this.automaton;
    }

    public String testType() {
        if(isFiniteAutomation()) {
            return "fa";
        }
        else if(isPushDownAutomaton()) {
            return "pda";
        }
        else if(isTuringMachine()) {
            return "tm";
        }
        else if(isMealyMachine()) {
            return "mealy";
        }
        else if(isMooreMachine()) {
            return "moore";
        }
        return "error";
    }

    public boolean isFiniteAutomation() {
        return ( this.automaton instanceof model.automata.acceptors.fsa.FiniteStateAcceptor );
    }

    public boolean isPushDownAutomaton() {
        return ( this.automaton instanceof model.automata.acceptors.pda.PushdownAutomaton );
    }

    public boolean isTuringMachine() {
        return ( this.automaton instanceof model.automata.turing.TuringMachine );
    }

    public boolean isMealyMachine() {
        return ( this.automaton instanceof model.automata.transducers.mealy.MealyMachine );
    }

    public boolean isMooreMachine() {
        return ( this.automaton instanceof model.automata.transducers.moore.MooreMachine );
    }
}
