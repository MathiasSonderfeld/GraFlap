package de.HsH.inform.GraFlap.answerMessage;

import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.entity.*;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.CommentMarker;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.SetResult;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.State;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.Transition;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * abstract class that serves a parent to generate the answer message, which is sent back to LON-CAPA
 * @author Benjamin Held (07-29-2016)
 * @author Lukas Seltmann (06-12-2019)
 * @author Mathias Sonderfeld
 * @since 08-04-2016
 * @version 0.5
 */
public class AnswerMessage {
    protected ResourceBundle messages;

    protected String taskTitle;
    private Locale userLocale;
    private Element svgImage;
    private int percentOfTestWordsFailed;
    private boolean hasPassed;
    private TaskMode taskMode;
    private TaskType taskType;
    protected StringBuilder aditionalFeedback;
    protected String svgTitle;
    private StringBuilder feedback;
    private ResourceBundle.Control control;

    private SetResult<State> states = null;
    private SetResult<State> initials = null;
    private SetResult<State> finals = null;
    private SetResult<String> alphabet = null;
    private SetResult<String> stackalphabet = null;
    private SetResult<Transition> transitions = null;

    private AnswerMessage(Result result, Arguments arguments){
        this.feedback = new StringBuilder();
        this.aditionalFeedback = new StringBuilder();
        this.control = new ResourceBundle.Control(){
            @Override
            public Locale getFallbackLocale(String baseName, Locale locale){
                return Locale.ROOT;
            }
        };
        this.taskTitle = arguments.getTaskTitle();
        this.percentOfTestWordsFailed = result.getPercentageFailed();
        this.userLocale = arguments.getUserLanguage();
        messages = ResourceBundle.getBundle("GraFlapAnswerMessage", userLocale, control);
        this.taskMode = arguments.getTaskMode();
        this.taskType = arguments.getTaskType();
        this.hasPassed = percentOfTestWordsFailed == 0;
    }

    public AnswerMessage(Result result, Arguments arguments, String errorMessage){
        this(result, arguments);
        this.svgImage = null;
        this.svgTitle = "ERROR";
        this.feedback.append(errorMessage);
    }

    public AnswerMessage(Result result, Arguments arguments, Element svg) {
        this(result, arguments);
        this.svgImage = svg;
        String message = "", format = "%d %s";
        String aditionalFeedbackDelimiter ="\n";
        switch(this.taskMode){
            //Grammar
            case GG: case GGW: case GGT: case GGTW: case EGT: case GR: case GRT: case GRW: case GRTW:
                this.svgTitle = messages.getString(String.valueOf(AnswerMessages.GRAMMAR_Svgtitle));
                if (this.taskType != result.getsubmissionTaskType() && !(this.taskType == TaskType.RLCFG && (result.getsubmissionTaskType() == TaskType.RL || result.getsubmissionTaskType() == TaskType.CFG))) {
                    this.aditionalFeedback.append(messages.getString(String.valueOf(AnswerMessages.GRAMMAR_Type)));
                    this.hasPassed = false;
                }
                if(!this.hasPassed){
                    message = String.format(format, percentOfTestWordsFailed, messages.getString(AnswerMessages.GRAMMAR_Feedback.toString()));
                    feedback.append(message);
                    if(aditionalFeedback.length() > 0){
                        feedback.append(aditionalFeedbackDelimiter).append(aditionalFeedback);
                    }

                }
                break;

            //Automaton
            case ARP: case ARTP: case AGP: case AGTP: case ARWP: case AGWP: case ARTWP: case AGTWP:
                setSetResults(result);
            case AR: case ART: case AG: case AGT: case ARW: case AGW: case ARTW: case AGTW: case EAT:
                this.svgTitle = messages.getString(String.valueOf(AnswerMessages.ACCEPTOR_Svgtitle));
                this.hasPassed &= automatonSubmissionMatchesType(this.taskType, result.getsubmissionTaskType());

                if(!this.hasPassed){
                    message = String.format(format, percentOfTestWordsFailed, messages.getString(String.valueOf(AnswerMessages.ACCEPTOR_Feedback)));
                    feedback.append(message).append(aditionalFeedbackDelimiter).append(aditionalFeedback);
                }
                break;

            //Automaton Automaton comparison
            case AA:
                this.svgTitle = messages.getString(String.valueOf(AnswerMessages.ACCEPTOR_Svgtitle));
                setSetResults(result);
                if(!this.hasPassed){
                    message = String.format(format, percentOfTestWordsFailed, messages.getString(String.valueOf(AnswerMessages.ACCEPTOR_AAFeedback)));
                    feedback.append(message);
                }
                break;

            case MP: case MMW:
                this.svgTitle = messages.getString(String.valueOf(AnswerMessages.TRANSDUCER_Svgtitle));
                if(this.taskMode.isTyped()) this.hasPassed &= automatonIsDeterministic(this.taskType, result.getsubmissionTaskType());
                this.hasPassed &= automatonIsTuring(this.taskType, result.getsubmissionTaskType());
                if (this.taskType == TaskType.MEALY && result.getsubmissionTaskType() != TaskType.MEALY) {
                    aditionalFeedback.append(messages.getString(String.valueOf(AnswerMessages.TRANSDUCER_Mealy)));
                    this.hasPassed = false;
                }
                else if (this.taskType == TaskType.MOORE && result.getsubmissionTaskType() != TaskType.MOORE) {
                    aditionalFeedback.append(messages.getString(String.valueOf(AnswerMessages.TRANSDUCER_Moore)));
                    this.hasPassed = false;
                }
                if(!this.hasPassed){
                    message = messages.getString(AnswerMessages.TRANSDUCER_Feedback.toString());
                    this.feedback.append(String.format(format, percentOfTestWordsFailed, message));
                    this.feedback.append(aditionalFeedbackDelimiter).append(aditionalFeedback);
                }
                break;

            case WW:
                this.svgTitle = messages.getString(String.valueOf(AnswerMessages.WORD_Svgtitle));
                if(!this.hasPassed){
                    message = messages.getString(AnswerMessages.WORD_Feedback.toString());
                    this.feedback.append(String.format(format, percentOfTestWordsFailed, message));
                }
                break;

            case CYK:
                this.svgTitle = messages.getString(String.valueOf(AnswerMessages.CYK_Svgtitle));
                if(!this.hasPassed){
                    message = messages.getString(AnswerMessages.CYK_Feedback.toString());
                    this.feedback.append(String.format(format, percentOfTestWordsFailed, message));
                }
                break;

            case DER:
                this.svgTitle = messages.getString(String.valueOf(AnswerMessages.DERIVATION_Svgtitle));
                if(!this.hasPassed){
                    message = messages.getString(AnswerMessages.DERIVATION_Feedback.toString());
                    this.feedback.append(String.format(format, percentOfTestWordsFailed, message));
                }
                break;

            case SVGA: case SVGG:
                this.svgTitle = messages.getString(String.valueOf(AnswerMessages.SVG_Svgtitle));
                if(!this.hasPassed){
                    message = messages.getString(AnswerMessages.SVG_Feedback.toString());
                    this.feedback.append(String.format(format, percentOfTestWordsFailed, message));
                }
                break;

            default:
        }
    }

    private void setSetResults(Result result){
        this.states = result.getStates();
        this.initials= result.getInitials();
        this.finals = result.getFinals();
        this.alphabet = result.getAlphabet();
        this.stackalphabet = result.getStackalphabet();
        this.transitions = result.getTransitions();
    }

    private boolean automatonIsDeterministic(TaskType solutionTaskType, TaskType submissionTaskType) {
        if (solutionTaskType.isNonDeterministic() && submissionTaskType.isDeterministic()) {
            aditionalFeedback.append(messages.getString(String.valueOf(AnswerMessages.AUTOMATON_MatchesNotDeterministic)));
        }
        else if (solutionTaskType.isDeterministic() && submissionTaskType.isNonDeterministic()) {
            aditionalFeedback.append(messages.getString(String.valueOf(AnswerMessages.AUTOMATON_MatchesDeterministic)));
            return false;
        }
        return true;
    }

    private boolean automatonIsTuring(TaskType solutionTaskType, TaskType submissionTaskType){
        if (solutionTaskType.isTuring() && !submissionTaskType.isTuring()){
            aditionalFeedback.append(messages.getString(String.valueOf(AnswerMessages.AUTOMATON_IsTuring))).append(" ").append(this.svgTitle);
            return false;
        }
        return true;
    }

    protected boolean automatonSubmissionMatchesType( TaskType solutionTaskType, TaskType submissionTaskType){
        boolean passed = true;
        if (taskMode.isTyped()) {
            passed = automatonIsDeterministic(solutionTaskType, submissionTaskType);
            if (solutionTaskType.isFiniteAutomaton() && !submissionTaskType.isFiniteAutomaton()) {
                aditionalFeedback.append(messages.getString(String.valueOf(AnswerMessages.ACCEPTOR_FAFeedback)));
                return false;
            }
            else if (solutionTaskType.isPushDownAutomaton() && !submissionTaskType.isPushDownAutomaton()) {
                aditionalFeedback.append(messages.getString(String.valueOf(AnswerMessages.ACCEPTOR_PDAFeedback)));
                return false;
            }
            else
                return automatonIsTuring(solutionTaskType, submissionTaskType) && passed;
        }
        return passed;
    }

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
                    switch(this.userLocale.getLanguage()){
                        case "de":
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
            switch(this.userLocale.getLanguage()){
                case "de":
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
            switch(this.userLocale.getLanguage()){
                case "de":
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

    public TaskType getTaskType() {
        return taskType;
    }

    public String getFeedback() {
        if(GraFlap.printAsACII) return replaceGermanCharacters(feedback.toString());
        return feedback.toString();
    }

    public String getSvgTitle() {
        return svgTitle;
    }

    public boolean hasPassed() {
        return hasPassed;
    }
}