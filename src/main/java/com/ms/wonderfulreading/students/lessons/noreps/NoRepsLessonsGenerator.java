package com.ms.wonderfulreading.students.lessons.noreps;

import com.ms.wonderfulreading.learning.paths.noreps.Word;
import com.ms.wonderfulreading.learning.paths.noreps.WordLesson;
import com.ms.wonderfulreading.learning.paths.noreps.WordsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NoRepsLessonsGenerator {

    private final WordsService wordsService;

    public NoRepsLessonsGenerator(WordsService wordsService) {
        this.wordsService = wordsService;
    }

    public List<NoRepsLesson> generate() {
        List<WordLesson> wordLessons = wordsService.getWordLessons();

        List<NoRepsLesson> wor1dLessons = new ArrayList<>();

        long id = 0;
        for (WordLesson wl : wordLessons) {
            wor1dLessons.add(new NoRepsLesson(id++, wl.words().stream().map(Word::getWord).toList(), NoRepsLesson.INITIAL_STEP));
        }
        return wor1dLessons;
    }
}
