package de.HsH.inform.GraFlap.io.formatter;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * @author Mathias Sonderfeld
 * @version 1.0
 *
 * makes an Proforma-XML String from given answerMessage.
 * requires Jdom-2.0.6 Library.
 */
public class ProformaOutputFormatter implements OutputFormatter {
    private String namespace = "proforma";
    private Document document;

    /**
     * creates a proforma xml from given AnswerMessage
     * @param answerMessage the AnswerMessage containing the values
     * @return xml as String
     */
    public String format(AnswerMessage answerMessage){
        try{
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = db.newDocument();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter stringWriter = new StringWriter();
            String svgAsString = "";
            if(answerMessage.getSvgImage() != null){
                org.jdom2.output.XMLOutputter jdom2outputter = new org.jdom2.output.XMLOutputter(org.jdom2.output.Format.getPrettyFormat());
                svgAsString = jdom2outputter.outputString(answerMessage.getSvgImage());
            }
            buildProforma(answerMessage, svgAsString);
            document.normalizeDocument();
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
            return stringWriter.toString();
        }
        catch(ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * builds the mainframe of the document
     * @param answerMessage data
     * @param svgAsString workaround because svgImage is Jdom2 Element
     */
    private void buildProforma(AnswerMessage answerMessage, String svgAsString){
        Element response;
        if ("asvg".equals(answerMessage.getTaskMode())) {
            response = document.createElement(svgAsString);
            document.appendChild(response);
            return;
        }

        response = document.createElementNS("urn:proforma:v2.1","response");
        response.setPrefix(namespace);
        document.appendChild(response);
        Element seperateTestFeedback = createElement(response, "separate-test-feedback");
        createElement(seperateTestFeedback, "submission-feedback-list");

        Element testsResponse = createElement(seperateTestFeedback, "tests-response");
        buildMainTestResponse(testsResponse, answerMessage, svgAsString);
        if(answerMessage.getTaskMode().contains("a")){
            buildSetsTestResponse(testsResponse, "states", true, "", "");
            buildSetsTestResponse(testsResponse, "initials", true, "", "");
            buildSetsTestResponse(testsResponse, "finals", true, "", "");
            buildSetsTestResponse(testsResponse, "transitions", true, "", "");
        }

        createElement(response, "files");
        Element metaData = createElement(response, "response-meta-data");
        Element graderEngine = createElement(metaData, "grader-engine");
        graderEngine.setAttribute("name","GraFlap");
        graderEngine.setAttribute("version", "0.3");

    }

    /**
     * builds Test Response Segment for general feedback
     */
    private void buildMainTestResponse(Element testsResponse, AnswerMessage answerMessage, String svgAsString){
        Element feedbackList = buildPartTestResponse(testsResponse, answerMessage.getTaskTitle(), answerMessage.hasPassed()?"1.0":"0.0", answerMessage.getPercentOfTestWordsFailed()>=0?"1.0":"0.0");
        addFeedback(feedbackList, false, "Musterloesung", "plaintext", "", true); //answerMessage.getMusterloesung()
        addFeedback(feedbackList, true, "TaskTitle", "plaintext", answerMessage.getTaskTitle(), false);
        addFeedback(feedbackList, true, "SvgTitle", "plaintext", answerMessage.getSvgTitle(), false);
        addFeedback(feedbackList, true, "SvGImage", "plaintext", svgAsString, true);
        addFeedback(feedbackList, true, "FeedbackText", "plaintext", answerMessage.getFeedback(), false);

    }

    /**
     * builds Test Response Segment for a Set Task Feedback
     */
    private void buildSetsTestResponse(Element testsResponse, String testTitle, boolean hasPassed ,String teacherFeedbackText, String studentFeedbackText){
        Element feedbackList = buildPartTestResponse(testsResponse, testTitle, hasPassed?"1.0":"0.0", "1.0");
        addFeedback(feedbackList, false, "ListOfMissing", "plaintext", teacherFeedbackText, false);
        addFeedback(feedbackList,true, "Feedback", "plaintext", studentFeedbackText, false);
    }

    /**
     * bundled to remove Redundancy
     */
    private Element buildPartTestResponse(Element testsResponse, String id, String scoreValue, String validityValue){
        Element testResponse = createElement(testsResponse, "test-response", "id", id);
        Element testResult = createElement(testResponse, "test-result");
        Element result = createElement(testResult, "result");
        Element score = createElement(result, "score");
        score.appendChild(document.createTextNode(scoreValue));
        Element validity = createElement(result, "validity");
        validity.appendChild(document.createTextNode(validityValue));
        Element feedbackList = createElement(testResult, "feedback-list");
        return feedbackList;
    }

    /**
     * adds a feedback-list element
     */
    private void addFeedback(Element feedbackList, boolean isStudentFeedback, String title, String formatValue, String content, boolean asCDATA){
        Element feedback;
        if(isStudentFeedback){
            feedback = createElement(feedbackList, "student-feedback");
        }
        else{
            feedback = createElement(feedbackList, "teacher-feedback");
        }
        Element fbTitle = createElement(feedback, "title");
        fbTitle.appendChild(document.createTextNode(title));

        Element fbContent = createElement(feedback, "content", "format", formatValue);
        if(asCDATA){
            fbContent.appendChild(document.createCDATASection(content));
        }
        else{
            fbContent.appendChild(document.createTextNode(content));
        }
    }


    private Element createElement(Element parent, String name){
        Element e = document.createElementNS("urn:proforma:v2.1",name);
        e.setPrefix(namespace);
        parent.appendChild(e);
        return e;
    }

    private Element createElement(Element parent, String name, String attributeName, String attributeValue){
        Element e = createElement(parent, name);
        e.setAttribute(attributeName, attributeValue);
        return e;
    }
}
