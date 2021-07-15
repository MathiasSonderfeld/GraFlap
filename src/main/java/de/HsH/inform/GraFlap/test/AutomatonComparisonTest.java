package de.HsH.inform.GraFlap.test;

import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.SetResult;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.State;
import de.HsH.inform.GraFlap.entity.AutomatonAsFormal.Transition;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.io.parsing.XmlAutomatonParser;

public class AutomatonComparisonTest {
    private SetResult<State> statesResult = new SetResult<>();
    private SetResult<State> initialsResult = new SetResult<>();
    private SetResult<State> finalsResult = new SetResult<>();
    private SetResult<String> alphabetResult = new SetResult<>();
    private SetResult<String> stackAlphabetResult = new SetResult<>();
    private SetResult<Transition> transitionsResult = new SetResult<>();

    public AutomatonComparisonTest(String taskXML, String submissionXML) throws GraFlapException {
        XmlAutomatonParser taskXMLparser = new XmlAutomatonParser(taskXML);
        XmlAutomatonParser submissionXmlParser = new XmlAutomatonParser(submissionXML);
        SetGrader.grade(taskXMLparser.getXmlStates(), submissionXmlParser.getXmlStates(), statesResult);
        SetGrader.grade(taskXMLparser.getXmlInitialStates(), submissionXmlParser.getXmlInitialStates(), initialsResult);
        SetGrader.grade(taskXMLparser.getXmlFinalStates(), submissionXmlParser.getXmlFinalStates(), finalsResult);
        SetGrader.grade(taskXMLparser.getXmlAlphabet(), submissionXmlParser.getXmlAlphabet(), alphabetResult);
        SetGrader.grade(taskXMLparser.getXmlStackAlphabet(), submissionXmlParser.getXmlStackAlphabet(), stackAlphabetResult);
        SetGrader.grade(taskXMLparser.getXmlTransitions(), submissionXmlParser.getXmlTransitions(), transitionsResult);
    }

    public int getTotalErrors(){
        return statesResult.getTotalErrors() + initialsResult.getTotalErrors() + finalsResult.getTotalErrors() + alphabetResult.getTotalErrors() + stackAlphabetResult.getTotalErrors() + transitionsResult
                .getTotalErrors();
    }

    public SetResult<State> getStatesResult() {
        return statesResult;
    }

    public SetResult<State> getInitialsResult() {
        return initialsResult;
    }

    public SetResult<State> getFinalsResult() {
        return finalsResult;
    }

    public SetResult<String> getAlphabetResult() {
        return alphabetResult;
    }

    public SetResult<String> getStackAlphabetResult() {
        return stackAlphabetResult;
    }

    public SetResult<Transition> getTransitionsResult() {
        return transitionsResult;
    }
}
