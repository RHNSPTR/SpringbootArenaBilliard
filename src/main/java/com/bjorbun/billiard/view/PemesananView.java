package com.bjorbun.billiard.view;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "pemesanan", layout = MainLayout.class)
public class PemesananView extends VerticalLayout {
    public PemesananView() {
        add(new H2("Halaman Pemesanan"));
    }
}