package com.ms.wonderfulreading.services;

import com.ms.wonderfulreading.model.book.Book;
import com.ms.wonderfulreading.model.student.lesson.BookLesson;
import com.ms.wonderfulreading.model.Sentence;
import com.ms.wonderfulreading.model.book.SentenceLesson;
import com.ms.wonderfulreading.model.student.Student;
import com.ms.wonderfulreading.model.student.lesson.WordLesson;
import com.ms.wonderfulreading.model.Word;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final Student student;
    private final BooksService booksService;
    private final WordsService wordsService;

    public StudentService(BooksService booksService, WordsService wordsService) {
        this.booksService = booksService;
        this.wordsService = wordsService;

        this.student = create("Szymon");
    }

    public Student create(String name) {

        List<Book> books = booksService.getAll();
        List<com.ms.wonderfulreading.model.book.WordLesson> wordLessons = wordsService.getWordLessons();

        List<BookLesson> lessons = new ArrayList<>();
        long id = 0;
        for (Book book : books) {
            for (com.ms.wonderfulreading.model.book.WordLesson wl : book.getWordLessons()) {
                lessons.add(new BookLesson(id++, wl.words().stream().map(Word::getWord).toList(), book));
            }

            for (SentenceLesson sl : book.getSentenceLessons()) {
                lessons.add(new BookLesson(id++, sl.sentences().stream().map(Sentence::getSentence).toList(), book));
            }
        }

        List<WordLesson> words = new ArrayList<>();

        long idd = 0;
        for (com.ms.wonderfulreading.model.book.WordLesson wl : wordLessons) {
            words.add(new WordLesson(idd++, wl.words().stream().map(Word::getWord).toList()));
        }

        return new Student(name, lessons, words);
    }

    public Student getStudent() {
        return this.student;
    }
}
