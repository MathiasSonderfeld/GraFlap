package de.HsH.inform.GraFlap.BlackBoxTests.CorrectResults;

import de.HsH.inform.GraFlap.BlackBoxTests.BlackBoxTestTestwordsUtil;
import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.answerMessage.AnswerMessage;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Mode;
import de.HsH.inform.GraFlap.entity.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class BlackBoxCorrectOtherTest{
    @Test
    void testWordsforCFG() {
        String title = "Worte fuer CFG  Sprache";
        Mode mode = Mode.WW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S->aSa|a|bSb");
        arguments.setMode(mode);
        arguments.setType(Type.NON);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setNumberOfWords(4);
        arguments.setStudentAnswer("a,aaa,bab,bbabb");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Worte", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testCYKforGrammar() {
        String title = "CYK fuer Grammatik";
        Mode mode = Mode.CYK;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S->XY|XZ,Z->SY,X->a,Y->b");
        arguments.setMode(mode);
        arguments.setType(Type.CFG);
        arguments.setWordString("aaabbb");
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getSingleWordTestwords("aaabbb"));
        arguments.setNumberOfWords(1);
        arguments.setStudentAnswer("{X->a};{X->a};{X->a};{Y->b};{Y->b};{Y->b}%{};{};{S->XY};{};{}%{};{};{Z->SY};{}%{};{S->XZ};{}%{};{Z->SY}%{S->XZ}");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("CYK-Algorithmus", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testCYKforGrammarLHS() {
        String title = "CYK fuer Grammatik - lhs";
        Mode mode = Mode.CYK;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S->XY|XZ,Z->SY,X->a,Y->b");
        arguments.setMode(mode);
        arguments.setType(Type.CFG);
        arguments.setWordString("aaabbb");
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getSingleWordTestwords("aaabbb"));
        arguments.setNumberOfWords(1);
        arguments.setStudentAnswer("{X};{X};{X};{Y};{Y};{Y}%{};{};{S};{};{}%{};{};{Z};{}%{};{S};{}%{};{Z}%{S}");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("CYK-Algorithmus", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testDerivationforCFGrammar() {
        String title = "Left side derivation for contextfree grammar and word aaabbb";
        Mode mode = Mode.DER;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("S->aSb|ab");
        arguments.setMode(mode);
        arguments.setType(Type.CFG);
        arguments.setWordString("aaabbb");
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getSingleWordTestwords("aaabbb"));
        arguments.setNumberOfWords(1);
        arguments.setStudentAnswer("S=>aSb=>aaSbb=>aaabbb");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Wortableitung", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testMealyForSmoothingBinaryImages() {
        String title = "Mealy Maschine zur Bildglättung";
        Mode mode = Mode.MMW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>mealy</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>103.0</x><y>93.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>237.0</x><y>52.0</y></state><state id=\"2\" name=\"q2\"><x>237.0</x><y>168.0</y></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>1</read><transout>1</transout></transition><transition><from>2</from><to>2</to><read>0</read><transout>0</transout></transition><transition><from>2</from><to>0</to><read>1</read><transout>0</transout></transition><transition><from>0</from><to>2</to><read>0</read><transout>0</transout></transition><transition><from>0</from><to>1</to><read>1</read><transout>1</transout></transition><transition><from>1</from><to>0</to><read>0</read><transout>1</transout></transition></automaton></structure>");
        arguments.setMode(mode);
        arguments.setType(Type.MEALY);
        arguments.setWordString("01%10%001%010%011%1010010%00101101");
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>mealy</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>103.0</x><y>93.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>237.0</x><y>52.0</y></state><state id=\"2\" name=\"q2\"><x>237.0</x><y>168.0</y></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>1</read><transout>1</transout></transition><transition><from>2</from><to>2</to><read>0</read><transout>0</transout></transition><transition><from>2</from><to>0</to><read>1</read><transout>0</transout></transition><transition><from>0</from><to>2</to><read>0</read><transout>0</transout></transition><transition><from>0</from><to>1</to><read>1</read><transout>1</transout></transition><transition><from>1</from><to>0</to><read>0</read><transout>1</transout></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Maschine", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testMooreForSmoothingBinaryImages() {
        String title = "Moore Maschine zur Bildglättung";
        Mode mode = Mode.MMW;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>moore</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>141.0</x><y>120.0</y><initial/><output/></state><state id=\"1\" name=\"q1\"><x>224.0</x><y>205.0</y><output>0</output></state><state id=\"2\" name=\"q2\"><x>22.0</x><y>202.0</y><output>0</output></state><state id=\"3\" name=\"q3\"><x>27.0</x><y>33.0</y><output>1</output></state><state id=\"4\" name=\"q4\"><x>284.0</x><y>43.0</y><output>1</output></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>3</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>2</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>0</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>3</from><to>4</to><read>0</read><transout>1</transout></transition><transition><from>4</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>4</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>2</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>0</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>1</from><to>2</to><read>1</read><transout>0</transout></transition></automaton></structure>");
        arguments.setMode(mode);
        arguments.setType(Type.MOORE);
        arguments.setWordString("01%10%001%010%011%1010010%00101101");
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>moore</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>141.0</x><y>120.0</y><initial/><output/></state><state id=\"1\" name=\"q1\"><x>224.0</x><y>205.0</y><output>0</output></state><state id=\"2\" name=\"q2\"><x>22.0</x><y>202.0</y><output>0</output></state><state id=\"3\" name=\"q3\"><x>27.0</x><y>33.0</y><output>1</output></state><state id=\"4\" name=\"q4\"><x>284.0</x><y>43.0</y><output>1</output></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>3</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>2</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>0</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>3</from><to>4</to><read>0</read><transout>1</transout></transition><transition><from>4</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>4</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>2</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>0</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>1</from><to>2</to><read>1</read><transout>0</transout></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Maschine", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testTuringForBinaryToUnaryNumbers() {
        String title = "Turing Automat zur Konvertierung binärer zu unären Nummern";
        Mode mode = Mode.MP;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("-");
        arguments.setMode(mode);
        arguments.setType(Type.NTM);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.getPairsTestWords("0001101;|||||||||||||%10;||%10001;|||||||||||||||||%10101;|||||||||||||||||||||%11001;|||||||||||||||||||||||||%11;|||%1101;|||||||||||||%10111;|||||||||||||||||||||||%10010;||||||||||||||||||%1010;||||||||||%11000;||||||||||||||||||||||||%100;||||%10110;||||||||||||||||||||||%1000;||||||||%10000;||||||||||||||||%1100;||||||||||||%111;|||||||%1111;|||||||||||||||%110;||||||%1;|%1001;|||||||||%1110;||||||||||||||%10100;||||||||||||||||||||%0;%1011;|||||||||||"));
        arguments.setWordString("0001101;|||||||||||||%10;||%10001;|||||||||||||||||%10101;|||||||||||||||||||||%11001;|||||||||||||||||||||||||%11;|||%1101;|||||||||||||%10111;|||||||||||||||||||||||%10010;||||||||||||||||||%1010;||||||||||%11000;||||||||||||||||||||||||%100;||||%10110;||||||||||||||||||||||%1000;||||||||%10000;||||||||||||||||%1100;||||||||||||%111;|||||||%1111;|||||||||||||||%110;||||||%1;|%1001;|||||||||%1110;||||||||||||||%10100;||||||||||||||||||||%0;%1011;|||||||||||");
        arguments.setNumberOfWords(25);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><structure type=\"editor_panel\"><structure type=\"transition_graph\"><structure mode=\"Default mode\" type=\"turing\"><tapes>1</tapes><structure type=\"blank_symbol\"><value>□</value></structure><structure type=\"start_state\"><state><name>st</name><id>0</id></state></structure><structure type=\"transition_set\"><transition><tapes>1</tapes><write0>□</write0><from><name>st</name><id>0</id></from><to><name>st</name><id>0</id></to><move0>R</move0><read0>0</read0></transition><transition><tapes>1</tapes><write0>1</write0><from><name>st</name><id>0</id></from><to><name>sr</name><id>1</id></to><move0>S</move0><read0>1</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>st</name><id>0</id></from><to><name>sf</name><id>18</id></to><move0>S</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>sr</name><id>1</id></from><to><name>s01</name><id>2</id></to><move0>R</move0><read0>0</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>sr</name><id>1</id></from><to><name>s11</name><id>3</id></to><move0>R</move0><read0>1</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>sr</name><id>1</id></from><to><name>sf</name><id>18</id></to><move0>R</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>0</write0><from><name>s01</name><id>2</id></from><to><name>s01</name><id>2</id></to><move0>R</move0><read0>0</read0></transition><transition><tapes>1</tapes><write0>1</write0><from><name>s01</name><id>2</id></from><to><name>s01</name><id>2</id></to><move0>R</move0><read0>1</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>s01</name><id>2</id></from><to><name>s02</name><id>4</id></to><move0>R</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>0</write0><from><name>s11</name><id>3</id></from><to><name>s11</name><id>3</id></to><move0>R</move0><read0>0</read0></transition><transition><tapes>1</tapes><write0>1</write0><from><name>s11</name><id>3</id></from><to><name>s11</name><id>3</id></to><move0>R</move0><read0>1</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>s11</name><id>3</id></from><to><name>s12</name><id>5</id></to><move0>R</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s02</name><id>4</id></from><to><name>s03</name><id>6</id></to><move0>R</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s12</name><id>5</id></from><to><name>s13</name><id>7</id></to><move0>R</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s12</name><id>5</id></from><to><name>sc</name><id>16</id></to><move0>L</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s03</name><id>6</id></from><to><name>s03</name><id>6</id></to><move0>R</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s03</name><id>6</id></from><to><name>s03</name><id>6</id></to><move0>R</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s03</name><id>6</id></from><to><name>s04</name><id>10</id></to><move0>L</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s13</name><id>7</id></from><to><name>s13</name><id>7</id></to><move0>R</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s13</name><id>7</id></from><to><name>s13</name><id>7</id></to><move0>R</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s13</name><id>7</id></from><to><name>s14</name><id>11</id></to><move0>L</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s04</name><id>10</id></from><to><name>s04</name><id>10</id></to><move0>L</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s04</name><id>10</id></from><to><name>s05</name><id>12</id></to><move0>L</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>s04</name><id>10</id></from><to><name>s06</name><id>14</id></to><move0>R</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s14</name><id>11</id></from><to><name>s14</name><id>11</id></to><move0>L</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s14</name><id>11</id></from><to><name>s15</name><id>13</id></to><move0>L</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>s14</name><id>11</id></from><to><name>s16</name><id>15</id></to><move0>R</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s05</name><id>12</id></from><to><name>s02</name><id>4</id></to><move0>R</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s05</name><id>12</id></from><to><name>s05</name><id>12</id></to><move0>L</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s15</name><id>13</id></from><to><name>s12</name><id>5</id></to><move0>R</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s15</name><id>13</id></from><to><name>s15</name><id>13</id></to><move0>L</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s06</name><id>14</id></from><to><name>s06</name><id>14</id></to><move0>R</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>s06</name><id>14</id></from><to><name>sc</name><id>16</id></to><move0>L</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s16</name><id>15</id></from><to><name>s16</name><id>15</id></to><move0>R</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s16</name><id>15</id></from><to><name>sc</name><id>16</id></to><move0>L</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>sc</name><id>16</id></from><to><name>sc</name><id>16</id></to><move0>L</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>sc</name><id>16</id></from><to><name>sb</name><id>17</id></to><move0>L</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>sb</name><id>17</id></from><to><name>sr</name><id>1</id></to><move0>R</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>0</write0><from><name>sb</name><id>17</id></from><to><name>sb</name><id>17</id></to><move0>L</move0><read0>0</read0></transition><transition><tapes>1</tapes><write0>1</write0><from><name>sb</name><id>17</id></from><to><name>sb</name><id>17</id></to><move0>L</move0><read0>1</read0></transition></structure><structure type=\"state_set\"><state><name>st</name><id>0</id></state><state><name>sr</name><id>1</id></state><state><name>s01</name><id>2</id></state><state><name>s11</name><id>3</id></state><state><name>s02</name><id>4</id></state><state><name>s12</name><id>5</id></state><state><name>s03</name><id>6</id></state><state><name>s13</name><id>7</id></state><state><name>s04</name><id>10</id></state><state><name>s14</name><id>11</id></state><state><name>s05</name><id>12</id></state><state><name>s15</name><id>13</id></state><state><name>s06</name><id>14</id></state><state><name>s16</name><id>15</id></state><state><name>sc</name><id>16</id></state><state><name>sb</name><id>17</id></state><state><name>sf</name><id>18</id></state></structure><structure type=\"tape_alph\"><symbol>+</symbol><symbol>0</symbol><symbol>1</symbol><symbol>|</symbol><symbol>□</symbol></structure><structure type=\"final_states\"><state><name>sf</name><id>18</id></state></structure><structure type=\"input_alph\"><symbol>+</symbol><symbol>0</symbol><symbol>1</symbol><symbol>|</symbol></structure></structure><state_point_map><state_point><state>0</state><point><x>198.0</x><y>166.0</y></point></state_point><state_point><state>1</state><point><x>323.0</x><y>265.0</y></point></state_point><state_point><state>2</state><point><x>519.0</x><y>287.0</y></point></state_point><state_point><state>3</state><point><x>450.0</x><y>199.0</y></point></state_point><state_point><state>4</state><point><x>648.0</x><y>289.0</y></point></state_point><state_point><state>5</state><point><x>589.0</x><y>201.0</y></point></state_point><state_point><state>6</state><point><x>839.0</x><y>388.0</y></point></state_point><state_point><state>7</state><point><x>721.0</x><y>202.0</y></point></state_point><state_point><state>10</state><point><x>1059.0</x><y>343.0</y></point></state_point><state_point><state>11</state><point><x>846.0</x><y>201.0</y></point></state_point><state_point><state>12</state><point><x>916.0</x><y>293.0</y></point></state_point><state_point><state>13</state><point><x>993.0</x><y>257.0</y></point></state_point><state_point><state>14</state><point><x>1218.0</x><y>334.0</y></point></state_point><state_point><state>15</state><point><x>1092.0</x><y>246.0</y></point></state_point><state_point><state>16</state><point><x>1379.0</x><y>308.0</y></point></state_point><state_point><state>17</state><point><x>931.0</x><y>454.0</y></point></state_point><state_point><state>18</state><point><x>188.0</x><y>347.0</y></point></state_point></state_point_map><control_point_map><control_point><from>0</from><to>0</to><point><x>192.0</x><y>77.24882718188422</y></point></control_point><control_point><from>2</from><to>2</to><point><x>519.0</x><y>217.0</y></point></control_point><control_point><from>5</from><to>16</to><point><x>697.7593376803671</x><y>-24.430601265615735</y></point></control_point><control_point><from>6</from><to>10</to><point><x>949.0</x><y>365.5</y></point></control_point><control_point><from>7</from><to>11</to><point><x>783.5</x><y>201.5</y></point></control_point><control_point><from>13</from><to>5</to><point><x>789.1383155561728</x><y>263.0933197186326</y></point></control_point><control_point><from>1</from><to>3</to><point><x>386.5</x><y>232.0</y></point></control_point><control_point><from>0</from><to>18</to><point><x>156.0</x><y>251.00000000000003</y></point></control_point><control_point><from>0</from><to>1</to><point><x>260.5</x><y>215.5</y></point></control_point><control_point><from>12</from><to>4</to><point><x>815.975769323787</x><y>288.66129076601527</y></point></control_point><control_point><from>17</from><to>17</to><point><x>931.0</x><y>384.0</y></point></control_point><control_point><from>16</from><to>17</to><point><x>1155.0</x><y>381.0</y></point></control_point><control_point><from>16</from><to>16</to><point><x>1379.0</x><y>238.0</y></point></control_point><control_point><from>17</from><to>1</to><point><x>627.0</x><y>359.5</y></point></control_point><control_point><from>3</from><to>5</to><point><x>519.5</x><y>200.0</y></point></control_point><control_point><from>1</from><to>2</to><point><x>421.0</x><y>276.0</y></point></control_point><control_point><from>2</from><to>4</to><point><x>583.5</x><y>288.0</y></point></control_point><control_point><from>3</from><to>3</to><point><x>446.0</x><y>119.0</y></point></control_point><control_point><from>5</from><to>7</to><point><x>655.0</x><y>201.5</y></point></control_point><control_point><from>6</from><to>6</to><point><x>839.0</x><y>318.0</y></point></control_point><control_point><from>10</from><to>14</to><point><x>1120.4974026182788</x><y>344.24596213380835</y></point></control_point><control_point><from>11</from><to>13</to><point><x>919.5</x><y>229.0</y></point></control_point><control_point><from>12</from><to>12</to><point><x>916.0</x><y>223.0</y></point></control_point><control_point><from>11</from><to>15</to><point><x>1024.0</x><y>134.0</y></point></control_point><control_point><from>4</from><to>6</to><point><x>743.5</x><y>338.5</y></point></control_point><control_point><from>7</from><to>7</to><point><x>721.0</x><y>132.0</y></point></control_point><control_point><from>10</from><to>10</to><point><x>1059.0</x><y>273.0</y></point></control_point><control_point><from>14</from><to>14</to><point><x>1218.0</x><y>264.0</y></point></control_point><control_point><from>10</from><to>12</to><point><x>987.5</x><y>318.0</y></point></control_point><control_point><from>11</from><to>11</to><point><x>846.0</x><y>131.0</y></point></control_point><control_point><from>13</from><to>13</to><point><x>993.0</x><y>187.0</y></point></control_point><control_point><from>14</from><to>16</to><point><x>1298.5</x><y>321.0</y></point></control_point><control_point><from>15</from><to>15</to><point><x>1092.0</x><y>176.0</y></point></control_point><control_point><from>1</from><to>18</to><point><x>255.5</x><y>306.0</y></point></control_point><control_point><from>15</from><to>16</to><point><x>1235.5</x><y>277.0</y></point></control_point></control_point_map></structure><state_label_map/><note_map/></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Maschine", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }


    @Test
    void testSVGATuringForBinaryToUnaryNumbers() {
        String title = "Turing Automat zur Konvertierung binärer zu unären Nummern";
        Mode mode = Mode.SVGA;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("-");
        arguments.setMode(mode);
        arguments.setType(Type.NTM);
        arguments.setWordString("0001101;|||||||||||||%10;||%10001;|||||||||||||||||%10101;|||||||||||||||||||||%11001;|||||||||||||||||||||||||%11;|||%1101;|||||||||||||%10111;|||||||||||||||||||||||%10010;||||||||||||||||||%1010;||||||||||%11000;||||||||||||||||||||||||%100;||||%10110;||||||||||||||||||||||%1000;||||||||%10000;||||||||||||||||%1100;||||||||||||%111;|||||||%1111;|||||||||||||||%110;||||||%1;|%1001;|||||||||%1110;||||||||||||||%10100;||||||||||||||||||||%0;%1011;|||||||||||");
        arguments.setNumberOfWords(25);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><structure type=\"editor_panel\"><structure type=\"transition_graph\"><structure mode=\"Default mode\" type=\"turing\"><tapes>1</tapes><structure type=\"blank_symbol\"><value>□</value></structure><structure type=\"start_state\"><state><name>st</name><id>0</id></state></structure><structure type=\"transition_set\"><transition><tapes>1</tapes><write0>□</write0><from><name>st</name><id>0</id></from><to><name>st</name><id>0</id></to><move0>R</move0><read0>0</read0></transition><transition><tapes>1</tapes><write0>1</write0><from><name>st</name><id>0</id></from><to><name>sr</name><id>1</id></to><move0>S</move0><read0>1</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>st</name><id>0</id></from><to><name>sf</name><id>18</id></to><move0>S</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>sr</name><id>1</id></from><to><name>s01</name><id>2</id></to><move0>R</move0><read0>0</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>sr</name><id>1</id></from><to><name>s11</name><id>3</id></to><move0>R</move0><read0>1</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>sr</name><id>1</id></from><to><name>sf</name><id>18</id></to><move0>R</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>0</write0><from><name>s01</name><id>2</id></from><to><name>s01</name><id>2</id></to><move0>R</move0><read0>0</read0></transition><transition><tapes>1</tapes><write0>1</write0><from><name>s01</name><id>2</id></from><to><name>s01</name><id>2</id></to><move0>R</move0><read0>1</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>s01</name><id>2</id></from><to><name>s02</name><id>4</id></to><move0>R</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>0</write0><from><name>s11</name><id>3</id></from><to><name>s11</name><id>3</id></to><move0>R</move0><read0>0</read0></transition><transition><tapes>1</tapes><write0>1</write0><from><name>s11</name><id>3</id></from><to><name>s11</name><id>3</id></to><move0>R</move0><read0>1</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>s11</name><id>3</id></from><to><name>s12</name><id>5</id></to><move0>R</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s02</name><id>4</id></from><to><name>s03</name><id>6</id></to><move0>R</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s12</name><id>5</id></from><to><name>s13</name><id>7</id></to><move0>R</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s12</name><id>5</id></from><to><name>sc</name><id>16</id></to><move0>L</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s03</name><id>6</id></from><to><name>s03</name><id>6</id></to><move0>R</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s03</name><id>6</id></from><to><name>s03</name><id>6</id></to><move0>R</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s03</name><id>6</id></from><to><name>s04</name><id>10</id></to><move0>L</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s13</name><id>7</id></from><to><name>s13</name><id>7</id></to><move0>R</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s13</name><id>7</id></from><to><name>s13</name><id>7</id></to><move0>R</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s13</name><id>7</id></from><to><name>s14</name><id>11</id></to><move0>L</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s04</name><id>10</id></from><to><name>s04</name><id>10</id></to><move0>L</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s04</name><id>10</id></from><to><name>s05</name><id>12</id></to><move0>L</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>s04</name><id>10</id></from><to><name>s06</name><id>14</id></to><move0>R</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s14</name><id>11</id></from><to><name>s14</name><id>11</id></to><move0>L</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s14</name><id>11</id></from><to><name>s15</name><id>13</id></to><move0>L</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>s14</name><id>11</id></from><to><name>s16</name><id>15</id></to><move0>R</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s05</name><id>12</id></from><to><name>s02</name><id>4</id></to><move0>R</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s05</name><id>12</id></from><to><name>s05</name><id>12</id></to><move0>L</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>+</write0><from><name>s15</name><id>13</id></from><to><name>s12</name><id>5</id></to><move0>R</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s15</name><id>13</id></from><to><name>s15</name><id>13</id></to><move0>L</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s06</name><id>14</id></from><to><name>s06</name><id>14</id></to><move0>R</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>s06</name><id>14</id></from><to><name>sc</name><id>16</id></to><move0>L</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s16</name><id>15</id></from><to><name>s16</name><id>15</id></to><move0>R</move0><read0>+</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>s16</name><id>15</id></from><to><name>sc</name><id>16</id></to><move0>L</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>|</write0><from><name>sc</name><id>16</id></from><to><name>sc</name><id>16</id></to><move0>L</move0><read0>|</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>sc</name><id>16</id></from><to><name>sb</name><id>17</id></to><move0>L</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>□</write0><from><name>sb</name><id>17</id></from><to><name>sr</name><id>1</id></to><move0>R</move0><read0>□</read0></transition><transition><tapes>1</tapes><write0>0</write0><from><name>sb</name><id>17</id></from><to><name>sb</name><id>17</id></to><move0>L</move0><read0>0</read0></transition><transition><tapes>1</tapes><write0>1</write0><from><name>sb</name><id>17</id></from><to><name>sb</name><id>17</id></to><move0>L</move0><read0>1</read0></transition></structure><structure type=\"state_set\"><state><name>st</name><id>0</id></state><state><name>sr</name><id>1</id></state><state><name>s01</name><id>2</id></state><state><name>s11</name><id>3</id></state><state><name>s02</name><id>4</id></state><state><name>s12</name><id>5</id></state><state><name>s03</name><id>6</id></state><state><name>s13</name><id>7</id></state><state><name>s04</name><id>10</id></state><state><name>s14</name><id>11</id></state><state><name>s05</name><id>12</id></state><state><name>s15</name><id>13</id></state><state><name>s06</name><id>14</id></state><state><name>s16</name><id>15</id></state><state><name>sc</name><id>16</id></state><state><name>sb</name><id>17</id></state><state><name>sf</name><id>18</id></state></structure><structure type=\"tape_alph\"><symbol>+</symbol><symbol>0</symbol><symbol>1</symbol><symbol>|</symbol><symbol>□</symbol></structure><structure type=\"final_states\"><state><name>sf</name><id>18</id></state></structure><structure type=\"input_alph\"><symbol>+</symbol><symbol>0</symbol><symbol>1</symbol><symbol>|</symbol></structure></structure><state_point_map><state_point><state>0</state><point><x>198.0</x><y>166.0</y></point></state_point><state_point><state>1</state><point><x>323.0</x><y>265.0</y></point></state_point><state_point><state>2</state><point><x>519.0</x><y>287.0</y></point></state_point><state_point><state>3</state><point><x>450.0</x><y>199.0</y></point></state_point><state_point><state>4</state><point><x>648.0</x><y>289.0</y></point></state_point><state_point><state>5</state><point><x>589.0</x><y>201.0</y></point></state_point><state_point><state>6</state><point><x>839.0</x><y>388.0</y></point></state_point><state_point><state>7</state><point><x>721.0</x><y>202.0</y></point></state_point><state_point><state>10</state><point><x>1059.0</x><y>343.0</y></point></state_point><state_point><state>11</state><point><x>846.0</x><y>201.0</y></point></state_point><state_point><state>12</state><point><x>916.0</x><y>293.0</y></point></state_point><state_point><state>13</state><point><x>993.0</x><y>257.0</y></point></state_point><state_point><state>14</state><point><x>1218.0</x><y>334.0</y></point></state_point><state_point><state>15</state><point><x>1092.0</x><y>246.0</y></point></state_point><state_point><state>16</state><point><x>1379.0</x><y>308.0</y></point></state_point><state_point><state>17</state><point><x>931.0</x><y>454.0</y></point></state_point><state_point><state>18</state><point><x>188.0</x><y>347.0</y></point></state_point></state_point_map><control_point_map><control_point><from>0</from><to>0</to><point><x>192.0</x><y>77.24882718188422</y></point></control_point><control_point><from>2</from><to>2</to><point><x>519.0</x><y>217.0</y></point></control_point><control_point><from>5</from><to>16</to><point><x>697.7593376803671</x><y>-24.430601265615735</y></point></control_point><control_point><from>6</from><to>10</to><point><x>949.0</x><y>365.5</y></point></control_point><control_point><from>7</from><to>11</to><point><x>783.5</x><y>201.5</y></point></control_point><control_point><from>13</from><to>5</to><point><x>789.1383155561728</x><y>263.0933197186326</y></point></control_point><control_point><from>1</from><to>3</to><point><x>386.5</x><y>232.0</y></point></control_point><control_point><from>0</from><to>18</to><point><x>156.0</x><y>251.00000000000003</y></point></control_point><control_point><from>0</from><to>1</to><point><x>260.5</x><y>215.5</y></point></control_point><control_point><from>12</from><to>4</to><point><x>815.975769323787</x><y>288.66129076601527</y></point></control_point><control_point><from>17</from><to>17</to><point><x>931.0</x><y>384.0</y></point></control_point><control_point><from>16</from><to>17</to><point><x>1155.0</x><y>381.0</y></point></control_point><control_point><from>16</from><to>16</to><point><x>1379.0</x><y>238.0</y></point></control_point><control_point><from>17</from><to>1</to><point><x>627.0</x><y>359.5</y></point></control_point><control_point><from>3</from><to>5</to><point><x>519.5</x><y>200.0</y></point></control_point><control_point><from>1</from><to>2</to><point><x>421.0</x><y>276.0</y></point></control_point><control_point><from>2</from><to>4</to><point><x>583.5</x><y>288.0</y></point></control_point><control_point><from>3</from><to>3</to><point><x>446.0</x><y>119.0</y></point></control_point><control_point><from>5</from><to>7</to><point><x>655.0</x><y>201.5</y></point></control_point><control_point><from>6</from><to>6</to><point><x>839.0</x><y>318.0</y></point></control_point><control_point><from>10</from><to>14</to><point><x>1120.4974026182788</x><y>344.24596213380835</y></point></control_point><control_point><from>11</from><to>13</to><point><x>919.5</x><y>229.0</y></point></control_point><control_point><from>12</from><to>12</to><point><x>916.0</x><y>223.0</y></point></control_point><control_point><from>11</from><to>15</to><point><x>1024.0</x><y>134.0</y></point></control_point><control_point><from>4</from><to>6</to><point><x>743.5</x><y>338.5</y></point></control_point><control_point><from>7</from><to>7</to><point><x>721.0</x><y>132.0</y></point></control_point><control_point><from>10</from><to>10</to><point><x>1059.0</x><y>273.0</y></point></control_point><control_point><from>14</from><to>14</to><point><x>1218.0</x><y>264.0</y></point></control_point><control_point><from>10</from><to>12</to><point><x>987.5</x><y>318.0</y></point></control_point><control_point><from>11</from><to>11</to><point><x>846.0</x><y>131.0</y></point></control_point><control_point><from>13</from><to>13</to><point><x>993.0</x><y>187.0</y></point></control_point><control_point><from>14</from><to>16</to><point><x>1298.5</x><y>321.0</y></point></control_point><control_point><from>15</from><to>15</to><point><x>1092.0</x><y>176.0</y></point></control_point><control_point><from>1</from><to>18</to><point><x>255.5</x><y>306.0</y></point></control_point><control_point><from>15</from><to>16</to><point><x>1235.5</x><y>277.0</y></point></control_point></control_point_map></structure><state_label_map/><note_map/></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Svg-Modus", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testSVGAMooreForSmoothingBinaryImages() {
        String title = "Moore Maschine zur Bildglättung";
        Mode mode = Mode.SVGA;

        Arguments arguments = new Arguments();
        arguments.setTaskTitle(title);
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setSolution("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>moore</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>141.0</x><y>120.0</y><initial/><output/></state><state id=\"1\" name=\"q1\"><x>224.0</x><y>205.0</y><output>0</output></state><state id=\"2\" name=\"q2\"><x>22.0</x><y>202.0</y><output>0</output></state><state id=\"3\" name=\"q3\"><x>27.0</x><y>33.0</y><output>1</output></state><state id=\"4\" name=\"q4\"><x>284.0</x><y>43.0</y><output>1</output></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>3</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>2</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>0</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>3</from><to>4</to><read>0</read><transout>1</transout></transition><transition><from>4</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>4</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>2</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>0</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>1</from><to>2</to><read>1</read><transout>0</transout></transition></automaton></structure>");
        arguments.setMode(mode);
        arguments.setType(Type.MOORE);
        arguments.setWordString("01%10%001%010%011%1010010%00101101");
        arguments.setNumberOfWords(50);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>moore</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>141.0</x><y>120.0</y><initial/><output/></state><state id=\"1\" name=\"q1\"><x>224.0</x><y>205.0</y><output>0</output></state><state id=\"2\" name=\"q2\"><x>22.0</x><y>202.0</y><output>0</output></state><state id=\"3\" name=\"q3\"><x>27.0</x><y>33.0</y><output>1</output></state><state id=\"4\" name=\"q4\"><x>284.0</x><y>43.0</y><output>1</output></state><!--The list of transitions.--><transition><from>1</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>3</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>2</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>0</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>3</from><to>4</to><read>0</read><transout>1</transout></transition><transition><from>4</from><to>3</to><read>1</read><transout>1</transout></transition><transition><from>4</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>2</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>0</from><to>1</to><read>0</read><transout>0</transout></transition><transition><from>1</from><to>2</to><read>1</read><transout>0</transout></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals(title, answerMessage.getTaskTitle());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals(mode, answerMessage.getMode());
        Assertions.assertEquals("Richtige Antwort, gut gemacht!", answerMessage.getFeedback());
        Assertions.assertEquals("Svg-Modus", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

}
