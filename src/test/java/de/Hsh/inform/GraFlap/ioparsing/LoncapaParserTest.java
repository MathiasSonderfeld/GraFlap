package de.HsH.inform.GraFlap.ioparsing;

import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.OperationMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoncapaParserTest {
    LoncapaParser loncapaParser = new LoncapaParser();

    @Test
    void testFine(){
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
                loncapaParser.parse(ParserTestUtils.getLoncapaInput(arguments)))));
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

        Assertions.assertThrows(IllegalArgumentException.class, () -> loncapaParser.parse(ParserTestUtils.getLoncapaInput(arguments)));
    }
}
