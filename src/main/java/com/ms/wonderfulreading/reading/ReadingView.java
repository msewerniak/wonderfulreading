package com.ms.wonderfulreading.reading;

import com.ms.wonderfulreading.BooksService;
import com.ms.wonderfulreading.MainView;
import com.ms.wonderfulreading.model.Student;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Reading")
@RouteAlias(value = "", layout = MainView.class)
@Route(value = "reading", layout = MainView.class)
public class ReadingView extends VerticalLayout {

    private final Student student;

    public ReadingView(BooksService booksService) {
        student = new Student("Szymon", booksService.getBooks());
    }
    

}
