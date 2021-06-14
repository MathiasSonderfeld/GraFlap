package de.HsH.inform.GraFlap.answer.Messages;

import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.SetResult;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.State;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.Transition;
import de.HsH.inform.GraFlap.entity.UserLanguage;
import org.jdom2.Element;

import java.util.ArrayList;


/**
 * abstract class that serves a parent to generate the answer message, which is sent back to LON-CAPA
 * @author Benjamin Held (07-29-2016)
 * @author Lukas Seltmann (06-12-2019)
 * @author Mathias Sonderfeld
 * @since 08-04-2016
 * @version 0.5
 */
public abstract class AnswerMessage {
    protected String taskTitle;
    protected UserLanguage lang;
    protected final Element svgImage;
    protected final int percentOfTestWordsFailed;
    protected boolean hasPassed;
    protected String taskMode;
    protected StringBuilder feedbackText;
    protected String svgTitle;

    private SetResult<State> states = null;
    private SetResult<State> initials = null;
    private SetResult<State> finals = null;
    private SetResult<String> alphabet = null;
    private SetResult<String> stackalphabet = null;
    private SetResult<Transition> transitions = null;

    /**
     * Constructor
     * @param percentOfTestWordsFailed value how many words failed the testing ranging form [0,100]
     * @param taskTitle the taskTitle of the assignment
     * @param bestLanguage a string coding the used language of the assignment
     * @param taskMode a string holding the coded mode information
     * @param solutionType a string coding the solutionType of the solution
     * @param submissionType a string coding the solutionType of the submission
     * @param svg a XML-element that gains the information for the output svg
     */
    public AnswerMessage(int percentOfTestWordsFailed, String taskTitle, String bestLanguage, String taskMode, String solutionType, String submissionType, Element svg) {
        this.taskTitle = taskTitle;
        this.percentOfTestWordsFailed = percentOfTestWordsFailed;
        this.lang = UserLanguage.get(bestLanguage);
        this.feedbackText = new StringBuilder();
        this.svgImage = svg;
        this.taskMode = taskMode;

        if(lang == UserLanguage.German){
            checkAndReplaceGermanCharactersInTaskTitle();
        }

        this.svgTitle = getLangDependentSvgTitle(lang);
        this.hasPassed = percentOfTestWordsFailed == 0;
        this.hasPassed &= submissionMatchesTarget(solutionType, submissionType);
        if(percentOfTestWordsFailed < 0){ //Error
            this.feedbackText.append(submissionType);
        }
        else if(!this.hasPassed){
            this.feedbackText.append(percentOfTestWordsFailed).append(" ").append(getLangDependentFeedback(lang));
        }
    }

    /**
     * private method to replace german special characters
     */
    private void checkAndReplaceGermanCharactersInTaskTitle() {
        taskTitle = taskTitle.replaceAll("ä","ae");
        taskTitle = taskTitle.replaceAll("ö","oe");
        taskTitle = taskTitle.replaceAll("ü","ue");
        taskTitle = taskTitle.replaceAll("Ä","Ae");
        taskTitle = taskTitle.replaceAll("Ö","Oe");
        taskTitle = taskTitle.replaceAll("Ü","Ue");
        taskTitle = taskTitle.replaceAll("ß","ss");
    }

    private String getTeacherFeedback(String name, SetResult result){
        StringBuilder feedback = new StringBuilder();
        boolean noneMissing = true;
        ArrayList tmp = result.getMissing();
        if(tmp.size() > 0){
            noneMissing = false;
            feedback.append("Es fehlen").append(" ");
            for(int i = 0; i <tmp.size()-1; i++) {
                feedback.append(tmp.get(i).toString()).append(", ");
            }
            feedback.append(tmp.get(tmp.size()-1));
        }
        tmp = result.getSurplus();
        if(tmp.size() > 0){
            if(!noneMissing) feedback.append("; ");
            feedback.append("Zu viel sind").append(" ");
            for(int i = 0; i <tmp.size()-1; i++) {
                feedback.append(tmp.get(i).toString()).append(", ");
            }
            feedback.append(tmp.get(tmp.size()-1));
        }
        return feedback.toString();
    }

    private String getStudentFeedback(String name, SetResult result){
        StringBuilder feedback = new StringBuilder();
        int errors = result.getTotalErrors();
        if(errors > 0){
            switch(this.lang){
                case German:
                    feedback.append(errors).append(" Fehler. Keine Punkte hierfür.");
                    break;
                default:
                    feedback.append(errors).append(" Mistakes. No points for this.");
            }
        }
        ArrayList tmp = result.getDoubles();
        if(tmp.size() > 0){
            StringBuilder doubles = new StringBuilder();
            for(int i = 0; i <tmp.size()-1; i++) {
                doubles.append(tmp.get(i).toString()).append(", ");
            }
            doubles.append(tmp.get(tmp.size()-1));

            if(errors > 0) feedback.append(" ");
            switch(this.lang){
                case German:
                    feedback.append("Duplikate: ").append(doubles);
                    break;
                default:
                    feedback.append("Duplicates: ").append(doubles);
            }
        }
        return feedback.toString();
    }

    public double getStatesScore(){
        return states.getScore();
    }

    public String getStatesTeacherFeedback(){
        if(states == null) return "";
        return getTeacherFeedback("States", states);
    }

    public String getStatesStudentFeedback(){
        if(states == null) return "";
        return getStudentFeedback("States", states);
    }

    public double getInitialsScore(){
        return  initials.getScore();
    }

    public String getInitialsTeacherFeedback(){
        if(initials == null) return "";
        return getTeacherFeedback("Initials", initials);
    }

    public String getInitialsStudentFeedback(){
        if(initials == null) return "";
        return getStudentFeedback("Initials", initials);
    }

    public double getFinalsScore(){
        return  finals.getScore();
    }

    public String getFinalsTeacherFeedback(){
        if(finals == null) return "";
        return getTeacherFeedback("Finals", finals);
    }

    public String getFinalsStudentFeedback(){
        if(finals == null) return "";
        return getStudentFeedback("Finals", finals);
    }

    public double getAlphabetScore(){
        return  alphabet.getScore();
    }

    public String getAlphabetTeacherFeedback(){
        if(alphabet == null) return "";
        return getTeacherFeedback("Alphabet", alphabet);
    }

    public String getAlphabetStudentFeedback(){
        if(alphabet == null) return "";
        return getStudentFeedback("Alphabet", alphabet);
    }

    public double getStackAlphabetScore(){
        return  stackalphabet.getScore();
    }

    public String getStackAlphabetTeacherFeedback(){
        if(stackalphabet == null) return "";
        return getTeacherFeedback("Stack Alphabet", stackalphabet);
    }

    public String getStackAlphabetStudentFeedback(){
        if(stackalphabet == null) return "";
        return getStudentFeedback("Stack Alphabet", stackalphabet);
    }

    public double getTransitionsScore(){
        return  transitions.getScore();
    }

    public String getTransitionsTeacherFeedback(){
        if(transitions == null) return "";
        return getTeacherFeedback("Transitions", transitions);
    }

    public String getTransitionsStudentFeedback(){
        if(transitions == null) return "";
        return getStudentFeedback("Transitions", transitions);
    }

    public double getScore(){
        return hasPassed?1.0:0.0;
    }

    protected abstract String getLangDependentSvgTitle(UserLanguage lang);
    protected abstract String getLangDependentFeedback(UserLanguage lang);

    protected boolean submissionMatchesTarget(String type, String studType){
        return true;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public Element getSvgImage() {
        return svgImage;
    }

    public int getPercentOfTestWordsFailed() {
        return percentOfTestWordsFailed;
    }

    public String getTaskMode() {
        return taskMode;
    }

    public String getFeedback() {
        return feedbackText.toString();
    }

    public String getSvgTitle() {
        return svgTitle;
    }

    public boolean hasPassed() {
        return hasPassed;
    }

    public void setStates( SetResult<State> states ) {
        this.states = states;
    }

    public void setInitials( SetResult<State> initials ) {
        this.initials = initials;
    }

    public void setFinals( SetResult<State> finals ) {
        this.finals = finals;
    }

    public void setAlphabet( SetResult<String> alphabet ) {
        this.alphabet = alphabet;
    }

    public void setStackalphabet( SetResult<String> stackalphabet ) {
        this.stackalphabet = stackalphabet;
    }

    public void setTransitions( SetResult<Transition> transitions ) {
        this.transitions = transitions;
    }

}