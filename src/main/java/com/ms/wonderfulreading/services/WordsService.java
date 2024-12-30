package com.ms.wonderfulreading.services;

import com.ms.wonderfulreading.model.Word;
import com.ms.wonderfulreading.model.WordLesson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordsService {

    private static List<WordLesson> wordLessons = new ArrayList<>();

    public WordsService() {
        wordLessons.add(new WordLesson(new ArrayList<>(List.of(new Word("pociąg"), new Word("skakać"), new Word("drzwi")))));
        wordLessons.add(new WordLesson(new ArrayList<>(List.of(new Word("wiosna"), new Word("ścieżka"), new Word("altana")))));
        wordLessons.add(new WordLesson(new ArrayList<>(List.of(new Word("trzeba"), new Word("zielony"), new Word("potrzeba")))));
    }

    public List<WordLesson> getWordLessons() {
        return wordLessons;
    }

    public void save(List<WordLesson> wordLessons) {
        WordsService.wordLessons = wordLessons;
    }
}
