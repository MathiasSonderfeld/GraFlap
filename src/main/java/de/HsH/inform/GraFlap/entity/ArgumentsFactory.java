package de.HsH.inform.GraFlap.entity;

import de.HsH.inform.GraFlap.exception.GraFlapException;

import java.util.HashSet;

public class ArgumentsFactory {
    private static final HashSet<InputMode> automatonModes = new HashSet<>();
    private static final HashSet<InputMode> grammarModes = new HashSet<>();
    private static final HashSet<InputMode> machineModes = new HashSet<>();
    private static final HashSet<InputType> automatonTypes = new HashSet<>();
    private static final HashSet<InputType> grammarTypes = new HashSet<>();
    private static final HashSet<InputType> machineTypes = new HashSet<>();
    private static boolean setsMade = false;


    /**
     * parses the Input structured as old Loncapa Format and returns an Argument
     * @param args
     * @return
     * @throws GraFlapException
     */
    public static Arguments parseLoncapaFormat(String[] args) throws GraFlapException {
        Arguments arguments = new Arguments();
        String[] taskArguments = args[0].split("#");
        arguments.setStudentAnswer(args[1]);

        arguments.setTaskTitle(taskArguments[0]);
        arguments.setUserLanguage(taskArguments[1]);
        arguments.setSolution(taskArguments[2]);
        int numberOfWords = parseAndCheckNumberOfWords(taskArguments[5]);
        String inputWords = taskArguments[6];
        checkInputWords(numberOfWords, inputWords);
        arguments.setNumberOfWords(numberOfWords);
        arguments.setWordString(inputWords);

        InputMode mode = InputMode.valueOf(taskArguments[3]);
        InputType type = InputType.valueOf(taskArguments[4]);
        checkCorrectModeAndType(mode, type);
        OperationMode operationMode = determineOperationMode(taskArguments[3]);
        arguments.setMode(taskArguments[3]);
        arguments.setArgtype(taskArguments[4]);
        arguments.setOperationMode(operationMode);
        return arguments;
    }

    public static Arguments parseProformaFormat(String input){
        return null;
    }


    /**
     * method to parse the provided number of test words and check for a valid number
     * @param numberOfWordsAsString the string holding the number of test words
     * @throws GraFlapException throws a {@link GraFlapException} if the parsing fails or the number is below 0 and
     * so invalid
     */
    private static int parseAndCheckNumberOfWords(String numberOfWordsAsString) throws GraFlapException {
        int numberOfWords = -1;
        try {
            numberOfWords = Integer.parseInt(numberOfWordsAsString);
        } catch (Exception e) {
            throw new GraFlapException("ERROR - Error in LON-CAPA problem. The number of test words could not be parsed. Please check.");
        }
        if (numberOfWords < 0) {
            throw new GraFlapException("ERROR - Error in LON-CAPA problem. The number of test words is < 0. Please " +
                    "check.");
        }
        return numberOfWords;
    }

    /**
     * method to check if test words are provided and if the number of provided words in the word string match the
     * specified number of input words
     * @throws GraFlapException throws a {@link GraFlapException} if the number of words does not match the number of
     * words in the word string
     */
    private static void checkInputWords(int numberOfWords, String wordString) throws GraFlapException {
        boolean ok = true;
        if (wordString.equals("-")) {
            if(numberOfWords != 0){
                ok = false;
            }
        }
        else{
            String[] tmp = wordString.split("!");
            int wordNumber = 0;
            for (String words: tmp) {
                wordNumber += words.split("%").length;
            }
            if (numberOfWords != wordNumber) {
                ok = false;
            }
        }

        if(!ok){
            throw new GraFlapException("ERROR - Error in LON-CAPA problem. The number of test words is not equal to the number of provided words. Please check.");
        }
    }

    /**
     * checks that the given mode and type match the operationModes
     * @param mode the mode to verify
     * @param type the type to verify
     * @throws GraFlapException Error-Message if mismatch is found
     */
    private static void checkCorrectModeAndType(InputMode mode, InputType type) throws GraFlapException {
       if(mode == null)
            throw new GraFlapException("Mode-Setting is wrong");

        if(type == null)
            throw new GraFlapException("AgType-Setting is wrong");

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
     * @param mode the string containing the mode information
     * @return the corresponding mode
     */
    private static OperationMode determineOperationMode( String mode) {
        switch (mode) {
            case ("ar"): return OperationMode.AR;
            case ("art"): return OperationMode.AR;
            case ("ag"): return OperationMode.AG;
            case ("agt"): return OperationMode.AG;
            case ("gg"): return OperationMode.GG;
            case ("ggt"): return OperationMode.GG;
            case ("arw"): return OperationMode.ARW;
            case ("artw"): return OperationMode.ARW;
            case ("agw"): return OperationMode.AGW;
            case ("agtw"): return OperationMode.AGW;
            case ("ggw"): return OperationMode.GGW;
            case ("ggtw"): return OperationMode.GGW;
            case ("eat"): return OperationMode.EAT;
            case ("egt"): return OperationMode.EAT;
            case ("ww"): return OperationMode.WW;
            case ("gr"): return OperationMode.GR;
            case ("grt"): return OperationMode.GR;
            case ("grw"): return OperationMode.GRW;
            case ("grtw"): return OperationMode.GRW;
            case ("mp"): return OperationMode.MP;
            case ("mmw"): return OperationMode.MMW;
            case ("cyk"): return OperationMode.CYK;
            case ("der"): return OperationMode.DER;
            case ("svgg"): return OperationMode.SVGG;
            case ("svga"): return OperationMode.SVGA;
            default: return OperationMode.ERROR;
        }
    }

    private static void initHashSets(){
        if(!setsMade){
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
