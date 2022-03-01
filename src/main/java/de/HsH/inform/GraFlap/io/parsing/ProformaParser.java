package de.HsH.inform.GraFlap.io.parsing;

import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.TaskMode;
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
            Document doc = db.parse(new ByteArrayInputStream(args[1].getBytes(StandardCharsets.UTF_8)));
            ArrayList<Node> submissionAsList = new ArrayList<>(1);
            submissionAsList.add(doc.getDocumentElement());



            List<Element> tests = null;
            String testid = "";
            String graflapArgumentsFileName = "";
            try {
                tests = submissionAsList.stream().flatMap(toChildElements).filter(byName("task")).flatMap(toChildElements)
                        .filter(byName("tests")).flatMap(toChildElements)
                        .filter(byName("test"))
                        .filter(byAttribute("id", "graflap"))
                        .collect(Collectors.toList());

                testid = tests.stream().filter(byName("test")).findFirst().map(toElement).get().getAttribute("id");
                graflapArgumentsFileName = tests.stream().filter(byName("test")).flatMap(toChildElements)
                                                         .filter(byName("test-configuration")).flatMap(toChildElements)
                                                         .filter(byName("filerefs")).flatMap(toChildElements)
                                                         .findFirst().get().getAttribute("refid");
            }
            catch (Exception e){}

            if(testid.equals("")) testid = "graflap";
            if(graflapArgumentsFileName.equals("")) graflapArgumentsFileName = "graflap-arguments";

            String graflapArguments = submissionAsList.stream()
                    .flatMap(toChildElements)
                    .filter(byName("task")).flatMap(toChildElements)
                    .filter(byName("files")).flatMap(toChildElements)
                    .filter(byAttribute("id", graflapArgumentsFileName)).flatMap(toChildElements)
                    .filter(byName("embedded")).flatMap(toChildNodes)
                    .filter(byIsCDATAOrText).findFirst().get().getTextContent().trim();
            arguments = new LoncapaParser().parse(new String[]{graflapArguments, ""});
            arguments.setTestId(testid);


            List<Element> submissionFiles = submissionAsList.stream()
                                                          .flatMap(toChildElements)
                                                          .filter(byName("files")).flatMap(toChildElements)
                                                          .collect(Collectors.toList());
            String studentAnswerFileName = getFileNameFromTaskMode(arguments.getTaskMode());
            if(studentAnswerFileName.equals("internal")) throw new GraFlapException("Illegal Taskmode");
            String studentAnswer = getFileContent(submissionFiles, studentAnswerFileName);
            arguments.setStudentAnswer(studentAnswer);

            //check if sets need to be extracted
            if(arguments.getTaskMode().isParameterized()){
                arguments.setStates(getFileContent(submissionFiles, "states"));
                arguments.setInitials(getFileContent(submissionFiles, "initials"));
                arguments.setFinals(getFileContent(submissionFiles, "finals"));
                arguments.setAlphabet(getFileContent(submissionFiles, "alphabet"));
                arguments.setTransitions(getFileContent(submissionFiles, "transitions"));
                if(arguments.getTaskMode().isTyped() && (arguments.getTaskType().isPushDownAutomaton() || arguments.getTaskType().isTuring())){
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
     * Maps TaskMode to expected filename in ProFormA Submission
     * @param taskMode the TaskMode of the Submission
     * @return the filename where the student answer should be
     */
    public static String getFileNameFromTaskMode(TaskMode taskMode){
        if(taskMode.isGrammar()){
            return "grammar";
        }
        else if(taskMode.isAutomaton()){
            return "(jff|jflap)";
        }
        else{
            switch (taskMode){
                case MP:
                case MMW: return "(jff|jflap)";
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
