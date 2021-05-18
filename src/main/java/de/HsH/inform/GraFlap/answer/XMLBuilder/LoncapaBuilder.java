package de.HsH.inform.GraFlap.answer.XMLBuilder;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import org.jdom2.CDATA;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * @author Mathias Sonderfeld
 * @version 1.0
 *
 * makes an XML String, that can be passed to Loncapa, from given answerMessage.
 * requires Jdom-2.0.6 Library.
 */
public class LoncapaBuilder implements OutputBuilder {

    /**
     * converts AnswerMessage to XML in Loncapa-Style
     * @param answerMessage the answerMessage to convert into XML
     * @throws NullPointerException if input is null
     * @return String containing the whole xml document
     */
    public String getOutput( AnswerMessage answerMessage ) {
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        if ("asvg".equals(answerMessage.getTaskMode())) {
            return out.outputString(answerMessage.getSvgImage());
        }
        String award, grade;

        if(answerMessage.getPercentOfTestWordsFailed() < 0){
            award = "ERROR";
            grade = "error";
        }
        else if (answerMessage.hasPassed()) {
            award = "EXACT_ANS";
            grade = "passed";
        } else {
            award = "INCORRECT";
            grade = "failed";
        }

        Element root = new Element("loncapagrade");
        root.addContent(new Element("awarddetail").addContent(award));
        Element taskResult = new Element("taskresult");
        taskResult.setAttribute("grade", grade);
        taskResult.addContent(new Element("tasktitle").addContent(answerMessage.getTaskTitle()));
        taskResult.addContent(new Element("titlesvg").addContent(answerMessage.getSvgTitle()));

        if (!(answerMessage.getSvgImage() == null)) {
            String svgstring = new XMLOutputter().outputString(answerMessage.getSvgImage());

            taskResult.addContent(new Element("imagesvg").addContent(new CDATA(svgstring)));
        }

        taskResult.addContent(new Element("resulttext").addContent(answerMessage.getFeedback()));
        root.addContent(new Element("message").addContent(taskResult));
        return out.outputString(root);
    }
}
