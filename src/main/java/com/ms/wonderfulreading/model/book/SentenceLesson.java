package com.ms.wonderfulreading.model.book;

import com.ms.wonderfulreading.model.Sentence;

import java.util.List;
import java.util.stream.Collectors;

public final class SentenceLesson {

    private final List<Sentence> sentences;

    /**
     * Copy constructor.
     */
    public SentenceLesson(SentenceLesson sentenceLesson) {
        this(sentenceLesson.sentences.stream().map(Sentence::new).collect(Collectors.toList()));
    }

    public SentenceLesson(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public List<Sentence> sentences() {
        return sentences;
    }
}
