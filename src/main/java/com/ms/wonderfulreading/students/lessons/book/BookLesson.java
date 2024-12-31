package com.ms.wonderfulreading.students.lessons.book;

import com.ms.wonderfulreading.learning.paths.books.Book;
import com.ms.wonderfulreading.students.lessons.noreps.NoRepsLesson;

import java.util.ArrayList;
import java.util.List;

public class BookLesson extends NoRepsLesson {
    
    private final Book book;

    public BookLesson(Long id, List<String> sentences, int step, Book book) {
        super(id, sentences, step);
        this.book = book;
    }

    public BookLesson(BookLesson bookLesson) {
        this(bookLesson.id, new ArrayList<>(bookLesson.sentences()), bookLesson.step, bookLesson.book);
    }

    public Book book() {
        return book;
    }

    @Override
    public String summary() {
        return String.format("%s: %s", book.getName(), super.summary());
    }
}
