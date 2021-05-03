package de.HsH.inform.GraFlap.entity;

import de.HsH.inform.GraFlap.exception.GraFlapException;

import java.util.HashSet;

/**
 * helper class for the main method to extract the required attributes from the program arguments
 * @author Benjamin Held (04-17-2016)
 * @since 07-03-2016
 * @version 0.1.3
 */
public class Arguments {
    public void tueDinge(){

    }
    /**
     * the name of the task
     */
    private String taskTitle;
    /**
     * shortcut the reading language the user of the submission uses in his browser, e.g. de, en, ...
     */
    private String userLanguage;
    /**
     * the solution to the task
     */
    private String solution;
    /**
     * mode information to determine the correct task type
     */
    private String mode;
    /**
     * the argument type;
     */
    private String agtype;
    /**
     * a string with coded test words
     */
    private String wordString;
    /**
     * the number of requested test words
     */
    private int numberOfWords;
    /**
     * the submission
     */
    private String studentAnswer;


    private static final HashSet<InputMode> automatonModes = new HashSet<>();
    private static final HashSet<InputMode> grammarModes = new HashSet<>();
    private static final HashSet<InputMode> machineModes = new HashSet<>();
    private static final HashSet<InputType> automatonTypes = new HashSet<>();
    private static final HashSet<InputType> grammarTypes = new HashSet<>();
    private static final HashSet<InputType> machineTypes = new HashSet<>();

    private static void initHashSets(){
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
    }


    /**
     * Constructor
     * @param arguments the argument string array with the input
     * @throws GraFlapException throws a {@link GraFlapException} if the array does not have enough arguments
     */
    public Arguments( String[] arguments) throws GraFlapException {
        Arguments.initHashSets();
        try {
            String taskArgumentString = arguments[0];
            studentAnswer = arguments[1];
            String[] taskArguments = taskArgumentString.split("#");

            setTaskTitle(taskArguments[0]);
            setUserLanguage(taskArguments[1]);
            setSolution(taskArguments[2]);
            setMode(taskArguments[3]);
            setAgtype(taskArguments[4]);
            parseAndCheckNumberOfWords(taskArguments[5]);
            setWordString(taskArguments[6]);
            checkCorrectModeAndType();
            checkInputWords();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new GraFlapException("ERROR - Error in LON-CAPA problem. JFlap did not get enough parameters. Please check.");

        }
    }

    /**
     * method to parse the provided number of test words and check for a valid number
     * @param argument the string holding the number of test words
     * @throws GraFlapException throws a {@link GraFlapException} if the parsing fails or the number is not valid
     */
    private void parseAndCheckNumberOfWords(String argument) throws GraFlapException {
        try {
            numberOfWords = Integer.parseInt(argument);
        } catch (Exception e) {
            throw new GraFlapException("ERROR - Error in LON-CAPA problem. The number of test words could not be parsed. Please check.");
        }
        if (numberOfWords < 0) {
            throw new GraFlapException("ERROR - Error in LON-CAPA problem. The number of test is < 0. Please check.");
        }
    }

    /**
     * method to check if test words are provided and if the number of provided words in the word string match the
     * specified number of input words
     * @throws GraFlapException throws a {@link GraFlapException} if the number of words does not match the number of
     * words in the word string
     */
    private void checkInputWords() throws GraFlapException {
        if (!wordString.equals("-")) {
            String[] tmp = wordString.split("!");
            int wordNumber = 0;
            for (String words: tmp) {
                wordNumber += words.split("%").length;
            }
            if (numberOfWords != wordNumber) {
                throw new GraFlapException("ERROR - Error in LON-CAPA problem. The number of test words is not equal to the number of provided words. Please check.");
            }
        }
    }

    private void checkCorrectModeAndType() throws GraFlapException {
        InputMode m = InputMode.valueOf(this.mode);
        InputType t = InputType.valueOf(this.agtype);

        // Check if Mode is legit
        //TODO move to Setter
        //BS - TRY Catch!
        if(m == null) throw new GraFlapException("Mode-Setting is wrong");

        // Check if Type is legit
        //TODO move to Setter
        if(t == null) throw new GraFlapException("AgType-Setting is wrong");

        if(automatonModes.contains(m)){
            if(!automatonTypes.contains(t)){
                throw new GraFlapException("Wrong Type for Automaton Task");
            }
        }
        if(machineModes.contains(m)){
            if(!machineTypes.contains(t)){
                throw new GraFlapException("Wrong Type for Automata Task");
            }
        }

        if(grammarModes.contains(m)){
            if(!grammarTypes.contains(t)){
                throw new GraFlapException("Wrong Type for Gramma Task");
            }
        }

    }


    /**
     * simple getter
     * @return the title of the task
     */
    public String getTaskTitle() {
        return taskTitle;
    }

    /**
     * simple getter
     * @return the coded language that the user of the submission uses
     */
    public String getUserLanguage() {
        return userLanguage;
    }

    /**
     * simple getter
     * @return the solution to the task
     */
    public String getSolution() {
        return solution;
    }

    /**
     * simple getter
     * @return the coded mode information
     */
    public String getMode() {
        return mode;
    }

    /**
     * simple getter
     * @return the ag type
     */
    public String getAgtype() {
        return agtype;
    }

    /**
     * simple getter
     * @return the coded test words
     */
    public String getWordString() {
        return wordString;
    }

    /**
     * simple getter
     * @return the number of requested test words
     */
    public int getNumberOfWords() {
        return numberOfWords;
    }

    /**
     * simple getter
     * @return the coded submission
     */
    public String getStudentAnswer() {
        return studentAnswer;
    }

    /*
     * private Setters
     */

    private void setTaskTitle(String taskTitle){
        this.taskTitle = taskTitle;
    }

    private void setUserLanguage(String userLanguage){
        this.userLanguage = userLanguage;
    }

    private void setSolution(String solution){
        this.solution = solution;
    }

    private void setMode(String mode){
        this.mode = mode;
    }

    private void setAgtype(String agtype){
        this.agtype = agtype;
    }

    private void setWordString(String wordString){
        this.wordString = wordString;
    }
}
