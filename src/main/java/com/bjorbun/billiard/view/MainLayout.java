package com.bjorbun.billiard.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Arena Billiard");
        logo.addClassNames(
            LumoUtility.FontSize.LARGE,
            LumoUtility.Margin.MEDIUM);

        var header = new HorizontalLayout(new DrawerToggle(), logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.addClassNames(
            LumoUtility.Padding.Vertical.NONE,
            LumoUtility.Padding.Horizontal.MEDIUM);

        addToNavbar(header);
    }

    private void createDrawer() {
        // Menggunakan SideNav untuk menu yang jauh lebih modern
        SideNav nav = new SideNav();
        
        // Menambahkan ikon untuk masing-masing menu
        nav.addItem(new SideNavItem("Dashboard", MainView.class, VaadinIcon.DASHBOARD.create()));
        nav.addItem(new SideNavItem("Manajemen Meja", MejaView.class, VaadinIcon.TABLE.create()));
        nav.addItem(new SideNavItem("Manajemen Member", MemberView.class, VaadinIcon.USERS.create()));
        nav.addItem(new SideNavItem("Pemesanan", PemesananView.class, VaadinIcon.TICKET.create()));

        addToDrawer(nav);
    }
}