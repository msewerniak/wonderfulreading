package org.vaadin.example;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Learing to read")
@RouteAlias(value = "", layout = MainView.class)
@Route(value = "reading", layout = MainView.class)
public class ReadingView extends VerticalLayout {

    public ReadingView() {

        Text text = new Text("You are in the home view");
        add(text);
    }
}
