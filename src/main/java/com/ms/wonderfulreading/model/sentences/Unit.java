package com.ms.wonderfulreading.model.sentences;

import java.util.ArrayList;
import java.util.List;

public class Unit {

    private final Long bookId;
    private List<WordLesson> wordLessons = new ArrayList<>();
    private List<SentenceLesson> sentenceLessons = new ArrayList<>();

    public Unit(Long bookId, List<Word> words, Integer perDay) {

        this.bookId = bookId;

        int days = words.size() % perDay == 0 ? words.size() / perDay : words.size() / perDay + 1;

        for (int i = 0; i < days; i++) {

            List<Word> lessonWords = new ArrayList<>();
            List<Sentence> lessonSentences = new ArrayList<>();

            for (int k = 0; k < perDay; k++) {

                int index = i * perDay + k;

                if (index < words.size()) {
                    lessonWords.add(words.get(index));
                } else {
                    lessonWords.add(new Word("?"));
                }
                lessonSentences.add(new Sentence(""));
            }

            wordLessons.add(new WordLesson(lessonWords));
            sentenceLessons.add(new SentenceLesson(lessonSentences));
        }
    }

    public List<WordLesson> lessons() {
        return wordLessons;
    }

    public List<SentenceLesson> sentenceLessons() {
        return sentenceLessons;
    }
}
