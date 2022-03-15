package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Mode;
import de.HsH.inform.GraFlap.entity.Testwords;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Helper class to compare Arguments Objects as it doenst have equals Method implemented yet
 */
public class ArgumentsToInputConverter {
    private Arguments arguments;
    private StringBuilder graflapArguments;

    public ArgumentsToInputConverter(Arguments arguments){
        this.arguments = arguments;
        graflapArguments = new StringBuilder();
        graflapArguments.append(arguments.getTaskTitle()).append("#")
                .append(arguments.getUserLanguage()).append("#")
                .append(arguments.getSolution()).append("#");

        if(arguments.getMode() != null)
            graflapArguments.append(arguments.getMode().toString()).append("#");
        else
            graflapArguments.append("null").append("#");

        if(arguments.getType() != null)
            graflapArguments.append(arguments.getType().toString()).append("#");
        else
            graflapArguments.append("null").append("#");

        graflapArguments.append(arguments.getNumberOfWords()).append("#");

        StringBuilder testwordsString = new StringBuilder();
        Testwords testwords = arguments.getTestwords();

        if(testwords.isEmpty())
            testwordsString.append("-");

        else if(arguments.getMode() == Mode.DER || arguments.getMode() == Mode.CYK){
            testwordsString.append(testwords.getSingleWord());
        }
        else if(arguments.getMode() == Mode.MP){
            HashMap<String, String> wordpairs = testwords.getWordpairs();
            String[] froms = wordpairs.keySet().toArray(new String[wordpairs.keySet().size()]);

            for(int fromIndex = 0; fromIndex < froms.length-1; fromIndex++){
                testwordsString.append(froms[fromIndex]).append(";").append(wordpairs.get(froms[fromIndex])).append("%");
            }
            testwordsString.append(froms[froms.length-1]).append(";").append(wordpairs.get(froms[froms.length-1]));
        }
        else if(arguments.getMode() == Mode.MMW){
            List<String> wordList = testwords.getTestWordsList();
            for(int wordIndex = 0; wordIndex < wordList.size()-1; wordIndex++){
                testwordsString.append(wordList.get(wordIndex)).append("%");
            }
            testwordsString.append(wordList.get(wordList.size()-1));
        }
        else {
            List<String> wordList = testwords.getCorrectWords();
            for(int wordIndex = 0; wordIndex < wordList.size()-1; wordIndex++){
                testwordsString.append(wordList.get(wordIndex)).append("%");
            }
            testwordsString.append(wordList.get(wordList.size()-1)).append("!");

            wordList = testwords.getFailingWords();
            for(int wordIndex = 0; wordIndex < wordList.size()-1; wordIndex++){
                testwordsString.append(wordList.get(wordIndex)).append("%");
            }
            testwordsString.append(wordList.get(wordList.size()-1));

        }
        graflapArguments.append(testwordsString);
    }

    public String getBKP(){
        return graflapArguments.toString();
    }

    public String[] getLoncapaInput(){
        if(this.arguments == null) return null;
        return new String[]{graflapArguments.toString(), arguments.getStudentAnswer()};
    }

    private StringBuilder getMainProforma(){
        if(arguments.getMode() == null)
            arguments.setMode(Mode.ERROR);
        String filename = ProformaParser.getFileNameFromMode(arguments.getMode());
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><proforma:submission xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:proforma=\"urn:proforma:v2.1\" id=\"SubmissionUUID\"><proforma:task uuid=\"0123\" parent-uuid=\"0\" lang=\"de\">")
                .append(arguments.getTaskTitle()).append("<proforma:description>Blocked</proforma:description><proforma:proglang version=\"1.0\">Blocked</proforma:proglang><proforma:files><proforma:file id=\"graflap-arguments\" ")
                .append("mimetype=\"base64\" used-by-grader=\"true\" visible=\"no\" usage-by-lms=\"edit\"><proforma:embedded-txt-file filename=\"graflap-arguments\"><![CDATA[")
                .append(graflapArguments).append("]]></proforma:embedded-txt-file></proforma:file></proforma:files><tests><test id=\"")
                .append(arguments.getTestId()).append("\"><title>GraFlap</title><test-type>graflap</test-type><test-configuration><filerefs><fileref refid=\"graflap-arguments\" /></filerefs></test-configuration></test></tests><proforma:meta-data /></proforma:task><proforma:files>")
                .append("<proforma:file id=\"studentAnswer\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"").append(filename).append("\"><![CDATA[").append(arguments.getStudentAnswer()).append("]]></proforma:embedded-txt-file></proforma:file>");
        return sb;
    }


    public String[] getProformaInput(){
        if(this.arguments == null) return null;
        StringBuilder sb = getMainProforma();
        sb.append("</proforma:files><proforma:grader-spec format=\"xml\" lang=\"proforma\" structure=\"separate-test-feedback\" /></proforma:submission>");
        return new String[]{"", sb.toString()};
    }

    public String[] getProformaInputWithSets(){
        if(this.arguments == null) return null;
        StringBuilder sb = getMainProforma();
        sb.append("<proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"states\"><![CDATA[").append(arguments.getStates())
                .append("]]></proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"transitions\"><![CDATA[").append(arguments.getTransitions())
                .append("]]></proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"initials\"><![CDATA[").append(arguments.getInitials())
                .append("]]></proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"alphabet\"><![CDATA[").append(arguments.getAlphabet())
                .append("]]></proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"finals\"><![CDATA[").append(arguments.getFinals())
                .append("]]></proforma:embedded-txt-file></proforma:file><proforma:file id=\"\" mimetype=\"xml\"><proforma:embedded-txt-file filename=\"stackalphabet\"><![CDATA[").append(arguments.getStackalphabet())
                .append("]]></proforma:embedded-txt-file></proforma:file></proforma:files><proforma:grader-spec format=\"xml\" lang=\"proforma\" structure=\"separate-test-feedback\" /></proforma:submission>");
        return new String[]{"", sb.toString()};
    }
}
