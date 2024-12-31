package com.ms.wonderfulreading.model.student.booklesson;

import com.ms.wonderfulreading.model.book.Sentence;
import com.ms.wonderfulreading.model.word.Word;
import com.ms.wonderfulreading.model.book.Book;
import com.ms.wonderfulreading.model.book.SentenceLesson;
import com.ms.wonderfulreading.model.book.BooksService;
import com.ms.wonderfulreading.model.word.WordLesson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookLessonsGenerator {

    private final BooksService booksService;

    public BookLessonsGenerator(BooksService booksService) {
        this.booksService = booksService;
    }

    public List<BookLesson> generateBookLessons() {
        List<Book> books = booksService.getAll();

        List<BookLesson> bookLessons = new ArrayList<>();
        long id = 0;
        for (Book book : books) {
            for (WordLesson wl : book.getWordLessons()) {
                bookLessons.add(new BookLesson(id++, wl.words().stream().map(Word::getWord).toList(), book));
            }

            for (SentenceLesson sl : book.getSentenceLessons()) {
                bookLessons.add(new BookLesson(id++, sl.sentences().stream().map(Sentence::getSentence).toList(), book));
            }
        }
        return bookLessons;
    }
}
