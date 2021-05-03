package de.HsH.inform.GraFlap.derivation;

import de.HsH.inform.GraFlap.entity.ValuePair;
import de.HsH.inform.GraFlap.grammar.Grammar;
import de.HsH.inform.GraFlap.grammar.Production;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * logic class to evaluate the submission of a derivation exercise
 * @author Benjamin Held (08-17-2016)
 * @since 09-04-2016
 * @version 0.1.2
 */
public class DerivationChecker {
    private Grammar grammar;
    private int falseCounter;
    private String word;

    public DerivationChecker(Grammar grammar, String word) {
        this.grammar = grammar;
        this.word = word;
        this.falseCounter = 0;
    }

    /**
     * method to start the evaluation process for a given submission
     * @param submission the submitted derivations
     * @return true: if the submission is correct, false: if not
     */
    public boolean checkSubmission(String[] submission) {
        for (int i = 0; i < submission.length - 1; i++) {
            if (!isCorrect(submission[i], submission[i+1])) {
                falseCounter++; // invalid grammar rule found
            }
        }

        if (!submission[submission.length - 1].equals(word)) {
            falseCounter++;
        }

        return (falseCounter == 0);
    }

    public int getFalseCounter() {
        return this.falseCounter;
    }

    /**
     * method to check if the considered derivation step is correct
     * @param actual the start word
     * @param next the resulting word after applying a grammar rule
     * @return true: if the derivation step is correct, false: if not
     */
    private boolean isCorrect(String actual, String next) {
        ValuePair<String, String> diff = findDifference(castStringToList(actual), castStringToList(next));
        if (actual.length() < next.length()) {
            // look at rules with |lhs| < |rhs| and diff in rhs
            ArrayList<Production> productions = new ArrayList<>();
            for (Production production: grammar.getProductions()) {
                if (production.getLHS().length() < production.getRHS().length() &&
                    production.getRHS().contains(diff.getValue())) {
                    productions.add(production);
                }
            }
            // check if lhs occurs in actual
            // check if applying rule on actual for every occurrence of lhs in actual leads to next
            return checkProductionApplication(actual, next, productions);
        } else if (actual.length() == next.length()) {
            // look at rules with |lhs| = |rhs| and diff in rhs
            ArrayList<Production> productions = new ArrayList<>();
            for (Production production: grammar.getProductions()) {
                if (production.getLHS().length() == production.getRHS().length() && !production.getRHS().equals("ε") &&
                    production.getRHS().contains(diff.getValue())) {
                    productions.add(production);
                }
            }
            // check if lhs occurs in actual
            // check if applying rule on actual for every occurrence of lhs in actual leads to next
            return checkProductionApplication(actual, next, productions);
        } else {
            // look at epsilon rules
            ArrayList<Production> productions = new ArrayList<>();
            for (Production production: grammar.getProductions()) {
                if (production.getRHS().equals("ε") && production.getLHS().contains(diff.getKey())) {
                    productions.add(production);
                }
            }
            // check if lhs occurs in actual
            // check if applying rule on actual for every occurrence of lhs in actual leads to next
            return checkProductionApplication(actual, next, productions);
        }
    }

    /**
     * method to find the different characters between two words of a derivation step
     * @param actualList the list of letters of the start word
     * @param nextList the list of letters of the word after applying a grammar rule
     * @return the differences in both letter lists
     */
    private ValuePair<String, String> findDifference(List<Character> actualList, List<Character> nextList) {
        int minLength = Math.min(actualList.size(), nextList.size());
        for (int i = 0; i < minLength; i++) {
            if ((actualList.isEmpty() || nextList.isEmpty())) {
                break;
            }
            if (actualList.get(i).equals(nextList.get(i))) {
                actualList.remove(i);
                nextList.remove(i);
                i--;
                minLength--;
            }
        }

        minLength = Math.min(actualList.size(), nextList.size());
        for (int i = 0; i < minLength; i++) {
            if ((actualList.isEmpty() || nextList.isEmpty())) {
                break;
            }
            if (actualList.get(actualList.size() - 1).equals(nextList.get(nextList.size() - 1))) {
                actualList.remove(actualList.size() - 1);
                nextList.remove(nextList.size() - 1);
            }
        }

        return new ValuePair<>(generateString(actualList), generateString(nextList));
    }

    private List<Character> castStringToList(String text) {
        return text.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    }

    private String generateString(List<Character> list) {
        StringBuilder sb = new StringBuilder();
        list.forEach(sb::append);
        return sb.toString();
    }

    private boolean checkProductionApplication(String actual, String next, ArrayList<Production> productions) {
        for (Production production: productions) {
            if (productionGeneratesNext(production, actual, next)) {
                return true;
            }
        }
        return false;
    }

    /**
     * method to check if the usage of the rule results in the correct next word
     * @param production the considered grammar rule
     * @param actual the start word
     * @param next the resulting word after applying a grammar rule
     * @return true: if the derivation step is correct, false: if not
     */
    private boolean productionGeneratesNext(Production production, String actual, String next) {
        List<Character> lhsList = castStringToList(production.getLHS());
        List<Character> actualList = castStringToList(actual);

        if (lhsList.size() > actualList.size()) {
            return false;
        }

        for (int i = 0; i < actualList.size() - lhsList.size() + 1; i++) {
            boolean doesGenerateNext = false;

            if (actualList.get(i) == lhsList.get(0)) {
                boolean isMatch = true;
                for (int j = 1; j < lhsList.size(); j++) {
                    if (!(actualList.get(i + j) == lhsList.get(j))) {
                        isMatch = false;
                        break;
                    }
                }
                if (isMatch) {
                    doesGenerateNext = replaceAndCheckString(castStringToList(actual), i, production, next);
                }
            }
            if (doesGenerateNext) {
                return true;
            }
        }
        return false;
    }

    /**
     * method to replace the occurring characters from the left side of the rule with the character of the right side
     * and check for equality with the following derivation word
     * @param characters the characters of the actual word
     * @param index the index where the characters of the right side start
     * @param production the considered rule
     * @param next the following derivation word
     * @return true: if the replacement produces next, false: if not
     */
    private boolean replaceAndCheckString(List<Character> characters, int index, Production production, String next) {
        List<Character> lhsList = castStringToList(production.getLHS());
        List<Character> rhsList = castStringToList(production.getRHS().replaceAll("ε", ""));

        for (int i = lhsList.size() - 1; i >= 0; i--) {
            characters.remove(index + i);
        }

        for (int i = 0; i < rhsList.size(); i++) {
            characters.add(index + i, rhsList.get(i));
        }

        return generateString(characters).equals(next);
    }
}