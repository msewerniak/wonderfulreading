package com.ms.wonderfulreading.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lesson {

    private final static int MAX_STEPS = 3;

    private final Book book;
    private final Long id;
    private final List<String> sentences;
    private int step;

    public Lesson(Long id, List<String> sentences, Book book) {
        this.id = id;
        this.sentences = sentences;
        this.book = book;
        this.step = 0;
    }

    public List<String> nextSentences() {
        return shuffle(sentences);
    }

    private List<String> shuffle(List<String> sentences) {

        List<String> sentencesClone = new ArrayList<>(sentences);
        List<String> shuffled = new ArrayList<>();

        while (!sentencesClone.isEmpty()) {
            int random = new Random().nextInt(sentencesClone.size());
            String removed = sentencesClone.remove(random);
            shuffled.add(removed);
        }

        return shuffled;
    }

    public void stepDone() {
        this.step++;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean isInProgress() {
        return step > 0;
    }

    public boolean isDone() {
        return step >= MAX_STEPS;
    }

    public List<String> sentences() {
        return sentences;
    }

    public Book book() {
        return book;
    }

    public Long getId() {
        return id;
    }

    public String summary() {
        return String.format("%s: %s", book.getName(), String.join(", ", sentences));
    }

    public String progress() {
        return step + "/" + MAX_STEPS;
    }
}
