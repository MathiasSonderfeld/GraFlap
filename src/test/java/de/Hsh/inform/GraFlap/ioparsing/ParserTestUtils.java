package de.HsH.inform.GraFlap.ioparsing;

import de.HsH.inform.GraFlap.entity.Arguments;

/**
 * Helper class to compare Arguments Objects as it doenst have equals Method implemented yet
 * TODO Should it be added to Arguments Class?
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
        argumentsComp.getStudentAnswer().equals(argumentsToComp.getStudentAnswer());
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
}
