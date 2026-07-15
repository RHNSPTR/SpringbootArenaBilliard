package com.bjorbun.billiard.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {

    public MainView() {
        // Mengatur elemen agar berada pas di tengah halaman
        setSizeFull(); 
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // 1. Teks Emas Kecil di Atas (Mengikuti tulisan "PEMROGRAMAN BERBASIS OBJEK")
        H3 subJudulEmas = new H3("PEMROGRAMAN BERBASIS OBJEK");
        subJudulEmas.getStyle().set("color", "#d7b257");
        subJudulEmas.getStyle().set("letter-spacing", "2px");
        subJudulEmas.addClassNames(LumoUtility.Margin.Bottom.NONE);

        // 2. Judul Utama Besar bergaya Serif
        H1 judul = new H1("ARENA BILLIARD");
        judul.getStyle().set("font-family", "Georgia, serif");
        judul.getStyle().set("font-size", "4rem");
        judul.addClassNames(LumoUtility.Margin.Top.NONE, LumoUtility.Margin.Bottom.SMALL);
        
        // 3. Deskripsi bercetak miring (Italic)
        Paragraph deskripsi = new Paragraph("Sistem Informasi Manajemen Reservasi Billiard");
        deskripsi.getStyle().set("font-style", "italic");
        deskripsi.getStyle().set("font-size", "1.2rem");
        deskripsi.addClassNames(LumoUtility.TextColor.BODY, LumoUtility.Margin.Bottom.XLARGE);

        // 4. Tombol Aksi berwarna Emas
        Button tombol = new Button("Mulai Navigasi", VaadinIcon.ARROW_RIGHT.create());
        tombol.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        // Mengubah warna teks tombol menjadi gelap agar kontras dengan latar tombol yang emas
        tombol.getStyle().set("color", "#0a261d"); 
        tombol.getStyle().set("font-weight", "bold");
        
        tombol.addClickListener(e -> {
            tombol.setText("Sistem Siap Digunakan!");
            tombol.setIcon(VaadinIcon.CHECK.create());
        });

        add(subJudulEmas, judul, deskripsi, tombol);
    }
}