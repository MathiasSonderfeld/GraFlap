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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.HsH.inform.GraFlap.answer.AnswerFactory;
import de.HsH.inform.GraFlap.parse.Arguments.ArgumentsParser;
import de.HsH.inform.GraFlap.entity.OutputType;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.parse.Arguments.LoncapaParser;
import de.HsH.inform.GraFlap.parse.Arguments.ProformaParser;
import de.HsH.inform.GraFlap.svg.SvgFactory;
import de.HsH.inform.GraFlap.typetest.*;
import org.jdom2.Element;

/**
 * Main execution file that starts the application
 * @author Ufuk Tosun (2012)
 * @author Benjamin Held (04-17-2016)
 * @author Mathias Sonderfeld (07-2021)
 * @since 08-11-2016
 * @version 0.3
 */
public class GraFlap {
    /**
     * Grades a submission for a theoretical computer science task
     * @param args Parameters:
     *             -f: input is given by file. Path to file containinga Submission as Proforma-XML in version 2.1 is expected to follow as second
     *             parameter.
     *             Example: -f ./test.xml
     *
     *             -s input is given as String. Submission as Proforma-XML in Version 2.1 is expected as next argument.
     *             Example: -s <?xml version="1.0" encoding="UTF-8"?><proforma:submisison>...</proforma:submisison>
     *
     *             Response is formatted in a Proforma-Response in version 2.1 if Input is given as Proforma-XML.
     *
     *             no commands: expected old Loncapa format, first parameter is csv of task data with # as delimiter, second parameter is student
     *             answer to grade. Response is formatted for Loncapa. This format should not be used for future projects.
     *             Example: "Beispiel fuer eine kontextfreie Grammatik#de#n,o,p#egt#cfg#0#-" "S -> E | p | n S o"
     */
    public static void main(String[] args) {
        Arguments arguments = null;
        OutputType outputType;
        ArgumentsParser parser = null;
        try {
            if(args.length < 2){
                throw new GraFlapException("not enough Parameters.");
            }
            else if("-f".equals(args[0])){
                outputType = OutputType.Proforma;
                StringBuilder sb = new StringBuilder();
                Files.lines(Paths.get(args[1]), StandardCharsets.UTF_8).forEach(s -> sb.append(s));
                args[1] = sb.toString();
                parser = new ProformaParser();
            }
            else if("-s".equals(args[0])){
                outputType = OutputType.Proforma;
                parser = new ProformaParser();
            }
            else{
                outputType = OutputType.Loncapa;
                parser = new LoncapaParser();
            }
            arguments = parser.parse(args);
        }
        catch(IOException e){
            System.out.println("Cant read file at " + args[1]);
            e.printStackTrace(System.out);
            return;
        }
        catch (GraFlapException lex) {
            System.out.println(lex.getErrorMessage(args[0].split("#")[0]));
            return;
        }
        produceResult(arguments, outputType);
    }

    /**
     * method to generate the result based on the input arguments
     * @param arguments the {@link Arguments} object that holds the submission information
     * @param outputType the Format to which to output to.
     */
    private static void produceResult(Arguments arguments, OutputType outputType) {
        try {
            Result result = new Result(arguments.getOperationMode()).generateResult(arguments);
            String studType = result.getStudType();

            if (arguments.getMode().contains("t")) {
                if (arguments.getMode().contains("a")) {
                    studType = AutomatonTypeTest.checkForAutomatonType(result.getSubmission());
                } else if (arguments.getMode().contains("g")) {
                    studType = GrammarTypeTest.checkForGrammarType(result.getSubmission());
                }
            }

            Element svg = SvgFactory.determineBuilder(arguments, result.getSubmission().getOperationType(), arguments.getOperationMode()).getSvg();
            String xml = AnswerFactory.getOutput(result.getResult(), arguments.getTaskTitle(),
                                                             arguments.getUserLanguage(),arguments.getMode(),
                                                             arguments.getArgtype(), studType, svg, outputType);
            System.out.println(xml);

        } catch (GraFlapException lex) {
            System.out.println(lex.getErrorMessage(arguments.getTaskTitle()));
            return;
        }
    }
}