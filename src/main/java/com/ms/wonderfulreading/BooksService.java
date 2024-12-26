package com.ms.wonderfulreading;

import com.ms.wonderfulreading.model.sentences.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BooksService {

    private final List<Book> books = new ArrayList<>();

    public BooksService() {
        books.add(new Book(1L, "1", List.of("To jest Ola", "To jest Adaś", "To jest Mruczek")));
        books.add(new Book(2L, "2", List.of("Ala je kanapkę", "Adaś je banana", "Mruczek pije mleko")));
        books.add(new Book(3L, "3", List.of("Adaś skacze", "Ola biega", "Mruczek śpi")));
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
            b.setSentences(book.getSentences());
        }, () -> books.add(book));
    }

    public Integer nextBookId() {
        return books.size() + 1;
    }
}
