package com.ms.wonderfulreading.students;

import com.ms.wonderfulreading.learning.paths.books.Book;
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

    public List<NoRepsLesson> getNoRepsLessons() {
        return noRepsLessons;
    }

    public void updateBookLessons(List<BookLesson> bookLessons) {
        for (BookLesson bookLesson : bookLessons) {
            Long bookLessonId = bookLesson.getId();
            this.bookLessons.stream().filter(b1 -> b1.getId().equals(bookLessonId)).findFirst().ifPresentOrElse(b -> {
                if (!isAnyBookLessonInProgress(b.book())) {
                    b.setSentences(bookLesson.getSentences());
                }
            }, () -> this.bookLessons.add(bookLesson));
        }
    }

    private boolean isAnyBookLessonInProgress(Book book) {
        boolean inprogress = this.bookLessons.stream().filter(l -> {
            Book book1 = l.book();
            return book1.getId().equals(book.getId());
        }).anyMatch(NoRepsLesson::isInProgress);
        return inprogress;
    }

    public void updateNoRepsLessons(List<NoRepsLesson> noRepsLessons) {
        for (NoRepsLesson noRepsLesson : noRepsLessons) {
            Long noRepsLessonId = noRepsLesson.getId();
            this.noRepsLessons.stream().filter(b1 -> b1.getId().equals(noRepsLessonId)).findFirst().ifPresentOrElse(b -> {
                if (!b.isInProgress()) {
                    b.setSentences(noRepsLesson.getSentences());
                }
            }, () -> this.noRepsLessons.add(noRepsLesson));
        }
    }

    public BookLesson bookLesson(Long id) {
        return bookLessons.stream().filter(l -> l.getId().equals(id)).findFirst().get();
    }

    public NoRepsLesson noRepsLesson(Long id) {
        return noRepsLessons.stream().filter(w -> w.getId().equals(id)).findFirst().get();
    }

    public BookLesson nextPath1BookLesson() {
        Long path2BookId = nextPath2Lesson != null ? nextPath2Lesson.book().getId() : null;
        nextPath1Lesson =
                bookLessons.stream().filter(l -> !l.isDone()).filter(lesson -> !lesson.book().getId().equals(path2BookId)).findFirst()
                        .orElse(null);
        return nextPath1Lesson;
    }

    public BookLesson nextPath2BookLesson() {
        Long path1BookId = nextPath1Lesson != null ? nextPath1Lesson.book().getId() : null;
        nextPath2Lesson =
                bookLessons.stream().filter(l -> !l.isDone()).filter(lesson -> !lesson.book().getId().equals(path1BookId)).findFirst()
                        .orElse(null);
        return nextPath2Lesson;
    }

    public NoRepsLesson nextNoRepsLesson() {
        return noRepsLessons.stream().filter(l -> !l.isDone()).findFirst().orElse(null);
    }
}
