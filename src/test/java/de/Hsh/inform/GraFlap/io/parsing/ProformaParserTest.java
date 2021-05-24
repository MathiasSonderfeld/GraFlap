package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.OperationMode;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ProformaParserTest {
    ProformaParser proformaParser = new ProformaParser();

    @Test
    void testSuccess(){
        Arguments arguments = new Arguments();
        arguments.setTaskTitle("LoncapaParserTest OK");
        arguments.setUserLanguage("de");
        arguments.setMode("gg");
        arguments.setOperationMode(OperationMode.GG);
        arguments.setArgtype("cfg");
        arguments.setSolution("ThisIsGiven");
        arguments.setNumberOfWords(0);
        arguments.setWordString("-");
        arguments.setStudentAnswer("ThisShouldBeTheStudentAnswer");

        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(ParserTestUtils.compareTo(arguments,
                proformaParser.parse(ParserTestUtils.getProformaInput(arguments)))));
    }

    @Test
    void testSetsSuccess() throws GraFlapException {
        Arguments arguments = new Arguments();
        arguments.setTaskTitle("LoncapaParserTest OK");
        arguments.setUserLanguage("de");
        arguments.setMode("ggp");
        arguments.setOperationMode(OperationMode.GG);
        arguments.setArgtype("cfg");
        arguments.setSolution("ThisIsGiven");
        arguments.setNumberOfWords(0);
        arguments.setWordString("-");
        arguments.setStudentAnswer("ThisShouldBeTheStudentAnswer");
        arguments.setTransitions("Transitions");
        arguments.setStackalphabet("StackAlphabet");
        arguments.setInitials("Initials");
        arguments.setFinals("Finals");
        arguments.setStates("States");
        arguments.setAlphabet("Alphabet");

        Assertions.assertDoesNotThrow(() -> Assertions.assertTrue(ParserTestUtils.compareTo(arguments,
                proformaParser.parse(ParserTestUtils.getProformaInputWithSets(arguments)))));
    }

    @Test
    void testNull(){
        Arguments arguments = new Arguments();
        arguments.setTaskTitle(null);
        arguments.setUserLanguage(null);
        arguments.setMode(null);
        arguments.setOperationMode(null);
        arguments.setArgtype(null);
        arguments.setSolution(null);
        arguments.setNumberOfWords(0);
        arguments.setWordString("-");
        arguments.setStudentAnswer(null);

        Assertions.assertThrows(IllegalArgumentException.class, () -> proformaParser.parse(ParserTestUtils.getProformaInput(arguments)));
    }
}
