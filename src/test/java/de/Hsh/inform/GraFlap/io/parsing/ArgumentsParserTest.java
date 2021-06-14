package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.InputType;
import de.HsH.inform.GraFlap.entity.TaskMode;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class ArgumentsParserTest {
    ArgumentsParser argumentsParser = new ArgumentsParser(){
        @Override
        public Arguments parse( String[] args ) throws GraFlapException {
            return null;
        }
    };

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
    void checkInputWordsZeroMinus(){
        Assertions.assertDoesNotThrow(() -> argumentsParser.checkInputWords(0, "-"));
    }

    @Test
    void checkInputWordsOneOne(){
        Assertions.assertDoesNotThrow(() -> argumentsParser.checkInputWords(1, "One"));
    }

    @Test
    void checkInputWordsFourWords(){
        Assertions.assertDoesNotThrow(() -> argumentsParser.checkInputWords(4, "One%Two!Three%Four"));
    }

    @Test
    void checkInputWordsMismatch(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.checkInputWords(1, "One%Two!Three%Four"));
    }

    @Test
    void checkInputWordsNull(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.checkInputWords(1, null));
    }


    @Test
    void checkCorrectModeAndTypeAutomaton(){
        Assertions.assertDoesNotThrow(() -> argumentsParser.checkCorrectModeAndType(TaskMode.ART, InputType.dfa));
    }

    @Test
    void checkCorrectModeAndTypeGrammar(){
        Assertions.assertDoesNotThrow(() -> argumentsParser.checkCorrectModeAndType(TaskMode.GG, InputType.cfg));
    }

    @Test
    void checkCorrectModeAndTypeMachine(){
        Assertions.assertDoesNotThrow(() -> argumentsParser.checkCorrectModeAndType(TaskMode.MP, InputType.moore));
    }

    @Test
    void checkCorrectModeAndTypeAutomatonWrongType(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.checkCorrectModeAndType(TaskMode.ART, InputType.moore));
    }

    @Test
    void checkCorrectModeAndTypeGrammarWrongType(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.checkCorrectModeAndType(TaskMode.GG, InputType.moore));
    }

    @Test
    void checkCorrectModeAndTypeMachineWrongType(){
        Assertions.assertThrows(GraFlapException.class, () -> argumentsParser.checkCorrectModeAndType(TaskMode.MP, InputType.cfg));
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
