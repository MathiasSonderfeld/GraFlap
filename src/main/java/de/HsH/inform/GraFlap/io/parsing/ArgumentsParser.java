package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.TaskType;
import de.HsH.inform.GraFlap.entity.TaskMode;

import java.util.Locale;

/**
 * @author Mathias Sonderfeld
 * @version 0.5
 *
 *  Abstract class to parse input data to Arguments Object for further processing.
 */
public abstract class ArgumentsParser {
    private static final int wordLengthLimit = 250;
    private static final double warningThreshhold = 0.8;
    private static final double abortThreshhold = 0.1;

    private int correctWordsAmount = 0;
    private int filteredCorrectWordsAmount = 0;
    private int failingWordsAmount = 0;
    private int filteredFailingWordsAmount = 0;
    private boolean filterWarning = false;

    public abstract Arguments parse(String[] args) throws GraFlapException;

    protected Locale getLocale( String in){
        return Locale.forLanguageTag(in);
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
    protected Testwords parseInputWords( int numberOfWords, String wordString ) throws GraFlapException {
        boolean ok = false;
        if(wordString == null) throw new GraFlapException("WordString must not be null");
        if(wordString.equals("-")){
            return new Testwords(0,0);
        }
        String[] wordsSplitByCategory = wordString.split("!");
        String[] correctWordsArray = wordsSplitByCategory[0].split("%");
        String[] failingWordsArray = wordsSplitByCategory[1].split("%");
        this.correctWordsAmount = correctWordsArray.length;
        this.failingWordsAmount = failingWordsArray.length;
        if(this.correctWordsAmount + this.failingWordsAmount != numberOfWords) throw new GraFlapException("NumberOfWords and WordString Mismatch");

        Testwords testwords = new Testwords(this.correctWordsAmount, this.failingWordsAmount);
        for(String word : correctWordsArray){
            if(word.length() < wordLengthLimit){
                testwords.addToCorrectWords(word);
                this.filteredCorrectWordsAmount++;
            }
        }
        for(String word : failingWordsArray){
            if(word.length() < wordLengthLimit){
                testwords.addToFailingWords(word);
                this.filteredFailingWordsAmount++;
            }
        }
        double filteredPercentage = (this.filteredCorrectWordsAmount + this.filteredFailingWordsAmount) / ((double) (this.correctWordsAmount + this.failingWordsAmount));
        if(filteredPercentage < warningThreshhold) this.filterWarning = true;
        if(filteredPercentage < abortThreshhold) throw new GraFlapException("more than "+ Math.round((1-abortThreshhold)*100) +"% of the words were filtered because they were too long. Grading " +
                "aborted.");
        return testwords;
    }

    /**
     * checks that the given mode and type match the operationModes
     * @param mode the mode to verify
     * @param type the type to verify
     * @throws GraFlapException if mismatch is found
     */
    protected void checkCorrectModeAndType( TaskMode mode, TaskType type ) throws GraFlapException {
        if(mode == null) { throw new GraFlapException("Mode-Setting is wrong"); }

        if(type == null) { throw new GraFlapException("AgType-Setting is wrong"); }

        switch(mode){
            //Automaton Modes
            case AR:
            case AG:
            case ART:
            case AGT:
            case EAT:
            case ARW:
            case AGW:
            case ARTW:
            case AGTW:
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

    public int getCorrectWordsAmount() {
        return correctWordsAmount;
    }

    public int getFilteredCorrectWordsAmount() {
        return filteredCorrectWordsAmount;
    }

    public int getFailingWordsAmount() {
        return failingWordsAmount;
    }

    public int getFilteredFailingWordsAmount() {
        return filteredFailingWordsAmount;
    }
}
