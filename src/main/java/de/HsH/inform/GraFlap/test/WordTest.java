package de.HsH.inform.GraFlap.test;

import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.GrammarBuilder;
import de.HsH.inform.GraFlap.JflapWrapper.grammar.Grammar;
import de.HsH.inform.GraFlap.JflapWrapper.parse.BruteParser;
import de.HsH.inform.GraFlap.JflapWrapper.parse.Parser;
import de.HsH.inform.GraFlap.JflapWrapper.parse.RestrictedBruteParser;
import de.HsH.inform.GraFlap.convert.ConvertSubmission;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.typetest.GrammarTypeTest;

import java.util.regex.Pattern;

/**
 * static helper class to test words coded into a provided string or to check if the given word can be built by
 * a given grammar
 * @author Frauke Sprengel (08-17-2015)
 * @author Benjamin Held (04-10-2016)
 * @version {@value GraFlap#version}
 */
public class WordTest {

    private static String positiveFeedback;
    private static String negativeFeedback;

    /**
     * method to test given test words against a grammar or regex
     * @param given the grammar or regular expression coded into a string
     * @param words the parsed test words
     * @return rounded percentage value how many word were tested successfully ranging form [0,100]
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    public static int checkWords(String given, String[] words) throws GraFlapException {
        int result;

        if (given.contains("->")) {
            result = checkWordsWithGrammar(given, words);
        } else {
            result = checkWordsWithRegex(given, words);
        }

        double resultValue = (result * 100.0) / words.length;
        if (resultValue > 0 && resultValue < 1) {
            return 1;
        }
        return (int) (Math.round(resultValue));
    }

    /**
     * method to test word with a grammar
     * @param grammar the given grammar
     * @param words the test words
     * @return the number of failed tested words
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    public static int checkWordsWithGrammar( Grammar grammar, String[] words) throws GraFlapException {
        int result = 0;
        int pos = 0;
        positiveFeedback = "";
        negativeFeedback = "";
        Parser parser;
        if (GrammarTypeTest.isContextFreeGrammar(grammar)) {
            parser = new RestrictedBruteParser(grammar);
        } else {
            parser = new BruteParser(grammar);
        }
        for (String word : words) {
            if (!parser.solve(word)) {
                result++;
                if (result>1) { negativeFeedback += ", "; }
                negativeFeedback += word ;
            }else{
                pos++;
                if (pos>1) { positiveFeedback += ", "; }
                positiveFeedback += word ;
            }
        }

        for (int i=0; i<words.length;i++){
            for (int j=0; j<i; j++){
                if (words[i].equals(words[j])){
                    result++;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * private method to test word with a regular expression
     * @param regex the regular expression
     * @param words the test words
     * @return the number of correctly tested words
     */
    public static int checkWordsWithRegex(String regex, String[] words) {
        int result = 0;
        int pos = 0;
        positiveFeedback = "";
        negativeFeedback = "";
         int numberOfRightWords = 0;
        Pattern p = Pattern.compile(regex);
        for (String word : words) {
            if ((p.matcher(word).matches())) {
                boolean old_word = false;
                for (int j = 0; j < numberOfRightWords; j++) {
                    old_word = old_word || word.equals(words[j]);
                }
                if (old_word) {
                    result++;
                } else {
                    numberOfRightWords++;
                 pos++;
                if (pos>1) { positiveFeedback += ", "; }
                positiveFeedback += word ;
               }
            } else {
                result++;
                if (result>1) { negativeFeedback += ", "; }
                negativeFeedback += word ;
            }
        }

        return result;
    }

    /**
     * private method to test word with a grammar
     * @param grammarString the grammar coded into a string
     * @param words the test words
     * @return the number of correctly tested words
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    private static int checkWordsWithGrammar(String grammarString, String[] words) throws GraFlapException {
        String jffGrammar = GrammarBuilder.buildGrammar(grammarString);
        return checkWordsWithGrammar(ConvertSubmission.openGrammar(jffGrammar).getSubmissionObject(), words);
    }

    public static String getPositiveFeedback() {
        return positiveFeedback;
    }

    public static String getNegativeFeedback() {
        return negativeFeedback;
    }
}
