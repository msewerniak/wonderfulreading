package com.ms.wonderfulreading.model;

import java.util.ArrayList;
import java.util.List;

public class Student {

    private final String name;

    private final List<Lesson> lessons = new ArrayList<>();

    public Student(String name, List<Book> books) {
        this.name = name;

        for (Book book : books) {
            book.getUnit().wordLessons().forEach(wl -> {
                lessons.add(new Lesson(wl.words().stream().map(Word::getValue).toList(), book));
            });
            book.getUnit().sentenceLessons().forEach(sl -> {
                lessons.add(new Lesson(sl.sentences().stream().map(Sentence::getSentence).toList(), book));
            });
        }
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

}
