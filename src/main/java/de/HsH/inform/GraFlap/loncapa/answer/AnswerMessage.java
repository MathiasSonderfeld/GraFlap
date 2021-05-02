package de.HsH.inform.GraFlap.loncapa.answer;

import org.jdom2.CDATA;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.util.Scanner;

/**
 * abstract class that serves a parent to generate the answer message, which is sent back to LON-CAPA
 * @author Benjamin Held (07-29-2016)
 * @author Lukas Seltmann (06-12-2019)
 * @author Mathias Sonderfeld (14-04-2021)
 * @since 08-04-2016
 * @version 0.1.5
 */
public abstract class AnswerMessage {

    /**
     * a string holding the coded mode information
     */
    protected String mode;
    /**
     * a string coding the information if the submission was correct or not (EXACT_ANS|INCORRECT)
     */
    private String award;
    /**
     * a string coding the grading of the submission (passed|failed)
     */
    private String grade;
    /**
     * a string builder that creates the feedback to the submission
     */
    protected StringBuilder resultText;
    /**
     * a string for the name of the assignment
     */
    private String taskTitle;
    /**
     * a string coding the used language of the assignment
     */
    protected String language;
    /**
     * a string for the title of the result svg
     */
    protected String svgTitle;
    /**
     * a XML-element that gains the information for the output svg
     */
    private Element svgImage;

    /**
     * Constructor
     * @param resultValue value how many word failed the testing ranging form [0,100]
     * @param title the title of the assignment
     * @param bestLanguage a string coding the used language of the assignment
     * @param taskMode a string holding the coded mode information
     * @param type a string coding the type of the solution
     * @param studType a string coding the type of the submission
     * @param svg a XML-element that gains the information for the output svg
     */
    public AnswerMessage(int resultValue, String title, String bestLanguage, String taskMode, String type,
                         String studType, Element svg) {
        taskTitle = title;
        language = bestLanguage;
        resultText = new StringBuilder();
        svgImage = svg;
        mode = taskMode;

        checkAndReplaceGermanCharacter();

        determineSvgTitle();
        boolean hasPassed = submissionMatchesTarget(type, studType);
        hasPassed &= finishAssessment(resultValue);
        determineGradingInformation(hasPassed);
    }

    /**
     * method to create an output string that holds the answer message that can be sent to LonCapa
     * @return a string holding the formatted answer for LonCapa
     */
    public String generateAnswerMessage() {
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        if (mode.equals("asvg")) {
            return out.outputString(svgImage);
        } else {
            Element root = new Element("loncapagrade");
            root.addContent(new Element("awarddetail").addContent(award));
            Element taskResult = new Element("taskresult");
            taskResult.setAttribute("grade", grade);
            taskResult.addContent(new Element("tasktitle").addContent(taskTitle));
            taskResult.addContent(new Element("titlesvg").addContent(svgTitle));

            if (!(svgImage == null)) {
                String svgstring = new XMLOutputter().outputString(svgImage);

                taskResult.addContent(new Element("imagesvg").addContent(new CDATA(svgstring)));
            }

            taskResult.addContent(new Element("resulttext").addContent(resultText.toString()));
            root.addContent(new Element("message").addContent(taskResult));

            return out.outputString(root);
        }
    }

    /**
     * creates ant output string in proforma format
     * @return
     */
    public String generateProformaMessage(){
        XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
        if(mode.equals("asvg")) {
            throw new RuntimeException("FUCK DIS!");
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
        testResponse.setAttribute("id", this.taskTitle);
        testsResponse.addContent(testResponse);

        Element testResult = new Element("test-result");
        testResult.setNamespace(proforma);
        testResponse.addContent(testResult);

        Element result = new Element("result");
        result.setNamespace(proforma);
        testResult.addContent(result);

        Element testScore = new Element("score");
        testScore.setNamespace(proforma);

        //TODO
        //FUCK YOU FOR NOT PUTTING IN A NUMBER!!!
        Scanner s = new Scanner(this.resultText.toString());
        int p = s.nextInt();
        s.close();
        double score = 1-(p/100);

        testScore.addContent(""+ score);
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
        studentFeedbackTitle_content.addContent(this.taskTitle);
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
        studentFeedbackTitleSVG_content.addContent(this.svgTitle);
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

        String escapedSvgImage = this.svgImage.getText();

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
        studentFeedbackResultText_content.addContent(this.resultText.toString());
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


    /**
     * private method to replace german special characters
     */
    private void checkAndReplaceGermanCharacter() {
        if(language.equalsIgnoreCase("de")){
            taskTitle = taskTitle.replaceAll("ä","ae");
            taskTitle = taskTitle.replaceAll("ö","oe");
            taskTitle = taskTitle.replaceAll("ü","ue");
            taskTitle = taskTitle.replaceAll("Ä","Ae");
            taskTitle = taskTitle.replaceAll("Ö","Oe");
            taskTitle = taskTitle.replaceAll("Ü","Ue");
            taskTitle = taskTitle.replaceAll("ß","ss");
        }
    }

    private void determineGradingInformation(boolean hasPassed) {
        if (hasPassed) {
            award = "EXACT_ANS";
            grade = "passed";
        } else {
            award = "INCORRECT";
            grade = "failed";
        }
    }

    protected abstract void determineSvgTitle();
    protected abstract boolean submissionMatchesTarget(String type, String studType);
    protected abstract boolean finishAssessment(int resultValue);
}