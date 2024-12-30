package com.ms.wonderfulreading.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Unit {

    private final List<WordLesson> wordLessons = new ArrayList<>();
    private final List<SentenceLesson> sentenceLessons = new ArrayList<>();

    public Unit() {
    }

    public Unit(Unit unit) {
        this(unit.wordLessons.stream().map(WordLesson::new).toList(), unit.sentenceLessons.stream().map(SentenceLesson::new).toList());
    }

    public Unit(List<WordLesson> wordLessons, List<SentenceLesson> sentenceLessons) {
        this.wordLessons.addAll(wordLessons);
        this.sentenceLessons.addAll(sentenceLessons);
    }
    
    public Unit(Set<Word> words, Integer perDay) {

        List<Word> wordList = new ArrayList<>(words);

        int days = wordList.size() % perDay == 0 ? wordList.size() / perDay : wordList.size() / perDay + 1;

        for (int i = 0; i < days; i++) {

            List<Word> lessonWords = new ArrayList<>();
            List<Sentence> lessonSentences = new ArrayList<>();

            for (int k = 0; k < perDay; k++) {

                int index = i * perDay + k;

                if (index < wordList.size()) {
                    lessonWords.add(wordList.get(index));
                } else {
                    lessonWords.add(new Word(""));
                }
                lessonSentences.add(new Sentence(""));
            }

            wordLessons.add(new WordLesson(lessonWords));
            sentenceLessons.add(new SentenceLesson(lessonSentences));
        }
    }

    public List<WordLesson> wordLessons() {
        return wordLessons;
    }

    public List<SentenceLesson> sentenceLessons() {
        return sentenceLessons;
    }

    public Set<Word> newWords() {
        return wordLessons.stream().map(WordLesson::words).flatMap(Collection::stream).filter(Word::hasValue).collect(Collectors.toSet());
    }
}
