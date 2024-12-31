package com.ms.wonderfulreading.model.word;

import java.util.List;
import java.util.stream.Collectors;

public final class WordLesson {

    private final List<Word> words;

    /**
     * Copy constructor.
     */
    public WordLesson(WordLesson wordLesson) {
        this(wordLesson.words.stream().map(Word::new).collect(Collectors.toList()));
    }

    public WordLesson(List<Word> words) {
        this.words = words;
    }

    public List<Word> words() {
        return words;
    }
}
