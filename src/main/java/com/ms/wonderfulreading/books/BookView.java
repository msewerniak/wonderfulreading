package com.ms.wonderfulreading.books;

import com.ms.wonderfulreading.BooksService;
import com.ms.wonderfulreading.MainView;
import com.ms.wonderfulreading.model.sentences.Book;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Optional;

@PageTitle("Book")
@Route(value = "book/:bookId", layout = MainView.class)
public class BookView extends VerticalLayout implements BeforeEnterObserver {

    public static final String BOOK_ID = "bookId";
    private final BooksService booksService;
    private List<Book> previousBooks;
    private Book book;

    public BookView(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> bookId = event.getRouteParameters().get(BOOK_ID).map(Long::parseLong);

        bookId.ifPresentOrElse(id -> this.book = booksService.getById(id), () -> this.book = new Book());
        bookId.ifPresent(id -> this.previousBooks = booksService.getPreviousBooks(id));

        add(buildBookLayout());
    }

    private Component buildBookLayout() {

        TextField bookNameTextField = new TextField();
        bookNameTextField.setValue(book.getName());

        List<TextField> list = book.getSentences().stream().map(sentence -> {
            TextField textField = new TextField();
            textField.setValue(sentence.getSentence());
            textField.addValueChangeListener(event -> {

            });
            return textField;
        }).toList();

        FormLayout formLayout = new FormLayout();
        formLayout.setLabelWidth("150px");
        formLayout.addFormItem(bookNameTextField, "Book name");
        list.forEach(textField -> formLayout.addFormItem(textField, "Sentence"));

        Component buttonsLayout = buttonsLayout();

        return new VerticalLayout(formLayout, buttonsLayout);
    }

    private Component buttonsLayout() {

        Button cancel = new Button("Cancel");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button save = new Button("Save", event -> {
            
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        buttonLayout.add(save, cancel);

        return buttonLayout;
    }

}
