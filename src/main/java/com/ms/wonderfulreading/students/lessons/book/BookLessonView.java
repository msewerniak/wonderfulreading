package com.ms.wonderfulreading.students.lessons.book;

import com.ms.wonderfulreading.students.Student;
import com.ms.wonderfulreading.students.StudentsService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Lesson")
@Route(value = "students/:studentName/lessons/book/:lessonId")
public class BookLessonView extends VerticalLayout implements BeforeEnterObserver {

    public static final String LESSON_ID = "lessonId";
    public static final String STUDENT_NAME = "studentName";

    private final Span sentenceSpan = new Span();
    private final StudentsService studentService;
    private List<String> sentencesToLearn = new ArrayList<>();
    private BookLesson lesson;
    private Student student;

    public BookLessonView(StudentsService studentService) {
        this.studentService = studentService;
        sentenceSpan.addClassName("prevent-select");

        HorizontalLayout horizontalLayout = new HorizontalLayout(sentenceSpan);
        horizontalLayout.setHeightFull();
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        this.setSizeFull();
        this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        this.add(horizontalLayout);
        this.addClickListener(e -> {
            if (sentencesToLearn.isEmpty()) {
                lesson.stepDone();
                UI.getCurrent().navigate("students/" + student.getName() + "/lessons");
            } else {
                showNextSentence();
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Long lessonId = event.getRouteParameters().get(LESSON_ID).map(Long::parseLong).orElseThrow();
        String studentName = event.getRouteParameters().get(STUDENT_NAME).orElseThrow();

        student = studentService.getByName(studentName);
        lesson = student.bookLesson(lessonId);
        sentencesToLearn = lesson.nextSentences();

        showNextSentence();
    }

    private void showNextSentence() {
        String ss = sentencesToLearn.remove(0);
        adjustFontSize(ss.length());
        sentenceSpan.setText(ss);
    }

    private void adjustFontSize(int sentenceLength) {
        sentenceSpan.getStyle().set("font-size", sentenceLength < 10 ? "20vw" : "12vw");
    }
}
