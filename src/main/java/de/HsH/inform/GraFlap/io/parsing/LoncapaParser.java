package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.TaskMode;
import de.HsH.inform.GraFlap.entity.TaskType;
import de.HsH.inform.GraFlap.exception.GraFlapException;

/**
 * parses Input-Data in Loncapa Format to Arguments Object
 * @author Mathias Sonderfeld
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
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
        arguments.setUserLanguage(getLocale(taskArguments[1]));
        arguments.setSolution(taskArguments[2]);
        int numberOfWords = parseAndCheckNumberOfWords(taskArguments[5]);
        arguments.setNumberOfWords(numberOfWords);
        arguments.setTestwords(parseInputWords(numberOfWords, taskArguments[6]));
        arguments.setWordString(taskArguments[6]);

        TaskMode taskMode;
        try{
            taskMode = TaskMode.valueOf(taskArguments[3].toUpperCase());
        }
        catch(Exception e){
            taskMode = TaskMode.ERROR;
        }

        TaskType taskType;
        try{
            taskType = TaskType.valueOf(taskArguments[4].toUpperCase());
        }
        catch(Exception e){
            taskType = TaskType.ERROR;
        }

        checkCorrectModeAndType(taskMode, taskType);
        arguments.setTaskMode(taskMode);
        arguments.setTaskType(taskType);
        return arguments;
    }
}
