package de.HsH.inform.GraFlap;

import de.HsH.inform.GraFlap.BlackBoxTestData;
import de.HsH.inform.GraFlap.GraFlap;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Mathias Sonderfeld
 * This is a start, but not every program part is covered.
 * Its better than nothing but does not verify total integrity of the code.
 * Current Method:
 * Repipe JVM wide System.out to an OutputStream Object.
 * Call GraFlap.main with String-Array as arguments.
 * Catch output from GraFlap collected by Outputstream and match against expected Result.
 * Reset Outputstream for next test.
 * After all tests reset System.out to default.
 *
 *
 * Test-Method could be changed if thats acceptable with common practices to:
 *      - move System.out.println out of GraFlap.generateResult(). main would be easiest.
 *      - test input parsers and Output Builders indipendently
 *      - write a OutputBuilder for Tests that outputs an easier parsable format, csv or json maybe?
 *      - test GraFLap.generateResult() with easier controllable Arguments Object and TestOutputBuilder
 */

public class GraFlapTest {
    private static PrintStream systemOut;
    private static final ByteArrayOutputStream capture = new ByteArrayOutputStream();
    private static final PrintStream captureStream = new PrintStream(capture);

    /**
     * repipe JVM wide System.out and backup correct System.out
     */
    @BeforeAll
    static void init(){
        systemOut = System.out;
        System.setOut(captureStream);
    }

    /**
     *  call GraFlap with given arguments, capture output and return it, if no Exception is thrown by GraFlap.
     * @param test the arguments to pass to GraFlap
     * @return the output of GraFlap
     */
    static String runGraFlapBlackBoxTest( BlackBoxTestData test){
        try {
            GraFlap.main(new String[]{test.getInput(), test.getStudentAnswer()});
            String ret = capture.toString().replaceAll("[\\r|\\n]\\s+", "").trim();
            capture.reset();
            return ret;
        }
        catch(Exception e) {
            e.printStackTrace(systemOut);
            return "";
        }
    }

    /**
     * Error in Input-Parameters.
     */
    @Disabled
    @Test
    void testAutomatonForGivenLanguage() {
        BlackBoxTestData blackBoxTest = new BlackBoxTestData();

        blackBoxTest.setInput("Automat fuer gegebene Sprache#de#S -> A, A -> g B|h C, B -> g A|h D,C -> g D|h F, D -> g C|h G, F -> g G|h H, G -> g F|h J, H -> g J| h H | E, J -> g H|h J#agtw#fa#56#hhh%hhhh%gghhh%ghghh%ghhgh%ghhhg%hgghh%hghgh%hghhg%hhggh%hhghg%hhhgg%hhhhh%gghhhh%ghghhh%ghhghh%ghhhgh%ghhhhg%hgghhh%hghghh%hghhgh%hghhhg!%g%h%gg%hh%gh%ggh%hgg%ghh%hgh%ggg%hggg%hggh%gggg%gggh%ghgh%hghg%hghh%hhgh%hhgg%ghhg%hhhg%ghhhh%ggghg%hhghh%gghgg%ghggh%gghhg%hgghg%hgggg%ghggg%gghgh%hgggh%ghghg");
        blackBoxTest.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>fa</type><automaton><!--The list of states.--><state id=\"0\" name=\"s0\"><x>57.0</x><y>61.0</y><initial/></state><state id=\"1\" name=\"s1\"><x>200.0</x><y>65.0</y></state><state id=\"2\" name=\"s2\"><x>414.0</x><y>58.0</y></state><state id=\"3\" name=\"s4\"><x>53.0</x><y>188.0</y></state><state id=\"4\" name=\"s5\"><x>192.0</x><y>189.0</y></state><state id=\"5\" name=\"s6\"><x>404.0</x><y>188.0</y></state><state id=\"6\" name=\"s7\"><x>598.0</x><y>191.0</y></state><state id=\"7\" name=\"s3\"><x>593.0</x><y>57.0</y><final/></state><!--The list of transitions.--><transition><from>0</from><to>3</to><read>g </read></transition><transition><from>3</from><to>0</to><read>g </read></transition><transition><from>7</from><to>7</to><read>h </read></transition><transition><from>6</from><to>6</to><read>h </read></transition><transition><from>0</from><to>1</to><read>h </read></transition><transition><from>5</from><to>6</to><read>h </read></transition><transition><from>3</from><to>4</to><read>h </read></transition><transition><from>4</from><to>1</to><read>g </read></transition><transition><from>1</from><to>4</to><read>g </read></transition><transition><from>6</from><to>7</to><read>g </read></transition><transition><from>7</from><to>6</to><read>g </read></transition><transition><from>4</from><to>5</to><read>h </read></transition><transition><from>2</from><to>7</to><read>h </read></transition><transition><from>5</from><to>2</to><read>g </read></transition><transition><from>2</from><to>5</to><read>g </read></transition><transition><from>1</from><to>2</to><read>h </read></transition></automaton></structure>");
        String expected = "";

        Assertions.assertEquals(expected, runGraFlapBlackBoxTest(blackBoxTest));
    }

    /**
     * Error in Input-Parameters.
     */
    @Test
    void testAutomatonForRegex() {
        BlackBoxTestData blackBoxTest = new BlackBoxTestData();

        blackBoxTest.setInput("Automat fuer e^n f^n#de#S->eSf|ef#agtw#pda#64#ef%eeff%eeefff%eeeeffff%eeeeefffff%eeeeeeffffff%eeeeeeefffffff%eeeeeeeeffffffff%eeeeeeeeefffffffff%eeeeeeeeeeffffffffff%eeeeeeeeeeefffffffffff%eeeeeeeeeeeeffffffffffff%eeeeeeeeeeeeefffffffffffff%eeeeeeeeeeeeeeffffffffffffff%eeeeeeeeeeeeeeefffffffffffffff%eeeeeeeeeeeeeeeeffffffffffffffff%eeeeeeeeeeeeeeeeefffffffffffffffff%eeeeeeeeeeeeeeeeeeffffffffffffffffff%eeeeeeeeeeeeeeeeeeefffffffffffffffffff%eeeeeeeeeeeeeeeeeeeeffffffffffffffffffff%eeeeeeeeeeeeeeeeeeeeefffffffffffffffffffff%eeeeeeeeeeeeeeeeeeeeeeffffffffffffffffffffff!%f%e%ee%efe%eff%eee%fef%fee%ffe%ffee%eeef%fefe%ffef%efee%feef%feff%efef%efffe%efeee%eefee%ffeee%eefff%efefe%effef%fefff%efeff%feffe%ffffe%fefee%eefef%ffeef%eeeff%eeefe%eeeee%feeff%feeffe%eefeff%fefefe%fefeef%effeef%efefef");
        blackBoxTest.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>34.0</x><y>122.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>177.0</x><y>117.0</y></state><state id=\"2\" name=\"q2\"><x>317.0</x><y>112.0</y></state><state id=\"3\" name=\"q3\"><x>465.0</x><y>108.0</y><final/></state><!--The list of transitions.--><transition><from>2</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>1</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>0</from><to>1</to><read>e</read><pop>Z</pop><push>e Z</push></transition><transition><from>1</from><to>1</to><read>e</read><pop>e</pop><push>ee</push></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition></automaton></structure>");
        String expected = "<loncapagrade><awarddetail>EXACT_ANS</awarddetail><message><taskresult grade=\"passed\"><tasktitle>Automat fuer e^n f^n</tasktitle><titlesvg>Automat</titlesvg><imagesvg><![CDATA[<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"824pt\" height=\"100pt\" viewBox=\"0.00 0.00 824.00 99.87\"><g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 95.87)\"><title>automaton</title><polygon fill=\"white\" stroke=\"transparent\" points=\"-4,4 -4,-95.87 820,-95.87 820,4 -4,4\" /><!-- start --><g id=\"node1\" class=\"node\"><title>start</title></g><!-- 0 --><g id=\"node2\" class=\"node\"><title>0</title><ellipse fill=\"none\" stroke=\"black\" cx=\"170.96\" cy=\"-20.32\" rx=\"20.3\" ry=\"20.3\" /><text text-anchor=\"middle\" x=\"170.96\" y=\"-16.62\" font-family=\"Times New Roman,serif\" font-size=\"14.00\">q0</text></g><!-- start&#45;&gt;0 --><g id=\"edge1\" class=\"edge\"><title>start-&gt;0</title><path fill=\"none\" stroke=\"black\" d=\"M54.05,-20.32C78.57,-20.32 114.67,-20.32 140.25,-20.32\" /><polygon fill=\"black\" stroke=\"black\" points=\"140.51,-23.82 150.51,-20.32 140.51,-16.82 140.51,-23.82\" /></g><!-- 1 --><g id=\"node3\" class=\"node\"><title>1</title><ellipse fill=\"none\" stroke=\"black\" cx=\"376.88\" cy=\"-27.52\" rx=\"20.3\" ry=\"20.3\" /><text text-anchor=\"middle\" x=\"376.88\" y=\"-23.82\" font-family=\"Times New Roman,serif\" font-size=\"14.00\">q1</text></g><!-- 0&#45;&gt;1 --><g id=\"edge4\" class=\"edge\"><title>0-&gt;1</title><path fill=\"none\" stroke=\"black\" d=\"M191.46,-21.04C227.84,-22.31 303.89,-24.97 346.42,-26.45\" /><polygon fill=\"black\" stroke=\"black\" points=\"346.35,-29.95 356.47,-26.81 346.59,-22.96 346.35,-29.95\" /><text text-anchor=\"middle\" x=\"241.44\" y=\"-27.55\" font-family=\"Times New Roman,serif\" font-size=\"14.00\">e , Z ; e Z</text></g><!-- 1&#45;&gt;1 --><g id=\"edge5\" class=\"edge\"><title>1:ne-&gt;1:nw</title><path fill=\"none\" stroke=\"black\" d=\"M390.88,-41.52C401.88,-55 401.88,-69.67 376.88,-69.67 357.15,-69.67 352.99,-60.54 357.52,-50.06\" /><polygon fill=\"black\" stroke=\"black\" points=\"360.53,-51.85 362.88,-41.52 354.6,-48.13 360.53,-51.85\" /><text text-anchor=\"middle\" x=\"376.88\" y=\"-73.47\" font-family=\"Times New Roman,serif\" font-size=\"14.00\">e , e ; ee</text></g><!-- 2 --><g id=\"node4\" class=\"node\"><title>2</title><ellipse fill=\"none\" stroke=\"black\" cx=\"578.48\" cy=\"-34.72\" rx=\"20.3\" ry=\"20.3\" /><text text-anchor=\"middle\" x=\"578.48\" y=\"-31.02\" font-family=\"Times New Roman,serif\" font-size=\"14.00\">q2</text></g><!-- 1&#45;&gt;2 --><g id=\"edge3\" class=\"edge\"><title>1-&gt;2</title><path fill=\"none\" stroke=\"black\" d=\"M397.32,-28.25C432.96,-29.52 506.62,-32.15 548.2,-33.64\" /><polygon fill=\"black\" stroke=\"black\" points=\"548.17,-37.14 558.29,-34 548.42,-30.14 548.17,-37.14\" /><text text-anchor=\"middle\" x=\"451.76\" y=\"-34.74\" font-family=\"Times New Roman,serif\" font-size=\"14.00\">f , e ; E</text></g><!-- 2&#45;&gt;2 --><g id=\"edge2\" class=\"edge\"><title>2:ne-&gt;2:nw</title><path fill=\"none\" stroke=\"black\" d=\"M592.48,-48.72C603.48,-62.2 603.48,-76.87 578.48,-76.87 558.75,-76.87 554.59,-67.74 559.12,-57.26\" /><polygon fill=\"black\" stroke=\"black\" points=\"562.13,-59.05 564.48,-48.72 556.2,-55.33 562.13,-59.05\" /><text text-anchor=\"middle\" x=\"578.48\" y=\"-80.67\" font-family=\"Times New Roman,serif\" font-size=\"14.00\">f , e ; E</text></g><!-- 3 --><g id=\"node5\" class=\"node\"><title>3</title><ellipse fill=\"none\" stroke=\"black\" cx=\"791.6\" cy=\"-40.48\" rx=\"20.27\" ry=\"20.27\" /><ellipse fill=\"none\" stroke=\"black\" cx=\"791.6\" cy=\"-40.48\" rx=\"24.3\" ry=\"24.3\" /><text text-anchor=\"middle\" x=\"791.6\" y=\"-36.78\" font-family=\"Times New Roman,serif\" font-size=\"14.00\">q3</text></g><!-- 2&#45;&gt;3 --><g id=\"edge6\" class=\"edge\"><title>2-&gt;3</title><path fill=\"none\" stroke=\"black\" d=\"M598.91,-35.27C635.47,-36.26 712.41,-38.34 757.22,-39.55\" /><polygon fill=\"black\" stroke=\"black\" points=\"757.25,-43.05 767.34,-39.82 757.44,-36.06 757.25,-43.05\" /><text text-anchor=\"middle\" x=\"654.07\" y=\"-41.21\" font-family=\"Times New Roman,serif\" font-size=\"14.00\">E , Z ; E</text></g></g></svg>]]></imagesvg><resulttext /></taskresult></message></loncapagrade>";

        Assertions.assertEquals(expected, runGraFlapBlackBoxTest(blackBoxTest));
    }

    /**
     * Error in Input-Parameters.
     */
    @Disabled
    @Test
    void testAutomatonForCombinedLanguage() {
        BlackBoxTestData blackBoxTest = new BlackBoxTestData();

        blackBoxTest.setInput("Automat fuer Worte einer zusammengesetzten Sprache#de#(g|h)*|(hi)*#art#fa#20#-");
        blackBoxTest.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>fa</type><automaton><!--The list of states.--><state id=\"0\" name=\"s0\"><x>48.0</x><y>143.0</y><initial/></state><state id=\"1\" name=\"s1\"><x>208.0</x><y>64.0</y><final/></state><state id=\"2\" name=\"s2\"><x>210.0</x><y>185.0</y><final/></state><state id=\"3\" name=\"s3\"><x>360.0</x><y>187.0</y></state><!--The list of transitions.--><transition><from>0</from><to>1</to><read/></transition><transition><from>1</from><to>1</to><read>h </read></transition><transition><from>0</from><to>2</to><read/></transition><transition><from>1</from><to>1</to><read>g </read></transition><transition><from>2</from><to>3</to><read>h </read></transition><transition><from>3</from><to>2</to><read>i </read></transition></automaton></structure>");
        String expected = "";

        Assertions.assertEquals(expected, runGraFlapBlackBoxTest(blackBoxTest));
    }

    /**
     * Tests an Automaton against a given Grammar.
     */
    @Test
    void testAutomatonForEvenNumberOfW() {
        BlackBoxTestData blackBoxTest = new BlackBoxTestData();

        blackBoxTest.setInput("Automat fuer gerade Anzahl von w#de#x*(x*wx*wx*)*#artw#dfa#20#xxxxxxx%xxxxxxxxxxxwxwxxwxwxxwxwxxwxwxxwxwxxwxw%xxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxx%xxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxx%xxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxx!w%xw%xwx%www%wxww%xwxx%wwwx%xxwxxx%wxxxww%xwwxxwx%wwxwwxwx%wxxxwwww%wxxxwwxww%wxxwwxwxxw%xxxxwwwwxw");
        blackBoxTest.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure>&#13;<type>fa</type>&#13;<automaton>&#13;<!--The list of states.-->&#13;<state id=\"0\" name=\"s0\">&#13;<x>82.0</x>&#13;<y>90.0</y>&#13;<initial/>&#13;<final/>&#13;</state>&#13;<state id=\"1\" name=\"s1\">&#13;<x>180.0</x>&#13;<y>90.0</y>&#13;</state>&#13;<!--The list of transitions.-->&#13;<transition>&#13;<from>0</from>&#13;<to>1</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>0</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>0</from>&#13;<to>0</to>&#13;<read>x</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>1</to>&#13;<read>x</read>&#13;</transition>&#13;</automaton>&#13;</structure>");
        String expected = "<loncapagrade><awarddetail>EXACT_ANS</awarddetail><message><taskresult grade=\"passed\"><tasktitle>Automat fuer gerade Anzahl von w</tasktitle><titlesvg>Automat</titlesvg><imagesvg><![CDATA[<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"341pt\" height=\"90pt\" viewBox=\"0.00 0.00 340.64 90.25\"><g id=\"graph0\" class=\"graph\" transform=\"scale(1 1) rotate(0) translate(4 86.25)\"><title>automaton</title><polygon fill=\"white\" stroke=\"transparent\" points=\"-4,4 -4,-86.25 336.64,-86.25 336.64,4 -4,4\" /><!-- start --><g id=\"node1\" class=\"node\"><title>start</title></g><!-- 0 --><g id=\"node2\" class=\"node\"><title>0</title><ellipse fill=\"none\" stroke=\"black\" cx=\"171.08\" cy=\"-22.4\" rx=\"18.72\" ry=\"18.72\" /><ellipse fill=\"none\" stroke=\"black\" cx=\"171.08\" cy=\"-22.4\" rx=\"22.7\" ry=\"22.7\" /><text text-anchor=\"middle\" x=\"171.08\" y=\"-18.7\" font-family=\"Times New Roman,serif\" font-size=\"14.00\">s0</text></g><!-- start&#45;&gt;0 --><g id=\"edge1\" class=\"edge\"><title>start-&gt;0</title><path fill=\"none\" stroke=\"black\" d=\"M54.17,-22.4C77.83,-22.4 112.28,-22.4 137.66,-22.4\" /><polygon fill=\"black\" stroke=\"black\" points=\"137.92,-25.9 147.92,-22.4 137.92,-18.9 137.92,-25.9\" /></g><!-- 0&#45;&gt;0 --><g id=\"edge4\" class=\"edge\"><title>0:ne-&gt;0:nw</title><path fill=\"none\" stroke=\"black\" d=\"M187.08,-38.4C198.08,-52.58 198.08,-67.25 171.08,-67.25 149.56,-67.25 145.19,-57.93 149.87,-46.97\" /><polygon fill=\"black\" stroke=\"black\" points=\"152.88,-48.76 155.08,-38.4 146.9,-45.13 152.88,-48.76\" /><text text-anchor=\"middle\" x=\"171.08\" y=\"-71.05\" font-family=\"Times New Roman,serif\" font-size=\"14.00\">x</text></g><!-- 1 --><g id=\"node3\" class=\"node\"><title>1</title><ellipse fill=\"none\" stroke=\"black\" cx=\"312.2\" cy=\"-22.4\" rx=\"18.7\" ry=\"18.7\" /><text text-anchor=\"middle\" x=\"312.2\" y=\"-18.7\" font-family=\"Times New Roman,serif\" font-size=\"14.00\">s1</text></g><!-- 0&#45;&gt;1 --><g id=\"edge2\" class=\"edge\"><title>0-&gt;1</title><path fill=\"none\" stroke=\"black\" d=\"M193.13,-28.59C217.52,-31.12 256.9,-31.32 283.64,-29.18\" /><polygon fill=\"black\" stroke=\"black\" points=\"284.29,-32.63 293.9,-28.16 283.6,-25.67 284.29,-32.63\" /><text text-anchor=\"middle\" x=\"233.38\" y=\"-32.68\" font-family=\"Times New Roman,serif\" font-size=\"14.00\">w</text></g><!-- 1&#45;&gt;0 --><g id=\"edge3\" class=\"edge\"><title>1-&gt;0</title><path fill=\"none\" stroke=\"black\" d=\"M294.04,-16.65C271.2,-13.84 231.58,-13.4 203.49,-15.33\" /><polygon fill=\"black\" stroke=\"black\" points=\"203.02,-11.86 193.35,-16.19 203.61,-18.83 203.02,-11.86\" /><text text-anchor=\"middle\" x=\"243.77\" y=\"-19.79\" font-family=\"Times New Roman,serif\" font-size=\"14.00\">w</text></g><!-- 1&#45;&gt;1 --><g id=\"edge5\" class=\"edge\"><title>1:ne-&gt;1:nw</title><path fill=\"none\" stroke=\"black\" d=\"M325.2,-35.4C336.2,-48.58 336.2,-63.25 312.2,-63.25 293.45,-63.25 289.35,-54.3 293.7,-44.09\" /><polygon fill=\"black\" stroke=\"black\" points=\"296.81,-45.72 299.2,-35.4 290.89,-41.98 296.81,-45.72\" /><text text-anchor=\"middle\" x=\"312.2\" y=\"-67.05\" font-family=\"Times New Roman,serif\" font-size=\"14.00\">x</text></g></g></svg>]]></imagesvg><resulttext /></taskresult></message></loncapagrade>";

        Assertions.assertEquals(expected, runGraFlapBlackBoxTest(blackBoxTest));
    }

    /**
     * Error in Input-Parameters.
     */
    @Disabled
    @Test
    void testAutomatonForBinaryNumbers() {
        BlackBoxTestData blackBoxTest = new BlackBoxTestData();

        blackBoxTest.setInput("Automat fuer den Vergleich von Dualzahlen#de#(00|11)*10(00|01|10|11)*#artw#fa#80#0010%111100000010%1100111111110011001100001110001100%0000110011001111001110101010010111%1100000000001100000011111110111010%0000000010001110011010000110111100%11111001010110100111111101011010001011%10110110101111110101001111001110010010%11111010001001100001001011001101100000%001111111100110000001111101100000101110011%000000100110110111000001000010111110011000%11101010100110000111110001111101110000110101%000000000011000011111100110000000011101011101111%00111111111111110000000000110010000000100001011000%0011110011100110101100001101010101000000000000111111%110011111100000011000011110000000011111110100111011101%110000001111111111001111111111001100100000001111110000%00111111000000110000000000111111111100101011100000000001%00111100110011000000110011110000100001000110100110001101010110010010%1111111100111100110011111111000011001000000010000111101000111000100111!%1%0%11%01%110%111%100%000%010%0110%0000%1111%01111%01110%01001%011000%000001%1111100%0001110%0010011%01011101%11000001%00010110%011001001%111001010%111101000%110001110%111000010%0001111000%1111011000%11011110010%10000110010%110101000000%1101101000100%1010000101000%0110100001010%1010011010100%0011111101110%0100101101000%01111100111100%00000011110111%01000010101100%01110110010100%01100111100011%111110000111001%000000000100101%000011011001001%0110101111100101%1100110011001101%1101011100011010%0000010101100000%01111000010000001%11011000110111011%00001010001011101%00011111001000010%000111100011001101%0001101001010110011%01011001110000110000%00000011011000100101");
        blackBoxTest.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>fa</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>90.0</x><y>144.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>253.0</x><y>144.0</y></state><state id=\"2\" name=\"q2\"><x>67.0</x><y>33.0</y></state><state id=\"3\" name=\"q3\"><x>252.0</x><y>24.0</y><final/></state><state id=\"4\" name=\"q4\"><x>407.0</x><y>19.0</y></state><!--The list of transitions.--><transition><from>4</from><to>3</to><read>0</read></transition><transition><from>3</from><to>4</to><read>0</read></transition><transition><from>2</from><to>3</to><read>0</read></transition><transition><from>4</from><to>3</to><read>1</read></transition><transition><from>3</from><to>4</to><read>1</read></transition><transition><from>1</from><to>0</to><read>0</read></transition><transition><from>0</from><to>1</to><read>0</read></transition><transition><from>0</from><to>2</to><read>1</read></transition><transition><from>2</from><to>0</to><read>1</read></transition></automaton></structure>");
        String expected = "";

        Assertions.assertEquals(expected, runGraFlapBlackBoxTest(blackBoxTest));
    }

    /**
     * Tests a context free Grammar against a given Alphabet.
     */
    @Test
    void testContextFreeGrammar() {
        BlackBoxTestData blackBoxTest = new BlackBoxTestData();

        blackBoxTest.setInput("Beispiel fuer eine kontextfreie Grammatik#de#n,o,p#egt#cfg#0#-");
        blackBoxTest.setStudentAnswer("S -> E | p | n S o");
        String expected = "<loncapagrade><awarddetail>EXACT_ANS</awarddetail><message><taskresult grade=\"passed\"><tasktitle>Beispiel fuer eine kontextfreie Grammatik</tasktitle><titlesvg>Grammatik</titlesvg><imagesvg><![CDATA[<p>S -&gt; E p n S o</p>]]></imagesvg><resulttext /></taskresult></message></loncapagrade>";

        Assertions.assertEquals(expected, runGraFlapBlackBoxTest(blackBoxTest));
    }

    /**
     * Tests a non context free Grammar against a given Alphabet.
     */
    @Test
    void testNonContextFreeGrammar() {
        BlackBoxTestData blackBoxTest = new BlackBoxTestData();

        blackBoxTest.setInput("Beispiel fuer eine nicht kontextfreie Grammatik#de#u,v,w#egt#ncfg#0#-");
        blackBoxTest.setStudentAnswer("S -> w | uSv, uS -> Sw");
        String expected = "<loncapagrade><awarddetail>EXACT_ANS</awarddetail><message><taskresult grade=\"passed\"><tasktitle>Beispiel fuer eine nicht kontextfreie Grammatik</tasktitle><titlesvg>Grammatik</titlesvg><imagesvg><![CDATA[<p>S -&gt; w uSv, uS -&gt; Sw</p>]]></imagesvg><resulttext /></taskresult></message></loncapagrade>";

        Assertions.assertEquals(expected, runGraFlapBlackBoxTest(blackBoxTest));
    }
    /**
     * Tests a right regular Grammar against a given Alphabet.
     */
    @Test
    void testRightRegularGrammar() {
        BlackBoxTestData blackBoxTest = new BlackBoxTestData();

        blackBoxTest.setInput("Beispiel fuer eine rechtslineare Grammatik#de#h,i,j#egt#rl#0#-");
        blackBoxTest.setStudentAnswer("S -> E | j | hiS");
        String expected = "<loncapagrade><awarddetail>EXACT_ANS</awarddetail><message><taskresult grade=\"passed\"><tasktitle>Beispiel fuer eine rechtslineare Grammatik</tasktitle><titlesvg>Grammatik</titlesvg><imagesvg><![CDATA[<p>S -&gt; E j hiS</p>]]></imagesvg><resulttext /></taskresult></message></loncapagrade>";

        Assertions.assertEquals(expected, runGraFlapBlackBoxTest(blackBoxTest));
    }

    /**
     * Error in Input-Parameters.
     */
    @Disabled
    @Test
    void testContextFreeGrammarForGivenLanguage() {
        BlackBoxTestData blackBoxTest = new BlackBoxTestData();

        blackBoxTest.setInput("Kontextfreie Grammatik fuer gegebene Sprache#de#S -> T|V, T -> ef | e T f, V -> eef | ee V f#ggtw#cfg#64#ef%eef%eeff%eeeeff%eeefff%eeeeffff%eeeeeefff%eeeeefffff%eeeeeeeeffff%eeeeeeffffff%eeeeeeefffffff%eeeeeeeeeefffff%eeeeeeeeffffffff%eeeeeeeeeeeeffffff%eeeeeeeeefffffffff%eeeeeeeeeeffffffffff%eeeeeeeeeeeeeefffffff%eeeeeeeeeeefffffffffff%eeeeeeeeeeeeeeeeffffffff%eeeeeeeeeeeeeeeeeefffffffff%eeeeeeeeeeeeeeeeeeeeffffffffff%eeeeeeeeeeeeeeeeeeeeeefffffffffff!%f%ee%ff%fe%fff%ffe%fee%eee%eff%efe%efee%fefe%ffef%ffff%eeef%efef%feff%eeee%efffe%eeefe%efeff%feeff%effee%eefff%eeeee%eeeef%effff%eefee%fefee%ffeee%fffef%eeffe%efefe%feeef%feefe%ffffe%fefff%efeee%efeef%effef%eefef");
        blackBoxTest.setStudentAnswer("S -> T|V, T -> ef | e T f, V -> eef | ee V f");
        String expected = "";

        Assertions.assertEquals(expected, runGraFlapBlackBoxTest(blackBoxTest));
    }

    /**
     * Error in Input-Parameters.
     */
    @Disabled
    @Test
    void testGrammarForIntegers() {
        BlackBoxTestData blackBoxTest = new BlackBoxTestData();

        blackBoxTest.setInput("Grammatik fuer ganze Zahlen#de#S -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D | -P, P ->  1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D , D ->  0| 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 0D | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D#ggtw#rl#0%9%-2%91%24%-10%-20%437%-32%350%720%887%-791%-253%-532%-268%-319%-680%-973%-807%-431!-%-0%00%-00%-1-2%00884%00687%00695%00224%00957%00855%00806%00656%00871%00292%-0090%00892%00870%00907%00-703%-00106%00-558%-00819%00-380%-00547%-00785%00-943%-00352%-00528%00-695%-00-28%-00594%-00717%-00-180%-00-328%-00-793%-00-304%-00-388%-00-384%-00-365%-00-392#-");
        blackBoxTest.setStudentAnswer("S -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9  \t  | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D  | -P, P -> 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9  \t\t  | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D, D -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9  | 0D  | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D");
        String expected = "";

        Assertions.assertEquals(expected, runGraFlapBlackBoxTest(blackBoxTest));
    }

    /**
     * Error in Input-Parameters.
     */
    @Disabled
    @Test
    void testGrammarForGivenLanguage() {
        BlackBoxTestData blackBoxTest = new BlackBoxTestData();

        blackBoxTest.setInput("Grammatik fuer Sprache (enthaelt nicht ef)#de#e*|(f|g)*e*((g+f*)*e*)*#grtw#rl#80#eeeee%eeeeee%eeeeeeeeee%eeeeeeeeeee%eeeeeeeeeeee%eeeeeeeeeeeeee%eeeeeeeeeeeeeee%eeeeeeeeeeeeeeee%eeeeeeeeeeeeeeeee%eeeeeeeeeeeeeeeeee%ffeeeegffffffffffffffgffffffffffffffegffffffffffffffgffffffffffffffegffffffffffffffgffffffffffffffegffffffffffffffgffffffffffffffe%ffgfggfgfggffggfgeeeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeee%fgffgffgfgeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeee%gffffgeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeee%ffgggfgggffgfffffgfgeeeeeeeeeeeeeeeeeeegggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffeeeeeeeegggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffeeeeeeeegggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffeeeeeeeegggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffeeeeeeee%gggfggggffffffggfffeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeee%fgggeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeee%ggfgeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeee%eeeeeeeeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeee%gfgfffgfgfeeeeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeee!eef%gffef%egfeefg%eefefeegg%ggffgefgf%fgfgeffgff%ggegfefgff%feeggeffgf%gffffeefgee%eeggffefefe%egegeffgeee%eeggefefege%efgegegggfg%fgegggeeefee%efefgggggeef%eeffgeefffeg%egeefefeffge%ggfgffeefffg%fffeeeegeeef%eegggfeefeef%gefffgefegeg%geffeegefggff%fgefgffgfegff%egffeffffgegf%fefeegegggfgee%ggeefggfgegfgg%fefgeefgeggfgg%egffgfeeffggeg%fgefeeggffffee%fgfeegfgeeffff%fgefgegegeeege%egegeffegfffefg%efgfefgfffggeff%egggggefgggfgef%fgggfffefgggggf%ffegefegggeffff%fffeefgefgeggee%feggfgefgggfgeef%efgegffgfgeggfgf%gfggffgefefggggg%feffgegefeeggfegg%fegegeeefgeggeggf%ggffgefeffgffggeff%gfgefffeggfeffffef%gegfegfefeeeffgfeg%gfgggfffegfeeggefg%efeffffffggfgfgffe%eegeffffggeeefeeff%ffefgfeffgggggefege%fgefgefeeffgefegfef%effeggeeggfegfgffge%eegeeeffgeffggggggg%feffgegfggeffgfffeg%ffefeffeffffgggegfg%gfeefefegfgfgeeffee%efggffgegfeegffeefef%eggeegeeegefgeegeeef%geefefeegffeffgfegge%feeeefgefeffgeeggggf%ffgfefeegegffeefgegg");
        blackBoxTest.setStudentAnswer("S -> e A | f S | g S | E , A -> e A | g S | E");
        String expected = "";

        Assertions.assertEquals(expected, runGraFlapBlackBoxTest(blackBoxTest));
    }

    /**
     * Resets System.out and closes Outputstream.
     */
    @AfterAll
    static void cleanup(){
        System.setOut(systemOut);
        captureStream.close();
        try {
            capture.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
