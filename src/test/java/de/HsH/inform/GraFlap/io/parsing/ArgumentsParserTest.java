package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.BlackBoxTests.BlackBoxTestTestwordsUtil;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Mode;
import de.HsH.inform.GraFlap.entity.Type;
import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Locale;

public class ArgumentsParserTest {
    ArgumentsParser argumentsParser;

    static String buildToooLongWords(int okWordCount, int tooLongWordCount){
        String longWord ="LoremipsumdolorsitametconsetetursadipscingelitrseddiamnonumyeirmodtemporinviduntutlaboreetdoloremagnaaliquyameratseddiamvoluptuaAtveroeosetaccusametjustoduodoloresetearebumStetclitakasdgubergrennoseatakimatasanctusestLoremipsumdolorsitametLoremipsumdolorsitametconsetetursadipscingelitrseddiamnonumyeirmodtemporinviduntutlaboreetdoloremagnaaliquyameratseddiamvoluptuaAtveroeosetaccusametjustoduodoloresetearebumStetclitakasdgubergrennoseatakimatasanctusestLoremipsumdolorsitamet";
        String okWord = "buffer";
        StringBuilder sb = new StringBuilder(), half = new StringBuilder();
        for(int i = 0; i < okWordCount-1; i++) {
            half.append(okWord).append("%");
        }
        half.append(okWord);
        for(int i = 0; i < tooLongWordCount; i++) {
            half.append("%").append(longWord);
        }
        sb.append(half).append("!").append(half);
        return sb.toString();
    }

    @BeforeEach
    void init(){
        argumentsParser = new ArgumentsParser(){};
    }


    @Test
    void parseNumberOfWordsTestMinusOne(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.parseAndCheckNumberOfWords("-1"));
    }

    @Test
    void parseNumberOfWordsTestZero(){
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(0, argumentsParser.parseAndCheckNumberOfWords("0")));
    }

    @Test
    void parseNumberOfWordsTestFive(){
        Assertions.assertDoesNotThrow(() -> Assertions.assertEquals(5, argumentsParser.parseAndCheckNumberOfWords("5")));
    }

    @Test
    void parseNumberOfWordsTestFloat(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.parseAndCheckNumberOfWords("1.4"));
    }

    @Test
    void parseNumberOfWordsTestNull(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.parseAndCheckNumberOfWords(null));
    }


    @Test
    void parseInputWordsExceptionNull(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.parseInputWords(0, null));
    }

    @Test
    void parseInputWordsEmpty(){
        Assertions.assertDoesNotThrow( () -> argumentsParser.parseInputWords(1, "-"));
    }

    @Test
    void parseInputWordsSuccessEmpty(){
        Assertions.assertDoesNotThrow(() -> argumentsParser.parseInputWords(0, "-"));
        Assertions.assertFalse(argumentsParser.isFilterWarning());
    }

    @Test
    void parseInputWordsOneWord(){
        Assertions.assertDoesNotThrow(() -> argumentsParser.parseInputWords(1, "OneTwo"));
    }

    @Test
    void parseInputWordsExceptionMisMatch(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.parseInputWords(3, "One!Two"));
    }

    @Test
    void parseInputWordsSuccess(){
        Testwords toCompare = new Testwords(2,2);
        toCompare.addToCorrectWords("One");
        toCompare.addToCorrectWords("Two");
        toCompare.addToFailingWords("three");
        toCompare.addToFailingWords("four");

        Testwords result = Assertions.assertDoesNotThrow(() -> argumentsParser.parseInputWords(4, "One%Two!three%four"));
        Assertions.assertFalse(argumentsParser.isFilterWarning());
        Assertions.assertEquals(toCompare, result);
    }

    @Test
    void parseInputWordsWarning(){
        int expectedWords = 6;
        Testwords toCompare = new Testwords(expectedWords,expectedWords);
        for(int i = 0; i <expectedWords; i++) {
            toCompare.addToCorrectWords("buffer");
            toCompare.addToFailingWords("buffer");
        }
        Testwords result = Assertions.assertDoesNotThrow(() -> argumentsParser.parseInputWords(20, buildToooLongWords(expectedWords,(int) (expectedWords*0.75))));
        Assertions.assertTrue(argumentsParser.isFilterWarning());
        Assertions.assertEquals(toCompare, result);
    }

    @Test
    void parseInputWordsTooFewWords(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.parseInputWords(24, buildToooLongWords(1,11)));
    }


    @Test
    void checkCorrectModeAndTypeAutomaton(){
        Assertions.assertDoesNotThrow(() -> argumentsParser.checkCorrectModeAndType(Mode.ART, Type.DFA));
    }

    @Test
    void checkCorrectModeAndTypeGrammar(){
        Assertions.assertDoesNotThrow(() -> argumentsParser.checkCorrectModeAndType(Mode.GG, Type.CFG));
    }

    @Test
    void checkCorrectModeAndTypeMachine(){
        Assertions.assertDoesNotThrow(() -> argumentsParser.checkCorrectModeAndType(Mode.MP, Type.MOORE));
    }

    @Test
    void checkCorrectModeAndTypeAutomatonWrongType(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.checkCorrectModeAndType(Mode.ART, Type.MOORE));
    }

    @Test
    void checkCorrectModeAndTypeGrammarWrongType(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.checkCorrectModeAndType(Mode.GG, Type.MOORE));
    }

    @Test
    void checkCorrectModeAndTypeMachineWrongType(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.checkCorrectModeAndType(Mode.MP, Type.CFG));
    }

    @Test
    void checkCorrectModeAndTypeMachineTypeNull(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.checkCorrectModeAndType(Mode.MP, null));
    }

    @Test
    void checkCorrectModeAndTypeModeNull(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.checkCorrectModeAndType(null, null));
    }

    @Test
    void testArgumentsToInputConverter(){
        String studentAnswer = "ThisShouldBeTheStudentAnswer";
        Arguments arguments = new Arguments();
        arguments.setTestId("graflap");
        arguments.setTaskTitle("LoncapaArgsToInputConverter");
        arguments.setUserLanguage(Locale.GERMAN);
        arguments.setMode(Mode.GG);
        arguments.setType(Type.CFG);
        arguments.setSolution("ThisIsGiven");
        arguments.setNumberOfWords(0);
        arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);
        arguments.setStudentAnswer(studentAnswer);
        ArgumentsToInputConverter argumentsToInputConverter = new ArgumentsToInputConverter(arguments);
        String[] loncapaInput = {"LoncapaArgsToInputConverter#de#ThisIsGiven#GG#CFG#0#-", studentAnswer};

        Assertions.assertTrue(Arrays.equals(loncapaInput, argumentsToInputConverter.getLoncapaInput()));
    }

    @Nested
    class ArgumentsParserNestedTest{
        @Test
        void testParseCorrect(){
            Arguments arguments = new Arguments();
            arguments.setTestId("");
            arguments.setTaskTitle("ArgumentsParserTest parse OK");
            arguments.setUserLanguage(Locale.GERMAN);
            arguments.setMode(Mode.GG);
            arguments.setType(Type.CFG);
            arguments.setSolution("ThisIsGiven");
            arguments.setNumberOfWords(0);
            arguments.setTestwords(BlackBoxTestTestwordsUtil.emptyTestwords);

            ArgumentsToInputConverter argumentsToInputConverter = new ArgumentsToInputConverter(arguments);
            String[] generatedInput = {argumentsToInputConverter.getBKP(), ""};
            Arguments parsed = Assertions.assertDoesNotThrow(() -> argumentsParser.parse(generatedInput));
            Assertions.assertEquals(arguments, parsed);
        }

        @Test
        void testParseNull(){
            Arguments arguments = new Arguments();
            arguments.setTestId(null);
            arguments.setTaskTitle(null);
            arguments.setUserLanguage(null);
            arguments.setMode(null);
            arguments.setType(null);
            arguments.setSolution(null);
            arguments.setNumberOfWords(0);
            arguments.setTestwords(null);
            arguments.setStudentAnswer(null);

            ArgumentsToInputConverter argumentsToInputConverter = new ArgumentsToInputConverter(arguments);
            String[] generatedInput = {argumentsToInputConverter.getBKP(), ""};
            Assertions.assertDoesNotThrow(() -> argumentsParser.parse(generatedInput));
        }
    }
}
