package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.entity.Arguments;

import java.util.Comparator;

/**
 * Helper class to compare Arguments Objects as it doenst have equals Method implemented yet
 */
public class ParserTestUtils {
    public static boolean compareTo( Arguments argumentsComp, Arguments argumentsToComp ){
        return argumentsComp.getTaskTitle().equals(argumentsToComp.getTaskTitle()) &&
        argumentsComp.getUserLanguage().equals(argumentsToComp.getUserLanguage()) &&
        argumentsComp.getMode().equals(argumentsToComp.getMode()) &&
        argumentsComp.getOperationMode().equals(argumentsToComp.getOperationMode()) &&
        argumentsComp.getArgtype().equals(argumentsToComp.getArgtype()) &&
        argumentsComp.getSolution().equals(argumentsToComp.getSolution()) &&
        argumentsComp.getNumberOfWords() == argumentsToComp.getNumberOfWords() &&
        argumentsComp.getWordString().equals(argumentsToComp.getWordString()) &&
        argumentsComp.getStudentAnswer().equals(argumentsToComp.getStudentAnswer()) &&

        (argumentsComp.getStates() == null)?        (argumentsToComp.getStates() == null):          (argumentsComp.getStates().equals(argumentsToComp.getStates())) &&
        (argumentsComp.getInitials() == null)?      (argumentsToComp.getInitials() == null):        (argumentsComp.getInitials().equals(argumentsToComp.getInitials())) &&
        (argumentsComp.getFinals() == null)?        (argumentsToComp.getFinals() == null):          (argumentsComp.getFinals().equals(argumentsToComp.getFinals())) &&
        (argumentsComp.getTransitions() == null)?   (argumentsToComp.getTransitions() == null):     (argumentsComp.getTransitions().equals(argumentsToComp.getTransitions())) &&
        (argumentsComp.getAlphabet() == null)?      (argumentsToComp.getAlphabet() == null):        (argumentsComp.getAlphabet().equals(argumentsToComp.getAlphabet())) &&
        (argumentsComp.getStackalphabet() == null)? (argumentsToComp.getStackalphabet() == null):   (argumentsComp.getStackalphabet().equals(argumentsToComp.getStackalphabet()));
    }

    public static String[] getLoncapaInput(Arguments args){
        if(args == null) return null;
        String input =  args.getTaskTitle() + "#" +
                        args.getUserLanguage() + "#" +
                        args.getSolution() + "#" +
                        args.getMode() + "#" +
                        args.getArgtype() + "#" +
                        args.getNumberOfWords() + "#" +
                        args.getWordString();
        return new String[]{input, args.getStudentAnswer()};
    }

    public static String[] getProformaInput(Arguments args){
        if(args == null) return null;
        String[] largs = getLoncapaInput(args);
        String input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><proforma:submission xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:proforma=\"urn:proforma:v2.1\" id=\"SubmissionUUID\"><proforma:task uuid=\"0123\" parent-uuid=\"0\" lang=\"de\">" +
                args.getTaskTitle() +
                "<proforma:description>Blocked</proforma:description><proforma:proglang version=\"1.0\">Blocked</proforma:proglang><proforma:files><proforma:file id=\"graflap-arguments\" " +
                "mimetype=\"base64\" used-by-grader=\"true\" visible=\"no\" usage-by-lms=\"edit\"><proforma:embedded-txt-file filename=\"graflap-arguments\">" +
                "<![CDATA[" + largs[0] + "]]>" +
                "</proforma:embedded-txt-file></proforma:file></proforma:files><proforma:tests /><proforma:meta-data /></proforma:task><proforma:files><proforma:file id=\"studentAnswer\" " +
                "mimetype=\"xml\"><proforma:embedded-txt-file filename=\"test.jff\">" +
                "<![CDATA[" + largs[1] + "]]>" +
                "</proforma:embedded-txt-file>" +
                "</proforma:file></proforma:files><proforma:grader-spec format=\"xml\" lang=\"proforma\" structure=\"separate-test-feedback\" /></proforma:submission>";

        return new String[]{"", input};
    }

    public static String[] getProformaInputWithSets(Arguments args){
        if(args == null) return null;
        String[] largs = getLoncapaInput(args);
        String input = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><proforma:submission xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:proforma=\"urn:proforma:v2.1\" id=\"SubmissionUUID\"><proforma:task uuid=\"0123\" parent-uuid=\"0\" lang=\"de\">" +
                args.getTaskTitle() +
                "<proforma:description>Blocked</proforma:description><proforma:proglang version=\"1.0\">Blocked</proforma:proglang><proforma:files><proforma:file id=\"graflap-arguments\" " +
                "mimetype=\"base64\" used-by-grader=\"true\" visible=\"no\" usage-by-lms=\"edit\"><proforma:embedded-txt-file filename=\"graflap-arguments\">" +
                "<![CDATA[" + largs[0] + "]]>" +
                "</proforma:embedded-txt-file></proforma:file></proforma:files><proforma:tests /><proforma:meta-data /></proforma:task><proforma:files><proforma:file id=\"studentAnswer\" " +
                "mimetype=\"xml\"><proforma:embedded-txt-file filename=\"test.jff\">" +
                "<![CDATA[" + largs[1] + "]]>" +
                "</proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"states\">" +
                "<![CDATA["+ args.getStates() + "]]>" +
                "</proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"transitions\">" +
                "<![CDATA["+ args.getTransitions() + "]]>" +
                "</proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"initials\">" +
                "<![CDATA["+ args.getInitials() + "]]>" +
                "</proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"alphabet\">" +
                "<![CDATA["+ args.getAlphabet() + "]]>" +
                "</proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"finals\">" +
                "<![CDATA["+ args.getFinals() + "]]>" +
                "</proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"stackalphabet\">" +
                "<![CDATA["+ args.getStackalphabet() + "]]></proforma:embedded-txt-file>" +
                "</proforma:file></proforma:files><proforma:grader-spec format=\"xml\" lang=\"proforma\" structure=\"separate-test-feedback\" /></proforma:submission>";

        return new String[]{"", input};
    }
}
