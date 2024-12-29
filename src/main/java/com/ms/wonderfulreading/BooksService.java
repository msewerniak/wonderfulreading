package com.ms.wonderfulreading;

import com.ms.wonderfulreading.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BooksService {

    private final List<Book> books = new ArrayList<>();

    public BooksService() {
        Book book1 = new Book(1L, "Book 1", 3, List.of("To jest Ola", "To jest Adaś", "To jest Mruczek"));
        book1.generateUnit(List.of());
        books.add(book1);

        Book book2 = new Book(2L, "Book 2", 3, List.of("Ala je kanapkę", "Adaś je banana", "Mruczek pije mleko"));
        book2.generateUnit(List.of(book1));
        books.add(book2);

        Book book3 = new Book(3L, "Book 3", 3, List.of("Adaś skacze", "Ola biega", "Mruczek śpi"));
        book3.generateUnit(List.of(book1, book2));
        books.add(book3);
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
