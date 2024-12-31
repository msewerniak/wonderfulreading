package com.ms.wonderfulreading.model.student.booklesson;

import com.ms.wonderfulreading.model.book.Book;
import com.ms.wonderfulreading.model.student.lesson.Lesson;

import java.util.List;

public class BookLesson extends Lesson {

    private final Book book;

    public BookLesson(Long id, List<String> sentences, Book book) {
        super(id, sentences);
        this.book = book;
    }

    public Book book() {
        return book;
    }

    @Override
    public String summary() {
        return String.format("%s: %s", book.getName(), super.summary());
    }
}
