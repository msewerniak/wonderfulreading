package com.ms.wonderfulreading.learning.paths.noreps;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordsService {

    private static List<WordLesson> wordLessons = new ArrayList<>();

    public WordsService() {
        wordLessons.add(new WordLesson(new ArrayList<>(List.of(new Word("drzewo"), new Word("jedzie"), new Word("paczka")))));
        wordLessons.add(new WordLesson(new ArrayList<>(List.of(new Word("obraz"), new Word("książka"), new Word("drugi")))));
        wordLessons.add(new WordLesson(new ArrayList<>(List.of(new Word("lalka"), new Word("czerwony"), new Word("spać")))));
        wordLessons.add(new WordLesson(new ArrayList<>(List.of(new Word("małpka"), new Word("tam"), new Word("tramwaj")))));
        wordLessons.add(new WordLesson(new ArrayList<>(List.of(new Word("samochód"), new Word("jeleń"), new Word("samolot")))));
        wordLessons.add(new WordLesson(new ArrayList<>(List.of(new Word("koparka"), new Word("gość"), new Word("autobus")))));
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
