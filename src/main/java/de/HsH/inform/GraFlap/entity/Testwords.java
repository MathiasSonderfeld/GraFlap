package de.HsH.inform.GraFlap.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

/**
 * Datastructure to store the given words for grading
 * @author Benjamin Held (07-04-2016)
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value de.HsH.inform.GraFlap.GraFlap#version}
 */
public class Testwords {
    private ArrayList<String> correctWords;
    private ArrayList<String> failingWords;

    private HashMap<String, String> wordpairs;
    private String singleWord;

    public Testwords(){
        correctWords = new ArrayList<>();
        failingWords = new ArrayList<>();
        wordpairs = new HashMap<>();
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

    public void addToWordPairs(String from, String to){
        this.wordpairs.put(from, to);
    }

    public void setSingleWord(String word){
        this.singleWord = word;
    }



    public ArrayList<String> getCorrectWords() {
        return correctWords;
    }

    public ArrayList<String> getFailingWords() {
        return failingWords;
    }

    public HashMap<String, String> getWordpairs(){
        return wordpairs;
    }

    public String getSingleWord(){
        return singleWord;
    }

    public String[] getCorrectWordsArray(){
        return correctWords.toArray(new String[correctWords.size()]);
    }

    public String[] getFailingWordsArray(){
        return failingWords.toArray(new String[failingWords.size()]);
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
