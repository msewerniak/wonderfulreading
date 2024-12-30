package com.ms.wonderfulreading.model.book;

import com.ms.wonderfulreading.model.Sentence;
import com.ms.wonderfulreading.model.Word;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Book {

    private Long id;
    private String name;
    private Integer wordsPerDay;

    private List<Sentence> sentences;
    private List<WordLesson> wordLessons;
    private List<SentenceLesson> sentenceLessons;

    public Book(Long id, String name, Integer wordsPerDay, List<Sentence> sentences, List<WordLesson> wordLessons,
            List<SentenceLesson> sentenceLessons) {
        this.id = id;
        this.name = name;
        this.wordsPerDay = wordsPerDay;
        this.sentences = sentences;
        this.wordLessons = wordLessons;
        this.sentenceLessons = sentenceLessons;
    }

    /**
     * Copy constructor.
     */
    public Book(Book book) {
        this(book.id, book.name, book.wordsPerDay, book.sentences.stream().map(Sentence::new).collect(Collectors.toList()),
                book.wordLessons.stream().map(WordLesson::new).collect(Collectors.toList()),
                book.sentenceLessons.stream().map(SentenceLesson::new).collect(Collectors.toList()));
    }

    public List<WordLesson> getWordLessons() {
        return wordLessons;
    }

    public List<SentenceLesson> getSentenceLessons() {
        return sentenceLessons;
    }

    // Methods

    public Set<Word> newWords() {
        return wordLessons.stream().map(WordLesson::words).flatMap(Collection::stream).filter(Word::hasValue).collect(Collectors.toSet());
    }

    private Set<Word> wordsFromSentences() {
        return sentences.stream().map(Sentence::words).flatMap(Set::stream).filter(Word::hasValue).collect(Collectors.toSet());
    }

    private Set<Word> generateNewWords(List<Book> previousBooks) {

        Set<Word> previousWords =
                previousBooks.stream().map(Book::newWords).flatMap(Set::stream).filter(Word::hasValue).collect(Collectors.toSet());

        return wordsFromSentences().stream().filter(word -> !previousWords.contains(word)).collect(Collectors.toSet());
    }

    public void generateUnit(List<Book> previousBooks) {
        Set<Word> newWords = generateNewWords(previousBooks);

        List<Word> wordList = new ArrayList<>(newWords);

        int days = wordList.size() % wordsPerDay == 0 ? wordList.size() / wordsPerDay : wordList.size() / wordsPerDay + 1;

        wordLessons.clear();
        sentenceLessons.clear();

        for (int i = 0; i < days; i++) {

            List<Word> lessonWords = new ArrayList<>();
            List<Sentence> lessonSentences = new ArrayList<>();

            for (int k = 0; k < wordsPerDay; k++) {

                int index = i * wordsPerDay + k;

                if (index < wordList.size()) {
                    lessonWords.add(wordList.get(index));
                } else {
                    lessonWords.add(new Word(""));
                }
                lessonSentences.add(new Sentence(""));
            }

            wordLessons.add(new WordLesson(lessonWords));
            sentenceLessons.add(new SentenceLesson(lessonSentences));
        }
    }

    // Getters, setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWordsPerDay() {
        return wordsPerDay;
    }

    public void setWordsPerDay(Integer wordsPerDay) {
        this.wordsPerDay = wordsPerDay;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }
}
