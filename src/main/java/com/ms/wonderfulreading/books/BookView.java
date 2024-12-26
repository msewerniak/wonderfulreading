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
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@PageTitle("Book")
@Route(value = "book/:bookId", layout = MainView.class)
public class BookView extends VerticalLayout implements BeforeEnterObserver {

    public static final String BOOK_ID = "bookId";
    private final BooksService booksService;
    private List<Book> previousBooks;
    private Book book;

    private FormLayout titleFormLayout = new FormLayout();
    private FormLayout sentencesFormLayout = new FormLayout();
    private VerticalLayout newWordsHorizontalLayout = new VerticalLayout();
    private VerticalLayout knownWordsHorizontalLayout = new VerticalLayout();
    private HorizontalLayout lessonsHorizontalLayout = new HorizontalLayout();
    private HorizontalLayout generateLessonsHorizontalLayout = new HorizontalLayout();
    private HorizontalLayout buttonsHorizontalLayout = new HorizontalLayout();

    public BookView(BooksService booksService) {
        this.booksService = booksService;
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

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> bookId = event.getRouteParameters().get(BOOK_ID).map(Long::parseLong);

        bookId.ifPresent(id -> {
            Book bookById = booksService.getById(id);
            this.book = bookById != null ? new Book(bookById) : new Book(id, id.toString(), new ArrayList<>());
        });
        bookId.ifPresent(id -> this.previousBooks = booksService.getPreviousBooks(id));

        add(titleFormLayout);

        add(new HorizontalLayout(sentencesFormLayout, newWordsHorizontalLayout, knownWordsHorizontalLayout));
        add(buttonsHorizontalLayout);
        add(generateLessonsHorizontalLayout);
        add(lessonsHorizontalLayout);

        buildTitleField();
        buildLessonsGenerateButtons();
        buildSaveCancelButtons();

        refreshKnownWordsLayout();
        refreshSentencesLayout();
    }

    private void buildTitleField() {
        TextField nameTextField = new TextField();
        nameTextField.setValue(book.getName());
        titleFormLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0px", 1));
        titleFormLayout.setLabelWidth("150px");
        titleFormLayout.addFormItem(nameTextField, "Book name");
    }

    private void buildLessonsGenerateButtons() {

        TextField wordsPerDay = new TextField("Words per day");
        wordsPerDay.setValue("3");

        Button generateLessonsButton = new Button("Generate lessons");
        generateLessonsButton.addClickListener(event -> refreshLessonsLayout(wordsPerDay.getValue()));

        generateLessonsHorizontalLayout.setDefaultVerticalComponentAlignment(Alignment.END);
        generateLessonsHorizontalLayout.add(wordsPerDay, generateLessonsButton);
    }

    private void refreshLessonsLayout(String wordsPerDay) {

        List<Word> newWords = book.newWords(previousBooks);

        Unit unit = new Unit(book.getId(), newWords, Integer.parseInt(wordsPerDay));
        List<WordLesson> wordLessons = unit.lessons();
        List<SentenceLesson> sentenceLessons = unit.sentenceLessons();

        lessonsHorizontalLayout.removeAll();
        for (int i = 0; i < wordLessons.size(); i++) {
            lessonsHorizontalLayout.add(buildWordsLessonLayout(wordLessons.get(i), i + 1));
        }

        for (int i = 0; i < sentenceLessons.size(); i++) {
            lessonsHorizontalLayout.add(buildSentencesLessonLayout(sentenceLessons.get(i), wordLessons.size() + i + 1));
        }
    }

    private Component buildWordsLessonLayout(WordLesson lesson, int day) {

        VerticalLayout lessonVerticalLayout = new VerticalLayout();
        lessonVerticalLayout.add(new Span("Day " + day));

        lesson.words().forEach(word -> {
            lessonVerticalLayout.add(new TextField("", "", word.getValue()));
        });

        return lessonVerticalLayout;
    }

    private Component buildSentencesLessonLayout(SentenceLesson lesson, int day) {

        VerticalLayout lessonVerticalLayout = new VerticalLayout();
        lessonVerticalLayout.add(new Span("Day " + day));

        lesson.words().forEach(sentence -> {
            lessonVerticalLayout.add(new TextField("", "", sentence.getSentence()));
        });

        return lessonVerticalLayout;
    }

    private void refreshSentencesLayout() {

        sentencesFormLayout.removeAll();
        sentencesFormLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        sentencesFormLayout.setLabelWidth("150px");

        book.getSentences().forEach(sentence -> {
            TextField sentenceTextField = new TextField();
            sentenceTextField.setValue(sentence.getSentence());
            sentenceTextField.addValueChangeListener(event -> {
                sentence.setSentence(sentenceTextField.getValue());
                refreshNewWordsLayout();
            });
            sentencesFormLayout.addFormItem(sentenceTextField, "Sentence");
        });

        TextField newSentenceTextField = new TextField();
        newSentenceTextField.addValueChangeListener(event -> {
            book.getSentences().add(new Sentence(event.getValue()));
            refreshSentencesLayout();
        });
        sentencesFormLayout.addFormItem(newSentenceTextField, "Sentence");
        newSentenceTextField.focus();

        refreshNewWordsLayout();
    }

    private void buildSaveCancelButtons() {

        Button cancel = new Button("Cancel", event -> {
            UI.getCurrent().navigate("books");
        });
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button save = new Button("Save", event -> {
            booksService.saveBook(book);
            UI.getCurrent().navigate("books");
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        buttonsHorizontalLayout.add(save, cancel);
    }

    private void refreshNewWordsLayout() {

        List<Word> newWords = book.newWords(this.previousBooks);

        newWordsHorizontalLayout.removeAll();
        newWordsHorizontalLayout.add(new Span("New words: " + newWords.size()));
        newWordsHorizontalLayout.add(createWordsLayout(newWords));
    }

    private void refreshKnownWordsLayout() {

        Set<Word> knownWords = previousBooks.stream().map(Book::words).flatMap(Collection::stream).collect(Collectors.toSet());

        knownWordsHorizontalLayout.removeAll();
        knownWordsHorizontalLayout.add(new Span("Known words: " + knownWords.size()));
        knownWordsHorizontalLayout.add(createWordsLayout(knownWords));
    }

}
