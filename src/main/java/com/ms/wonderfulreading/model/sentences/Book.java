package com.ms.wonderfulreading.model.sentences;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Book {

    private Long id;
    private String name;
    private List<Sentence> sentences = List.of(new Sentence(), new Sentence(), new Sentence());

    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }

    public Book(Long id, String name, String s1, String s2, String s3) {
        this.id = id;
        this.name = name;
        setSentence1(s1);
        setSentence2(s2);
        setSentence3(s3);
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

    public String getSentence1() {
        return sentences.get(0).getSentence();
    }

    public void setSentence1(String value) {
        sentences.get(0).setSentence(value);
    }

    public String getSentence2() {
        return sentences.get(1).getSentence();
    }

    public void setSentence2(String value) {
        sentences.get(1).setSentence(value);
    }

    public String getSentence3() {
        return sentences.get(2).getSentence();
    }

    public void setSentence3(String value) {
        sentences.get(2).setSentence(value);
    }

    Set<Word> words() {
        return sentences.stream().map(Sentence::words).flatMap(Set::stream).collect(Collectors.toSet());
    }

    public Set<Word> newWords(List<Book> previousBooks) {

        Set<Word> previousWords = previousBooks.stream().map(Book::words).flatMap(Set::stream).collect(Collectors.toSet());

        return words().stream().filter(word -> !previousWords.contains(word)).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
