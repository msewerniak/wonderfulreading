package com.ms.wonderfulreading.model.sentences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Book {

    private Long id;
    private String name;
    private List<Sentence> sentences;

    public Book(Long id, String name, List<String> sentences) {
        this.id = id;
        this.name = name;
        this.sentences = new ArrayList<>();
        sentences.forEach(s -> this.sentences.add(new Sentence(s)));
    }

    public Book(Book book) {
        this(book.id, book.name, book.sentences.stream().map(Sentence::getSentence).toList());
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

    public Set<Word> words() {
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
