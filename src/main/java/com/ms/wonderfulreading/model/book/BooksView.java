package com.ms.wonderfulreading.model.book;

import com.ms.wonderfulreading.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Books")
@Route(value = "books", layout = MainView.class)
public class BooksView extends Div {

    private final BooksService booksService;

    @Autowired
    public BooksView(BooksService booksService) {
        this.booksService = booksService;
        add(addBookButton());
        booksService.getAll().forEach(book -> add(addBookComponent(book)));
    }

    private Component addBookButton() {

        Button addBookButton = new Button("Add Book");
        addBookButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        addBookButton.addClickListener(event -> {
            UI.getCurrent().navigate("book/" + booksService.nextBookId());
        });
        return new HorizontalLayout(addBookButton);
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