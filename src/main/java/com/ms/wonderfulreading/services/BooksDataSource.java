package com.ms.wonderfulreading.services;

import com.ms.wonderfulreading.model.Sentence;
import com.ms.wonderfulreading.model.Word;
import com.ms.wonderfulreading.model.book.Book;
import com.ms.wonderfulreading.model.book.SentenceLesson;
import com.ms.wonderfulreading.model.book.WordLesson;

import java.util.ArrayList;
import java.util.List;

public class BooksDataSource {

    public static List<Book> books() {

        List<WordLesson> wordLesson1 = List.of(new WordLesson(List.of(new Word("Adaś"), new Word("mama"), new Word("Mruczek"))),
                new WordLesson(List.of(new Word("Ola"), new Word("tata"), new Word("kot"))),
                new WordLesson(List.of(new Word("To"), new Word("jest"), new Word("Tam"))));
        List<SentenceLesson> sentenceLesson1 =
                List.of(new SentenceLesson(List.of(new Sentence("Ola to kot"), new Sentence("Tam jest Adaś"), new Sentence("Adaś to kot"))),
                        new SentenceLesson(
                                List.of(new Sentence("Mruczek to kot"), new Sentence("To jest kot"), new Sentence("Tam jest Ola"))),
                        new SentenceLesson(
                                List.of(new Sentence("To jest mama"), new Sentence("Tam jest Ola"), new Sentence("Tam jest mama"))));
        List<Sentence> sentences1 = List.of(new Sentence("To jest Adaś"), new Sentence("To jest Ola"), new Sentence("To jest mama"),
                new Sentence("To jest tata"), new Sentence("To jest mruczek"));
        Book book1 = new Book(0L, "Book 1", 3, sentences1, wordLesson1, sentenceLesson1);
        List<WordLesson> wordLesson2 = List.of(new WordLesson(List.of(new Word("Mysz"), new Word("serek"), new Word("naleśniki"))),
                new WordLesson(List.of(new Word("Pies"), new Word("kość"), new Word("smakołyki"))),
                new WordLesson(List.of(new Word("Kot"), new Word("rybkę"), new Word("je"))));
        List<SentenceLesson> sentenceLesson2 = List.of(new SentenceLesson(
                        List.of(new Sentence("Ola je smakołyki"), new Sentence("Kot je smakołyki"), new Sentence("Ola je serek"))),
                new SentenceLesson(
                        List.of(new Sentence("Adaś je naleśniki"), new Sentence("Mysz je naleśniki"), new Sentence("Adaś je rybkę"))),
                new SentenceLesson(
                        List.of(new Sentence("Mysz je rybkę"), new Sentence("Pies je serek"), new Sentence("Pies je smakołyki"))));
        Book book2 = new Book(1L, "Book 2", 3,
                List.of(new Sentence("Mysz je serek"), new Sentence("Pies je kość"), new Sentence("Kot je rybkę"),
                        new Sentence("Ola je naleśniki"), new Sentence("Adaś je smakołyki")), wordLesson2, sentenceLesson2);
        List<WordLesson> wordLesson3 = List.of(new WordLesson(List.of(new Word("Zosia"), new Word("Franek"), new Word("jedzie"))),
                new WordLesson(List.of(new Word("biega"), new Word("Bartek"), new Word("Magda"))),
                new WordLesson(List.of(new Word("tańczy"), new Word("skacze"), new Word("śpiewa"))));
        List<SentenceLesson> sentenceLesson3 = List.of(new SentenceLesson(
                        List.of(new Sentence("Zosia tańczy"), new Sentence("Franek skacze"), new Sentence("Franek tańczy"))),
                new SentenceLesson(List.of(new Sentence("Ola skacze"), new Sentence("Barek biega"), new Sentence("Bartek śpiewa"))),
                new SentenceLesson(List.of(new Sentence("Magda jedzie"), new Sentence("Ola jedzie"), new Sentence("Zosia biega"))));
        Book book3 = new Book(2L, "Book 3", 3,
                List.of(new Sentence("Zosia skacze"), new Sentence("Franek biega"), new Sentence("Ola tańczy"),
                        new Sentence("Bartek jedzie"), new Sentence("Magda śpiewa")), wordLesson3, sentenceLesson3);
        List<WordLesson> wordLesson4 = List.of(new WordLesson(List.of(new Word("zielona"), new Word("chmura"), new Word("żółta"))),
                new WordLesson(List.of(new Word("trawa"), new Word("błękitna"), new Word("łąka"))),
                new WordLesson(List.of(new Word("biała"), new Word("rzeka"), new Word("róża"))));
        List<SentenceLesson> sentenceLesson4 = List.of(new SentenceLesson(
                        List.of(new Sentence("zielona łąka"), new Sentence("żółta trawa"), new Sentence("zielona róża"))),
                new SentenceLesson(List.of(new Sentence("biała róża"), new Sentence("błękitna róża"), new Sentence("biała rzeka"))),
                new SentenceLesson(
                        List.of(new Sentence("błękitna chmura"), new Sentence("zielona chmura"), new Sentence("błękitna łąka"))));
        Book book4 = new Book(3L, "Book 4", 3,
                List.of(new Sentence("To jest zielona trawa"), new Sentence("To jest biała chmura"), new Sentence("To jest błękitna rzeka"),
                        new Sentence("To jest żółta łąka"), new Sentence("To jest róża")), wordLesson4, sentenceLesson4);

        return new ArrayList<>(List.of(book1, book2, book3, book4));
    }
}
