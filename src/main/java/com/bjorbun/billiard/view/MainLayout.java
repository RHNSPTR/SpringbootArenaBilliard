package com.bjorbun.billiard.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {

    public MainLayout() {
        // 1. Mengaktifkan Mode Gelap (Teks akan otomatis menjadi putih)
        getElement().getThemeList().add(Lumo.DARK);

        // 2. Mengubah variabel warna bawaan Lumo agar sesuai dengan PPT
        getStyle().set("--lumo-base-color", "#0f3a2c"); // Latar belakang hijau gelap khas meja biliar
        getStyle().set("--lumo-primary-color", "#d7b257"); // Warna aksen utama (Kuning Emas)
        getStyle().set("--lumo-primary-text-color", "#d7b257"); // Teks primary kuning emas

        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Arena Billiard");
        logo.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM);
        // Menggunakan font serif untuk logo agar sesuai dengan PPT
        logo.getStyle().set("font-family", "Georgia, serif");

        DrawerToggle toggle = new DrawerToggle();
        toggle.getStyle().set("color", "#d7b257"); // Ikon garis tiga (burger menu) berwarna emas

        var header = new HorizontalLayout(toggle, logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM);
        
        // Warna header dibuat sedikit lebih gelap dari warna latar utama
        header.getStyle().set("background-color", "#0a261d");

        addToNavbar(header);
    }

    private void createDrawer() {
        SideNav nav = new SideNav();
        
        nav.addItem(new SideNavItem("Dashboard", MainView.class, VaadinIcon.DASHBOARD.create()));
        nav.addItem(new SideNavItem("Manajemen Meja", MejaView.class, VaadinIcon.TABLE.create()));
        nav.addItem(new SideNavItem("Manajemen Member", MemberView.class, VaadinIcon.USERS.create()));
        nav.addItem(new SideNavItem("Pemesanan", PemesananView.class, VaadinIcon.TICKET.create()));

        addToDrawer(nav);
    }
}