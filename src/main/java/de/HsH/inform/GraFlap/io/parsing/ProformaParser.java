package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.HsH.inform.GraFlap.io.XmlStreamConstants.*;

/**
 * parses Input-Data in Proforma-XML 2.1 Format to Arguments Object
 * @author Mathias Sonderfeld
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class ProformaParser extends ArgumentsParser{

    public ProformaParser(){
        super();
    }
    /**
     * parses the Input structured as Proforma-XMl 2.1 Format and returns an Arguments Object
     * @param args the input arguments, only the second element of the array gets processed.
     * @return Arguments Object
     * @throws GraFlapException if input constraints are infringed
     */
    public Arguments parse( String[] args ) throws GraFlapException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Arguments arguments = null;
        try {
            Document doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(args[1].getBytes(StandardCharsets.UTF_8)));
            ArrayList<Node> submissionAsList = new ArrayList<>(1);
            submissionAsList.add(doc.getDocumentElement());

            String graflapArguments = submissionAsList.stream()
                                                    .flatMap(toChildElements)
                                                    .filter(byName("task")).flatMap(toChildElements)
                                                    .filter(byName("files")).flatMap(toChildElements)
                                                    .filter(byAttribute("id","graflap-arguments")).flatMap(toChildElements)
                                                    .filter(byName("embedded")).flatMap(toChildNodes)
                                                    .filter(byIsCDATAOrText).findFirst().get().getTextContent().trim();
            String testid = "";
            try {
                testid = submissionAsList.stream()
                        .flatMap(toChildElements)
                        .filter(byName("task")).flatMap(toChildElements)
                        .filter(byName("tests")).flatMap(toChildElements)
                        .filter(byName("test")).findFirst().map(toElement).get().getAttribute("id");
            }
            catch (Exception e){}

            List<Element> submissionFiles = submissionAsList.stream()
                                                          .flatMap(toChildElements)
                                                          .filter(byName("files")).flatMap(toChildElements)
                                                          .collect(Collectors.toList());

            arguments = new LoncapaParser().parse(new String[]{graflapArguments, ""});
            arguments.setTestId(testid);

            String studentAnswerFileName = null;
            if(arguments.getTaskMode().isGrammar()) studentAnswerFileName = "grammar";
            if(arguments.getTaskMode().isAutomaton()) studentAnswerFileName = "\\.*(jff|jflap)";
            else{ studentAnswerFileName = "exampleWords";}

            String studentAnswer = submissionFiles.stream().flatMap(toChildElements)
                    .filter(byName("embedded")).filter(byAttribute("filename", studentAnswerFileName)).flatMap(toChildNodes)
                    .filter(byIsCDATAOrText).findFirst().get().getTextContent().trim();
            arguments.setStudentAnswer(studentAnswer);

            //check if sets need to be extracted
            if(arguments.getTaskMode().isParameterized()){
                List<Element> otherEmbeddedFiles = submissionFiles.stream()
                                                                  .filter(byAttribute("id", "studentAnswer").negate())
                                                                  .flatMap(toChildElements).collect(Collectors.toList());

                String states = otherEmbeddedFiles.stream()
                                                  .filter(byAttribute("filename", "states")).flatMap(toChildNodes)
                                                  .filter(byIsCDATAOrText).findFirst().get().getTextContent().trim();
                arguments.setStates(states);

                String initials = otherEmbeddedFiles.stream()
                                                    .filter(byAttribute("filename", "initials")).flatMap(toChildNodes)
                                                    .filter(byIsCDATAOrText).findFirst().get().getTextContent().trim();
                arguments.setInitials(initials);

                String finals = otherEmbeddedFiles.stream()
                                                  .filter(byAttribute("filename", "finals")).flatMap(toChildNodes)
                                                  .filter(byIsCDATAOrText).findFirst().get().getTextContent().trim();
                arguments.setFinals(finals);

                String alphabet = otherEmbeddedFiles.stream()
                                                    .filter(byAttribute("filename", "alphabet")).flatMap(toChildNodes)
                                                    .filter(byIsCDATAOrText).findFirst().get().getTextContent().trim();
                arguments.setAlphabet(alphabet);

                String stackalphabet = otherEmbeddedFiles.stream()
                                                         .filter(byAttribute("filename", "stackalphabet")).flatMap(toChildNodes)
                                                         .filter(byIsCDATAOrText).findFirst().get().getTextContent().trim();
                arguments.setStackalphabet(stackalphabet);

                String transitions = otherEmbeddedFiles.stream()
                                                         .filter(byAttribute("filename", "transitions")).flatMap(toChildNodes)
                                                         .filter(byIsCDATAOrText).findFirst().get().getTextContent().trim();
                arguments.setTransitions(transitions);
            }
        }
        catch(ClassCastException | NullPointerException | SAXException | IOException | ParserConfigurationException e) {
            throw new GraFlapException("Cant parse Proforma XML");
        }
        return arguments;
    }
}
