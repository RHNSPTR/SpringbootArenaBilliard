package com.bjorbun.billiard.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {

    public MainView() {
        // Mengatur container agar sepenuh layar dan konten berada di tengah
        setSizeFull(); 
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 judul = new H1("Selamat Datang di Arena Billiard");
        judul.addClassNames(LumoUtility.Margin.Top.XLARGE, LumoUtility.Margin.Bottom.NONE);
        
        Paragraph deskripsi = new Paragraph("Sistem Informasi Manajemen Reservasi Biliar");
        deskripsi.addClassNames(LumoUtility.TextColor.SECONDARY, LumoUtility.Margin.Bottom.LARGE);

        // Styling Button dengan Icon dan Tema Lumo Primary (Biru)
        Button tombol = new Button("Klik Saya!", VaadinIcon.POINTER.create());
        tombol.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        
        tombol.addClickListener(e -> {
            tombol.setText("Terkilik! UI Vaadin Berhasil Berjalan.");
            tombol.setIcon(VaadinIcon.CHECK.create());
            // Ubah warna jadi hijau (success) saat diklik
            tombol.addThemeVariants(ButtonVariant.LUMO_SUCCESS); 
        });

        add(judul, deskripsi, tombol);
    }
}