package org.vaadin.example;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Admin")
@Route(value = "admin", layout = MainView.class)
public class AdminView extends VerticalLayout {

    public AdminView() {

        Text title = new Text("You are in the admin view");
        add(title);
    }
}
