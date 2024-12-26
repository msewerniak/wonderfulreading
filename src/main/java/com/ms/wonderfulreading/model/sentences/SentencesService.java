package com.ms.wonderfulreading.model.sentences;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SentencesService {

    private final List<Book> books = new ArrayList<>();

    public SentencesService() {
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Lesson> generateLessons() {
        return new Schedule(books).generate().stream().map(Unit::lessons).flatMap(Collection::stream).toList();
    }
}
