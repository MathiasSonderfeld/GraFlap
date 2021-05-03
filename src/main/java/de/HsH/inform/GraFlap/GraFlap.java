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
import de.HsH.inform.GraFlap.entity.ArgumentsFactory;
import de.HsH.inform.GraFlap.entity.OutputType;
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
 * @version 0.3
 */
public class GraFlap {
    /**
     * main method that starts the application
     * @param args the program arguments, here: args[0] contains task title, nickname, bestlanguage, given, mode and
     *             wordString or numberOfWords, each separated by '#'
     * @throws IOException throws a {@link IOException} that occurs deeper within the calling hierarchy
     * @throws JDOMException throws a {@link JDOMException} that occurs deeper within the calling hierarchy
     */
    public static void main(String[] args) {
        Arguments arguments = null;
        OutputType outputType;
        try {
            if(args.length < 2){
                throw new GraFlapException("not enough Parameters.");
            }
            else if("-f".equals(args[0])){
                outputType = OutputType.Proforma;
                StringBuilder sb = new StringBuilder();
                Files.lines(Paths.get(args[1]), StandardCharsets.UTF_8).forEach(s -> sb.append(s));
                arguments = ArgumentsFactory.parseProformaFormat(sb.toString());
            }
            else if("-s".equals(args[0])){
                outputType = OutputType.Proforma;
                arguments = ArgumentsFactory.parseProformaFormat(args[1]);
            }
            else{
                outputType = OutputType.Loncapa;
                arguments = ArgumentsFactory.parseLoncapaFormat(args);
            }
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
            String xml = AnswerFactory.getXML(result.getResult(), arguments.getTaskTitle(),
                                                             arguments.getUserLanguage(),arguments.getMode(),
                                                             arguments.getArgtype(), studType, svg, outputType);
            System.out.println(xml);

        } catch (GraFlapException lex) {
            System.out.println(lex.getErrorMessage(arguments.getTaskTitle()));
            return;
        }
    }
}