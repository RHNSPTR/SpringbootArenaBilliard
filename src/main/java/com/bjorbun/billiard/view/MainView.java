package com.bjorbun.billiard.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

// Anotasi @Route menentukan URL endpoint untuk tampilan ini
@Route("") // String kosong berarti ini adalah halaman utama (localhost:8080/)
public class MainView extends VerticalLayout {

    public MainView() {
        // Membuat komponen UI menggunakan Java
        H1 judul = new H1("Selamat Datang di Arena Billiard");
        Button tombol = new Button("Klik Saya!");

        // Menambahkan interaksi (Event Listener)
        tombol.addClickListener(e -> {
            tombol.setText("Terkilik! UI Vaadin Berhasil Berjalan.");
        });

        // Menyusun komponen ke dalam layout
        add(judul, tombol);
        
        // Mengatur posisi komponen di tengah layar
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }
}