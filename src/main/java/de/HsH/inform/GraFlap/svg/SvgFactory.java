package de.HsH.inform.GraFlap.svg;

import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.SubmissionType;
import de.HsH.inform.GraFlap.exception.GraFlapException;

/**
 * Static factory class to return the correct svg builder based on the input
 * @author Benjamin Held (04-24-2016)
 * @since 08-03-2016
 * @version 0.2.2
 */
public class SvgFactory {

    public static SvgBuilder determineBuilder( Arguments arguments, SubmissionType SubmissionType, boolean isSVGA )
            throws GraFlapException {
        switch (SubmissionType) {
            case ALPHABET:
                return new SvgGrammarBuilder(arguments);
            case CYKINPUT:
                return new SvgCYKBuilder(arguments);
            case DERIVATION:
                return new SvgGrammarBuilder(arguments);
            case GRAMMAR:
                return new SvgGrammarBuilder(arguments);
            case JFFSTRUCTURE:
                return new SvgJffBuilder(arguments, isSVGA);
            case JFFTURING:
                return new SvgJffBuilder(arguments, isSVGA);
            case JFLAPSTRUCTURE:
                return new SvgJflapBuilder(arguments, isSVGA);
            case WORDS:
                return new SvgGrammarBuilder(arguments);
            case UNDEFINED:
            default:
                throw new GraFlapException("ERROR [SvgFactory]: the type of the input could not be determined.");
        }
    }
}
