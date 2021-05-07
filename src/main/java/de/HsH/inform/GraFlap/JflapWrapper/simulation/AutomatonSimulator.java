package de.HsH.inform.GraFlap.JflapWrapper.simulation;

import model.algorithms.testinput.simulate.*;
import model.symbols.symbolizer.Symbolizers;
import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;

/**
 * wrapper class for JFLAP isolation to represent an automaton simulator
 * @author Benjamin Held (04-20-2016)
 * @since 06-26-2016
 * @version 0.1.4
 */
public class AutomatonSimulator {
    /**
     * the automaton simulator that this class represents
     */
    private final model.algorithms.testinput.simulate.AutoSimulator simulator;
    private final model.automata.Automaton automaton;

    /**
     * Constructor
     * @param automaton the automaton that should be tested
     */
    public AutomatonSimulator(Automaton automaton) {
        simulator = new AutoSimulator(automaton.getJFLAPAutomaton(),0);
        this.automaton = automaton.getJFLAPAutomaton();
    }

    /**
     * method to determine if the provided word is accepted from the given automaton or machine
     * @param word the provided input
     * @return true: if the input is accepted, false: if not
     */
    public boolean acceptInput(String word) {
        simulator.beginSimulation(Symbolizers.symbolize(word, automaton));
        return !simulator.getNextAccept().isEmpty();
    }

    /**
     * method to determine the produced output from the given machine
     * @param word the provided input
     * @return the output that is generate by the machine based on the provided input
     */
    public String testAndGetResult(String word) {
        simulator.beginSimulation(Symbolizers.symbolize(word, automaton));
        return simulator.getLastHalt().get(0).getLast().getStringForIndex(0).toString();
    }
}
