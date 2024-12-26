package com.ms.wonderfulreading.books;

import com.ms.wonderfulreading.BooksService;
import com.ms.wonderfulreading.MainView;
import com.ms.wonderfulreading.model.sentences.Book;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Books")
@Route(value = "books", layout = MainView.class)
public class BooksView extends Div {

    @Autowired
    public BooksView(BooksService booksService) {
        booksService.getBooks().forEach(book -> add(addBookComponent(book)));
    }

    private Component addBookComponent(Book book) {

        Span span = new Span(book.getName());
        span.getStyle().set("font-weight", "bold");

        VerticalLayout layout = new VerticalLayout(span);

        book.getSentences().forEach(sentence -> {
            layout.add(new Span(sentence.getSentence()));
        });

        layout.addClickListener(event -> UI.getCurrent().navigate("book/" + book.getId()));

        return new Div(layout);
    }
}