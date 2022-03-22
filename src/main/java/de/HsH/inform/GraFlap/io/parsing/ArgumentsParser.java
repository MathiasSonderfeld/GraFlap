package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.entity.*;
import de.HsH.inform.GraFlap.exception.GraFlapException;

import java.util.Locale;

/**
 *  Abstract class to parse input data to Arguments Object for further processing.
 * @author Mathias Sonderfeld
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public abstract class ArgumentsParser {
    private static final int wordLengthLimit = 250;
    private static final double warningThreshhold = 0.8;
    private static final double abortThreshhold = 0.1;

    private int inputWordsAmount = 0;
    private int filterApprovedWordsAmount = 0;
    private boolean filterWarning = false;

    public Arguments parse(String[] args) throws GraFlapException{
        Arguments arguments = new Arguments();
        String[] taskArguments = args[0].split("#");
        if(taskArguments.length < 7) throw new GraFlapException("less than 7 graflap-arguments, cant parse task");

        arguments.setTaskTitle(taskArguments[0]);
        arguments.setUserLanguage(Locale.forLanguageTag((taskArguments[1])));
        arguments.setReference(taskArguments[2]);
        int numberOfWords = parseAndCheckNumberOfWords(taskArguments[5]);
        arguments.setNumberOfWords(numberOfWords);

        Mode mode;
        try{
            mode = Mode.valueOf(taskArguments[3].toUpperCase());
        }
        catch(Exception e){
            mode = Mode.ERROR;
        }

        Type type;
        try{
            type = Type.valueOf(taskArguments[4].toUpperCase());
        }
        catch(Exception e){
            type = Type.ERROR;
        }

        checkCorrectModeAndType(mode, type);
        arguments.setMode(mode);
        arguments.setType(type);
        arguments.setTestwords(parseInputWords(mode, numberOfWords, taskArguments[6]));
        return arguments;
    }

    /**
     * method to parse the provided number of test words and check for a valid number
     * @param numberOfWordsAsString the string holding the number of test words
     * @throws GraFlapException if the parsing fails or the number is below 0 and so invalid
     */
    protected int parseAndCheckNumberOfWords( String numberOfWordsAsString ) throws GraFlapException {
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
    protected Testwords parseInputWords(Mode mode, int numberOfWords, String wordString ) throws GraFlapException {
        if(wordString == null) throw new GraFlapException("WordString must not be null");
        if(mode == Mode.ERROR) return null;
        Testwords testwords = new Testwords();
        if(wordString.equals("-")) return testwords;

        // Single Word
        if(mode == Mode.DER || mode == Mode.CYK){
            this.inputWordsAmount = 1;
            if(wordString.length() < wordLengthLimit){
                this.filterApprovedWordsAmount++;
                testwords.setSingleWord(wordString);
            }
        }

        //word pairs
        else if(mode == Mode.MP){
            String[] wordPairsArray = wordString.split("%");
            String[] pairArray;
            this.inputWordsAmount = wordPairsArray.length;
            for(int pair = 0; pair < wordPairsArray.length; pair++){
                pairArray = wordPairsArray[pair].split(";");
                if(pairArray.length < 2) throw new GraFlapException("Illegal Testwords Format");
                if(pairArray[0].length() < wordLengthLimit || pairArray[1].length() < wordLengthLimit){
                    filterApprovedWordsAmount++;
                    testwords.addToWordPairs(pairArray[0], pairArray[1]);
                }
            }
        }

        //single list
        else if(mode == Mode.MMW){
            String[] testWordsArray = wordString.split("%");
            this.inputWordsAmount = testWordsArray.length;
            for(String word : testWordsArray){
                if(word.length() < wordLengthLimit){
                    testwords.addToTestWordsList(word);
                    this.filterApprovedWordsAmount++;
                }
            }
        }

        //Word Lists
        else{
            String[] wordsSplitByCategory = wordString.split("!");
            if(wordsSplitByCategory.length < 2) throw new GraFlapException("Illegal Testwords Format");
            String[] correctWordsArray = wordsSplitByCategory[0].split("%");
            String[] failingWordsArray = wordsSplitByCategory[1].split("%");

            int correctWordsAmount = correctWordsArray.length;
            int failingWordsAmount = failingWordsArray.length;
            this.inputWordsAmount = correctWordsAmount + failingWordsAmount;

            for(String word : correctWordsArray){
                if(word.length() < wordLengthLimit){
                    testwords.addToCorrectWords(word);
                    this.filterApprovedWordsAmount++;
                }
            }
            for(String word : failingWordsArray){
                if(word.length() < wordLengthLimit){
                    testwords.addToFailingWords(word);
                    this.filterApprovedWordsAmount++;
                }
            }
        }
        if(this.inputWordsAmount != numberOfWords) throw new GraFlapException("NumberOfWords and WordString Mismatch");
        double filteredPercentage = this.filterApprovedWordsAmount / ((double) this.inputWordsAmount);
        if(filteredPercentage < warningThreshhold) this.filterWarning = true;
        if(filteredPercentage < abortThreshhold) throw new GraFlapException("more than " + Math.round((1-abortThreshhold)*100) + "% of the words were filtered because they were too long. Grading aborted.");
        return testwords;
    }

    /**
     * checks that the given mode and type match the operationModes
     * @param mode the mode to verify
     * @param type the type to verify
     * @throws GraFlapException if mismatch is found
     */
    protected void checkCorrectModeAndType(Mode mode, Type type ) throws GraFlapException {
        if(mode == null) { throw new GraFlapException("Mode-Setting is wrong"); }
        if(type == null) { throw new GraFlapException("Type-Setting is wrong"); }

        switch(mode){
            //Automaton Modes
            case AA:
            case AR:
            case ART:
            case AG:
            case AGT:
            case ARW:
            case AGW:
            case ARTW:
            case AGTW:
            case EAT:
            case ARP:
            case ARTP:
            case AGP:
            case AGTP:
            case ARWP:
            case AGWP:
            case ARTWP:
            case AGTWP:
                switch(type){
                    //Automaton Types
                    case FA:
                    case DFA:
                    case NFA:
                    case NON:
                    case PDA:
                    case DPDA:
                    case NPDA:
                    case TM:
                    case DTM:
                    case NTM:
                    case TMWW:
                        return;
                    default:
                        throw new GraFlapException("Wrong Type for Automaton Task");
                }
            //Grammar Modes
            case EGT:
            case GG:
            case GGT:
            case GGW:
            case GGTW:
            case GR:
            case GRT:
            case GRW:
            case GRTW:
                switch(type){
                    //Grammar Types
                    case RL:
                    case RLCFG:
                    case CFG:
                    case NCFG:
                    case NON:
                    case GINT:
                        return;
                    default:
                        throw new GraFlapException("Wrong Type for Grammar Task");
                }
            //Machine Modes
            case MP:
            case MMW:
                switch(type){
                    case TM:
                    case DTM:
                    case NTM:
                    case MEALY:
                    case MOORE:
                        return;
                    default:
                        throw new GraFlapException("Wrong Type for Machine Task");
                }
            default:
                return;
        }
    }

    public boolean isFilterWarning() {
        return filterWarning;
    }

    public int getInputWordsAmount(){
        return inputWordsAmount;
    }

    public int getFilterApprovedWordsAmount(){
        return filterApprovedWordsAmount;
    }
}
