package de.HsH.inform.GraFlap.BlackBoxTests;

import de.HsH.inform.GraFlap.entity.Testwords;
import de.HsH.inform.GraFlap.entity.ValuePair;

public class BlackBoxTestTestwordsUtil{
    public static final Testwords emptyTestwords = new Testwords();

    public static Testwords getListTestWords(String in){
        String[] wordsSplitByCategory = in.split("!");
        String[] correctWordsArray = wordsSplitByCategory[0].split("%");

        String[] failingWordsArray;
        if(wordsSplitByCategory.length > 1)
            failingWordsArray = wordsSplitByCategory[1].split("%");
        else
            failingWordsArray = new String[]{""};

        Testwords testwords = new Testwords();
        for(String word : correctWordsArray){
            testwords.addToCorrectWords(word);
        }
        for(String word : failingWordsArray){
            testwords.addToFailingWords(word);
        }
        return testwords;
    }

    public static Testwords getPairsTestWords(String in){
        Testwords testwords = new Testwords();
        String[] wordPairsArray = in.split("%");
        String[] pairArray;
        for(int pair = 0; pair < wordPairsArray.length; pair++){
            pairArray = wordPairsArray[pair].split(";");
            testwords.addToWordPairs(new ValuePair<>(pairArray[0], pairArray[1]));
        }
        return testwords;
    }

    public static Testwords getSinglleWordTestwords(String in){
        Testwords testwords = new Testwords();
        testwords.setSingleWord(in);
        return testwords;
    }
}
