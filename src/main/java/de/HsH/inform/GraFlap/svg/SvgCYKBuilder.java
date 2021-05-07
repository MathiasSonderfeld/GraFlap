package de.HsH.inform.GraFlap.svg;

import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.entity.Arguments;
import org.jdom2.Document;
import de.HsH.inform.GraFlap.JflapWrapper.file.DOMFactory;

/**
 * Child class of the {@link SvgBuilder} that creates result output for cyk exercises
 * @author Benjamin Held (07-13-2016)
 * @since 07-25-2016
 * @version 0.1.0
 */
public class SvgCYKBuilder extends SvgBuilder {

    SvgCYKBuilder( Arguments arguments) throws GraFlapException {
        buildSvgElement(arguments.getStudentAnswer());
    }

    /**
     * implementation of the method to build the svg image for a grammar submission
     * @param answer the given submission
     */
    @Override
    void buildSvgElement(String answer) throws GraFlapException {
        String[] lines = answer.split("%");

        StringBuilder svgBuilder = new StringBuilder();
        svgBuilder.append("<svg width=\"500\" height=\"150\">");
        svgBuilder.append("<text y=\"0\">");
        for (String line: lines) {
            String[] elements = line.split(";");
            StringBuilder sb = new StringBuilder("<tspan x=\"0\" dy=\"1.4em\">| ");
            for (String element: elements) {
                sb.append(element).append(" | ");
            }
            sb.append("</tspan>");
            svgBuilder.append(sb);
        }
        svgBuilder.append("</text>");
        svgBuilder.append("</svg>");

        String errorMessage = "ERROR - Cannot open SVG string for building the SVG image. \n";
        Document doc = DOMFactory.buildDocument(svgBuilder.toString(), errorMessage);
        svgElement = doc.detachRootElement();
        svgString = svgBuilder.toString();
    }

}