package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.GraFlapBlackBoxTest;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Mode;
import de.HsH.inform.GraFlap.entity.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class LoncapaParserTest {
    LoncapaParser loncapaParser = new LoncapaParser();

    @Test
    void testFine(){
        Arguments arguments = new Arguments();
        arguments.setTestId("");
        arguments.setTaskTitle("LoncapaParserTest OK");
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setMode(Mode.GG);
        arguments.setType(Type.CFG);
        arguments.setSolution("ThisIsGiven");
        arguments.setNumberOfWords(0);
        arguments.setTestwords(GraFlapBlackBoxTest.emptyTestwords);
        arguments.setStudentAnswer("ThisShouldBeTheStudentAnswer");
        ArgumentsToInputConverter argumentsToInputConverter = new ArgumentsToInputConverter(arguments);
        String[] generatedInput = argumentsToInputConverter.getLoncapaInput();
        Arguments parsed = Assertions.assertDoesNotThrow(() -> loncapaParser.parse(generatedInput));
        Assertions.assertEquals(arguments, parsed);
    }


    @Test
    void testNull(){
        Arguments arguments = new Arguments();
        arguments.setTestId(null);
        arguments.setTaskTitle(null);
        arguments.setUserLanguage(null);
        arguments.setMode(Mode.ERROR);
        arguments.setType(Type.NON);
        arguments.setSolution(null);
        arguments.setNumberOfWords(0);
        arguments.setTestwords(GraFlapBlackBoxTest.emptyTestwords);
        arguments.setStudentAnswer(null);
        ArgumentsToInputConverter argumentsToInputConverter = new ArgumentsToInputConverter(arguments);
        Assertions.assertDoesNotThrow(() -> loncapaParser.parse(argumentsToInputConverter.getLoncapaInput()));
    }
}
