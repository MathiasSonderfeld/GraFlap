package de.HsH.inform.GraFlap.BlackBoxTests;

import de.HsH.inform.GraFlap.entity.Testwords;

public class BlackBoxTestTestwordsUtil{
    public static final Testwords emptyTestwords = new Testwords(0, 0);

    public static Testwords getTestWords(String in){
        String[] wordsSplitByCategory = in.split("!");
        String[] correctWordsArray = wordsSplitByCategory[0].split("%");

        String[] failingWordsArray;
        if(wordsSplitByCategory.length > 1)
            failingWordsArray = wordsSplitByCategory[1].split("%");
        else
            failingWordsArray = new String[]{""};

        Testwords testwords = new Testwords(correctWordsArray.length, failingWordsArray.length);
        for(String word : correctWordsArray){
            testwords.addToCorrectWords(word);
        }
        for(String word : failingWordsArray){
            testwords.addToFailingWords(word);
        }
        return testwords;
    }
}
