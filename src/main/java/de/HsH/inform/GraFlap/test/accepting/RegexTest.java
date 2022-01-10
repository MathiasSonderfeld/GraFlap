package de.HsH.inform.GraFlap.test.accepting;

import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.JflapWrapper.words.GenerateWords;
import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.scoring.accepting.RegexScoringTest;

public class RegexTest {

    private String wordFeedback="";

    public int getResult(String solution, Submission<String> studentInput, int numberOfWordsToBeGenerated) throws GraFlapException {
        String studentRegex = studentInput.getSubmissionObject();

        GenerateWords generateWords1 = new GenerateWords(numberOfWordsToBeGenerated);
        Testwords testwords1 = generateWords1.generateTestWords(solution);
        int score1 = testInput(studentRegex, testwords1);

        //GenerateWords generateWords2 = new GenerateWords(numberOfWordsToBeGenerated);
        //Testwords testwords2 = generateWords2.generateTestWords(studentRegex);
        //int score2 = testInput(solution, testwords2);

        return score1; // (score1+score2)/2;

    }

    public int getResult(String solution, Submission<String> studentInput, Testwords testwords) throws GraFlapException {
        String studentRegex = studentInput.getSubmissionObject();


        int score1 = testInput(studentRegex, testwords);

        //GenerateWords generateWords2 = new GenerateWords(numberOfWordsToBeGenerated);
        //Testwords testwords2 = generateWords2.generateTestWords(studentRegex);
        //int score2 = testInput(solution, testwords2);

        return score1; // (score1+score2)/2;

    }

        int testInput(String regex, Testwords testwords) throws GraFlapException {
        RegexScoringTest scoringTest = new RegexScoringTest(regex, testwords);
        wordFeedback = scoringTest.getWordFeedback();
        return scoringTest.returnScore();
    }

    public String getWordFeedback() {
        return wordFeedback;
    }
}
