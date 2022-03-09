package de.HsH.inform.GraFlap.JflapWrapper.automaton;

import de.HsH.inform.GraFlap.entity.Type;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import file.xml.graph.AutomatonEditorData;

/**
 * wrapper class for JFLAP isolation
 *
 * @author Benjamin Held (04-20-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
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

    public Type testType() {
        if(isFiniteAutomation()) {
            return Type.FA;
        }
        else if(isPushDownAutomaton()) {
            return Type.PDA;
        }
        else if(isTuringMachine()) {
            return Type.TM;
        }
        else if(isMealyMachine()) {
            return Type.MEALY;
        }
        else if(isMooreMachine()) {
            return Type.MOORE;
        }
        return Type.ERROR;
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
