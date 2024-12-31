package com.ms.wonderfulreading.students;

import com.ms.wonderfulreading.students.lessons.book.BookLesson;
import com.ms.wonderfulreading.students.lessons.noreps.NoRepsLesson;

import java.util.List;
import java.util.stream.Collectors;

public class Student {

    private Long id;
    private String name;

    private List<BookLesson> bookLessons;
    private List<NoRepsLesson> noRepsLessons;

    private BookLesson nextPath1Lesson;
    private BookLesson nextPath2Lesson;

    public Student(Long id, String name, List<BookLesson> bookLessons, List<NoRepsLesson> noRepsLessons) {
        this.id = id;
        this.name = name;
        this.bookLessons = bookLessons;
        this.noRepsLessons = noRepsLessons;
    }

    /**
     * Copy constructor.
     */
    public Student(Student student) {
        this(student.id, student.name, student.bookLessons.stream().map(BookLesson::new).collect(Collectors.toList()),
                student.noRepsLessons.stream().map(NoRepsLesson::new).collect(Collectors.toList()));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookLesson> getBookLessons() {
        return bookLessons;
    }

    public void setBookLessons(List<BookLesson> bookLessons) {
        this.bookLessons = bookLessons;
    }

    public List<NoRepsLesson> getNoRepsLessons() {
        return noRepsLessons;
    }

    public void setNoRepsLessons(List<NoRepsLesson> noRepsLessons) {
        this.noRepsLessons = noRepsLessons;
    }

    public BookLesson bookLesson(Long id) {
        return bookLessons.stream().filter(l -> l.getId().equals(id)).findFirst().get();
    }

    public NoRepsLesson noRepsLesson(Long id) {
        return noRepsLessons.stream().filter(w -> w.getId().equals(id)).findFirst().get();
    }

    public List<BookLesson> bookLessons() {
        return bookLessons;
    }

    public List<NoRepsLesson> noRepsLessons() {
        return noRepsLessons;
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

    public NoRepsLesson nextWordLesson() {
        return noRepsLessons.stream().filter(l -> !l.isDone()).findFirst().orElse(null);
    }
}
