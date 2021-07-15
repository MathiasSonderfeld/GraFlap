package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.TaskMode;
import de.HsH.inform.GraFlap.entity.TaskType;
import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        argumentsParser = new ArgumentsParser(){
            @Override
            public Arguments parse( String[] args ) throws GraFlapException {
                return null;
            }
        };
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
        Assertions.assertDoesNotThrow(() -> argumentsParser.checkCorrectModeAndType(TaskMode.ART, TaskType.DFA));
    }

    @Test
    void checkCorrectModeAndTypeGrammar(){
        Assertions.assertDoesNotThrow(() -> argumentsParser.checkCorrectModeAndType(TaskMode.GG, TaskType.CFG));
    }

    @Test
    void checkCorrectModeAndTypeMachine(){
        Assertions.assertDoesNotThrow(() -> argumentsParser.checkCorrectModeAndType(TaskMode.MP, TaskType.MOORE));
    }

    @Test
    void checkCorrectModeAndTypeAutomatonWrongType(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.checkCorrectModeAndType(TaskMode.ART, TaskType.MOORE));
    }

    @Test
    void checkCorrectModeAndTypeGrammarWrongType(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.checkCorrectModeAndType(TaskMode.GG, TaskType.MOORE));
    }

    @Test
    void checkCorrectModeAndTypeMachineWrongType(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.checkCorrectModeAndType(TaskMode.MP, TaskType.CFG));
    }

    @Test
    void checkCorrectModeAndTypeMachineTypeNull(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.checkCorrectModeAndType(TaskMode.MP, null));
    }

    @Test
    void checkCorrectModeAndTypeModeNull(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.checkCorrectModeAndType(null, null));
    }
}
