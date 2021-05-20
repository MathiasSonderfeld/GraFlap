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
import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import de.HsH.inform.GraFlap.answer.Messages.Error.ErrorAnswerMessage;
import de.HsH.inform.GraFlap.io.formatter.LoncapaOutputFormatter;
import de.HsH.inform.GraFlap.io.formatter.OutputFormatter;
import de.HsH.inform.GraFlap.io.formatter.ProformaOutputFormatter;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.io.parsing.ArgumentsParser;
import de.HsH.inform.GraFlap.svg.SvgFactory;
import de.HsH.inform.GraFlap.typetest.AutomatonTypeTest;
import de.HsH.inform.GraFlap.typetest.GrammarTypeTest;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.io.parsing.LoncapaParser;
import de.HsH.inform.GraFlap.io.parsing.ProformaParser;
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
        OutputFormatter outputFormatter = null;
        ArgumentsParser parser = null;
        AnswerMessage answerMessage = null;
        try {
            if(args.length < 2){
                throw new IllegalArgumentException("not enough Parameters.");
            }
            else if("-f".equals(args[0])){
                outputFormatter = new ProformaOutputFormatter();
                StringBuilder sb = new StringBuilder();
                Files.lines(Paths.get(args[1]), StandardCharsets.UTF_8).forEach(s -> sb.append(s));
                args[1] = sb.toString();
                parser = new ProformaParser();
            }
            else if("-s".equals(args[0])){
                outputFormatter = new ProformaOutputFormatter();
                parser = new ProformaParser();
            }
            else{
                outputFormatter = new LoncapaOutputFormatter();
                parser = new LoncapaParser();
            }
            arguments = parser.parse(args);
            answerMessage = processSubmission(arguments);
        }
        catch(IOException | IllegalArgumentException e){
            e.printStackTrace(System.out);
        }
        catch(GraFlapException e){
            if(outputFormatter != null){
                String taskTitle = "";
                if(arguments != null){
                    taskTitle = arguments.getTaskTitle();
                }
                answerMessage = new ErrorAnswerMessage(e.getMessage(), taskTitle);
            }
            else{
                e.printStackTrace(System.out);
            }
        }
        finally {
            if(outputFormatter != null && answerMessage != null){
                System.out.println(outputFormatter.format(answerMessage));
            }
        }
    }

    /**
     * method to generate the result based on the input arguments
     * @param arguments the {@link Arguments} object that holds the submission information
     */
    private static AnswerMessage processSubmission( Arguments arguments) throws GraFlapException {
        Grader grader = new Grader(arguments.getOperationMode()).generateResult(arguments);
        String studType = grader.getStudType();

        if (arguments.getMode().contains("t")) {
            if (arguments.getMode().contains("a")) {
                studType = AutomatonTypeTest.checkForAutomatonType(grader.getSubmission());
            } else if (arguments.getMode().contains("g")) {
                studType = GrammarTypeTest.checkForGrammarType(grader.getSubmission());
            }
        }

        Element svg = SvgFactory.determineBuilder(arguments, grader.getSubmission().getOperationType(), arguments.getOperationMode()).getSvg();
        return AnswerFactory.determineAnswer(grader, arguments, studType, svg);
    }
}