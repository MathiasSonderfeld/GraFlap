package de.HsH.inform.GraFlap.JflapWrapper.automaton;

import de.HsH.inform.GraFlap.JflapWrapper.exception.AutomatonMismatchException;

/**
 * child class of {@link Automaton} to use specific details of turing machines
 * @author Benjamin Held (06-23-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class TuringMachine extends Automaton {

    public TuringMachine(model.automata.Automaton automaton) {
        super(automaton);
    }

    /**
     * method to return the blank symbol defined for the given turing machine
     * @return a {@link String} representing the blank symbol of the turing machine
     * @throws AutomatonMismatchException throws a {@link AutomatonMismatchException} if the provided automaton is not
     *  a turing machine
     */
    public String getBlankSymbol() throws AutomatonMismatchException {
        if (isTuringMachine()) {
            return ((model.automata.turing.MultiTapeTuringMachine) automaton).getBlankSymbol().toString();
        } else {
            throw new AutomatonMismatchException("Error: Given automaton is not a turing machine");
        }
    }
}