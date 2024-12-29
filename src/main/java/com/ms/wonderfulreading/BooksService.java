package com.ms.wonderfulreading;

import com.ms.wonderfulreading.model.Book;
import com.ms.wonderfulreading.model.Sentence;
import com.ms.wonderfulreading.model.SentenceLesson;
import com.ms.wonderfulreading.model.Unit;
import com.ms.wonderfulreading.model.Word;
import com.ms.wonderfulreading.model.WordLesson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BooksService {

    private final List<Book> books = new ArrayList<>();

    public BooksService() {
        Book book1 = new Book(1L, "Book 1", 3, List.of("To jest Adaś", "To jest Ola", "To jest mama", "To jest tata", "To jest mruczek"));
        book1.setUnit(new Unit(List.of(new WordLesson(List.of(new Word("Adaś"), new Word("mama"), new Word("Mruczek"))),
                new WordLesson(List.of(new Word("Ola"), new Word("tata"), new Word("kot"))),
                new WordLesson(List.of(new Word("To"), new Word("jest"), new Word("Tam")))),
                List.of(new SentenceLesson(List.of(new Sentence("Ola to kot"), new Sentence("Tam jest Adaś"), new Sentence("Adaś to kot"))),
                        new SentenceLesson(
                                List.of(new Sentence("Mruczek to kot"), new Sentence("To jest kot"), new Sentence("Tam jest Ola"))),
                        new SentenceLesson(
                                List.of(new Sentence("To jest mama"), new Sentence("Tam jest Ola"), new Sentence("Tam jest mama"))))));
        books.add(book1);

        Book book2 = new Book(2L, "Book 2", 3,
                List.of("Mysz je serek", "Pies je kość", "Kot je rybkę", "Ola je naleśniki", "Adaś je smakołyki"));
        book2.setUnit(new Unit(List.of(new WordLesson(List.of(new Word("Mysz"), new Word("serek"), new Word("naleśniki"))),
                new WordLesson(List.of(new Word("Pies"), new Word("kość"), new Word("smakołyki"))),
                new WordLesson(List.of(new Word("Kot"), new Word("rybkę"), new Word("je")))), List.of(new SentenceLesson(
                        List.of(new Sentence("Ola je smakołyki"), new Sentence("Kot je smakołyki"), new Sentence("Ola je serek"))),
                new SentenceLesson(
                        List.of(new Sentence("Adaś je naleśniki"), new Sentence("Mysz je naleśniki"), new Sentence("Adaś je rybkę"))),
                new SentenceLesson(
                        List.of(new Sentence("Mysz je rybkę"), new Sentence("Pies je serek"), new Sentence("Pies je smakołyki"))))));
        books.add(book2);

        Book book3 = new Book(3L, "Book 3", 3, List.of("Zosia skacze", "Franek biega", "Ola tańczy", "Bartek jedzie", "Magda śpiewa"));
        book3.setUnit(new Unit(List.of(new WordLesson(List.of(new Word("Zosia"), new Word("Franek"), new Word("jedzie"))),
                new WordLesson(List.of(new Word("biega"), new Word("Bartek"), new Word("Magda"))),
                new WordLesson(List.of(new Word("tańczy"), new Word("skacze"), new Word("śpiewa")))), List.of(new SentenceLesson(
                        List.of(new Sentence("Zosia tańczy"), new Sentence("Franek skacze"), new Sentence("Franek tańczy"))),
                new SentenceLesson(List.of(new Sentence("Ola skacze"), new Sentence("Barek biega"), new Sentence("Bartek śpiewa"))),
                new SentenceLesson(List.of(new Sentence("Magda jedzie"), new Sentence("Ola jedzie"), new Sentence("Zosia biega"))))));
        books.add(book3);

        Book book4 = new Book(3L, "Book 3", 3,
                List.of("To jest zielona trawa", "To jest biała chmura", "To jest błękitna rzeka", "To jest żółta łąka", "To jest róża"));
        book3.setUnit(new Unit(List.of(new WordLesson(List.of(new Word("zielona"), new Word("chmura"), new Word("żółta"))),
                new WordLesson(List.of(new Word("trawa"), new Word("błękitna"), new Word("łąka"))),
                new WordLesson(List.of(new Word("biała"), new Word("rzeka"), new Word("róża")))), List.of(new SentenceLesson(
                        List.of(new Sentence("zielona łąka"), new Sentence("żółta trawa"), new Sentence("zielona róża"))),
                new SentenceLesson(List.of(new Sentence("biała róża"), new Sentence("błękitna róża"), new Sentence("biała rzeka"))),
                new SentenceLesson(
                        List.of(new Sentence("błękitna chmura"), new Sentence("zielona chmura"), new Sentence("błękitna łąka"))))));
        
        books.add(book4);
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Book> getPreviousBooks(Long bookId) {
        return books.stream().filter(b -> b.getId() < bookId).toList();
    }

    public Book getById(Long bookId) {
        return books.stream().filter(book -> book.getId().equals(bookId)).findFirst().orElse(null);
    }

    public void saveBook(Book book) {
        books.stream().filter(b -> b.getId().equals(book.getId())).findFirst().ifPresentOrElse(b -> {
            b.setName(book.getName());
            b.setUnit(book.getUnit());
            b.setWordsPerDay(book.getWordsPerDay());
            b.setSentences(book.getSentences());
        }, () -> books.add(book));
    }

    public Long nextBookId() {
        return books.size() + 1L;
    }
}
