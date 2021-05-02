package de.HsH.inform.GraFlap.loncapa.svg;

import de.HsH.inform.GraFlap.loncapa.entity.InputType;
import de.HsH.inform.GraFlap.loncapa.entity.Mode;
import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;
import de.HsH.inform.GraFlap.loncapa.main.LoncapaArguments;

/**
 * Static factory class to return the correct svg builder based on the input
 * @author Benjamin Held (04-24-2016)
 * @since 08-03-2016
 * @version 0.2.2
 */
public class SvgFactory {

    public static SvgBuilder determineBuilder(LoncapaArguments arguments, InputType inputType, Mode mode)
            throws GraFlapException {
        switch (inputType) {
            case ALPHABET:
                return new SvgGrammarBuilder(arguments);
            case CYKINPUT:
                return new SvgCYKBuilder(arguments);
            case DERIVATION:
                return new SvgGrammarBuilder(arguments);
            case GRAMMAR:
                return new SvgGrammarBuilder(arguments);
            case JFFSTRUCTURE:
                return new SvgJffBuilder(arguments, mode);
            case JFFTURING:
                return new SvgJffBuilder(arguments, mode);
            case JFLAPSTRUCTURE:
                return new SvgJflapBuilder(arguments, mode);
            case WORDS:
                return new SvgGrammarBuilder(arguments);
            case UNDEFINED:
            default:
                throw new GraFlapException("ERROR [SvgFactory]: the type of the input could not be determined.");
        }
    }
}
