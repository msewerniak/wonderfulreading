package com.ms.wonderfulreading.reading;

import com.ms.wonderfulreading.BooksService;
import com.ms.wonderfulreading.MainView;
import com.ms.wonderfulreading.model.Lesson;
import com.ms.wonderfulreading.model.Student;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Reading")
@RouteAlias(value = "", layout = MainView.class)
@Route(value = "reading", layout = MainView.class)
public class ReadingView extends HorizontalLayout {

    private final Grid<Lesson> lessonsGrid = new Grid<>(Lesson.class, false);

    private final Student student;

    public ReadingView(BooksService booksService) {
        student = new Student("Szymon", booksService.getBooks());
        add(lessonsGrid);

        lessonsGrid.addColumn(lesson -> lesson.book().getName());
        lessonsGrid.addColumn(new LessonValueProvider(0));
        lessonsGrid.addColumn(new LessonValueProvider(1));
        lessonsGrid.addColumn(new LessonValueProvider(2));
        lessonsGrid.addColumn(new LessonValueProvider(3));
        lessonsGrid.addColumn(new LessonValueProvider(4));
        lessonsGrid.setItems(student.getLessons());
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
