package com.ms.wonderfulreading.model;

import java.util.ArrayList;
import java.util.List;

public final class WordLesson {

    private final List<Word> words;

    /**
     * Copy constructor.
     */
    public WordLesson(WordLesson wordLesson) {
        this(new ArrayList<>(wordLesson.words.stream().map(Word::new).toList()));
    }

    public WordLesson(List<Word> words) {
        this.words = words;
    }

    public List<Word> words() {
        return words;
    }
}
