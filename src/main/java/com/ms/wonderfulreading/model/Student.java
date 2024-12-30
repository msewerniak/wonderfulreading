package com.ms.wonderfulreading.model;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private final String name;

    private final List<Lesson> lessons = new ArrayList<>();

    private Lesson nextPath1Lesson;
    private Lesson nextPath2Lesson;

    public Student(String name, List<Lesson> lessons) {
        this.name = name;
        this.lessons.addAll(lessons);

        nextPath1Lesson = lessons.get(0);
        nextPath2Lesson = lessons.stream().filter(l -> !l.book().getId().equals(nextPath1Lesson.book().getId())).findFirst().get();
    }

    public Lesson byId(Long id) {
        return lessons.stream().filter(l -> l.getId().equals(id)).findFirst().get();
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public Lesson nextPath1Lesson() {
        Long path2BookId = nextPath2Lesson != null ? nextPath2Lesson.book().getId() : null;
        return lessons.stream().filter(l -> !l.isDone()).filter(lesson -> !lesson.book().getId().equals(path2BookId)).findFirst()
                .orElse(null);
    }

    public Lesson nextPath2Lesson() {
        Long path1BookId = nextPath1Lesson != null ? nextPath1Lesson.book().getId() : null;
        return lessons.stream().filter(l -> !l.isDone()).filter(lesson -> !lesson.book().getId().equals(path1BookId)).findFirst()
                .orElse(null);
    }
}
