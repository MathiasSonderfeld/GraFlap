package de.HsH.inform.GraFlap.JflapWrapper.words;

import de.HsH.inform.GraFlap.exception.GraFlapException;

import java.util.HashMap;

/**
 * class to read and generate the test words for machine input and output
 * @author Benjamin Held (06-16-2016)
 * @since 04-29-2016
 * @version 0.1.0
 */
public class MachineInputOutputParser {
    private final HashMap<String, String> inputOutputMapping;

    public MachineInputOutputParser(String wordString) throws GraFlapException {
        inputOutputMapping = new HashMap<>();
        parseString(wordString);
    }

    public HashMap<String, String> getMapping() {
        return inputOutputMapping;
    }

    /**
     * method to parse the provided word string and create the input/output pairs
     * @param wordString the test words coded in a string
     * @throws GraFlapException throws a {@link GraFlapException} if there are invalid input/output pairs
     */
    private void parseString(String wordString) throws GraFlapException {
        String[] pairs = wordString.split("%");
        for (String pair: pairs) {
            String[] inputOutput = pair.split(";");
            try {
                if (inputOutput.length == 1) {
                    inputOutputMapping.put(inputOutput[0], "");
                } else {
                    inputOutputMapping.put(inputOutput[0], inputOutput[1]);
                }
            } catch (IndexOutOfBoundsException ex) {
                throw new GraFlapException("Error: Machine word pair does not consist of a valid input;output pair");
            }
        }
    }
}
