package de.HsH.inform.GraFlap.svg;

import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Child class of the {@link SvgBuilder} that creates a result svg for a grammar
 * @author Benjamin Held (04-24-2016)
 * @since 05-01-2016
 * @version 0.1.1
 */
class SvgGrammarBuilder extends SvgBuilder {

    SvgGrammarBuilder( Arguments arguments) {
        buildSvgElement(arguments.getStudentAnswer());
    }

    @Override
    /**
     * implementation of the method to build the svg image for a grammar submission
     * @param answer the given submission
     * @throws GraFlapException throws {@link GraFlapException} if a svg image cannot be created from the submission
     */
    void buildSvgElement(String answer) {
        svgElement = new Element("p");
        svgElement.addContent(answer);

        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        svgString = out.outputString(svgElement);
    }
}
