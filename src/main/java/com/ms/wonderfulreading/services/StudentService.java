package com.ms.wonderfulreading.services;

import com.ms.wonderfulreading.model.Book;
import com.ms.wonderfulreading.model.Lesson;
import com.ms.wonderfulreading.model.Sentence;
import com.ms.wonderfulreading.model.SentenceLesson;
import com.ms.wonderfulreading.model.Student;
import com.ms.wonderfulreading.model.Word;
import com.ms.wonderfulreading.model.WordLesson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final Student student;

    public StudentService(BooksService booksService) {

        List<Book> books = booksService.getBooks();

        List<Lesson> lessons = new ArrayList<>();
        long id = 0;
        for (Book book : books) {
            for (WordLesson wl : book.getUnit().wordLessons()) {
                lessons.add(new Lesson(id++, wl.words().stream().map(Word::getValue).toList(), book));
            }

            for (SentenceLesson sl : book.getUnit().sentenceLessons()) {
                lessons.add(new Lesson(id++, sl.sentences().stream().map(Sentence::getSentence).toList(), book));
            }
        }

        this.student = new Student("Szymon", lessons);
    }

    public Student getStudent() {
        return this.student;
    }
}
