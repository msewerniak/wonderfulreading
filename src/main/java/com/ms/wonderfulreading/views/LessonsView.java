package com.ms.wonderfulreading.views;

import com.ms.wonderfulreading.MainView;
import com.ms.wonderfulreading.model.student.lesson.BookLesson;
import com.ms.wonderfulreading.model.student.Student;
import com.ms.wonderfulreading.model.student.lesson.RandomWordLesson;
import com.ms.wonderfulreading.services.StudentService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Lessons")
@RouteAlias(value = "", layout = MainView.class)
@Route(value = "lessons", layout = MainView.class)
public class LessonsView extends HorizontalLayout {

    private final Button nextPath1LessonButton = new Button();
    private final Button nextPath2LessonButton = new Button();
    private final Button nextWordsButton = new Button();

    private final Grid<BookLesson> lessonsGrid = new Grid<>(BookLesson.class, false);
    private final Grid<RandomWordLesson> wordsGrid = new Grid<>(RandomWordLesson.class, false);
    
    private final Student student;

    public LessonsView(StudentService studentService) {
        student = studentService.getStudent();

        setSizeFull();

        add(new VerticalLayout(new HorizontalLayout(nextPath1LessonButton, nextPath2LessonButton), lessonsGrid));
        add(new VerticalLayout(nextWordsButton, wordsGrid));

        lessonsGrid.addColumn(lesson -> lesson.book().getName());
        lessonsGrid.addColumn(BookLesson::progress);
        lessonsGrid.addColumn(new LessonValueProvider(0));
        lessonsGrid.addColumn(new LessonValueProvider(1));
        lessonsGrid.addColumn(new LessonValueProvider(2));
        lessonsGrid.addColumn(new LessonValueProvider(3));
        lessonsGrid.addColumn(new LessonValueProvider(4));
        lessonsGrid.setItems(student.getLessons());

        lessonsGrid.setPartNameGenerator(lesson -> lesson.isDone() ? "high-rating" : (lesson.isInProgress() ? "low-rating" : ""));
        lessonsGrid.addItemDoubleClickListener(event -> {
            UI.getCurrent().navigate("lesson/" + event.getItem().getId());
        });

        wordsGrid.addColumn(RandomWordLesson::progress);
        wordsGrid.addColumn(new WLessonValueProvider(0));
        wordsGrid.addColumn(new WLessonValueProvider(1));
        wordsGrid.addColumn(new WLessonValueProvider(2));
        wordsGrid.addColumn(new WLessonValueProvider(3));
        wordsGrid.addColumn(new WLessonValueProvider(4));
        wordsGrid.setItems(student.getWords());

        wordsGrid.setPartNameGenerator(lesson -> lesson.isDone() ? "high-rating" : (lesson.isInProgress() ? "low-rating" : ""));
        wordsGrid.addItemDoubleClickListener(event -> {
            UI.getCurrent().navigate("wlesson/" + event.getItem().getId());
        });
        
        refreshNextPathButtons(student.nextPath1Lesson(), nextPath1LessonButton);
        refreshNextPathButtons(student.nextPath2Lesson(), nextPath2LessonButton);
        refreshWLessonButtons(student.nextWordLesson(), nextWordsButton);
    }

    private void refreshNextPathButtons(BookLesson lesson, Button lessonButton) {

        if (lesson != null) {
            lessonButton.setText(lesson.summary());
            lessonButton.addClickListener(event -> UI.getCurrent().navigate("lesson/" + lesson.getId()));
        } else {
            lessonButton.setText("Completed !");
            lessonButton.setEnabled(false);
        }
    }

    private void refreshWLessonButtons(RandomWordLesson lesson, Button lessonButton) {

        if (lesson != null) {
            lessonButton.setText(lesson.summary());
            lessonButton.addClickListener(event -> UI.getCurrent().navigate("wlesson/" + lesson.getId()));
        } else {
            lessonButton.setText("Completed !");
            lessonButton.setEnabled(false);
        }
    }

    static class LessonValueProvider implements ValueProvider<BookLesson, String> {

        private final int index;

        LessonValueProvider(int index) {
            this.index = index;
        }

        @Override
        public String apply(BookLesson lesson) {
            return lesson.sentences().size() > index ? lesson.sentences().get(index) : null;
        }
    }

    static class WLessonValueProvider implements ValueProvider<RandomWordLesson, String> {

        private final int index;

        WLessonValueProvider(int index) {
            this.index = index;
        }

        @Override
        public String apply(RandomWordLesson lesson) {
            return lesson.sentences().size() > index ? lesson.sentences().get(index) : null;
        }
    }

}
