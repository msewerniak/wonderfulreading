package com.ms.wonderfulreading.students.lessons;

import com.ms.wonderfulreading.MainView;
import com.ms.wonderfulreading.students.Student;
import com.ms.wonderfulreading.students.StudentsService;
import com.ms.wonderfulreading.students.lessons.book.BookLesson;
import com.ms.wonderfulreading.students.lessons.noreps.NoRepsLesson;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.Optional;

@PageTitle("Lessons")
@RouteAlias(value = "", layout = MainView.class)
@Route(value = "students/:studentName/lessons", layout = MainView.class)
public class LessonsView extends HorizontalLayout implements BeforeEnterObserver {

    public static final String STUDENT_NAME = "studentName";

    private final Button nextPath1BookLessonButton = new Button();
    private final Button nextPath2BookLessonButton = new Button();
    private final Button nextWordsButton = new Button();

    private final Grid<BookLesson> bookLessonGrid;
    private final Grid<NoRepsLesson> noRepsLessonGrid;

    private final StudentsService studentService;

    private Student student;

    public LessonsView(StudentsService studentService) {
        this.studentService = studentService;

        bookLessonGrid = new Grid<>(BookLesson.class, false);
        bookLessonGrid.addColumn(lesson -> lesson.book().getName());
        bookLessonGrid.addColumn(BookLesson::progress);
        bookLessonGrid.addColumn(new BookLessonValueProvider(0));
        bookLessonGrid.addColumn(new BookLessonValueProvider(1));
        bookLessonGrid.addColumn(new BookLessonValueProvider(2));
        bookLessonGrid.addColumn(new BookLessonValueProvider(3));
        bookLessonGrid.addColumn(new BookLessonValueProvider(4));
        bookLessonGrid.setPartNameGenerator(lesson -> lesson.isDone() ? "high-rating" : (lesson.isInProgress() ? "low-rating" : ""));

        noRepsLessonGrid = new Grid<>(NoRepsLesson.class, false);
        noRepsLessonGrid.addColumn(NoRepsLesson::progress);
        noRepsLessonGrid.addColumn(new NoRepsLessonValueProvider(0));
        noRepsLessonGrid.addColumn(new NoRepsLessonValueProvider(1));
        noRepsLessonGrid.addColumn(new NoRepsLessonValueProvider(2));
        noRepsLessonGrid.addColumn(new NoRepsLessonValueProvider(3));
        noRepsLessonGrid.addColumn(new NoRepsLessonValueProvider(4));
        noRepsLessonGrid.setPartNameGenerator(lesson -> lesson.isDone() ? "high-rating" : (lesson.isInProgress() ? "low-rating" : ""));

        setSizeFull();
        add(new VerticalLayout(new HorizontalLayout(nextPath1BookLessonButton, nextPath2BookLessonButton), bookLessonGrid));
        add(new VerticalLayout(nextWordsButton, noRepsLessonGrid));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        Optional<String> studentParam = beforeEnterEvent.getRouteParameters().get(STUDENT_NAME);

        if (studentParam.isEmpty()) {
            UI.getCurrent().navigate("students/Szymon/lessons");
        }

        String studentName = studentParam.orElse("Szymon");
        this.student = studentService.getByName(studentName);

        if (student == null) {
            Notification.show("Cannot find student with name " + studentName);
            return;
        }

        noRepsLessonGrid.setItems(student.getNoRepsLessons());
        noRepsLessonGrid.addItemDoubleClickListener(event -> {
            UI.getCurrent().navigate("students/" + student.getName() + "/lessons/no-reps/" + event.getItem().getId());
        });

        bookLessonGrid.setItems(student.getBookLessons());
        bookLessonGrid.addItemDoubleClickListener(event -> {
            UI.getCurrent().navigate("students/" + student.getName() + "/lessons/book/" + event.getItem().getId());
        });

        refreshBookNextPathButton(student.nextPath1BookLesson(), nextPath1BookLessonButton);
        refreshBookNextPathButton(student.nextPath2BookLesson(), nextPath2BookLessonButton);
        refreshWLessonButtons(student.nextNoRepsLesson(), nextWordsButton);
    }


    private void refreshBookNextPathButton(BookLesson lesson, Button bookLessonButton) {

        if (lesson != null) {
            bookLessonButton.setText(lesson.summary());
            bookLessonButton.addClickListener(
                    event -> UI.getCurrent().navigate("students/" + student.getName() + "/lessons/book/" + lesson.getId()));
        } else {
            bookLessonButton.setText("Completed !");
            bookLessonButton.setEnabled(false);
        }
    }

    private void refreshWLessonButtons(NoRepsLesson lesson, Button noRepsLessonButton) {

        if (lesson != null) {
            noRepsLessonButton.setText(lesson.summary());
            noRepsLessonButton.addClickListener(
                    event -> UI.getCurrent().navigate("students/" + student.getName() + "/lessons/no-reps/" + lesson.getId()));
        } else {
            noRepsLessonButton.setText("Completed !");
            noRepsLessonButton.setEnabled(false);
        }
    }

    static class BookLessonValueProvider implements ValueProvider<BookLesson, String> {

        private final int index;

        BookLessonValueProvider(int index) {
            this.index = index;
        }

        @Override
        public String apply(BookLesson lesson) {
            return lesson.getSentences().size() > index ? lesson.getSentences().get(index) : null;
        }
    }

    static class NoRepsLessonValueProvider implements ValueProvider<NoRepsLesson, String> {

        private final int index;

        NoRepsLessonValueProvider(int index) {
            this.index = index;
        }

        @Override
        public String apply(NoRepsLesson lesson) {
            return lesson.getSentences().size() > index ? lesson.getSentences().get(index) : null;
        }
    }

}
