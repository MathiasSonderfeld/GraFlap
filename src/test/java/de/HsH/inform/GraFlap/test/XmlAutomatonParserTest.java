package de.HsH.inform.GraFlap.test;

import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.State;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.StateNameComparator;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.Transition;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.TransitionComparator;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.test.XmlAutomatonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.TreeSet;

public class XmlAutomatonParserTest {

    @Test
    void testSuccessFA() {
        XmlAutomatonParser xmlAutomatonParser = Assertions.assertDoesNotThrow(() -> new XmlAutomatonParser("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure>&#13;<type>fa</type>&#13;<automaton>&#13;<!--The list of states.-->&#13;<state id=\"0\" name=\"s0\">&#13;<x>82.0</x>&#13;<y>90.0</y>&#13;<initial/>&#13;<final/>&#13;</state>&#13;<state id=\"1\" name=\"s1\">&#13;<x>180.0</x>&#13;<y>90.0</y>&#13;</state>&#13;<!--The list of transitions.-->&#13;<transition>&#13;<from>0</from>&#13;<to>1</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>0</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>0</from>&#13;<to>0</to>&#13;<read>x</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>1</to>&#13;<read>x</read>&#13;</transition>&#13;</automaton>&#13;</structure>"));
        State s0 = new State("s0", true, true);
        State s1 = new State("s1");
        Transition t1 = new Transition(s0, s0, "x", "", "");
        Transition t2 = new Transition(s1, s1, "x", "", "");
        Transition t3 = new Transition(s0, s1, "w", "", "");
        Transition t4 = new Transition(s1, s0, "w", "", "");
        TreeSet<State> states = new TreeSet<>(StateNameComparator.getInstance());
        states.add(s0); states.add(s1);
        TreeSet<State> initials = new TreeSet<>(StateNameComparator.getInstance());
        initials.add(s0);
        TreeSet<State> finals = new TreeSet<>(StateNameComparator.getInstance());
        finals.add(s0);
        TreeSet<String> alphabet = new TreeSet<>();
        alphabet.add("w"); alphabet.add("x");
        TreeSet<String> stackAlphabet = new TreeSet<>();
        TreeSet<Transition> transitions = new TreeSet<>(TransitionComparator.getInstance());
        transitions.add(t1); transitions.add(t2); transitions.add(t3); transitions.add(t4);
        Assertions.assertEquals(states, xmlAutomatonParser.getXmlStates());
        Assertions.assertEquals(initials, xmlAutomatonParser.getXmlInitialStates());
        Assertions.assertEquals(finals, xmlAutomatonParser.getXmlFinalStates());
        Assertions.assertEquals(alphabet, xmlAutomatonParser.getXmlAlphabet());
        Assertions.assertEquals(stackAlphabet, xmlAutomatonParser.getXmlStackAlphabet());
        Assertions.assertEquals(transitions, xmlAutomatonParser.getXmlTransitions());
    }

    @Test
    void testSuccessPDA() {
        XmlAutomatonParser xmlAutomatonParser = Assertions.assertDoesNotThrow(() -> new XmlAutomatonParser("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>34.0</x><y>122.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>177.0</x><y>117.0</y></state><state id=\"2\" name=\"q2\"><x>317.0</x><y>112.0</y></state><state id=\"3\" name=\"q3\"><x>465.0</x><y>108.0</y><final/></state><!--The list of transitions.--><transition><from>2</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>1</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>0</from><to>1</to><read>e</read><pop>Z</pop><push>e Z</push></transition><transition><from>1</from><to>1</to><read>e</read><pop>e</pop><push>ee</push></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition></automaton></structure>"));
        State q0 = new State("q0", true, false);
        State q1 = new State("q1");
        State q2 = new State("q2");
        State q3 = new State("q3", false, true);
        Transition t1 = new Transition(q0, q1, "e", "Z", "eZ");
        Transition t2 = new Transition(q1, q1, "e", "e", "ee");
        Transition t3 = new Transition(q1, q2, "f", "e", "E");
        Transition t4 = new Transition(q2, q2, "f", "e", "E");
        Transition t5 = new Transition(q2, q3, "E", "Z", "E");
        TreeSet<State> states = new TreeSet<>(StateNameComparator.getInstance());
        states.add(q0); states.add(q1);  states.add(q2);  states.add(q3);
        TreeSet<State> initials = new TreeSet<>(StateNameComparator.getInstance());
        initials.add(q0);
        TreeSet<State> finals = new TreeSet<>(StateNameComparator.getInstance());
        finals.add(q3);
        TreeSet<String> alphabet = new TreeSet<>();
        alphabet.add("e"); alphabet.add("f");
        TreeSet<String> stackAlphabet = new TreeSet<>();
        stackAlphabet.add("e"); stackAlphabet.add("Z");
        TreeSet<Transition> transitions = new TreeSet<>(TransitionComparator.getInstance());
        transitions.add(t1); transitions.add(t2); transitions.add(t3); transitions.add(t4); transitions.add(t5);
        Assertions.assertEquals(states, xmlAutomatonParser.getXmlStates());
        Assertions.assertEquals(initials, xmlAutomatonParser.getXmlInitialStates());
        Assertions.assertEquals(finals, xmlAutomatonParser.getXmlFinalStates());
        Assertions.assertEquals(alphabet, xmlAutomatonParser.getXmlAlphabet());
        Assertions.assertEquals(stackAlphabet, xmlAutomatonParser.getXmlStackAlphabet());
        Assertions.assertEquals(transitions, xmlAutomatonParser.getXmlTransitions());
    }

    @Test
    void testFail() {
        Assertions.assertThrows(GraFlapException.class, () -> {
            PrintStream errbak = System.err;
            System.setErr(new PrintStream(new ByteArrayOutputStream()));
            new XmlAutomatonParser("");
            System.setErr(errbak);
        });
    }
}
