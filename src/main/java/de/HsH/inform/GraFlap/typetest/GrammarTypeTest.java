package de.HsH.inform.GraFlap.typetest;

import de.HsH.inform.GraFlap.entity.TaskType;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.JflapWrapper.grammar.Grammar;

/**
 * Action for testing grammar to see what type of grammar it is.
 * @author Kyung Min (Jason) Lee edited by Ufuk Tosun
 * @author Frauke Sprengel (07/2015; additions and refactoring)
 * @author Benjamin Held (04-10-2016)
 * @since 05-09-2016
 * @version 0.2.8
 */
public class GrammarTypeTest {

    /**
     * method to test the type of a given grammar
     * @param grammarSubmission the grammar that should be tested
     * @return the grammar type (rl = rightlinear|cfg = contextfree but not rightlinear|ncfg = not contextfree)
     * @throws GraFlapException throws a {@link GraFlapException} if there is a problem with the submission
     */
    public static TaskType checkForGrammarType(Submission<Grammar> grammarSubmission) throws GraFlapException {

        if (isRightlinearGrammar(grammarSubmission.getSubmissionObject())) {
            return TaskType.RL;
        } else if (isContextFreeGrammar(grammarSubmission.getSubmissionObject())){
            return  TaskType.CFG;
        }
        return TaskType.NCFG;
    }

    /**
     * method to determine if grammar is Context Free Grammar or in CNF form
     * @param grammar the grammar that should be tested
     * @return true if grammar is CFG in CNF
     */
    public static boolean isContextFreeGrammarAndChomsky(Grammar grammar) {
        return grammar.isCNF();
    }

    /**
     * method to determine if grammar is Context Free Grammar
     * @param grammar  grammar to check for
     * @return true if grammar is CFG, false if not
     */
    public static boolean isContextFreeGrammar(Grammar grammar) {
        return grammar.isContextFree();
    }

     /**
     * method to determine if grammar is Context Free Grammar
     * @param grammar grammar to check
     * @return true if rightlinear, false if not
     */
    private static boolean isRightlinearGrammar(Grammar grammar){
        return grammar.isRightlinear();
    }
}
