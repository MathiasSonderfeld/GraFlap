package de.HsH.inform.GraFlap;
/*
 *  JFLAP - Formal Languages and Automata Package
 * 
 * 
 *  Susan H. Rodger
 *  Computer Science Department
 *  Duke University
 *  August 27, 2009

 *  Copyright (c) 2002-2009
 *  All rights reserved.

 *  JFLAP is open source software. Please see the LICENSE for terms.
 *
 */

import java.io.*;

import de.HsH.inform.GraFlap.answer.*;
import de.HsH.inform.GraFlap.answer.AnswerMessage;
import de.HsH.inform.GraFlap.entity.OperationMode;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.svg.SvgFactory;
import de.HsH.inform.GraFlap.typetest.*;
import org.jdom2.Element;
import org.jdom2.JDOMException;

/**
 * Main execution file that starts the application
 * @author Ufuk Tosun (2012)
 * @author Benjamin Held (04-17-2016)
 * @author Mathias Sonderfeld (07-2021)
 * @since 08-11-2016
 * @version 0.2.6
 */
public class GraFlap {

    /**
     * main method that starts the application
     * @param args the program arguments, here: args[0] contains task title, nickname, bestlanguage, given, mode and
     *             wordString or numberOfWords, each separated by '#'
     * @throws IOException throws a {@link IOException} that occurs deeper within the calling hierarchy
     * @throws JDOMException throws a {@link JDOMException} that occurs deeper within the calling hierarchy
     */
    public static void main(String[] args) throws IOException, JDOMException {

        Arguments arguments = null;

        try {

            arguments = new Arguments(args);
        } catch (GraFlapException lex) {
                System.out.println(lex.getErrorMessage(args[0].split("#")[0]));
                return;
        }

        produceResult(arguments, determineModeNumber(arguments.getMode()));
    }

    /**
     * method to generate the result based on the input arguments
     * @param arguments the {@link Arguments} object that holds the submission information
     * @param operationMode the determined mode
     */
    private static void produceResult(Arguments arguments, OperationMode operationMode ) {
        try {
            Result result = new Result(operationMode).generateResult(arguments);
            String studType = result.getStudType();

            if (arguments.getMode().contains("t")) {
                if (arguments.getMode().contains("a")) {
                    studType = AutomatonTypeTest.checkForAutomatonType(result.getSubmission());
                } else if (arguments.getMode().contains("g")) {
                    studType = GrammarTypeTest.checkForGrammarType(result.getSubmission());
                }
            }

            Element svg = SvgFactory.determineBuilder(arguments, result.getSubmission().getInputType(), operationMode).getSvg();
            AnswerMessage an = AnswerFactory.determineAnswer(result.getResult(), arguments.getTaskTitle(),
                                                             arguments.getUserLanguage(),arguments.getMode(),
                                                             arguments.getAgtype(), studType, svg);
            // for LON-CAPA
            System.out.println(an.generateAnswerMessage());

        } catch (GraFlapException lex) {
            System.out.println(lex.getErrorMessage(arguments.getTaskTitle()));
            return;
        }
    }

    private static Arguments determineInputMode(String[] args) throws GraFlapException {
        if(args.length < 2){
            throw new GraFlapException("not enough parameters.");
        }
        else if("-f".equals(args[0])){

        }
        else if("-s".equals(args[0])){

        }
        else{

        }
        return null;
    }


    /**
     * method to determine the operation mode based on the content of the mode string
     * @param mode the string containing the mode information
     * @return the corresponding mode
     */
    private static OperationMode determineModeNumber( String mode) {
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
}