package com.ms.wonderfulreading.services;

import com.ms.wonderfulreading.model.book.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {

    private final List<Book> books;

    public BooksService() {
        this.books = BooksDataSource.books();
    }

    public List<Book> get() {
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
