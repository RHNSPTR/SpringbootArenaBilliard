package com.bjorbun.billiard.view;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "member", layout = MainLayout.class)
public class MemberView extends VerticalLayout {
    public MemberView() {
        add(new H2("Halaman Manajemen Member"));
    }
}