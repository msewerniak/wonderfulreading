package com.ms.wonderfulreading.learning.paths.books;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Sentence {

    private String sentence;

    /**
     * Copy constructor.
     */
    public Sentence(Sentence sentence) {
        this(sentence.getSentence());
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

    public Set<Word> words() {
        return Arrays.stream(sentence.split("\s")).filter(k -> !k.isEmpty()).map(Word::new).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return sentence;
    }
}
