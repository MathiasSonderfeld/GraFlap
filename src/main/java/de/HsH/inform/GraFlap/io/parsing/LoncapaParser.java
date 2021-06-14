package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.entity.TaskMode;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.InputType;

/**
 * @author Mathias Sonderfeld
 * parses Input-Data in Loncapa Format to Arguments Object
 */
public class LoncapaParser extends ArgumentsParser{

    public LoncapaParser(){
        super();
    }

    /**
     * parses the Input structured as old Loncapa Format and returns an Arguments Object
     * @param args the input arguments
     * @return Arguments Object
     * @throws GraFlapException if input constraints are infringed
     */
    public Arguments parse( String[] args ) throws GraFlapException {
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
        InputType type = InputType.valueOf(taskArguments[4]);
        TaskMode taskMode;
        try{
            taskMode = TaskMode.valueOf(taskArguments[3].toUpperCase());
        }
        catch(Exception e){
            taskMode = TaskMode.ERROR;
        }
        checkCorrectModeAndType(taskMode, type);
        arguments.setArgtype(taskArguments[4]);
        arguments.setTaskMode(taskMode);
        return arguments;
    }
}
