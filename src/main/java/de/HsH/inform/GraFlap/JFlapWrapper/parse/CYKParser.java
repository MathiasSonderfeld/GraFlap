package de.HsH.inform.GraFlap.JFlapWrapper.parse;

import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;
import model.algorithms.AlgorithmException;
import model.symbols.Symbol;
import de.HsH.inform.GraFlap.exception.NonCNFGrammarException;
import de.HsH.inform.GraFlap.JFlapWrapper.grammar.Grammar;
import de.HsH.inform.GraFlap.JFlapWrapper.grammar.Production;

import java.util.HashSet;
import java.util.Set;

/**
 * wrapper class for JFLAP isolation to represent the CYK parser
 * @author Benjamin Held (04-20-2016)
 * @since 09-20-2016
 * @version 0.3.2
 */
public class CYKParser extends Parser<model.algorithms.testinput.parse.cyk.CYKParser> {

    /**
     * Constructor
     * @param grammar the grammar that should be used
     * @throws NonCNFGrammarException throws a {@link NonCNFGrammarException} when the provided grammar is not CNF
     */
    public CYKParser(Grammar grammar) throws NonCNFGrammarException {
        super(grammar);
        try {
            this.parser = new model.algorithms.testinput.parse.cyk.CYKParser(this.useGrammar);
        } catch (AlgorithmException ex) {
            throw new NonCNFGrammarException("ERROR [CYKParser]: Provided solution grammar is not in CNF.");
        }
    }

    /**
     * method to check if the word that was used with solved was accepted
     * @return true: if accepted, false if not
     */
    public boolean isAccepted() {
        return this.parser.isAccept();
    }

    /**
     * method to compare the productions from the parser with productions from a submission.
     * Since the index mapping of the parser table differs the value for row and column are used to calculate the
     * correct corresponding entry
     * @param productions the productions at entry (row, column)
     * @param row the row value of the submission
     * @param column the column value of the submission
     * @return true: if the sets have the same left side variables, false if not
     * @throws GraFlapException throsw a {@link GraFlapException} when the provided production is null
     */
    public boolean compareProductionsAt(HashSet<Production> productions, int row, int column) throws GraFlapException {
        int parserColumn = column + row;
        Set<Symbol> symbols = parser.getValueAt(column, parserColumn);

        if (productions == null) {
            throw new GraFlapException("Error [CYKParser]: undef. production, something is wrong with the submission.");
        }

        if (productions.isEmpty()) {
            return symbols.isEmpty();
        }

        if (productions.size() != symbols.size()) {
            return false;
        }

        for (Symbol symbol: symbols) {
            boolean hasFoundSymbol = false;
            for (Production production: productions) {
                String lhs = production.getLHS();
                if (lhs.equals(symbol.toString())) {
                    hasFoundSymbol = true;
                }
            }
            if (!hasFoundSymbol) {
                return false;
            }
        }
        return true;
    }
}