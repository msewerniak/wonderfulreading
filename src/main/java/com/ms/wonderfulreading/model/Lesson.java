package com.ms.wonderfulreading.model;

import java.util.List;

public class Lesson {

    private final List<String> sentences;
    private final Book book;

    private boolean done;

    public Lesson(List<String> sentences, Book book) {
        this.sentences = sentences;
        this.book = book;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public List<String> sentences() {
        return sentences;
    }

    public Book book() {
        return book;
    }
}
