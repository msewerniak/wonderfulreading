package com.ms.wonderfulreading.students.lessons.noreps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoRepsLesson {

    public static final int INITIAL_STEP = 0;
    private final static int MAX_STEPS = 3;

    protected final Long id;
    protected List<String> sentences;
    protected int step;

    public NoRepsLesson(Long id, List<String> sentences, int step) {
        this.id = id;
        this.sentences = sentences;
        this.step = step;
    }

    public NoRepsLesson(NoRepsLesson noRepsLesson) {
        this(noRepsLesson.id, noRepsLesson.sentences, noRepsLesson.step);
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

    public List<String> getSentences() {
        return sentences;
    }

    public void setSentences(List<String> sentences) {
        this.sentences = sentences;
    }

    public Long getId() {
        return id;
    }

    public String summary() {
        return String.join(", ", sentences);
    }

    public String progress() {
        return step + "/" + MAX_STEPS;
    }
}
