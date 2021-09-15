package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.GraFlapBlackBoxTest;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.TaskMode;
import de.HsH.inform.GraFlap.entity.TaskType;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;


public class ProformaParserTest {
    ProformaParser proformaParser = new ProformaParser();

    @Test
    void testSuccess(){
        Arguments arguments = new Arguments();
        arguments.setTestId("graflap");
        arguments.setTaskTitle("LoncapaParserTest OK");
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setTaskMode(TaskMode.GG);
        arguments.setTaskType(TaskType.CFG);
        arguments.setSolution("ThisIsGiven");
        arguments.setNumberOfWords(0);
        arguments.setTestwords(GraFlapBlackBoxTest.emptyTestwords);
        arguments.setStudentAnswer("ThisShouldBeTheStudentAnswer");
        ArgumentsToInputConverter argumentsToInputConverter = new ArgumentsToInputConverter(arguments);
        String[] generatedInput = argumentsToInputConverter.getProformaInput();
        Arguments parsed = Assertions.assertDoesNotThrow(() -> proformaParser.parse(generatedInput));
        Assertions.assertEquals(arguments, parsed);
    }

    @Test
    void testSetsSuccess() throws GraFlapException {
        Arguments arguments = new Arguments();
        arguments.setTestId("graflap");
        arguments.setTaskTitle("LoncapaParserTest OK");
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setTaskMode(TaskMode.AGP);
        arguments.setTaskType(TaskType.DFA);
        arguments.setSolution("ThisIsGiven");
        arguments.setNumberOfWords(0);
        arguments.setTestwords(GraFlapBlackBoxTest.emptyTestwords);
        arguments.setStudentAnswer("ThisShouldBeTheStudentAnswer");
        arguments.setTransitions("Transitions");
        arguments.setInitials("Initials");
        arguments.setFinals("Finals");
        arguments.setStates("States");
        arguments.setAlphabet("Alphabet");
        ArgumentsToInputConverter argumentsToInputConverter = new ArgumentsToInputConverter(arguments);
        String[] generatedInput = argumentsToInputConverter.getProformaInputWithSets();
        Arguments parsed = Assertions.assertDoesNotThrow(() -> proformaParser.parse(generatedInput));
        Assertions.assertEquals(arguments, parsed);
    }

    @Test
    void testNull(){
        Arguments arguments = new Arguments();
        arguments.setTaskTitle(null);
        arguments.setUserLanguage(null);
        arguments.setTaskMode(TaskMode.AG);
        arguments.setTaskType(TaskType.NON);
        arguments.setSolution(null);
        arguments.setNumberOfWords(0);
        arguments.setTestwords(GraFlapBlackBoxTest.emptyTestwords);
        arguments.setStudentAnswer(null);
        ArgumentsToInputConverter argumentsToInputConverter = new ArgumentsToInputConverter(arguments);
        Assertions.assertDoesNotThrow(() -> proformaParser.parse(argumentsToInputConverter.getProformaInput()));
    }
}
