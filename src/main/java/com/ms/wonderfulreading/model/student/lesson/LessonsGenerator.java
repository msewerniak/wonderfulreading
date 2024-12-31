package com.ms.wonderfulreading.model.student.lesson;

import com.ms.wonderfulreading.model.word.Word;
import com.ms.wonderfulreading.model.word.WordLesson;
import com.ms.wonderfulreading.model.word.WordsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LessonsGenerator {

    private final WordsService wordsService;

    public LessonsGenerator(WordsService wordsService) {
        this.wordsService = wordsService;
    }

    public List<Lesson> generateWordLessons() {
        List<WordLesson> wordLessons = wordsService.getWordLessons();

        List<Lesson> wor1dLessons = new ArrayList<>();

        long id = 0;
        for (WordLesson wl : wordLessons) {
            wor1dLessons.add(new Lesson(id++, wl.words().stream().map(Word::getWord).toList()));
        }
        return wor1dLessons;
    }
}
