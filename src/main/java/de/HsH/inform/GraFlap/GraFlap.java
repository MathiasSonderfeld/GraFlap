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

import de.HsH.inform.GraFlap.loncapa.answer.*;
import de.HsH.inform.GraFlap.loncapa.answer.AnswerMessage;
import de.HsH.inform.GraFlap.loncapa.entity.Mode;
import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;
import de.HsH.inform.GraFlap.loncapa.main.LoncapaArguments;
import de.HsH.inform.GraFlap.loncapa.main.Result;
import de.HsH.inform.GraFlap.loncapa.svg.SvgFactory;
import de.HsH.inform.GraFlap.loncapa.typetest.*;
import org.jdom2.Element;
import org.jdom2.JDOMException;

/**
 * Main execution file that starts the application
 * @author Ufuk Tosun (2012)
 * @author Benjamin Held (04-17-2016)
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
        LoncapaArguments arguments = null;

        try {

            arguments = new LoncapaArguments(args);
        } catch (GraFlapException lex) {
                System.out.println(lex.getLonCapaMessage(args[0].split("#")[0]));
                System.exit(0);
        }

        produceResult(arguments, determineModeNumber(arguments.getMode()));
    }

    /**
     * method to generate the result based on the input arguments
     * @param arguments the {@link LoncapaArguments} object that holds the submission information
     * @param mode the determined mode
     */
    private static void produceResult(LoncapaArguments arguments, Mode mode) {
        try {
            Result result = new Result(mode).generateResult(arguments);
            String studType = result.getStudType();

            if (arguments.getMode().contains("t")) {
                if (arguments.getMode().contains("a")) {
                    studType = AutomatonTypeTest.checkForAutomatonType(result.getSubmission());
                } else if (arguments.getMode().contains("g")) {
                    studType = GrammarTypeTest.checkForGrammarType(result.getSubmission());
                }
            }

            Element svg = SvgFactory.determineBuilder(arguments, result.getSubmission().getInputType(), mode).getSvg();
            AnswerMessage an = AnswerFactory.determineAnswer(result.getResult(), arguments.getTaskTitle(),
                                                             arguments.getUserLanguage(),arguments.getMode(),
                                                             arguments.getAgtype(), studType, svg);
            // for LON-CAPA
            System.out.println(an.generateProformaMessage());

        } catch (GraFlapException lex) {
            System.out.println(lex.getLonCapaMessage(arguments.getTaskTitle()));
            System.exit(0);
        }
    }

    /**
     * method to determine the operation mode based on the content of the mode string
     * @param mode the string containing the mode information
     * @return the corresponding mode
     */
    private static Mode determineModeNumber(String mode) {
        switch (mode) {
            case ("ar"): return Mode.AR;
            case ("art"): return Mode.AR;
            case ("ag"): return Mode.AG;
            case ("agt"): return Mode.AG;
            case ("gg"): return Mode.GG;
            case ("ggt"): return Mode.GG;
            case ("arw"): return Mode.ARW;
            case ("artw"): return Mode.ARW;
            case ("agw"): return Mode.AGW;
            case ("agtw"): return Mode.AGW;
            case ("ggw"): return Mode.GGW;
            case ("ggtw"): return Mode.GGW;
            case ("eat"): return Mode.EAT;
            case ("egt"): return Mode.EAT;
            case ("ww"): return Mode.WW;
            case ("gr"): return Mode.GR;
            case ("grt"): return Mode.GR;
            case ("grw"): return Mode.GRW;
            case ("grtw"): return Mode.GRW;
            case ("mp"): return Mode.MP;
            case ("mmw"): return Mode.MMW;
            case ("cyk"): return Mode.CYK;
            case ("der"): return Mode.DER;
            case ("svgg"): return Mode.SVGG;
            case ("svga"): return Mode.SVGA;
            default: return Mode.ERROR;
        }
    }
}