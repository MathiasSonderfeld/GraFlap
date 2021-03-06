package de.HsH.inform.GraFlap.io.formatter;

import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.answerMessage.AnswerMessage;
import de.HsH.inform.GraFlap.entity.MetaData;
import de.HsH.inform.GraFlap.entity.Mode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.Date;

/**
 * makes an Proforma-2.1-XML String from a given answerMessage.
 * @author Mathias Sonderfeld
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class ProformaOutputFormatter implements OutputFormatter {
    private String namespace = "proforma";
    private Document document;
    private MetaData metaData;
    /**
     * creates a proforma xml from given AnswerMessage
     * @param answerMessage the AnswerMessage containing the values
     * @return xml as String
     */
    public String format(AnswerMessage answerMessage, MetaData metaData){
        if (answerMessage.getMode() == Mode.SVGA) {
            return answerMessage.getSvgImage();
        }
        this.metaData = metaData;
        try{
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter stringWriter = new StringWriter();
            document = db.newDocument();
            buildProforma(answerMessage);
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
     */
    private void buildProforma(AnswerMessage answerMessage){
        Element response = document.createElementNS("urn:proforma:v2.1","response");
        response.setPrefix(namespace);
        document.appendChild(response);
        Element seperateTestFeedback = createElement(response, "separate-test-feedback");
        createElement(seperateTestFeedback, "submission-feedback-list");
        Element testsResponse = createElement(seperateTestFeedback, "tests-response");
        buildMainTestResponse(testsResponse, answerMessage, answerMessage.getSvgImage());

        if(answerMessage.getMode().hasParts() || answerMessage.getMode() == Mode.AA){
            buildSetsTestResponse(testsResponse, answerMessage.getFeedbackTitle(), "states", answerMessage.getStatesScore(), answerMessage.getStatesTeacherFeedback(), answerMessage.getStatesStudentFeedback());
            buildSetsTestResponse(testsResponse, answerMessage.getFeedbackTitle(), "initials", answerMessage.getInitialsScore(), answerMessage.getInitialsTeacherFeedback(), answerMessage.getInitialsStudentFeedback());
            buildSetsTestResponse(testsResponse, answerMessage.getFeedbackTitle(), "finals", answerMessage.getFinalsScore(), answerMessage.getFinalsTeacherFeedback(), answerMessage.getFinalsStudentFeedback());
            buildSetsTestResponse(testsResponse, answerMessage.getFeedbackTitle(), "transitions", answerMessage.getTransitionsScore(), answerMessage.getTransitionsTeacherFeedback(), answerMessage.getTransitionsStudentFeedback());
            buildSetsTestResponse(testsResponse, answerMessage.getFeedbackTitle(), "alphabet", answerMessage.getAlphabetScore(), answerMessage.getAlphabetTeacherFeedback(), answerMessage.getAlphabetStudentFeedback());
            if(answerMessage.getTaskType().isPushDownAutomaton()){
                buildSetsTestResponse(testsResponse, answerMessage.getFeedbackTitle(), "stackalphabet", answerMessage.getStackAlphabetScore(), answerMessage.getStackAlphabetTeacherFeedback(), answerMessage.getStackAlphabetStudentFeedback());
            }
        }

        createElement(response, "files");
        Element metaData = createElement(response, "response-meta-data");
        Element graderEngine = createElement(metaData, "grader-engine");
        graderEngine.setAttribute("name","GraFlap");
        graderEngine.setAttribute("version", "" + GraFlap.version);
        Element dateTime = createElement(metaData, "grade-date");
        dateTime.setAttribute("datetime", "" + new Date().getTime());
    }

    /**
     * builds Test Response Segment for general feedback
     */
    private void buildMainTestResponse(Element testsResponse, AnswerMessage answerMessage, String svgAsString){
        Element feedbackList = buildTestResponsePart(testsResponse, metaData.getTestID(), "" + answerMessage.getScore(), !answerMessage.isError()?"1.0":"0.0");
        //addFeedback(feedbackList, false, "Musterloesung", "plaintext", "", true); //answerMessage.getMusterloesung()
        addFeedback(feedbackList, false, "Time", "html", answerMessage.getTime(), false); //answerMessage.getMusterloesung()
        addFeedback(feedbackList, true, answerMessage.getSvgTitle(), "html", svgAsString, true);
        addFeedback(feedbackList, true, answerMessage.getFeedbackTitle(), "html", answerMessage.getFeedback(), true);
        String warnings = answerMessage.getWarnings();
        if(warnings.length() > 0){
            addFeedback(feedbackList, false, "Warnings", "html", warnings, true);
        }
        String extra = answerMessage.getTeachersExtra();
        if (!(extra==null)){
            if(extra.length()>0) {
                addFeedback(feedbackList, false, "Extras", "html", extra, true);
            }
        }
    }

    /**
     * builds Test Response Segment for a Set Task Feedback
     */
    private void buildSetsTestResponse(Element testsResponse, String feedbackTitle, String testTitle, double score ,String teacherFeedbackText, String studentFeedbackText){
        Element feedbackList = buildTestResponsePart(testsResponse, testTitle, "" + score, "1.0");
        addFeedback(feedbackList, false, feedbackTitle, "html", teacherFeedbackText, false);
        addFeedback(feedbackList,true, feedbackTitle, "html", studentFeedbackText, false);
        System.out.println("");
    }

    /**
     * bundled to remove Redundancy
     */
    private Element buildTestResponsePart(Element testsResponse, String id, String scoreValue, String validityValue){
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
            feedback = createElement(feedbackList, "student-feedback","level","info");
        }
        else{
            feedback = createElement(feedbackList, "teacher-feedback","level","info");
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
