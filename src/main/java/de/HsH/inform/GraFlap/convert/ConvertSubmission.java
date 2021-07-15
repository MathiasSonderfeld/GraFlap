package de.HsH.inform.GraFlap.convert;

import de.HsH.inform.GraFlap.JflapWrapper.automaton.Automaton;
import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.JflapWrapper.exception.JffTuringException;
import de.HsH.inform.GraFlap.JflapWrapper.file.Transducer;
import de.HsH.inform.GraFlap.JflapWrapper.file.TuringConverter;
import de.HsH.inform.GraFlap.JflapWrapper.grammar.Grammar;
import de.HsH.inform.GraFlap.entity.SubmissionType;
import de.HsH.inform.GraFlap.entity.ValuePair;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.util.EmptyErrorHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 *  helper class with static method to read and convert the given submission string
 *  @author Frauke Sprengel (08-15-2015)
 *  @author Benjamin Held (04-09-2016)
 *  @since 04-14-2016
 *  @version 0.3.3
 */
public class ConvertSubmission {

    /**
     * method to generate an automaton from the given string input
     * @param submissionString the submission string
     * @return the {@link Submission} object with the automaton that is produced from the submission string
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further in the calling hierarchy
     */
    public static Submission<Automaton> openAutomaton(String submissionString) throws GraFlapException {
        ValuePair<Object, SubmissionType> input = openString(submissionString);
        if (input.getValue() == SubmissionType.JFFTURING) {
            return new Submission<>(submissionString, new Automaton(((Automaton) input.getKey()).getJFLAPAutomaton()), input.getValue());
        }
        return new Submission<>(submissionString, new Automaton(input.getKey()), input.getValue());
    }

    /**
     * method to generate a grammar from the given string input
     * @param submissionString the submission string
     * @return the {@link Submission} object with the grammar that is produced from the submission string
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further in the calling hierarchy
     */
    public static Submission<Grammar> openGrammar(String submissionString) throws GraFlapException {
        ValuePair<Object, SubmissionType> input = openString(submissionString);
        return new Submission<>(submissionString, new Grammar(input.getKey()), SubmissionType.GRAMMAR);
    }

    /**
     * method to generate a list of test words from the given string input
     * @param submissionString the submission string
     * @param numberOfWords the number of given test words
     * @return the {@link Submission} object with the words that are parsed from the submission string
     * @throws GraFlapException throws a {@link GraFlapException} if the number of parsed words is not equal with
     *    the provided number
     */
    public static Submission<String[]> openWords(String submissionString, int numberOfWords) throws GraFlapException {
        String[] words = submissionString.replace(" ","").split(",");

        if (words.length < numberOfWords) {
            throw new GraFlapException("Error - not enough words.");
        } else {
            return new Submission<>(submissionString, words, SubmissionType.WORDS);
        }
    }

    /**
     * static method to read a given convert-automaton from a string and return it as an object. also checks for formal
     * errors within the automaton and throws an exception if that is the case.
     * @param submissionString is the String with the submission
     * @return a key value pair containing the created object and an indicator which jflap format was submitted
     * @throws GraFlapException when there is a formal error within the automaton
     */
    private static ValuePair<Object, SubmissionType> openString( String submissionString) throws GraFlapException {
        try {
            InputStream stream = new ByteArrayInputStream(submissionString.getBytes(StandardCharsets.UTF_8));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(EmptyErrorHandler.instance);
            Document doc = builder.parse(stream);
            Transducer transducer = new Transducer();
            Object obj = transducer.parseDocument(doc);
            if (transducer.getInputType() == SubmissionType.JFFSTRUCTURE) {
                checkJFFComponents(doc);
            }
            return new ValuePair<>(obj, transducer.getInputType());
        } catch (JffTuringException ex) {
            Automaton turing = new TuringConverter(submissionString).getAutomaton();
            return new ValuePair<>(turing, SubmissionType.JFFTURING);
        } catch(GraFlapException ex) {
            throw new GraFlapException("ERROR - Cannot open JFF" + ex.getMessage());
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new GraFlapException("Parsing error - Cannot open JFF");
        }
    }

    /**
     * method to check the parsed document for correct components
     * @param doc the parsed document
     * @throws GraFlapException throws a {@link GraFlapException} if formal components are missing
     */
    private static void checkJFFComponents(Document doc) throws GraFlapException {
        StringBuilder errorString = new StringBuilder();
        boolean hasFormalError = false;
        Element elem = doc.getDocumentElement();
        NodeList auto = elem.getElementsByTagName("automaton");
        String type = elem.getElementsByTagName("type").item(0).getTextContent();

        if (auto.getLength() > 0) {
            if (elem.getElementsByTagName("initial").getLength() == 0) {
                hasFormalError = true;
                errorString.append("\nNo initial state found. ");
            }
            if (elem.getElementsByTagName("final").getLength() == 0 &&
                !(type.equals("mealy") || type.equals("moore"))) {
                hasFormalError = true;
                errorString.append("\nNo final state found. ");
            }
            if (processesMoreThanOneCharacter(elem.getElementsByTagName("read"))) {
                hasFormalError = true;
                errorString.append("\nPlease use only one character reading from the stack. ");
            }
            if (processesMoreThanOneCharacter(elem.getElementsByTagName("pop"))) {
                hasFormalError = true;
                errorString.append("\nPlease use only one character reading from the stack. ");
            }
        }
        if (hasFormalError) {
            throw new GraFlapException(errorString.toString());
        }
    }

    /**
     * static method to check if only one character has been processed
     * @param nodeList the element list specified by the given tag
     * @return true, if more then one character is processed per step, false if not
     */
    private static boolean processesMoreThanOneCharacter(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++){
            Node r = nodeList.item(i);
            if (r.getTextContent().length() > 1){
                return true;
            }
        }
        return false;
    }
}
