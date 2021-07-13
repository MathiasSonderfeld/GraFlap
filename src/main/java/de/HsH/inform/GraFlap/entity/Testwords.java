package de.HsH.inform.GraFlap.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Testwords {
    private ArrayList<String> correctWords;
    private ArrayList<String> failingWords;

    public Testwords(int correctWordsSize, int failingWordsSize){
        correctWords = new ArrayList<>(correctWordsSize);
        failingWords = new ArrayList<>(failingWordsSize);
    }

    public void addToCorrectWords(String word){
        correctWords.add(word);
    }

    public void addAllToCorrectWords( Collection<String> collection){
        this.correctWords.addAll(collection);
    }

    public void addToFailingWords(String word){
        failingWords.add(word);
    }

    public void addAllToFailingWords(Collection<String> collection){
        this.failingWords.addAll(collection);
    }

    public String[] getCorrectWordsArray(){
        return correctWords.toArray(new String[correctWords.size()]);
    }

    public String[] getFailingWordsArray(){
        return failingWords.toArray(new String[failingWords.size()]);
    }

    public String getWordString(){
        StringBuilder sb = new StringBuilder();
        if(correctWords.size() > 0){
            for(int i = 0; i < correctWords.size()-1; i++) {
                sb.append(correctWords.get(i)).append("%");
            }
            sb.append(correctWords.get(correctWords.size()-1)).append("!");
        }
        if(failingWords.size() > 0){
            for(int i = 0; i < failingWords.size()-1; i++) {
                sb.append(failingWords.get(i)).append("%");
            }
            sb.append(failingWords.get(failingWords.size()-1));
        }
        if(sb.length() == 0) sb.append("-");
        return sb.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if(this == o) { return true; }
        if(!( o instanceof Testwords )) { return false; }
        Testwords testwords = (Testwords) o;
        return Objects.equals(correctWords, testwords.correctWords) && Objects.equals(failingWords, testwords.failingWords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(correctWords, failingWords);
    }
}
