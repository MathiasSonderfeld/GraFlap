package de.HsH.inform.GraFlap.JflapWrapper.parse;

import de.HsH.inform.GraFlap.JflapWrapper.grammar.Grammar;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import model.algorithms.testinput.parse.ParserException;
import model.symbols.symbolizer.Symbolizers;

/**
 * wrapper class for JFLAP isolation to serve as a hierarchy for the used Parser
 * @author Benjamin Held (04-20-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class Parser<T extends model.algorithms.testinput.parse.Parser> {
    /**
     * the grammar that the parser should use
     */
    model.grammar.Grammar useGrammar;
    /**
     * the jflap parser
     */
    T parser;

    /**
     * Constructor
     * @param grammar the grammar that should be used
     */
    Parser( Grammar grammar) {
        this.useGrammar = grammar.getJFLAPGrammar();
    }

    /**
     * implementation of the abstract method from the parent class to parse a given word
     * @param word the input word that should be parsed
     * @return a boolean indicating if the word could be parsed or not
     * @throws GraFlapException throws a {@link GraFlapException} when the parsing fails
     */
    public boolean solve(String word) throws GraFlapException {
        try {
            if (word.equals("λ") || word.equals("E") || word.equals("ε") ) {
                word = "";
            }
            return this.parser.quickParse(Symbolizers.symbolize(word, this.useGrammar));
        } catch (ParserException ex) {
            if (ex.getMessage().equals("The string must not contain non-terminal symbols.")){
                // abfangen The string must not contain non-terminal symbols. ist bei uns Unsinn
                return false;
            }else {
                throw new GraFlapException("Error [Parser] while parsing: " + ex.getMessage());

            }
        }
    }

    public Derivation getDerivation() {
        return new Derivation(this.parser.getDerivation());
    }
}
