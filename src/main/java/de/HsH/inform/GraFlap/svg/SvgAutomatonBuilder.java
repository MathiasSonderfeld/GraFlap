package de.HsH.inform.GraFlap.svg;

import de.HsH.inform.GraFlap.entity.OperationMode;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.jdom2.Document;
import org.jdom2.Element;
import de.HsH.inform.GraFlap.file.DOMFactory;

import java.awt.geom.Point2D;
import java.io.*;

/**
 * Child class of the {@link SvgBuilder} that creates a result svg for a jflap automaton
 * @author Benjamin Held (04-30-2016)
 * @since 07-06-2016
 * @version 0.2.0
 */
abstract class SvgAutomatonBuilder extends SvgBuilder {
    /**
     * coordinates for the invisible starting point
     */
    Point2D.Double start;
    /**
     * scaling factor of the coordinates
     */
    final double SCALING = 50.0;

    SvgAutomatonBuilder( OperationMode operationMode ) {
        super(operationMode);
    }

    @Override
    /**
     * implementation of the method to build the svg image for an automaton submission
     * @param answer the given submission
     * @throws GraFlapException throws {@link GraFlapException} if a svg image cannot be created from the submission
     */
    void buildSvgElement(String answer) throws GraFlapException {
        transformGraphVisToSvg(transformXMLToGraphVis(answer));
        String errorMessage = "ERROR - Cannot open SVG string for building the SVG image. \n";
        Document doc = DOMFactory.buildDocument(svgString, errorMessage);
        svgElement = doc.detachRootElement();
    }

    /**
     * method to generate the transition string for a pda transition
     * @param element the considered element
     * @return the formatted string for the transformation
     */
    String buildPDATransitionText(Element element) {
        String pop = element.getChildText("pop");
        if (pop.isEmpty()) {
            pop = "E";
        }
        String push = element.getChildText("push");
        if (push.isEmpty()) {
            push = "E";
        }
        return " , " + pop + " ; " + push;
    }

    /**
     * abstract method to generate a graphvis object of the given content
     * @param givenString the submission string from LonCapa
     * @return an XML coded string that contains the graphvis object of the given content
     * @throws GraFlapException throws {@link GraFlapException} if the content cannot be used or has errors
     */
    abstract String transformXMLToGraphVis(String givenString) throws GraFlapException;

    /**
     * method to transform the graphvis object into a svg image
     * @param gvString the graphvis string
     * @throws GraFlapException throws {@link GraFlapException} if the transformation to a svg image fails
     */
    private void transformGraphVisToSvg(String gvString) throws GraFlapException {
        ProcessBuilder pb = new ProcessBuilder();
        pb.command(filepath, "-Tsvg");
        pb.redirectErrorStream(true);
        BufferedReader graphVizResult;
        PrintWriter graphVizInput;

        Process graphViz = null;
        try {
            graphViz = pb.start();
            graphVizResult = new BufferedReader(new InputStreamReader(graphViz.getInputStream()));
            graphVizInput = new PrintWriter(graphViz.getOutputStream());

            graphVizInput.println(gvString);
            graphVizInput.flush();
            graphVizInput.close();

            graphViz.waitFor();

            String line;
            if ((line = graphVizResult.readLine()) != null) {
                svgString +=line;
            }
            graphVizResult.readLine();
            graphVizResult.readLine();
            while ((line = graphVizResult.readLine()) != null) {
                svgString += line;
            }
        } catch ( IOException | InterruptedException e) {
            throw new GraFlapException("ERROR - Problems with showing the automaton in Graphviz. " + e.getMessage());
        } finally {
            if (graphViz != null) {
                graphViz.destroy();
            }
        }
    }
}
