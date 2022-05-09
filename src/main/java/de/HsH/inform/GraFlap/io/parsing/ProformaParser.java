package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Mode;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.io.SilentHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setErrorHandler(SilentHandler.instance);
            Document doc = db.parse(new ByteArrayInputStream(args[1].getBytes(StandardCharsets.UTF_8))); //get node tree from xml doc
            ArrayList<Node> submissionAsList = new ArrayList<>(1); //easiest way to get a stream from an object
            submissionAsList.add(doc.getDocumentElement());

            List<Element> tests = null;
            String testid = "";
            String graflapArgumentsFileId = "";
            try {
                //workaround to split stream into two
                tests = submissionAsList.stream().flatMap(toChildElements).filter(byName("task")).flatMap(toChildElements)
                        .filter(byName("tests")).flatMap(toChildElements)
                        .filter(byName("test"))
                        .filter(byAttribute("id", "graflap"))
                        .collect(Collectors.toList());

                testid = tests.stream().filter(byName("test")).findFirst().map(toElement).get().getAttribute("id");
                graflapArgumentsFileId = tests.stream().filter(byName("test")).flatMap(toChildElements)
                                                         .filter(byName("test-configuration")).flatMap(toChildElements)
                                                         .filter(byName("filerefs")).flatMap(toChildElements)
                                                         .findFirst().get().getAttribute("refid");
            }
            catch (Exception e){}

            if(testid.equals("")) testid = "graflap";
            if(graflapArgumentsFileId.equals("")) graflapArgumentsFileId = "graflap-arguments";

            String graflapArguments = submissionAsList.stream()
                    .flatMap(toChildElements)
                    .filter(byName("task")).flatMap(toChildElements)
                    .filter(byName("files")).flatMap(toChildElements)
                    .filter(byAttribute("id", graflapArgumentsFileId)).flatMap(toChildElements)
                    .filter(byName("embedded")).flatMap(toChildNodes)
                    .filter(byIsCDATAOrText).findFirst().get().getTextContent().trim();
            arguments = super.parse(new String[]{graflapArguments});
            arguments.setTestId(testid);


            List<Element> submissionFiles = submissionAsList.stream()
                                                          .flatMap(toChildElements)
                                                          .filter(byName("files")).flatMap(toChildElements)
                                                          .collect(Collectors.toList());
            String studentAnswerFileName = getFileNameFromMode(arguments.getMode());
            if(studentAnswerFileName.equals("internal")) throw new GraFlapException("Illegal Mode in Task");
            String studentAnswer = getFileContent(submissionFiles, studentAnswerFileName);
            arguments.setStudentAnswer(studentAnswer);

            //check if sets need to be extracted
            if(arguments.getMode().hasParts()){
                arguments.setStates(getFileContent(submissionFiles, "states"));
                arguments.setInitials(getFileContent(submissionFiles, "initials"));
                arguments.setFinals(getFileContent(submissionFiles, "finals"));
                arguments.setAlphabet(getFileContent(submissionFiles, "alphabet"));
                arguments.setTransitions(getFileContent(submissionFiles, "transitions"));
                if(arguments.getMode().isTyped() && (arguments.getType().isPushDownAutomaton() || arguments.getType().isTuring())){
                    arguments.setStackalphabet(getFileContent(submissionFiles, "stackalphabet"));
                }
            }
        }
        catch(ClassCastException | NullPointerException | SAXException | IOException | ParserConfigurationException e) {
            throw new GraFlapException("Cant parse Proforma XML - no readable XML found");
        }

        return arguments;
    }

    private String getFileContent(List<Element> list, String fileName) throws GraFlapException {
        String found = "";
        try{
             found = list.stream().flatMap(toChildElements).filter(byName("embedded"))
                    .filter(byAttribute("filename", fileName)).flatMap(toChildNodes)
                    .filter(byIsCDATAOrText).findFirst().get().getTextContent().trim();
        }
        catch (NoSuchElementException e){
            //             throw new GraFlapException("A file with name \"" + fileName + "\" expected in submission files but none found.");
        }
        return found;
    }

    /**
     * Maps Mode to expected filename in ProFormA Submission
     * @param mode the Mode of the Submission
     * @return the filename where the student answer should be
     */
    public static String getFileNameFromMode(Mode mode){
        if(mode.isGrammar()){
            return "grammar";
        }
        else if(mode.isAutomaton()){
            return "(jff|jflap)";
        }
        else{
            switch (mode){
                case MP:
                case MMW: return "(jff|jflap)"; //regex match for jff or jflap files
                case WW: return "examplewords";
                case CYK: return "cyk";
                case DER: return "derivation";
                case RR:
                case RRW: return "regex";
                case SVGA:
                case SVGG:
                case ERROR:
                default: return "internal";
            }
        }
    }
}
