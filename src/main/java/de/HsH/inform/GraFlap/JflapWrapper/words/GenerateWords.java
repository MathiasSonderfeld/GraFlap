package de.HsH.inform.GraFlap.JflapWrapper.words;

import de.HsH.inform.GraFlap.GrammarBuilder;
import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.JflapWrapper.grammar.Grammar;
import de.HsH.inform.GraFlap.JflapWrapper.parse.BruteParser;
import de.HsH.inform.GraFlap.JflapWrapper.parse.Parser;
import de.HsH.inform.GraFlap.JflapWrapper.parse.RestrictedBruteParser;
import de.HsH.inform.GraFlap.JflapWrapper.simulation.WordBuilder;
import de.HsH.inform.GraFlap.convert.ConvertSubmission;
import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.typetest.GrammarTypeTest;
import nl.flotsam.xeger.Xeger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Helper class to generate words for a given grammar or regular expression
 * @author Ufuk Tosun (11-29-2012)
 * @author Benjamin Held (04-12-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class GenerateWords {
    /**
     * a factor that specifies the ration between right and wrong words
     */
    private final int WRONG_WORD_RATIO_FACTOR = 3;
    /**
     * specifies how man test word should be used
     */
    private final int numberOfTestWords;
    /**
     * a set holding all generated words
     */
    private final HashSet<String> generatedWords;

    /**
     * Constructor
     * @param numberOfTestWords the number of required test words
     */
    public GenerateWords(int numberOfTestWords) {
        this.numberOfTestWords = numberOfTestWords;
        generatedWords = new HashSet<>();
    }


    public Testwords generateTestWords(String regex) throws GraFlapException {
        Testwords testwords = new Testwords(this.numberOfTestWords, this.numberOfTestWords * this.WRONG_WORD_RATIO_FACTOR);
        testwords.addAllToCorrectWords(Arrays.asList(generateRightWordForRegex(regex)));
        testwords.addAllToFailingWords(Arrays.asList(generateWrongWordsForRegex(regex)));
        return testwords;
    }


    /**
     * method to generate random wrong words based on a regular expression
     * @param regex the regular expression coded in a string from LON-CAPA
     * @return an array containing not accepted words
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    public String[] generateWrongWordsForRegex(String regex) throws GraFlapException {
        String[] alphabet;
        alphabet = this.getAlphabet(regex);
        String[] words = new String[numberOfTestWords * WRONG_WORD_RATIO_FACTOR];
        int numberOfWords = 0;
        Pattern p = Pattern.compile(regex);
        try {
            do {
                String word;
                word = generateRandomWord(alphabet, 20);
                if (!(p.matcher(word).matches())) {
                    boolean old_word = false;
                    for (int i=0; i< numberOfWords; i++){
                        old_word = old_word || word.equals(words[i]);
                    }
                    if (!old_word){
                    words[numberOfWords] = word;
                    numberOfWords++;
                    }
                }
            } while (numberOfWords < numberOfTestWords * WRONG_WORD_RATIO_FACTOR);
        } catch (NullPointerException e) {
            throw new GraFlapException("- Keine nicht zu akzeptierenden Woerter für den regulaeren Ausdruck vorhanden.");
        }
        return words;
    }

    /**
     * method to generate random correct words based on a regular expression
     * @param regex the regular expression coded in a string from LON-CAPA
     * @return an array containing accepted words
     */
    public String[] generateRightWordForRegex(String regex) {
        String[] words = new String[numberOfTestWords];
        Xeger generator = new Xeger(regex);
        String result;
        int numberOfWords = 0;

         do {
            result = generator.generate();
            boolean old_word = false;
            for (int i=0; i< numberOfWords; i++){
                old_word = old_word || result.equals(words[i]);
            }
            if (!old_word && result.length()>=1 ){
                words[numberOfWords] = result;
                numberOfWords++;
            }
         }  while (numberOfWords < numberOfTestWords);
        return words;
    }

    /**
     * method to generate correct words of the grammar based on its grammar type: for context free grammars the
     * CYK parser is used, for other grammars the BruteForce parser
     * @param grammarString a string containing the coded grammar
     *                      (for example: S=AB,A=aAb,A=ab,B=aBb,B=ab).
     * @return an array of right words for the given grammar
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further in the calling hierarchy or when
     * a problem occurs within the CYK Parser
     */
    public Testwords generateTestWordsForGrammar(String grammarString) throws GraFlapException {
        String jffGrammar = GrammarBuilder.buildGrammar(grammarString);
        Submission<Grammar> submission = ConvertSubmission.openGrammar(jffGrammar);
        Grammar grammar = submission.getSubmissionObject();
        Parser parser = determineParser(grammar);
        Testwords testwords = new Testwords(numberOfTestWords, numberOfTestWords * WRONG_WORD_RATIO_FACTOR);
        testwords.addAllToCorrectWords(Arrays.asList(WordBuilder.buildWords(numberOfTestWords, submission.getSubmissionObject())));
        ArrayList<String> wrongWords = testwords.getFailingWords();
        int wrongWordCount = 0;

        if (!parser.solve("")) {
            wrongWords.add("");
            wrongWordCount++;
        }

        while (wrongWordCount < numberOfTestWords * WRONG_WORD_RATIO_FACTOR) {
            String word = generateRandomWord(grammar.getAlphabet(), 20);
            if (!parser.solve(word) && !wrongWords.contains(word)) {
                wrongWords.add(word);
                wrongWordCount++;
            }
        }
        return testwords;
    }

    /**
     * checks the correctness of the wrong words
     * @param grammarString a string with the coded grammar
     * @param testwords the test words
     * @return an array containing the wrong words
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further in the calling hierarchy
     */
    public Testwords checkWrongGrammarWords( String grammarString, Testwords testwords) throws GraFlapException {
        String jffGrammar = GrammarBuilder.buildGrammar(grammarString);
        Grammar grammar = ConvertSubmission.openGrammar(jffGrammar).getSubmissionObject();

        ArrayList<String> listOfWords = testwords.getFailingWords();
        Parser parser = determineParser(grammar);
        for (String wrongWord : testwords.getFailingWordsArray()) {
            if (parser.solve(wrongWord)) {
                listOfWords.remove(wrongWord);
            }
        }
        return testwords;
    }

    /**
     * method to retrieve the terminal alphabet of the provided grammar coded into the given string
     * @param grammar the grammar encoded (for example: S=AB,A=aAb,A=ab,B=aBb,B=ab).
     * @return an array with the alphabet of the grammar
     */
    private String[] getAlphabet(String grammar) {
        String[] alphabet;
        int ascii_a = 'a', ascii_z = 'z';
        int ascii_0 = '0', ascii_9 = '9';
        String str_current_letter;
        ArrayList<String> small_type = addCharacterToList(grammar, ascii_a, ascii_z);
        small_type.addAll(addCharacterToList(grammar, ascii_0, ascii_9));
        String reducedGrammar = grammar.replaceAll("->","");
        str_current_letter = "-";
        if (reducedGrammar.contains(str_current_letter)) {
            small_type.add(str_current_letter);
        }

        alphabet = new String[small_type.size()];
        for (int i = 0; i < small_type.size(); i++) {
            alphabet[i] = small_type.get(i);
        }
        return alphabet;
    }

    /**
     * method to construct to required parser for the given grammar
     * @param grammar the grammar that is needed to determine the parser
     * @return the parser based on the input grammar
     */
    private Parser determineParser(Grammar grammar) {
        if (GrammarTypeTest.isContextFreeGrammar(grammar)) {
            return new RestrictedBruteParser(grammar);
        } else {
            return new BruteParser(grammar);
        }
    }

    /**
     * method to generate a word from a given alphabet
     * @param alphabet the alphabet of the grammar
     * @param maxWordLength the maximum length of a word
     * @return a generated random word of the alphabet
     */
    private String generateRandomWord(String[] alphabet, int maxWordLength) {
        Random rand = new Random();
        String word;
        do {
            word = "";
            for (int j = 0; j <= rand.nextInt(maxWordLength) ; j++) {
                word = word + alphabet[rand.nextInt(alphabet.length)];
            }
        } while(generatedWords.contains(word));
        generatedWords.add(word);
        return word;
    }

    /**
     * method to add all lower case characters between asciiStart and asciiEnd into an {@link ArrayList} if it
     * used by the given grammar
     * @param grammar the input grammar
     * @param asciiStart the integer representation of the considered start ascii character
     * @param asciiEnd the integer representation of the considered end ascii character
     * @return an {@link ArrayList} holding all character between asciiStart and asciiEnd that are used in grammar
     */
    private ArrayList<String> addCharacterToList(String grammar, int asciiStart, int asciiEnd) {
        ArrayList<String> small_type = new ArrayList<>();
        char current_letter;
        String str_current_letter;
        for (int i = asciiStart; i <= asciiEnd; i++) {
            current_letter = (char) i;
            str_current_letter = "" + current_letter;
            if (grammar.contains(str_current_letter)) {
                small_type.add(str_current_letter);
            }
        }
        return small_type;
    }
}