package com.ms.wonderfulreading.model.student;

import com.ms.wonderfulreading.model.student.lesson.BookLesson;
import com.ms.wonderfulreading.model.student.lesson.RandomWordLesson;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private final String name;

    private final List<BookLesson> lessons = new ArrayList<>();
    private final List<RandomWordLesson> words = new ArrayList<>();

    private BookLesson nextPath1Lesson;
    private BookLesson nextPath2Lesson;

    public Student(String name, List<BookLesson> lessons, List<RandomWordLesson> words) {
        this.name = name;
        this.lessons.addAll(lessons);
        this.words.addAll(words);

        nextPath1Lesson = lessons.get(0);
        nextPath2Lesson = lessons.stream().filter(l -> !l.book().getId().equals(nextPath1Lesson.book().getId())).findFirst().get();
    }

    public BookLesson byId(Long id) {
        return lessons.stream().filter(l -> l.getId().equals(id)).findFirst().get();
    }

    public RandomWordLesson wLessonById(Long id) {
        return words.stream().filter(w -> w.getId().equals(id)).findFirst().get();
    }

    public List<BookLesson> getLessons() {
        return lessons;
    }

    public List<RandomWordLesson> getWords() {
        return words;
    }

    public BookLesson nextPath1Lesson() {
        Long path2BookId = nextPath2Lesson != null ? nextPath2Lesson.book().getId() : null;
        return lessons.stream().filter(l -> !l.isDone()).filter(lesson -> !lesson.book().getId().equals(path2BookId)).findFirst()
                .orElse(null);
    }

    public BookLesson nextPath2Lesson() {
        Long path1BookId = nextPath1Lesson != null ? nextPath1Lesson.book().getId() : null;
        return lessons.stream().filter(l -> !l.isDone()).filter(lesson -> !lesson.book().getId().equals(path1BookId)).findFirst()
                .orElse(null);
    }

    public RandomWordLesson nextWordLesson() {
        return words.stream().filter(l -> !l.isDone()).findFirst().orElse(null);
    }
}
