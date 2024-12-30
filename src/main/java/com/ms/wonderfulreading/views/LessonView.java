package com.ms.wonderfulreading.views;

import com.ms.wonderfulreading.model.Lesson;
import com.ms.wonderfulreading.services.StudentService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@PageTitle("Lesson")
@Route(value = "lesson/:lessonId")
public class LessonView extends VerticalLayout implements BeforeEnterObserver {

    public static final String LESSON_ID = "lessonId";

    private final Span sentenceSpan = new Span();
    private final StudentService studentService;
    private List<String> sentencesToLearn;

    public LessonView(StudentService studentService) {
        this.studentService = studentService;

        HorizontalLayout horizontalLayout = new HorizontalLayout(sentenceSpan);
        horizontalLayout.setHeightFull();
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        this.setSizeFull();
        this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        this.add(horizontalLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Long id = event.getRouteParameters().get(LESSON_ID).map(Long::parseLong).orElseThrow();

        Lesson lesson = studentService.getStudent().byId(id);

        sentencesToLearn = lesson.nextSentences();

        sentenceSpan.addClassName("prevent-select");

        String s = sentencesToLearn.remove(0);
        adjustFontSize(s.length());
        sentenceSpan.setText(s);

        this.addClickListener(e -> {
            if (sentencesToLearn.isEmpty()) {
                lesson.stepDone();
                UI.getCurrent().navigate("reading");
            } else {
                String ss = sentencesToLearn.remove(0);
                sentenceSpan.setText(ss);
                adjustFontSize(ss.length());
            }
        });
    }

    private void adjustFontSize(int sentenceLength) {
        sentenceSpan.getStyle().set("font-size", sentenceLength < 10 ? "20vw" : "12vw");
    }
}
