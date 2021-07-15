package de.HsH.inform.GraFlap;

import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.util.regex.Pattern;

/**
 *  helper class to build a grammar form a given string
 *  @author Frauke Sprengel (08-15-2015)
 *  @author Benjamin Held (04-10-2016)
 * @version {@value GraFlap#version}
 */
public class GrammarBuilder {
    /**
     * builds grammar from given string
     * @param grammar a grammar coded in a string
     * @return the converted string of the grammar
     * @throws GraFlapException if an error in the grammar occurs
     */
    public static String buildGrammar(String grammar) throws GraFlapException {
        //example: S->AB,A->aAb,A->ab,B->aBb,B->ab
        String[] rules = split(grammar);

        Element root = new Element("structure");
        Document doc = new Document(root);
        root.addContent(new Element("type").addContent("grammar"));

        try {
            for (String rule1 : rules) {
                String[] rule = rule1.split("->");
                String[] rule2 = rule[1].split("\\|");
                for (int j = 0; j < rule2.length; j++) {
                    if (rule2[j].contains("E") || rule2[j].contains("ε") || rule2[j].contains("λ")) rule2[j] = "";
                    root.addContent(new Element("production")
                            .addContent(new Element("left").addContent(rule[0]))
                            .addContent(new Element("right").addContent(rule2[j])));
                }
            }
        } catch (IndexOutOfBoundsException e){
            throw new GraFlapException("There is something wrong with the grammar.");
        }

        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        return out.outputString(doc);
    }

    /**
     * method to split the given grammar into an array
     * @param grammar a string containing the grammar that needs to be split
     * @return an array containing all rules
     * @throws GraFlapException if there are formal errors in the grammar
     */
    private static String[] split(String grammar) throws GraFlapException {
        grammar = grammar.replaceAll(" ", "");
        grammar = grammar.replaceAll("\n", "");
        grammar = grammar.replaceAll("\r", "");
        grammar = grammar.replaceAll("\t", "");

        String GramAlphabet = grammar.replaceAll("\\|", "");
        GramAlphabet = GramAlphabet.replaceAll(",", "");
        GramAlphabet = GramAlphabet.replaceAll("->","");

        String grammarRegex = "([a-zA-Z]|0|1|2|3|4|5|6|7|8|9|-)+";
        Pattern p = Pattern.compile(grammarRegex);

        if (!(p.matcher(GramAlphabet).matches())) {
            throw new GraFlapException("ERROR - There is something wrong with your grammar.");
        }
        if (!(GramAlphabet.contains("S"))) {
            throw new GraFlapException("ERROR - Missing Startsymbol S.");
        }

        return grammar.split(",");
    }
 }
