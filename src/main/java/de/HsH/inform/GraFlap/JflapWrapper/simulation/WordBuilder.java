package de.HsH.inform.GraFlap.JflapWrapper.simulation;

import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JflapWrapper.grammar.Grammar;
import model.languages.BruteLanguageGenerator;
import model.languages.GrammarFactory;
import model.symbols.SymbolString;

import java.util.List;

/**
 * Static wrapper class to generate words for a given grammar or automaton with the help of the jflap
 * LanguageGenerator
 * @author Benjamin Held (05-07-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class WordBuilder {

    /**
     * method to generate the given number of words for the given grammar
     * @param numberOfWords the number word that should be produced
     * @param grammar the grammar which is used to created words
     * @return an array with the produced words
     */
    public static String[] buildWords(int numberOfWords, Grammar grammar) {
        BruteLanguageGenerator generator = new BruteLanguageGenerator(grammar.getJFLAPGrammar());
        List<SymbolString> strings = generator.getStrings(numberOfWords);
        String[] words = new String[strings.size()];
        for (int i = 0; i < words.length; i++) {
            words[i] = strings.get(i).toNondelimitedString();
        }
        return words;
    }

    /**
     * method to generate the given number of words for the given automaton
     * @param numberOfWords the number word that should be produced
     * @param automaton the automaton which is used to created words
     * @return an array with the produced words
     */
    public static String[] buildWords(int numberOfWords, Automaton automaton) {
        Grammar grammar = new Grammar(GrammarFactory.createGrammar(automaton.getJFLAPAutomaton()));
        return buildWords(numberOfWords, grammar);
    }
}
