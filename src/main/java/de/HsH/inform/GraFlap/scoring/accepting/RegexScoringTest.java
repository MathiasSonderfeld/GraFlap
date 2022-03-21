package de.HsH.inform.GraFlap.scoring.accepting;

import de.HsH.inform.GraFlap.JflapWrapper.grammar.Grammar;
import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import de.HsH.inform.GraFlap.test.WordTest;

public class RegexScoringTest extends AcceptingScoringTest<String> {

        /**
         * constructor
         * @param object the grammar that should be used to test the words
         * @param testwords a list of words for testing
         * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
         */
    public RegexScoringTest( String object, Testwords testwords) throws GraFlapException {
            super(object, testwords);
        }

        /**
         * method to test the provided words against the given grammar
         * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
         */
        @Override
        protected void testing() throws GraFlapException {
            correctWordsCount = WordTest.checkWordsWithRegex(object, testwords.getCorrectWordsArray());
            String fb = WordTest.getNegativeFeedback();
            if (!fb.equals("")) { WordFeedback += "<p> false negative: " + fb + "</p>" + System.lineSeparator(); }
            wrongWordsCount = testwords.getFailingWords().size() - WordTest.checkWordsWithRegex(object, testwords.getFailingWordsArray());
            fb = WordTest.getPositiveFeedback();
            if (!fb.equals("")) { WordFeedback += "<p> false positive: " + fb + "</p>" + System.lineSeparator(); }
        }
}
