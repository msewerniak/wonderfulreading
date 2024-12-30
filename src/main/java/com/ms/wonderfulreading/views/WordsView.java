package com.ms.wonderfulreading.views;

import com.ms.wonderfulreading.MainView;
import com.ms.wonderfulreading.model.Word;
import com.ms.wonderfulreading.model.book.WordLesson;
import com.ms.wonderfulreading.services.WordsService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Words")
@Route(value = "words", layout = MainView.class)
public class WordsView extends VerticalLayout {

    private final VerticalLayout lessonsLayout = new VerticalLayout();
    private final List<WordLesson> wordLessons;
    private final WordsService wordsService;
    private int dayToSetFocus = 0;

    public WordsView(WordsService wordsService) {
        wordLessons = new ArrayList<>(wordsService.getWordLessons().stream().map(WordLesson::new).toList());

        add(lessonsLayout);
        add(buildButtonsLayout());

        refreshLessonsLayout();
        this.wordsService = wordsService;
    }

    private Component buildButtonsLayout() {

        Button cancel = new Button("Cancel", event -> {
            UI.getCurrent().navigate("words");
        });
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button save = new Button("Save", event -> {
            wordsService.save(wordLessons);
            UI.getCurrent().navigate("words");
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return new HorizontalLayout(save, cancel);
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
