package com.ms.wonderfulreading.model;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Sentence {

    private String sentence;

    public Sentence() {
    }

    public Sentence(String sentence) {
        this.sentence = sentence;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    Set<Word> words() {
        return Arrays.stream(sentence.split("\s")).filter(k -> !k.isEmpty()).map(Word::new).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return sentence;
    }
}
