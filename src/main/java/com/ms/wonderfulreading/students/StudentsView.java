package com.ms.wonderfulreading.students;

import com.ms.wonderfulreading.MainView;
import com.ms.wonderfulreading.students.lessons.book.BookLessonsGenerator;
import com.ms.wonderfulreading.students.lessons.noreps.NoRepsLessonsGenerator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@PageTitle("Students")
@Route(value = "students", layout = MainView.class)
public class StudentsView extends VerticalLayout {

    private final StudentsService studentsService;
    private final BookLessonsGenerator bookLessonsGenerator;
    private final NoRepsLessonsGenerator noRepsLessonsGenerator;

    public StudentsView(StudentsService studentsService, BookLessonsGenerator bookLessonsGenerator,
            NoRepsLessonsGenerator noRepsLessonsGenerator) {

        this.studentsService = studentsService;
        this.bookLessonsGenerator = bookLessonsGenerator;
        this.noRepsLessonsGenerator = noRepsLessonsGenerator;

        refreshStudentsView();
    }

    private void refreshStudentsView() {
        removeAll();

        List<Student> students = studentsService.getStudents();

        for (Student s : students) {

            Student student = new Student(s);

            TextField studentNameTextField = new TextField(null, student.getName(), "");
            studentNameTextField.addValueChangeListener(event -> {
                student.setName(event.getValue());
            });

            Button saveButton = new Button("Save", event -> {
                studentsService.save(student);
                refreshStudentsView();
            });
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            Span bookLessonsSummarySpan = new Span("Book lessons: " + student.getBookLessons().size());
            bookLessonsSummarySpan.setWidth("150px");
            Button bookLessonsGenerateButton = new Button("Generate book lessons", event -> {
                student.updateBookLessons(bookLessonsGenerator.generate());
                bookLessonsSummarySpan.setText("Book lessons: " + student.getBookLessons().size());
            });

            Span noRepsLessonsSummarySpan = new Span("No reps lessons: " + student.getNoRepsLessons().size());
            noRepsLessonsSummarySpan.setWidth("150px");
            Button noRepsLessonsGenerateButton = new Button("Generate no reps lessons", event -> {
                student.updateNoRepsLessons(noRepsLessonsGenerator.generate());
                noRepsLessonsSummarySpan.setText("No reps lessons: " + student.getNoRepsLessons().size());
            });

            Button goToStudentButton = new Button("Learnings", event -> {
                UI.getCurrent().navigate("students/" + student.getName() + "/lessons");
            });
            goToStudentButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

            HorizontalLayout horizontalLayout =
                    new HorizontalLayout(studentNameTextField, saveButton, bookLessonsSummarySpan, bookLessonsGenerateButton,
                            noRepsLessonsSummarySpan, noRepsLessonsGenerateButton, goToStudentButton);
            horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
            add(horizontalLayout);
        }

        TextField newStudentNameTextField = new TextField(null, "", "new student name");
        Button newStudentSaveButton = new Button("Save", event -> {
            Student newStudent =
                    new Student(studentsService.nextStudentId(), newStudentNameTextField.getValue(), bookLessonsGenerator.generate(),
                            noRepsLessonsGenerator.generate());
            studentsService.save(newStudent);
            refreshStudentsView();
        });
        newStudentSaveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(new HorizontalLayout(newStudentNameTextField, newStudentSaveButton));
    }
}
