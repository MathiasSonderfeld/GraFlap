package de.HsH.inform.GraFlap.loncapa.convert;

import de.HsH.inform.GraFlap.loncapa.entity.CYKTable;
import de.HsH.inform.GraFlap.loncapa.entity.InputType;
import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;
import de.HsH.inform.GraFlap.JFlapWrapper.Submission;
import de.HsH.inform.GraFlap.JFlapWrapper.grammar.Grammar;
import de.HsH.inform.GraFlap.JFlapWrapper.grammar.Production;

import java.util.HashSet;

/**
 * Helper class with static method to read and convert the given submission string for cyk input
 * @author Benjamin Held (07-12-2016)
 * @since 09-07-2016
 * @version 0.1.1
 */
public class CYKInputParser {

    public static Submission<CYKTable> openCYKInput(String submissionString, String word, Grammar grammar)
            throws GraFlapException {
        CYKTable table = parseSubmission(submissionString, grammar, word);
        return new Submission<>(submissionString, table, InputType.CYKINPUT);
    }

    private static CYKTable parseSubmission(String submissionString, Grammar grammar, String word)
            throws GraFlapException {
        HashSet[][] cykTable = new HashSet[word.length()][word.length()];
        String[] rows = submissionString.split("%");
        for (int i = 0; i < rows.length; i++) {
            String[] entry = rows[i].split(";");
            for (int j = 0; j < entry.length; j++) {
                if (!entry[j].startsWith("{") || !entry[j].endsWith("}")) {
                    throw new GraFlapException("Error [CYKInputParser]: input \"" + entry[j] +  "\" is not a set. " );
                }
                String entrySet = entry[j].substring(1, entry[j].length() - 1);
                String[] productions = entrySet.split(",");
                HashSet<Production> rules = new HashSet<>();
                for (String production: productions) {
                    String[] sides = production.split("->");
                    if (sides.length == 2) {
                        rules.add(new Production(sides[0], sides[1], grammar));
                    } else if (!production.isEmpty()) {
                        rules.add(new Production(production, grammar));
                    }
                }
                cykTable[i][j] = rules;
            }
        }
        return new CYKTable(cykTable);
    }
}
