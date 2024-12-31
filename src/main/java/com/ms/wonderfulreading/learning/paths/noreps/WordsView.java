package com.ms.wonderfulreading.learning.paths.noreps;

import com.ms.wonderfulreading.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Words")
@Route(value = "words", layout = MainView.class)
public class WordsView extends VerticalLayout {

    private final VerticalLayout lessonsLayout = new VerticalLayout();
    private final WordsService wordsService;
    private List<WordLesson> wordLessons;
    private int dayToSetFocus = 0;
    private Button cancelButton;
    private Button saveButton;

    public WordsView(WordsService wordsService) {
        this.wordsService = wordsService;

        add(lessonsLayout);
        add(buildButtonsLayout());

        loadWordLessons();
        refreshLessonsLayout();
    }

    private void loadWordLessons() {
        this.wordLessons = wordsService.getWordLessons().stream().map(WordLesson::new).collect(Collectors.toList());
    }

    private Component buildButtonsLayout() {

        cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelButton.addClickListener(event -> {
            loadWordLessons();
            refreshLessonsLayout();
        });

        saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(event -> {
            wordsService.save(wordLessons.stream().filter(w -> !w.isEmpty()).toList());
            Notification.show("Saved");
            loadWordLessons();
            refreshLessonsLayout();
        });

        return new HorizontalLayout(saveButton, cancelButton);
    }

    private void refreshLessonsLayout() {

        lessonsLayout.removeAll();
        int i = 0;
        for (; i < wordLessons.size(); i++) {
            lessonsLayout.add(buildWordsLessonLayout(wordLessons.get(i), i + 1));
        }
        lessonsLayout.add(buildNewWordLessonLayout(new WordLesson(new ArrayList<>()), i + 1));
    }

    private Component buildNewWordLessonLayout(WordLesson newWordLesson, int day) {
        HorizontalLayout lessonHorizontalLayout = new HorizontalLayout();
        lessonHorizontalLayout.add(new Span("Day " + day));

        TextField newWordTextField = new TextField();
        newWordTextField.setClearButtonVisible(true);
        if (dayToSetFocus == day) {
            newWordTextField.focus();
            dayToSetFocus = -1;
        }
        newWordTextField.addValueChangeListener(event -> {
            dayToSetFocus = day;
            newWordLesson.words().add(new Word(event.getValue()));
            wordLessons.add(newWordLesson);
            refreshLessonsLayout();
        });
        lessonHorizontalLayout.add(newWordTextField);

        return lessonHorizontalLayout;
    }

    private Component buildWordsLessonLayout(WordLesson lesson, int day) {

        HorizontalLayout lessonHorizontalLayout = new HorizontalLayout();
        lessonHorizontalLayout.add(new Span("Day " + day));

        lesson.words().forEach(word -> {
            TextField wordTextField = new TextField("", word.getWord(), "");
            wordTextField.setClearButtonVisible(true);
            wordTextField.addValueChangeListener(event -> {
                word.setWord(event.getValue());
            });
            lessonHorizontalLayout.add(wordTextField);
        });

        if (lesson.words().size() < 5) {
            TextField newWordTextField = new TextField();
            newWordTextField.setClearButtonVisible(true);
            if (dayToSetFocus == day) {
                newWordTextField.focus();
                dayToSetFocus = -1;
            }
            newWordTextField.addValueChangeListener(event -> {
                dayToSetFocus = day;
                lesson.words().add(new Word(event.getValue()));
                refreshLessonsLayout();
            });
            lessonHorizontalLayout.add(newWordTextField);
        }

        return lessonHorizontalLayout;
    }
}
