package com.ms.wonderfulreading.model.student;

import com.ms.wonderfulreading.model.student.lesson.BookLesson;
import com.ms.wonderfulreading.model.student.lesson.WordLesson;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private final String name;

    private final List<BookLesson> bookLessons = new ArrayList<>();
    private final List<WordLesson> randomWordLessons = new ArrayList<>();

    private BookLesson nextPath1Lesson;
    private BookLesson nextPath2Lesson;

    public Student(String name, List<BookLesson> bookLessons, List<WordLesson> randomWordLessons) {
        this.name = name;
        this.bookLessons.addAll(bookLessons);
        this.randomWordLessons.addAll(randomWordLessons);
    }

    public String getName() {
        return name;
    }

    public BookLesson bookLesson(Long id) {
        return bookLessons.stream().filter(l -> l.getId().equals(id)).findFirst().get();
    }

    public WordLesson randomLesson(Long id) {
        return randomWordLessons.stream().filter(w -> w.getId().equals(id)).findFirst().get();
    }

    public List<BookLesson> bookLessons() {
        return bookLessons;
    }

    public List<WordLesson> randomWordLessons() {
        return randomWordLessons;
    }

    public BookLesson nextPath1Lesson() {
        Long path2BookId = nextPath2Lesson != null ? nextPath2Lesson.book().getId() : null;
        nextPath1Lesson =
                bookLessons.stream().filter(l -> !l.isDone()).filter(lesson -> !lesson.book().getId().equals(path2BookId)).findFirst()
                        .orElse(null);
        return nextPath1Lesson;
    }

    public BookLesson nextPath2Lesson() {
        Long path1BookId = nextPath1Lesson != null ? nextPath1Lesson.book().getId() : null;
        nextPath2Lesson =
                bookLessons.stream().filter(l -> !l.isDone()).filter(lesson -> !lesson.book().getId().equals(path1BookId)).findFirst()
                        .orElse(null);
        return nextPath2Lesson;
    }

    public WordLesson nextWordLesson() {
        return randomWordLessons.stream().filter(l -> !l.isDone()).findFirst().orElse(null);
    }
}
