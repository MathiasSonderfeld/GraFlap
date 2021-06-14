package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.InputMode;
import de.HsH.inform.GraFlap.entity.InputType;
import de.HsH.inform.GraFlap.entity.TaskMode;

/**
 * @author Mathias Sonderfeld
 * @version 0.5
 *
 *  Abstract class to parse input data to Arguments Object for further processing.
 */
public abstract class ArgumentsParser {

    public abstract Arguments parse(String[] args) throws GraFlapException;

    /**
     * method to parse the provided number of test words and check for a valid number
     * @param numberOfWordsAsString the string holding the number of test words
     * @throws GraFlapException if the parsing fails or the number is below 0 and so invalid
     */
    public int parseAndCheckNumberOfWords( String numberOfWordsAsString ) throws GraFlapException {
        int numberOfWords = -1;
        try {
            numberOfWords = Integer.parseInt(numberOfWordsAsString);
        }catch(Exception e) {
            throw new GraFlapException("ERROR - The number of test words could not be parsed. Please check.");
        }
        if(numberOfWords < 0) {
            throw new GraFlapException("ERROR - The number of test words is < 0. Please check.");
        }
        return numberOfWords;
    }

    /**
     * method to check if test words are provided and if the number of provided words in the word string match the specified number of input words
     * @throws GraFlapException if the number of words does not match the number of words in the word string
     */
    public void checkInputWords( int numberOfWords, String wordString ) throws GraFlapException {
        boolean ok = false;
        if(wordString != null) {
            if(wordString.equals("-")) {
                if(numberOfWords == 0) {
                    ok = true;
                }
            }
            else {
                String[] tmp = wordString.split("!");
                int wordNumber = 0;
                for(String words : tmp) {
                    wordNumber += words.split("%").length;
                }
                if(numberOfWords == wordNumber) {
                    ok = true;
                }
            }
        }
        if(!ok) {
            throw new GraFlapException("ERROR - The number of test words is not equal to the number of provided words. Please check.");
        }
    }

    /**
     * checks that the given mode and type match the operationModes
     * @param mode the mode to verify
     * @param type the type to verify
     * @throws GraFlapException if mismatch is found
     */
    public void checkCorrectModeAndType( InputMode mode, InputType type ) throws GraFlapException {
        if(mode == null) { throw new GraFlapException("Mode-Setting is wrong"); }

        if(type == null) { throw new GraFlapException("AgType-Setting is wrong"); }

        switch(mode){
            //Automaton Modes
            case ar:
            case ag:
            case art:
            case agt:
            case eat:
            case arw:
            case agw:
            case artw:
            case agtw:
                switch(type){
                    //Automaton Types
                    case fa:
                    case dfa:
                    case nfa:
                    case non:
                    case pda:
                    case dpda:
                    case npda:
                    case tm:
                    case dtm:
                    case ntm:
                    case tmww:
                        return;
                    default:
                        throw new GraFlapException("Wrong Type for Automaton Task");
                }
            //Grammar Modes
            case egt:
            case gg:
            case ggt:
            case ggw:
            case ggtw:
            case gr:
            case grt:
            case grw:
            case grtw:
                switch(type){
                    //Grammar Types
                    case rl:
                    case rlcfg:
                    case cfg:
                    case ncfg:
                    case non:
                    case gint:
                        return;
                    default:
                        throw new GraFlapException("Wrong Type for Grammar Task");
                }
            //Machine Modes
            case mp:
            case mmw:
                switch(type){
                    case tm:
                    case dtm:
                    case ntm:
                    case mealy:
                    case moore:
                        return;
                    default:
                        throw new GraFlapException("Wrong Type for Machine Task");
                }
            default:
                return;
        }
    }

    /**
     * method to determine the operation mode based on the content of the mode string
     * @param mode the string containing the mode
     * @return the corresponding operation mode
     */
    public TaskMode determineOperationMode( String mode ) {
        if(mode == null) return TaskMode.ERROR;
        switch(mode) {
            case ( "ar" ):
                return TaskMode.AR;
            case ( "art" ):
                return TaskMode.ART;
            case ( "ag" ):
            case ( "agt" ):
                return TaskMode.AG;
            case ( "gg" ):
            case ( "ggt" ):
                return TaskMode.GG;
            case ( "arw" ):
            case ( "artw" ):
                return TaskMode.ARW;
            case ( "agw" ):
            case ( "agtw" ):
                return TaskMode.AGW;
            case ( "ggw" ):
            case ( "ggtw" ):
                return TaskMode.GGW;
            case ( "eat" ):
            case ( "egt" ):
                return TaskMode.EAT;
            case ( "ww" ):
                return TaskMode.WW;
            case ( "gr" ):
            case ( "grt" ):
                return TaskMode.GR;
            case ( "grw" ):
            case ( "grtw" ):
                return TaskMode.GRW;
            case ( "mp" ):
                return TaskMode.MP;
            case ( "mmw" ):
                return TaskMode.MMW;
            case ( "cyk" ):
                return TaskMode.CYK;
            case ( "der" ):
                return TaskMode.DER;
            case ( "svgg" ):
                return TaskMode.SVGG;
            case ( "svga" ):
                return TaskMode.SVGA;
            default:
                return TaskMode.ERROR;
        }
    }
}
