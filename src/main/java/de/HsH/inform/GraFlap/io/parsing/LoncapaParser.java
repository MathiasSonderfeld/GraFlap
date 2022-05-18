package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.entity.Arguments;
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
        Arguments arguments = super.parse(args);
        arguments.setStudentAnswer(args[1]);
        return arguments;
    }
}
