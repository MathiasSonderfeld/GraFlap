package de.HsH.inform.GraFlap.answerMessage;

import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.entity.*;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.CommentMarker;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.SetResult;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.State;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.Transition;
import de.HsH.inform.GraFlap.util.LocaleControl;

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
    private String svgImage;
    private int percentOfTestWordsFailed;
    private boolean hasPassed;
    private TaskMode taskMode;
    private TaskType taskType;
    protected StringBuilder aditionalFeedback;
    protected StringBuilder warnings;
    protected String svgTitle;
    private StringBuilder feedback;

    private SetResult<State> states = null;
    private SetResult<State> initials = null;
    private SetResult<State> finals = null;
    private SetResult<String> alphabet = null;
    private SetResult<String> stackalphabet = null;
    private SetResult<Transition> transitions = null;

    private AnswerMessage(Result result, Arguments arguments){
        this.feedback = new StringBuilder();
        this.aditionalFeedback = new StringBuilder();
        this.warnings = new StringBuilder();
        this.taskTitle = arguments.getTaskTitle();
        this.percentOfTestWordsFailed = result.getPercentageFailed();
        this.userLocale = arguments.getUserLanguage();
        messages = ResourceBundle.getBundle("GraFlapAnswerMessage", userLocale, new LocaleControl());
        this.taskMode = arguments.getTaskMode();
        this.taskType = arguments.getTaskType();
        this.hasPassed = percentOfTestWordsFailed == 0;
    }

    public AnswerMessage(Result result, Arguments arguments, String svg, String errorMessage){
        this(result, arguments);
        this.svgImage = svg;
        this.svgTitle = "ERROR";
        this.feedback.append(errorMessage);
    }

    public AnswerMessage(Result result, Arguments arguments, String svg) {
        this(result, arguments);
        this.svgImage = svg;
        String message = "", format = "%d %s";
        String aditionalFeedbackDelimiter ="\n";
        switch(this.taskMode){
            //Grammar
            case GG: case GGW: case GGT: case GGTW: case EGT: case GR: case GRT: case GRW: case GRTW:
                this.svgTitle = messages.getString(FeedbackMessage.GRAMMAR_Svgtitle.name());
                if (this.taskType != result.getsubmissionTaskType() && !(this.taskType == TaskType.RLCFG && (result.getsubmissionTaskType() == TaskType.RL || result.getsubmissionTaskType() == TaskType.CFG))) {
                    this.aditionalFeedback.append(messages.getString(FeedbackMessage.GRAMMAR_Type.name()));
                    this.hasPassed = false;
                }
                if(!this.hasPassed){
                    message = String.format(format, percentOfTestWordsFailed, messages.getString(FeedbackMessage.GRAMMAR_Feedback.toString()));
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
                this.svgTitle = messages.getString(FeedbackMessage.ACCEPTOR_Svgtitle.name());
                this.hasPassed &= automatonSubmissionMatchesType(this.taskType, result.getsubmissionTaskType());

                if(!this.hasPassed){
                    message = String.format(format, percentOfTestWordsFailed, messages.getString(FeedbackMessage.ACCEPTOR_Feedback.name()));
                    feedback.append(message).append(aditionalFeedbackDelimiter).append(aditionalFeedback);
                }
                break;

            //Automaton Automaton comparison
            case AA:
                this.svgTitle = messages.getString(FeedbackMessage.ACCEPTOR_Svgtitle.name());
                setSetResults(result);
                if(!this.hasPassed){
                    message = String.format(format, percentOfTestWordsFailed, messages.getString(FeedbackMessage.ACCEPTOR_AAFeedback.name()));
                    feedback.append(message);
                }
                break;

            case MP: case MMW:
                this.svgTitle = messages.getString(FeedbackMessage.TRANSDUCER_Svgtitle.name());
                if(this.taskMode.isTyped()) this.hasPassed &= automatonIsDeterministic(this.taskType, result.getsubmissionTaskType());
                this.hasPassed &= automatonIsTuring(this.taskType, result.getsubmissionTaskType());
                if (this.taskType == TaskType.MEALY && result.getsubmissionTaskType() != TaskType.MEALY) {
                    aditionalFeedback.append(messages.getString(FeedbackMessage.TRANSDUCER_Mealy.name()));
                    this.hasPassed = false;
                }
                else if (this.taskType == TaskType.MOORE && result.getsubmissionTaskType() != TaskType.MOORE) {
                    aditionalFeedback.append(messages.getString(FeedbackMessage.TRANSDUCER_Moore.name()));
                    this.hasPassed = false;
                }
                if(!this.hasPassed){
                    message = messages.getString(FeedbackMessage.TRANSDUCER_Feedback.toString());
                    this.feedback.append(String.format(format, percentOfTestWordsFailed, message));
                    this.feedback.append(aditionalFeedbackDelimiter).append(aditionalFeedback);
                }
                break;

            case WW:
                this.svgTitle = messages.getString(FeedbackMessage.WORD_Svgtitle.name());
                if(!this.hasPassed){
                    message = messages.getString(FeedbackMessage.WORD_Feedback.toString());
                    this.feedback.append(String.format(format, percentOfTestWordsFailed, message));
                }
                break;

            case CYK:
                this.svgTitle = messages.getString(FeedbackMessage.CYK_Svgtitle.name());
                if(!this.hasPassed){
                    message = messages.getString(FeedbackMessage.CYK_Feedback.toString());
                    this.feedback.append(String.format(format, percentOfTestWordsFailed, message));
                }
                break;

            case DER:
                this.svgTitle = messages.getString(FeedbackMessage.DERIVATION_Svgtitle.name());
                if(!this.hasPassed){
                    message = messages.getString(FeedbackMessage.DERIVATION_Feedback.toString());
                    this.feedback.append(String.format(format, percentOfTestWordsFailed, message));
                }
                break;

            case SVGA: case SVGG:
                this.svgTitle = messages.getString(FeedbackMessage.SVG_Svgtitle.name());
                if(!this.hasPassed){
                    message = messages.getString(FeedbackMessage.SVG_Feedback.toString());
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
            aditionalFeedback.append(messages.getString(FeedbackMessage.AUTOMATON_MatchesNotDeterministic.name()));
        }
        else if (solutionTaskType.isDeterministic() && submissionTaskType.isNonDeterministic()) {
            aditionalFeedback.append(messages.getString(FeedbackMessage.AUTOMATON_MatchesDeterministic.name()));
            return false;
        }
        return true;
    }

    private boolean automatonIsTuring(TaskType solutionTaskType, TaskType submissionTaskType){
        if (solutionTaskType.isTuring() && !submissionTaskType.isTuring()){
            aditionalFeedback.append(messages.getString(FeedbackMessage.AUTOMATON_IsTuring.name())).append(" ").append(this.svgTitle);
            return false;
        }
        return true;
    }

    protected boolean automatonSubmissionMatchesType( TaskType solutionTaskType, TaskType submissionTaskType){
        boolean passed = true;
        if (taskMode.isTyped()) {
            passed = automatonIsDeterministic(solutionTaskType, submissionTaskType);
            if (solutionTaskType.isFiniteAutomaton() && !submissionTaskType.isFiniteAutomaton()) {
                aditionalFeedback.append(messages.getString(FeedbackMessage.ACCEPTOR_FAFeedback.name()));
                return false;
            }
            else if (solutionTaskType.isPushDownAutomaton() && !submissionTaskType.isPushDownAutomaton()) {
                aditionalFeedback.append(messages.getString(FeedbackMessage.ACCEPTOR_PDAFeedback.name()));
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

    public void addWarning(String warning){
        if(warnings.length() > 0) warnings.append("; ");
        this.warnings.append(warning);
    }

    public String getWarnings() {
        return warnings.toString();
    }

    private <T> String getTeacherFeedback( String name, SetResult<T> result){
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
                    feedback.append(messages.getString(FeedbackMessage.AUTOMATON_PARAMETERS_SQUAREBRACKETS.name()));
                    break;
            }
        }

        int errors = result.getTotalErrors();
        if(errors > 0){
            if(addedComments) feedback.append(" ");
            else feedback.append(name).append(": ");
            feedback.append(errors).append(" ").append(messages.getString(FeedbackMessage.AUTOMATON_PARAMETERS_ERRORS.name()));
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
            feedback.append(messages.getString(FeedbackMessage.AUTOMATON_PARAMETERS_DUPLICATES.name())).append(" ").append(doubles);
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

    public String getSvgImage() {
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