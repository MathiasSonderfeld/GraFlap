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
 * generates the feedback texts for the result so it can be printed
 * @author Benjamin Held (07-29-2016)
 * @author Lukas Seltmann (06-12-2019)
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value GraFlap#version}
 */
public class AnswerMessage {
    protected ResourceBundle messages;

    protected String taskTitle;
    private Locale userLocale;
    private String svgImage;
    private int percentOfTestWordsFailed;
    private boolean hasPassed;
    private boolean isError;
    private Mode mode;
    private Type taskType;
    protected StringBuilder aditionalFeedback;
    protected StringBuilder warnings;
    protected String svgTitle;
    private String feedbackTitle;
    private StringBuilder feedback;
    private long time = 0;
    private double score = 0.0;

    private SetResult<State> states = null;
    private SetResult<State> initials = null;
    private SetResult<State> finals = null;
    private SetResult<String> alphabet = null;
    private SetResult<String> stackalphabet = null;
    private SetResult<Transition> transitions = null;
    private String teachersExtra = "";

    private AnswerMessage(Result result, Arguments arguments){
        this.feedback = new StringBuilder();
        this.aditionalFeedback = new StringBuilder();
        this.warnings = new StringBuilder();

        if(arguments != null){
            this.taskTitle = arguments.getTaskTitle();
            this.userLocale = arguments.getUserLanguage();

            this.mode = arguments.getMode();
            this.taskType = arguments.getType();
            this.percentOfTestWordsFailed = result.getPercentageFailed();
            this.hasPassed = percentOfTestWordsFailed == 0;
            if (this.hasPassed) { this.score = 1.0; }
            this.teachersExtra = result.getExtraText();
            this.isError = false;
        }
        else{
            this.taskTitle = "ERROR";
            this.userLocale = Locale.ENGLISH;
            this.mode = Mode.ERROR;
            this.taskType = Type.ERROR;
            this.percentOfTestWordsFailed = 100;
            this.hasPassed = false;
            this.isError = true;
        }
        this.messages = ResourceBundle.getBundle("GraFlapAnswerMessage", this.userLocale, new LocaleControl());
        this.feedbackTitle = messages.getString(FeedbackMessage.Feedback_Title.name());
    }

    public AnswerMessage(Result result, Arguments arguments, String svg, String errorMessage){
        this(result, arguments);
        this.isError = true;
        this.svgImage = svg;
        this.svgTitle = "ERROR";
        this.feedback.append(errorMessage);
    }

    public AnswerMessage(Result result, Arguments arguments, String svg) {
        this(result, arguments);
        this.svgImage = svg;
        this.time = result.getTime();
        String message = "", format = "%d %s";
        String aditionalFeedbackDelimiter ="\n";


        switch(this.mode){
            //Grammar
            case GG: case GGW: case GGT: case GGTW: case EGT: case GR: case GRT: case GRW: case GRTW:
                this.svgTitle = messages.getString(FeedbackMessage.GRAMMAR_Svgtitle.name());
                if (this.taskType != result.getsubmissionTaskType() && !(this.taskType == Type.RLCFG && (result.getsubmissionTaskType() == Type.RL || result.getsubmissionTaskType() == Type.CFG))) {
                    this.aditionalFeedback.append(messages.getString(FeedbackMessage.GRAMMAR_Type.name()));
                    this.hasPassed = false;
                    score -= 0.5;
                }
                generalFeedback();
                if(!this.hasPassed){
                    message = String.format(format, percentOfTestWordsFailed, messages.getString(FeedbackMessage.GRAMMAR_Feedback.toString()));
                    feedback.append(message);
                    if(aditionalFeedback.length() > 0){
                        feedback.append(aditionalFeedbackDelimiter).append(aditionalFeedback);
                    }

                }
                break;

            case RR: case RRW:
                this.svgTitle = messages.getString(FeedbackMessage.REGEX_Svgtitle.name());
                generalFeedback();
                if(!this.hasPassed) {
                    message = String.format(format, percentOfTestWordsFailed, messages.getString(FeedbackMessage.REGEX_Feedback.toString()));
                    feedback.append(message);
                }
                break;

            //Automaton
            case ARP: case ARTP: case AGP: case AGTP: case ARWP: case AGWP: case ARTWP: case AGTWP:
                setSetResults(result);
            case AR: case ART: case AG: case AGT: case ARW: case AGW: case ARTW: case AGTW: case EAT:
                this.svgTitle = messages.getString(FeedbackMessage.ACCEPTOR_Svgtitle.name());
                boolean typeMatch = automatonSubmissionMatchesType(this.taskType, result.getsubmissionTaskType());
                this.hasPassed &= typeMatch;
                if (!typeMatch) { score -= 0.5; }
                generalFeedback();
                if(!this.hasPassed){
                    message = String.format(format, percentOfTestWordsFailed, messages.getString(FeedbackMessage.ACCEPTOR_Feedback.name()));
                    feedback.append(message).append(aditionalFeedbackDelimiter).append(aditionalFeedback);
                }
                break;

            //Automaton Automaton comparison
            case AA:
                this.svgTitle = messages.getString(FeedbackMessage.ACCEPTOR_Svgtitle.name());
                setSetResults(result);
                generalFeedback();
                if(!this.hasPassed){
                    message = String.format(format, percentOfTestWordsFailed, messages.getString(FeedbackMessage.ACCEPTOR_AAFeedback.name()));
                    feedback.append(message);
                }
                break;

            case MP: case MMW:
                this.svgTitle = messages.getString(FeedbackMessage.TRANSDUCER_Svgtitle.name());
                if(this.mode.isTyped()) this.hasPassed &= automatonIsDeterministic(this.taskType, result.getsubmissionTaskType());
                this.hasPassed &= automatonIsTuring(this.taskType, result.getsubmissionTaskType());
                if (this.taskType == Type.MEALY && result.getsubmissionTaskType() != Type.MEALY) {
                    aditionalFeedback.append(messages.getString(FeedbackMessage.TRANSDUCER_Mealy.name()));
                    this.hasPassed = false;
                    score -= 0.5;
                }
                else if (this.taskType == Type.MOORE && result.getsubmissionTaskType() != Type.MOORE) {
                    aditionalFeedback.append(messages.getString(FeedbackMessage.TRANSDUCER_Moore.name()));
                    this.hasPassed = false;
                    score -= 0.5;
                }
                generalFeedback();
                if(!this.hasPassed){
                    message = messages.getString(FeedbackMessage.TRANSDUCER_Feedback.toString());
                    this.feedback.append(String.format(format, percentOfTestWordsFailed, message));
                    this.feedback.append(aditionalFeedbackDelimiter).append(aditionalFeedback);
                }
                break;

            case WW:
                this.svgTitle = messages.getString(FeedbackMessage.WORD_Svgtitle.name());
                generalFeedback();
                if(!this.hasPassed){
                    message = messages.getString(FeedbackMessage.WORD_Feedback.toString());
                    this.feedback.append(String.format(format, percentOfTestWordsFailed, message));
                }
                break;

            case CYK:
                this.svgTitle = messages.getString(FeedbackMessage.CYK_Svgtitle.name());
                generalFeedback();
                if(!this.hasPassed){
                    message = messages.getString(FeedbackMessage.CYK_Feedback.toString());
                    this.feedback.append(String.format(format, percentOfTestWordsFailed, message));
                }
                break;

            case DER:
                this.svgTitle = messages.getString(FeedbackMessage.DERIVATION_Svgtitle.name());
                generalFeedback();
                if(!this.hasPassed){
                    message = messages.getString(FeedbackMessage.DERIVATION_Feedback.toString());
                    this.feedback.append(String.format(format, percentOfTestWordsFailed, message));
                }
                break;

            case SVGA: case SVGG:
                this.svgTitle = messages.getString(FeedbackMessage.SVG_Svgtitle.name());
                generalFeedback();
                if(!this.hasPassed){
                    message = messages.getString(FeedbackMessage.SVG_Feedback.toString());
                    this.feedback.append(String.format(format, percentOfTestWordsFailed, message));
                }
                break;

            default:
        }
    }

    private void generalFeedback(){
        double eps = 1e-6;
        if(score > 1-eps) {
            feedback.append(messages.getString(FeedbackMessage.All_Correct.name()));
        }
        else if (score < eps){
            feedback.append(messages.getString(FeedbackMessage.Anything_wrong.name())).append(" ");
        }else{
            feedback.append(messages.getString(FeedbackMessage.Partly_correct.name())).append(" ");
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

    private boolean automatonIsDeterministic(Type solutionType, Type submissionType) {
        if (solutionType.isNonDeterministic() && submissionType.isDeterministic()) {
            aditionalFeedback.append(messages.getString(FeedbackMessage.AUTOMATON_MatchesNotDeterministic.name()));
        }
        else if (solutionType.isDeterministic() && submissionType.isNonDeterministic()) {
            aditionalFeedback.append(messages.getString(FeedbackMessage.AUTOMATON_MatchesDeterministic.name()));
            return false;
        }
        return true;
    }

    private boolean automatonIsTuring(Type solutionType, Type submissionType){
        if (solutionType.isTuring() && !submissionType.isTuring()){
            aditionalFeedback.append(messages.getString(FeedbackMessage.AUTOMATON_IsTuring.name())).append(" ").append(this.svgTitle);
            return false;
        }
        return true;
    }

    protected boolean automatonSubmissionMatchesType(Type solutionType, Type submissionType){
        boolean passed = true;
        if (mode.isTyped()) {
            passed = automatonIsDeterministic(solutionType, submissionType);
            if (solutionType.isFiniteAutomaton() && !submissionType.isFiniteAutomaton()) {
                aditionalFeedback.append(messages.getString(FeedbackMessage.ACCEPTOR_FAFeedback.name()));
                return false;
            }
            else if (solutionType.isPushDownAutomaton() && !submissionType.isPushDownAutomaton()) {
                aditionalFeedback.append(messages.getString(FeedbackMessage.ACCEPTOR_PDAFeedback.name()));
                return false;
            }
            else
                return automatonIsTuring(solutionType, submissionType) && passed;
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
        boolean allOK = true;
        ArrayList<T> tmp = result.getMissing();
        if(tmp.size() > 0){
            noneMissing = false;
            allOK = false;
            feedback.append(name).append(": ").append("Es fehlen").append(" ");
            for(int i = 0; i <tmp.size()-1; i++) {
                feedback.append(tmp.get(i).toString()).append(", ");
            }
            feedback.append(tmp.get(tmp.size()-1));
        }
        tmp = result.getSurplus();
        if(tmp.size() > 0){
            allOK = false;
            if(!noneMissing) feedback.append(". ");
            else feedback.append(name).append(": ");
            feedback.append("Zu viel sind").append(" ");
            for(int i = 0; i <tmp.size()-1; i++) {
                feedback.append(tmp.get(i).toString()).append(", ");
            }
            feedback.append(tmp.get(tmp.size()-1));
        }
        if (allOK) {
            feedback.append(messages.getString(FeedbackMessage.TEACHER_OK.name()));
        }
        if(GraFlap.printAsACII) return replaceGermanCharacters(feedback.toString());
        return feedback.toString();
    }

    private <T> String getStudentFeedback(String name, SetResult<T> result){
        StringBuilder feedback = new StringBuilder();
        boolean addedComments = false;
        boolean allOK = true;
        for(CommentMarker marker : result.getComments()){
            addedComments = true;
            feedback.append(name).append(": ");
            switch(marker){
                case SquareBrackets:
                    feedback.append(messages.getString(FeedbackMessage.AUTOMATON_PARAMETERS_SQUAREBRACKETS.name()));
                    allOK = false;
                    break;
            }
        }

        int errors = result.getTotalErrors();
        if(errors > 0){
            allOK = false;
            if(addedComments) feedback.append(" ");
            else feedback.append(name).append(": ");
            feedback.append(errors).append(" ").append(messages.getString(FeedbackMessage.AUTOMATON_PARAMETERS_ERRORS.name()));
        }
        ArrayList<T> tmp = result.getDoubles();
        if(tmp.size() > 0){
            allOK = false;
            StringBuilder doubles = new StringBuilder();
            for(int i = 0; i <tmp.size()-1; i++) {
                doubles.append(tmp.get(i).toString()).append(", ");
            }
            doubles.append(tmp.get(tmp.size()-1));

            if(errors > 0 || addedComments) feedback.append(" ");
            else feedback.append(name).append(": ");
            feedback.append(messages.getString(FeedbackMessage.AUTOMATON_PARAMETERS_DUPLICATES.name())).append(" ").append(doubles);
        }
        if (allOK) {
            feedback.append(messages.getString(FeedbackMessage.All_Correct.name()));
        }
        if(GraFlap.printAsACII) return replaceGermanCharacters(feedback.toString());
        return feedback.toString();
    }

    public double getStatesScore(){
        if(states == null) return 0.0;
        return states.getScore();
    }

    public String getStatesTeacherFeedback(){
        if(states == null) return "No test possible.";
        return getTeacherFeedback("States", states);
    }

    public String getStatesStudentFeedback(){
        if(states == null) return "No test possible.";
        return getStudentFeedback("States", states);
    }

    public double getInitialsScore(){
        if(initials == null) return 0.0;
        return  initials.getScore();
    }

    public String getInitialsTeacherFeedback(){
        if(initials == null) return "No test possible.";
        return getTeacherFeedback("Initials", initials);
    }

    public String getInitialsStudentFeedback(){
        if(initials == null) return "No test possible.";
        return getStudentFeedback("Initials", initials);
    }

    public double getFinalsScore(){
        if(finals == null) return 0.0;
        return  finals.getScore();
    }

    public String getFinalsTeacherFeedback(){
        if(finals == null) return "No test possible.";
        return getTeacherFeedback("Finals", finals);
    }

    public String getFinalsStudentFeedback(){
        if(finals == null) return "No test possible.";
        return getStudentFeedback("Finals", finals);
    }

    public double getAlphabetScore(){
        if(alphabet == null) return 0.0;
        return  alphabet.getScore();
    }

    public String getAlphabetTeacherFeedback(){
        if(alphabet == null) return "No test possible.";
        return getTeacherFeedback("Alphabet", alphabet);
    }

    public String getAlphabetStudentFeedback(){
        if(alphabet == null) return "No test possible.";
        return getStudentFeedback("Alphabet", alphabet);
    }

    public double getStackAlphabetScore(){
        if(stackalphabet == null) return 0.0;
        return  stackalphabet.getScore();
    }

    public String getStackAlphabetTeacherFeedback(){
        if(stackalphabet == null) return "No test possible.";
        return getTeacherFeedback("Stack Alphabet", stackalphabet);
    }

    public String getStackAlphabetStudentFeedback(){
        if(stackalphabet == null) return "No test possible.";
        return getStudentFeedback("Stack Alphabet", stackalphabet);
    }

    public double getTransitionsScore(){
        if(transitions == null) return 0.0;
        return  transitions.getScore();
    }

    public String getTransitionsTeacherFeedback(){
        if(transitions == null) return "No test possible.";
        return getTeacherFeedback("Transitions", transitions);
    }

    public String getTransitionsStudentFeedback(){
        if(transitions == null) return "No test possible.";
        return getStudentFeedback("Transitions", transitions);
    }

    public double getScore(){
        return (score > 0)?score:0.0;
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

    public Mode getMode() {
        return mode;
    }

    public Type getTaskType() {
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

    public boolean isError(){
        return isError;
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public String getTime() { return "Grading took " + time + " ms."; }

    public String getTeachersExtra() {
        return teachersExtra;
    }
}