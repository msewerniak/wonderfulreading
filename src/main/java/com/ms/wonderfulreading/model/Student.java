package com.ms.wonderfulreading.model;

import java.util.Collection;
import java.util.List;

public class Student {

    private final String name;
    private final List<WordLesson> words;
    private final List<SentenceLesson> sentences;
    private final List<BookLesson> books;

    private int currentWordLesson = 0;
    private int currentSentenceLesson = 0;
    private int currentBookLesson = 0;

    public Student(String name, List<Book> books) {

        this.name = name;
        this.words = books.stream().map(Book::getUnit).map(Unit::wordLessons).flatMap(Collection::stream).toList();
        this.sentences = books.stream().map(Book::getUnit).map(Unit::sentenceLessons).flatMap(Collection::stream).toList();
        this.books = books.stream().map(b -> new BookLesson(b.getSentences())).toList();
    }

    public WordLesson getCurrentWordLesson() {
        return words.get(currentWordLesson);
    }

    public SentenceLesson getCurrentSentenceLesson() {
        return sentences.get(currentSentenceLesson);
    }

    public BookLesson getCurrentBookLesson() {
        return books.get(currentBookLesson);
    }
}
