package com.ms.wonderfulreading.model;

import java.util.List;

public final class BookLesson {

    private final List<Sentence> sentences;
    private boolean done;

    public BookLesson(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public List<Sentence> sentences() {
        return sentences;
    }

    public boolean isDone() {
        return done;
    }

    public void markDone() {
        this.done = true;
    }
}
