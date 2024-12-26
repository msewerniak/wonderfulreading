package com.ms.wonderfulreading.model.sentences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public record Schedule(List<Book> books) {

    private static final Integer wordsPerLesson = 5;

    List<Unit> generate() {

        List<Unit> units = new ArrayList<>();

        List<Book> previousBooks = new ArrayList<>();

        for (Book book : books) {

            units.add(createUnit(book, previousBooks));
            previousBooks.add(book);
        }

        return units;
    }

    private Unit createUnit(Book book, List<Book> previousBooks) {

        Set<Word> newWords = book.newWords(previousBooks);

        System.out.println("Found " + book.words().size() + " words and " + newWords.size() + " new words");

        List<Lesson> lessons = new ArrayList<>();

        while (newWords.iterator().hasNext()) {

            List<Word> wordsForNewLesson = new ArrayList<>();
            for (int i = 0; i < wordsPerLesson; i++) {
                if (newWords.iterator().hasNext()) {
                    wordsForNewLesson.add(newWords.iterator().next());
                }
            }
            lessons.add(new Lesson(wordsForNewLesson));
        }

        return new Unit(lessons);
    }
}
