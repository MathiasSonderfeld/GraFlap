package de.HsH.inform.GraFlap.loncapa.main;

import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;

import java.util.HashSet;

/**
 * helper class for the main method to extract the required attributes from the program arguments
 * @author Benjamin Held (04-17-2016)
 * @since 07-03-2016
 * @version 0.1.3
 */
public class LoncapaArguments {
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


    private static final HashSet<Mode> AutomatonModes = new HashSet<>();
    private static final HashSet<Mode> GrammarModes = new HashSet<>();
    private static final HashSet<Mode> MachineModes = new HashSet<>();
    private static final HashSet<Type> ATypes = new HashSet<>();
    private static final HashSet<Type> MTypes = new HashSet<>();
    private static final HashSet<Type> GTypes = new HashSet<>();

    private static void initHashSets(){
        AutomatonModes.add(Mode.ar);
        AutomatonModes.add(Mode.ag);
        AutomatonModes.add(Mode.art);
        AutomatonModes.add(Mode.agt);
        AutomatonModes.add(Mode.eat);
        AutomatonModes.add(Mode.arw);
        AutomatonModes.add(Mode.agw);
        AutomatonModes.add(Mode.artw);
        AutomatonModes.add(Mode.agtw);

        GrammarModes.add(Mode.egt);
        GrammarModes.add(Mode.gg);
        GrammarModes.add(Mode.ggt);
        GrammarModes.add(Mode.ggw);
        GrammarModes.add(Mode.ggtw);
        GrammarModes.add(Mode.gr);
        GrammarModes.add(Mode.grt);
        GrammarModes.add(Mode.grw);
        GrammarModes.add(Mode.grtw);

        MachineModes.add(Mode.mp);
        MachineModes.add(Mode.mmw);

        ATypes.add(Type.fa);
        ATypes.add(Type.dfa);
        ATypes.add(Type.nfa);
        ATypes.add(Type.non);
        ATypes.add(Type.pda);
        ATypes.add(Type.dpda);
        ATypes.add(Type.npda);
        ATypes.add(Type.tm);
        ATypes.add(Type.dtm);
        ATypes.add(Type.ntm);
        ATypes.add(Type.tmww);

        MTypes.add(Type.tm);
        MTypes.add(Type.dtm);
        MTypes.add(Type.ntm);
        MTypes.add(Type.mealy);
        MTypes.add(Type.moore);

        GTypes.add(Type.rl);
        GTypes.add(Type.rlcfg);
        GTypes.add(Type.cfg);
        GTypes.add(Type.ncfg);
        GTypes.add(Type.non);
        GTypes.add(Type.gint);
    }


    /**
     * Constructor
     * @param arguments the argument string array with the input
     * @throws GraFlapException throws a {@link GraFlapException} if the array does not have enough arguments
     */
    public LoncapaArguments(String[] arguments) throws GraFlapException {
        LoncapaArguments.initHashSets();
        try {
            String argsFromLoncapa = arguments[0];
            studentAnswer = arguments[1];
            String[] loncapaArguments = argsFromLoncapa.split("#");

            setTaskTitle(loncapaArguments[0]);
            setUserLanguage(loncapaArguments[1]);
            setSolution(loncapaArguments[2]);
            setMode(loncapaArguments[3]);
            setAgtype(loncapaArguments[4]);
            parseAndCheckNumberOfWords(loncapaArguments[5]);
            setWordString(loncapaArguments[6]);
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
        Mode m = Mode.valueOf(this.mode);
        Type t = Type.valueOf(this.agtype);

        // Check if Mode is legit
        //TODO move to Setter
        //BS - TRY Catch!
        if(m == null) throw new GraFlapException("Mode-Setting is wrong");

        // Check if Type is legit
        //TODO move to Setter
        if(t == null) throw new GraFlapException("AgType-Setting is wrong");

        if(AutomatonModes.contains(m)){
            if(!ATypes.contains(t)){
                throw new GraFlapException("Wrong Type for Automaton Task");
            }
        }
        if(MachineModes.contains(m)){
            if(!MTypes.contains(t)){
                throw new GraFlapException("Wrong Type for Automata Task");
            }
        }

        if(GrammarModes.contains(m)){
            if(!GTypes.contains(t)){
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
     * @return the coded language that the user of the submission uses to run LonCapa
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
