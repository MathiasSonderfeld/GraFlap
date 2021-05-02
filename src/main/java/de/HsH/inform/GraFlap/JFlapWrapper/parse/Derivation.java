package de.HsH.inform.GraFlap.JFlapWrapper.parse;

import model.symbols.SymbolString;

/**
 * wrapper class for JFLAP isolation to represent a derivation
 * @author Benjamin Held (07-26-2016)
 * @since 08-01-2016
 * @version 0.1.0
 */
public class Derivation {

    private model.algorithms.testinput.parse.Derivation derivation;

    Derivation(model.algorithms.testinput.parse.Derivation derivation) {
        this.derivation = derivation;
    }

    public String[] getDerivationWords() {
        SymbolString[] resultSymbols = this.derivation.getResultArray();
        String[] resultStrings = new String[resultSymbols.length];
        for (int i = 0; i < resultSymbols.length; i++) {
            resultStrings[i] = resultSymbols[i].toString().replace(" ", "");
        }
        return resultStrings;
    }

}
