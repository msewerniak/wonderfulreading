package com.ms.wonderfulreading.model.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {

    private final List<Book> books;

    public BooksService() {
        this.books = BooksRepository.books();
    }

    public List<Book> getAll() {
        return books;
    }

    public List<Book> getPreviousBooks(Long bookId) {
        return books.stream().filter(b -> b.getId() < bookId).toList();
    }

    public Book getById(Long bookId) {
        return books.stream().filter(book -> book.getId().equals(bookId)).findFirst().orElse(null);
    }

    public void saveBook(Book book) {
        if (book.getId() < books.size()) {
            books.set(book.getId().intValue(), book);
        } else {
            books.add(book);
        }
    }

    public Long nextBookId() {
        return books.size() + 1L;
    }
}
