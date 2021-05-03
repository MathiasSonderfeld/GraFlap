package de.HsH.inform.GraFlap.answer.XMLBuilder;

import de.HsH.inform.GraFlap.answer.Messages.AnswerMessage;
import org.jdom2.CDATA;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class LoncapaBuilder extends XMLBuilder{

    public LoncapaBuilder( AnswerMessage answerMessage ) {
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        if ("asvg".equals(answerMessage.getTaskMode())) {
            this.xml = out.outputString(answerMessage.getSvgImage());
            return;
        }
        String award, grade;

        if (answerMessage.hasPassed()) {
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

        taskResult.addContent(new Element("resulttext").addContent(answerMessage.getResultText()));
        root.addContent(new Element("message").addContent(taskResult));
        this.xml = out.outputString(root);
    }
}
