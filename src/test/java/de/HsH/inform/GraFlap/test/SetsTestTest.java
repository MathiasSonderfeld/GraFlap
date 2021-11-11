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
    void testSuccessSpaces(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>34.0</x><y>122.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>177.0</x><y>117.0</y></state><state id=\"2\" name=\"q2\"><x>317.0</x><y>112.0</y></state><state id=\"3\" name=\"q3\"><x>465.0</x><y>108.0</y><final/></state><!--The list of transitions.--><transition><from>2</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>1</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>0</from><to>1</to><read>e</read><pop>Z</pop><push>e Z</push></transition><transition><from>1</from><to>1</to><read>e</read><pop>e</pop><push>ee</push></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition></automaton></structure>");
        setsTest.setStudentStatesSet(" { q 0 , q 1 , q 2 , q 3 } ");
        setsTest.setStudentInitialsSet(" { q 0 } ");
        setsTest.setStudentFinalsSet("{ q 3 } ");
        setsTest.setStudentAlphabetSet("{ e , f } ");
        setsTest.setStudentStackAlphabetSet("{ Z , e } ");
        setsTest.setStudentTransitionsSet("{ ( q 2 , f , e , q 2 , E ) , ( q 1 , f , e , q 2 , E ) , ( q 0 , e , Z , q 1 , e   Z ) , ( q 1 , e , e , q 1 , e e ) , ( q 2 , E , Z , q 3 , E ) } ");
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
    void testSuccessDFA(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure>&#13;<type>fa</type>&#13;<automaton>&#13;<!--The list of states.-->&#13;<state id=\"0\" name=\"s0\">&#13;<x>82.0</x>&#13;<y>90.0</y>&#13;<initial/>&#13;<final/>&#13;</state>&#13;<state id=\"1\" name=\"s1\">&#13;<x>180.0</x>&#13;<y>90.0</y>&#13;</state>&#13;<!--The list of transitions.-->&#13;<transition>&#13;<from>0</from>&#13;<to>1</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>0</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>0</from>&#13;<to>0</to>&#13;<read>x</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>1</to>&#13;<read>x</read>&#13;</transition>&#13;</automaton>&#13;</structure>");
        setsTest.setStudentStatesSet("{s0,s1}");
        setsTest.setStudentInitialsSet("s0");
        setsTest.setStudentFinalsSet("{s0}");
        setsTest.setStudentAlphabetSet("{w,x}");
        setsTest.setStudentStackAlphabetSet("");
        setsTest.setStudentTransitionsSet("{(s0, x, s0), (s0, w, s1), (s1, w, s0), (s1, x, s1)}");
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
    void testFailDFA(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure>&#13;<type>fa</type>&#13;<automaton>&#13;<!--The list of states.-->&#13;<state id=\"0\" name=\"s0\">&#13;<x>82.0</x>&#13;<y>90.0</y>&#13;<initial/>&#13;<final/>&#13;</state>&#13;<state id=\"1\" name=\"s1\">&#13;<x>180.0</x>&#13;<y>90.0</y>&#13;</state>&#13;<!--The list of transitions.-->&#13;<transition>&#13;<from>0</from>&#13;<to>1</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>0</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>0</from>&#13;<to>0</to>&#13;<read>x</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>1</to>&#13;<read>x</read>&#13;</transition>&#13;</automaton>&#13;</structure>");
        setsTest.setStudentStatesSet("{s0,s1}");
        setsTest.setStudentInitialsSet("s0,s0");
        setsTest.setStudentFinalsSet("{s0}");
        setsTest.setStudentAlphabetSet("{w,x}");
        setsTest.setStudentStackAlphabetSet("");
        setsTest.setStudentTransitionsSet("{(s0, x, s0), (s0, w, s1), (s1, w, s0), (s1, x, s1)}");
        Assertions.assertDoesNotThrow(() -> setsTest.gradeSets());
        SetResult<State> statesSetResult= new SetResult<>();
        statesSetResult.setScore(1.0);
        SetResult<State> initialsSetResult= new SetResult<>();
        initialsSetResult.setScore(0.0);
        initialsSetResult.addToMissing(new State("s0", true, true));
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
    void testSuccessFA(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure>&#13;<type>fa</type>&#13;<automaton>&#13;<!--The list of states.-->&#13;<state id=\"0\" name=\"s0\">&#13;<x>82.0</x>&#13;<y>90.0</y>&#13;<initial/>&#13;<final/>&#13;</state>&#13;<state id=\"1\" name=\"s1\">&#13;<x>180.0</x>&#13;<y>90.0</y>&#13;</state>&#13;<!--The list of transitions.-->&#13;<transition>&#13;<from>0</from>&#13;<to>1</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>0</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>0</from>&#13;<to>0</to>&#13;<read>x</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>1</to>&#13;<read>x</read>&#13;</transition>&#13;</automaton>&#13;</structure>");
        setsTest.setStudentStatesSet("{s0,s1}");
        setsTest.setStudentInitialsSet("{s0}");
        setsTest.setStudentFinalsSet("{s0}");
        setsTest.setStudentAlphabetSet("{w,x}");
        setsTest.setStudentStackAlphabetSet("");
        setsTest.setStudentTransitionsSet("{(s0, x, s0), (s0, w, s1), (s1, w, s0), (s1, x, s1)}");
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
    void testDoublesFA(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure>&#13;<type>fa</type>&#13;<automaton>&#13;<!--The list of states.-->&#13;<state id=\"0\" name=\"s0\">&#13;<x>82.0</x>&#13;<y>90.0</y>&#13;<initial/>&#13;<final/>&#13;</state>&#13;<state id=\"1\" name=\"s1\">&#13;<x>180.0</x>&#13;<y>90.0</y>&#13;</state>&#13;<!--The list of transitions.-->&#13;<transition>&#13;<from>0</from>&#13;<to>1</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>0</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>0</from>&#13;<to>0</to>&#13;<read>x</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>1</to>&#13;<read>x</read>&#13;</transition>&#13;</automaton>&#13;</structure>");
        setsTest.setStudentStatesSet("{s0,s0,s1}");
        setsTest.setStudentInitialsSet("{s0,s0}");
        setsTest.setStudentFinalsSet("{s0,s0}");
        setsTest.setStudentAlphabetSet("{w,w,x}");
        setsTest.setStudentStackAlphabetSet("");
        setsTest.setStudentTransitionsSet("{(s0, x, s0), (s0, x, s0), (s0, w, s1), (s1, w, s0), (s1, x, s1)}");
        Assertions.assertDoesNotThrow(() -> setsTest.gradeSets());
        SetResult<State> statesSetResult= new SetResult<>();
        statesSetResult.setScore(1.0);
        State s0 = new State("s0");
        s0.setFinale(true);
        s0.setInitial(true);
        statesSetResult.addToDoubles(s0);
        SetResult<State> initialsSetResult= new SetResult<>();
        initialsSetResult.setScore(1.0);
        initialsSetResult.addToDoubles(s0);
        SetResult<State> finalsSetResult= new SetResult<>();
        finalsSetResult.setScore(1.0);
        finalsSetResult.addToDoubles(s0);
        SetResult<String> alphabetSetResult= new SetResult<>();
        alphabetSetResult.setScore(1.0);
        alphabetSetResult.addToDoubles("w");
        SetResult<String> stackAlphabetSetResult= new SetResult<>();
        stackAlphabetSetResult.setScore(1.0);
        SetResult<Transition> transitionSetResult= new SetResult<>();
        transitionSetResult.setScore(1.0);
        transitionSetResult.addToDoubles(new Transition(s0,s0,"x","", ""));

        Assertions.assertEquals(statesSetResult, setsTest.getStatesResult());
        Assertions.assertEquals(initialsSetResult, setsTest.getInitialsResult());
        Assertions.assertEquals(finalsSetResult, setsTest.getFinalsResult());
        Assertions.assertEquals(alphabetSetResult, setsTest.getAlphabetResult());
        Assertions.assertEquals(stackAlphabetSetResult, setsTest.getStackAlphabetResult());
        Assertions.assertEquals(transitionSetResult, setsTest.getTransitionsResult());
    }

    @Test
    void testMissingFA(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure>&#13;<type>fa</type>&#13;<automaton>&#13;<!--The list of states.-->&#13;<state id=\"0\" name=\"s0\">&#13;<x>82.0</x>&#13;<y>90.0</y>&#13;<initial/>&#13;<final/>&#13;</state>&#13;<state id=\"1\" name=\"s1\">&#13;<x>180.0</x>&#13;<y>90.0</y>&#13;</state>&#13;<!--The list of transitions.-->&#13;<transition>&#13;<from>0</from>&#13;<to>1</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>0</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>0</from>&#13;<to>0</to>&#13;<read>x</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>1</to>&#13;<read>x</read>&#13;</transition>&#13;</automaton>&#13;</structure>");
        setsTest.setStudentStatesSet("{s1}");
        setsTest.setStudentInitialsSet("{}");
        setsTest.setStudentFinalsSet("{}");
        setsTest.setStudentAlphabetSet("{x}");
        setsTest.setStudentStackAlphabetSet("");
        setsTest.setStudentTransitionsSet("{(s1, x, s1)}");
        Assertions.assertDoesNotThrow(() -> setsTest.gradeSets());
        SetResult<State> statesSetResult= new SetResult<>();
        statesSetResult.setScore(0.0);
        State s0 = new State("s0", true, true);
        statesSetResult.addToMissing(s0);
        SetResult<State> initialsSetResult= new SetResult<>();
        initialsSetResult.setScore(0.0);
        initialsSetResult.addToMissing(s0);
        SetResult<State> finalsSetResult= new SetResult<>();
        finalsSetResult.setScore(0.0);
        finalsSetResult.addToMissing(s0);
        SetResult<String> alphabetSetResult= new SetResult<>();
        alphabetSetResult.setScore(0.0);
        alphabetSetResult.addToMissing("w");
        SetResult<String> stackAlphabetSetResult= new SetResult<>();
        stackAlphabetSetResult.setScore(1.0);
        SetResult<Transition> transitionSetResult= new SetResult<>();
        transitionSetResult.setScore(0.0);
        State s1 = new State("s1");
        transitionSetResult.addToMissing(new Transition(s0,s0,"x","", ""));
        transitionSetResult.addToMissing(new Transition(s0,s1,"w","", ""));
        transitionSetResult.addToMissing(new Transition(s1,s0,"w","", ""));

        Assertions.assertEquals(statesSetResult, setsTest.getStatesResult());
        Assertions.assertEquals(initialsSetResult, setsTest.getInitialsResult());
        Assertions.assertEquals(finalsSetResult, setsTest.getFinalsResult());
        Assertions.assertEquals(alphabetSetResult, setsTest.getAlphabetResult());
        Assertions.assertEquals(stackAlphabetSetResult, setsTest.getStackAlphabetResult());
        Assertions.assertEquals(transitionSetResult, setsTest.getTransitionsResult());
    }

    @Test
    void testSurplusFA(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure>&#13;<type>fa</type>&#13;<automaton>&#13;<!--The list of states.-->&#13;<state id=\"0\" name=\"s0\">&#13;<x>82.0</x>&#13;<y>90.0</y>&#13;<initial/>&#13;<final/>&#13;</state>&#13;<state id=\"1\" name=\"s1\">&#13;<x>180.0</x>&#13;<y>90.0</y>&#13;</state>&#13;<!--The list of transitions.-->&#13;<transition>&#13;<from>0</from>&#13;<to>1</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>0</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>0</from>&#13;<to>0</to>&#13;<read>x</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>1</to>&#13;<read>x</read>&#13;</transition>&#13;</automaton>&#13;</structure>");
        setsTest.setStudentStatesSet("{s0,s2,s1}");
        setsTest.setStudentInitialsSet("{s0,s2}");
        setsTest.setStudentFinalsSet("{s0,s2}");
        setsTest.setStudentAlphabetSet("{w,y,x}");
        setsTest.setStudentStackAlphabetSet("");
        setsTest.setStudentTransitionsSet("{(s2, x, s2), (s0, x, s0), (s0, w, s1), (s1, w, s0), (s1, x, s1)}");
        Assertions.assertDoesNotThrow(() -> setsTest.gradeSets());
        SetResult<State> statesSetResult= new SetResult<>();
        statesSetResult.setScore(0.0);
        State s2 = new State("s2", true, true);
        statesSetResult.addToSurplus(s2);
        SetResult<State> initialsSetResult= new SetResult<>();
        initialsSetResult.setScore(0.0);
        initialsSetResult.addToSurplus(s2);
        SetResult<State> finalsSetResult= new SetResult<>();
        finalsSetResult.setScore(0.0);
        finalsSetResult.addToSurplus(s2);
        SetResult<String> alphabetSetResult= new SetResult<>();
        alphabetSetResult.setScore(0.0);
        alphabetSetResult.addToSurplus("y");
        SetResult<String> stackAlphabetSetResult= new SetResult<>();
        stackAlphabetSetResult.setScore(1.0);
        SetResult<Transition> transitionSetResult= new SetResult<>();
        transitionSetResult.setScore(0.0);
        transitionSetResult.addToSurplus(new Transition(s2,s2,"x","", ""));

        Assertions.assertEquals(statesSetResult, setsTest.getStatesResult());
        Assertions.assertEquals(initialsSetResult, setsTest.getInitialsResult());
        Assertions.assertEquals(finalsSetResult, setsTest.getFinalsResult());
        Assertions.assertEquals(alphabetSetResult, setsTest.getAlphabetResult());
        Assertions.assertEquals(stackAlphabetSetResult, setsTest.getStackAlphabetResult());
        Assertions.assertEquals(transitionSetResult, setsTest.getTransitionsResult());
    }

    @Test
    void testSuccessPDA(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>34.0</x><y>122.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>177.0</x><y>117.0</y></state><state id=\"2\" name=\"q2\"><x>317.0</x><y>112.0</y></state><state id=\"3\" name=\"q3\"><x>465.0</x><y>108.0</y><final/></state><!--The list of transitions.--><transition><from>2</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>1</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>0</from><to>1</to><read>e</read><pop>Z</pop><push>e Z</push></transition><transition><from>1</from><to>1</to><read>e</read><pop>e</pop><push>ee</push></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition></automaton></structure>");
        setsTest.setStudentStatesSet("{q0,q1,q2,q3}");
        setsTest.setStudentInitialsSet("{q0}");
        setsTest.setStudentFinalsSet("{q3}");
        setsTest.setStudentAlphabetSet("{e,f}");
        setsTest.setStudentStackAlphabetSet("{Z,e}");
        setsTest.setStudentTransitionsSet("{(q2,f,e,q2,E),(q1,f,e,q2,E),(q0,e,Z,q1,e Z),(q1,e,e,q1,ee),(q2,E,Z,q3,E)}");
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
    void testDoublesPDA(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>34.0</x><y>122.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>177.0</x><y>117.0</y></state><state id=\"2\" name=\"q2\"><x>317.0</x><y>112.0</y></state><state id=\"3\" name=\"q3\"><x>465.0</x><y>108.0</y><final/></state><!--The list of transitions.--><transition><from>2</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>1</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>0</from><to>1</to><read>e</read><pop>Z</pop><push>e Z</push></transition><transition><from>1</from><to>1</to><read>e</read><pop>e</pop><push>ee</push></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition></automaton></structure>");
        setsTest.setStudentStatesSet("{q0,q0,q1,q2,q3}");
        setsTest.setStudentInitialsSet("{q0,q0}");
        setsTest.setStudentFinalsSet("{q3,q3}");
        setsTest.setStudentAlphabetSet("{e,e,f}");
        setsTest.setStudentStackAlphabetSet("{Z,e,e}");
        setsTest.setStudentTransitionsSet("{(q2,f,e,q2,E),(q2,f,e,q2,E),(q1,f,e,q2,E),(q0,e,Z,q1,e Z),(q1,e,e,q1,ee),(q2,E,Z,q3,E)}");
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
    void testMissingPDA(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>34.0</x><y>122.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>177.0</x><y>117.0</y></state><state id=\"2\" name=\"q2\"><x>317.0</x><y>112.0</y></state><state id=\"3\" name=\"q3\"><x>465.0</x><y>108.0</y><final/></state><!--The list of transitions.--><transition><from>2</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>1</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>0</from><to>1</to><read>e</read><pop>Z</pop><push>e Z</push></transition><transition><from>1</from><to>1</to><read>e</read><pop>e</pop><push>ee</push></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition></automaton></structure>");
        setsTest.setStudentStatesSet("{q1,q2,q3}");
        setsTest.setStudentInitialsSet("{}");
        setsTest.setStudentFinalsSet("{}");
        setsTest.setStudentAlphabetSet("{f}");
        setsTest.setStudentStackAlphabetSet("{Z}");
        setsTest.setStudentTransitionsSet("{(q1,f,e,q2,E),(q0,e,Z,q1,e Z),(q1,e,e,q1,ee),(q2,E,Z,q3,E)}");
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
        transitionSetResult.addToMissing(new Transition(q0,new State("q1", false, false), "e", "Z", "eZ"));
        transitionSetResult.addToMissing(new Transition(q2,q2, "f", "e", "E"));

        Assertions.assertEquals(statesSetResult, setsTest.getStatesResult());
        Assertions.assertEquals(initialsSetResult, setsTest.getInitialsResult());
        Assertions.assertEquals(finalsSetResult, setsTest.getFinalsResult());
        Assertions.assertEquals(alphabetSetResult, setsTest.getAlphabetResult());
        Assertions.assertEquals(stackAlphabetSetResult, setsTest.getStackAlphabetResult());
        Assertions.assertEquals(transitionSetResult, setsTest.getTransitionsResult());
    }


    @Test
    void testSurplusPDA(){
        SetsTest setsTest = new SetsTest();
        setsTest.setJflapXml("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>34.0</x><y>122.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>177.0</x><y>117.0</y></state><state id=\"2\" name=\"q2\"><x>317.0</x><y>112.0</y></state><state id=\"3\" name=\"q3\"><x>465.0</x><y>108.0</y><final/></state><!--The list of transitions.--><transition><from>2</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>1</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>0</from><to>1</to><read>e</read><pop>Z</pop><push>e Z</push></transition><transition><from>1</from><to>1</to><read>e</read><pop>e</pop><push>ee</push></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition></automaton></structure>");
        setsTest.setStudentStatesSet("{q0,q1,q2,q3,q4}");
        setsTest.setStudentInitialsSet("{q0,q1}");
        setsTest.setStudentFinalsSet("{q2,q3}");
        setsTest.setStudentAlphabetSet("{e,f,g}");
        setsTest.setStudentStackAlphabetSet("{Z,e,f,h}");
        setsTest.setStudentTransitionsSet("{(q4,f,e,q4,E),(q2,f,e,q2,E),(q1,f,e,q2,E),(q0,e,Z,q1,e Z),(q1,e,e,q1,ee),(q2,E,Z,q3,E)}");
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
        setsTest.setStudentTransitionsSet("{(q2,f,e,q2,E),(q1,f,e,q2,E),(q0,e,Z,q1,e Z),(q1,e,e,q1,ee),(q2,E,Z,q3,E)}");
        PrintStream systemError = System.err;
        System.setErr(new PrintStream(new ByteArrayOutputStream()));
        Assertions.assertThrows(GraFlapException.class,() -> setsTest.gradeSets());
        System.setErr(systemError);
    }
}
