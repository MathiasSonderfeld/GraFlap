package de.HsH.inform.GraFlap.test;

import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.SetResult;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.State;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.Transition;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class SetsTestTest {

    @Test
    void testSuccess(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>34.0</x><y>122.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>177.0</x><y>117.0</y></state><state id=\"2\" name=\"q2\"><x>317.0</x><y>112.0</y></state><state id=\"3\" name=\"q3\"><x>465.0</x><y>108.0</y><final/></state><!--The list of transitions.--><transition><from>2</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>1</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>0</from><to>1</to><read>e</read><pop>Z</pop><push>e Z</push></transition><transition><from>1</from><to>1</to><read>e</read><pop>e</pop><push>ee</push></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition></automaton></structure>");
        setsTest.setStudentStatesSet("{q0,q1,q2,q3}");
        setsTest.setStudentInitialsSet("{q0}");
        setsTest.setStudentFinalsSet("{q3}");
        setsTest.setStudentAlphabetSet("{e,f}");
        setsTest.setStudentStackAlphabetSet("{Z,e}");
        setsTest.setStudentTransitionsSet("{{q2,f,e,q2,E},{q1,f,e,q2,E},{q0,e,Z,q1,e Z},{q1,e,e,q1,ee},{q2,E,Z,q3,E}}");
        Assertions.assertDoesNotThrow(() -> setsTest.gradeSets());
        SetResult<State> statesSetResult= new SetResult<>();
        statesSetResult.setScore(1.0);
        SetResult<State> initialsSetResult= new SetResult<>();
        initialsSetResult.setScore(1.0);
        SetResult<State> finalsSetResult= new SetResult<>();
        finalsSetResult.setScore(1.0);
        SetResult<String> alphabetSetResult= new SetResult<>();
        alphabetSetResult.setScore(1.0);
        SetResult<String> stackAlphabetSetResult= new SetResult<>();
        stackAlphabetSetResult.setScore(1.0);
        SetResult<Transition> transitionSetResult= new SetResult<>();
        transitionSetResult.setScore(1.0);

        Assertions.assertEquals(statesSetResult, setsTest.getStatesResult());
        Assertions.assertEquals(initialsSetResult, setsTest.getInitialsResult());
        Assertions.assertEquals(finalsSetResult, setsTest.getFinalsResult());
        Assertions.assertEquals(alphabetSetResult, setsTest.getAlphabetResult());
        Assertions.assertEquals(stackAlphabetSetResult, setsTest.getStackAlphabetResult());
        Assertions.assertEquals(transitionSetResult, setsTest.getTransitionsResult());
    }

    @Test
    void testDoubles(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>34.0</x><y>122.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>177.0</x><y>117.0</y></state><state id=\"2\" name=\"q2\"><x>317.0</x><y>112.0</y></state><state id=\"3\" name=\"q3\"><x>465.0</x><y>108.0</y><final/></state><!--The list of transitions.--><transition><from>2</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>1</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>0</from><to>1</to><read>e</read><pop>Z</pop><push>e Z</push></transition><transition><from>1</from><to>1</to><read>e</read><pop>e</pop><push>ee</push></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition></automaton></structure>");
        setsTest.setStudentStatesSet("{q0,q0,q1,q2,q3}");
        setsTest.setStudentInitialsSet("{q0,q0}");
        setsTest.setStudentFinalsSet("{q3,q3}");
        setsTest.setStudentAlphabetSet("{e,e,f}");
        setsTest.setStudentStackAlphabetSet("{Z,e,e}");
        setsTest.setStudentTransitionsSet("{{q2,f,e,q2,E},{q2,f,e,q2,E},{q1,f,e,q2,E},{q0,e,Z,q1,e Z},{q1,e,e,q1,ee},{q2,E,Z,q3,E}}");
        Assertions.assertDoesNotThrow(() -> setsTest.gradeSets());
        State q0 = new State("q0", true, false);
        SetResult<State> statesSetResult= new SetResult<>();
        statesSetResult.setScore(1.0);
        statesSetResult.addToDoubles(q0);
        SetResult<State> initialsSetResult= new SetResult<>();
        initialsSetResult.setScore(1.0);
        initialsSetResult.addToDoubles(q0);
        SetResult<State> finalsSetResult= new SetResult<>();
        finalsSetResult.setScore(1.0);
        finalsSetResult.addToDoubles(new State("q3", false, true));
        SetResult<String> alphabetSetResult= new SetResult<>();
        alphabetSetResult.setScore(1.0);
        alphabetSetResult.addToDoubles("e");
        SetResult<String> stackAlphabetSetResult= new SetResult<>();
        stackAlphabetSetResult.setScore(1.0);
        stackAlphabetSetResult.addToDoubles("e");
        SetResult<Transition> transitionSetResult= new SetResult<>();
        transitionSetResult.setScore(1.0);
        State q2 = new State("q2", false, false);
        transitionSetResult.addToDoubles(new Transition(q2,q2, "f", "e", "E"));

        Assertions.assertEquals(statesSetResult, setsTest.getStatesResult());
        Assertions.assertEquals(initialsSetResult, setsTest.getInitialsResult());
        Assertions.assertEquals(finalsSetResult, setsTest.getFinalsResult());
        Assertions.assertEquals(alphabetSetResult, setsTest.getAlphabetResult());
        Assertions.assertEquals(stackAlphabetSetResult, setsTest.getStackAlphabetResult());
        Assertions.assertEquals(transitionSetResult, setsTest.getTransitionsResult());
    }


    @Test
    void testMissing(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>34.0</x><y>122.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>177.0</x><y>117.0</y></state><state id=\"2\" name=\"q2\"><x>317.0</x><y>112.0</y></state><state id=\"3\" name=\"q3\"><x>465.0</x><y>108.0</y><final/></state><!--The list of transitions.--><transition><from>2</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>1</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>0</from><to>1</to><read>e</read><pop>Z</pop><push>e Z</push></transition><transition><from>1</from><to>1</to><read>e</read><pop>e</pop><push>ee</push></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition></automaton></structure>");
        setsTest.setStudentStatesSet("{q1,q2,q3}");
        setsTest.setStudentInitialsSet("{}");
        setsTest.setStudentFinalsSet("{}");
        setsTest.setStudentAlphabetSet("{f}");
        setsTest.setStudentStackAlphabetSet("{Z}");
        setsTest.setStudentTransitionsSet("{{q1,f,e,q2,E},{q0,e,Z,q1,e Z},{q1,e,e,q1,ee},{q2,E,Z,q3,E}}");
        Assertions.assertDoesNotThrow(() -> setsTest.gradeSets());
        State q0 = new State("q0", true, false);
        SetResult<State> statesSetResult= new SetResult<>();
        statesSetResult.setScore(0.0);
        statesSetResult.addToMissing(q0);
        SetResult<State> initialsSetResult= new SetResult<>();
        initialsSetResult.setScore(0.0);
        initialsSetResult.addToMissing(q0);
        SetResult<State> finalsSetResult= new SetResult<>();
        finalsSetResult.setScore(0.0);
        finalsSetResult.addToMissing(new State("q3", false, true));
        SetResult<String> alphabetSetResult= new SetResult<>();
        alphabetSetResult.setScore(0.0);
        alphabetSetResult.addToMissing("e");
        SetResult<String> stackAlphabetSetResult= new SetResult<>();
        stackAlphabetSetResult.setScore(0.0);
        stackAlphabetSetResult.addToMissing("e");
        SetResult<Transition> transitionSetResult= new SetResult<>();
        transitionSetResult.setScore(0.0);
        State q2 = new State("q2", false, false);
        transitionSetResult.addToMissing(new Transition(q0,new State("q1", false, false), "e", "Z", "e Z"));
        transitionSetResult.addToMissing(new Transition(q2,q2, "f", "e", "E"));

        Assertions.assertEquals(statesSetResult, setsTest.getStatesResult());
        Assertions.assertEquals(initialsSetResult, setsTest.getInitialsResult());
        Assertions.assertEquals(finalsSetResult, setsTest.getFinalsResult());
        Assertions.assertEquals(alphabetSetResult, setsTest.getAlphabetResult());
        Assertions.assertEquals(stackAlphabetSetResult, setsTest.getStackAlphabetResult());
        Assertions.assertEquals(transitionSetResult, setsTest.getTransitionsResult());
    }


    @Test
    void testSurplus(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>34.0</x><y>122.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>177.0</x><y>117.0</y></state><state id=\"2\" name=\"q2\"><x>317.0</x><y>112.0</y></state><state id=\"3\" name=\"q3\"><x>465.0</x><y>108.0</y><final/></state><!--The list of transitions.--><transition><from>2</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>1</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>0</from><to>1</to><read>e</read><pop>Z</pop><push>e Z</push></transition><transition><from>1</from><to>1</to><read>e</read><pop>e</pop><push>ee</push></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition></automaton></structure>");
        setsTest.setStudentStatesSet("{q0,q1,q2,q3,q4}");
        setsTest.setStudentInitialsSet("{q0,q1}");
        setsTest.setStudentFinalsSet("{q2,q3}");
        setsTest.setStudentAlphabetSet("{e,f,g}");
        setsTest.setStudentStackAlphabetSet("{Z,e,f,h}");
        setsTest.setStudentTransitionsSet("{{q4,f,e,q4,E},{q2,f,e,q2,E},{q1,f,e,q2,E},{q0,e,Z,q1,e Z},{q1,e,e,q1,ee},{q2,E,Z,q3,E}}");
        Assertions.assertDoesNotThrow(() -> setsTest.gradeSets());
        SetResult<State> statesSetResult= new SetResult<>();
        statesSetResult.setScore(0.0);
        statesSetResult.addToSurplus(new State("q4", false, false));
        SetResult<State> initialsSetResult= new SetResult<>();
        initialsSetResult.setScore(0.0);
        initialsSetResult.addToSurplus(new State("q1", true, false));
        SetResult<State> finalsSetResult= new SetResult<>();
        finalsSetResult.setScore(0.0);
        finalsSetResult.addToSurplus(new State("q2", false, true));
        SetResult<String> alphabetSetResult= new SetResult<>();
        alphabetSetResult.setScore(0.0);
        alphabetSetResult.addToSurplus("g");
        SetResult<String> stackAlphabetSetResult= new SetResult<>();
        stackAlphabetSetResult.setScore(0.0);
        stackAlphabetSetResult.addToSurplus("h");
        SetResult<Transition> transitionSetResult= new SetResult<>();
        transitionSetResult.setScore(0.0);
        State q4 = new State("q4", false, false);
        transitionSetResult.addToSurplus(new Transition(q4,q4, "f", "e", "E"));

        Assertions.assertEquals(statesSetResult, setsTest.getStatesResult());
        Assertions.assertEquals(initialsSetResult, setsTest.getInitialsResult());
        Assertions.assertEquals(finalsSetResult, setsTest.getFinalsResult());
        Assertions.assertEquals(alphabetSetResult, setsTest.getAlphabetResult());
        Assertions.assertEquals(stackAlphabetSetResult, setsTest.getStackAlphabetResult());
        Assertions.assertEquals(transitionSetResult, setsTest.getTransitionsResult());
    }

    @Test
    void testFailXML(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("null");
        setsTest.setStudentStatesSet("{q0,q1,q2,q3}");
        setsTest.setStudentInitialsSet("{q0}");
        setsTest.setStudentFinalsSet("{q3}");
        setsTest.setStudentAlphabetSet("{e,f}");
        setsTest.setStudentStackAlphabetSet("{Z,e}");
        setsTest.setStudentTransitionsSet("{{q2,f,e,q2,E},{q1,f,e,q2,E},{q0,e,Z,q1,e Z},{q1,e,e,q1,ee},{q2,E,Z,q3,E}}");
        PrintStream systemError = System.err;
        System.setErr(new PrintStream(new ByteArrayOutputStream()));
        Assertions.assertThrows(GraFlapException.class,() -> setsTest.gradeSets());
        System.setErr(systemError);
    }
}
