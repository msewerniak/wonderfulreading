package com.ms.wonderfulreading.model.sentences;

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
        return Arrays.stream(sentence.split("\s")).map(Word::new).collect(Collectors.toSet());
    }
}
