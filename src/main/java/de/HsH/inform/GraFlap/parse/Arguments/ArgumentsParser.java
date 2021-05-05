package de.HsH.inform.GraFlap.parse.Arguments;

import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.InputMode;
import de.HsH.inform.GraFlap.entity.InputType;
import de.HsH.inform.GraFlap.entity.OperationMode;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.function.Function;

/**
 * @author Mathias Sonderfeld
 * @version 0.5
 *
 *  Abstract class to parse input data to Arguments Object for further processing.
 */
public abstract class ArgumentsParser {
    private static final HashSet<InputMode> automatonModes = new HashSet<>();
    private static final HashSet<InputMode> grammarModes = new HashSet<>();
    private static final HashSet<InputMode> machineModes = new HashSet<>();
    private static final HashSet<InputType> automatonTypes = new HashSet<>();
    private static final HashSet<InputType> grammarTypes = new HashSet<>();
    private static final HashSet<InputType> machineTypes = new HashSet<>();
    private static boolean setsMade = false;

    ArgumentsParser(){
        initHashSets();
    }

    public abstract Arguments parse(String[] args) throws GraFlapException;

    /**
     * method to parse the provided number of test words and check for a valid number
     * @param numberOfWordsAsString the string holding the number of test words
     * @throws GraFlapException if the parsing fails or the number is below 0 and so invalid
     */
    int parseAndCheckNumberOfWords( String numberOfWordsAsString ) throws GraFlapException {
        int numberOfWords = -1;
        try {
            numberOfWords = Integer.parseInt(numberOfWordsAsString);
        }catch(Exception e) {
            throw new GraFlapException("ERROR - Error in LON-CAPA problem. The number of test words could not be parsed. Please check.");
        }
        if(numberOfWords < 0) {
            throw new GraFlapException("ERROR - Error in LON-CAPA problem. The number of test words is < 0. Please " +
                    "check.");
        }
        return numberOfWords;
    }

    /**
     * method to check if test words are provided and if the number of provided words in the word string match the specified number of input words
     * @throws GraFlapException if the number of words does not match the number of words in the word string
     */
    void checkInputWords( int numberOfWords, String wordString ) throws GraFlapException {
        boolean ok = true;
        if(wordString.equals("-")) {
            if(numberOfWords != 0) {
                ok = false;
            }
        }
        else {
            String[] tmp = wordString.split("!");
            int wordNumber = 0;
            for(String words : tmp) {
                wordNumber += words.split("%").length;
            }
            if(numberOfWords != wordNumber) {
                ok = false;
            }
        }

        if(!ok) {
            throw new GraFlapException("ERROR - Error in LON-CAPA problem. The number of test words is not equal to the number of provided words. Please check.");
        }
    }

    /**
     * checks that the given mode and type match the operationModes
     * @param mode the mode to verify
     * @param type the type to verify
     * @throws GraFlapException if mismatch is found
     */
    void checkCorrectModeAndType( InputMode mode, InputType type ) throws GraFlapException {
        if(mode == null) { throw new GraFlapException("Mode-Setting is wrong"); }

        if(type == null) { throw new GraFlapException("AgType-Setting is wrong"); }

        if(automatonModes.contains(mode)) {
            if(!automatonTypes.contains(type)) {
                throw new GraFlapException("Wrong Type for Automaton Task");
            }
        }
        if(machineModes.contains(mode)) {
            if(!machineTypes.contains(type)) {
                throw new GraFlapException("Wrong Type for Automata Task");
            }
        }

        if(grammarModes.contains(mode)) {
            if(!grammarTypes.contains(type)) {
                throw new GraFlapException("Wrong Type for Gramma Task");
            }
        }
    }

    /**
     * method to determine the operation mode based on the content of the mode string
     * @param mode the string containing the mode
     * @return the corresponding operation mode
     */
    OperationMode determineOperationMode( String mode ) {
        switch(mode) {
            case ( "ar" ):
                return OperationMode.AR;
            case ( "art" ):
                return OperationMode.AR;
            case ( "ag" ):
                return OperationMode.AG;
            case ( "agt" ):
                return OperationMode.AG;
            case ( "gg" ):
                return OperationMode.GG;
            case ( "ggt" ):
                return OperationMode.GG;
            case ( "arw" ):
                return OperationMode.ARW;
            case ( "artw" ):
                return OperationMode.ARW;
            case ( "agw" ):
                return OperationMode.AGW;
            case ( "agtw" ):
                return OperationMode.AGW;
            case ( "ggw" ):
                return OperationMode.GGW;
            case ( "ggtw" ):
                return OperationMode.GGW;
            case ( "eat" ):
                return OperationMode.EAT;
            case ( "egt" ):
                return OperationMode.EAT;
            case ( "ww" ):
                return OperationMode.WW;
            case ( "gr" ):
                return OperationMode.GR;
            case ( "grt" ):
                return OperationMode.GR;
            case ( "grw" ):
                return OperationMode.GRW;
            case ( "grtw" ):
                return OperationMode.GRW;
            case ( "mp" ):
                return OperationMode.MP;
            case ( "mmw" ):
                return OperationMode.MMW;
            case ( "cyk" ):
                return OperationMode.CYK;
            case ( "der" ):
                return OperationMode.DER;
            case ( "svgg" ):
                return OperationMode.SVGG;
            case ( "svga" ):
                return OperationMode.SVGA;
            default:
                return OperationMode.ERROR;
        }
    }

    /**
     * saves the accepted modes and types for automatons, grammars and turing machines in corresponsing attributes for input verification.
     * Kinda overkill for this as the program just processes one input per runtime, could be refactored to switch
     */
    private void initHashSets() {
        if(!setsMade) {
            automatonModes.add(InputMode.ar);
            automatonModes.add(InputMode.ag);
            automatonModes.add(InputMode.art);
            automatonModes.add(InputMode.agt);
            automatonModes.add(InputMode.eat);
            automatonModes.add(InputMode.arw);
            automatonModes.add(InputMode.agw);
            automatonModes.add(InputMode.artw);
            automatonModes.add(InputMode.agtw);

            grammarModes.add(InputMode.egt);
            grammarModes.add(InputMode.gg);
            grammarModes.add(InputMode.ggt);
            grammarModes.add(InputMode.ggw);
            grammarModes.add(InputMode.ggtw);
            grammarModes.add(InputMode.gr);
            grammarModes.add(InputMode.grt);
            grammarModes.add(InputMode.grw);
            grammarModes.add(InputMode.grtw);

            machineModes.add(InputMode.mp);
            machineModes.add(InputMode.mmw);

            automatonTypes.add(InputType.fa);
            automatonTypes.add(InputType.dfa);
            automatonTypes.add(InputType.nfa);
            automatonTypes.add(InputType.non);
            automatonTypes.add(InputType.pda);
            automatonTypes.add(InputType.dpda);
            automatonTypes.add(InputType.npda);
            automatonTypes.add(InputType.tm);
            automatonTypes.add(InputType.dtm);
            automatonTypes.add(InputType.ntm);
            automatonTypes.add(InputType.tmww);

            machineTypes.add(InputType.tm);
            machineTypes.add(InputType.dtm);
            machineTypes.add(InputType.ntm);
            machineTypes.add(InputType.mealy);
            machineTypes.add(InputType.moore);

            grammarTypes.add(InputType.rl);
            grammarTypes.add(InputType.rlcfg);
            grammarTypes.add(InputType.cfg);
            grammarTypes.add(InputType.ncfg);
            grammarTypes.add(InputType.non);
            grammarTypes.add(InputType.gint);
            setsMade = true;
        }
    }
}
