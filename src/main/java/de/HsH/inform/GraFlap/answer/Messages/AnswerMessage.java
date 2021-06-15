package de.HsH.inform.GraFlap.answer.Messages;

import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.entity.*;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.CommentMarker;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.SetResult;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.State;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.Transition;
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
    protected TaskMode taskMode;
    protected StringBuilder feedbackText;
    protected String svgTitle;

    private SetResult<State> states = null;
    private SetResult<State> initials = null;
    private SetResult<State> finals = null;
    private SetResult<String> alphabet = null;
    private SetResult<String> stackalphabet = null;
    private SetResult<Transition> transitions = null;

    public AnswerMessage(Result result, Arguments arguments, Element svg) {
        this.taskTitle = arguments.getTaskTitle();
        this.percentOfTestWordsFailed = result.getPercentageFailed();
        this.lang = UserLanguage.get(arguments.getUserLanguage());
        this.feedbackText = new StringBuilder();
        this.svgImage = svg;
        this.taskMode = arguments.getTaskMode();

        this.svgTitle = getLangDependentSvgTitle(lang);
        this.hasPassed = percentOfTestWordsFailed == 0;
        this.hasPassed &= submissionMatchesTarget(arguments.getTaskType(), result.getsubmissionTaskType());
        if(percentOfTestWordsFailed < 0){ //Error
            this.feedbackText.append(result.getsubmissionTaskType());
        }
        else if(!this.hasPassed){
            this.feedbackText.append(percentOfTestWordsFailed).append(" ").append(getLangDependentFeedback(lang));
        }
    }

    protected abstract String getLangDependentSvgTitle(UserLanguage lang);
    protected abstract String getLangDependentFeedback(UserLanguage lang);

    protected boolean submissionMatchesTarget( TaskType type, TaskType studType){
        return true;
    }

    /**
     * private method to replace german special characters
     */
    private String replaceGermanCharacters(String in) {
        return in.replaceAll("ä","ae")
                 .replaceAll("ö","oe")
                 .replaceAll("ü","ue")
                 .replaceAll("Ä","Ae")
                 .replaceAll("Ö","Oe")
                 .replaceAll("Ü","Ue")
                 .replaceAll("ß","ss");
    }

    private <T> String getTeacherFeedback(String name, SetResult<T> result){
        StringBuilder feedback = new StringBuilder();
        boolean noneMissing = true;
        ArrayList<T> tmp = result.getMissing();
        if(tmp.size() > 0){
            noneMissing = false;
            feedback.append(name).append(": ").append("Es fehlen").append(" ");
            for(int i = 0; i <tmp.size()-1; i++) {
                feedback.append(tmp.get(i).toString()).append(", ");
            }
            feedback.append(tmp.get(tmp.size()-1));
        }
        tmp = result.getSurplus();
        if(tmp.size() > 0){
            if(!noneMissing) feedback.append(". ");
            else feedback.append(name).append(": ");
            feedback.append("Zu viel sind").append(" ");
            for(int i = 0; i <tmp.size()-1; i++) {
                feedback.append(tmp.get(i).toString()).append(", ");
            }
            feedback.append(tmp.get(tmp.size()-1));
        }
        if(GraFlap.printAsACII) return replaceGermanCharacters(feedback.toString());
        return feedback.toString();
    }

    private <T> String getStudentFeedback(String name, SetResult<T> result){
        StringBuilder feedback = new StringBuilder();
        boolean addedComments = false;
        for(CommentMarker marker : result.getComments()){
            addedComments = true;
            feedback.append(name).append(": ");
            switch(marker){
                case SquareBrackets:
                    switch(this.lang){
                        case German:
                          feedback.append("Du hast eckige Klammern verwendet, korrekt wären runde Klammern. Kein Punktabzug.");
                          break;
                        default:
                            feedback.append("You used square brackets, round brackets would be correct. No point deduction.");
                            break;
                    }
                    break;
            }
        }

        int errors = result.getTotalErrors();
        if(errors > 0){
            if(addedComments) feedback.append(" ");
            else feedback.append(name).append(": ");
            switch(this.lang){
                case German:
                    feedback.append(errors).append(" Fehler. Keine Punkte für diesen Aufgabenteil.");
                    break;
                default:
                    feedback.append(errors).append(" mistakes. No points for this part.");
            }
        }
        ArrayList<T> tmp = result.getDoubles();
        if(tmp.size() > 0){
            StringBuilder doubles = new StringBuilder();
            for(int i = 0; i <tmp.size()-1; i++) {
                doubles.append(tmp.get(i).toString()).append(", ");
            }
            doubles.append(tmp.get(tmp.size()-1));

            if(errors > 0 || addedComments) feedback.append(" ");
            else feedback.append(name).append(": ");
            switch(this.lang){
                case German:
                    feedback.append("Duplikate: ").append(doubles);
                    break;
                default:
                    feedback.append("Duplicates: ").append(doubles);
            }
        }
        if(GraFlap.printAsACII) return replaceGermanCharacters(feedback.toString());
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

    public String getTaskTitle() {
        if(GraFlap.printAsACII) return replaceGermanCharacters(taskTitle);
        return taskTitle;
    }

    public Element getSvgImage() {
        return svgImage;
    }

    public int getPercentOfTestWordsFailed() {
        return percentOfTestWordsFailed;
    }

    public TaskMode getTaskMode() {
        return taskMode;
    }

    public String getFeedback() {
        if(GraFlap.printAsACII) return replaceGermanCharacters(feedbackText.toString());
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