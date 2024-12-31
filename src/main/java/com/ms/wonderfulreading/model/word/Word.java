package com.ms.wonderfulreading.model.word;

import java.util.Objects;

public class Word {

    private String word;

    /**
     * Copy constructor.
     */
    public Word(Word word) {
        this(word.getWord());
    }
    
    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean hasValue() {
        return word != null && !word.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Word word = (Word) o;
        return Objects.equals(this.word, word.word);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(word);
    }

    @Override
    public String toString() {
        return word;
    }
}
