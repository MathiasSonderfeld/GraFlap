package de.HsH.inform.GraFlap.svg;

import de.HsH.inform.GraFlap.entity.OperationMode;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.jdom2.Element;

/**
 * abstract class to generate an svg image for the submitted solution
 * @author Frauke Sprengel (08-14-2015)
 * @author Benjamin Held (04-09-2016)
 * @since 07-06-2016
 * @version 0.4.0
 */
public abstract class SvgBuilder {
    /**
     * the string that holds the coded svg information
     */
    String svgString = "";
    /**
     * the svg element that will be used
     */
    Element svgElement = null;
    /**
     * the function mode, determined by the submission
     */
    OperationMode operationMode;
    /**
     * string pointing to the required directory
     */
    static String filepath;

    public SvgBuilder() {
        operationMode = OperationMode.DEFAULT;
        filepath = "C:\\Program Files\\Graphviz\\bin\\fdp.exe";
    }

    public SvgBuilder( OperationMode operationMode ) {
        this.operationMode = operationMode;
        if (operationMode == OperationMode.SVGA) {
            filepath = "C:\\Program Files\\Graphviz\\bin\\dot.exe";
        } else {
            filepath = "C:\\Program Files\\Graphviz\\bin\\fdp.exe";
        }
    }

    /**
     * simple getter to return the svg string
     * @return the svg string
     */
    public String getSvgString() {
        return svgString;
    }

    /**
     * simple getter to return the svg element
     * @return the svg element
     */
    public Element getSvg() {
        return svgElement;
    }

    /**
     * abstract method to build the svg image for the given submission
     * @param answer the given submission
     * @throws GraFlapException throws {@link GraFlapException} if a svg image cannot be created from the submission
     */
    abstract void buildSvgElement(String answer) throws GraFlapException;
}