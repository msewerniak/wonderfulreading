package com.ms.wonderfulreading;

import com.ms.wonderfulreading.model.sentences.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {

    private final List<Book> books = List.of(new Book(1L, "1", "To jest Ola", "To jest Adaś", "To jest Mruczek"),
            new Book(2L, "2", "Ala je kanapkę", "Adaś je banana", "Mruczek pije mleko"),
            new Book(3L, "3", "Adaś skacze", "Ola biega", "Mruczek śpi"));

    public List<Book> getBooks() {
        return books;
    }

    public List<Book> getPreviousBooks(Long bookId) {
        return books.stream().filter(b -> b.getId() < bookId).toList();
    }

    public Book getById(Long bookId) {
        return books.stream().filter(book -> book.getId().equals(bookId)).findFirst().orElse(null);
    }
}
