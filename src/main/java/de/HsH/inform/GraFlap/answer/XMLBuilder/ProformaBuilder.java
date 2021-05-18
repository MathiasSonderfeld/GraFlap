package de.HsH.inform.GraFlap.answer.XMLBuilder;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * @author Mathias Sonderfeld
 * @version 1.0
 *
 * makes an Proforma-XML String from given answerMessage.
 * requires Jdom-2.0.6 Library.
 */
public class ProformaBuilder implements OutputBuilder {

    /**
     * converts AnswerMessage to XML in Proforma-Style with "proforma" as Namespace
     * @param answerMessage the answerMessage to convert into XML
     * @throws NullPointerException if input is null
     * @return String containing the whole xml document
     */
    public String getOutput( AnswerMessage answerMessage ) {
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        if ("asvg".equals(answerMessage.getTaskMode())) {
            return out.outputString(answerMessage.getSvgImage());
        }

        Namespace proforma = Namespace.getNamespace("proforma", "urn:proforma:v2.1");
        Namespace xs = Namespace.getNamespace("xs", "http://www.w3.org/2001/XMLSchema");

        Element response = new Element("response");
        response.setNamespace(proforma);


        Element seperateTestFeedback = new Element("separate-test-feedback");
        seperateTestFeedback.setNamespace(proforma);
        response.addContent(seperateTestFeedback);

        Element submissionFeedbackList = new Element("submission-feedback-list");
        submissionFeedbackList.setNamespace(proforma);
        seperateTestFeedback.addContent(submissionFeedbackList);

        Element testsResponse = new Element("tests-response");
        testsResponse.setNamespace(proforma);
        seperateTestFeedback.addContent(testsResponse);

        Element testResponse = new Element("test-response");
        testResponse.setNamespace(proforma);
        testResponse.setAttribute("id", answerMessage.getTaskTitle());
        testsResponse.addContent(testResponse);

        Element testResult = new Element("test-result");
        testResult.setNamespace(proforma);
        testResponse.addContent(testResult);

        Element result = new Element("result");
        result.setNamespace(proforma);
        testResult.addContent(result);

        Element testScore = new Element("score");
        testScore.setNamespace(proforma);
        testScore.addContent("" + (100 - answerMessage.getPercentOfTestWordsFailed())/100.0);
        result.addContent(testScore);

        Element testValidity = new Element("validity");
        testValidity.setNamespace(proforma);
        testValidity.addContent("1.0");
        result.addContent(testValidity);

        Element testFeedbackList = new Element("feedback-list");
        testFeedbackList.setNamespace(proforma);
        testResult.addContent(testFeedbackList);

        Element studentFeedbackTitle = new Element("student-feedback");
        studentFeedbackTitle.setNamespace(proforma);

        Element studentFeedbackTitle_title = new Element("title");
        studentFeedbackTitle_title.setNamespace(proforma);
        studentFeedbackTitle_title.addContent("TaskTitle");
        studentFeedbackTitle.addContent(studentFeedbackTitle_title);
        Element studentFeedbackTitle_content = new Element("content");
        studentFeedbackTitle_content.setAttribute("format", "plaintext");
        studentFeedbackTitle_content.setNamespace(proforma);
        studentFeedbackTitle_content.addContent(answerMessage.getTaskTitle());
        studentFeedbackTitle.addContent(studentFeedbackTitle_content);
        testFeedbackList.addContent(studentFeedbackTitle);

        Element studentFeedbackTitleSVG = new Element("student-feedback");
        studentFeedbackTitleSVG.setNamespace(proforma);
        Element studentFeedbackTitleSVG_title = new Element("title");
        studentFeedbackTitleSVG_title.setNamespace(proforma);
        studentFeedbackTitleSVG_title.addContent("SvgTitle");
        studentFeedbackTitleSVG.addContent(studentFeedbackTitleSVG_title);
        Element studentFeedbackTitleSVG_content = new Element("content");
        studentFeedbackTitleSVG_content.setAttribute("format", "plaintext");
        studentFeedbackTitleSVG_content.setNamespace(proforma);
        studentFeedbackTitleSVG_content.addContent(answerMessage.getSvgTitle());
        studentFeedbackTitleSVG.addContent(studentFeedbackTitleSVG_content);
        testFeedbackList.addContent(studentFeedbackTitleSVG);

        Element studentFeedbackImageSVG = new Element("student-feedback");
        studentFeedbackImageSVG.setNamespace(proforma);
        Element studentFeedbackImageSVG_title = new Element("title");
        studentFeedbackImageSVG_title.setNamespace(proforma);
        studentFeedbackImageSVG_title.addContent("SvgImage");
        studentFeedbackImageSVG.addContent(studentFeedbackImageSVG_title);
        Element studentFeedbackImageSVG_content = new Element("content");
        studentFeedbackImageSVG_content.setAttribute("format", "html");
        studentFeedbackImageSVG_content.setNamespace(proforma);

        String escapedSvgImage = answerMessage.getSvgImage().getText();

        studentFeedbackImageSVG_content.addContent(escapedSvgImage);
        studentFeedbackImageSVG.addContent(studentFeedbackImageSVG_content);
        testFeedbackList.addContent(studentFeedbackImageSVG);

        Element studentFeedbackResultText = new Element("student-feedback");
        studentFeedbackResultText.setNamespace(proforma);
        Element studentFeedbackResultText_title = new Element("title");
        studentFeedbackResultText_title.setNamespace(proforma);
        studentFeedbackResultText_title.addContent("ResultText");
        studentFeedbackResultText.addContent(studentFeedbackResultText_title);
        Element studentFeedbackResultText_content = new Element("content");
        studentFeedbackResultText_content.setAttribute("format", "plaintext");
        studentFeedbackResultText_content.setNamespace(proforma);
        studentFeedbackResultText_content.addContent(answerMessage.getFeedbackText());
        studentFeedbackResultText.addContent(studentFeedbackResultText_content);
        testFeedbackList.addContent(studentFeedbackResultText);

        Element files = new Element("files");
        files.setNamespace(proforma);
        response.addContent(files);

        Element responseMetaData = new Element("response-meta-data");
        responseMetaData.setNamespace(proforma);
        response.addContent(responseMetaData);

        Element graderEngine = new Element("grader-engine");
        graderEngine.setNamespace(proforma);
        graderEngine.setAttribute("name", "de.HsH.inform.GraFlap.GraFlap");
        graderEngine.setAttribute("version", "0.1");
        responseMetaData.addContent(graderEngine);

        return out.outputString(response);
    }
}
