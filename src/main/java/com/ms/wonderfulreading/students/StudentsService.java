package com.ms.wonderfulreading.students;

import com.ms.wonderfulreading.students.lessons.book.BookLessonsGenerator;
import com.ms.wonderfulreading.students.lessons.noreps.NoRepsLessonsGenerator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentsService {

    private final List<Student> students;

    public StudentsService(BookLessonsGenerator bookLessonsGenerator, NoRepsLessonsGenerator lessonsGenerator) {
        this.students = new ArrayList<>(List.of(new Student(0L, "Szymon", bookLessonsGenerator.generate(), lessonsGenerator.generate())));
    }

    public Student getByName(String name) {
        return students.stream().filter(student -> student.getName().equals(name)).findFirst().orElse(null);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void save(Student student) {
        if (student.getId() < students.size()) {
            students.set(student.getId().intValue(), student);
        } else {
            students.add(student);
        }
    }

    public Long nextStudentId() {
        return (long) students.size();
    }
}
