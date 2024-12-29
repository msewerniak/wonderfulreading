package com.ms.wonderfulreading.books;

import com.ms.wonderfulreading.BooksService;
import com.ms.wonderfulreading.MainView;
import com.ms.wonderfulreading.model.sentences.Book;
import com.ms.wonderfulreading.model.sentences.Sentence;
import com.ms.wonderfulreading.model.sentences.SentenceLesson;
import com.ms.wonderfulreading.model.sentences.Unit;
import com.ms.wonderfulreading.model.sentences.Word;
import com.ms.wonderfulreading.model.sentences.WordLesson;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@PageTitle("Book")
@Route(value = "book/:bookId", layout = MainView.class)
public class BookView extends VerticalLayout implements BeforeEnterObserver {

    public static final String BOOK_ID = "bookId";
    private final BooksService booksService;
    private List<Book> previousBooks;
    private Book book;

    private final TextField bookTitleTextField = new TextField();
    private final IntegerField bookNumberOfDaysTextField = new IntegerField();
    private final HorizontalLayout lessonsLayout = new HorizontalLayout();
    private final VerticalLayout bookSentencesLayout = new VerticalLayout();

    private final TextField newWordsSearch = new TextField();
    private final Grid<Word> newWordsGrid = new Grid<>(Word.class, false);
    private final TextField knownWordsSearch = new TextField();
    private final Grid<Word> knownWordsGrid = new Grid<>(Word.class, false);

    public BookView(BooksService booksService) {
        this.booksService = booksService;
    }

    private FormLayout buildTitleAndDaysLayout() {

        FormLayout titleAndDaysLayout = new FormLayout();
        titleAndDaysLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0px", 1));
        titleAndDaysLayout.setLabelWidth("150px");

        bookTitleTextField.setValue(book.getName());
        bookTitleTextField.addValueChangeListener(event -> book.setName(event.getValue()));
        titleAndDaysLayout.addFormItem(bookTitleTextField, "Book title");

        bookNumberOfDaysTextField.setStepButtonsVisible(true);
        bookNumberOfDaysTextField.setMin(2);
        bookNumberOfDaysTextField.setMax(5);
        bookNumberOfDaysTextField.setValue(book.getWordsPerDay());
        bookNumberOfDaysTextField.addValueChangeListener(event -> {
            book.setWordsPerDay(event.getValue());
            book.generateUnit(previousBooks);
            refreshLessonsLayout();
        });
        titleAndDaysLayout.addFormItem(bookNumberOfDaysTextField, "Words per day");
        return titleAndDaysLayout;
    }

    private Component buildButtonsLayout() {

        Button cancel = new Button("Cancel", event -> {
            UI.getCurrent().navigate("books");
        });
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button save = new Button("Save", event -> {
            booksService.saveBook(book);
            UI.getCurrent().navigate("books");
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return new HorizontalLayout(save, cancel);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Long id = event.getRouteParameters().get(BOOK_ID).map(Long::parseLong).orElse(booksService.nextBookId());

        Book bookById = booksService.getById(id);
        this.book = bookById != null ? new Book(bookById) : new Book(id, "Book " + id, 3, new ArrayList<>());
        this.previousBooks = booksService.getPreviousBooks(id);

        add(buildTitleAndDaysLayout());
        add(lessonsLayout);
        add(buildButtonsLayout());
        add(buildWordsLayout());

        refreshLessonsLayout();
        refreshBookSentencesLayout();

        refreshNewWordsLayout();
        refreshKnownWordsLayout();
    }

    private void refreshLessonsLayout() {

        Unit unit = book.getUnit();

        List<WordLesson> wordLessons = unit.wordLessons();
        List<SentenceLesson> sentenceLessons = unit.sentenceLessons();

        lessonsLayout.removeAll();
        for (int i = 0; i < wordLessons.size(); i++) {
            lessonsLayout.add(buildWordsLessonLayout(wordLessons.get(i), i + 1));
        }

        for (int i = 0; i < sentenceLessons.size(); i++) {
            lessonsLayout.add(buildSentencesLessonLayout(sentenceLessons.get(i), wordLessons.size() + i + 1));
        }

        lessonsLayout.add(bookSentencesLayout);
    }

    private static Component createWordsLayout(Collection<Word> newWords) {

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMaxHeight("300px");

        newWords.forEach(word -> {

            TextField textField = new TextField();
            textField.setReadOnly(true);
            textField.setValue(word.getValue());

            verticalLayout.add(textField);
        });

        Scroller scroller = new Scroller(verticalLayout);
        scroller.getStyle().set("border", "1px solid var(--lumo-contrast-20pct)").set("padding", "var(--lumo-space-m)");
        return scroller;
    }

    private Component buildWordsLessonLayout(WordLesson lesson, int day) {

        VerticalLayout lessonVerticalLayout = new VerticalLayout();
        lessonVerticalLayout.add(new Span("Day " + day));

        lesson.words().forEach(word -> {
            TextField wordTextField = new TextField("", word.getValue(), "");
            wordTextField.setClearButtonVisible(true);
            wordTextField.addValueChangeListener(event -> {
                word.setValue(event.getValue());
                refreshNewWordsLayout();
            });
            lessonVerticalLayout.add(wordTextField);
        });

        return lessonVerticalLayout;
    }

    private Component buildSentencesLessonLayout(SentenceLesson lesson, int day) {

        VerticalLayout lessonVerticalLayout = new VerticalLayout();
        lessonVerticalLayout.add(new Span("Day " + day));

        lesson.words().forEach(sentence -> {
            TextField wordTextField = new TextField("", sentence.getSentence(), "");
            wordTextField.setClearButtonVisible(true);
            lessonVerticalLayout.add(wordTextField);
        });

        return lessonVerticalLayout;
    }

    private void refreshBookSentencesLayout() {

        bookSentencesLayout.removeAll();
        bookSentencesLayout.add(new Span("Book"));

        book.getSentences().forEach(sentence -> {

            TextField sentenceTextField = new TextField("", sentence.getSentence(), "");
            sentenceTextField.setClearButtonVisible(true);
            sentenceTextField.addValueChangeListener(event -> {
                sentence.setSentence(sentenceTextField.getValue());
                if (event.getValue().isEmpty()) {
                    clearEmptyBookSentencesAndRefresh();
                }
                book.generateUnit(previousBooks);
                refreshLessonsLayout();
                refreshNewWordsLayout();
            });

            bookSentencesLayout.add(sentenceTextField);
        });
        TextField newSentenceTextField = new TextField();
        newSentenceTextField.focus();
        newSentenceTextField.addValueChangeListener(event -> {
            book.getSentences().add(new Sentence(event.getValue()));
            refreshBookSentencesLayout();
            book.generateUnit(previousBooks);
            refreshLessonsLayout();
            refreshNewWordsLayout();
        });
        bookSentencesLayout.add(newSentenceTextField);
    }

    private void clearEmptyBookSentencesAndRefresh() {
        book.setSentences(book.getSentences().stream().filter(s -> !s.getSentence().isEmpty()).collect(Collectors.toList()));
        refreshBookSentencesLayout();
    }

    private HorizontalLayout buildWordsLayout() {

        return new HorizontalLayout(buildWordsLayout(newWordsGrid, newWordsSearch), buildWordsLayout(knownWordsGrid, knownWordsSearch));
    }

    private Component buildWordsLayout(Grid<Word> grid, TextField searchTextField) {

        grid.setMinWidth("200px");
        grid.addColumn(Word::getValue);

        searchTextField.setPlaceholder("Search");
        searchTextField.setClearButtonVisible(true);
        searchTextField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchTextField.setValueChangeMode(ValueChangeMode.EAGER);

        return new VerticalLayout(searchTextField, grid);
    }

    private void refreshNewWordsLayout() {
        Set<Word> newWords = book.getUnit().newWords();
        refreshWords(newWordsSearch, newWordsGrid, newWords);
    }

    private void refreshKnownWordsLayout() {

        Set<Word> knownWords =
                previousBooks.stream().map(Book::getUnit).map(Unit::newWords).flatMap(Collection::stream).collect(Collectors.toSet());
        refreshWords(knownWordsSearch, knownWordsGrid, knownWords);
    }

    private void refreshWords(TextField search, Grid<Word> grid, Set<Word> words) {

        GridListDataView<Word> dataView = grid.setItems(words);
        dataView.addFilter(word -> word.getValue().toLowerCase().startsWith(search.getValue().toLowerCase().trim()));

        search.addValueChangeListener(e -> dataView.refreshAll());
    }
}
