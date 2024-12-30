package com.ms.wonderfulreading.reading;

import com.ms.wonderfulreading.MainView;
import com.ms.wonderfulreading.model.Lesson;
import com.ms.wonderfulreading.model.Student;
import com.ms.wonderfulreading.student.StudentService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Reading")
@RouteAlias(value = "", layout = MainView.class)
@Route(value = "reading", layout = MainView.class)
public class ReadingView extends VerticalLayout {

    private final Button nextPath1LessonButton = new Button();
    private final Button nextPath2LessonButton = new Button();

    private final Grid<Lesson> lessonsGrid = new Grid<>(Lesson.class, false);
    private final Student student;

    public ReadingView(StudentService studentService) {
        student = studentService.getStudent();

        setSizeFull();
        add(new HorizontalLayout(nextPath1LessonButton, nextPath2LessonButton));
        add(lessonsGrid);

        lessonsGrid.addColumn(lesson -> lesson.book().getName());
        lessonsGrid.addColumn(Lesson::progress);
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

        refreshNextPathButtons(student.nextPath1Lesson(), nextPath1LessonButton);
        refreshNextPathButtons(student.nextPath2Lesson(), nextPath2LessonButton);
    }

    private void refreshNextPathButtons(Lesson lesson, Button lessonButton) {

        if (lesson != null) {
            lessonButton.setText(lesson.summary());
            lessonButton.addClickListener(event -> UI.getCurrent().navigate("lesson/" + lesson.getId()));
        } else {
            lessonButton.setText("Completed !");
            lessonButton.setEnabled(false);
        }
    }

    static class LessonValueProvider implements ValueProvider<Lesson, String> {

        private final int index;

        LessonValueProvider(int index) {
            this.index = index;
        }

        @Override
        public String apply(Lesson lesson) {
            return lesson.sentences().size() > index ? lesson.sentences().get(index) : null;
        }
    }

}
