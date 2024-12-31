package com.ms.wonderfulreading.model.student;

import com.ms.wonderfulreading.model.student.booklesson.BookLessonsGenerator;
import com.ms.wonderfulreading.model.student.lesson.LessonsGenerator;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final Student student;

    public StudentService(BookLessonsGenerator bookLessonsGenerator, LessonsGenerator lessonsGenerator) {
        this.student = new Student("Szymon", bookLessonsGenerator.generateBookLessons(), lessonsGenerator.generateWordLessons());
    }

    public Student getStudent() {
        return this.student;
    }
}
