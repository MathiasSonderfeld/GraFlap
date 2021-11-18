package de.HsH.inform.GraFlap.svg;

import de.HsH.inform.GraFlap.JflapWrapper.file.DOMFactory;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.jdom2.Document;
import org.jdom2.Element;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Child class of the {@link SvgBuilder} that creates a result svg for a jflap automaton
 * @author Benjamin Held (04-30-2016)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
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
    protected String emptyWord = "&#949;";
    protected String bottom = "&perp;";

    /**
     * zu ersetzen
     * E = "&#949;";  // epsilon
     * Z = "&perp;";
     * einfügen
     *  <style> svg { max-width: 100%; max-height: 100%; } </style>
     */

    SvgAutomatonBuilder( boolean isSVGA ) {
        super(isSVGA);
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
            pop = emptyWord;
        }
        String push = element.getChildText("push");
        if (push.isEmpty()) {
            push = emptyWord;
        }
        String transText = " , " + pop + " ; " + push;
        transText = transText.replaceAll("Z",bottom);
        return transText;
    }

    /**
     * abstract method to generate a graphvis object of the given content
     * @param givenString the submission string from Input
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

            /*
             * GraphWiz expects more than one Input on Windows so I had to adapt this code accordingly.
             * Graflap needs to send a SIGTERM after the task has been completed. Sending a SIGTERM is done via graphwiz.destroy().
             * But Graflap has to wait for Graphwiz to do its job before sending SIGTERM.
             * To achieve that I marked the beginning of the Graphwiz output and wait for the answer to appear.
             * Then I reset the buffer to the mark at the beginning and then send SIGTERM. That way the buffer is at the beginning of the answer,
             * Graphwiz has completed the task and SIGTERM is sent correctly.
             */
            graphVizInput.println(gvString);
            graphVizInput.flush();
            graphVizInput.close();

            String line;
            if ((line = graphVizResult.readLine()) != null) {
                svgString +=line;
            }
            // omit the DOCTYPE ...
            graphVizResult.readLine();
            graphVizResult.readLine();
            while ((line = graphVizResult.readLine()) != null) {
                // omit the background polygon
                if (!line.startsWith("<polygon fill=")) {
                    svgString += line;
                }
            }
            graphViz.destroy();
            graphViz.waitFor();
        } catch ( IOException | InterruptedException e) {
            e.printStackTrace();
            throw new GraFlapException("ERROR - Problems with showing the automaton in Graphviz. " + e.getMessage());
        } finally {
            if (graphViz != null) {
                graphViz.destroy();
            }
        }
    }
}
