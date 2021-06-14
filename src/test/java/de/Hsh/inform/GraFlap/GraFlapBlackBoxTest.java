package de.HsH.inform.GraFlap;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.TaskMode;
import de.HsH.inform.GraFlap.entity.TaskType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


public class GraFlapBlackBoxTest {

    @Test
    void testTuringForSpMTp2M() {
        Arguments arguments = new Arguments();
        arguments.setTaskTitle("Turing-Automat fuer s^m t^{2 m}");
        arguments.setUserLanguage("de");
        arguments.setSolution("S -> s tt | s S t t");
        arguments.setTaskMode(TaskMode.AGTW);
        arguments.setTaskType(TaskType.DTM);
        arguments.setWordString("stt%sstttt%ssstttttt%sssstttttttt%ssssstttttttttt%sssssstttttttttttt%ssssssstttttttttttttt%sssssssstttttttttttttttt%ssssssssstttttttttttttttttt%sssssssssstttttttttttttttttttt%ssssssssssstttttttttttttttttttttt%sssssssssssstttttttttttttttttttttttt%ssssssssssssstttttttttttttttttttttttttt%sssssssssssssstttttttttttttttttttttttttttt%ssssssssssssssstttttttttttttttttttttttttttttt%sssssssssssssssstttttttttttttttttttttttttttttttt%ssssssssssssssssstttttttttttttttttttttttttttttttttt%sssssssssssssssssstttttttttttttttttttttttttttttttttttt%ssssssssssssssssssstttttttttttttttttttttttttttttttttttttt%sssssssssssssssssssstttttttttttttttttttttttttttttttttttttttt%ssssssssssssssssssssstttttttttttttttttttttttttttttttttttttttttt%sssssssssssssssssssssstttttttttttttttttttttttttttttttttttttttttttt!%t%s%st%tt%ss%ts%tts%tst%sts%sst%sss%stss%stts%ttst%tsts%ssst%ssss%tttt%ttss%tsss%ttts%stsss%ttttt%sstss%ststs%stsst%sssts%sssss%sssst%ssstt%tssss%tstst%tsstt%tstts%ttsss%stttt%sstst%tssts%ssttt%sttts%ststt");
        arguments.setNumberOfWords(64);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>turing</type><automaton><!--The list of states.--><block id=\"0\" name=\"s0\"><tag>Machine0</tag><x>42.0</x><y>94.0</y><initial/></block><block id=\"1\" name=\"sr\"><tag>Machine1</tag><x>153.0</x><y>93.0</y></block><block id=\"2\" name=\"ss\"><tag>Machine2</tag><x>302.0</x><y>94.0</y></block><block id=\"3\" name=\"s2s\"><tag>Machine3</tag><x>559.0</x><y>216.0</y></block><block id=\"4\" name=\"sz\"><tag>Machine4</tag><x>328.0</x><y>245.0</y></block><block id=\"5\" name=\"sv\"><tag>Machine5</tag><x>144.0</x><y>238.0</y></block><block id=\"6\" name=\"sf\"><tag>Machine6</tag><x>256.0</x><y>355.0</y><final/></block><!--The list of transitions.--><transition><from>1</from><to>2</to><read/><write/><move>L</move></transition><transition><from>0</from><to>1</to><read>s</read><write/><move>R</move></transition><transition><from>4</from><to>5</to><read/><write/><move>R</move></transition><transition><from>2</from><to>3</to><read>t</read><write/><move>L</move></transition><transition><from>1</from><to>1</to><read>t</read><write>t</write><move>R</move></transition><transition><from>1</from><to>1</to><read>s</read><write>s</write><move>R</move></transition><transition><from>5</from><to>1</to><read>s</read><write/><move>R</move></transition><transition><from>3</from><to>4</to><read>t</read><write/><move>L</move></transition><transition><from>4</from><to>4</to><read>t</read><write>t</write><move>L</move></transition><transition><from>4</from><to>4</to><read>s</read><write>s</write><move>L</move></transition><transition><from>5</from><to>6</to><read/><write/><move>S</move></transition><!--The list of automata--><Machine3/><Machine4/><Machine5/><Machine6/><Machine0/><Machine1/><Machine2/></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Turing-Automat fuer s^m t^{2 m}", answerMessage.getTaskTitle());
        //Assertions.assertEquals(new org.jdom2.Element("S -> s tt | s S t t"), answerMessage.getSvgImage());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals("agtw", answerMessage.getTaskMode());
        Assertions.assertEquals("", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testAutomatonForEvenNumberOfW() {
        Arguments arguments = new Arguments();
        arguments.setTaskTitle("Automat fuer gerade Anzahl von w");
        arguments.setUserLanguage("de");
        arguments.setSolution("x*(x*wx*wx*)*");
        arguments.setTaskMode(TaskMode.ARTW);
        arguments.setTaskType(TaskType.DFA);
        arguments.setWordString("xxxxxxx%xxxxxxxxxxxwxwxxwxwxxwxwxxwxwxxwxwxxwxw%xxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxxxwxxx%xxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxxxxxxxwxxxxxwxx%xxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxxxxxxxxwxwxxxxxxx!w%xw%xwx%www%wxww%xwxx%wwwx%xxwxxx%wxxxww%xwwxxwx%wwxwwxwx%wxxxwwww%wxxxwwxww%wxxwwxwxxw%xxxxwwwwxw");
        arguments.setNumberOfWords(20);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure>&#13;<type>fa</type>&#13;<automaton>&#13;<!--The list of states.-->&#13;<state id=\"0\" name=\"s0\">&#13;<x>82.0</x>&#13;<y>90.0</y>&#13;<initial/>&#13;<final/>&#13;</state>&#13;<state id=\"1\" name=\"s1\">&#13;<x>180.0</x>&#13;<y>90.0</y>&#13;</state>&#13;<!--The list of transitions.-->&#13;<transition>&#13;<from>0</from>&#13;<to>1</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>0</to>&#13;<read>w</read>&#13;</transition>&#13;<transition>&#13;<from>0</from>&#13;<to>0</to>&#13;<read>x</read>&#13;</transition>&#13;<transition>&#13;<from>1</from>&#13;<to>1</to>&#13;<read>x</read>&#13;</transition>&#13;</automaton>&#13;</structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Automat fuer gerade Anzahl von w", answerMessage.getTaskTitle());
        //Assertions.assertEquals(new org.jdom2.Element("x*(x*wx*wx*)*"), answerMessage.getSvgImage());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals("artw", answerMessage.getTaskMode());
        Assertions.assertEquals("", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testAutomatonForBinaryNumbers() {
        Arguments arguments = new Arguments();
        arguments.setTaskTitle("Automat fuer den Vergleich von Dualzahlen");
        arguments.setUserLanguage("de");
        arguments.setSolution("(00|11)*10(00|01|10|11)*");
        arguments.setTaskMode(TaskMode.ARTW);
        arguments.setTaskType(TaskType.FA);
        arguments.setWordString("0010%111100000010%1100111111110011001100001110001100%0000110011001111001110101010010111%1100000000001100000011111110111010%0000000010001110011010000110111100%11111001010110100111111101011010001011%10110110101111110101001111001110010010%11111010001001100001001011001101100000%001111111100110000001111101100000101110011%000000100110110111000001000010111110011000%11101010100110000111110001111101110000110101%000000000011000011111100110000000011101011101111%00111111111111110000000000110010000000100001011000%0011110011100110101100001101010101000000000000111111%110011111100000011000011110000000011111110100111011101%110000001111111111001111111111001100100000001111110000%00111111000000110000000000111111111100101011100000000001%00111100110011000000110011110000100001000110100110001101010110010010%1111111100111100110011111111000011001000000010000111101000111000100111!%1%0%11%01%110%111%100%000%010%0110%0000%1111%01111%01110%01001%011000%000001%1111100%0001110%0010011%01011101%11000001%00010110%011001001%111001010%111101000%110001110%111000010%0001111000%1111011000%11011110010%10000110010%110101000000%1101101000100%1010000101000%0110100001010%1010011010100%0011111101110%0100101101000%01111100111100%00000011110111%01000010101100%01110110010100%01100111100011%111110000111001%000000000100101%000011011001001%0110101111100101%1100110011001101%1101011100011010%0000010101100000%01111000010000001%11011000110111011%00001010001011101%00011111001000010%000111100011001101%0001101001010110011%01011001110000110000%00000011011000100101");
        arguments.setNumberOfWords(80);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>fa</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>90.0</x><y>144.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>253.0</x><y>144.0</y></state><state id=\"2\" name=\"q2\"><x>67.0</x><y>33.0</y></state><state id=\"3\" name=\"q3\"><x>252.0</x><y>24.0</y><final/></state><state id=\"4\" name=\"q4\"><x>407.0</x><y>19.0</y></state><!--The list of transitions.--><transition><from>4</from><to>3</to><read>0</read></transition><transition><from>3</from><to>4</to><read>0</read></transition><transition><from>2</from><to>3</to><read>0</read></transition><transition><from>4</from><to>3</to><read>1</read></transition><transition><from>3</from><to>4</to><read>1</read></transition><transition><from>1</from><to>0</to><read>0</read></transition><transition><from>0</from><to>1</to><read>0</read></transition><transition><from>0</from><to>2</to><read>1</read></transition><transition><from>2</from><to>0</to><read>1</read></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Automat fuer den Vergleich von Dualzahlen", answerMessage.getTaskTitle());
        //Assertions.assertEquals(new org.jdom2.Element("(00|11)*10(00|01|10|11)*"), answerMessage.getSvgImage());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals("artw", answerMessage.getTaskMode());
        Assertions.assertEquals("", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testContextFreeGrammar() {
        Arguments arguments = new Arguments();
        arguments.setTaskTitle("Beispiel fuer eine kontextfreie Grammatik");
        arguments.setUserLanguage("de");
        arguments.setSolution("n,o,p");
        arguments.setTaskMode(TaskMode.EGT);
        arguments.setTaskType(TaskType.CFG);
        arguments.setWordString("-");
        arguments.setNumberOfWords(0);
        arguments.setStudentAnswer("S -> E | p | n S o");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Beispiel fuer eine kontextfreie Grammatik", answerMessage.getTaskTitle());
        //Assertions.assertEquals(new org.jdom2.Element("n,o,p"), answerMessage.getSvgImage());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals("egt", answerMessage.getTaskMode());
        Assertions.assertEquals("", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testNonContextFreeGrammar() {
        Arguments arguments = new Arguments();
        arguments.setTaskTitle("Beispiel fuer eine nicht kontextfreie Grammatik");
        arguments.setUserLanguage("de");
        arguments.setSolution("u,v,w");
        arguments.setTaskMode(TaskMode.EGT);
        arguments.setTaskType(TaskType.NCFG);
        arguments.setWordString("-");
        arguments.setNumberOfWords(0);
        arguments.setStudentAnswer("S -> w | uSv, uS -> Sw");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Beispiel fuer eine nicht kontextfreie Grammatik", answerMessage.getTaskTitle());
        //Assertions.assertEquals(new org.jdom2.Element("u,v,w"), answerMessage.getSvgImage());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals("egt", answerMessage.getTaskMode());
        Assertions.assertEquals("", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testRightRegularGrammar() {
        Arguments arguments = new Arguments();
        arguments.setTaskTitle("Beispiel fuer eine rechtslineare Grammatik");
        arguments.setUserLanguage("de");
        arguments.setSolution("h,i,j");
        arguments.setTaskMode(TaskMode.EGT);
        arguments.setTaskType(TaskType.RL);
        arguments.setWordString("-");
        arguments.setNumberOfWords(0);
        arguments.setStudentAnswer("S -> E | j | hiS");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Beispiel fuer eine rechtslineare Grammatik", answerMessage.getTaskTitle());
        //Assertions.assertEquals(new org.jdom2.Element("h,i,j"), answerMessage.getSvgImage());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals("egt", answerMessage.getTaskMode());
        Assertions.assertEquals("", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testContextFreeGrammarForGivenLanguage() {
        Arguments arguments = new Arguments();
        arguments.setTaskTitle("Kontextfreie Grammatik fuer gegebene Sprache");
        arguments.setUserLanguage("de");
        arguments.setSolution("S -> T|V, T -> ef | e T f, V -> eef | ee V f");
        arguments.setTaskMode(TaskMode.GGTW);
        arguments.setTaskType(TaskType.CFG);
        arguments.setWordString("ef%eef%eeff%eeeeff%eeefff%eeeeffff%eeeeeefff%eeeeefffff%eeeeeeeeffff%eeeeeeffffff%eeeeeeefffffff%eeeeeeeeeefffff%eeeeeeeeffffffff%eeeeeeeeeeeeffffff%eeeeeeeeefffffffff%eeeeeeeeeeffffffffff%eeeeeeeeeeeeeefffffff%eeeeeeeeeeefffffffffff%eeeeeeeeeeeeeeeeffffffff%eeeeeeeeeeeeeeeeeefffffffff%eeeeeeeeeeeeeeeeeeeeffffffffff%eeeeeeeeeeeeeeeeeeeeeefffffffffff!%f%ee%ff%fe%fff%ffe%fee%eee%eff%efe%efee%fefe%ffef%ffff%eeef%efef%feff%eeee%efffe%eeefe%efeff%feeff%effee%eefff%eeeee%eeeef%effff%eefee%fefee%ffeee%fffef%eeffe%efefe%feeef%feefe%ffffe%fefff%efeee%efeef%effef%eefef");
        arguments.setNumberOfWords(64);
        arguments.setStudentAnswer("S -> T|V, T -> ef | e T f, V -> eef | ee V f");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Kontextfreie Grammatik fuer gegebene Sprache", answerMessage.getTaskTitle());
        //Assertions.assertEquals(new org.jdom2.Element("S -> T|V, T -> ef | e T f, V -> eef | ee V f"), answerMessage.getSvgImage());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals("ggtw", answerMessage.getTaskMode());
        Assertions.assertEquals("", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testGrammarForIntegers() {
        Arguments arguments = new Arguments();
        arguments.setTaskTitle("Grammatik fuer ganze Zahlen");
        arguments.setUserLanguage("de");
        arguments.setSolution("S -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D | -P, P ->  1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D , D ->  0| 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 0D | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D");
        arguments.setTaskMode(TaskMode.GGTW);
        arguments.setTaskType(TaskType.RL);
        arguments.setWordString("0%9%-2%91%24%-10%-20%437%-32%350%720%887%-791%-253%-532%-268%-319%-680%-973%-807%-431!-%-0%00%-00%-1-2%00884%00687%00695%00224%00957%00855%00806%00656%00871%00292%-0090%00892%00870%00907%00-703%-00106%00-558%-00819%00-380%-00547%-00785%00-943%-00352%-00528%00-695%-00-28%-00594%-00717%-00-180%-00-328%-00-793%-00-304%-00-388%-00-384%-00-365%-00-392");
        arguments.setNumberOfWords(62);
        arguments.setStudentAnswer("S -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9  \t  | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D  | -P, P -> 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D, D -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9  | 0D  | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Grammatik fuer ganze Zahlen", answerMessage.getTaskTitle());
        //Assertions.assertEquals(new org.jdom2.Element("S -> 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D | -P, P ->  1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D , D ->  0| 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 0D | 1D | 2D | 3D | 4D | 5D | 6D | 7D | 8D | 9D"), answerMessage.getSvgImage());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals("ggtw", answerMessage.getTaskMode());
        Assertions.assertEquals("", answerMessage.getFeedback());
        Assertions.assertEquals("Grammatik", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testAutomatonForGivenLanguage() {
        Arguments arguments = new Arguments();
        arguments.setTaskTitle("Automat fuer gegebene Sprache");
        arguments.setUserLanguage("de");
        arguments.setSolution("S -> A, A -> x B|y C, B -> x A|y D,C -> x D|y F, D -> x C|y G, F -> x G|y H, G -> x F|y J, H -> x J| y H | E, J -> x H|y J");
        arguments.setTaskMode(TaskMode.AGTW);
        arguments.setTaskType(TaskType.FA);
        arguments.setWordString("yyy%yyyy%xxyyy%xyxyy%xyyxy%xyyyx%yxxyy%yxyxy%yxyyx%yyxxy%yyxyx%yyyxx%yyyyy%xxyyyy%xyxyyy%xyyxyy%xyyyxy%xyyyyx%yxxyyy%yxyxyy%yxyyxy%yxyyyx!%y%xx%yx%yyx%xyy%yxx%xxy%xyx%xxyy%yxxy%yxxx%xyxx%yyxy%xyxy%xxxx%xyyy%xxxy%yyxx%xyxxx%xyxyx%yyyxy%xyyxx%yxxxy%xxxyy%xxyyx%yxxxx%xxxyx%yyxxx%xyxxy%xyyyy%xxyxy%xxyxx");
        arguments.setNumberOfWords(55);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>fa</type><automaton><!--The list of states.--><state id=\"0\" name=\"s0\"><x>57.0</x><y>61.0</y><initial/></state><state id=\"1\" name=\"s1\"><x>200.0</x><y>65.0</y></state><state id=\"2\" name=\"s2\"><x>414.0</x><y>58.0</y></state><state id=\"3\" name=\"s4\"><x>53.0</x><y>188.0</y></state><state id=\"4\" name=\"s5\"><x>192.0</x><y>189.0</y></state><state id=\"5\" name=\"s6\"><x>404.0</x><y>188.0</y></state><state id=\"6\" name=\"s7\"><x>598.0</x><y>191.0</y></state><state id=\"7\" name=\"s3\"><x>593.0</x><y>57.0</y><final/></state><!--The list of transitions.--><transition><from>0</from><to>3</to><read>x</read></transition><transition><from>3</from><to>0</to><read>x</read></transition><transition><from>7</from><to>7</to><read>y</read></transition><transition><from>6</from><to>6</to><read>y</read></transition><transition><from>0</from><to>1</to><read>y</read></transition><transition><from>5</from><to>6</to><read>y</read></transition><transition><from>3</from><to>4</to><read>y</read></transition><transition><from>4</from><to>1</to><read>x</read></transition><transition><from>1</from><to>4</to><read>x</read></transition><transition><from>6</from><to>7</to><read>x</read></transition><transition><from>7</from><to>6</to><read>x</read></transition><transition><from>4</from><to>5</to><read>y</read></transition><transition><from>2</from><to>7</to><read>y</read></transition><transition><from>5</from><to>2</to><read>x</read></transition><transition><from>2</from><to>5</to><read>x</read></transition><transition><from>1</from><to>2</to><read>y</read></transition></automaton></structure> ");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Automat fuer gegebene Sprache", answerMessage.getTaskTitle());
        //Assertions.assertEquals(new org.jdom2.Element("S -> A, A -> x B|y C, B -> x A|y D,C -> x D|y F, D -> x C|y G, F -> x G|y H, G -> x F|y J, H -> x J| y H | E, J -> x H|y J"), answerMessage.getSvgImage());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals("agtw", answerMessage.getTaskMode());
        Assertions.assertEquals("", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Test
    void testAutomatonForRegex() {
        Arguments arguments = new Arguments();
        arguments.setTaskTitle("Automat fuer e^n f^n");
        arguments.setUserLanguage("de");
        arguments.setSolution("S->eSf|ef");
        arguments.setTaskMode(TaskMode.AGTW);
        arguments.setTaskType(TaskType.PDA);
        arguments.setWordString("ef%eeff%eeefff%eeeeffff%eeeeefffff%eeeeeeffffff%eeeeeeefffffff%eeeeeeeeffffffff%eeeeeeeeefffffffff%eeeeeeeeeeffffffffff%eeeeeeeeeeefffffffffff%eeeeeeeeeeeeffffffffffff%eeeeeeeeeeeeefffffffffffff%eeeeeeeeeeeeeeffffffffffffff%eeeeeeeeeeeeeeefffffffffffffff%eeeeeeeeeeeeeeeeffffffffffffffff%eeeeeeeeeeeeeeeeefffffffffffffffff%eeeeeeeeeeeeeeeeeeffffffffffffffffff%eeeeeeeeeeeeeeeeeeefffffffffffffffffff%eeeeeeeeeeeeeeeeeeeeffffffffffffffffffff%eeeeeeeeeeeeeeeeeeeeefffffffffffffffffffff%eeeeeeeeeeeeeeeeeeeeeeffffffffffffffffffffff!%f%e%ee%efe%eff%eee%fef%fee%ffe%ffee%eeef%fefe%ffef%efee%feef%feff%efef%efffe%efeee%eefee%ffeee%eefff%efefe%effef%fefff%efeff%feffe%ffffe%fefee%eefef%ffeef%eeeff%eeefe%eeeee%feeff%feeffe%eefeff%fefefe%fefeef%effeef%efefef");
        arguments.setNumberOfWords(64);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>pda</type><automaton><!--The list of states.--><state id=\"0\" name=\"q0\"><x>34.0</x><y>122.0</y><initial/></state><state id=\"1\" name=\"q1\"><x>177.0</x><y>117.0</y></state><state id=\"2\" name=\"q2\"><x>317.0</x><y>112.0</y></state><state id=\"3\" name=\"q3\"><x>465.0</x><y>108.0</y><final/></state><!--The list of transitions.--><transition><from>2</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>1</from><to>2</to><read>f</read><pop>e</pop><push/></transition><transition><from>0</from><to>1</to><read>e</read><pop>Z</pop><push>e Z</push></transition><transition><from>1</from><to>1</to><read>e</read><pop>e</pop><push>ee</push></transition><transition><from>2</from><to>3</to><read/><pop>Z</pop><push/></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("Automat fuer e^n f^n", answerMessage.getTaskTitle());
        //Assertions.assertEquals(new org.jdom2.Element("S->eSf|ef"), answerMessage.getSvgImage());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals("agtw", answerMessage.getTaskMode());
        Assertions.assertEquals("", answerMessage.getFeedback());
        Assertions.assertEquals("Automat", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    @Disabled
    @Test
    void testAutomatonForCombinedLanguage() {
        Arguments arguments = new Arguments();
        arguments.setTaskTitle("Automat fuer Worte einer zusammengesetzten Sprache");
        arguments.setUserLanguage("de");
        arguments.setSolution("(g|h)*|(hi)*");
        arguments.setTaskMode(TaskMode.ART);
        arguments.setTaskType(TaskType.FA);
        arguments.setWordString("-");
        arguments.setNumberOfWords(0);
        arguments.setStudentAnswer("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><!--Created with JFLAP 6.4.--><structure><type>fa</type><automaton><!--The list of states.--><state id=\"0\" name=\"s0\"><x>48.0</x><y>143.0</y><initial/></state><state id=\"1\" name=\"s1\"><x>208.0</x><y>64.0</y><final/></state><state id=\"2\" name=\"s2\"><x>210.0</x><y>185.0</y><final/></state><state id=\"3\" name=\"s3\"><x>360.0</x><y>187.0</y></state><!--The list of transitions.--><transition><from>0</from><to>1</to><read/></transition><transition><from>1</from><to>1</to><read>h </read></transition><transition><from>0</from><to>2</to><read/></transition><transition><from>1</from><to>1</to><read>g </read></transition><transition><from>2</from><to>3</to><read>h </read></transition><transition><from>3</from><to>2</to><read>i </read></transition></automaton></structure>");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("", answerMessage.getTaskTitle());
        //Assertions.assertEquals(new org.jdom2.Element("(g|h)*|(hi)*"), answerMessage.getSvgImage());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals("", answerMessage.getTaskMode());
        Assertions.assertEquals("", answerMessage.getFeedback());
        Assertions.assertEquals("", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }

    /**
     * Endless Loop
     */
    @Disabled
    @Test
    void testGrammarForGivenLanguage() {
        Arguments arguments = new Arguments();
        arguments.setTaskTitle("Grammatik fuer Sprache (enthaelt nicht ef)");
        arguments.setUserLanguage("de");
        arguments.setSolution("e*|(f|g)*e*((g+f*)*e*)*");
        arguments.setTaskMode(TaskMode.GRTW);
        arguments.setTaskType(TaskType.RL);
        arguments.setWordString("eeeee%eeeeee%eeeeeeeeee%eeeeeeeeeee%eeeeeeeeeeee%eeeeeeeeeeeeee%eeeeeeeeeeeeeee%eeeeeeeeeeeeeeee%eeeeeeeeeeeeeeeee%eeeeeeeeeeeeeeeeee%ffeeeegffffffffffffffgffffffffffffffegffffffffffffffgffffffffffffffegffffffffffffffgffffffffffffffegffffffffffffffgffffffffffffffe%ffgfggfgfggffggfgeeeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeeeggggfggggfeeeeeeeeeee%fgffgffgfgeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeeeggggggggggfffffffffffffffffffggggggggggfffffffffffffffffffeeeeeeeeeeeeee%gffffgeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeee%ffgggfgggffgfffffgfgeeeeeeeeeeeeeeeeeeegggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffeeeeeeeegggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffeeeeeeeegggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffeeeeeeeegggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffgggggggggggggggffffffffffeeeeeeee%gggfggggffffffggfffeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeeegggggggggggggggggggfffffffgggggggggggggggggggfffffffgggggggggggggggggggfffffffeeee%fgggeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeeeggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffggggggggggggggffffeeeeeeeeeeeeee%ggfgeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggeeeee%eeeeeeeeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeeeggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffggggggggggggggfffffffeeeeeeeee%gfgfffgfgfeeeeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeeegggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffgggggfffffffffeeeeeeeeeeeeeeeee!eef%gffef%egfeefg%eefefeegg%ggffgefgf%fgfgeffgff%ggegfefgff%feeggeffgf%gffffeefgee%eeggffefefe%egegeffgeee%eeggefefege%efgegegggfg%fgegggeeefee%efefgggggeef%eeffgeefffeg%egeefefeffge%ggfgffeefffg%fffeeeegeeef%eegggfeefeef%gefffgefegeg%geffeegefggff%fgefgffgfegff%egffeffffgegf%fefeegegggfgee%ggeefggfgegfgg%fefgeefgeggfgg%egffgfeeffggeg%fgefeeggffffee%fgfeegfgeeffff%fgefgegegeeege%egegeffegfffefg%efgfefgfffggeff%egggggefgggfgef%fgggfffefgggggf%ffegefegggeffff%fffeefgefgeggee%feggfgefgggfgeef%efgegffgfgeggfgf%gfggffgefefggggg%feffgegefeeggfegg%fegegeeefgeggeggf%ggffgefeffgffggeff%gfgefffeggfeffffef%gegfegfefeeeffgfeg%gfgggfffegfeeggefg%efeffffffggfgfgffe%eegeffffggeeefeeff%ffefgfeffgggggefege%fgefgefeeffgefegfef%effeggeeggfegfgffge%eegeeeffgeffggggggg%feffgegfggeffgfffeg%ffefeffeffffgggegfg%gfeefefegfgfgeeffee%efggffgegfeegffeefef%eggeegeeegefgeegeeef%geefefeegffeffgfegge%feeeefgefeffgeeggggf%ffgfefeegegffeefgegg");
        arguments.setNumberOfWords(80);
        arguments.setStudentAnswer("S -> e A | f S | g S | E , A -> e A | g S | E");
        AnswerMessage answerMessage = Assertions.assertDoesNotThrow(() -> GraFlap.processSubmission(arguments));
        Assertions.assertEquals("", answerMessage.getTaskTitle());
        //Assertions.assertEquals(new org.jdom2.Element("e*|(f|g)*e*((g+f*)*e*)*"), answerMessage.getSvgImage());
        Assertions.assertEquals(0, answerMessage.getPercentOfTestWordsFailed());
        Assertions.assertEquals("", answerMessage.getTaskMode());
        Assertions.assertEquals("", answerMessage.getFeedback());
        Assertions.assertEquals("", answerMessage.getSvgTitle());
        Assertions.assertTrue(answerMessage.hasPassed());
        Assertions.assertEquals(1.0, answerMessage.getScore());
    }
}
