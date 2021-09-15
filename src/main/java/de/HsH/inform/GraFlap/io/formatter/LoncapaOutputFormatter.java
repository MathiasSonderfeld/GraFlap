package de.HsH.inform.GraFlap.io.formatter;

import de.HsH.inform.GraFlap.answerMessage.AnswerMessage;
import de.HsH.inform.GraFlap.entity.MetaData;
import de.HsH.inform.GraFlap.entity.TaskMode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * makes an XML String, that can be passed to Loncapa, from given answerMessage.
 * @author Mathias Sonderfeld
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class LoncapaOutputFormatter implements OutputFormatter {
    /**
     * converts AnswerMessage to XML in Loncapa-Style
     * @param answerMessage the answerMessage to convert into XML
     * @throws NullPointerException if input is null
     * @return String containing the whole xml document
     */
    public String format( AnswerMessage answerMessage, MetaData metaData ) {
        if (answerMessage.getTaskMode() == TaskMode.SVGA) {
            return answerMessage.getSvgImage();
        }
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter stringWriter = new StringWriter();
            String award, grade;

            if (answerMessage.getPercentOfTestWordsFailed() < 0) {
                award = "ERROR";
                grade = "error";
            } else if (answerMessage.hasPassed()) {
                award = "EXACT_ANS";
                grade = "passed";
            } else {
                award = "INCORRECT";
                grade = "failed";
            }

            Element root = document.createElement("loncapagrade");
            Element awardDetail = document.createElement("awarddetail");
            Element taskResult = document.createElement("taskresult");
            Element taskTitle = document.createElement("tasktitle");
            Element titlesvg = document.createElement("titlesvg");
            Element resulttext = document.createElement("resulttext");
            Element message = document.createElement("message");

            root.appendChild(awardDetail);

            awardDetail.appendChild(document.createTextNode(award));

            taskResult.setAttribute("grade", grade);
            taskResult.appendChild(taskTitle);
            taskResult.appendChild(titlesvg);

            taskTitle.appendChild(document.createTextNode(answerMessage.getTaskTitle()));

            titlesvg.appendChild(document.createTextNode(answerMessage.getSvgTitle()));

            if (!(answerMessage.getSvgImage() == null)) {
                Element imagesvg = document.createElement("imagesvg");
                imagesvg.appendChild(document.createCDATASection(answerMessage.getSvgImage()));
                taskResult.appendChild(imagesvg);
            }
            taskResult.appendChild(resulttext);
            resulttext.appendChild(document.createTextNode(answerMessage.getFeedback()));
            message.appendChild(taskResult);
            root.appendChild(message);
            document.appendChild(root);
            document.normalizeDocument();
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
            return stringWriter.toString().substring(55);
        }
        catch (TransformerException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e){
        }
        return "<loncapagrade><awarddetail>INCORRECT</awarddetail><message><taskresult grade=\"failed\"><tasktitle /><titlesvg /><resulttext /></taskresult></message></loncapagrade>";
    }
}
