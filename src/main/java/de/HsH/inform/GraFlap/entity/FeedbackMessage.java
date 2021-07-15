package de.HsH.inform.GraFlap.entity;

/**
 * Enum that match the ResourceBundle tags to make sure no typo creates issues.
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public enum FeedbackMessage {
    CYK_Svgtitle, CYK_Feedback,
    DERIVATION_Svgtitle, DERIVATION_Feedback,
    ACCEPTOR_Svgtitle, ACCEPTOR_Feedback, ACCEPTOR_AAFeedback, ACCEPTOR_FAFeedback, ACCEPTOR_PDAFeedback,
    AUTOMATON_MatchesNotDeterministic, AUTOMATON_MatchesDeterministic, AUTOMATON_IsTuring, AUTOMATON_PARAMETERS_SQUAREBRACKETS, AUTOMATON_PARAMETERS_ERRORS, AUTOMATON_PARAMETERS_DUPLICATES,
    TRANSDUCER_Svgtitle, TRANSDUCER_Feedback, TRANSDUCER_Mealy, TRANSDUCER_Moore,
    ERROR_Svgtitle, ERROR_Feedback,
    GRAMMAR_Svgtitle, GRAMMAR_Feedback, GRAMMAR_Type,
    WORD_Svgtitle, WORD_Feedback,
    SVG_Svgtitle, SVG_Feedback;
}
