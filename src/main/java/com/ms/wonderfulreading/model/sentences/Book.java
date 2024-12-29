package com.ms.wonderfulreading.model.sentences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Book {

    private Long id;
    private String name;
    private Integer wordsPerDay;
    private Unit unit;
    private List<Sentence> sentences;

    public Book(Long id, String name, Integer wordsPerDay, List<String> sentences) {
        this(id, name, wordsPerDay, new Unit(), sentences);
    }

    public Book(Long id, String name, Integer wordsPerDay, Unit unit, List<String> sentences) {
        this.id = id;
        this.name = name;
        this.wordsPerDay = wordsPerDay;
        this.unit = unit;
        this.sentences = new ArrayList<>();
        sentences.forEach(s -> this.sentences.add(new Sentence(s)));
    }


    public Book(Book book) {
        this(book.id, book.name, book.wordsPerDay, book.unit, book.sentences.stream().map(Sentence::getSentence).toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    private Set<Word> wordsFromSentences() {
        return sentences.stream().map(Sentence::words).flatMap(Set::stream).filter(Word::hasValue).collect(Collectors.toSet());
    }

    private Set<Word> generateNewWords(List<Book> previousBooks) {

        Set<Word> previousWords = previousBooks.stream().map(Book::getUnit).map(Unit::newWords).flatMap(Set::stream).filter(Word::hasValue)
                .collect(Collectors.toSet());

        return wordsFromSentences().stream().filter(word -> !previousWords.contains(word)).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWordsPerDay() {
        return wordsPerDay;
    }

    public void setWordsPerDay(Integer wordsPerDay) {
        this.wordsPerDay = wordsPerDay;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void generateUnit(List<Book> previousBooks) {
        Set<Word> newWords = generateNewWords(previousBooks);
        this.unit = new Unit(newWords, wordsPerDay);
    }
}
