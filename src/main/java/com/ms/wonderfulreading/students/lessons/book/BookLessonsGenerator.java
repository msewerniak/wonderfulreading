package com.ms.wonderfulreading.students.lessons.book;

import com.ms.wonderfulreading.learning.paths.books.Sentence;
import com.ms.wonderfulreading.learning.paths.books.Word;
import com.ms.wonderfulreading.learning.paths.books.Book;
import com.ms.wonderfulreading.learning.paths.books.SentenceLesson;
import com.ms.wonderfulreading.learning.paths.books.BooksService;
import com.ms.wonderfulreading.learning.paths.books.WordLesson;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookLessonsGenerator {

    private final BooksService booksService;

    public BookLessonsGenerator(BooksService booksService) {
        this.booksService = booksService;
    }

    public List<BookLesson> generate() {
        List<Book> books = booksService.getAll();

        List<BookLesson> bookLessons = new ArrayList<>();
        long id = 0;
        for (Book book : books) {
            for (WordLesson wl : book.getWordLessons()) {
                bookLessons.add(new BookLesson(id++, wl.words().stream().map(Word::getWord).toList(), BookLesson.INITIAL_STEP, book));
            }

            for (SentenceLesson sl : book.getSentenceLessons()) {
                bookLessons.add(
                        new BookLesson(id++, sl.sentences().stream().map(Sentence::getSentence).toList(), BookLesson.INITIAL_STEP, book));
            }
        }
        return bookLessons;
    }
}
