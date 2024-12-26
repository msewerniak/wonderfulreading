package com.ms.wonderfulreading.admin;

import com.ms.wonderfulreading.MainView;
import com.ms.wonderfulreading.model.sentences.Book;
import com.ms.wonderfulreading.model.sentences.SentencesService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@PageTitle("Admin")
@Route(value = "admin", layout = MainView.class)
public class AdminView extends SplitLayout {

    public final SentencesService sentencesService;
    private final List<Book> books = List.of(new Book(1L, "1", "To jest Ola", "To jest Adaś", "To jest Mruczek"),
            new Book(2L, "2", "Ala je kanapkę", "Adaś je banana", "Mruczek pije mleko"),
            new Book(3L, "3", "Adaś skacze", "Ola biega", "Mruczek śpi"));

    Binder<Book> binder = new Binder<>(Book.class);
    private Book selectedBook = new Book("nothing");
    private Grid<Book> grid;

    @Autowired
    public AdminView(SentencesService sentencesService) {

        this.sentencesService = sentencesService;

        addToPrimary(createListComponent());
        addToSecondary(createEditorLayout());
    }

    private Component createEditorLayout() {

        TextField bookNameTextField = new TextField("Name");

        TextField sentence1TextField = new TextField("Sentence 1");
        TextField sentence2TextField = new TextField("Sentence 2");
        TextField sentence3TextField = new TextField("Sentence 3");

        Component buttonsLayout = buttonsLayout();

        binder.bind(bookNameTextField, book -> book.getName(), (book, name) -> book.setName(name));
        binder.bind(sentence1TextField, book -> book.getSentence1(), (book, sentence) -> book.setSentence1(sentence));
        binder.bind(sentence2TextField, book -> book.getSentence2(), (book, sentence) -> book.setSentence2(sentence));
        binder.bind(sentence3TextField, book -> book.getSentence3(), (book, sentence) -> book.setSentence3(sentence));

        try {
            binder.writeBean(selectedBook);
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }

        return new FormLayout(bookNameTextField, sentence1TextField, sentence2TextField, sentence3TextField, buttonsLayout);
    }

    private Component buttonsLayout() {
        Button cancel = new Button("Cancel");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button save = new Button("Save", event -> {
            try {
                binder.writeBean(selectedBook);
                grid.select(null);
                grid.getDataProvider().refreshAll();
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        buttonLayout.add(save, cancel);

        return buttonLayout;
    }

    private VerticalLayout createListComponent() {

        grid = new Grid<>(Book.class, false);
        grid.addColumn(Book::getName).setHeader("Book name");
        grid.addColumn(Book::getSentence1).setHeader("Sentence 1");
        grid.addColumn(Book::getSentence2).setHeader("Sentence 2");
        grid.addColumn(Book::getSentence3).setHeader("Sentence 3");
        grid.removeAllHeaderRows();
        grid.setItems(books);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                this.selectedBook = event.getValue();
                this.binder.readBean(selectedBook);
            }
        });

        return new VerticalLayout(grid);
    }

}