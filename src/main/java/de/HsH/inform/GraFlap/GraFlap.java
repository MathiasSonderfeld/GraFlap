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

import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.answerMessage.AnswerMessage;
import de.HsH.inform.GraFlap.entity.*;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.io.formatter.LoncapaOutputFormatter;
import de.HsH.inform.GraFlap.io.formatter.OutputFormatter;
import de.HsH.inform.GraFlap.io.formatter.ProformaOutputFormatter;
import de.HsH.inform.GraFlap.io.parsing.ArgumentsParser;
import de.HsH.inform.GraFlap.io.parsing.LoncapaParser;
import de.HsH.inform.GraFlap.io.parsing.ProformaParser;
import de.HsH.inform.GraFlap.svg.SvgBuilder;
import de.HsH.inform.GraFlap.svg.SvgFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main execution file that starts the application
 * @author Ufuk Tosun (2012)
 * @author Benjamin Held (04-17-2016)
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value GraFlap#version}
 */
public class GraFlap {
    public static final boolean printAsACII = false;
    public static final double version = 1.0;

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
        System.out.println(parseAndProcessSubmission(args));
    }

    public static String parseAndProcessSubmission(String[] args){
        Arguments arguments = null;
        OutputFormatter outputFormatter = null;
        ArgumentsParser parser = null;
        AnswerMessage answerMessage = null;
        MetaData metaData = new MetaData();
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
            metaData.setTestID(arguments.getTestId());
            answerMessage = processSubmission(arguments);
            if(parser.isFilterWarning()){
                answerMessage.addWarning(String.format("Nach der Filterung sind nur noch %d von %d korrekten und %d von %d falschen Worten übrig", parser.getFilteredCorrectWordsAmount(),
                        parser.getCorrectWordsAmount(), parser.getFilteredFailingWordsAmount(), parser.getFailingWordsAmount()));
            }
        }
        catch(IOException | IllegalArgumentException e){
            e.printStackTrace(System.err);
            return "";
        }
        catch(GraFlapException e){
            if(outputFormatter != null){
                answerMessage = new AnswerMessage(new Result(new Submission(), 100, TaskType.ERROR), arguments,"" , e.getMessage());
            }
            else{
                e.printStackTrace(System.err);
            }
        }
        return outputFormatter.format(answerMessage, metaData);
    }

    /**
     * method to generate the result based on the input arguments
     * @param arguments the {@link Arguments} object that holds the submission information
     */
    protected static AnswerMessage processSubmission(Arguments arguments) throws GraFlapException{
        Result result = Grader.generateResult(arguments);
        boolean isSVGA = arguments.getTaskMode() == TaskMode.SVGA;
        SvgBuilder svgBuilder = SvgFactory.determineBuilder(arguments, result.getSubmission().getOperationType(), isSVGA);
        return new AnswerMessage(result, arguments, svgBuilder.getSvgString());
    }
}