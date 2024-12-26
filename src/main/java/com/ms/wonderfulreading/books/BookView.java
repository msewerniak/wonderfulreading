package com.ms.wonderfulreading.books;

import com.ms.wonderfulreading.BooksService;
import com.ms.wonderfulreading.MainView;
import com.ms.wonderfulreading.model.sentences.Book;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Optional;

@PageTitle("Book")
@Route(value = "book/:bookId", layout = MainView.class)
public class BookView extends FormLayout implements BeforeEnterObserver {

    public static final String BOOK_ID = "bookId";
    private final BooksService booksService;
    private Book book;

    public BookView(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> bookId = event.getRouteParameters().get(BOOK_ID).map(Long::parseLong);
        bookId.ifPresent(aLong -> this.book = booksService.get(aLong));

        refreshLayout();
    }

    private void refreshLayout() {

        book.getSentences().forEach(sentence -> {
            TextField textField = new TextField();
            textField.setValue(sentence.getSentence());
            textField.addValueChangeListener(event -> {
                Notification.show(event.getValue());
            });
            this.add(textField);
        });
    }
}
