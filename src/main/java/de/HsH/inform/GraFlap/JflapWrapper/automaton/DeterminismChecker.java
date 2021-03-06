package de.HsH.inform.GraFlap.JflapWrapper.automaton;


import model.automata.determinism.DeterminismCheckerFactory;

/**
 * wrapper class for JFLAP isolation to check for determinism
 * @author Benjamin Held (04-20-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class DeterminismChecker {

    /**
     * static wrapper method to determine if the given automaton is deterministic or not
     * @param automaton the automaton that is required to generate the correct detector
     * @return a boolean determine if the given input lead to a deterministic automaton or not
     */
    public static boolean isDeterministic( Automaton automaton) {
        model.automata.determinism.DeterminismChecker checker = DeterminismCheckerFactory.getChecker(automaton.getJFLAPAutomaton());
        return checker.isDeterministic(automaton.getJFLAPAutomaton());
    }
}
