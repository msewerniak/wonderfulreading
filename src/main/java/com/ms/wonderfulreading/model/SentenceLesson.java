package com.ms.wonderfulreading.model;

import java.util.List;

public final class SentenceLesson {

    private final List<Sentence> sentences;

    /**
     * Copy constructor.
     */
    public SentenceLesson(SentenceLesson sentenceLesson) {
        this(sentenceLesson.sentences.stream().map(Sentence::new).toList());
    }

    public SentenceLesson(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public List<Sentence> sentences() {
        return sentences;
    }
}
